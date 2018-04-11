package resources;

import model.User;
import services.LogService;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

// http://localhost:8080/log?mail="jorge@hotmail.com"&pass="1234"

@Path("/log")
public class LogResource {

    private LogService logService;

    public LogResource(){ this.logService = new LogService();}

    @GET
    public User logUser(@QueryParam("mail") String mail, @QueryParam("pass") String pass){
        return logService.logUser(mail,pass);
    }
}
