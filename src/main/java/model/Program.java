package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sun.nio.cs.US_ASCII;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private double rating;

    @ManyToOne
    private User publisher;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "Program_Course",
            joinColumns = { @JoinColumn(name = "program_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @ManyToMany(mappedBy = "enrolledPrograms")
    @JsonIgnore
    private Set<User> enrolledStudents = new HashSet<>();

    public Program(){}

    public Program(String name, String description, User publisher) {
        this.name = name;
        this.description = description;
        this.publisher = publisher;
    }

    public void addCourse(Course course) {
        courses.add(course);
        int r = courses.size();
        rating = rating * (r-1)/(r) + (course.getRating()/r);
    }

    public User getPublisher() {
        return publisher;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public Collection<Course> getCourses() {
        return courses;
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }
}