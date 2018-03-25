import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private int id;
    private String mail;
    private String name;
    private String surname;

    public User(){}

    public User(int id, String mail, String name, String surname){
        this.id = id;
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
