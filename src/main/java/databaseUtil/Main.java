package databaseUtil;

import model.Course;
import model.LocalCourse;
import model.Unit;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.CourseService;
import services.UserService;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        CourseService courseService = new CourseService();
        User publisher = userService.getUser(1);

        Course course = courseService.getCourse(17);

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        Unit unit2 = new Unit(course, "youtubee",2,"Implementation");

        //Deje este main solo para simplificar el proceso de llenar la base
//        User user = new User("paul@hotmail.com", "1234","Paul","Perez");
//        SessionFactory sf = new Configuration().configure().buildSessionFactory();
//        Session session = sf.openSession();
//        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(unit2);
        transaction.commit();
        session.close();
        System.exit(0);

//        Transaction transaction = session.beginTransaction();
//        User user = session.get(User.class,1);
//        transaction.commit();
//        System.out.println(user.getMail());
    }
}
