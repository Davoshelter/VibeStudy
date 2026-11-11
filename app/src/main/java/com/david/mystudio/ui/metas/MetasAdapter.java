package com.david.mystudio.ui.metas;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.util.ColorUtils;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adaptador para mostrar la lista de metas por asignatura.
 */
public class MetasAdapter extends RecyclerView.Adapter<MetasAdapter.MetaViewHolder> {

    private final List<MetaSemanal> listaMetas = new ArrayList<>();
    private final Map<Integer, Materia> mapaMaterias = new HashMap<>();

    public void actualizarDatos(List<MetaSemanal> nuevasMetas, Map<Integer, Materia> nuevasMaterias) {
        listaMetas.clear();
        if (nuevasMetas != null) listaMetas.addAll(nuevasMetas);
        mapaMaterias.clear();
        if (nuevasMaterias != null) mapaMaterias.putAll(nuevasMaterias);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta, parent, false);
        return new MetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        MetaSemanal meta = listaMetas.get(position);
        Materia materia = mapaMaterias.get(meta.getIdMateria());

        String nombreMateria = materia != null ? materia.getNombre() : "Asignatura";
        String colorHex = materia != null ? materia.getColorHex() : ColorUtils.COLOR_POR_DEFECTO_HEX;

        holder.txtNombre.setText(nombreMateria);

        int colorEntero = ColorUtils.parseColorConFallback(colorHex, Color.BLUE);
        holder.viewSwatch.setBackgroundTintList(ColorStateList.valueOf(colorEntero));

        int porcentaje = (int) meta.calcularPorcentajeAvance();
        holder.prgItem.setProgress(porcentaje);

        if (meta.estaCumplida()) {
            // Variante Cumplida 100% (Verde)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_success_container));
            holder.txtChipProgreso.setText(R.string.metas_chip_cumplida);
            holder.txtChipProgreso.setTextColor(ContextCompat.getColor(context, R.color.color_success));
            holder.txtChipProgreso.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            holder.prgItem.setIndicatorColor(ContextCompat.getColor(context, R.color.color_success));
            holder.txtPorcentaje.setText(meta.getHorasCumplidas() + " / " + meta.getHorasObjetivo() + " hrs completadas");
            holder.txtPorcentaje.setTextColor(ContextCompat.getColor(context, R.color.color_success));
            holder.txtDetalleFaltante.setText(R.string.metas_felicitacion);
            holder.txtDetalleFaltante.setTextColor(ContextCompat.getColor(context, R.color.color_success));
        } else if (porcentaje < 30) {
            // Variante Rezagada (Alerta / Ámbar-Rojo)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_error_container));
            holder.txtChipProgreso.setText(meta.getHorasCumplidas() + " / " + meta.getHorasObjetivo() + " hrs");
            holder.txtChipProgreso.setTextColor(ContextCompat.getColor(context, R.color.color_error));
            holder.txtChipProgreso.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            holder.prgItem.setIndicatorColor(ContextCompat.getColor(context, R.color.color_error));
            holder.txtPorcentaje.setText(porcentaje + "% logrado");
            holder.txtPorcentaje.setTextColor(ContextCompat.getColor(context, R.color.color_error));
            double faltan = Math.max(0.0, Math.round((meta.getHorasObjetivo() - meta.getHorasCumplidas()) * 10.0) / 10.0);
            holder.txtDetalleFaltante.setText("⚠️ Asignatura rezagada: faltan " + faltan + "h");
            holder.txtDetalleFaltante.setTextColor(ContextCompat.getColor(context, R.color.color_error));
        } else {
            // Variante En Progreso Normal
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_surface));
            holder.txtChipProgreso.setText(meta.getHorasCumplidas() + " / " + meta.getHorasObjetivo() + " hrs");
            holder.txtChipProgreso.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
            holder.txtChipProgreso.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_surface_variant)));
            holder.prgItem.setIndicatorColor(ContextCompat.getColor(context, R.color.color_primary));
            holder.txtPorcentaje.setText(porcentaje + "% logrado");
            holder.txtPorcentaje.setTextColor(ContextCompat.getColor(context, R.color.color_on_surface));
            double faltan = Math.max(0.0, Math.round((meta.getHorasObjetivo() - meta.getHorasCumplidas()) * 10.0) / 10.0);
            holder.txtDetalleFaltante.setText("Faltan " + faltan + " hrs");
            holder.txtDetalleFaltante.setTextColor(ContextCompat.getColor(context, R.color.color_on_surface_variant));
        }
    }

    @Override
    public int getItemCount() {
        return listaMetas.size();
    }

    static class MetaViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        View viewSwatch;
        TextView txtNombre;
        TextView txtChipProgreso;
        LinearProgressIndicator prgItem;
        TextView txtPorcentaje;
        TextView txtDetalleFaltante;

        MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.crdMetaItem);
            viewSwatch = itemView.findViewById(R.id.viewSwatchMeta);
            txtNombre = itemView.findViewById(R.id.txtNombreMateriaMeta);
            txtChipProgreso = itemView.findViewById(R.id.txtChipProgresoMeta);
            prgItem = itemView.findViewById(R.id.prgMetaItem);
            txtPorcentaje = itemView.findViewById(R.id.txtPorcentajeMetaItem);
            txtDetalleFaltante = itemView.findViewById(R.id.txtDetalleFaltanteMeta);
        }
    }
}
