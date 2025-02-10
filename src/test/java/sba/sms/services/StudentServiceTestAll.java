package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import sba.sms.models.Student;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTestAll {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private static StudentService studentService;
    private static Student student;
    private static final String studentEmail = "mary@poppin.com";

    @BeforeAll
    static void beforeAll() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        studentService = new StudentService();
    }

    @AfterAll
    static void afterAll() {
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
    @Order(1)
    public void testCreateStudent() {
        student = new Student(studentEmail, "Mary Poppin", "password");
        studentService.createStudent(student);

//        studentEmail = "mary@poppin.com";

        Student fetchedStudent = studentService.getStudentByEmail(studentEmail);
        assertNotNull(fetchedStudent, "Student should be fetched");

    }
    @Test
    @Order(2)
    public void testGetStudentByEmail() {
        Student fetchedStudent = studentService.getStudentByEmail(studentEmail);
        assertNotNull(fetchedStudent.getEmail(), "Student should be fetched");
        assertEquals(studentEmail, fetchedStudent.getEmail(), "Student Email should match");
    }


    @Test
    @Order(3)
    public void testValidateStudent() {
        assertTrue(studentService.validateStudent("mary@poppin.com", "password"), "Validate student failed");
    }

}
