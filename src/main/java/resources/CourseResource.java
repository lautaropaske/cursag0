package resources;

import model.Course;
import services.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/course")
public class CourseResource {

    private CourseService service;

    //Resource classes no pueden tener argumentos
    public CourseResource() {
        service = new CourseService();
    }

    @GET
    @Path("/{courseID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourse(@PathParam("courseID") int id) {
        return service.getCourse(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Course registerCourse(Course course) {
        return service.registerCourse(course);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateCourse(Course course) {
        return service.updateCourse(course);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCourse(Course course) {
        
    }
}