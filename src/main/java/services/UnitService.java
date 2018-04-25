package services;

import model.Unit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UnitService {

    private Session session;

    public UnitService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session  = sf.openSession();
    }

    public Unit createUnit(Unit unit) {
        Transaction transaction = session.beginTransaction();
        session.save(unit);
        transaction.commit();
        return unit;
    }
}
