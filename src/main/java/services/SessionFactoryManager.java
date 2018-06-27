package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {
    private static SessionFactory INSTANCE = null;

    //private constructor suppresses
    private SessionFactoryManager(){}

    public Session openSession() {
        System.out.println("Creating Session");
        return INSTANCE.openSession();
    }

    //resolve possible multi-thread problems
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration().configure().buildSessionFactory();
        }
    }

    //clone not permitted
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    //returns the same instance every time someone asks for an instance
    public static SessionFactory getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
}
