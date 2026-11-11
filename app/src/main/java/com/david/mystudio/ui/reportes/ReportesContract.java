package com.david.mystudio.ui.reportes;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.ReporteEstudio;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.List;
import java.util.Map;

/**
 * Contrato MVP para la Pantalla 5: Reportes y Analítica Visual.
 */
public interface ReportesContract {

    interface View extends BaseView {
        void mostrarReporte(ReporteEstudio reporte);
        void mostrarHistorico(List<SesionEstudio> sesiones, Map<Integer, Materia> mapaMaterias);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarReporteSemanal();
        void cargarReporteMensual();
    }
}
