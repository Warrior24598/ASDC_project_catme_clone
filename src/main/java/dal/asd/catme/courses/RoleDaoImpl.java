package dal.asd.catme.courses;

import dal.asd.catme.accesscontrol.IRoleDao;
import dal.asd.catme.accesscontrol.IUserDao;
import dal.asd.catme.config.SystemConfig;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.util.CatmeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dal.asd.catme.util.DBQueriesUtil.*;

public class RoleDaoImpl implements IRoleDao
{
    IUserDao userDao;

    ICourseDao courseDao;

    @Override
    public int assignRole(String bannerId, int roleId, Connection con)
    {
        int rs = 0;
        try
        {
            PreparedStatement stmt = con.prepareStatement(ASSIGN_ROLE_QUERY);
            stmt.setString(1, bannerId);
            stmt.setInt(2, roleId);

            rs = stmt.executeUpdate();
            return rs;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public int addInstructor(String courseId, int userRoleId, Connection con)
    {
        int rs = 0;
        try
        {
            PreparedStatement stmt = con.prepareStatement(INSERT_COURSE_INSTRUCTOR_QUERY);
            stmt.setString(1, courseId);
            stmt.setInt(2, userRoleId);

            rs = stmt.executeUpdate();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public int checkCourseInstructor(String bannerId, String courseId, Connection con)
    {
        int rowCount = 0;
        try
        {
            PreparedStatement stmt = con.prepareStatement(CHECK_COURSE_INSTRUCTOR_QUERY);
            stmt.setString(1, bannerId);
            stmt.setString(2, courseId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return rowCount;
    }

    @Override
    public int checkUserRole(String bannerId, int roleId, Connection con)
    {

        int rowCount = 0;
        try
        {
            PreparedStatement stmt = con.prepareStatement(CHECK_USER_ROLE);
            stmt.setString(1, bannerId);
            stmt.setInt(2, roleId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return rowCount;

    }

    @Override
    public int getUserRoleId(String bannerId, int roleId, Connection con)
    {

        int userRoleId = -1;
        try
        {
            PreparedStatement stmt = con.prepareStatement(GET_USER_ROLEID_QUERY);
            stmt.setString(1, bannerId);
            stmt.setInt(2, roleId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            userRoleId = rs.getInt(1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return userRoleId;

    }

    @Override
    public int assignTa(Enrollment user, Connection con)
    {
        String isAssigned = "";
        int result=0;
        try
        {
            userDao = SystemConfig.instance().getUserDao();
            if (CatmeUtil.ONE == userDao.checkExistingUser(user.bannerId, con))
            {
                courseDao = SystemConfig.instance().getCourseDao();
                if (CatmeUtil.ONE == courseDao.checkCourseExists(user.courseId, con))
                {
                    if (CatmeUtil.ZERO == courseDao.checkCourseRegistration(user.bannerId, user.courseId, con))
                    {
                        if (CatmeUtil.ZERO == checkCourseInstructor(user.bannerId, user.courseId, con))
                        {
                            if (CatmeUtil.ONE == checkUserRole(user.bannerId, CatmeUtil.TA_ROLE_ID, con))
                            {
                                int userRoleId = getUserRoleId(user.bannerId, CatmeUtil.TA_ROLE_ID, con);
                                result=addInstructor(user.courseId, userRoleId, con);
                            }

                            else
                            {
                                assignRole(user.bannerId, CatmeUtil.TA_ROLE_ID, con);
                                int userRoleId = getUserRoleId(user.bannerId, CatmeUtil.TA_ROLE_ID, con);
                                result=addInstructor(user.courseId, userRoleId, con);
                            }
                            throw new CatmeException("The user is successfully assigned as TA.");

                        } else
                        {
                        	throw new CatmeException("This user is already an instructor for this course.");
                        }
                    } else
                    {
                    	throw new CatmeException("This user is currently registered in this course. Failed to assign.");
                    }
                } else
                {
                	throw new CatmeException("No course exists with this Course Id. Failed to assign.");
                }

            } else
            {
            	throw new CatmeException("No user exists with this Banner Id. Failed to assign.");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}

