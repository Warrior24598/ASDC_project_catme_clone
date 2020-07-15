package dal.asd.catme.util;

public class DBQueriesUtil
{

    public static final String SELECT_USER_SECURITY_QUERY ="CALL getUserSecurity(?)";
    public static final String SELECT_ROLE_SECURITY_QUERY = "CALL getRoleSecurity(?)";
    public static final String SELECT_GUEST_COURSES_QUERY ="CALL getAllCourseDetails()";
    public static final String SELECT_INSTRUTOR_COURSES_QUERY ="CALL getInstructorCourseDetails(?)";
    public static final String SELECT_COURSE_ROLE_QUERY ="CALL getCourseRole(?,?)";
    public static final String SELECT_COURSE_QUERY ="CALL getCourseDetails(?)";
    public static final String SELECT_STUDENT_COURSES_QUERY = "CALL getStudentCourseDetails(?)";
    public static final String SELECT_STUDENT_INSTRUCTOR_COURSE="CALL getStudentInstructorCourseDetails(?)";
    public static final String SELECT_COURSE = "CALL getAllCourses()";
    
    
    public static final String SELECT_COURSE_INSTRUCTOR_BY_USER_ROLE_COURSEID = "select * from CourseInstructor where userRoleId=? and courseId=?";
    //public static final String SELECT_COURSE_INSTRUCTOR_BY_USER_ROLE_COURSEID="CALL fetchCourseInstructor(?,?)";
    public static final String DELETE_COURSE_INSTRUCTOR_QUERY = "delete from CourseInstructor where CourseId=?";
    //public static final String DELETE_COURSE_INSTRUCTOR_QUERY="CALL deleteCourseInstructor(?)";
    public static final String DELETE_ENROLLMENT_QUERY = "delete from Enrollment where CourseId=?";
   // public static final String DELETE_ENROLLMENT_QUERY = "CALL deleteEnrollment(?)";
    public static final String DELETE_COURSE_QUERY = "delete from Course where CourseId=?";
    //public static final String DELETE_COURSE_QUERY = "CALL deleteCourse(?)";
    public static final String ADD_COURSE_QUERY = "insert into Course values(?,?)";
    //public static final String ADD_COURSE_QUERY = "CALL addCourse(?,?)";
    public static final String SELECT_ROLE_BY_ROLENAME = "select * from Role where roleName=?";
    //public static final String SELECT_ROLE_BY_ROLENAME = "CALL fetchRole(?)";
    public static final String SELECT_USER_ROLE_BY_BANNERID = "select * from UserRole where bannerId=? and RoleId=?";
    //public static final String SELECT_USER_ROLE_BY_BANNERID = "CALL fetchUserRole(?,?)";
    public static final String INSERT_INTO_USER_ROLE = "insert into UserRole(RoleId,BannerId) values(?,?)";
    //public static final String INSERT_INTO_USER_ROLE = "CALL createUserRole(?,?)";
    public static final String INSERT_INTO_COURSE_INSTRUCTOR = "insert into CourseInstructor(CourseId,UserRoleId) values(?,?)";
    //public static final String INSERT_INTO_COURSE_INSTRUCTOR = "CALL createCourseInstructor(?,?)";
    public static final String LIST_USER_QUERY = "select * from User where BannerId not in (select BannerId from UserRole where RoleId in (select RoleId from Role where RoleName=?))";
    //public static final String LIST_USER_QUERY = "CALL getUsers(?)";
    public static final String CHECK_INSTRUCTOR = "select * from Course where CourseId=?";
    //public static final String CHECK_INSTRUCTOR = "CALL checkGetInstructor(?)";
    public static final String SEELCT_ENROLLED_STUDENTS_QUERY = "select BannerId, FirstName, LastName from Enrollment join(`User`) using(BannerId) where CourseId=?";
    //public static final String SEELCT_ENROLLED_STUDENTS_QUERY = "CALL getEnrolledStudents(?)";
    public static final String CHECK_COURSE_QUERY = "SELECT EXISTS(SELECT * FROM Course WHERE CourseId  = ?);";
    //public static final String CHECK_COURSE_QUERY = "CALL checkCourseExists(?)";
    public static final String CHECK_COURSE_REGISTRATION_QUERY = "SELECT EXISTS(SELECT * FROM Enrollment WHERE BannerId = ? AND CourseId = ?);";
    //public static final String CHECK_COURSE_REGISTRATION_QUERY = "CALL checkCourseRegistration(?,?)";
    public static final String ASSIGN_ROLE_QUERY = "INSERT IGNORE INTO UserRole (BannerId, RoleId) VALUES ( ? ,? );";
    //public static final String ASSIGN_ROLE_QUERY = "CALL assignRole(?,?)";
    public static final String INSERT_COURSE_INSTRUCTOR_QUERY = "INSERT IGNORE INTO CourseInstructor (CourseId, UserRoleId) VALUES (?,?);";
    //public static final String INSERT_COURSE_INSTRUCTOR_QUERY = "CALL insertCourseInstructor(?,?)";
    public static final String CHECK_COURSE_INSTRUCTOR_QUERY = "SELECT EXISTS(WITH temp AS ( SELECT ci.UserRoleId,ci.CourseId, ur.BannerId FROM CourseInstructor ci INNER JOIN UserRole ur ON ci.UserRoleId = ur.UserRoleId ) SELECT * FROM temp WHERE temp.BannerId = ? AND temp.CourseId = ?);";
    //public static final String CHECK_COURSE_INSTRUCTOR_QUERY = "CALL checkCourseInstructorExists(?,?)";
    public static final String CHECK_USER_ROLE = "SELECT EXISTS(SELECT * FROM UserRole WHERE BannerId = ? AND RoleId = ? );";
    //public static final String CHECK_USER_ROLE = "CALL checkUserRoleExists(?,?)";
    public static final String GET_USER_ROLEID_QUERY = "SELECT UserRoleId FROM UserRole WHERE BannerId = ? AND RoleId = ?;";
    //public static final String GET_USER_ROLEID_QUERY = "CALL getUserRole(?,?)";
    public static final String STUDENT_ENROLL_QUERY = "INSERT INTO Enrollment (BannerId, CourseId) VALUES(?,?);";
    //public static final String STUDENT_ENROLL_QUERY = "CALL enrollStudent(?,?)";
    public static final String CHECK_EXISTING_USER_QUERY = "SELECT EXISTS(SELECT * FROM User WHERE BannerId = ? );";
    //public static final String CHECK_EXISTING_USER_QUERY = "CALL checkUserExists(?)";
    public static final String ADD_USER_QUERY = "INSERT IGNORE INTO User (BannerId, FirstName, LastName, EmailId, Password) VALUES ( ? , ? , ? , ? , ? );";
    //public static final String ADD_USER_QUERY = "CALL addUser( ? , ? , ? , ? , ? )";
    public static final String GET_USER_QUERY = "SELECT * FROM User WHERE BannerId=?";
    //public static final String GET_USER_QUERY = "CALL fetchUser(?)";
    public static final String RESET_PASSWORD_QUERY = "UPDATE `User` SET `Password`=? WHERE `BannerId`=?";
    //public static final String RESET_PASSWORD_QUERY = "CALL resetPass(?)";
    public static final String GENERATE_PASSWORD_RESET_TOKEN = "insert into PasswordResetTokens (BannerId ,Token ) values(?,?);";
    //public static final String GENERATE_PASSWORD_RESET_TOKEN = "CALL getPassToken(?,?)";
    public static final String READ_BANNERID_FROM_TOKEN = "select BannerId from PasswordResetTokens where Token =?;";
    //public static final String READ_BANNERID_FROM_TOKEN = "CALL getUserToken(?)";
    public static final String REMOVE_TOKEN = "delete from PasswordResetTokens where BannerId =?;";
    //public static final String REMOVE_TOKEN = "CALL deleteToken(?)";
    public static final String CHECK_QUESTION_TITLE = "select * from QuestionTitle where QuestionTitleText=?";
    //public static final String CHECK_QUESTION_TITLE = "CALL checkQuestionTitle(?)";
    public static final String INSERT_QUESTION_TITLE = "insert into QuestionTitle(QuestionTitleText,UserRoleId) values(?,?)";
    //public static final String INSERT_QUESTION_TITLE = "CALL insertQuestionTitle(?,?)";
    public static final String CHECK_QUESTION = "select * from Question where QuestionTitleId=? and QuestionText=?";
    //public static final String CHECK_QUESTION = "CALL checkQuestion(?,?)";
    public static final String INSERT_QUESTION = "insert into Question(QuestionTitleId,QuestionText,QuestionType) values(?,?,?)";
    //public static final String INSERT_QUESTION = "insertQuestion(?,?,?)";
    public static final String CHECK_QUESTION_OPTION = "select * from QuestionOption where QuestionId=? and QuestionOption=?";
    //public static final String CHECK_QUESTION_OPTION = "checkQuestionOption(?,?)";
    public static final String INSERT_QUESTION_OPTION = "insert into QuestionOption(QuestionId,QuestionOption,OptionOrder) values(?,?,?)";
    //public static final String INSERT_QUESTION_OPTION = "CALL insertQuestionOption(?,?,?)";
    
