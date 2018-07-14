package services;

import model.Course;
import model.LocalCourse;
import model.Unit;
import model.UserCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UnitService {

    private SessionFactory sf;

    public UnitService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public Unit createUnit(Unit unit) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(unit);
        transaction.commit();
        session.close();
        return unit;
    }

    public List<Unit> updateOrder(List<Unit> units) {

        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();

        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            unit.setNumber(i + 1);
            session.update(unit);
        }

        transaction.commit();
        session.close();
        return units;
    }

    public void deleteUnit(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        final Unit unit = session.get(Unit.class, id);
        session.delete(unit);
        transaction.commit();
        session.close();
    }



//    public Unit getUnit(int courseId, int index) {
//        Session session  = sf.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        LocalCourse course = (LocalCourse) session.get(Course.class,courseId);
//
//        Unit response;
//        if(course == null){
//            response = null;
//        }
//        else{
//            response = course.getUnits().get(index);
//        }
//
//        transaction.commit();
//        session.close();
//
//        return response;
//    }
}
