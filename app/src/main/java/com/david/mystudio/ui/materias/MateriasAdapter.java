package com.david.mystudio.ui.materias;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MateriaConEstadisticas;
import com.david.mystudio.util.ColorUtils;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de RecyclerView para el catálogo de materias.
 */
public class MateriasAdapter extends RecyclerView.Adapter<MateriasAdapter.MateriaViewHolder> {

    public interface OnMateriaAccionListener {
        void onEditar(Materia materia);
        void onEliminar(Materia materia);
    }

    private final List<MateriaConEstadisticas> listaMaterias = new ArrayList<>();
    private final OnMateriaAccionListener listener;

    public MateriasAdapter(OnMateriaAccionListener listener) {
        this.listener = listener;
    }

    public void actualizarLista(List<MateriaConEstadisticas> nuevasMaterias) {
        listaMaterias.clear();
        if (nuevasMaterias != null) listaMaterias.addAll(nuevasMaterias);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia, parent, false);
        return new MateriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        MateriaConEstadisticas item = listaMaterias.get(position);
        Materia materia = item.getMateria();

        holder.txtNombre.setText(materia.getNombre());

        String detalle = materia.getSigla() + " • " + item.getTotalSesiones() + " sesiones • " + item.getTotalHoras() + " hrs";
        holder.txtDetalle.setText(detalle);

        int colorEntero = ColorUtils.parseColorConFallback(materia.getColorHex(), Color.BLUE);
        holder.viewSwatch.setBackgroundTintList(ColorStateList.valueOf(colorEntero));

        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) listener.onEditar(materia);
        });

        holder.ibtnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onEliminar(materia);
        });
    }

    @Override
    public int getItemCount() {
        return listaMaterias.size();
    }

    static class MateriaViewHolder extends RecyclerView.ViewHolder {
        View viewSwatch;
        TextView txtNombre;
        TextView txtDetalle;
        MaterialButton btnEditar;
        ImageButton ibtnEliminar;

        MateriaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewSwatch = itemView.findViewById(R.id.viewSwatchColor);
            txtNombre = itemView.findViewById(R.id.txtNombreMateria);
            txtDetalle = itemView.findViewById(R.id.txtDetalleMateria);
            btnEditar = itemView.findViewById(R.id.btnEditarMateria);
            ibtnEliminar = itemView.findViewById(R.id.ibtnEliminarMateria);
        }
    }
}
