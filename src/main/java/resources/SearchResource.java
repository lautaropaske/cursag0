package resources;

import model.Course;
import services.SearchService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("search")
public class SearchResource {

    private SearchService service;

    public SearchResource() {
        service = new SearchService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> searchCourses(@QueryParam("token") String token){
        System.out.println(token);
        return service.searchCourses(token);
    }
}