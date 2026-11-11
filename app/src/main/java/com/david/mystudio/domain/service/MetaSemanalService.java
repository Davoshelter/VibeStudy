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

        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        sesionRepository.obtenerCompletadasPorMateriaYSemana(idMateria, inicioSemana, finSemana, sesiones -> {
            int minutosTotales = 0;
            if (sesiones != null) {
                for (SesionEstudio s : sesiones) {
                    minutosTotales += s.getDuracionMinutos();
                }
            }
            double horasExistentes = Math.round((minutosTotales / 60.0) * 10.0) / 10.0;
            if (minutosTotales > 0 && horasExistentes == 0.0) {
                horasExistentes = 0.1;
            }

            final double horasBase = horasExistentes;
            metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, metaExistente -> {
                MetaSemanal metaAGuardar;
                if (metaExistente != null) {
                    metaAGuardar = metaExistente;
                    metaAGuardar.setHorasObjetivo(horasObjetivo);
                    metaAGuardar.setHorasCumplidas(Math.max(metaExistente.getHorasCumplidas(), horasBase));
                } else {
                    metaAGuardar = new MetaSemanal(0, idMateria, numeroSemana, anio, horasObjetivo, horasBase, EstadoMeta.EN_PROGRESO);
                }

                if (metaAGuardar.getHorasCumplidas() >= horasObjetivo && horasObjetivo > 0.0) {
                    metaAGuardar.setEstado(EstadoMeta.CUMPLIDA);
                } else {
                    metaAGuardar.setEstado(EstadoMeta.EN_PROGRESO);
                }

                metaRepository.guardarOActualizar(metaAGuardar, callback::onSuccess);
            });
        });
    }

    public void acumularAvanceSesion(int idMateria, Date fechaSesion, int minutosEfectivos) {
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
            int minutosTotales = 0;
            if (sesiones != null) {
                for (SesionEstudio s : sesiones) {
                    minutosTotales += s.getDuracionMinutos();
                }
            }
            double horasTotales = Math.round((minutosTotales / 60.0) * 10.0) / 10.0;
            if (minutosTotales > 0 && horasTotales == 0.0) {
                horasTotales = 0.1;
            }

            final double horasFinales = horasTotales;
            metaRepository.obtenerPorMateriaYSemana(idMateria, numeroSemana, anio, meta -> {
                if (meta != null) {
                    meta.setHorasCumplidas(horasFinales);
                    meta.setEstado((horasFinales >= meta.getHorasObjetivo() && meta.getHorasObjetivo() > 0.0)
                            ? EstadoMeta.CUMPLIDA
                            : EstadoMeta.EN_PROGRESO);
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
                    int prev = minutosPorMateria.containsKey(s.getIdMateria()) ? minutosPorMateria.get(s.getIdMateria()) : 0;
                    minutosPorMateria.put(s.getIdMateria(), prev + s.getDuracionMinutos());
                }
            }

            metaRepository.obtenerPorSemanaYAnio(numeroSemana, anio, metas -> {
                if (metas != null && !metas.isEmpty()) {
                    for (MetaSemanal meta : metas) {
                        int mins = minutosPorMateria.containsKey(meta.getIdMateria()) ? minutosPorMateria.get(meta.getIdMateria()) : 0;
                        double horas = Math.round((mins / 60.0) * 10.0) / 10.0;
                        if (mins > 0 && horas == 0.0) {
                            horas = 0.1;
                        }
                        meta.setHorasCumplidas(horas);
                        if (horas >= meta.getHorasObjetivo() && meta.getHorasObjetivo() > 0.0) {
                            meta.setEstado(EstadoMeta.CUMPLIDA);
                        } else {
                            meta.setEstado(EstadoMeta.EN_PROGRESO);
                        }
                        metaRepository.guardarOActualizar(meta, id -> {});
                    }
                }
                if (onCompleto != null) {
                    onCompleto.run();
                }
            });
        });
    }
}
