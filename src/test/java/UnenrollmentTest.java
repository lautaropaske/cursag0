import model.LocalCourse;
import model.User;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class UnenrollmentTest {

    @Test
    public void enrollUserInCourseTest() {

        final int idOfUser = 14;
        final int idOfCourse = 17;

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();


        UserCourse.UserCourseId id = new UserCourse.UserCourseId(idOfUser, idOfCourse);

        UserCourse uc = session.get(UserCourse.class,id);

        session.delete(uc);
        transaction.commit();

        session.close();
    }
}
