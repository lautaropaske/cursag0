package model;

import javax.persistence.*;

@Entity
public class Unit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Course parent;
    private String videoLink;
    private int number;
    private String textContent;

    public Unit(){}

    public Unit(Course parent){
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public Course getParent() {
        return parent;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
