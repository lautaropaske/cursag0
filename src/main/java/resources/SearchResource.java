package resources;

import model.Course;
import services.SearchService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("search")
public class SearchResource {

    private SearchService service;

    public SearchResource() {
        service = new SearchService();
    }

    @GET
    public List<Course> searchCourses(){

        return new ArrayList<>();
    }
}
