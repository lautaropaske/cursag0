package resources;

import model.Unit;
import services.UnitService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/unit")

@Produces(MediaType.APPLICATION_JSON)
public class UnitResource {

    private UnitService unitService;

    public UnitResource() { this.unitService = new UnitService();}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Unit createUnit(Unit unit){
        return unitService.createUnit(unit);
    }

//    http://localhost:8080/unit?courseId=14&index=0
    @GET
    public Unit getUnit(@QueryParam("courseId") int courseId, @QueryParam("index") int index){
        return unitService.getUnit(courseId, index);
    }
}