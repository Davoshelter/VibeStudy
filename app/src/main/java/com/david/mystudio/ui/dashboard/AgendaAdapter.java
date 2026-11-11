package com.david.mystudio.ui.dashboard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.util.ColorUtils;
import com.david.mystudio.util.DateTimeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adaptador para mostrar la agenda de sesiones del día en el Dashboard.
 */
public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

    private final List<SesionEstudio> sesiones = new ArrayList<>();
    private final Map<Integer, Materia> mapaMaterias = new HashMap<>();

    public void actualizarDatos(List<SesionEstudio> nuevasSesiones, Map<Integer, Materia> nuevasMaterias) {
        sesiones.clear();
        if (nuevasSesiones != null) sesiones.addAll(nuevasSesiones);
        mapaMaterias.clear();
        if (nuevasMaterias != null) mapaMaterias.putAll(nuevasMaterias);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda_sesion, parent, false);
        return new AgendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        SesionEstudio sesion = sesiones.get(position);
        Materia materia = mapaMaterias.get(sesion.getIdMateria());

        String nombreMateria = materia != null ? materia.getNombre() : "Asignatura";
        String colorHex = materia != null ? materia.getColorHex() : ColorUtils.COLOR_POR_DEFECTO_HEX;

        holder.txtNombreMateria.setText(nombreMateria);

        int colorEntero = ColorUtils.parseColorConFallback(colorHex, Color.BLUE);
        holder.viewSwatch.setBackgroundTintList(ColorStateList.valueOf(colorEntero));

        String horaInicio = DateTimeUtils.formatearHora(sesion.getFechaProgramada());
        String tecnicaStr = sesion.getTecnica() != null ? sesion.getTecnica().name() : "";
        String detalle = horaInicio + " (" + sesion.getDuracionMinutos() + " min) • " + tecnicaStr;
        holder.txtDetalleHorario.setText(detalle);

        String estadoTexto = sesion.getEstado() != null ? sesion.getEstado().name() : "PENDIENTE";
        holder.txtChipEstado.setText(estadoTexto);
    }

    @Override
    public int getItemCount() {
        return sesiones.size();
    }

    static class AgendaViewHolder extends RecyclerView.ViewHolder {
        View viewSwatch;
        TextView txtNombreMateria;
        TextView txtDetalleHorario;
        TextView txtChipEstado;

        AgendaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewSwatch = itemView.findViewById(R.id.viewSwatchAgenda);
            txtNombreMateria = itemView.findViewById(R.id.txtNombreMateriaAgenda);
            txtDetalleHorario = itemView.findViewById(R.id.txtDetalleHorarioAgenda);
            txtChipEstado = itemView.findViewById(R.id.txtChipEstadoAgenda);
        }
    }
}
