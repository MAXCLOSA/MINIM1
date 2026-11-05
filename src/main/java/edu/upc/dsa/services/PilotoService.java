package edu.upc.dsa.services;

import edu.upc.dsa.DronManager;
import edu.upc.dsa.DronManagerImpl;
import edu.upc.dsa.exceptions.PilotoNotFoundException;
import edu.upc.dsa.models.Piloto;
import edu.upc.dsa.models.PlanVuelo;
import edu.upc.dsa.to.PilotoTO;
import edu.upc.dsa.to.PlanVueloTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/pilotos")
@Path("/pilotos")
public class PilotoService {

    private DronManager dm;

    public PilotoService() {
        this.dm = DronManagerImpl.getInstance();
    }

    @POST
    @Path("/")
    @ApiOperation(value = "Añadir un piloto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Piloto creado", response = PilotoTO.class),
            @ApiResponse(code = 400, message = "Datos incorrectos")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPiloto(PilotoTO p) {
        if (p == null || p.getId() == null || p.getNombre() == null || p.getApellidos() == null) {
            return Response.status(400).build();
        }
        Piloto piloto = dm.addPiloto(p.getId(), p.getNombre(), p.getApellidos());
        return Response.status(201).entity(new PilotoTO(piloto)).build();
    }

    @GET
    @Path("/{id}/planes")
    @ApiOperation(value = "Planes de vuelo de un piloto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PlanVueloTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Piloto no encontrado")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlanesPiloto(@PathParam("id") String id) {
        try {
            List<PlanVuelo> planes = dm.getPlanesPiloto(id);
            List<PlanVueloTO> lista = new ArrayList<>();
            for (PlanVuelo plan : planes) {
                lista.add(new PlanVueloTO(plan));
            }
            GenericEntity<List<PlanVueloTO>> entity = new GenericEntity<List<PlanVueloTO>>(lista) {};
            return Response.status(200).entity(entity).build();
        } catch (PilotoNotFoundException e) {
            return Response.status(404).build();
        }
    }
}
