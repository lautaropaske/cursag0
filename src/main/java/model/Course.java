package model;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    private double price;

    // TODO - Design proper rating strategy
    private double rating;

    @ManyToOne
    private User publisher;

//    @ManyToMany(mappedBy = "enrolled") - Test later
//    private Collection<User> students;

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

    public User getPublisher() {
        return publisher;
    }

//    public Collection<User> getStudents() { - Test later
//        return students;
//    }
}
