package services;

import model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private Session session;

    public SearchService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    /*
        Helpers:
            -   https://stackoverflow.com/questions/14290857/sql-select-where-field-contains-words
     */
    @SuppressWarnings("unchecked")
    public List<Course> searchCourses(String token) {

        // Prepare and clean token, leaving only key words

        String[] keyWords = token.split(" ");

        // Build query and ask database to retrieve relevant courses. Built as XOR.
        // TODO Query that prioritizes most successful matches (n matches first, n-1 matches second ...)

        StringBuilder sb = new StringBuilder("FROM Course WHERE Course.description ");

        int l = keyWords.length-1;

        for (int i = 0; i < l; i++) {
            sb.append("LIKE \'");
            sb.append("%");
            sb.append(keyWords[i]);
            sb.append("%");
            sb.append("\' OR WHERE Course.description "); // How is multiple WHERE search in HQL? Is OR WHEERe
        }

        sb.append("LIKE \'%");
        sb.append(keyWords[l]);
        sb.append("%\'");

        Query query = session.createQuery(sb.toString());

        return query.list();
    }
}