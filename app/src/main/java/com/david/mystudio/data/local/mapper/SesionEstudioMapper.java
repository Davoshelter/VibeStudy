package com.david.mystudio.data.local.mapper;

import com.david.mystudio.data.local.entity.SesionEstudioEntity;
import com.david.mystudio.domain.model.EstadoSesion;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Mapeador entre SesionEstudioEntity y SesionEstudio (modelo de dominio).
 */
public final class SesionEstudioMapper {

    private SesionEstudioMapper() {
    }

    public static SesionEstudio toDomain(SesionEstudioEntity entity) {
        if (entity == null) {
            return null;
        }

        Date fechaProgramada = entity.getFechaProgramada() != null ? new Date(entity.getFechaProgramada()) : null;
        Date fechaInicio = entity.getFechaHoraInicio() != null ? new Date(entity.getFechaHoraInicio()) : null;
        Date fechaFin = entity.getFechaHoraFin() != null ? new Date(entity.getFechaHoraFin()) : null;

        EstadoSesion estado = EstadoSesion.PENDIENTE;
        if (entity.getEstado() != null) {
            try {
                estado = EstadoSesion.valueOf(entity.getEstado());
            } catch (IllegalArgumentException e) {
                estado = EstadoSesion.PENDIENTE;
            }
        }

        TipoTecnica tecnica = TipoTecnica.BLOQUE_LIBRE;
        if (entity.getTecnica() != null) {
            try {
                tecnica = TipoTecnica.valueOf(entity.getTecnica());
            } catch (IllegalArgumentException e) {
                tecnica = TipoTecnica.BLOQUE_LIBRE;
            }
        }

        return new SesionEstudio(
                entity.getId(),
                entity.getIdMateria(),
                fechaProgramada,
                fechaInicio,
                fechaFin,
                entity.getDuracionMinutos(),
                estado,
                tecnica,
                entity.getNotasObservaciones()
        );
    }

    public static SesionEstudioEntity toEntity(SesionEstudio model) {
        if (model == null) {
            return null;
        }

        Long fechaProgramadaLong = model.getFechaProgramada() != null ? model.getFechaProgramada().getTime() : null;
        Long fechaInicioLong = model.getFechaHoraInicio() != null ? model.getFechaHoraInicio().getTime() : null;
        Long fechaFinLong = model.getFechaHoraFin() != null ? model.getFechaHoraFin().getTime() : null;

        String estadoStr = model.getEstado() != null ? model.getEstado().name() : EstadoSesion.PENDIENTE.name();
        String tecnicaStr = model.getTecnica() != null ? model.getTecnica().name() : TipoTecnica.BLOQUE_LIBRE.name();

        return new SesionEstudioEntity(
                model.getId(),
                model.getIdMateria(),
                fechaProgramadaLong,
                fechaInicioLong,
                fechaFinLong,
                model.getDuracionMinutos(),
                estadoStr,
                tecnicaStr,
                model.getNotasObservaciones()
        );
    }

    public static List<SesionEstudio> toDomainList(List<SesionEstudioEntity> entities) {
        List<SesionEstudio> lista = new ArrayList<>();
        if (entities != null) {
            for (SesionEstudioEntity entity : entities) {
                lista.add(toDomain(entity));
            }
        }
        return lista;
    }
}
