package com.david.mystudio.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entidad de persistencia Room para la tabla 'materias'.
 */
@Entity(tableName = "materias")
public class MateriaEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "sigla")
    private String sigla;

    @ColumnInfo(name = "color_hex")
    private String colorHex;

    @ColumnInfo(name = "fecha_creacion")
    private long fechaCreacion;

    @ColumnInfo(name = "activa")
    private boolean activa;

    public MateriaEntity() {
    }

    @Ignore
    public MateriaEntity(int id, String nombre, String sigla, String colorHex, long fechaCreacion, boolean activa) {
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

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
