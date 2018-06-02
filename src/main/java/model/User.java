package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @NaturalId
    private String mail;
    private String password;
    private String name;
    private String surname;
    
    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "publisher", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    private Collection<Course> published;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "publisher",cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    private Collection<Program> publishedPrograms;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserCourse> enrolledCourses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "User_Program",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "program_id") }
    )
    @JsonIgnore
    private Set<Program> enrolledPrograms = new HashSet<>();

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Review> madeReviews = new HashSet<>();

    private boolean isAdmin; // set only in db manager (from IDEA UI or HSQLDB GUI)

    public User(){}

    public User(String mail, String password, String name, String surname,Boolean isAdmin){
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public int getId(){return id;}

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Collection<Course> getPublished() {
        return published;
    }

    public void addPublished(Course course){
        published.add(course);
    }

    public Set<UserCourse> getEnrolledCourses() {
        return enrolledCourses;
    }

    @JsonProperty(value="isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setPublished(Collection<Course> published) {
        this.published = published;
    }

    public void setEnrolledCourses(Set<UserCourse> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Collection<Program> getPublishedPrograms() {
        return publishedPrograms;
    }

    public Set<Program> getEnrolledPrograms() {
        return enrolledPrograms;
    }

    public Set<Review> getMadeReviews() {
        return madeReviews;
    }
}
