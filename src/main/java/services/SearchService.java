package services;

import model.Course;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import java.util.List;

public class SearchService {

    private FullTextSession fts;

    public SearchService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.fts = Search.getFullTextSession(sf.openSession());
    }

    @SuppressWarnings("unchecked")
    // https://docs.jboss.org/hibernate/stable/search/reference/en-US/html_single/#_searching
    // Working from this documentation
    public List<Course> searchCourses(String token) {

        try {
            this.fts.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Transaction tx = this.fts.beginTransaction();

        QueryBuilder queryBuilder = this.fts.getSearchFactory()
                                            .buildQueryBuilder()
                                            .forEntity(Course.class)
                                            .get();

        org.apache.lucene.search.Query query = queryBuilder.keyword()
                                                           .fuzzy()
                                                           .onFields("description","name")
                                                           .matching(token)
                                                           .createQuery();

        org.hibernate.query.Query hibQuery = fts.createFullTextQuery(query, Course.class);

        List result = hibQuery.list();

        tx.commit();

        return result;
    }
}