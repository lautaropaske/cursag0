package resources;

import model.Unit;
import services.UnitService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/unit")

@Produces(MediaType.APPLICATION_JSON)
public class UnitResource {

    private UnitService unitService;

    public UnitResource() { this.unitService = new UnitService();}

    @POST
    @RolesAllowed({"USER", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Unit createUnit(Unit unit){
        return unitService.createUnit(unit);
    }


    @PUT
    @RolesAllowed({"USER"})
    @Path("/updateorder")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Unit> updateOrderOfUnits(List<Unit> units){
        return unitService.updateOrder(units);
    }

    @DELETE
    @RolesAllowed("USER")
    @Path("/{unitId}")
    public void deleteUnit(@PathParam("unitId") int id) {
        unitService.deleteUnit(id);
    }

//    http://localhost:8080/unit?courseId=14&index=0
//    @GET
//    public Unit getUnit(@QueryParam("courseId") int courseId, @QueryParam("index") int index){
//        return unitService.getUnit(courseId, index);
//    }
}