package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Inheritance(strategy = InheritanceType.JOINED)
public class Course implements Comparable<Course>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Field (termVector = TermVector.YES)
    private String name;

    @Field
    private String description;

    private double price;

    // TODO - Design proper rating strategy
    private double rating;

    @ManyToOne
    private User publisher;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<UserCourse> enrolledStudents = new HashSet<>();


    public Course() {
    }

    protected Course(String name, String description, double price, double rating, User publisher) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public User getPublisher() { return publisher; }

    public Set<UserCourse> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<UserCourse> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public int compareTo(@NotNull Course o) {
        return Double.compare(this.rating, o.getRating());
    }
}
