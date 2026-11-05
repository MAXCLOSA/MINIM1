package edu.upc.dsa.services;

import edu.upc.dsa.DronManager;
import edu.upc.dsa.DronManagerImpl;
import edu.upc.dsa.exceptions.DronEnMantenimientoException;
import edu.upc.dsa.exceptions.DronNotFoundException;
import edu.upc.dsa.exceptions.PilotoNotFoundException;
import edu.upc.dsa.exceptions.PlanSolapadoException;
import edu.upc.dsa.models.PlanVuelo;
import edu.upc.dsa.to.PlanVueloTO;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Api(value = "/planes")
@Path("/planes")
public class PlanVueloService {

    private DronManager dm;

    public PlanVueloService() {
        this.dm = DronManagerImpl.getInstance();
    }

    @POST
    @Path("/")
    @ApiOperation(value = "Crear un plan de vuelo", notes = "fecha con formato 2026-11-10T10:00")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plan creado", response = PlanVueloTO.class),
            @ApiResponse(code = 400, message = "Datos incorrectos"),
            @ApiResponse(code = 404, message = "Dron o piloto no encontrado"),
            @ApiResponse(code = 409, message = "Dron en mantenimiento o plan solapado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPlan(PlanVueloTO p) {
        if (p == null || p.getDronId() == null || p.getPilotoId() == null || p.getFecha() == null || p.getHoras() <= 0) {
            return Response.status(400).build();
        }
        LocalDateTime fecha;
        try {
            fecha = LocalDateTime.parse(p.getFecha());
        } catch (DateTimeParseException e) {
            return Response.status(400).build();
        }
        try {
            PlanVuelo plan = dm.crearPlanVuelo(p.getDronId(), p.getPilotoId(), fecha, p.getHoras(), p.getPosiciones());
            return Response.status(201).entity(new PlanVueloTO(plan)).build();
        } catch (DronNotFoundException | PilotoNotFoundException e) {
            return Response.status(404).build();
        } catch (DronEnMantenimientoException | PlanSolapadoException e) {
            return Response.status(409).build();
        }
    }
}
