package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.MetaSemanalDao;
import com.david.mystudio.data.local.entity.MetaSemanalEntity;
import com.david.mystudio.data.local.mapper.MetaSemanalMapper;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.domain.repository.MetaSemanalRepository;
import com.david.mystudio.util.ThreadExecutor;
import java.util.List;

/**
 * Implementación concreta del repositorio de metas semanales utilizando Room.
 */
public class MetaSemanalRoomRepository implements MetaSemanalRepository {

    private final MetaSemanalDao metaSemanalDao;
    private final ThreadExecutor executor;

    public MetaSemanalRoomRepository(MetaSemanalDao metaSemanalDao) {
        this.metaSemanalDao = metaSemanalDao;
        this.executor = ThreadExecutor.getInstance();
    }

    @Override
    public void guardarOActualizar(MetaSemanal meta, Callback<Long> callback) {
        executor.executeOnBackground(() -> {
            MetaSemanalEntity entity = MetaSemanalMapper.toEntity(meta);
            long idGenerado = metaSemanalDao.insertar(entity);
            executor.executeOnMain(() -> callback.onResult(idGenerado));
        });
    }

    @Override
    public void buscarPorId(int id, Callback<MetaSemanal> callback) {
        executor.executeOnBackground(() -> {
            MetaSemanalEntity entity = metaSemanalDao.buscarPorId(id);
            MetaSemanal meta = MetaSemanalMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(meta));
        });
    }

    @Override
    public void obtenerPorSemanaYAnio(int numeroSemana, int anio, Callback<List<MetaSemanal>> callback) {
        executor.executeOnBackground(() -> {
            List<MetaSemanalEntity> entities = metaSemanalDao.obtenerPorSemanaYAnio(numeroSemana, anio);
            List<MetaSemanal> metas = MetaSemanalMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(metas));
        });
    }

    @Override
    public void obtenerPorMateriaYSemana(int idMateria, int numeroSemana, int anio, Callback<MetaSemanal> callback) {
        executor.executeOnBackground(() -> {
            MetaSemanalEntity entity = metaSemanalDao.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio);
            MetaSemanal meta = MetaSemanalMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(meta));
        });
    }
}
