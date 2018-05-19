package databaseUtil;

import model.*;
import services.*;

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
        ProgramService programService = new ProgramService();

        User admin = userService.registerUser(new User("admin@cursago.com", "admin", "admin", "",true));
        User diego = userService.registerUser(new User("diegolarralde@gmail.com", "1234", "Diego", "Larralde",false));
        User lucas = userService.registerUser(new User("lucasluppani@gmail.com", "1234", "Lucas", "Luppani",false));
        User santi = userService.registerUser(new User("santiagofuentes@gmail.com", "1234", "Santiago", "Fuentes",false));

        Course jUnit = courseService.registerCourse(new LocalCourse("Learn Java Unit Testing with JUnit 5", "JUnit is most popular Java Unit Testing Framework. The new version of JUnit - Junit 5 or Jupiter is even more special. \nIn this course, we look into the important features of JUnit 5. We understand the need for unit testing and learn how to write great unit tests with JUnit 5.\n"
                , 0, diego));
        Unit unit1 = unitService.createUnit(new Unit(jUnit,"Introduction of JUnit", "https://www.youtube.com/watch?v=KBx1pWhNUMc&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXAF",1,"In this Video we Create project set up and write our class which contain a Simple method that can compare two string and return boolean value."));
        Unit unit2 = unitService.createUnit(new Unit(jUnit,"Use of @Test and assertTrue()", "https://www.youtube.com/watch?v=FZVjICj_1vE&index=2&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA",2,"In this video we write our first Test case by using @Test annotation. In our Test method we use assertTrue and assertFalse methods."));
        Unit unit3 = unitService.createUnit(new Unit(jUnit,"How to use assertEquals in JUnit", "https://www.youtube.com/watch?v=6RcjjpgOj5c&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA&index=3",3,"In this video we create a method that can concatenate two strings and write JUnit Test case for this using assertEquals method."));
        Unit unit4 = unitService.createUnit(new Unit(jUnit,"How to test arrays in JUnit", "https://www.youtube.com/watch?v=skF3VZbfVqc&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA&index=4",4,"In this video we will see how to write JUnit Test case for array by using assertArrayEquals method."));

        reviewService.registerReview(new Review("Very intresting and enjoyful course",4,lucas,jUnit));
        reviewService.registerReview(new Review("Clear, concise, and informative",5,santi,jUnit));


        Course introUnitTesting = courseService.registerCourse(new ExtCourse("Introduction to Unit Testing", "Testing and debugging are places where developers spend a lot of their time. Code doesn’t always perform as expected and, with complex applications, it’s very easy to break code with minor changes. Unit tests and test-driven-development exist to help us manage expectations of our functionality and to ensure that other developers working on our code are aware when a change they made affects already existing functionality.",
                11.99, santi, "https://www.udemy.com/refactoru-intro-unit-test/", "Udemy"));


        Program testingProgram = programService.registerProgram(new Program("Unit testing course oriented to java", "Building unit tests from the start, and running them often during development whenever changes to the component, dependent code, or toolchain changes occur can go a long way to helping ensure the quality of the software that uses them, and help to identify problems before they reach QA or end users.", admin));
        programService.addCourseToProgram(testingProgram.getId(), introUnitTesting.getId());
        programService.addCourseToProgram(testingProgram.getId(), jUnit.getId());

    }
}