    public static final String GET_QUESTIONS = "CALL GetQuestionsList(?)";
    public static final String CHECK_EXISTING_QUESTION_QUERY = "SELECT EXISTS(SELECT * FROM Question WHERE QuestionId = ? );";
    //public static final String CHECK_EXISTING_QUESTION_QUERY = "CALL checkQuestionExists(?)";
    public static final String DELETE_QUESTION_QUERY = "DELETE FROM Question WHERE QuestionId = ? ;";
    public static final String FETCH_SURVEY_QUESTIONS="CALL getSurveyQuestionsByCourse(?)";
    public static final String GET_SURVEY_QUESTION_RULE="CALL getSurveyQuestionRule(?)";
    public static final String GET_SURVEY_BY_COURSE="CALL getSurveyByCourse(?)";
    public static final String ADD_QUESTION_TO_SURVEY="CALL createSurveyQuestion(?,?,?,?)";
    public static final String ADD_RULE_SURVEY_QUESTION="CALL createSurveyQuestionRule(?,?)";
    public static final String DELETE_ALL_SURVEY_QUESTIONS="CALL deleteAllSurveyQuestions(?)";
    public static final String IS_PUBLISHED="CALL GetPublishedSurvey(?)";
    public static final String PUBLISH_SURVEY="CALL publishSurvey(?)";
    public static final String UPDATE_SURVEY_GROUPSIZE="CALL updateSurvey(?,?)";
    public static final String GET_PRIORITY="CALL getPriority(?,?)";
    public static final int DEFAULT_SURVEY_QUESTION_RULE=1;
    public static final int DEFAULT_PRIORITY=1;
    public static final String DELETE_SURVEY_QUESTION="CALL deleteSurveyQuestion(?,?)";
    public static final String GET_PUBLISHED_SURVEY = "call GetPublishedSurvey(?);";
    public static final String GET_SURVEY_QUESTIONS = "call GetSurveyQuestions(?);";
    public static final String SAVE_ANSWER = "call SaveAnswer(?,?,?);";
    public static final String SAVE_ANSWER_WITH_VALUE = "call SaveAnswerWithValue(?,?,?,?);";
    public static final String SAVE_ATTEMPT = "call SaveSurveyAttempt(?,?)";
    public static final String GET_ATTEMPT = "call GetSurveyAttempt(?,?)";

}

