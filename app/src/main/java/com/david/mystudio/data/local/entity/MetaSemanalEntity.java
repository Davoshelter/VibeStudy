package com.david.mystudio.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad de persistencia Room para la tabla 'metas_semanales'.
 */
@Entity(
        tableName = "metas_semanales",
        foreignKeys = @ForeignKey(
                entity = MateriaEntity.class,
                parentColumns = "id",
                childColumns = "id_materia",
                onDelete = ForeignKey.RESTRICT
        ),
        indices = {
                @Index(value = "id_materia"),
                @Index(value = {"id_materia", "numero_semana", "anio"}, unique = true)
        }
)
public class MetaSemanalEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_materia")
    private int idMateria;

    @ColumnInfo(name = "numero_semana")
    private int numeroSemana;

    @ColumnInfo(name = "anio")
    private int anio;

    @ColumnInfo(name = "horas_objetivo")
    private double horasObjetivo;

    @ColumnInfo(name = "horas_cumplidas")
    private double horasCumplidas;

    @ColumnInfo(name = "estado")
    private String estado;

    public MetaSemanalEntity() {
    }

    @Ignore
    public MetaSemanalEntity(int id, int idMateria, int numeroSemana, int anio,
                             double horasObjetivo, double horasCumplidas, String estado) {
        this.id = id;
        this.idMateria = idMateria;
        this.numeroSemana = numeroSemana;
        this.anio = anio;
        this.horasObjetivo = horasObjetivo;
        this.horasCumplidas = horasCumplidas;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
