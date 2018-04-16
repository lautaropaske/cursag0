import model.Course;
import model.ExtCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoursePersistenceTest {

    @Test
    public void createAndDeleteTest() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();

        User publisher1 = new User("elpelapapa@live.es", "tonterias1", "Carlin", "Calvo");

        Transaction transaction1 = session.beginTransaction();
        session.save(publisher1); // To be part of ExtCourse, publisher must be a transient entity (SEE HIBERNATE STATE CHANGES @ javabrains)
        transaction1.commit();

        Course course1 = new ExtCourse("Unit Testing", "Learn JUnit", 0,5, publisher1,"URL", "Youtube");

        Transaction transaction2 = session.beginTransaction();
        int id = (int) session.save(course1);
        transaction2.commit();

        Course createdCourse = session.find(Course.class, id);

        assertEquals("Unit Testing",createdCourse.getName());

        Transaction newTransaction = session.beginTransaction();
        session.delete(createdCourse);
        newTransaction.commit();

        User publisher2 = new User("jorge@gmail.com", "qwerty", "Jorge", "Lin");

        Transaction transaction3 = session.beginTransaction();
        session.save(publisher2); // To be part of ExtCourse, publisher must be a transient entity (SEE HIBERNATE STATE CHANGES @ javabrains)
        transaction3.commit();

        Course course2 = new ExtCourse("Java basics", "Learn the basics", 0,4, publisher2,"URL", "Cousera");

        Transaction transaction4 = session.beginTransaction();
        session.save(course2);
        transaction4.commit();

        session.close();

    }
}
