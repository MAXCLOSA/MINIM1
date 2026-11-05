package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Piloto {
    private String id;
    private String nombre;
    private String apellidos;
    private double horasVuelo;
    private List<PlanVuelo> planes = new LinkedList<>();

    public Piloto() {
    }

    public Piloto(String id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.horasVuelo = 0;
    }

    public void addPlan(PlanVuelo plan) {
        this.planes.add(plan);
        this.horasVuelo = this.horasVuelo + plan.getHoras();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public List<PlanVuelo> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanVuelo> planes) {
        this.planes = planes;
    }

    @Override
    public String toString() {
        return "Piloto[" + id + ", " + nombre + " " + apellidos + ", " + horasVuelo + "h]";
    }
}
