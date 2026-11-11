package com.david.mystudio.ui.dashboard;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.domain.service.MateriaService;
import com.david.mystudio.domain.service.MetaSemanalService;
import com.david.mystudio.domain.service.RachaService;
import com.david.mystudio.domain.service.SesionEstudioService;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presentador para el Dashboard principal.
 */
public class DashboardPresenter implements DashboardContract.Presenter {

    private DashboardContract.View vista;
    private final RachaService rachaService;
    private final MetaSemanalService metaSemanalService;
    private final SesionEstudioService sesionEstudioService;
    private final MateriaService materiaService;

    public DashboardPresenter(RachaService rachaService,
                              MetaSemanalService metaSemanalService,
                              SesionEstudioService sesionEstudioService,
                              MateriaService materiaService) {
        this.rachaService = rachaService;
        this.metaSemanalService = metaSemanalService;
        this.sesionEstudioService = sesionEstudioService;
        this.materiaService = materiaService;
    }

    @Override
    public void vincularVista(DashboardContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        this.vista = null;
    }

    @Override
    public void cargarDatosDashboard() {
        if (vista == null) return;
        vista.mostrarCargando(true);

        // 1. Cargar Racha Kaizen
        rachaService.obtenerRachaActual(racha -> {
            if (vista != null) {
                vista.mostrarRacha(racha != null ? racha.getDiasConsecutivos() : 0);
            }
        });

        // 2. Cargar Meta Semanal Global
        Date hoy = new Date();
        int semanaActual = DateTimeUtils.obtenerNumeroSemana(hoy);
        int anioActual = DateTimeUtils.obtenerAnio(hoy);

        metaSemanalService.obtenerMetasSemana(semanaActual, anioActual, new MetaSemanalService.ResultadoCallback<List<MetaSemanal>>() {
            @Override
            public void onSuccess(List<MetaSemanal> metas) {
                double totalObjetivo = 0.0;
                double totalCumplidas = 0.0;
                if (metas != null) {
                    for (MetaSemanal m : metas) {
                        totalObjetivo += m.getHorasObjetivo();
                        totalCumplidas += m.getHorasCumplidas();
                    }
                }
                int porcentaje = totalObjetivo > 0.0
                        ? (int) Math.min(100.0, Math.round((totalCumplidas / totalObjetivo) * 100.0))
                        : 0;

                if (vista != null) {
                    vista.mostrarMetaGlobal(totalCumplidas, totalObjetivo, porcentaje);
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) vista.mostrarError(mensajeError);
            }
        });

        // 3. Cargar Agenda de Hoy y Materias para nombres/colores
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                Map<Integer, Materia> mapa = new HashMap<>();
                if (materias != null) {
                    for (Materia m : materias) mapa.put(m.getId(), m);
                }

                sesionEstudioService.obtenerAgendaHoy(new SesionEstudioService.ResultadoCallback<List<com.david.mystudio.domain.model.SesionEstudio>>() {
                    @Override
                    public void onSuccess(List<com.david.mystudio.domain.model.SesionEstudio> sesiones) {
                        if (vista != null) {
                            vista.mostrarCargando(false);
                            vista.mostrarAgendaHoy(sesiones, mapa);
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
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void alPulsarIniciarEnfoque() {
        if (vista != null) vista.navegarAEnfoque();
    }

    @Override
    public void alPulsarPlanificarSesion() {
        if (vista == null) return;
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                if (vista != null) vista.mostrarDialogoPlanificar(materias);
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) vista.mostrarError(mensajeError);
            }
        });
    }

    @Override
    public void planificarSesion(int idMateria, Date fechaHora, int duracionMinutos, TipoTecnica tecnica) {
        if (vista == null) return;
        vista.mostrarCargando(true);
        sesionEstudioService.planificarSesionFutura(idMateria, fechaHora, duracionMinutos, tecnica,
                new SesionEstudioService.ResultadoCallback<Long>() {
                    @Override
                    public void onSuccess(Long nuevoId) {
                        if (vista != null) {
                            vista.mostrarCargando(false);
                            vista.mostrarMensajeExito("Sesión planificada exitosamente");
                            cargarDatosDashboard();
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
}
