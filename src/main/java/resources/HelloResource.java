package resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
@Path("/")
public class HelloResource {
    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return "Api funcionando";
    }
}
