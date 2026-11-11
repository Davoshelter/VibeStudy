package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.EstadoMeta;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.domain.repository.MetaSemanalRepository;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Date;
import java.util.List;

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
        if (horasObjetivo < MINIMO_HORAS_OBJETIVO || horasObjetivo > MAXIMO_HORAS_OBJETIVO) {
            callback.onError("El objetivo semanal debe estar comprendido entre 1 y 50 horas");
            return;
        }

        metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, metaExistente -> {
            MetaSemanal metaAGuardar;
            if (metaExistente != null) {
                metaAGuardar = metaExistente;
                metaAGuardar.setHorasObjetivo(horasObjetivo);
                if (metaAGuardar.getHorasCumplidas() >= horasObjetivo) {
                    metaAGuardar.setEstado(EstadoMeta.CUMPLIDA);
                } else {
                    metaAGuardar.setEstado(EstadoMeta.EN_PROGRESO);
                }
            } else {
                metaAGuardar = new MetaSemanal(0, idMateria, numeroSemana, anio, horasObjetivo, 0.0, EstadoMeta.EN_PROGRESO);
            }

            metaRepository.guardarOActualizar(metaAGuardar, callback::onSuccess);
        });
    }

    public void acumularAvanceSesion(int idMateria, Date fechaSesion, int minutosEfectivos) {
        int numeroSemana = DateTimeUtils.obtenerNumeroSemana(fechaSesion);
        int anio = DateTimeUtils.obtenerAnio(fechaSesion);

        metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, meta -> {
            if (meta != null) {
                double horasNuevas = meta.getHorasCumplidas() + (minutosEfectivos / 60.0);
                meta.setHorasCumplidas(Math.round(horasNuevas * 10.0) / 10.0);
                if (meta.getHorasCumplidas() >= meta.getHorasObjetivo()) {
                    meta.setEstado(EstadoMeta.CUMPLIDA);
                }
                metaRepository.guardarOActualizar(meta, id -> {});
            }
        });
    }

    public void obtenerMetasSemana(int numeroSemana, int anio, ResultadoCallback<List<MetaSemanal>> callback) {
        metaRepository.obtenerPorSemanaYAnio(numeroSemana, anio, callback::onSuccess);
    }

    public void recalcularAvanceSemanal(int idMateria, int numeroSemana, int anio, Runnable onCompleto) {
        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        sesionRepository.obtenerCompletadasPorMateriaYSemana(idMateria, inicioSemana, finSemana, sesiones -> {
            int minutosTotales = 0;
            if (sesiones != null) {
                for (var s : sesiones) minutosTotales += s.getDuracionMinutos();
            }
            double horasTotales = Math.round((minutosTotales / 60.0) * 10.0) / 10.0;

            metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, meta -> {
                if (meta != null) {
                    meta.setHorasCumplidas(horasTotales);
                    meta.setEstado(horasTotales >= meta.getHorasObjetivo() ? EstadoMeta.CUMPLIDA : EstadoMeta.EN_PROGRESO);
                    metaRepository.guardarOActualizar(meta, id -> onCompleto.run());
                } else {
                    onCompleto.run();
                }
            });
        });
    }
}
