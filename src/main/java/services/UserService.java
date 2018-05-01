package services;

import model.LocalCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserService {

    //TODO consultar el correcto manejo de las connections y las sessions & transactions

    private Session session;

    public UserService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    public User getUser(int id){
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        transaction.commit();
        return user;

    }

    public User registerUser(User user){
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        return user; // Returns user but with ID set
    }

    public User updateUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        return user;
    }

    public boolean enrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        LocalCourse course = session.get(LocalCourse.class,courseId);
        User user = session.get(User.class,userId);

        user.getEnrolled().add(course);
        course.getEnrolledStudents().add(user);

        session.persist(user);
        transaction.commit();
        return true;
    }

    public boolean unenrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        LocalCourse course = session.get(LocalCourse.class,courseId);
        User user = session.get(User.class,userId);

        user.getEnrolled().remove(course);
        course.getEnrolledStudents().remove(user);

        session.persist(user);
        transaction.commit();
        return true;

    }

    public void delete(int id) {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class,id));
        transaction.commit();
    }
}
