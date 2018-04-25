package resources;

import model.Course;
import model.ExtCourse;
import model.LocalCourse;
import services.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    private CourseService service;

    //Resource classes no pueden tener argumentos
    public CourseResource() {
        service = new CourseService();
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