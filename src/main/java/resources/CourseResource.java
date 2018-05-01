package resources;

import model.Course;
import model.ExtCourse;
import model.LocalCourse;
import services.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    private CourseService service;

    //Resource classes no pueden tener argumentos
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
    @Path("/publishedBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Course> getPublishedCourses(@PathParam("userID") int id){
        return service.getCoursesPublishedBy(id);
    }

    //    http://localhost:8080/course/isEnrolled?userId=14&courseId=17
    @GET
    @Path("/isEnrolled")
    public boolean userIsEnrolled(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.userIsEnrolled(userId, courseId);
    }

    @GET
    @Path("/unenroll")
    public boolean unenrollUserToCourse(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.unenrollInCourse(userId, courseId);
    }

    //    http://localhost:8080/user/enroll?userId=14&courseId=17
    @GET
    @Path("/enroll")
    public boolean enrollUserToCourse(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.enrollInCourse(userId, courseId);
    }

    @GET
    @Path("/{courseID}")
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Path("/external")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateExtCourse(ExtCourse course) {
        return service.updateCourse(course);
    }

    @PUT
    @Path("/local")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateLocalCourse(LocalCourse course) {
        return service.updateCourse(course);
    }

    @DELETE
    @Path("/{courseID}")
    public void deleteCourse(@PathParam("courseID") int id) {
        service.deleteCourse(id);
    }
}