package com.david.mystudio.ui.reportes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.util.DateTimeUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Adaptador para mostrar la lista cronológica del histórico de sesiones.
 */
public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {

    private final List<SesionEstudio> sesiones = new ArrayList<>();
    private final Map<Integer, Materia> mapaMaterias = new HashMap<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

    public void actualizarDatos(List<SesionEstudio> nuevasSesiones, Map<Integer, Materia> nuevasMaterias) {
        sesiones.clear();
        if (nuevasSesiones != null) sesiones.addAll(nuevasSesiones);
        mapaMaterias.clear();
        if (nuevasMaterias != null) mapaMaterias.putAll(nuevasMaterias);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico_sesion, parent, false);
        return new HistoricoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        SesionEstudio sesion = sesiones.get(position);
        Materia materia = mapaMaterias.get(sesion.getIdMateria());

        String fechaStr = sesion.getFechaHoraInicio() != null ? dateFormat.format(sesion.getFechaHoraInicio()) : "Reciente";
        String nombreMateria = materia != null ? materia.getNombre() : "Asignatura";

        String textoPrincipal = fechaStr + " • " + nombreMateria + " (" + sesion.getDuracionMinutos() + " min)";
        holder.txtDetalle.setText(textoPrincipal);

        String notas = sesion.getNotasObservaciones();
        if (notas != null && !notas.trim().isEmpty()) {
            holder.txtNotas.setText(notas);
            holder.txtNotas.setVisibility(View.VISIBLE);
        } else {
            holder.txtNotas.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return sesiones.size();
    }

    static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView txtDetalle;
        TextView txtNotas;

        HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDetalle = itemView.findViewById(R.id.txtDetalleSesionHistorico);
            txtNotas = itemView.findViewById(R.id.txtNotasHistorico);
        }
    }
}
