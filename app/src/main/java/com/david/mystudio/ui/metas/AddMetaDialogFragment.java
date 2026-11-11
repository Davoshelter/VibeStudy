package com.david.mystudio.ui.metas;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

/**
 * Diálogo modal para establecer la meta semanal de horas para una materia (HU-07).
 */
public class AddMetaDialogFragment extends DialogFragment {

    public interface OnMetaDefinidaListener {
        void onMetaDefinida(int idMateria, double horasObjetivo);
    }

    private List<Materia> materiasDisponibles;
    private OnMetaDefinidaListener listener;

    public static AddMetaDialogFragment nuevaInstancia(List<Materia> materias, OnMetaDefinidaListener listener) {
        AddMetaDialogFragment fragment = new AddMetaDialogFragment();
        fragment.materiasDisponibles = materias;
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_meta, null);

        Spinner spn = view.findViewById(R.id.spnMateriasMeta);
        TextInputEditText edtHoras = view.findViewById(R.id.edtHorasMeta);

        if (materiasDisponibles != null && !materiasDisponibles.isEmpty()) {
            ArrayAdapter<Materia> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, materiasDisponibles);
            spn.setAdapter(adapter);
        }

        return new MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .setPositiveButton(R.string.btn_guardar, (dialog, which) -> {
                    Materia seleccionada = (Materia) spn.getSelectedItem();
                    String horasStr = edtHoras.getText() != null ? edtHoras.getText().toString().trim() : "";
                    if (seleccionada != null && !horasStr.isEmpty()) {
                        try {
                            double horas = Double.parseDouble(horasStr);
                            if (listener != null) {
                                listener.onMetaDefinida(seleccionada.getId(), horas);
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Ingresa un número de horas válido", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, null)
                .create();
    }
}
