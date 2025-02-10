package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * CourseRepo is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A CourseRepo object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 50, nullable = false)
    private String name;

    @Column(name="instructor", length = 50, nullable = false)
    private String instructor;

    @ManyToMany(mappedBy = "courses",cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();


    public Course() {
    }
    public Course(String name, String instructor) {

        this.name = name;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "CourseRepo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor);
    }
}
