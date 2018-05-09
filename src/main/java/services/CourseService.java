package services;

import model.Course;
import model.User;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.*;

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

        final Set<Course> enrolled = new HashSet<>();
        final Set<UserCourse> relations = user.getEnrolledCourses();
        relations.forEach(relation -> enrolled.add(relation.getCourse()));

        return enrolled;
    }

    public Collection<Course> getCoursesPublishedBy(int id) {
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Collection<Course> published = user.getPublished();
        transaction.commit();
        return published;
    }

    public int enrollmentStatus(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);

        UserCourse uc = session.get(UserCourse.class,id);

        int response;
        if(uc == null){
            response = -1;
        }
        else{
            response = uc.getProgress();
        }

        transaction.commit();
        return response;
    }

    public boolean enrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,courseId);
        User user = session.get(User.class,userId);

        UserCourse uc = new UserCourse(user, course, 0);
        session.persist(uc);
        transaction.commit();
        return true;
    }

    public boolean unenrollInCourse(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        session.delete(uc);
        transaction.commit();
        return true;
    }

    public boolean makeProgess(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(uc.getProgress() + 1);
        session.persist(uc);
        transaction.commit();
        return true;
    }

    public boolean goBack(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(uc.getProgress() - 1);
        session.persist(uc);
        transaction.commit();
        return true;

    }

    public boolean finished(int userId, int courseId) {
        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(-2);
        session.persist(uc);
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

    public List<Course> getSamplePopularCouses() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Course ORDER BY rating DESC");
        query.setMaxResults(4);
        List<Course> result = query.list();
        transaction.commit();
        return result;
    }

    //TODO must have COURSES with ID 20,22,24 in db

    public List<Course> getCarouselCourses() {
        Transaction transaction = session.beginTransaction();
        List<Course> result = new ArrayList<>();
        result.add(session.get(Course.class,20));
        result.add(session.get(Course.class,22));
        result.add(session.get(Course.class,24));
        transaction.commit();
        return result;
    }
}
