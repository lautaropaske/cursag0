package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    //TODO agregar atributo contraseña, y aclarar que mail sea unico
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String mail;
    private String name;
    private String surname;

    public User(){}

    public User(String mail, String name, String surname){
        this.mail = mail;
        this.name = name;
        this.surname = surname;
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
