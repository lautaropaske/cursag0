package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Unit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Course parent;
    private String name;
    private String videoLink;
    private int number;
    private String textContent;

    public Unit(){}

    public Unit(Course parent,String name, String videoLink, int number, String textContent) {
        this.parent = parent;
        this.name = name;
        this.videoLink = videoLink;
        this.number = number;
        this.textContent = textContent;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @JsonIgnore
    public Course getParent() {
        return parent;
    }

    @JsonProperty
    public void setParent(Course parent) {
        this.parent = parent;
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
