package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
    private Admin publisher;
    @ManyToMany
    @JsonIgnore
    private Collection<Course> courses;

    public Program(){}

    public Program(String name, String description, Admin publisher) {
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
}