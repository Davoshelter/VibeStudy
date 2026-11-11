package com.david.mystudio.domain.model;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que representa una asignatura académica cursada por el estudiante.
 */
public class Materia {

    private int id;
    private String nombre;
    private String sigla;
    private String colorHex;
    private Date fechaCreacion;
    private boolean activa;

    public Materia() {
    }

    public Materia(int id, String nombre, String sigla, String colorHex, Date fechaCreacion, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.sigla = sigla;
        this.colorHex = colorHex;
        this.fechaCreacion = fechaCreacion;
        this.activa = activa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public boolean equals(Object objetoComparado) {
        if (this == objetoComparado) return true;
        if (objetoComparado == null || getClass() != objetoComparado.getClass()) return false;
        Materia materia = (Materia) objetoComparado;
        return id == materia.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
