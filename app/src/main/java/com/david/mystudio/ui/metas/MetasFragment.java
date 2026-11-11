package com.david.mystudio.ui.metas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.david.mystudio.domain.model.MetaSemanal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.List;
import java.util.Map;

/**
 * Fragmento de la Pantalla 4: Metas Semanales (Kaizen Goals).
 */
public class MetasFragment extends Fragment implements MetasContract.View {

    private MetasContract.Presenter presenter;
    private TextView txtSemanaSelector;
    private TextView txtBadgeCompromiso;
    private TextView txtHorasCompromiso;
    private LinearProgressIndicator prgCompromiso;
    private TextView txtRitmoOptimo;
    private TextView txtConteoMetas;
    private TextView txtMetasVacias;
    private RecyclerView rvMetas;
    private MetasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_metas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudyApplication app = (StudyApplication) requireActivity().getApplication();
        presenter = new MetasPresenter(
                app.getAppContainer().getMetaSemanalService(),
                app.getAppContainer().getMateriaService()
        );
        presenter.vincularVista(this);

        txtSemanaSelector = view.findViewById(R.id.txtSemanaSelector);
        txtBadgeCompromiso = view.findViewById(R.id.txtBadgeCompromisoTotal);
        txtHorasCompromiso = view.findViewById(R.id.txtHorasCompromisoTotal);
        prgCompromiso = view.findViewById(R.id.prgCompromisoTotal);
        txtRitmoOptimo = view.findViewById(R.id.txtRitmoOptimoMetas);
        txtConteoMetas = view.findViewById(R.id.txtConteoMetas);
        txtMetasVacias = view.findViewById(R.id.txtMetasVacias);
        rvMetas = view.findViewById(R.id.rvMetas);

        rvMetas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MetasAdapter();
        rvMetas.setAdapter(adapter);

        ImageButton ibtnAnterior = view.findViewById(R.id.ibtnSemanaAnterior);
        ImageButton ibtnSiguiente = view.findViewById(R.id.ibtnSemanaSiguiente);

        ibtnAnterior.setOnClickListener(v -> presenter.cambiarSemana(-1));
        ibtnSiguiente.setOnClickListener(v -> presenter.cambiarSemana(1));

        FloatingActionButton fab = view.findViewById(R.id.fabAgregarMeta);
        fab.setOnClickListener(v -> presenter.alPulsarAgregarMeta());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.cargarMetasSemanaActual();
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
    public void mostrarSemana(String textoSemana) {
        if (txtSemanaSelector != null) {
            txtSemanaSelector.setText(textoSemana);
        }
    }

    @Override
    public void mostrarCompromisoTotal(double cumplidas, double objetivo, int porcentaje) {
        if (txtHorasCompromiso != null) {
            txtHorasCompromiso.setText(cumplidas + " / " + objetivo + " hrs");
            prgCompromiso.setProgress(porcentaje);
            txtBadgeCompromiso.setText(porcentaje + "% Completado");
            double faltan = Math.max(0.0, Math.round((objetivo - cumplidas) * 10.0) / 10.0);
            if (porcentaje >= 100) {
                txtRitmoOptimo.setText("🎉 ¡Felicitaciones! Has completado el compromiso de la semana.");
            } else {
                txtRitmoOptimo.setText("Falta completar " + faltan + " horas antes del domingo.");
            }
        }
    }

    @Override
    public void mostrarMetas(List<MetaSemanal> metas, Map<Integer, Materia> mapaMaterias) {
        if (metas == null || metas.isEmpty()) {
            rvMetas.setVisibility(View.GONE);
            txtMetasVacias.setVisibility(View.VISIBLE);
            txtConteoMetas.setText("0 Asignadas");
        } else {
            rvMetas.setVisibility(View.VISIBLE);
            txtMetasVacias.setVisibility(View.GONE);
            txtConteoMetas.setText(metas.size() + " Asignadas");
            adapter.actualizarDatos(metas, mapaMaterias);
        }
    }

    @Override
    public void mostrarDialogoFijarMeta(List<Materia> materiasDisponibles) {
        if (materiasDisponibles == null || materiasDisponibles.isEmpty()) {
            Toast.makeText(getContext(), "Primero debes registrar una materia para asignarle una meta", Toast.LENGTH_LONG).show();
            return;
        }

        AddMetaDialogFragment.nuevaInstancia(materiasDisponibles, (idMateria, horasObjetivo) -> {
            presenter.guardarMeta(idMateria, horasObjetivo);
        }).show(getParentFragmentManager(), "DialogAddMeta");
    }

    @Override
    public void mostrarMensajeExito(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
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
