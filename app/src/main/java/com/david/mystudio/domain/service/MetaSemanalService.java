package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.EstadoMeta;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.repository.MetaSemanalRepository;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio de dominio que implementa las reglas de negocio de metas semanales (RN-MET).
 */
public class MetaSemanalService {

    public static final double MINIMO_HORAS_OBJETIVO = 1.0;
    public static final double MAXIMO_HORAS_OBJETIVO = 50.0;

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
        void onError(String mensajeError);
    }

    private final MetaSemanalRepository metaRepository;
    private final SesionEstudioRepository sesionRepository;

    public MetaSemanalService(MetaSemanalRepository metaRepository,
                              SesionEstudioRepository sesionRepository) {
        this.metaRepository = metaRepository;
        this.sesionRepository = sesionRepository;
    }

    public void establecerMetaSemanal(int idMateria, int numeroSemana, int anio, double horasObjetivo,
                                     ResultadoCallback<Long> callback) {
        // RN-MET-01: Rango de horas entre 1 y 50
        if (!Double.isFinite(horasObjetivo)
                || horasObjetivo < MINIMO_HORAS_OBJETIVO || horasObjetivo > MAXIMO_HORAS_OBJETIVO) {
            callback.onError("El objetivo semanal debe estar comprendido entre 1 y 50 horas");
            return;
        }

        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        sesionRepository.obtenerCompletadasPorMateriaYSemana(idMateria, inicioSemana, finSemana, sesiones -> {
            int minutos = sumarMinutos(sesiones);
            metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, metaExistente -> {
                MetaSemanal metaAGuardar = metaExistente != null ? metaExistente
                        : new MetaSemanal(0, idMateria, numeroSemana, anio, horasObjetivo, 0, EstadoMeta.EN_PROGRESO);
                metaAGuardar.setHorasObjetivo(horasObjetivo);
                metaAGuardar.actualizarAvanceEnMinutos(minutos);
                metaRepository.guardarOActualizar(metaAGuardar, callback::onSuccess);
            });
        });
    }

    public void acumularAvanceSesion(int idMateria, Date fechaSesion) {
        int numeroSemana = DateTimeUtils.obtenerNumeroSemana(fechaSesion);
        int anio = DateTimeUtils.obtenerAnio(fechaSesion);

        recalcularAvanceSemanal(idMateria, numeroSemana, anio, null);
    }

    public void obtenerMetasSemana(int numeroSemana, int anio, ResultadoCallback<List<MetaSemanal>> callback) {
        metaRepository.obtenerPorSemanaYAnio(numeroSemana, anio, callback::onSuccess);
    }

    public void recalcularAvanceSemanal(int idMateria, int numeroSemana, int anio, Runnable onCompleto) {
        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        sesionRepository.obtenerCompletadasPorMateriaYSemana(idMateria, inicioSemana, finSemana, sesiones -> {
            int minutos = sumarMinutos(sesiones);
            metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, meta -> {
                if (meta != null) {
                    meta.actualizarAvanceEnMinutos(minutos);
                    metaRepository.guardarOActualizar(meta, id -> {
                        if (onCompleto != null) onCompleto.run();
                    });
                } else if (onCompleto != null) {
                    onCompleto.run();
                }
            });
        });
    }

    public void sincronizarMetasConSesionesReales(int numeroSemana, int anio, Runnable onCompleto) {
        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        sesionRepository.obtenerCompletadasPorRango(inicioSemana, finSemana, sesiones -> {
            Map<Integer, Integer> minutosPorMateria = new HashMap<>();
            if (sesiones != null) {
                for (SesionEstudio s : sesiones) {
                    minutosPorMateria.merge(s.getIdMateria(), s.getDuracionMinutos(), Integer::sum);
                }
            }

            metaRepository.obtenerPorSemanaYAnio(numeroSemana, anio, metas -> {
                if (metas == null || metas.isEmpty()) {
                    if (onCompleto != null) onCompleto.run();
                    return;
                }

                AtomicInteger pendientes = new AtomicInteger(metas.size());
                for (MetaSemanal meta : metas) {
                    int minutos = minutosPorMateria.getOrDefault(meta.getIdMateria(), 0);
                    meta.actualizarAvanceEnMinutos(minutos);
                    metaRepository.guardarOActualizar(meta, id -> {
                        if (pendientes.decrementAndGet() == 0 && onCompleto != null) onCompleto.run();
                    });
                }
            });
        });
    }

    private int sumarMinutos(List<SesionEstudio> sesiones) {
        int minutos = 0;
        if (sesiones != null) {
            for (SesionEstudio sesion : sesiones) minutos += sesion.getDuracionMinutos();
        }
        return minutos;
    }
}
