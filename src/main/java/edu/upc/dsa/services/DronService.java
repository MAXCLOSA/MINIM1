package edu.upc.dsa.services;

import edu.upc.dsa.DronManager;
import edu.upc.dsa.DronManagerImpl;
import edu.upc.dsa.models.Dron;
import edu.upc.dsa.to.DronTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/drones")
@Path("/drones")
public class DronService {

    private DronManager dm;

    public DronService() {
        this.dm = DronManagerImpl.getInstance();
    }

    @POST
    @Path("/")
    @ApiOperation(value = "Añadir un dron")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Dron creado", response = DronTO.class),
            @ApiResponse(code = 400, message = "Datos incorrectos")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDron(DronTO d) {
        if (d == null || d.getId() == null || d.getNombre() == null || d.getFabricante() == null || d.getModelo() == null) {
            return Response.status(400).build();
        }
        Dron dron = dm.addDron(d.getId(), d.getNombre(), d.getFabricante(), d.getModelo());
        return Response.status(201).entity(new DronTO(dron)).build();
    }
}
