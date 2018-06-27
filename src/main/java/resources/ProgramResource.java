package resources;

import model.Course;
import model.Program;
import pojos.CoursesOfProgramUpdate;
import services.ProgramService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {
    private ProgramService service;

    public ProgramResource() {
        service = new ProgramService();
    }

    @GET
    @PermitAll
    public List<Program> getPrograms(){
        return service.getPrograms();
    }

    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("/courses/{programID}")
    public List<Course> getCoursesOfProgram(@PathParam("programID") int id) {
        return service.getCoursesOfProgram(id);
    }


    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("/enrolledBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<Program> getEnrolledPrograms(@PathParam("userID") int id){
        return service.getProgramsEnrolledBy(id);
    }

    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("/publishedBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Program> getPublishedPrograms(@PathParam("userID") int id){
        return service.getProgramsPublishedBy(id);
    }

    //    http://localhost:8080/program/enroll?userId=14&programId=17
    @GET
    @RolesAllowed("USER")
    @Path("/enroll")
    public boolean enrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
        return service.enrollInProgram(userId, programId);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/unenroll")
    public boolean unenrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
        return service.unenrollInProgram(userId, programId);
    }


    @GET
    @RolesAllowed("USER")
    @Path("/isfavorite")
    public boolean isFavorite(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
        return service.isFavorite(userId, programId);
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("/addcourse")
    public boolean addCourseToProgram(@QueryParam("programId") int programId, @QueryParam("courseId") int courseId){
        return service.addCourseToProgram(programId, courseId);
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("/removecourse")
    public boolean removeCourseFromProgram(@QueryParam("programId") int programId, @QueryParam("courseId") int courseId){
        return service.removeCourseFromProgram(programId, courseId);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{programID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program getProgram(@PathParam("programID") int id) {
        return service.getProgram(id);
    }


    @GET
    @RolesAllowed("ADMIN")

    @Path("/courseNotPresent/{courseID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Program> getProgramsCourseNotPresent(@PathParam("courseID") int id) {
        return service.getProgramsCourseNotPresent(id);
    }

    @POST
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program registerProgram(Program program) {
        return service.registerProgram(program);
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/courses")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateCouresOfProgram(CoursesOfProgramUpdate courses) {
        return service.updateCoursesOfProgram(courses);
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program updateProgram(Program program) {
        return service.updateProgram(program);
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{programID}")
    public void deleteProgram(@PathParam("programID") int id) {
        service.deleteProgram(id);
    }

    @GET
    @PermitAll
    @Path("/carousel")
    public List<Program> getCarouselPrograms(){
        return service.getCarouselPrograms();
    }

}
