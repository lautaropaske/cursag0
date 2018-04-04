package resources;

import model.User;
import org.hibernate.Session;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService service;

    public UserResource(Session session){
        this.service = new UserService(session);
    }

    @GET
    @Path("/{userID}")
    public User getUser(@PathParam("userID") long id){
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
}
