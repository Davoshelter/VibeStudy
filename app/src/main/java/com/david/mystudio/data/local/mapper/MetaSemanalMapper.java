package com.david.mystudio.data.local.mapper;

import com.david.mystudio.data.local.entity.MetaSemanalEntity;
import com.david.mystudio.domain.model.EstadoMeta;
import com.david.mystudio.domain.model.MetaSemanal;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapeador entre MetaSemanalEntity y MetaSemanal (modelo de dominio).
 */
public final class MetaSemanalMapper {

    private MetaSemanalMapper() {
    }

    public static MetaSemanal toDomain(MetaSemanalEntity entity) {
        if (entity == null) {
            return null;
        }

        EstadoMeta estado = EstadoMeta.EN_PROGRESO;
        if (entity.getEstado() != null) {
            try {
                estado = EstadoMeta.valueOf(entity.getEstado());
            } catch (IllegalArgumentException e) {
                estado = EstadoMeta.EN_PROGRESO;
            }
        }

        return new MetaSemanal(
                entity.getId(),
                entity.getIdMateria(),
                entity.getNumeroSemana(),
                entity.getAnio(),
                entity.getHorasObjetivo(),
                entity.getHorasCumplidas(),
                estado
        );
    }

    public static MetaSemanalEntity toEntity(MetaSemanal model) {
        if (model == null) {
            return null;
        }

        String estadoStr = model.getEstado() != null ? model.getEstado().name() : EstadoMeta.EN_PROGRESO.name();

        return new MetaSemanalEntity(
                model.getId(),
                model.getIdMateria(),
                model.getNumeroSemana(),
                model.getAnio(),
                model.getHorasObjetivo(),
                model.getHorasCumplidas(),
                estadoStr
        );
    }

    public static List<MetaSemanal> toDomainList(List<MetaSemanalEntity> entities) {
        List<MetaSemanal> lista = new ArrayList<>();
        if (entities != null) {
            for (MetaSemanalEntity entity : entities) {
                lista.add(toDomain(entity));
            }
        }
        return lista;
    }
}
