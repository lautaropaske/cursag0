package services;

import model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CourseService {

    private Session session;

    public CourseService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    public Course getCourse(int id) {
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,id);
        transaction.commit();
        return course;
    }

    public Course registerCourse(Course course) {
        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        return course;
    }

    public Course updateCourse(Course course) {
        Transaction transaction = session.beginTransaction();
        session.update(course);
        transaction.commit();
        return course;
    }

    public List<Course> getCourses() {
        Transaction transaction = session.beginTransaction();
        List<Course> courses = session.createQuery("FROM Course").list();
        transaction.commit();
        return courses;
    }

    //TODO verificar mejor práctica para hacer el delete. Tomamos el ID pq el component tira error al hacer this.http.delete(...,OBJECT)
    public void deleteCourse(int id) {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Course.class,id));
        transaction.commit();
    }
}
