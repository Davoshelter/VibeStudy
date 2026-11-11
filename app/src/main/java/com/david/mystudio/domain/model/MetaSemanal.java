package com.david.mystudio.domain.model;

import java.util.Objects;

/**
 * Entidad de dominio que representa un compromiso de horas semanales para una materia.
 */
public class MetaSemanal {

    private int id;
    private int idMateria;
    private int numeroSemana;
    private int anio;
    private double horasObjetivo;
    private double horasCumplidas;
    private EstadoMeta estado;

    public MetaSemanal() {
        this.estado = EstadoMeta.EN_PROGRESO;
    }

    public MetaSemanal(int id, int idMateria, int numeroSemana, int anio,
                       double horasObjetivo, double horasCumplidas, EstadoMeta estado) {
        this.id = id;
        this.idMateria = idMateria;
        this.numeroSemana = numeroSemana;
        this.anio = anio;
        this.horasObjetivo = horasObjetivo;
        this.horasCumplidas = horasCumplidas;
        this.estado = estado != null ? estado : EstadoMeta.EN_PROGRESO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(int numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getHorasObjetivo() {
        return horasObjetivo;
    }

    public void setHorasObjetivo(double horasObjetivo) {
        this.horasObjetivo = horasObjetivo;
    }

    public double getHorasCumplidas() {
        return horasCumplidas;
    }

    public void setHorasCumplidas(double horasCumplidas) {
        this.horasCumplidas = horasCumplidas;
    }

    public EstadoMeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoMeta estado) {
        this.estado = estado;
    }

    /**
     * Calcula el porcentaje de avance acotado entre 0.0 y 100.0.
     */
    public double calcularPorcentajeAvance() {
        if (horasObjetivo <= 0.0) {
            return 0.0;
        }
        double porcentaje = (horasCumplidas / horasObjetivo) * 100.0;
        return Math.min(100.0, Math.max(0.0, porcentaje));
    }

    /**
     * Retorna verdadero si la meta ha sido cumplida en un 100%.
     */
    public boolean estaCumplida() {
        return horasCumplidas >= horasObjetivo && horasObjetivo > 0.0;
    }

    @Override
    public boolean equals(Object objetoComparado) {
        if (this == objetoComparado) return true;
        if (objetoComparado == null || getClass() != objetoComparado.getClass()) return false;
        MetaSemanal that = (MetaSemanal) objetoComparado;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
