package services;

import model.Course;
import model.ExtCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SearchServiceTest {

    @Test
    public void searchCourses() {
        String requestToken = "java";

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        User author = session.get(User.class,1);

        Course course = new ExtCourse("Java", "java Michael Javson jab jab java jabbers javajavadoo", 1,author,"link","source");

        session.save(author);
        session.save(course);
        transaction.commit();

        SearchService ss = new SearchService();

        List result = ss.searchCourses(requestToken);

        try {
            assertTrue(result.contains(course));
        } finally {
            Transaction newTransaction = session.beginTransaction();
            session.delete(course);
            newTransaction.commit();
            session.close();
        }
    }
}