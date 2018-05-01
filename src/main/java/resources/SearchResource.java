package resources;

import model.Course;
import services.SearchService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("search")
public class SearchResource {

    private SearchService service;

    public SearchResource() {
        service = new SearchService();
    }

    @GET
    public List<Course> searchCourses(@QueryParam("token") String token){
        List<Course> result = service.searchCourses(token, SearchService.MatchIn.DESCRIPTION);
        result.addAll(service.searchCourses(token, SearchService.MatchIn.NAME));
        result.sort(Course::compareTo);
        return result;
    }
}
