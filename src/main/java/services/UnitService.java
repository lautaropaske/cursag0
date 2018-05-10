package services;

import model.Course;
import model.LocalCourse;
import model.Unit;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UnitService {

    private Session session;

    public UnitService(){
        SessionFactory sf = SessionFactoryManager.getInstance();
        this.session  = sf.openSession();
    }

    public Unit createUnit(Unit unit) {
        Transaction transaction = session.beginTransaction();
        session.save(unit);
        transaction.commit();
        return unit;
    }

    public Unit getUnit(int courseId, int index) {
        Transaction transaction = session.beginTransaction();

        LocalCourse course = (LocalCourse) session.get(Course.class,courseId);

        Unit response;
        if(course == null){
            response = null;
        }
        else{
            response = course.getUnits().get(index);
        }

        transaction.commit();
        return response;
    }
}
