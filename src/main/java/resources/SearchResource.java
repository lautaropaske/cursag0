package resources;

import model.Course;
import services.CourseService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("search")
public class SearchResource {

    private CourseService courseService;

    public SearchResource() {
        courseService = new CourseService();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> searchCourses(@QueryParam("token") String token){
        List<Course> result = new ArrayList<>(courseService.searchCourses(token));
        result.sort(Course::compareTo);
        return result;
    }
}