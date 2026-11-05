package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Dron {
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private double horasVuelo;
    private boolean enMantenimiento;
    private List<PlanVuelo> planes = new LinkedList<>();

    public Dron() {
    }

    public Dron(String id, String nombre, String fabricante, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = 0;
        this.enMantenimiento = false;
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

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public List<PlanVuelo> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanVuelo> planes) {
        this.planes = planes;
    }

    @Override
    public String toString() {
        return "Dron[" + id + ", " + nombre + ", " + horasVuelo + "h]";
    }
}
