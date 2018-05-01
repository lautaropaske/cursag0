package services;

import model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class SearchService {

    private Session session;

    public enum MatchIn { DESCRIPTION, NAME};

    public SearchService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    /*
        Helpers:
            -   https://stackoverflow.com/questions/14290857/sql-select-where-field-contains-words
     */
    @SuppressWarnings("unchecked")
    public List<Course> searchCourses(String token, MatchIn column) {

        // Prepare and clean token, leaving only key words

        String[] keyWords = token.split(" ");

        // Build query and ask database to retrieve relevant courses.
        // TODO Query that prioritizes most successful matches (n matches first, n-1 matches second ...)

        StringBuilder sb = new StringBuilder("FROM Course WHERE ");
        String colName = "Course.";
        if(column.equals(MatchIn.DESCRIPTION))  colName += "description";
        else if(column.equals(MatchIn.NAME))  colName += "name";
        sb.append(colName);

        int i = 0;

        sb.append(" LIKE \'");
        sb.append("%");
        sb.append(keyWords[i]);
        sb.append("%\'");

        if(keyWords.length != 1){
            i++;

            for (; i < keyWords.length; i++) {
                sb.append(" OR " + colName +
                        " LIKE \'");
                sb.append("%");
                sb.append(keyWords[i]);
                sb.append("%\'");
            }
        }

        Query query = session.createQuery(sb.toString());

        return query.list();
    }
}