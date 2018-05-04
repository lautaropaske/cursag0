package services;

import model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ReviewService {

    private Session session;

    public ReviewService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    public Review getReview(int id){
        Transaction transaction = session.beginTransaction();
        Review review = session.get(Review.class,id);
        transaction.commit();
        return review;

    }

    public Review registerReview(Review review){
        Transaction transaction = session.beginTransaction();
        session.save(review);
        transaction.commit();
        return review;
    }

    public Review updateReview(Review review) {
        Transaction transaction = session.beginTransaction();
        session.update(review);
        transaction.commit();
        return review;
    }


    public void deleteReview(int id) {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Review.class,id));
        transaction.commit();
    }
}
