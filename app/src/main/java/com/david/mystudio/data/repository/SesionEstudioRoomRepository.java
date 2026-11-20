package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.SesionEstudioDao;
import com.david.mystudio.data.local.entity.SesionEstudioEntity;
import com.david.mystudio.data.local.mapper.SesionEstudioMapper;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.ThreadExecutor;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementación concreta del repositorio de sesiones de estudio utilizando Room.
 */
public class SesionEstudioRoomRepository implements SesionEstudioRepository {

    private final SesionEstudioDao sesionEstudioDao;
    private final ThreadExecutor executor;
    // El saneamiento de arranque debe terminar antes de insertar una nueva sesión.
    private final ExecutorService operacionesSesion = Executors.newSingleThreadExecutor();

    public SesionEstudioRoomRepository(SesionEstudioDao sesionEstudioDao) {
        this.sesionEstudioDao = sesionEstudioDao;
        this.executor = ThreadExecutor.getInstance();
    }

    @Override
    public void guardar(SesionEstudio sesion, Callback<Long> callback, Consumer<RuntimeException> onError) {
        ejecutarEnCola(() -> sesionEstudioDao.insertar(SesionEstudioMapper.toEntity(sesion)), callback, onError);
    }

    @Override
    public void guardarSiNoHaySesionActiva(SesionEstudio sesion, Callback<Long> callback,
                                          Consumer<RuntimeException> onError) {
        ejecutarEnCola(() -> sesionEstudioDao.insertarSiNoHaySesionActiva(
                SesionEstudioMapper.toEntity(sesion)), callback, onError);
    }

    @Override
    public void cerrar(SesionEstudio cierre, Callback<Integer> callback, Consumer<RuntimeException> onError) {
        ejecutarEnCola(() -> sesionEstudioDao.cerrarSesionActiva(cierre.getId(), cierre.getEstado().name(),
                cierre.getFechaHoraFin().getTime(), cierre.getDuracionMinutos(), cierre.getNotasObservaciones()),
                callback, onError);
    }

    private <T> void ejecutarEnCola(Supplier<T> operacion, Callback<T> callback, Consumer<RuntimeException> onError) {
        operacionesSesion.execute(() -> {
            try {
                T resultado = operacion.get();
                executor.executeOnMain(() -> callback.onResult(resultado));
            } catch (RuntimeException error) {
                android.util.Log.e("SesionEstudioRepo", "Falló el acceso al registro de la sesión", error);
                executor.executeOnMain(() -> onError.accept(error));
            }
        });
    }

    @Override
    public void buscarPorId(int id, Callback<SesionEstudio> callback, Consumer<RuntimeException> onError) {
        ejecutarEnCola(() -> SesionEstudioMapper.toDomain(sesionEstudioDao.buscarPorId(id)), callback, onError);
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
    public void sanearSesionesHuerfanas(int idConCronometro, String motivo, Callback<Integer> callback,
                                       Consumer<RuntimeException> onError) {
        ejecutarEnCola(() -> sesionEstudioDao.cancelarSesionesEnCurso(motivo, System.currentTimeMillis(), idConCronometro),
                callback, onError);
    }
}
