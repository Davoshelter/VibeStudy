package com.david.mystudio.domain.model;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que contabiliza la constancia y racha de días consecutivos de estudio.
 */
public class RachaEstudio {

    private int id;
    private int diasConsecutivos;
    private Date ultimaFechaEstudio;
    private int rachaMaximaHistorica;

    public RachaEstudio() {
    }

    public RachaEstudio(int id, int diasConsecutivos, Date ultimaFechaEstudio, int rachaMaximaHistorica) {
        this.id = id;
        this.diasConsecutivos = diasConsecutivos;
        this.ultimaFechaEstudio = ultimaFechaEstudio;
        this.rachaMaximaHistorica = rachaMaximaHistorica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiasConsecutivos() {
        return diasConsecutivos;
    }

    public void setDiasConsecutivos(int diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }

    public Date getUltimaFechaEstudio() {
        return ultimaFechaEstudio;
    }

    public void setUltimaFechaEstudio(Date ultimaFechaEstudio) {
        this.ultimaFechaEstudio = ultimaFechaEstudio;
    }

    public int getRachaMaximaHistorica() {
        return rachaMaximaHistorica;
    }

    public void setRachaMaximaHistorica(int rachaMaximaHistorica) {
        this.rachaMaximaHistorica = rachaMaximaHistorica;
    }

    @Override
    public boolean equals(Object objetoComparado) {
        if (this == objetoComparado) return true;
        if (objetoComparado == null || getClass() != objetoComparado.getClass()) return false;
        RachaEstudio that = (RachaEstudio) objetoComparado;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
