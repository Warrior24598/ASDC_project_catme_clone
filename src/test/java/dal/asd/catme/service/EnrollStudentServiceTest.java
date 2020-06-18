package dal.asd.catme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import dal.asd.catme.beans.Course;
import dal.asd.catme.beans.Role;
import dal.asd.catme.beans.Student;
import dal.asd.catme.beans.User;
import dal.asd.catme.dao.IRoleDao;
import dal.asd.catme.dao.IStudentDao;
import dal.asd.catme.dao.RoleDaoMock;
import dal.asd.catme.exception.EnrollmentException;
import dal.asd.catme.dao.JavaMailSenderMock;
import dal.asd.catme.dao.StudentDaoMock;
import dal.asd.catme.util.CatmeUtil;

class EnrollStudentServiceTest
{

    ArrayList<User> users = getUsers();
    Course c = getCourse();
    Student s = getStudent();

    IRoleDao roleDao = new RoleDaoMock(users,c);
    IStudentDao studentDao = new StudentDaoMock(s);

    IMailSenderService mailSenderService = new MailSenderService(new JavaMailSenderMock(s,"Sub","Body"));
    @Test
    void assignStudentRoleToStudent()
    {
        EnrollStudentService service = new EnrollStudentService(roleDao,studentDao);

        try
        {
            service.assignStudentRole(users.get(0));

            List<Role> roles = users.get(users.size()-1).getRole();
            assertEquals(String.valueOf(CatmeUtil.STUDENT_ROLE_ID),roles.get(roles.size()-1).getRoleId());
        } catch (EnrollmentException e)
        {
            e.printStackTrace();
            fail();
        }

        try
        {
            service.assignStudentRole(new User("B00454545","A","B","a@b"));

            fail();
        } catch (EnrollmentException e)
        {
        }
    }

    @Test
    void enrollStudent()
    {
        EnrollStudentService service = new EnrollStudentService(roleDao,studentDao);
        try
        {
            service.enrollStudent(s,c);
            assertEquals(s.enrolledCourses.get(0),c);
        } catch (EnrollmentException e)
        {
            e.printStackTrace();
            fail();
        }
    }

    ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        User u = new User("B00121212","Last","First","abc@123.com");
        ArrayList<Role> roles = new ArrayList<>();
        u.setRole(roles);

        users.add(u);
        return users;
    }

    Course getCourse()
    {
        Course c = new Course();

        c.setCourseId("5308");
        c.setCourseName("ADV SDC");
        return c;
    }
    Student getStudent()
    {
        Student s = new Student("B00121212","Last","First","abc@123.com");
        ArrayList<Course> courses = new ArrayList<>();
        s.enrolledCourses = courses;
        return s;
    }

}