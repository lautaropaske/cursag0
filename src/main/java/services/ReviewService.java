package services;

import model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReviewService {

    private SessionFactory sf;

    public ReviewService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public Review getReview(int id){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Review review = session.get(Review.class,id);
        transaction.commit();
        session.close();
        return review;

    }

    public Review registerReview(Review review){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(review);
        transaction.commit();
        session.close();
        return review;
    }

    public Review updateReview(Review review) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(review);
        transaction.commit();
        session.close();
        return review;
    }


    public void deleteReview(int id) {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Review.class,id));
        transaction.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<Review> getReviewsOfCourse(int courseId) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<Review> reviews = session.createQuery("FROM Review WHERE reviewed.id = " +courseId).list();
        transaction.commit();
        session.close();
        return reviews;

    }
}
