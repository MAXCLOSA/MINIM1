package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DronManagerTest {

    DronManager dm;
    List<Posicion> ruta;

    @Before
    public void setUp() {
        dm = DronManagerImpl.getInstance();
        dm.addDron("D1", "Paloma", "Autel", "Vega X2");
        dm.addDron("D2", "Tucan", "Skydio", "Orion 7");
        dm.addDron("D3", "Gaviota", "DJI", "Lince S");
        dm.addPiloto("P1", "Laura", "Garcia Lopez");
        dm.addPiloto("P2", "Marc", "Puig Soler");

        ruta = new ArrayList<>();
        ruta.add(new Posicion(41.2757, 1.9870));
        ruta.add(new Posicion(41.2800, 1.9900));
    }

    @After
    public void tearDown() {
        dm.clear();
    }

    @Test
    public void testAddDron() {
        assertEquals(3, dm.numDrones());
        dm.addDron("D4", "Loro", "Parrot", "Atlas 3");
        assertEquals(4, dm.numDrones());
    }

    @Test
    public void testAddPiloto() {
        assertEquals(2, dm.numPilotos());
        dm.addPiloto("P3", "Anna", "Vidal Roca");
        assertEquals(3, dm.numPilotos());
    }

    @Test
    public void testCrearPlanVuelo() throws Exception {
        dm.crearPlanVuelo("D2", "P1", LocalDateTime.of(2026, 11, 10, 10, 0), 2, ruta);
        assertEquals("D2", dm.getDronesPorHoras().get(0).getId());
        assertEquals(2, dm.getDronesPorHoras().get(0).getHorasVuelo(), 0.01);
        assertEquals("P1", dm.getPilotosPorHoras().get(0).getId());

        assertThrows(PlanSolapadoException.class, () -> dm.crearPlanVuelo("D2", "P2", LocalDateTime.of(2026, 11, 10, 11, 0), 1, ruta));
        assertThrows(PlanSolapadoException.class, () -> dm.crearPlanVuelo("D1", "P1", LocalDateTime.of(2026, 11, 10, 9, 0), 2, ruta));
        assertThrows(DronNotFoundException.class, () -> dm.crearPlanVuelo("D9", "P1", LocalDateTime.of(2026, 11, 11, 10, 0), 1, ruta));
        assertThrows(PilotoNotFoundException.class, () -> dm.crearPlanVuelo("D1", "P9", LocalDateTime.of(2026, 11, 11, 10, 0), 1, ruta));

        dm.guardarEnAlmacen("D1");
        dm.guardarEnAlmacen("D3");
        assertEquals(2, dm.numAlmacen());
        assertThrows(DronEnMantenimientoException.class, () -> dm.crearPlanVuelo("D1", "P2", LocalDateTime.of(2026, 11, 12, 10, 0), 1, ruta));

        assertEquals("D3", dm.repararDron().getId());
        assertEquals("D1", dm.repararDron().getId());
        assertThrows(AlmacenVacioException.class, () -> dm.repararDron());

        dm.crearPlanVuelo("D1", "P2", LocalDateTime.of(2026, 11, 12, 10, 0), 1, ruta);
        assertEquals(1, dm.getPlanesDron("D1").size());
    }

    @Test
    public void testGetPlanesPiloto() throws Exception {
        dm.crearPlanVuelo("D1", "P1", LocalDateTime.of(2026, 11, 10, 10, 0), 2, ruta);
        dm.crearPlanVuelo("D2", "P1", LocalDateTime.of(2026, 11, 10, 12, 0), 1, ruta);
        List<PlanVuelo> planes = dm.getPlanesPiloto("P1");
        assertEquals(2, planes.size());
        assertEquals("D1", planes.get(0).getDronId());
        assertEquals(0, dm.getPlanesPiloto("P2").size());
        assertThrows(PilotoNotFoundException.class, () -> dm.getPlanesPiloto("P9"));
    }
}
