package resources;

import model.User;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    private UserService service;

    //Resource classes no pueden tener argumentos
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
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {

    }
}
