package model;

import javax.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String mail;

    private String password;

    private String name;

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
