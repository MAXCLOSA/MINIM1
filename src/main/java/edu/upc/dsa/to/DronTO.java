package edu.upc.dsa.to;

import edu.upc.dsa.models.Dron;

public class DronTO {
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private double horasVuelo;

    public DronTO() {
    }

    public DronTO(Dron d) {
        this.id = d.getId();
        this.nombre = d.getNombre();
        this.fabricante = d.getFabricante();
        this.modelo = d.getModelo();
        this.horasVuelo = d.getHorasVuelo();
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
}
