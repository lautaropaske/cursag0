package resources;

import model.Unit;
import services.UnitService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/unit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UnitResource {

    private UnitService unitService;

    public UnitResource() { this.unitService = new UnitService();}

    @POST
    public Unit createUnit(Unit unit){
        return unitService.createUnit(unit);
    }
}