package resources;

import model.Course;
import model.ExtCourse;
import model.LocalCourse;
import model.Program;
import services.CourseService;
import services.ProgramService;

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
    public List<Program> getPrograms(){
        return service.getPrograms();
    }

    @GET
    @Path("/enrolledBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Set<Program> getEnrolledPrograms(@PathParam("userID") int id){
        return service.getProgramsEnrolledBy(id);
    }

    @GET
    @Path("/publishedBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Program> getPublishedPrograms(@PathParam("userID") int id){
        return service.getProgramsPublishedBy(id);
    }

    //    http://localhost:8080/program/enroll?userId=14&programId=17
    @GET
    @Path("/enroll")
    public boolean enrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
        return service.enrollInProgram(userId, programId);
    }

    @GET
    @Path("/unenroll")
    public boolean unenrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
        return service.unenrollInProgram(userId, programId);
    }

    @GET
    @Path("/addcourse")
    public boolean addCourseToProgram(@QueryParam("programId") int programId, @QueryParam("courseId") int courseId){
        return service.addCourseToProgram(programId, courseId);
    }

    @GET
    @Path("/removecourse")
    public boolean removeCourseFromProgram(@QueryParam("programId") int programId, @QueryParam("courseId") int courseId){
        return service.removeCourseFromProgram(programId, courseId);
    }

    @GET
    @Path("/{programID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program getProgram(@PathParam("programID") int id) {
        return service.getProgram(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Program registerProgram(Program program) {
        return service.registerProgram(program);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Program updateProgram(Program program) {
        return service.updateProgram(program);
    }

    @DELETE
    @Path("/{programID}")
    public void deleteProgram(@PathParam("programID") int id) {
        service.deleteProgram(id);
    }

}