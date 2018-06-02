package services;

import model.Course;
import model.Program;
import model.ProgramCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.CoursesOfProgramUpdate;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Course> getCoursesOfProgram(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Program program = session.get(Program.class,id);

        final List<Course> courses = new ArrayList<>();
        final List<ProgramCourse> programCourses = new ArrayList<>(program.getCourses());
        programCourses.sort(Comparator.comparingInt(ProgramCourse::getPosition));
        programCourses.forEach(relation -> courses.add(relation.getCourse()));
        transaction.commit();
        session.close();
        return courses;

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
        Course course = session.get(Course.class,courseId);
        Program program = session.get(Program.class,programId);

        ProgramCourse pc = new ProgramCourse(program, course, program.getCourses().size()+1);
        session.persist(pc);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean removeCourseFromProgram(int programId, int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();

        ProgramCourse.ProgramCourseId id = new ProgramCourse.ProgramCourseId(programId, courseId);
        ProgramCourse pc = session.get(ProgramCourse.class,id);
        final Program program = pc.getProgram();
        final int position = pc.getPosition();

        program.getCourses().forEach(programCourse -> {
            if(programCourse.getPosition() > position){
                programCourse.setPosition(programCourse.getPosition() -1);
                session.persist(programCourse);
            }
        });
        session.delete(pc);
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
        final Program program = session.get(Program.class, id);
        program.getCourses().forEach(programCourse -> {
            session.delete(programCourse);
        });
        session.delete(program);
        transaction.commit();
        session.close();
    }

    public boolean updateCoursesOfProgram(CoursesOfProgramUpdate coursesUpdate) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        int programId = coursesUpdate.getId();
        final List<Course> courses = coursesUpdate.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            ProgramCourse.ProgramCourseId id = new ProgramCourse.ProgramCourseId(programId, courses.get(i).getId());
            ProgramCourse pc = session.get(ProgramCourse.class,id);
            pc.setPosition(i+1);
            session.persist(pc);
        }
        transaction.commit();
        session.close();
        return true;
    }

    public List<Program> getProgramsCourseNotPresent(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();

        List<Program> programs = session.createQuery("FROM Program").list();

        List<Program> result = programs.stream()
                .filter(program -> {
                    boolean value = true;
                    for (ProgramCourse programCourse : program.getCourses()) {
                        if(programCourse.getCourse().getId() == id){
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        transaction.commit();
        session.close();
        return result;
    }

    public List<Program> getCarouselCourses() {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        List<Program> result = new ArrayList<>();
        result.add(session.get(Program.class,264));
        result.add(session.get(Program.class,269));
        result.add(session.get(Program.class,270));
        transaction.commit();
        session.close();
        return result;
    }
}
