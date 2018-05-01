package resources;

import model.Course;
import model.User;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserResource {

    private UserService service;

    public UserResource(){
        service = new UserService();
    }

    @GET
    @Path("/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userID") int id){
        return service.getUser(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public User registerUser(User user){
        return service.registerUser(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public  User updateUser(User user) {
        return service.updateUser(user);
    }

    @DELETE
    @Path("/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("userID") int id) {
        service.delete(id);
    }

    //    http://localhost:8080/user/enroll?userId=14&courseId=17
    @GET
    @Path("/enroll")
    public boolean logUser(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.enrollInCourse(userId, courseId);
    }
}
