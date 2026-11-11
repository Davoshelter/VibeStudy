package com.david.mystudio.ui.materias;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Diálogo modal para registrar o modificar una materia (HU-01 y HU-02).
 */
public class AddEditMateriaDialogFragment extends DialogFragment {

    public interface OnMateriaGuardadaListener {
        void onGuardarMateria(@Nullable Materia materiaOriginal, String nombre, String sigla, String colorHex);
    }

    private Materia materiaAEditar;
    private OnMateriaGuardadaListener listener;
    private String colorSeleccionadoHex = "#1E88E5";

    public static AddEditMateriaDialogFragment nuevaInstancia(@Nullable Materia materia, OnMateriaGuardadaListener listener) {
        AddEditMateriaDialogFragment fragment = new AddEditMateriaDialogFragment();
        fragment.materiaAEditar = materia;
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_subject, null);

        TextView txtTitulo = view.findViewById(R.id.txtTituloDialogoMateria);
        TextInputEditText edtNombre = view.findViewById(R.id.edtNombreMateria);
        TextInputEditText edtSigla = view.findViewById(R.id.edtSiglaMateria);

        View swatch1 = view.findViewById(R.id.swatchColor1);
        View swatch2 = view.findViewById(R.id.swatchColor2);
        View swatch3 = view.findViewById(R.id.swatchColor3);
        View swatch4 = view.findViewById(R.id.swatchColor4);

        swatch1.setOnClickListener(v -> colorSeleccionadoHex = "#1E88E5");
        swatch2.setOnClickListener(v -> colorSeleccionadoHex = "#43A047");
        swatch3.setOnClickListener(v -> colorSeleccionadoHex = "#FB8C00");
        swatch4.setOnClickListener(v -> colorSeleccionadoHex = "#8E24AA");

        if (materiaAEditar != null) {
            txtTitulo.setText(R.string.materias_dialog_editar_titulo);
            edtNombre.setText(materiaAEditar.getNombre());
            edtSigla.setText(materiaAEditar.getSigla());
            if (materiaAEditar.getColorHex() != null) {
                colorSeleccionadoHex = materiaAEditar.getColorHex();
            }
        } else {
            txtTitulo.setText(R.string.materias_dialog_crear_titulo);
        }

        return new MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .setPositiveButton(R.string.btn_guardar, (dialog, which) -> {
                    String nombre = edtNombre.getText() != null ? edtNombre.getText().toString().trim() : "";
                    String sigla = edtSigla.getText() != null ? edtSigla.getText().toString().trim() : "";
                    if (listener != null) {
                        listener.onGuardarMateria(materiaAEditar, nombre, sigla, colorSeleccionadoHex);
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, null)
                .create();
    }
}
