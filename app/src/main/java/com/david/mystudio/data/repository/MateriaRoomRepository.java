package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.MateriaDao;
import com.david.mystudio.data.local.entity.MateriaEntity;
import com.david.mystudio.data.local.mapper.MateriaMapper;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.repository.MateriaRepository;
import com.david.mystudio.util.ThreadExecutor;
import java.util.List;

/**
 * Implementación concreta del repositorio de materias utilizando Room.
 */
public class MateriaRoomRepository implements MateriaRepository {

    private final MateriaDao materiaDao;
    private final ThreadExecutor executor;

    public MateriaRoomRepository(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
        this.executor = ThreadExecutor.getInstance();
    }

    @Override
    public void guardar(Materia materia, Callback<Long> callback) {
        executor.executeOnBackground(() -> {
            MateriaEntity entity = MateriaMapper.toEntity(materia);
            long idGenerado = materiaDao.insertar(entity);
            executor.executeOnMain(() -> callback.onResult(idGenerado));
        });
    }

    @Override
    public void actualizar(Materia materia, Callback<Void> callback) {
        executor.executeOnBackground(() -> {
            MateriaEntity entity = MateriaMapper.toEntity(materia);
            materiaDao.actualizar(entity);
            executor.executeOnMain(() -> callback.onResult(null));
        });
    }

    @Override
    public void obtenerTodasActivas(Callback<List<Materia>> callback) {
        executor.executeOnBackground(() -> {
            List<MateriaEntity> entities = materiaDao.obtenerTodasActivas();
            List<Materia> materias = MateriaMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(materias));
        });
    }

    @Override
    public void buscarPorId(int id, Callback<Materia> callback) {
        executor.executeOnBackground(() -> {
            MateriaEntity entity = materiaDao.buscarPorId(id);
            Materia materia = MateriaMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(materia));
        });
    }

    @Override
    public void buscarPorNombre(String nombre, Callback<Materia> callback) {
        executor.executeOnBackground(() -> {
            MateriaEntity entity = materiaDao.buscarPorNombre(nombre);
            Materia materia = MateriaMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(materia));
        });
    }

    @Override
    public void darDeBajaLogica(int idMateria, Callback<Void> callback) {
        executor.executeOnBackground(() -> {
            materiaDao.darDeBajaLogica(idMateria);
            executor.executeOnMain(() -> callback.onResult(null));
        });
    }
}
