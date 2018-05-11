package services;

import model.Course;
import model.LocalCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

public class UserService {

    //TODO consultar el correcto manejo de las connections y las sessions & transactions

    private SessionFactory sf;

    public UserService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public User getUser(int id){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        transaction.commit();
        session.close();
        return user;

    }

    public User registerUser(User user){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user; // Returns user but with ID set
    }

    public User updateUser(User user) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return user;
    }


    public void deleteUser(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class,id));
        transaction.commit();
        session.close();

    }

}
