package pojos;

import model.Course;

import java.util.List;
import java.util.Set;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class CoursesOfProgramUpdate {

    private int id;

    private List<Course> courses;

    public CoursesOfProgramUpdate() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
