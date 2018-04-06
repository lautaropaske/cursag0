package model;

import javax.persistence.Entity;

@Entity
public class ExtCourse extends Course {
    private String link;
    private String source;

    public ExtCourse(String name, String description, double price, double rating, User publisher, String link, String source) {
        super(name,description,price,rating,publisher);
        this.link = link;
        this.source = source;
    }

    public ExtCourse() {
    }


    public String getLink() {
        return link;
    }

    public String getSource() {
        return source;
    }
}
