package com.david.mystudio.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entidad de persistencia Room para la tabla 'racha_estudio'.
 */
@Entity(tableName = "racha_estudio")
public class RachaEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id; // Se utiliza ID fijo = 1 para asegurar un registro singleton local

    @ColumnInfo(name = "dias_consecutivos")
    private int diasConsecutivos;

    @ColumnInfo(name = "ultima_fecha_estudio")
    private Long ultimaFechaEstudio;

    @ColumnInfo(name = "racha_maxima_historica")
    private int rachaMaximaHistorica;

    public RachaEntity() {
        this.id = 1;
    }

    @Ignore
    public RachaEntity(int id, int diasConsecutivos, Long ultimaFechaEstudio, int rachaMaximaHistorica) {
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

    public Long getUltimaFechaEstudio() {
        return ultimaFechaEstudio;
    }

    public void setUltimaFechaEstudio(Long ultimaFechaEstudio) {
        this.ultimaFechaEstudio = ultimaFechaEstudio;
    }

    public int getRachaMaximaHistorica() {
        return rachaMaximaHistorica;
    }

    public void setRachaMaximaHistorica(int rachaMaximaHistorica) {
        this.rachaMaximaHistorica = rachaMaximaHistorica;
    }
}
