package services;

import model.Course;
import model.LocalCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    public Set<Course> getCoursesEnrolledBy(int id) {
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Set<Course> enrolled = user.getEnrolled();
        transaction.commit();
        return enrolled;
    }


    public Collection<Course> getCoursesPublishedBy(int id) {
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Collection<Course> published = user.getPublished();
        transaction.commit();
        return published;
    }

    public boolean userIsEnrolled(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,courseId);
        User user = session.get(User.class,userId);

        boolean result;
        if(user.getEnrolled().contains(course)){
            result = true;
        }
        else{
            result = false;
        }

        session.persist(user);
        transaction.commit();
        return result;
    }

    public boolean enrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,courseId);
        User user = session.get(User.class,userId);

        user.getEnrolled().add(course);
        course.getEnrolledStudents().add(user);

        session.persist(user);
        transaction.commit();
        return true;
    }

    public boolean unenrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,courseId);
        User user = session.get(User.class,userId);

        user.getEnrolled().remove(course);
        course.getEnrolledStudents().remove(user);

        session.persist(user);
        transaction.commit();
        return true;

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
