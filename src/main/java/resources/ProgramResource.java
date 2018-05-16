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

//    @GET
//    @Path("/enrolledBy/{userID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Set<Program> getEnrolledPrograms(@PathParam("userID") int id){
//        return service.getProgramsEnrolledBy(id);
//    }

    @GET
    @Path("/publishedBy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Program> getPublishedPrograms(@PathParam("userID") int id){
        return service.getProgramsPublishedBy(id);
    }

    /*
     request: http://localhost:8080/program/enrollmentStatus?userId=14&programId=17
     codes:  -1 --> user is not enrolled
             -2 --> user has completed program
             < 0 --> index of current unit
     */
//    @GET
//    @Path("/enrollmentStatus")
//    public int enrollmentStatus(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
//        return service.enrollmentStatus(userId, programId);
//    }
//
//    //    http://localhost:8080/program/enroll?userId=14&programId=17
//    @GET
//    @Path("/enroll")
//    public boolean enrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
//        return service.enrollInProgram(userId, programId);
//    }
//
//    @GET
//    @Path("/unenroll")
//    public boolean unenrollUserToProgram(@QueryParam("userId") int userId, @QueryParam("programId") int programId){
//        return service.unenrollInProgram(userId, programId);
//    }

    @GET
    @Path("/{programID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program getProgram(@PathParam("programID") int id) {
        return service.getProgram(id);
    }

    @POST
    @Path("/external")
    @Consumes(MediaType.APPLICATION_JSON)
    public Program registerProgram(Program program) {
        return service.registerProgram(program);
    }


    @PUT
    @Path("/external")
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
