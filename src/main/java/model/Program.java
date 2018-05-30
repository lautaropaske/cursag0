package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sun.nio.cs.US_ASCII;

import javax.persistence.*;
import java.util.*;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    @Lob
    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<ProgramCourse> courses = new HashSet<>();

    @ManyToOne
    private User publisher;


    @ManyToMany(mappedBy = "enrolledPrograms")
    @JsonIgnore
    private Set<User> enrolledStudents = new HashSet<>();

    public Program(){}

    public Program(String name, String description, User publisher) {
        this.name = name;
        this.description = description;
        this.publisher = publisher;
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

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Set<ProgramCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<ProgramCourse> courses) {
        this.courses = courses;
    }
}