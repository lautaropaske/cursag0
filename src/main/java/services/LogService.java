package services;

import model.Course;
import model.User;
import org.intellij.lang.annotations.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class LogService {

    private Session session;

    public LogService(){
        SessionFactory sf = SessionFactoryManager.getInstance();
        this.session  = sf.openSession();
    }

    public User logUser(String mail, String pass) {
        Transaction transaction = session.beginTransaction();

        @Language("SQL")
        String sql = "SELECT * FROM USER WHERE MAIL = :mail";
        NativeQuery query = session.createNativeQuery(sql);
        query.addEntity(User.class);
        query.setParameter("mail", mail);
        List<User> results = query.list();

        if(results.get(0).getPassword().equals(pass)){
            transaction.commit();
            return results.get(0);
        }

        throw new AuthenticationFailedException("Pass or username invalid!");
    }
}
