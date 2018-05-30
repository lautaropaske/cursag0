package resources;

import model.Course;
import services.CourseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("search")
public class SearchResource {

    private CourseService courseService;

    public SearchResource() {
        courseService = new CourseService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> searchCourses(@QueryParam("token") String token){
        return courseService.searchCourses(token);
    }
}