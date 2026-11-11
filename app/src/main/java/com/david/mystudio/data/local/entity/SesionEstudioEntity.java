package com.david.mystudio.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad de persistencia Room para la tabla 'sesiones_estudio'.
 */
@Entity(
        tableName = "sesiones_estudio",
        foreignKeys = @ForeignKey(
                entity = MateriaEntity.class,
                parentColumns = "id",
                childColumns = "id_materia",
                onDelete = ForeignKey.RESTRICT
        ),
        indices = {@Index(value = "id_materia")}
)
public class SesionEstudioEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_materia")
    private int idMateria;

    @ColumnInfo(name = "fecha_programada")
    private Long fechaProgramada;

    @ColumnInfo(name = "fecha_hora_inicio")
    private Long fechaHoraInicio;

    @ColumnInfo(name = "fecha_hora_fin")
    private Long fechaHoraFin;

    @ColumnInfo(name = "duracion_minutos")
    private int duracionMinutos;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "tecnica")
    private String tecnica;

    @ColumnInfo(name = "notas_observaciones")
    private String notasObservaciones;

    public SesionEstudioEntity() {
    }

    @Ignore
    public SesionEstudioEntity(int id, int idMateria, Long fechaProgramada, Long fechaHoraInicio,
                               Long fechaHoraFin, int duracionMinutos, String estado,
                               String tecnica, String notasObservaciones) {
        this.id = id;
        this.idMateria = idMateria;
        this.fechaProgramada = fechaProgramada;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;
        this.tecnica = tecnica;
        this.notasObservaciones = notasObservaciones;
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

    public Long getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Long fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Long getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Long fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Long getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Long fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getNotasObservaciones() {
        return notasObservaciones;
    }

    public void setNotasObservaciones(String notasObservaciones) {
        this.notasObservaciones = notasObservaciones;
    }
}
