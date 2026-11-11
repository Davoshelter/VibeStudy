package com.david.mystudio.ui.materias;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.david.mystudio.domain.model.MateriaConEstadisticas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * Fragmento de la Pantalla 2: Gestión de Materias (5S).
 */
public class MateriasFragment extends Fragment implements MateriasContract.View, MateriasAdapter.OnMateriaAccionListener {

    private MateriasContract.Presenter presenter;
    private RecyclerView rvMaterias;
    private MateriasAdapter adapter;
    private LinearLayout layoutEstadoVacio;
    private TextView txtConteoMaterias;
    private EditText edtBuscar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudyApplication app = (StudyApplication) requireActivity().getApplication();
        presenter = new MateriasPresenter(
                app.getAppContainer().getMateriaService(),
                app.getAppContainer().getSesionEstudioRepository()
        );
        presenter.vincularVista(this);

        rvMaterias = view.findViewById(R.id.rvMaterias);
        layoutEstadoVacio = view.findViewById(R.id.layoutEstadoVacioMaterias);
        txtConteoMaterias = view.findViewById(R.id.txtConteoMaterias);
        edtBuscar = view.findViewById(R.id.edtBuscarMateria);

        rvMaterias.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MateriasAdapter(this);
        rvMaterias.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAgregarMateria);
        fab.setOnClickListener(v -> presenter.alPulsarBotonAgregar());

        view.findViewById(R.id.btnRegistrarPrimeraMateria).setOnClickListener(v -> presenter.alPulsarBotonAgregar());

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.filtrarMaterias(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.cargarMaterias();
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
    public void mostrarMaterias(List<MateriaConEstadisticas> materias) {
        if (materias == null || materias.isEmpty()) {
            rvMaterias.setVisibility(View.GONE);
            layoutEstadoVacio.setVisibility(View.VISIBLE);
            txtConteoMaterias.setText(getString(R.string.materias_conteo_formato, 0));
        } else {
            rvMaterias.setVisibility(View.VISIBLE);
            layoutEstadoVacio.setVisibility(View.GONE);
            txtConteoMaterias.setText(getString(R.string.materias_conteo_formato, materias.size()));
            adapter.actualizarLista(materias);
        }
    }

    @Override
    public void mostrarDialogoCrear() {
        AddEditMateriaDialogFragment.nuevaInstancia(null, (materiaOriginal, nombre, sigla, colorHex) -> {
            presenter.guardarNuevaMateria(nombre, sigla, colorHex);
        }).show(getParentFragmentManager(), "DialogCrearMateria");
    }

    @Override
    public void mostrarDialogoEditar(Materia materia) {
        AddEditMateriaDialogFragment.nuevaInstancia(materia, (materiaOriginal, nombre, sigla, colorHex) -> {
            presenter.actualizarMateria(materia.getId(), nombre, sigla, colorHex);
        }).show(getParentFragmentManager(), "DialogEditarMateria");
    }

    @Override
    public void mostrarDialogoConfirmarBaja(Materia materia) {
        if (getContext() == null) return;
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(R.string.materias_confirmar_eliminar_titulo)
                .setMessage(getString(R.string.materias_confirmar_eliminar_mensaje))
                .setPositiveButton(R.string.btn_eliminar, (dialog, which) -> {
                    presenter.confirmarBajaMateria(materia.getId());
                })
                .setNegativeButton(R.string.btn_cancelar, null)
                .show();
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

    @Override
    public void onEditar(Materia materia) {
        presenter.alPulsarEditarMateria(materia);
    }

    @Override
    public void onEliminar(Materia materia) {
        presenter.alPulsarEliminarMateria(materia);
    }
}
