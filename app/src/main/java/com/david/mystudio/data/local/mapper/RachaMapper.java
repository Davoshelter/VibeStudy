package com.david.mystudio.data.local.mapper;

import com.david.mystudio.data.local.entity.RachaEntity;
import com.david.mystudio.domain.model.RachaEstudio;
import java.util.Date;

/**
 * Mapeador entre RachaEntity y RachaEstudio (modelo de dominio).
 */
public final class RachaMapper {

    private RachaMapper() {
    }

    public static RachaEstudio toDomain(RachaEntity entity) {
        if (entity == null) {
            return new RachaEstudio(1, 0, null, 0);
        }

        Date ultimaFecha = entity.getUltimaFechaEstudio() != null
                ? new Date(entity.getUltimaFechaEstudio())
                : null;

        return new RachaEstudio(
                entity.getId(),
                entity.getDiasConsecutivos(),
                ultimaFecha,
                entity.getRachaMaximaHistorica()
        );
    }

    public static RachaEntity toEntity(RachaEstudio model) {
        if (model == null) {
            return new RachaEntity(1, 0, null, 0);
        }

        Long ultimaFechaLong = model.getUltimaFechaEstudio() != null
                ? model.getUltimaFechaEstudio().getTime()
                : null;

        return new RachaEntity(
                model.getId() > 0 ? model.getId() : 1,
                model.getDiasConsecutivos(),
                ultimaFechaLong,
                model.getRachaMaximaHistorica()
        );
    }
}
