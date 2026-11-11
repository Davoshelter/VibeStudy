package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.SesionEstudioDao;
import com.david.mystudio.data.local.entity.SesionEstudioEntity;
import com.david.mystudio.data.local.mapper.SesionEstudioMapper;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.ThreadExecutor;
import java.util.List;

/**
 * Implementación concreta del repositorio de sesiones de estudio utilizando Room.
 */
public class SesionEstudioRoomRepository implements SesionEstudioRepository {

    private final SesionEstudioDao sesionEstudioDao;
    private final ThreadExecutor executor;

    public SesionEstudioRoomRepository(SesionEstudioDao sesionEstudioDao) {
        this.sesionEstudioDao = sesionEstudioDao;
        this.executor = ThreadExecutor.getInstance();
    }

    @Override
    public void guardar(SesionEstudio sesion, Callback<Long> callback) {
        executor.executeOnBackground(() -> {
            SesionEstudioEntity entity = SesionEstudioMapper.toEntity(sesion);
            long idGenerado = sesionEstudioDao.insertar(entity);
            executor.executeOnMain(() -> callback.onResult(idGenerado));
        });
    }

    @Override
    public void actualizar(SesionEstudio sesion, Callback<Void> callback) {
        executor.executeOnBackground(() -> {
            SesionEstudioEntity entity = SesionEstudioMapper.toEntity(sesion);
            sesionEstudioDao.actualizar(entity);
            executor.executeOnMain(() -> callback.onResult(null));
        });
    }

    @Override
    public void buscarPorId(int id, Callback<SesionEstudio> callback) {
        executor.executeOnBackground(() -> {
            SesionEstudioEntity entity = sesionEstudioDao.buscarPorId(id);
            SesionEstudio sesion = SesionEstudioMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(sesion));
        });
    }

    @Override
    public void obtenerCompletadasPorMateria(int idMateria, Callback<List<SesionEstudio>> callback) {
        executor.executeOnBackground(() -> {
            List<SesionEstudioEntity> entities = sesionEstudioDao.obtenerCompletadasPorMateria(idMateria);
            List<SesionEstudio> sesiones = SesionEstudioMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(sesiones));
        });
    }

    @Override
    public void obtenerSesionActiva(Callback<SesionEstudio> callback) {
        executor.executeOnBackground(() -> {
            SesionEstudioEntity entity = sesionEstudioDao.obtenerSesionActiva();
            SesionEstudio sesion = SesionEstudioMapper.toDomain(entity);
            executor.executeOnMain(() -> callback.onResult(sesion));
        });
    }

    @Override
    public void obtenerSesionesProgramadasDelDia(long inicioDia, long finDia, Callback<List<SesionEstudio>> callback) {
        executor.executeOnBackground(() -> {
            List<SesionEstudioEntity> entities = sesionEstudioDao.obtenerSesionesProgramadasDelDia(inicioDia, finDia);
            List<SesionEstudio> sesiones = SesionEstudioMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(sesiones));
        });
    }

    @Override
    public void obtenerCompletadasPorRango(long inicioPeriodo, long finPeriodo, Callback<List<SesionEstudio>> callback) {
        executor.executeOnBackground(() -> {
            List<SesionEstudioEntity> entities = sesionEstudioDao.obtenerCompletadasPorRango(inicioPeriodo, finPeriodo);
            List<SesionEstudio> sesiones = SesionEstudioMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(sesiones));
        });
    }

    @Override
    public void obtenerCompletadasPorMateriaYSemana(int idMateria, long inicioSemana, long finSemana, Callback<List<SesionEstudio>> callback) {
        executor.executeOnBackground(() -> {
            List<SesionEstudioEntity> entities = sesionEstudioDao.obtenerCompletadasPorMateriaYSemana(idMateria, inicioSemana, finSemana);
            List<SesionEstudio> sesiones = SesionEstudioMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(sesiones));
        });
    }

    @Override
    public void obtenerHistorialReciente(int limite, Callback<List<SesionEstudio>> callback) {
        executor.executeOnBackground(() -> {
            List<SesionEstudioEntity> entities = sesionEstudioDao.obtenerHistorialReciente(limite);
            List<SesionEstudio> sesiones = SesionEstudioMapper.toDomainList(entities);
            executor.executeOnMain(() -> callback.onResult(sesiones));
        });
    }

    @Override
    public void sanearSesionesHuerfanas(String motivo, Callback<Integer> callback) {
        executor.executeOnBackground(() -> {
            int afectadas = sesionEstudioDao.cancelarSesionesEnCurso(motivo, System.currentTimeMillis());
            if (callback != null) {
                executor.executeOnMain(() -> callback.onResult(afectadas));
            }
        });
    }
}
