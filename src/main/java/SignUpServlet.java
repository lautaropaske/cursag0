import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// from http://met.guc.edu.eg/OnlineTutorials/JSP%20-%20Servlets/A%20servlet%20example.aspx
@WebServlet("/create_account")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        //TODO aca se guarda en la base de datos
        //Aca se guardaria el usuario pero estaba teniendo problemas porque no
        //logro que me agregue el config file de hibernate en el .war
//
//        User user = new User(email, pass,name,surname);
//        SessionFactory sf = new Configuration().configure().buildSessionFactory();
//        Session session = sf.openSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(user);
//        transaction.commit();
//        session.close();

        //aca se haria redirect a home o a access.jsp
        response.getOutputStream().println("Saved User!  Email: " + email + ", pass: "+ pass+
                ", name: "+name+ ", surname: " + surname);
    }

}