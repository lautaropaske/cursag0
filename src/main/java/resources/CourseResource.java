package resources;

import model.Course;
import model.ExtCourse;
import model.LocalCourse;
import services.CourseService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
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
    @PermitAll
    public List<Course> getCourses(){
        return service.getCourses();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{courseID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course getCourse(@PathParam("courseID") int id) {
        return service.getCourse(id);
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/enrolledBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<Course> getEnrolledCourses(@PathParam("userID") int id){
        return service.getCoursesEnrolledBy(id);
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/publishedBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Course> getPublishedCourses(@PathParam("userID") int id){
        return service.getCoursesPublishedBy(id);
    }

    /*
     request: http://localhost:8080/course/enrollmentStatus?userId=14&courseId=17
     codes:  -1 --> user is not enrolled
             -2 --> user has completed course
             < 0 --> index of current unit
     */
    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/enrollmentStatus")
    public int enrollmentStatus(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.enrollmentStatus(userId, courseId);
    }

    //    http://localhost:8080/course/makeProgress?userId=14&courseId=17
    @GET
    @RolesAllowed("USER")
    @Path("/makeProgress")
    public boolean userProgessesInCourse(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.makeProgess(userId, courseId);
    }

    //    http://localhost:8080/course/goBack?userId=14&courseId=17
    @GET
    @RolesAllowed("USER")
    @Path("/goBack")
    public boolean goBackOneUnit(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.goBack(userId, courseId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/finished")
    public boolean finished(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.finished(userId, courseId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/reset")
    public boolean reset(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.reset(userId, courseId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/unenroll")
    public boolean unenrollUserToCourse(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.unenrollInCourse(userId, courseId);
    }

    //    http://localhost:8080/course/enroll?userId=14&courseId=17
    @GET
    @RolesAllowed("USER")
    @Path("/enroll")
    public boolean enrollUserToCourse(@QueryParam("userId") int userId, @QueryParam("courseId") int courseId){
        return service.enrollInCourse(userId, courseId);
    }

    @POST
    @RolesAllowed({"USER","ADMIN"})
    @Path("/local")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course registerLocalCourse(LocalCourse course) {
        return service.registerCourse(course);
    }

    @POST
    @RolesAllowed({"USER","ADMIN"})
    @Path("/external")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course registerExtCourse(ExtCourse course) {
        return service.registerCourse(course);
    }


    @PUT
    @RolesAllowed({"USER","ADMIN"})
    @Path("/external")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateExtCourse(ExtCourse course) {
        return service.updateCourse(course);
    }

    @PUT
    @RolesAllowed({"USER","ADMIN"})
    @Path("/local")
    @Consumes(MediaType.APPLICATION_JSON)
    public Course updateLocalCourse(LocalCourse course) {
        return service.updateCourse(course);
    }

    @DELETE
    @RolesAllowed({"USER","ADMIN"})
    @Path("/{courseID}")
    public void deleteCourse(@PathParam("courseID") int id) {
        service.deleteCourse(id);
    }

    // Called in home.component for sample courses
    @GET
    @PermitAll
    @Path("/samplePopular")
    public List<Course> getSamplePopularCourses(){
        return service.getSamplePopularCouses();
    }

}