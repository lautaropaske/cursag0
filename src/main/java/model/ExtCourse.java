package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class ExtCourse extends Course {
    @Lob
    @Column(length = 1000)
    private String link;
    private String source;

    public ExtCourse(String name, String description, double price, User publisher, String link, String source) {
        super(name,description,price,publisher);
        this.link = link;
        this.source = source;
    }

    public ExtCourse() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }
}
