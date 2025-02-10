package sba.sms.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import sba.sms.models.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseServiceTestAll {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private static CourseService courseService;

    private static Course course;
    private static int courseId;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        courseService = new CourseService();
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
    void testCreateCourse() {
        course = new Course("Java", "Raheem");
        courseService.createCourse(course);

        courseId = course.getId();
        Course fetchedCourse = courseService.getCourseById(courseId);

        assertNotNull(fetchedCourse, "Course should not be null");
        assertEquals("Java", fetchedCourse.getName(), "Course name should match");
        assertEquals("Raheem", fetchedCourse.getInstructor(), "Course instructor should match");
    }
    @Test
    @Order(2)
    void testGetCourseById() {
        Course fetchedCourse = courseService.getCourseById(courseId);
        assertNotNull(fetchedCourse, "Course should not be null");
        assertEquals("Java", fetchedCourse.getName(), "Course name should match");
        assertEquals("Raheem", fetchedCourse.getInstructor(), "Course instructor should match");

    }

    @Test
    @Order(3)
    void testGetAllCourse() {

         Course course2 = new Course("Python", "Roger");
         courseService.createCourse(course2);

         List<Course> fetchedCourses = courseService.getAllCourses();
        assertNotNull(fetchedCourses, "Course should not be null");
         assertTrue(fetchedCourses.size() >= 2, "Course list should not be empty");


    }

}
