package com.david.mystudio.data.local.mapper;

import com.david.mystudio.data.local.entity.MateriaEntity;
import com.david.mystudio.domain.model.Materia;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Mapeador entre MateriaEntity (persistencia Room) y Materia (modelo de dominio).
 */
public final class MateriaMapper {

    private MateriaMapper() {
    }

    public static Materia toDomain(MateriaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Materia(
                entity.getId(),
                entity.getNombre(),
                entity.getSigla(),
                entity.getColorHex(),
                new Date(entity.getFechaCreacion()),
                entity.isActiva()
        );
    }

    public static MateriaEntity toEntity(Materia model) {
        if (model == null) {
            return null;
        }
        long fechaLong = model.getFechaCreacion() != null
                ? model.getFechaCreacion().getTime()
                : System.currentTimeMillis();

        return new MateriaEntity(
                model.getId(),
                model.getNombre(),
                model.getSigla(),
                model.getColorHex(),
                fechaLong,
                model.isActiva()
        );
    }

    public static List<Materia> toDomainList(List<MateriaEntity> entities) {
        List<Materia> lista = new ArrayList<>();
        if (entities != null) {
            for (MateriaEntity entity : entities) {
                lista.add(toDomain(entity));
            }
        }
        return lista;
    }
}
