import model.LocalCourse;
import model.User;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        UserCourse uc = new UserCourse(user, course, 0);

        session.persist(uc);

        transaction.commit();

        session.close();
    }
}
