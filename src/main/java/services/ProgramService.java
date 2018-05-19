package services;

import model.Course;
import model.Program;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;

public class ProgramService {

    private SessionFactory sf;

    public ProgramService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public Program getProgram(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,id);
        transaction.commit();
        session.close();
        return program;
    }

    public boolean enrollInProgram(int userId, int programId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,programId);
        User user = session.get(User.class,userId);

        program.getEnrolledStudents().add(user);
        user.getEnrolledPrograms().add(program);

        session.persist(user);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean unenrollInProgram(int userId, int programId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,programId);
        User user = session.get(User.class,userId);

        program.getEnrolledStudents().remove(user);
        user.getEnrolledPrograms().remove(program);

        session.persist(user);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean addCourseToProgram(int programId, int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,programId);
        Course course = session.get(Course.class,courseId);

        program.getCourses().add(course);
        course.getPrograms().add(program);

        session.persist(program);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean removeCourseFromProgram(int programId, int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,programId);
        Course course = session.get(Course.class,courseId);

        program.getCourses().remove(course);
        course.getPrograms().remove(program);

        session.persist(program);
        transaction.commit();
        session.close();
        return true;
    }

    public Set<Program> getProgramsEnrolledBy(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Set<Program> enrolled = user.getEnrolledPrograms();
        transaction.commit();
        session.close();
        return enrolled;
    }

    public Collection<Program> getProgramsPublishedBy(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        final Collection<Program> published = user.getPublishedPrograms();
        transaction.commit();
        session.close();
        return published;
    }

    public Program registerProgram(Program program) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(program);
        transaction.commit();
        session.close();
        return program;
    }

    public Program updateProgram(Program program) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.update(program);
        transaction.commit();
        session.close();
        return program;
    }

    @SuppressWarnings("unchecked")
    public List<Program> getPrograms() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        List<Program> programs = session.createQuery("FROM Program").list();
        transaction.commit();
        session.close();
        return programs;
    }

    public void deleteProgram(int id) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Program.class,id));
        transaction.commit();
    }

}
