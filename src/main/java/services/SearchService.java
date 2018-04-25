package services;

import model.Course;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    public List<Course> searchCourses(String token) {

        String[] keyWords = token.split(" ");

        //TODO Query that prioritizes most successful matches (n matches first, n-1 matches second ...)

        return new ArrayList<>();
    }
}
// searchCourses("java course coursera")
// "java","course","coursera"
// SELECT * WHERE