package resources;

import services.CourseService;
import services.PaymentService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.util.HashMap;
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
            this.courseService.enrollInCourse(userId, courseId);
        }
        return resultMap;
    }
}
