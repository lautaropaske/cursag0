package model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String textContent;

    private int rating;

    @ManyToOne
    private User publisher;

    public Review() {}

    public Review(String textContent, int rating, User publisher){
        this.textContent = textContent;
        this.rating = rating;
        this.publisher = publisher;
    }

    public String getTextContent() {
        return textContent;
    }

    public double getRating() {
        return rating;
    }

    public User getPublisher() {
        return publisher;
    }
}
