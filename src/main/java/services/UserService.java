package services;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {

    //TODO consultar el correcto manejo de las connections y las sessions & transactions

    private Session session;

    public UserService(Session session){
        this.session = session;
    }

    public User getUser(long id){
//        Transaction transaction = session.beginTransaction();
//        User user = session.get(User.class,id);
//        transaction.commit();
        return new User("apu@hotmail.com","1234","Apu","Bettati");
    }

    public User registerUser(User user){
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        return user; // Returns user but with ID set
    }

    public User updateUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        return user;
    }
}
