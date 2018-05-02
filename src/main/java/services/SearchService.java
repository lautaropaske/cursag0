package services;

import model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SearchService {

    private FullTextEntityManager ftem;

    public SearchService(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAService");
        this.ftem = Search.getFullTextEntityManager(emf.createEntityManager());

        try {
            this.ftem.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Course> searchCourses(String token) {

        QueryBuilder queryBuilder = this.ftem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Course.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("description","name")
                .matching(token)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery = ftem.createFullTextQuery(query, Course.class);

        List result = jpaQuery.getResultList();

        return result;
    }
}