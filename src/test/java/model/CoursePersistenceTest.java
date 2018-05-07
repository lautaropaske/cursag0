package model;

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

        User publisher1 = session.get(User.class,1);

        Course course1 = new ExtCourse("Unit Testing", "Learn JUnit", 0, publisher1,"URL", "Youtube");

        session.beginTransaction();
        int id = (int) session.save(course1);
        session.getTransaction().commit();

        Course createdCourse = session.find(Course.class, id);
        assertEquals("Unit Testing",createdCourse.getName());

        session.beginTransaction();
        session.delete(createdCourse);
        session.getTransaction().commit();

        session.close();
    }
}
