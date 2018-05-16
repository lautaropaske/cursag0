package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String textContent;
    @Column(nullable = false)
    private int rating;
    @ManyToOne
    private User publisher;
    @ManyToOne
    private Course reviewed;

    public Review() {}

    public Review(String textContent, int rating, User publisher){
        this.textContent = textContent;
        this.rating = rating;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public String getTextContent() {
        return textContent;
    }

    public int getRating() {
        return rating;
    }

    public User getPublisher() {
        return publisher;
    }

    @JsonIgnore
    public Course getReviewed() {
        return reviewed;
    }

    @JsonProperty
    public void setReviewed(Course reviewed) {
        this.reviewed = reviewed;
    }
}
