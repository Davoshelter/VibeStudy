package com.david.mystudio.ui.reportes;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.ReporteEstudio;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.service.MateriaService;
import com.david.mystudio.domain.service.ReporteService;
import com.david.mystudio.domain.service.SesionEstudioService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presentador para el motor de reportes y estadísticas.
 */
public class ReportesPresenter implements ReportesContract.Presenter {

    private ReportesContract.View vista;
    private final ReporteService reporteService;
    private final SesionEstudioService sesionEstudioService;
    private final MateriaService materiaService;

    public ReportesPresenter(ReporteService reporteService,
                             SesionEstudioService sesionEstudioService,
                             MateriaService materiaService) {
        this.reporteService = reporteService;
        this.sesionEstudioService = sesionEstudioService;
        this.materiaService = materiaService;
    }

    @Override
    public void vincularVista(ReportesContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        this.vista = null;
    }

    @Override
    public void cargarReporteSemanal() {
        if (vista == null) return;
        vista.mostrarCargando(true);

        reporteService.generarReporteSemanal(new Date(), reporte -> {
            if (vista != null) {
                vista.mostrarReporte(reporte);
                cargarHistoricoReciente();
            }
        });
    }

    @Override
    public void cargarReporteMensual() {
        if (vista == null) return;
        vista.mostrarCargando(true);

        reporteService.generarReporteMensual(new Date(), reporte -> {
            if (vista != null) {
                vista.mostrarReporte(reporte);
                cargarHistoricoReciente();
            }
        });
    }

    private void cargarHistoricoReciente() {
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                Map<Integer, Materia> mapa = new HashMap<>();
                if (materias != null) {
                    for (Materia m : materias) mapa.put(m.getId(), m);
                }

                sesionEstudioService.obtenerHistorialReciente(15, new SesionEstudioService.ResultadoCallback<List<SesionEstudio>>() {
                    @Override
                    public void onSuccess(List<SesionEstudio> sesiones) {
                        if (vista != null) {
                            vista.mostrarCargando(false);
                            vista.mostrarHistorico(sesiones, mapa);
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
}
