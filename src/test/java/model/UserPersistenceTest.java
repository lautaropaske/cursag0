package model;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class UserPersistenceTest {

    @Test
    public void createAndDeleteTest() {

        User user = new User("jorge@hotmail.com","1234","jorge","lin",false);

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(user);
        transaction.commit();

        User createdUser = session.find(User.class, id);

        assertEquals("jorge@hotmail.com",createdUser.getMail());

        Transaction newTransaction = session.beginTransaction();
        session.delete(createdUser);
        newTransaction.commit();

        session.close();

    }

}