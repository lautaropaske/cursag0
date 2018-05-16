package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    //TODO Estudiar fetch eager
    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "publisher", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Collection<Course> published;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserCourse> enrolledCourses = new HashSet<>();
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private Set<Review> madeReviews = new HashSet<>();
    private boolean isAdmin = false; // set only in db manager (from IDEA UI or HSQLDB GUI)

    public User(){}

    public User(String mail, String password, String name, String surname){
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isAdmin = false;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setPublished(Collection<Course> published) {
        this.published = published;
    }

    public void setEnrolledCourses(Set<UserCourse> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

}
