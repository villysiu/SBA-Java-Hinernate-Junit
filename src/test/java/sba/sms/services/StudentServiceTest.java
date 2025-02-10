package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class StudentServiceTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private StudentService studentService;
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        studentService = new StudentService();
        courseService = new CourseService();
    }

    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void getStudentByEmail() {
        Student student = new Student("jane@doe.com","Jane","password" );
        studentService.createStudent(student);

        Student fetchedStudent = studentService.getStudentByEmail("jane@doe.com");

        assertNotNull(fetchedStudent, "Student should be retrievable.");
        assertEquals("jane@doe.com", fetchedStudent.getEmail(), "Email should match");
        assertEquals("Jane", fetchedStudent.getName(), "Name should match");
        assertEquals("password", fetchedStudent.getPassword(), "Password should match");

    }

    @Test
    void validateStudent() {
        Student student = new Student("jack@doe.com","Jane","password" );
        studentService.createStudent(student);
//        System.out.println("_______________________________");
//        System.out.println(studentService.getAllStudents().size());
        boolean status = studentService.validateStudent("jack@doe.com","password");

        assertTrue(status, "Validate student failed");
    }

//    @Test
//    void registerStudentToCourse() {
//        Student student = new Student("paul@doe.com","Jane","password" );
//        studentService.createStudent(student);
//
//
//
//        Course course = new Course("Java", "Raheem");
//        courseService.createCourse(course);
//
//        studentService.registerStudentToCourse(student.getEmail(), course.getId());
//        Course fetchedCourse = courseService.getCourseById(course.getId());
//        assertNotNull(fetchedCourse, "Course should be retrievable.");
//
//        Student fetchedStudent = studentService.getStudentByEmail("jane@doe.com");
//        assertNotNull(fetchedStudent, "Student should be retrievable.");
//
//        Set<Student> fetchedStudentsInCourse = fetchedCourse.getStudents();
//        assertTrue(fetchedStudentsInCourse.contains(student), "course.students() set should contain student");
//
//        Set<Course> fetchedCoursesInStudent = fetchedStudent.getCourses();
//        assertTrue(fetchedCoursesInStudent.contains(course), "student.courses() set should contain course");
//
//    }
//
//    @Test
//    void getStudentCourses() {
//    }
}