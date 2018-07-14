package resources;

import model.Course;
import model.Payment;
import pojos.PaymentOfCourse;
import services.CourseService;
import services.PaymentService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
@Path("/payment")
public class PaymentResource {

    private PaymentService paymentService;
    private CourseService courseService;

    public PaymentResource() throws MalformedURLException {
       paymentService = new PaymentService();
       courseService = new CourseService();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/course/{courseID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PaymentOfCourse> paymentsOfCourse(@PathParam("courseID") int courseId) {
        return paymentService.paymentsOfCourse(courseId);
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/user/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,List<PaymentOfCourse>> paymentsOfUser(@PathParam("userID") int userId) {
        return paymentService.paymentsOfUser(userId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/initiate")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> initiatePayment(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId) throws MalformedURLException {
        return paymentService.initiatePayment(userId, courseId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> verifyPayment(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId, @QueryParam("answerKey") String answerKey) throws MalformedURLException {
        Map<String, Object> response = paymentService.verifyPayment(userId, answerKey);

        Map<String,String> resultMap =new HashMap<String,String>();
        for (Map.Entry<String, Object>  entry: response.entrySet()) {
            if(entry.getValue() instanceof String){
                resultMap.put(entry.getKey(), entry.getValue().toString());
            }
        }
        if("-1".equals(resultMap.get("StatusCode")) && "APROBADA".equals(resultMap.get("StatusMessage"))){
            double amount = Double.parseDouble(resultMap.get("AMOUNT"));
            this.paymentService.savePayment(userId, courseId,amount);
            this.courseService.enrollInCourse(userId, courseId);
        }
        return resultMap;
    }
}
