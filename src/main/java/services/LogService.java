package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;
import model.User;
import org.intellij.lang.annotations.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class LogService {

    private SessionFactory sf;

    public LogService(){
        this.sf = SessionFactoryManager.getInstance();
    }

    public Response logUser(String mail, String pass) throws JsonProcessingException {
        Session session  = sf.openSession();

        Transaction transaction = session.beginTransaction();

        @Language("SQL")
        String sql = "SELECT * FROM USER WHERE MAIL = :mail";
        NativeQuery query = session.createNativeQuery(sql);
        query.addEntity(User.class);
        query.setParameter("mail", mail);
        List<User> results = query.list();

        transaction.commit();
        session.close();
        if(results.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("No user is registered with given email").build();
        }
        if(results.get(0).getPassword().equals(pass)){
            final User user = results.get(0);
            ObjectMapper mapper = new ObjectMapper();
            return Response.ok(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Incorrect password for given email").build();
        }


    }
}
