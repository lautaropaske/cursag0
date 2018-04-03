package model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(unique = true, name = "Mail")
    private String mail;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

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
}
