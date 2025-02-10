package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    @Override
    public void createStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, email);
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            Student student = session.get(Student.class, email);
//            Student student = getStudentByEmail(email);
            if (student != null && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);

            if(student != null && course != null && !course.getStudents().contains(student)) {

                course.getStudents().add(student);
                student.getCourses().add(course);
                session.merge(course);

            }
            transaction.commit();
        }

    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = sessionFactory.openSession()) {
            Set<Course> courses = session.get(Student.class, email).getCourses();

            return new ArrayList<>(courses);
        }
    }
}
