package edu.upc.dsa.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanVuelo {
    private String dronId;
    private String pilotoId;
    private LocalDateTime fecha;
    private double horas;
    private List<Posicion> posiciones = new ArrayList<>();

    public PlanVuelo() {
    }

    public PlanVuelo(String dronId, String pilotoId, LocalDateTime fecha, double horas, List<Posicion> posiciones) {
        this.dronId = dronId;
        this.pilotoId = pilotoId;
        this.fecha = fecha;
        this.horas = horas;
        this.posiciones = posiciones;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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

    @Override
    public String toString() {
        return "PlanVuelo[" + dronId + ", " + pilotoId + ", " + fecha + ", " + horas + "h]";
    }
}
