package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;
import model.LocalCourse;
import model.User;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserService {

    private SessionFactory sf;

    public UserService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public User getUser(int id){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        transaction.commit();
        session.close();
        return user;

    }

    public Response registerUser(User user) throws JsonProcessingException {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        final User userWithSameMail = session.byNaturalId(User.class).using("mail", user.getMail()).load();
        if(userWithSameMail != null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Another user is already registered with this email").build();
        }
        else {
            session.save(user);
            transaction.commit();
            session.close();
            ObjectMapper mapper = new ObjectMapper();
            return Response.ok(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON).build();
        }
    }

    public User saveUser(User user){
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user;
    }

    public User updateUser(User user) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return user;
    }


    public void deleteUser(int id) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class,id));
        transaction.commit();
        session.close();

    }

}
