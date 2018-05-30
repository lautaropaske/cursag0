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


        Course html = courseService.registerCourse(new ExtCourse("The Complete HTML & CSS Course", "In this Udemy Course Students will be able to learn a Complete HTML & CSS Course taking them From Novice To Professional",
                5, diego, "https://www.udemy.com/htmlwebsite/", "Udemy"));
        Course javascript = courseService.registerCourse(new ExtCourse("JavaScript, jQuery, and JSON", "In this course, we'll look at the JavaScript language, and how it supports the Object-Oriented pattern, with a focus on the unique aspect of how JavaScript approaches OO. We'll explore a brief introduction to the jQuery library, which is widely used to do in-browser manipulation of the Document Object Model (DOM) and event handling. ",
                0, santi, "https://www.coursera.org/learn/javascript-jquery-json", "Coursera"));
        Course angular = courseService.registerCourse(new ExtCourse("Angular 4 In 60 Minutes", "In this Angular 4 crash course we will be diving into the latest version of the Angular framework and look at all the fundamentals including Angular CLI, components, services, types, directives, events, HTTP, routing and more",
                0, lucas, "https://www.youtube.com/watch?v=KhzGSHNhnbI", "Udemy"));
        Course nodejs = courseService.registerCourse(new ExtCourse("The Complete Node.js Developer Course ", "Learn Node.js by building real-world applications with Node, Express, MongoDB, Mocha, and more!",
                11.99, diego, "https://www.udemy.com/the-complete-nodejs-developer-course-2/?utm_content=_._ag_course_._ad_267316925735_._de_c_._dm__._lo_20009_._&utm_medium=udemyads&utm_source=adwords-row&utm_term=_._pl__._pd__._ti_kwd-314498519511_._kw_nodejs%20course_._&matchtype=e&utm_campaign=NEW-AW-PROS-ROW-TECH-Dev-Node-Js-EN-BBB_._ci_922484_._sl_ENG_._vi_TECH_._sd__._la_EN_._&k_clickid=724b426f-2f75-44df-a9b7-9d73eaedbf29_426_GOOGLE_NEW-AW-PROS-ROW-TECH-Dev-Node-Js-EN-BBB_._ci_922484_._sl_ENG_._vi_TECH_._sd__._la_EN_.__course_nodejs%20course_e_267316925735_c&gclid=Cj0KCQjw3InYBRCLARIsAG6bfMRxrScEyYETMI0xZ-JQe9v3mG8VaOmeLG9hJaaHF-289yRRj5Sj6X0aAgWjEALw_wcB", "Udemy"));

        Program fullstackProgram = programService.registerProgram(new Program("Fullstack javascript developer", "A Full-Stack Web Developer is someone who is able to work on both the front-end and back-end portions of an application. Front-end generally refers to the portion of an application the user will see or interact with, and the back-end is the part of the application that handles the logic, database interactions, user authentication, server configuration, etc.", admin));
        programService.addCourseToProgram(fullstackProgram.getId(), html.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), javascript.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), angular.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), nodejs.getId());

        System.exit(0);
    }
}
