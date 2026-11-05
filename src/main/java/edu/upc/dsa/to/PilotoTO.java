package edu.upc.dsa.to;

import edu.upc.dsa.models.Piloto;

public class PilotoTO {
    private String id;
    private String nombre;
    private String apellidos;
    private double horasVuelo;

    public PilotoTO() {
    }

    public PilotoTO(Piloto p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.apellidos = p.getApellidos();
        this.horasVuelo = p.getHorasVuelo();
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
}
