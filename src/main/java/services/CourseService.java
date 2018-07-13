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
import java.util.stream.Collectors;

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
        if(!course.isDeleted()) {
            return course;
        }
        else {
            return null;
        }
    }

    public Set<Course> getCoursesEnrolledBy(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);

        final Set<Course> enrolled = new HashSet<>();
        final Set<UserCourse> relations = user.getEnrolledCourses();
        relations.forEach(relation -> {
            if(!relation.getCourse().isDeleted()) {
                enrolled.add(relation.getCourse());
            }
        });

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
        return published.stream().filter(course -> !course.isDeleted()).collect(Collectors.toList());

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

    public boolean reset(int userId, int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        UserCourse.UserCourseId id = new UserCourse.UserCourseId(userId, courseId);
        UserCourse uc = session.get(UserCourse.class,id);
        uc.setProgress(0);
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
        List<Course> courses = session.createQuery("FROM Course WHERE deleted = false").list();
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

        final Set<UserCourse> enrolledStudents = course.getEnrolledStudents();
        enrolledStudents.forEach(studentCourse ->{
            session.delete(studentCourse);
        });

        course.getReviews().forEach(review -> {
            session.delete(review);
        });

        course.deleteCourse();
        session.update(course);
        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    public List<Course> getSamplePopularCouses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Course WHERE deleted = false ORDER BY rating DESC");
        query.setMaxResults(4);
        List<Course> result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    @SuppressWarnings("unchecked")
    public Set<Course> searchCourses(String token) {

        // Prepare and clean token, leaving only key words
        String[] keyWords = token.split(" ");

        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Course> q = cb.createQuery(Course.class);

        Root<Course> c = q.from(Course.class);
        Predicate orClause =
                cb.or(cb.like(c.get("name"), cb.parameter(String.class, "t")),
                        cb.like(c.get("description"), cb.parameter(String.class, "t")));
        q = q.select(c).where(orClause);

        Set<Course> result = new HashSet<>();

        for(String key : keyWords) {
            Query finalQuery = session.createQuery(q);
            finalQuery.setParameter("t", "%"+key+"%");
            List<Course> queried = finalQuery.getResultList();
            result.addAll(queried.stream().filter(course -> !course.isDeleted()).collect(Collectors.toList()));
        }

        transaction.commit();
        session.close();
        return result;
    }
}