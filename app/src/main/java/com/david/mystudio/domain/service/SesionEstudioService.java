package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.EstadoSesion;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Date;
import java.util.List;

/**
 * Servicio de dominio que implementa las reglas de negocio de sesiones de estudio (RN-SES).
 */
public class SesionEstudioService {

    public static final int DURACION_MINIMA_PLANIFICADA = 15;
    public static final int DURACION_MAXIMA_PLANIFICADA = 360;
    public static final int DURACION_MINIMA_REGISTRO_MINUTOS = 1;

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
        void onError(String mensajeError);
    }

    private final SesionEstudioRepository sesionRepository;
    private final MetaSemanalService metaSemanalService;
    private final RachaService rachaService;

    public SesionEstudioService(SesionEstudioRepository sesionRepository,
                                MetaSemanalService metaSemanalService,
                                RachaService rachaService) {
        this.sesionRepository = sesionRepository;
        this.metaSemanalService = metaSemanalService;
        this.rachaService = rachaService;
    }

    public void planificarSesionFutura(int idMateria, Date fechaHoraProgramada, int duracionEstimadaMinutos,
                                      TipoTecnica tecnica, ResultadoCallback<Long> callback) {
        // RN-SES-01: Validación de fecha posterior a la actual
        if (fechaHoraProgramada == null || fechaHoraProgramada.before(new Date())) {
            callback.onError("La fecha y hora de la sesión debe ser posterior al horario actual");
            return;
        }

        // RN-SES-02: Rango de duración
        if (duracionEstimadaMinutos < DURACION_MINIMA_PLANIFICADA || duracionEstimadaMinutos > DURACION_MAXIMA_PLANIFICADA) {
            callback.onError("La duración debe estar comprendida entre 15 y 360 minutos");
            return;
        }

        SesionEstudio nuevaSesion = new SesionEstudio(
                0,
                idMateria,
                fechaHoraProgramada,
                null,
                null,
                duracionEstimadaMinutos,
                EstadoSesion.PENDIENTE,
                tecnica != null ? tecnica : TipoTecnica.BLOQUE_LIBRE,
                ""
        );

        sesionRepository.guardar(nuevaSesion, callback::onSuccess);
    }

    public void iniciarSesion(int idMateria, TipoTecnica tecnica, ResultadoCallback<SesionEstudio> callback) {
        // RN-SES-03: Exclusividad de sesión activa
        sesionRepository.obtenerSesionActiva(sesionActiva -> {
            if (sesionActiva != null) {
                callback.onError("Ya existe una sesión de estudio en curso");
                return;
            }

            SesionEstudio nuevaSesion = new SesionEstudio(
                    0,
                    idMateria,
                    null,
                    new Date(),
                    null,
                    0,
                    EstadoSesion.EN_CURSO,
                    tecnica != null ? tecnica : TipoTecnica.BLOQUE_LIBRE,
                    ""
            );

            sesionRepository.guardar(nuevaSesion, nuevoId -> {
                nuevaSesion.setId(nuevoId.intValue());
                callback.onSuccess(nuevaSesion);
            });
        });
    }

    public void finalizarSesion(SesionEstudio sesion, int minutosEfectivos, String notas,
                               ResultadoCallback<Void> callback) {
        // RN-SES-05: Umbral mínimo de 1 minuto
        if (minutosEfectivos < DURACION_MINIMA_REGISTRO_MINUTOS) {
            callback.onError("La duración debe ser de al menos 1 minuto para guardarse");
            return;
        }

        Date fechaFin = new Date();
        sesion.setFechaHoraFin(fechaFin);
        sesion.setDuracionMinutos(minutosEfectivos);
        sesion.setEstado(EstadoSesion.COMPLETADA);
        sesion.setNotasObservaciones(notas != null ? notas.trim() : "");

        sesionRepository.actualizar(sesion, resultado -> {
            // Actualizar meta semanal asociada si existe
            if (metaSemanalService != null) {
                metaSemanalService.acumularAvanceSesion(sesion.getIdMateria(), fechaFin, minutosEfectivos);
            }

            // Evaluar incremento de racha
            if (rachaService != null) {
                rachaService.evaluarRachaTrasSesion(fechaFin, () -> callback.onSuccess(null));
            } else {
                callback.onSuccess(null);
            }
        });
    }

    public void obtenerAgendaHoy(ResultadoCallback<List<SesionEstudio>> callback) {
        Date ahora = new Date();
        long inicioDia = DateTimeUtils.obtenerInicioDia(ahora);
        long finDia = DateTimeUtils.obtenerFinDia(ahora);

        sesionRepository.obtenerSesionesProgramadasDelDia(inicioDia, finDia, callback::onSuccess);
    }

    public void obtenerHistorialReciente(int limite, ResultadoCallback<List<SesionEstudio>> callback) {
        sesionRepository.obtenerHistorialReciente(limite, callback::onSuccess);
    }

    public void obtenerSesionActiva(ResultadoCallback<SesionEstudio> callback) {
        sesionRepository.obtenerSesionActiva(callback::onSuccess);
    }

    public void finalizarOCancelarSesionActiva(int segundosTranscurridos, String notas, ResultadoCallback<Void> callback) {
        sesionRepository.obtenerSesionActiva(sesionActiva -> {
            if (sesionActiva == null) {
                if (callback != null) callback.onSuccess(null);
                return;
            }

            int minutos = segundosTranscurridos / 60;
            if (minutos >= DURACION_MINIMA_REGISTRO_MINUTOS) {
                finalizarSesion(sesionActiva, minutos, notas, callback);
            } else {
                sesionActiva.setEstado(EstadoSesion.CANCELADA);
                sesionActiva.setFechaHoraFin(new Date());
                sesionActiva.setNotasObservaciones("Sesión cancelada: tiempo inferior a 1 minuto.");
                sesionRepository.actualizar(sesionActiva, res -> {
                    if (callback != null) callback.onSuccess(null);
                });
            }
        });
    }

    public void sanearSesionesHuerfanas(ResultadoCallback<Integer> callback) {
        sesionRepository.sanearSesionesHuerfanas("Sesión cerrada tras reinicio o apagado del dispositivo", resultado -> {
            if (callback != null) {
                callback.onSuccess(resultado != null ? resultado : 0);
            }
        });
    }
}
