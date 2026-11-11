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
            vista.actualizarEstadoSesion(sesionActiva != null, !estaPausado);
        }
    }

    @Override
    public void finalizarSesion(String notas) {
        if (sesionActiva == null) {
            if (vista != null) vista.mostrarError("No hay una sesión activa para finalizar");
            return;
        }

        int minutosEfectivos = Math.max(1, segundosActuales / 60);

        if (vista != null) vista.mostrarCargando(true);
        sesionEstudioService.finalizarSesion(sesionActiva, minutosEfectivos, notas, new SesionEstudioService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                sesionActiva = null;
                segundosActuales = 0;
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.actualizarEstadoSesion(false, false);
                    vista.actualizarTiempo("25:00");
                    vista.limpiarNotas();
                    vista.mostrarResumenSesionCompletada("Sesión completada y guardada con éxito (" + minutosEfectivos + " min)");
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
        vista.actualizarEstadoSesion(sesionActiva != null, estaPausado);
    }
}
