package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.RachaDao;
import com.david.mystudio.data.local.entity.RachaEntity;
import com.david.mystudio.data.local.mapper.RachaMapper;
import com.david.mystudio.domain.model.RachaEstudio;
import com.david.mystudio.domain.repository.RachaRepository;
import com.david.mystudio.util.ThreadExecutor;

/**
 * Implementación concreta del repositorio de racha de estudio utilizando Room.
 */
public class RachaRoomRepository implements RachaRepository {

    private final RachaDao rachaDao;
    private final ThreadExecutor executor;

    public RachaRoomRepository(RachaDao rachaDao) {
        this.rachaDao = rachaDao;
        this.executor = ThreadExecutor.getInstance();
    }

    @Override
    public void obtenerRachaActual(Callback<RachaEstudio> callback) {
        executor.executeOnBackground(() -> {
            RachaEntity entity = rachaDao.obtenerRachaActual();
            if (entity == null) {
                // Registro inicial por defecto si la base de datos está vacía
                entity = new RachaEntity(1, 0, null, 0);
                rachaDao.insertar(entity);
            }
            RachaEstudio racha = RachaMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(racha));
        });
    }

    @Override
    public void actualizarRacha(RachaEstudio racha, Callback<Void> callback) {
        executor.executeOnBackground(() -> {
            RachaEntity entity = RachaMapper.toEntity(racha);
            rachaDao.actualizar(entity);
            executor.executeOnMain(() -> callback.onResult(null));
        });
    }
}
