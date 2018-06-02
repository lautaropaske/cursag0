package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Course;
import model.User;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response registerUser(User user) throws JsonProcessingException {
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
        service.deleteUser(id);
    }


}
