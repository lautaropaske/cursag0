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

    @ManyToMany(mappedBy = "enrolled")
    @JsonIgnore
    private Set<User> enrolledStudents = new HashSet<User>();


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

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void enrollStudents(User user){
        enrolledStudents.add(user);
    }

    @Override
    public int compareTo(@NotNull Course o) {
        return Double.compare(this.rating, o.getRating());
    }
}
