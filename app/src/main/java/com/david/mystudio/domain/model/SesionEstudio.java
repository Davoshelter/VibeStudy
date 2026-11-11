package com.david.mystudio.domain.model;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que representa una sesión de estudio planificada o ejecutada.
 */
public class SesionEstudio {

    private int id;
    private int idMateria;
    private Date fechaProgramada;
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private int duracionMinutos;
    private EstadoSesion estado;
    private TipoTecnica tecnica;
    private String notasObservaciones;

    public SesionEstudio() {
        this.estado = EstadoSesion.PENDIENTE;
        this.tecnica = TipoTecnica.BLOQUE_LIBRE;
    }

    public SesionEstudio(int id, int idMateria, Date fechaProgramada, Date fechaHoraInicio,
                         Date fechaHoraFin, int duracionMinutos, EstadoSesion estado,
                         TipoTecnica tecnica, String notasObservaciones) {
        this.id = id;
        this.idMateria = idMateria;
        this.fechaProgramada = fechaProgramada;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado != null ? estado : EstadoSesion.PENDIENTE;
        this.tecnica = tecnica != null ? tecnica : TipoTecnica.BLOQUE_LIBRE;
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

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public EstadoSesion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSesion estado) {
        this.estado = estado;
    }

    public TipoTecnica getTecnica() {
        return tecnica;
    }

    public void setTecnica(TipoTecnica tecnica) {
        this.tecnica = tecnica;
    }

    public String getNotasObservaciones() {
        return notasObservaciones;
    }

    public void setNotasObservaciones(String notasObservaciones) {
        this.notasObservaciones = notasObservaciones;
    }

    @Override
    public boolean equals(Object objetoComparado) {
        if (this == objetoComparado) return true;
        if (objetoComparado == null || getClass() != objetoComparado.getClass()) return false;
        SesionEstudio that = (SesionEstudio) objetoComparado;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
