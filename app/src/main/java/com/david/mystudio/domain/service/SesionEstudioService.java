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

        sesionRepository.guardar(nuevaSesion, callback::onSuccess,
                error -> callback.onError("No se pudo guardar la sesión programada"));
    }

    public void iniciarSesion(int idMateria, TipoTecnica tecnica, ResultadoCallback<SesionEstudio> callback) {
        SesionEstudio nuevaSesion = new SesionEstudio(0, idMateria, null, new Date(), null, 0,
                EstadoSesion.EN_CURSO, tecnica, "");
        // RN-SES-03: Room verifica e inserta dentro de la misma transacción.
        sesionRepository.guardarSiNoHaySesionActiva(nuevaSesion, nuevoId -> {
            if (nuevoId == 0) {
                callback.onError("Ya existe una sesión de estudio en curso");
                return;
            }
            nuevaSesion.setId(nuevoId.intValue());
            callback.onSuccess(nuevaSesion);
        }, error -> callback.onError("No se pudo iniciar la sesión de estudio"));
    }

    public void cerrarSesion(SesionEstudio sesion, int segundosEfectivos, String notas,
                             ResultadoCallback<Integer> callback) {
        int minutos = Math.max(0, segundosEfectivos) / 60;
        boolean completada = minutos >= DURACION_MINIMA_REGISTRO_MINUTOS;
        Date fechaFin = new Date();
        String observaciones = completada ? (notas == null ? "" : notas.trim())
                : "Sesión cancelada: tiempo inferior a 1 minuto.";
        SesionEstudio cierre = new SesionEstudio(sesion.getId(), sesion.getIdMateria(),
                sesion.getFechaProgramada(), sesion.getFechaHoraInicio(), fechaFin, minutos,
                completada ? EstadoSesion.COMPLETADA : EstadoSesion.CANCELADA, sesion.getTecnica(), observaciones);

        sesionRepository.cerrar(cierre, filas -> {
            if (filas > 0 && completada) {
                if (metaSemanalService != null) {
                    // Las consultas semanales clasifican las sesiones por fecha de inicio.
                    metaSemanalService.acumularAvanceSesion(sesion.getIdMateria(), sesion.getFechaHoraInicio());
                }
                if (rachaService != null) {
                    rachaService.evaluarRachaTrasSesion(fechaFin, () -> callback.onSuccess(minutos));
                    return;
                }
            }
            callback.onSuccess(minutos);
        }, error -> callback.onError("No se pudo guardar el cierre. Intenta finalizar de nuevo."));
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

    public void buscarSesionRecuperable(int id, ResultadoCallback<SesionEstudio> callback) {
        sesionRepository.buscarPorId(id, sesion -> callback.onSuccess(
                sesion != null && sesion.getEstado() == EstadoSesion.EN_CURSO ? sesion : null),
                error -> callback.onError("No se pudo recuperar la sesión de estudio."));
    }

    public void sanearSesionesHuerfanas(int idConCronometro, ResultadoCallback<Integer> callback) {
        sesionRepository.sanearSesionesHuerfanas(idConCronometro, "Sesión interrumpida sin un cronómetro guardado", resultado -> {
            if (callback != null) {
                callback.onSuccess(resultado);
            }
        }, error -> {
            if (callback != null) callback.onError("No se pudieron cerrar las sesiones anteriores");
        });
    }
}
