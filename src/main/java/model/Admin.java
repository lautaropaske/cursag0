package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @NaturalId
    private String mail;
    @JsonIgnore
    private String password;
    private String username;
    @OneToMany
    @JsonIgnore
    private Collection<Program> published;

    public Admin(){}

    public Admin(String mail, String password, String username){
        this.mail = mail;
        this.password = password;
        this.username = username;
    }

    public int getId(){return id;}

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}