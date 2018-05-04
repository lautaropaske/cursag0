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
public class EnrollmentProgressTest {

    @Test
    public void getProgressOfEnrollmentTest() {

        final int idOfUser = 14;
        final int idOfLocalCourse = 17;

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        LocalCourse course = session.get(LocalCourse.class,idOfLocalCourse);
        User user = session.get(User.class,idOfUser);

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(14,17);
        UserCourse uc = session.get(UserCourse.class, id);
        assertEquals(0, uc.getProgress());

        session.close();
        System.exit(0);
    }
}
