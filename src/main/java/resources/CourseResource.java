package resources;

import model.Course;
import model.ExtCourse;
import model.LocalCourse;
import services.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    private CourseService service;

    public CourseResource() {
        service = new CourseService();
    }

    @GET
    public List<Course> getCourses(){
        return service.getCourses();
    }

    @GET
    @Path("/enrolledBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<LocalCourse> getEnrolledCourses(@PathParam("userID") int id){
        return service.getCoursesEnrolledBy(id);
    }


    @GET
    @Path("/{courseID}")
    public Course getCourse(@PathParam("courseID") int id) {
        return service.getCourse(id);
    }

    @POST
    @Path("/local")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course registerLocalCourse(LocalCourse course) {
        return service.registerCourse(course);
    }

    @POST
    @Path("/external")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course registerExtCourse(ExtCourse course) {
        return service.registerCourse(course);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateCourse(Course course) {
        return service.updateCourse(course);
    }

    @DELETE
    @Path("/{courseID}")
    public void deleteCourse(@PathParam("courseID") int id) {
        service.deleteCourse(id);
    }
}