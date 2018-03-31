import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// from http://met.guc.edu.eg/OnlineTutorials/JSP%20-%20Servlets/A%20servlet%20example.aspx
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // reading the user input
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");



        response.getOutputStream().println("<h1>Email: " + email + ", pass: "+ pass+ "!</h1>");
    }

}