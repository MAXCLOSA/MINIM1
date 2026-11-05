package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;

import java.time.LocalDateTime;
import java.util.List;

public interface DronManager {
    Dron addDron(String id, String nombre, String fabricante, String modelo);
    Piloto addPiloto(String id, String nombre, String apellidos);
    List<Dron> getDronesPorHoras();
    List<Piloto> getPilotosPorHoras();
    void guardarEnAlmacen(String dronId) throws DronNotFoundException, DronEnMantenimientoException;
    Dron repararDron() throws AlmacenVacioException;
    PlanVuelo crearPlanVuelo(String dronId, String pilotoId, LocalDateTime fecha, double horas, List<Posicion> posiciones)
            throws DronNotFoundException, PilotoNotFoundException, DronEnMantenimientoException, PlanSolapadoException;
    List<PlanVuelo> getPlanesPiloto(String pilotoId) throws PilotoNotFoundException;
    List<PlanVuelo> getPlanesDron(String dronId) throws DronNotFoundException;

    int numDrones();
    int numPilotos();
    int numAlmacen();
    void clear();
}
