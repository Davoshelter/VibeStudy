package com.david.mystudio.ui.enfoque;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.domain.service.MateriaService;
import com.david.mystudio.domain.service.SesionEstudioService;
import java.util.List;

/**
 * Presentador para el temporizador de concentración Zanshin.
 */
public class EnfoquePresenter implements EnfoqueContract.Presenter {

    private static final int SEGUNDOS_POMODORO = 25 * 60;

    private EnfoqueContract.View vista;
    private final MateriaService materiaService;
    private final SesionEstudioService sesionEstudioService;

    private SesionEstudio sesionActiva;
    private int segundosActuales = 0;
    private TipoTecnica tecnicaActual = TipoTecnica.POMODORO;

    public EnfoquePresenter(MateriaService materiaService, SesionEstudioService sesionEstudioService) {
        this.materiaService = materiaService;
        this.sesionEstudioService = sesionEstudioService;
    }

    @Override
    public void vincularVista(EnfoqueContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        this.vista = null;
    }

    @Override
    public void cargarMaterias() {
        if (vista == null) return;
        vista.mostrarCargando(true);
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMaterias(materias);
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void iniciarSesion(int idMateria, TipoTecnica tecnica) {
        if (vista == null) return;
        this.tecnicaActual = tecnica != null ? tecnica : TipoTecnica.POMODORO;
        vista.mostrarCargando(true);

        sesionEstudioService.iniciarSesion(idMateria, tecnicaActual, new SesionEstudioService.ResultadoCallback<SesionEstudio>() {
            @Override
            public void onSuccess(SesionEstudio sesion) {
                sesionActiva = sesion;
                segundosActuales = 0;
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.actualizarEstadoSesion(true, false);
                    vista.iniciarTemporizadorVisual(0);
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void alternarPausa(boolean estaPausado) {
        if (vista != null) {
            vista.actualizarEstadoSesion(sesionActiva != null, estaPausado);
        }
    }

    @Override
    public void finalizarSesion(String notas) {
        if (sesionActiva == null) {
            if (vista != null) vista.mostrarError("No hay una sesión activa para finalizar");
            return;
        }

        if (vista != null) vista.mostrarCargando(true);

        int minutos = segundosActuales / 60;
        sesionEstudioService.finalizarOCancelarSesionActiva(segundosActuales, notas, new SesionEstudioService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                sesionActiva = null;
                segundosActuales = 0;
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.actualizarEstadoSesion(false, false);
                    vista.actualizarTiempo("25:00");
                    vista.limpiarNotas();
                    if (minutos >= 1) {
                        vista.mostrarResumenSesionCompletada("Sesión completada y guardada con éxito (" + minutos + " min)");
                    } else {
                        vista.mostrarResumenSesionCompletada("Sesión cancelada: tiempo inferior a 1 minuto.");
                    }
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void procesarTickSegundos(int segundos, boolean estaPausado) {
        this.segundosActuales = segundos;
        if (vista == null) return;

        int minutos;
        int segs;

        if (tecnicaActual == TipoTecnica.POMODORO) {
            int restantes = Math.max(0, SEGUNDOS_POMODORO - segundos);
            minutos = restantes / 60;
            segs = restantes % 60;
        } else {
            minutos = segundos / 60;
            segs = segundos % 60;
        }

        String tiempoFormateado = String.format("%02d:%02d", minutos, segs);
        vista.actualizarTiempo(tiempoFormateado);
        boolean haySesion = (sesionActiva != null) || StudyTimerService.isSesionEnCurso();
        vista.actualizarEstadoSesion(haySesion, estaPausado);
    }

    @Override
    public void restaurarSesionActivaSiExiste() {
        sesionEstudioService.obtenerSesionActiva(new SesionEstudioService.ResultadoCallback<SesionEstudio>() {
            @Override
            public void onSuccess(SesionEstudio sesion) {
                if (sesion != null) {
                    sesionActiva = sesion;
                    tecnicaActual = sesion.getTecnica() != null ? sesion.getTecnica() : TipoTecnica.POMODORO;
                    if (vista != null) {
                        vista.seleccionarMateriaActiva(sesion.getIdMateria());
                        boolean estaPausado = StudyTimerService.isSesionPausada();
                        vista.actualizarEstadoSesion(true, estaPausado);
                    }
                }
            }

            @Override
            public void onError(String mensajeError) {
                // Sin sesión activa previa
            }
        });
    }

    @Override
    public void sincronizarConServicio(int segundosTranscurridos, boolean estaPausado) {
        this.segundosActuales = segundosTranscurridos;
        procesarTickSegundos(segundosTranscurridos, estaPausado);
    }
}
