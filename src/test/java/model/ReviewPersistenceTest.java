package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewPersistenceTest {

    @Test
    public void createAndDeleteTest() {

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.find(User.class, 1);
        String textContent = "Good course 11321";
        Review review = new Review(textContent,4,user, new Course());

        int id = (int) session.save(review);
        transaction.commit();

        assertEquals(textContent,review.getTextContent());

        Transaction newTransaction = session.beginTransaction();
        session.delete(review);
        newTransaction.commit();

        session.close();
    }

}