package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String mail;

    private String password;

    private String name;

    private String surname;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE) //cascade: 'If ONE is removed, remove MANY' (If a user is deleted, delete his courses'
    @JsonIgnore
    private Collection<Course> published;

//    @ManyToMany - Test later
//    @JoinTable(name = "enrolled_users")
//    private Collection<Course> enrolled;

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

//    public Collection<Course> getEnrolled() { - Test later
//        return enrolled;
//    }
}
