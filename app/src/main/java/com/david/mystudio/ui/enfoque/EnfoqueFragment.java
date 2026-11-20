package com.david.mystudio.ui.enfoque;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.Fragment;
import com.david.mystudio.AppContainer;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.TipoTecnica;
import com.google.android.material.button.MaterialButton;
import java.util.List;

/** Pantalla Enfoque Zanshin. */
public class EnfoqueFragment extends Fragment implements EnfoqueContract.View {

    private EnfoqueContract.Presenter presenter;
    private Spinner spnAsignatura;
    private TextView txtTiempo;
    private TextView txtEstado;
    private MaterialButton btnPausarReanudar;
    private MaterialButton btnFinalizar;
    private MaterialButton btnIniciar;
    private EditText edtNotas;
    private final TextWatcher notasWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            presenter.actualizarBorrador(s.toString());
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enfoque, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppContainer container = ((StudyApplication) requireActivity().getApplication()).getAppContainer();
        SesionEnfoque sesionEnfoque = container.getSesionEnfoque();
        presenter = new EnfoquePresenter(container.getMateriaService(), sesionEnfoque);
        presenter.vincularVista(this);

        spnAsignatura = view.findViewById(R.id.spnAsignaturaEnfoque);
        txtTiempo = view.findViewById(R.id.txtTiempoCronometro);
        txtEstado = view.findViewById(R.id.txtEstadoCronometro);
        btnPausarReanudar = view.findViewById(R.id.btnPausarReanudarEnfoque);
        btnFinalizar = view.findViewById(R.id.btnFinalizarEnfoque);
        btnIniciar = view.findViewById(R.id.btnIniciarSesionEnfoque);
        edtNotas = view.findViewById(R.id.edtNotasSesion);
        edtNotas.setSaveEnabled(false); // El borrador acompaña la sesión al recrear la vista.

        btnIniciar.setOnClickListener(v -> {
            if (sesionEnfoque.getEstado() == SesionEnfoque.Estado.RECUPERABLE) {
                presenter.recuperarSesion();
                return;
            }
            Materia materia = (Materia) spnAsignatura.getSelectedItem();
            if (materia == null) {
                Toast.makeText(requireContext(), R.string.enfoque_sin_materia_alerta, Toast.LENGTH_SHORT).show();
                return;
            }
            presenter.iniciarSesion(materia.getId(), TipoTecnica.POMODORO);
        });
        btnPausarReanudar.setOnClickListener(v -> presenter.alternarPausa());
        btnFinalizar.setOnClickListener(v -> presenter.finalizarSesion());
        edtNotas.addTextChangedListener(notasWatcher);

        sesionEnfoque.observarEstado().observe(getViewLifecycleOwner(), estado -> presenter.mostrarEstado());
        sesionEnfoque.observarSegundos().observe(getViewLifecycleOwner(), segundos -> presenter.mostrarTiempo());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.cargarMaterias();
    }

    @Override
    public void onDestroyView() {
        edtNotas.removeTextChangedListener(notasWatcher);
        presenter.desvincularVista();
        spnAsignatura = null;
        txtTiempo = null;
        txtEstado = null;
        btnPausarReanudar = null;
        btnFinalizar = null;
        btnIniciar = null;
        edtNotas = null;
        super.onDestroyView();
    }

    @Override
    public void mostrarMaterias(List<Materia> materias) {
        ArrayAdapter<Materia> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, materias);
        spnAsignatura.setAdapter(adapter);
    }

    @Override
    public void seleccionarMateriaActiva(int idMateria) {
        if (spnAsignatura.getAdapter() == null) return;
        for (int i = 0; i < spnAsignatura.getAdapter().getCount(); i++) {
            Materia materia = (Materia) spnAsignatura.getAdapter().getItem(i);
            if (materia.getId() == idMateria) {
                spnAsignatura.setSelection(i);
                return;
            }
        }
    }

    @Override
    public void actualizarTiempo(String tiempoFormateado) {
        txtTiempo.setText(tiempoFormateado);
    }

    @Override
    public void actualizarEstadoSesion(SesionEnfoque.Estado estado, boolean puedePausar) {
        boolean inactiva = estado == SesionEnfoque.Estado.INACTIVA;
        boolean recuperable = estado == SesionEnfoque.Estado.RECUPERABLE;
        boolean iniciando = estado == SesionEnfoque.Estado.INICIANDO;
        boolean guardando = estado == SesionEnfoque.Estado.FINALIZANDO;
        boolean pausada = estado == SesionEnfoque.Estado.PAUSADA;
        boolean mostrarInicio = inactiva || recuperable || iniciando;
        btnIniciar.setVisibility(mostrarInicio ? View.VISIBLE : View.GONE);
        btnIniciar.setEnabled(!iniciando);
        btnIniciar.setText(recuperable ? R.string.enfoque_btn_recuperar : R.string.enfoque_btn_iniciar);
        btnPausarReanudar.setVisibility(mostrarInicio ? View.GONE : View.VISIBLE);
        btnPausarReanudar.setEnabled(puedePausar);
        btnPausarReanudar.setText(pausada ? R.string.enfoque_btn_reanudar : R.string.enfoque_btn_pausar);
        btnPausarReanudar.setIconResource(pausada ? R.drawable.ic_play_arrow : R.drawable.ic_pause);
        btnFinalizar.setVisibility(mostrarInicio ? View.GONE : View.VISIBLE);
        btnFinalizar.setEnabled(!guardando);
        spnAsignatura.setEnabled(inactiva);
        edtNotas.setEnabled(!guardando);

        switch (estado) {
            case INACTIVA: txtEstado.setText(R.string.enfoque_estado_inactivo); break;
            case RECUPERABLE: txtEstado.setText(R.string.enfoque_estado_recuperable); break;
            case INICIANDO: txtEstado.setText(R.string.enfoque_estado_iniciando); break;
            case ACTIVA: txtEstado.setText(R.string.enfoque_estado_activo); break;
            case PAUSADA: txtEstado.setText(R.string.enfoque_estado_pausado); break;
            case FINALIZANDO: txtEstado.setText(R.string.enfoque_estado_guardando); break;
        }
    }

    @Override
    public void mostrarResumenSesionCompletada(int minutos) {
        String mensaje = minutos > 0 ? getString(R.string.enfoque_resumen_guardado, minutos)
                : getString(R.string.enfoque_resumen_cancelado);
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostrarNotas(String notas) {
        if (!notas.contentEquals(edtNotas.getText())) edtNotas.setText(notas);
    }

    @Override
    public void mostrarCargando(boolean cargando) {}

    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}
