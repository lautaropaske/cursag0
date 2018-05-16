package services;

import model.Course;
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
        Course reviewedCourse = session.get(Course.class,review.getReviewed().getId());
        reviewedCourse.addReview(review);
        session.persist(reviewedCourse);
        session.save(review);
        transaction.commit();
        session.close();
        return review;
    }

    public Review updateReview(Review review) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Course reviewedCourse = session.get(Course.class,review.getReviewed().getId());
        reviewedCourse.removeReview(review);
        reviewedCourse.addReview(review);
        session.saveOrUpdate(reviewedCourse);
        transaction.commit();
        session.close();


        Session session2  = sf.openSession();
        Transaction transaction2 = session2.beginTransaction();
        session2.saveOrUpdate(review);
        transaction2.commit();
        session2.close();

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
