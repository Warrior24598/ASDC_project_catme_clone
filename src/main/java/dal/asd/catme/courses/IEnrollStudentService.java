package dal.asd.catme.courses;

import dal.asd.catme.accesscontrol.Student;
import dal.asd.catme.accesscontrol.User;
import dal.asd.catme.exception.EnrollmentException;

import java.util.ArrayList;

public interface IEnrollStudentService
{
    boolean enrollStudentsIntoCourse(ArrayList<Student> students, Course c);

    void enrollStudent(Student s, Course c) throws EnrollmentException;

    void assignStudentRole(User student) throws EnrollmentException;
}
