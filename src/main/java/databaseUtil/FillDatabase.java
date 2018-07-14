package databaseUtil;

import model.*;
import services.*;

import java.net.MalformedURLException;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class FillDatabase {

    public static void main(String[] args) throws MalformedURLException {
        UserService userService = new UserService();
        CourseService courseService = new CourseService();
        UnitService unitService = new UnitService();
        ReviewService reviewService = new ReviewService();
        ProgramService programService = new ProgramService();
        PaymentService paymentService = new PaymentService();

        User admin = userService.saveUser(new User("admin@cursago.com", "admin", "admin", "",true));
        User apu = userService.saveUser(new User("bettatiagustin@gmail.com", "1234", "Agustin", "Bettati",false));
        User paske = userService.saveUser(new User("lautaro.paskevicius@ing.austral.edu.ar", "1234", "Lautaro", "Paskevicius",false));
        User gonza = userService.saveUser(new User("gonza@gmail.com", "1234", "Gonzalo", "Perez",false));
        User juan = userService.saveUser(new User("juan@gmail.com", "1234", "Juan", "Perez",false));

        Course jUnit = courseService.registerCourse(new LocalCourse("Learn Java Unit Testing with JUnit 5", "JUnit is most popular Java Unit Testing Framework. The new version of JUnit - Junit 5 or Jupiter is even more special. \nIn this course, we look into the important features of JUnit 5. We understand the need for unit testing and learn how to write great unit tests with JUnit 5.\n"
                , 11, apu));
        Unit unit1 = unitService.createUnit(new Unit(jUnit,"Introduction of JUnit", "https://www.youtube.com/watch?v=KBx1pWhNUMc&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXAF",1,"In this Video we Create project set up and write our class which contain a Simple method that can compare two string and return boolean value."));
        Unit unit2 = unitService.createUnit(new Unit(jUnit,"Use of @Test and assertTrue()", "https://www.youtube.com/watch?v=FZVjICj_1vE&index=2&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA",2,"In this video we write our first Test case by using @Test annotation. In our Test method we use assertTrue and assertFalse methods."));
        Unit unit3 = unitService.createUnit(new Unit(jUnit,"How to use assertEquals in JUnit", "https://www.youtube.com/watch?v=6RcjjpgOj5c&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA&index=3",3,"In this video we create a method that can concatenate two strings and write JUnit Test case for this using assertEquals method."));
        Unit unit4 = unitService.createUnit(new Unit(jUnit,"How to test arrays in JUnit", "https://www.youtube.com/watch?v=skF3VZbfVqc&list=PL_WCPOWW_gJEJkasFUTZEHSMFKi76_VXA&index=4",4,"In this video we will see how to write JUnit Test case for array by using assertArrayEquals method."));

        reviewService.registerReview(new Review("Very intresting and enjoyful course",4,paske,jUnit));
        reviewService.registerReview(new Review("Clear, concise, and informative",5,gonza,jUnit));

        courseService.enrollInCourse(paske.getId(), jUnit.getId());
        courseService.enrollInCourse(gonza.getId(), jUnit.getId());

        paymentService.savePayment(paske.getId(), jUnit.getId(), 11);
        paymentService.savePayment(gonza.getId(), jUnit.getId(), 11);


        Course introUnitTesting = courseService.registerCourse(new ExtCourse("Foundations of Unit Testing", "Testing and debugging are places where developers spend a lot of their time. Code doesn’t always perform as expected and, with complex applications, it’s very easy to break code with minor changes. Unit tests and test-driven-development exist to help us manage expectations of our functionality and to ensure that other developers working on our code are aware when a change they made affects already existing functionality.",
                11.99, gonza, "https://www.udemy.com/refactoru-intro-unit-test/", "Udemy"));

        Program testingProgram = programService.registerProgram(new Program("Unit testing course oriented to java", "Building unit tests from the start, and running them often during development whenever changes to the component, dependent code, or toolchain changes occur can go a long way to helping ensure the quality of the software that uses them, and help to identify problems before they reach QA or end users.", admin));
        programService.addCourseToProgram(testingProgram.getId(), introUnitTesting.getId());
        programService.addCourseToProgram(testingProgram.getId(), jUnit.getId());


        Course html = courseService.registerCourse(new LocalCourse("30 Days to Learn HTML and CSS","Even if your goal is not to become a web designer, learning HTML and CSS can be an amazing tool to have in your skill-set -- both in the workplace, and at home. If this has been on your to-do list for some time, why don't you take thirty days and join me? Give me around ten minutes every day, and I'll teach you the essentials of HTML and CSS.", 9.99, gonza));
        Unit html1 = unitService.createUnit(new Unit(html,"Your First Webpage", "https://www.youtube.com/watch?v=bfqBUDk99Tc&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F&index=2",1,"In this lesson, you'll learn how to make a working web page that displays the words, Hello world."));
        Unit html2 = unitService.createUnit(new Unit(html,"Finding a Proper Code Editor", "https://www.youtube.com/watch?v=-8IoQTg5Ybs&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F&index=3",2,"Before we continue on with formatting our text, I don't want you to be using NotePad or Text Edit. Instead, we should be using a code editor that was specifically created for the purposes of writing code."));
        Unit html3 = unitService.createUnit(new Unit(html,"Lists", "https://www.youtube.com/watch?v=KHT6scxGm38&index=4&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F",3,"Welcome to Day 3. Today we're going to cover how to create a list of items using HTML."));
        Unit html4 = unitService.createUnit(new Unit(html,"Parent-Child Relationships", "https://www.youtube.com/watch?v=Pf8xmAZYZC4&index=5&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F",4,"While we touched on parent-child relationships in the previous episode, we need to focus on it a little more specifically today."));
        Unit html5 = unitService.createUnit(new Unit(html,"Heading Tags", "https://www.youtube.com/watch?v=1JFeWDZ-2D8&index=6&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F",5,"Up until now we've only briefly touched on the idea of headings. Let's dig a bit deeper and learn about all of the headings that are available to us."));
        Unit html6 = unitService.createUnit(new Unit(html,"Blockquotes", "https://www.youtube.com/watch?v=P7KU6mqdmJ0&list=PLgGbWId6zgaWZkPFI4Sc9QXDmmOWa1v5F&index=7",6,"Sometimes we need to quote somebody, and we can do that within HTML by using what's known as the blockquote element."));

        reviewService.registerReview(new Review("Good content, but voice is pretty dull",3,paske,html));
        reviewService.registerReview(new Review("Found the course very useful",5,apu,html));
        reviewService.registerReview(new Review("Quality of videos is not up to standard",2,juan,html));


        Course javascript = courseService.registerCourse(new ExtCourse("JavaScript, jQuery, and JSON", "In this course, we'll look at the JavaScript language, and how it supports the Object-Oriented pattern, with a focus on the unique aspect of how JavaScript approaches OO. We'll explore a brief introduction to the jQuery library, which is widely used to do in-browser manipulation of the Document Object Model (DOM) and event handling. ", 0, gonza, "https://www.coursera.org/learn/javascript-jquery-json", "Coursera"));
        Course angular = courseService.registerCourse(new ExtCourse("Angular 4 In 60 Minutes", "In this Angular 4 crash course we will be diving into the latest version of the Angular framework and look at all the fundamentals including Angular CLI, components, services, types, directives, events, HTTP, routing and more", 0, paske, "https://www.youtube.com/watch?v=KhzGSHNhnbI", "Udemy"));
        Course nodejs = courseService.registerCourse(new ExtCourse("The Complete Node.js Developer Course ", "Learn Node.js by building real-world applications with Node, Express, MongoDB, Mocha, and more!", 11.99, apu, "https://www.udemy.com/the-complete-nodejs-developer-course-2/?utm_content=_._ag_course_._ad_267316925735_._de_c_._dm__._lo_20009_._&utm_medium=udemyads&utm_source=adwords-row&utm_term=_._pl__._pd__._ti_kwd-314498519511_._kw_nodejs%20course_._&matchtype=e&utm_campaign=NEW-AW-PROS-ROW-TECH-Dev-Node-Js-EN-BBB_._ci_922484_._sl_ENG_._vi_TECH_._sd__._la_EN_._&k_clickid=724b426f-2f75-44df-a9b7-9d73eaedbf29_426_GOOGLE_NEW-AW-PROS-ROW-TECH-Dev-Node-Js-EN-BBB_._ci_922484_._sl_ENG_._vi_TECH_._sd__._la_EN_.__course_nodejs%20course_e_267316925735_c&gclid=Cj0KCQjw3InYBRCLARIsAG6bfMRxrScEyYETMI0xZ-JQe9v3mG8VaOmeLG9hJaaHF-289yRRj5Sj6X0aAgWjEALw_wcB", "Udemy"));

        reviewService.registerReview(new Review("Had a great time learing angular with this course",4,paske,angular));
        reviewService.registerReview(new Review("Awsome course, and loved learning node!",5,apu,nodejs));
        reviewService.registerReview(new Review("Had a hard time following the course due to not knowing javascript",3,juan,nodejs));

        Program fullstackProgram = programService.registerProgram(new Program("Fullstack javascript developer", "A Full-Stack Web Developer is someone who is able to work on both the front-end and back-end portions of an application. Front-end generally refers to the portion of an application the user will see or interact with, and the back-end is the part of the application that handles the logic, database interactions, user authentication, server configuration, etc.", admin));

        programService.addCourseToProgram(fullstackProgram.getId(), html.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), javascript.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), angular.getId());
        programService.addCourseToProgram(fullstackProgram.getId(), nodejs.getId());

        Program blockchain = programService.registerProgram(new Program("Fundamentals of Blockchain", "The blockchain is an undeniably ingenious invention – the brainchild of a person or group of people known by the pseudonym,  Satoshi Nakamoto. But since then, it has evolved into something greater, and the main question every single person is asking is: What is Blockchain? By allowing digital information to be distributed but not copied, blockchain technology created the backbone of a new type of internet. ", admin));

        // hago enroll a user en programs y courses
        programService.enrollInProgram(apu.getId(), fullstackProgram.getId());
        programService.enrollInProgram(apu.getId(), testingProgram.getId());

        programService.enrollInProgram(gonza.getId(), fullstackProgram.getId());

        courseService.enrollInCourse(apu.getId(), angular.getId());
        courseService.enrollInCourse(apu.getId(), javascript.getId());


        System.exit(0);
    }
}
