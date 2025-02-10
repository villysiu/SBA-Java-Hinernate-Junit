package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import sba.sms.models.Course;

import static org.junit.jupiter.api.Assertions.*;


class CourseServiceTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        courseService = new CourseService();
    }

    @AfterEach
    void tearDown() {
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
    void createCourse() {
        Course course = new Course("Java", "Raheem");
        courseService.createCourse(course);

        Course fetchedCourse = courseService.getCourseById(course.getId());


        assertNotNull(fetchedCourse, "Course should not be null");
        assertEquals("Java", fetchedCourse.getName(), "Course name should match");
        assertEquals("Raheem", fetchedCourse.getInstructor(), "Course instructor should match");
    }


}