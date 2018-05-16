package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
    @ManyToMany
    @JsonIgnore
    private Collection<Course> courses;

    public Program(){}

    public Program(String name, String description, User publisher) {
        this.name = name;
        this.description = description;
        this.publisher = publisher;
        this.courses = new ArrayList<>();
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
}