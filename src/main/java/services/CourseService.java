package services;

import model.*;
import javafx.beans.binding.BooleanBinding;
import model.Course;
import model.User;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;

import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;


import java.util.*;

public class CourseService {

    private SessionFactory sf;
    public enum MatchIn { DESCRIPTION, NAME};

    public CourseService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public Course getCourse(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,id);
        transaction.commit();
        session.close();
        return course;
    }

    public Set<Course> getCoursesEnrolledBy(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);

        final Set<Course> enrolled = new HashSet<>();
        final Set<UserCourse> relations = user.getEnrolledCourses();
        relations.forEach(relation -> enrolled.add(relation.getCourse()));

        transaction.commit();
        session.close();
        return enrolled;
    }

    public Collection<Course> getCoursesPublishedBy(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Collection<Course> published = user.getPublished();
        transaction.commit();
        session.close();
        return published;
    }

    public int enrollmentStatus(int userId, int courseId) {
        Session session  = sf.openSession();
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
        session.close();
        return response;
    }

    public boolean enrollInCourse(int userId, int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,courseId);
        User user = session.get(User.class,userId);

        UserCourse uc = new UserCourse(user, course, 0);
        session.persist(uc);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean unenrollInCourse(int userId, int courseId) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        session.delete(uc);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean makeProgess(int userId, int courseId) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(uc.getProgress() + 1);
        session.persist(uc);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean goBack(int userId, int courseId) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(uc.getProgress() - 1);
        session.persist(uc);
        transaction.commit();
        session.close();
        return true;

    }

    public boolean finished(int userId, int courseId) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();

        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(-2);
        session.persist(uc);
        transaction.commit();
        session.close();
        return true;

    }

    public Course registerCourse(Course course) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        session.close();
        return course;
    }

    public Course updateCourse(Course course) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.update(course);
        transaction.commit();
        session.close();
        return course;
    }

    @SuppressWarnings("unchecked")
    public List<Course> getCourses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        List<Course> courses = session.createQuery("FROM Course").list();
        transaction.commit();
        session.close();
        return courses;
    }

    public void deleteCourse(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class,id);

        final Set<ProgramCourse> programCourses = course.getPrograms();
        programCourses.forEach(programCourse -> {
            session.delete(programCourse);
        });
        //TODO borrar comentarios y la relacion de usuarios que estan inscriptos
        session.delete(course);
        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    public List<Course> getSamplePopularCouses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Course ORDER BY rating DESC");
        query.setMaxResults(4);
        List<Course> result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    //TODO must have COURSES with ID 12,14,15 in db / NEGREADA MAL CORREGIR

    public List<Course> getCarouselCourses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        List<Course> result = new ArrayList<>();
        result.add(session.get(Course.class,12));
        result.add(session.get(Course.class,14));
        result.add(session.get(Course.class,15));
        transaction.commit();
        session.close();
        return result;
    }

    public List<Course> searchCourses(String token) {
        List<Course> result = new ArrayList<>();
        Set<Course> byDescription = searchCourses(token, CourseService.MatchIn.DESCRIPTION);
        Set<Course> byName = searchCourses(token, CourseService.MatchIn.NAME);
        result.addAll(byDescription);
        result.addAll(byName);
        result.sort(Course::compareTo);
        return result;
    }

    @SuppressWarnings("unchecked")
    private Set<Course> searchCourses(String token, MatchIn column) {

        // Prepare and clean token, leaving only key words

        String[] keyWords = token.split(" ");

        // Build query and ask database to retrieve relevant courses.

        String colName = "";

        if(column.equals(MatchIn.DESCRIPTION)) {
            colName = "description";
        } else if(column.equals(MatchIn.NAME)) {
            colName = "name";
        }

        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Course> q = cb.createQuery(Course.class);

        Root<Course> c = q.from(Course.class);
        q = q.select(c).where(cb.like(c.get(colName), cb.parameter(String.class, "t")));

        Set<Course> result = new HashSet<>();

        for(String key : keyWords) {
            Query finalQuery = session.createQuery(q);
            finalQuery.setParameter("t", "%"+key+"%");
            List queried = finalQuery.getResultList(); // Not finding results
            result.addAll(queried);
        }

        transaction.commit();
        session.close();
        return result;
    }
}