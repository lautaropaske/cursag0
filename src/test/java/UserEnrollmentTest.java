import model.LocalCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class UserEnrollmentTest {

    @Test
    public void enrollUserInCourseTest() {

        final int idOfUser = 14;
        final int idOfLocalCourse = 17;

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        LocalCourse course = session.get(LocalCourse.class,idOfLocalCourse);
        User user = session.get(User.class,idOfUser);

        user.getEnrolled().add(course);
        course.getEnrolledStudents().add(user);

        session.persist(user);

        transaction.commit();

        session.close();
        System.exit(0);
    }
}
