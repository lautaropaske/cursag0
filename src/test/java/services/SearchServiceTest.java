package services;

import model.Course;
import model.ExtCourse;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SearchServiceTest {

    @Test
    public void searchCourses() {
        UserService us = new UserService();
        User author = us.getUser(1);

        CourseService cs = new CourseService();
        Course course = new ExtCourse("Java", "java Michael Javson jab jab java jabbers javajavadoo", 1,author,"link","source");
        cs.registerCourse(course);

        String requestToken = "java";
        Set result = cs.searchCourses(requestToken);

        try {
            assertTrue(result.contains(course));
        } finally {
            cs.deleteCourse(course.getId());
        }
    }
}