package com.david.mystudio.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Modelo de dominio de agregación para visualización de analíticas y reportes.
 */
public class ReporteEstudio {

    private Date fechaInicio;
    private Date fechaFin;
    private TipoPeriodo periodo;
    private int totalMinutosEstudio;
    private double promedioMinutosDiario;
    private Map<String, Double> distribucionPorMateria;
    private Map<String, Integer> minutosPorDiaSemana;

    public ReporteEstudio() {
        this.distribucionPorMateria = new HashMap<>();
        this.minutosPorDiaSemana = new HashMap<>();
    }

    public ReporteEstudio(Date fechaInicio, Date fechaFin, TipoPeriodo periodo,
                          int totalMinutosEstudio, double promedioMinutosDiario,
                          Map<String, Double> distribucionPorMateria,
                          Map<String, Integer> minutosPorDiaSemana) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
        this.totalMinutosEstudio = totalMinutosEstudio;
        this.promedioMinutosDiario = promedioMinutosDiario;
        this.distribucionPorMateria = distribucionPorMateria != null ? distribucionPorMateria : new HashMap<>();
        this.minutosPorDiaSemana = minutosPorDiaSemana != null ? minutosPorDiaSemana : new HashMap<>();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TipoPeriodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(TipoPeriodo periodo) {
        this.periodo = periodo;
    }

    public int getTotalMinutosEstudio() {
        return totalMinutosEstudio;
    }

    public void setTotalMinutosEstudio(int totalMinutosEstudio) {
        this.totalMinutosEstudio = totalMinutosEstudio;
    }

    public double getPromedioMinutosDiario() {
        return promedioMinutosDiario;
    }

    public void setPromedioMinutosDiario(double promedioMinutosDiario) {
        this.promedioMinutosDiario = promedioMinutosDiario;
    }

    public Map<String, Double> getDistribucionPorMateria() {
        return distribucionPorMateria;
    }

    public void setDistribucionPorMateria(Map<String, Double> distribucionPorMateria) {
        this.distribucionPorMateria = distribucionPorMateria;
    }

    public Map<String, Integer> getMinutosPorDiaSemana() {
        return minutosPorDiaSemana;
    }

    public void setMinutosPorDiaSemana(Map<String, Integer> minutosPorDiaSemana) {
        this.minutosPorDiaSemana = minutosPorDiaSemana;
    }
}
