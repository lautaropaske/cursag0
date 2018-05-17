package databaseUtil;

import model.Course;
import model.LocalCourse;
import model.Unit;
import model.User;
import services.CourseService;
import services.ReviewService;
import services.UnitService;
import services.UserService;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class FillDatabase {

    public static void main(String[] args) {
        UserService userService = new UserService();
        CourseService courseService = new CourseService();
        UnitService unitService = new UnitService();
        ReviewService reviewService = new ReviewService();


        User user1 = userService.registerUser(new User("gonza@gmail.com", "1234", "Gonza", "Perez",false));

        Course course1 = courseService.registerCourse(new LocalCourse("JUnit 5", "lalalaalal", 0, user1));

        Unit unit1 = unitService.createUnit(new Unit(course1,"Intro a JUnit", "link",1,"info de la unidad"));

    }
}
