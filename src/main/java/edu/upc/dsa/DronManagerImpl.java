package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class DronManagerImpl implements DronManager {

    private static DronManager instance;
    private static final Logger logger = Logger.getLogger(DronManagerImpl.class);

    private HashMap<String, Dron> drones;
    private HashMap<String, Piloto> pilotos;
    private Stack<Dron> almacen;

    private DronManagerImpl() {
        this.drones = new HashMap<>();
        this.pilotos = new HashMap<>();
        this.almacen = new Stack<>();
    }

    public static DronManager getInstance() {
        if (instance == null) {
            instance = new DronManagerImpl();
        }
        return instance;
    }

    @Override
    public Dron addDron(String id, String nombre, String fabricante, String modelo) {
        logger.info("addDron inicio: id=" + id + ", nombre=" + nombre + ", fabricante=" + fabricante + ", modelo=" + modelo);
        Dron dron = new Dron(id, nombre, fabricante, modelo);
        drones.put(id, dron);
        logger.info("addDron fin: " + dron);
        return dron;
    }

    @Override
    public Piloto addPiloto(String id, String nombre, String apellidos) {
        logger.info("addPiloto inicio: id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos);
        Piloto piloto = new Piloto(id, nombre, apellidos);
        pilotos.put(id, piloto);
        logger.info("addPiloto fin: " + piloto);
        return piloto;
    }

    @Override
    public List<Dron> getDronesPorHoras() {
        logger.info("getDronesPorHoras inicio");
        List<Dron> lista = new ArrayList<>(drones.values());
        lista.sort((d1, d2) -> Double.compare(d2.getHorasVuelo(), d1.getHorasVuelo()));
        logger.info("getDronesPorHoras fin: " + lista);
        return lista;
    }

    @Override
    public List<Piloto> getPilotosPorHoras() {
        logger.info("getPilotosPorHoras inicio");
        List<Piloto> lista = new ArrayList<>(pilotos.values());
        lista.sort((p1, p2) -> Double.compare(p2.getHorasVuelo(), p1.getHorasVuelo()));
        logger.info("getPilotosPorHoras fin: " + lista);
        return lista;
    }

    @Override
    public void guardarEnAlmacen(String dronId) throws DronNotFoundException, DronEnMantenimientoException {
        logger.info("guardarEnAlmacen inicio: dronId=" + dronId);
        Dron dron = drones.get(dronId);
        if (dron == null) {
            logger.error("Dron no encontrado: " + dronId);
            throw new DronNotFoundException();
        }
        if (dron.isEnMantenimiento()) {
            logger.error("El dron ya esta en el almacen: " + dronId);
            throw new DronEnMantenimientoException();
        }
        dron.setEnMantenimiento(true);
        almacen.push(dron);
        logger.info("guardarEnAlmacen fin: " + dron + ", drones en almacen=" + almacen.size());
    }

    @Override
    public Dron repararDron() throws AlmacenVacioException {
        logger.info("repararDron inicio");
        if (almacen.isEmpty()) {
            logger.error("El almacen esta vacio");
            throw new AlmacenVacioException();
        }
        Dron dron = almacen.pop();
        dron.setEnMantenimiento(false);
        logger.info("repararDron fin: " + dron);
        return dron;
    }

    @Override
    public PlanVuelo crearPlanVuelo(String dronId, String pilotoId, LocalDateTime fecha, double horas, List<Posicion> posiciones)
            throws DronNotFoundException, PilotoNotFoundException, DronEnMantenimientoException, PlanSolapadoException {
        logger.info("crearPlanVuelo inicio: dronId=" + dronId + ", pilotoId=" + pilotoId + ", fecha=" + fecha + ", horas=" + horas);
        Dron dron = drones.get(dronId);
        if (dron == null) {
            logger.error("Dron no encontrado: " + dronId);
            throw new DronNotFoundException();
        }
        Piloto piloto = pilotos.get(pilotoId);
        if (piloto == null) {
            logger.error("Piloto no encontrado: " + pilotoId);
            throw new PilotoNotFoundException();
        }
        if (dron.isEnMantenimiento()) {
            logger.error("El dron esta en mantenimiento: " + dronId);
            throw new DronEnMantenimientoException();
        }
        LocalDateTime fin = fecha.plusMinutes((long) (horas * 60));
        if (seSolapa(dron.getPlanes(), fecha, fin)) {
            logger.error("El dron ya tiene un plan en ese horario: " + dronId);
            throw new PlanSolapadoException();
        }
        if (seSolapa(piloto.getPlanes(), fecha, fin)) {
            logger.error("El piloto ya tiene un plan en ese horario: " + pilotoId);
            throw new PlanSolapadoException();
        }
        PlanVuelo plan = new PlanVuelo(dronId, pilotoId, fecha, horas, posiciones);
        dron.addPlan(plan);
        piloto.addPlan(plan);
        logger.info("crearPlanVuelo fin: " + plan);
        return plan;
    }

    private boolean seSolapa(List<PlanVuelo> planes, LocalDateTime inicio, LocalDateTime fin) {
        for (PlanVuelo p : planes) {
            LocalDateTime pInicio = p.getFecha();
            LocalDateTime pFin = pInicio.plusMinutes((long) (p.getHoras() * 60));
            if (inicio.isBefore(pFin) && pInicio.isBefore(fin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PlanVuelo> getPlanesPiloto(String pilotoId) throws PilotoNotFoundException {
        logger.info("getPlanesPiloto inicio: pilotoId=" + pilotoId);
        Piloto piloto = pilotos.get(pilotoId);
        if (piloto == null) {
            logger.error("Piloto no encontrado: " + pilotoId);
            throw new PilotoNotFoundException();
        }
        List<PlanVuelo> planes = piloto.getPlanes();
        logger.info("getPlanesPiloto fin: " + planes);
        return planes;
    }

    @Override
    public List<PlanVuelo> getPlanesDron(String dronId) throws DronNotFoundException {
        logger.info("getPlanesDron inicio: dronId=" + dronId);
        Dron dron = drones.get(dronId);
        if (dron == null) {
            logger.error("Dron no encontrado: " + dronId);
            throw new DronNotFoundException();
        }
        List<PlanVuelo> planes = dron.getPlanes();
        logger.info("getPlanesDron fin: " + planes);
        return planes;
    }

    @Override
    public int numDrones() {
        logger.info("numDrones inicio");
        int n = drones.size();
        logger.info("numDrones fin: " + n);
        return n;
    }

    @Override
    public int numPilotos() {
        logger.info("numPilotos inicio");
        int n = pilotos.size();
        logger.info("numPilotos fin: " + n);
        return n;
    }

    @Override
    public int numAlmacen() {
        logger.info("numAlmacen inicio");
        int n = almacen.size();
        logger.info("numAlmacen fin: " + n);
        return n;
    }

    @Override
    public void clear() {
        logger.info("clear inicio");
        drones.clear();
        pilotos.clear();
        almacen.clear();
        logger.info("clear fin");
    }
}
