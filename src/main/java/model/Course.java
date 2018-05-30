package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Inheritance(strategy = InheritanceType.JOINED)
public class Course implements Comparable<Course> {

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Field(termVector = TermVector.YES)
    private String name;
    @Field
    @Lob
    @Column(length = 1000)
    private String description;
    private double price;
    private double rating;


    @ManyToOne
    private User publisher;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<ProgramCourse> programs = new HashSet<>();


    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<UserCourse> enrolledStudents = new HashSet<>();

    @OneToMany(mappedBy = "reviewed")
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();


    public Course(){}

    protected Course(String name, String description, double price, User publisher) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public double getRating() { return rating;}

    public User getPublisher() {
        return publisher;
    }

    public Set<UserCourse> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<UserCourse> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public int compareTo(@NotNull Course o) {
        return Double.compare(this.getRating(), o.getRating());
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<ProgramCourse> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<ProgramCourse> programs) {
        this.programs = programs;
    }

    public void removeReview(Review review) {
        Review selectedReview = null;
        for (Review aReview : reviews) {
            if (aReview.getId() == review.getId())
                selectedReview = aReview;
        }

        if(selectedReview != null){
            int l = reviews.size();
            if(l <=1){
                this.rating = 0;
                this.reviews.remove(selectedReview);
            }
            else {
                this.rating = ((this.rating * l) - selectedReview.getRating()) / (l - 1);
                this.reviews.remove(selectedReview);
            }
        }
    }

    public void addReview(Review review) {
        reviews.add(review);
        int r = reviews.size();
        rating = (rating * (r-1) + review.getRating())/(double)r;
    }

}
