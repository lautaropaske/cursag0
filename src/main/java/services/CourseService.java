package services;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;


import java.util.*;

public class CourseService {

    private SessionFactory sf;

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

    //TODO must have COURSES with ID 20,22,24 in db

    public List<Course> getCarouselCourses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        List<Course> result = new ArrayList<>();
        result.add(session.get(Course.class,20));
        result.add(session.get(Course.class,22));
        result.add(session.get(Course.class,24));
        transaction.commit();
        session.close();
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Course> searchCourses(String token) {
        Session session  = sf.openSession();
        FullTextSession fts = Search.getFullTextSession(session);

        try {
            fts.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Transaction tx = fts.beginTransaction();

        QueryBuilder queryBuilder = fts.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Course.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder/*.keyword().fuzzy().onFields("description","name").matching(token).createQuery();*/.simpleQueryString()
                .onField("description").andField("name")
                .matching(token)
                .createQuery();

        org.hibernate.query.Query hibQuery = fts.createFullTextQuery(query, Course.class);

        List result = hibQuery.getResultList();

        tx.commit();
        session.close();

        return result;
    }
}
