package edu.upc.dsa.to;

import edu.upc.dsa.models.PlanVuelo;
import edu.upc.dsa.models.Posicion;

import java.util.ArrayList;
import java.util.List;

public class PlanVueloTO {
    private String dronId;
    private String pilotoId;
    private String fecha;
    private double horas;
    private List<Posicion> posiciones = new ArrayList<>();

    public PlanVueloTO() {
    }

    public PlanVueloTO(PlanVuelo p) {
        this.dronId = p.getDronId();
        this.pilotoId = p.getPilotoId();
        this.fecha = p.getFecha().toString();
        this.horas = p.getHoras();
        this.posiciones = p.getPosiciones();
    }

    public String getDronId() {
        return dronId;
    }

    public void setDronId(String dronId) {
        this.dronId = dronId;
    }

    public String getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(String pilotoId) {
        this.pilotoId = pilotoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<Posicion> posiciones) {
        this.posiciones = posiciones;
    }
}
