package databaseUtil;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.UserService;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        LocalCourse course = session.get(LocalCourse.class,17);
        User user = session.get(User.class,14);

//        user.getEnrolledCourses().add(course);
//        course.getEnrolledStudents().add(user);
//
//        session.persist(user);

        transaction.commit();

        session.close();
        System.exit(0);
    }
}
