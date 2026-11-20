package com.david.mystudio.ui.dashboard;

import static com.david.mystudio.util.DateTimeUtils.formatearMinutos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.MainActivity;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Fragmento de la Pantalla 1: Inicio (Dashboard).
 */
public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter presenter;
    private TextView txtBadgeRacha;
    private TextView txtHorasMetaGlobal;
    private LinearProgressIndicator prgMetaGlobal;
    private TextView txtPorcentajeMetaGlobal;
    private TextView txtHorasRestantesMetaGlobal;
    private RecyclerView rvAgendaHoy;
    private TextView txtAgendaVacia;
    private AgendaAdapter agendaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudyApplication app = (StudyApplication) requireActivity().getApplication();
        presenter = new DashboardPresenter(
                app.getAppContainer().getRachaService(),
                app.getAppContainer().getMetaSemanalService(),
                app.getAppContainer().getSesionEstudioService(),
                app.getAppContainer().getMateriaService()
        );
        presenter.vincularVista(this);

        txtBadgeRacha = view.findViewById(R.id.txtBadgeRacha);
        txtHorasMetaGlobal = view.findViewById(R.id.txtHorasMetaGlobal);
        prgMetaGlobal = view.findViewById(R.id.prgMetaGlobal);
        txtPorcentajeMetaGlobal = view.findViewById(R.id.txtPorcentajeMetaGlobal);
        txtHorasRestantesMetaGlobal = view.findViewById(R.id.txtHorasRestantesMetaGlobal);
        rvAgendaHoy = view.findViewById(R.id.rvAgendaHoy);
        txtAgendaVacia = view.findViewById(R.id.txtAgendaVacia);

        rvAgendaHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        agendaAdapter = new AgendaAdapter();
        rvAgendaHoy.setAdapter(agendaAdapter);

        MaterialButton btnIniciarEnfoque = view.findViewById(R.id.btnIniciarEnfoqueDashboard);
        btnIniciarEnfoque.setOnClickListener(v -> presenter.alPulsarIniciarEnfoque());

        TextView btnPlanificar = view.findViewById(R.id.btnPlanificarSesionAgenda);
        btnPlanificar.setOnClickListener(v -> presenter.alPulsarPlanificarSesion());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.cargarDatosDashboard();
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
    public void mostrarRacha(int diasConsecutivos) {
        if (txtBadgeRacha != null) {
            txtBadgeRacha.setText(getString(R.string.dashboard_racha_formato, diasConsecutivos));
        }
    }

    @Override
    public void mostrarMetaGlobal(int minutosCumplidos, int minutosObjetivo, int porcentajeAvance) {
        if (txtHorasMetaGlobal != null) {
            String textoHoras = formatearMinutos(minutosCumplidos) + " / " + formatearMinutos(minutosObjetivo);
            txtHorasMetaGlobal.setText(textoHoras);
            prgMetaGlobal.setProgress(porcentajeAvance);
            txtPorcentajeMetaGlobal.setText(porcentajeAvance + "% completado");
            txtHorasRestantesMetaGlobal.setText("Tiempo restante: " + formatearMinutos(minutosObjetivo - minutosCumplidos));
        }
    }

    @Override
    public void mostrarAgendaHoy(List<SesionEstudio> sesiones, Map<Integer, Materia> mapaMaterias) {
        if (sesiones == null || sesiones.isEmpty()) {
            rvAgendaHoy.setVisibility(View.GONE);
            txtAgendaVacia.setVisibility(View.VISIBLE);
        } else {
            rvAgendaHoy.setVisibility(View.VISIBLE);
            txtAgendaVacia.setVisibility(View.GONE);
            agendaAdapter.actualizarDatos(sesiones, mapaMaterias);
        }
    }

    @Override
    public void navegarAEnfoque() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).seleccionarPestana(R.id.menu_enfoque);
        }
    }

    @Override
    public void mostrarDialogoPlanificar(List<Materia> materiasDisponibles) {
        if (getContext() == null) return;
        if (materiasDisponibles == null || materiasDisponibles.isEmpty()) {
            Toast.makeText(getContext(), "Primero debes registrar al menos una materia", Toast.LENGTH_LONG).show();
            return;
        }

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_plan_session, null);
        Spinner spnMaterias = dialogView.findViewById(R.id.spnMateriasPlan);
        Spinner spnTecnica = dialogView.findViewById(R.id.spnTecnicaPlan);
        EditText edtDuracion = dialogView.findViewById(R.id.edtDuracionPlan);
        TextView txtFechaHora = dialogView.findViewById(R.id.txtFechaHoraSeleccionada);

        ArrayAdapter<Materia> materiaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, materiasDisponibles);
        spnMaterias.setAdapter(materiaAdapter);

        ArrayAdapter<TipoTecnica> tecnicaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, TipoTecnica.values());
        spnTecnica.setAdapter(tecnicaAdapter);

        final Calendar fechaSeleccionada = Calendar.getInstance();
        fechaSeleccionada.add(Calendar.HOUR_OF_DAY, 1);

        txtFechaHora.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                fechaSeleccionada.set(Calendar.YEAR, year);
                fechaSeleccionada.set(Calendar.MONTH, month);
                fechaSeleccionada.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog tpd = new TimePickerDialog(getContext(), (timeView, hourOfDay, minute) -> {
                    fechaSeleccionada.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    fechaSeleccionada.set(Calendar.MINUTE, minute);
                    txtFechaHora.setText(dayOfMonth + "/" + (month + 1) + "/" + year + " " + String.format("%02d:%02d", hourOfDay, minute));
                }, fechaSeleccionada.get(Calendar.HOUR_OF_DAY), fechaSeleccionada.get(Calendar.MINUTE), true);
                tpd.show();
            }, fechaSeleccionada.get(Calendar.YEAR), fechaSeleccionada.get(Calendar.MONTH), fechaSeleccionada.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });

        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Planificar Sesión a Futuro")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    Materia materiaElegida = (Materia) spnMaterias.getSelectedItem();
                    TipoTecnica tecnicaElegida = (TipoTecnica) spnTecnica.getSelectedItem();
                    String duracionStr = edtDuracion.getText().toString().trim();
                    int duracion = duracionStr.isEmpty() ? 60 : Integer.parseInt(duracionStr);

                    if (materiaElegida != null) {
                        presenter.planificarSesion(materiaElegida.getId(), fechaSeleccionada.getTime(), duracion, tecnicaElegida);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void mostrarMensajeExito(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mostrarCargando(boolean cargando) {
        // En MVP, podemos alternar un indicador de carga o shimmer
    }

    @Override
    public void mostrarError(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
