package com.david.mystudio.ui.reportes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.ReporteEstudio;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.util.DateTimeUtils;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.util.List;
import java.util.Map;

/**
 * Fragmento de la Pantalla 5: Reportes y Analítica Visual.
 */
public class ReportesFragment extends Fragment implements ReportesContract.View {

    private ReportesContract.Presenter presenter;
    private TextView txtHorasTotales;
    private TextView txtPromedioDiario;
    private TextView txtDistribucion;
    private TextView txtDedicacionDiaria;
    private RecyclerView rvHistorico;
    private TextView txtSinHistorico;
    private HistoricoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reportes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudyApplication app = (StudyApplication) requireActivity().getApplication();
        presenter = new ReportesPresenter(
                app.getAppContainer().getReporteService(),
                app.getAppContainer().getSesionEstudioService(),
                app.getAppContainer().getMateriaService()
        );
        presenter.vincularVista(this);

        txtHorasTotales = view.findViewById(R.id.txtKpiHorasTotales);
        txtPromedioDiario = view.findViewById(R.id.txtKpiPromedioDiario);
        txtDistribucion = view.findViewById(R.id.txtDetalleDistribucion);
        txtDedicacionDiaria = view.findViewById(R.id.txtResumenDedicacionDiaria);
        rvHistorico = view.findViewById(R.id.rvHistoricoSesiones);
        txtSinHistorico = view.findViewById(R.id.txtSinHistorico);

        rvHistorico.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoricoAdapter();
        rvHistorico.setAdapter(adapter);

        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.togglePeriodoReporte);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnPeriodoSemana) {
                    presenter.cargarReporteSemanal();
                } else if (checkedId == R.id.btnPeriodoMes) {
                    presenter.cargarReporteMensual();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.cargarReporteSemanal();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.desvincularVista();
        }
    }

    @Override
    public void mostrarReporte(ReporteEstudio reporte) {
        if (reporte == null) return;

        txtHorasTotales.setText(DateTimeUtils.formatearMinutos(reporte.getTotalMinutosEstudio()));
        txtPromedioDiario.setText(DateTimeUtils.formatearMinutos((int) Math.round(reporte.getPromedioMinutosDiario())) + " / día");

        // Detalle de distribución
        if (reporte.getDistribucionPorMateria().isEmpty()) {
            txtDistribucion.setText("Sin registros en este periodo.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Double> entry : reporte.getDistribucionPorMateria().entrySet()) {
                sb.append("■ ").append(entry.getKey()).append(" (").append(entry.getValue()).append("%)\n");
            }
            txtDistribucion.setText(sb.toString().trim());
        }

        // Dedicación diaria
        StringBuilder sbDias = new StringBuilder();
        String[] diasOrden = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasOrden) {
            int minutos = reporte.getMinutosPorDiaSemana().getOrDefault(dia, 0);
            sbDias.append(dia).append(": ").append(DateTimeUtils.formatearMinutos(minutos)).append(" | ");
        }
        if (sbDias.length() > 3) {
            sbDias.setLength(sbDias.length() - 3);
        }
        txtDedicacionDiaria.setText(sbDias.toString());
    }

    @Override
    public void mostrarHistorico(List<SesionEstudio> sesiones, Map<Integer, Materia> mapaMaterias) {
        if (sesiones == null || sesiones.isEmpty()) {
            rvHistorico.setVisibility(View.GONE);
            txtSinHistorico.setVisibility(View.VISIBLE);
        } else {
            rvHistorico.setVisibility(View.VISIBLE);
            txtSinHistorico.setVisibility(View.GONE);
            adapter.actualizarDatos(sesiones, mapaMaterias);
        }
    }

    @Override
    public void mostrarCargando(boolean cargando) {}

    @Override
    public void mostrarError(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
