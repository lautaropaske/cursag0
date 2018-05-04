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

    private String password;

    private String name;

    private String surname;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE) //cascade: 'If ONE is removed, remove MANY' (If a user is deleted, delete his courses'
    @JsonIgnore
    private Collection<Course> published;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "User_Course",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    @JsonIgnore
    private Set<Course> enrolled = new HashSet<>();

    public User(){}

    public User(String mail, String password, String name, String surname){
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
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

    public Set<Course> getEnrolled() {
        return enrolled;
    }

    public void setPublished(Collection<Course> published) {
        this.published = published;
    }

    public void setEnrolled(Set<Course> enrolled) {
        this.enrolled = enrolled;
    }


    public void enrollInCourse(LocalCourse course){
        enrolled.add(course);
    }


}
