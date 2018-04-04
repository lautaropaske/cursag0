package securityfilter;

import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.securityfilter.realm.SimpleSecurityRealmBase;

import java.util.List;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class SecurityRealm extends SimpleSecurityRealmBase{

    @Override
    public boolean booleanAuthenticate(String username, String password) {


        if("registeredUser@gmail.com".equals(username))
            return true;

        return false;

//      TODO aca preguntaria en la base de datos
//        User user;
//        try {
//            SessionFactory sf = new Configuration().configure().buildSessionFactory();
//            Session session = sf.openSession();
//
//            String jpql = "select e from User e where e.Mail = :Mail";
//            List<User> users =
//                    session.createQuery(jpql, User.class)
//                            .setParameter("Mail", username)
//                            .getResultList();
//            user = users.get(0);
//        }
//        catch (Exception e){
//            return false;
//        }
//
//        if(user.getPassword().equals(password)){
//            return true;
//        }
//        else{
//            return false;
//        }



    }

    @Override
    public boolean isUserInRole(String username, String rolename) {
        return true;
    }
}
