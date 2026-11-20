package com.david.mystudio.ui.enfoque;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.List;

/** Contrato MVP de la pantalla Enfoque. */
public interface EnfoqueContract {

    interface View extends BaseView {
        void mostrarMaterias(List<Materia> materias);
        void actualizarTiempo(String tiempoFormateado);
        void actualizarEstadoSesion(SesionEnfoque.Estado estado, boolean puedePausar);
        void mostrarResumenSesionCompletada(int minutos);
        void mostrarNotas(String notas);
        void seleccionarMateriaActiva(int idMateria);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarMaterias();
        void iniciarSesion(int idMateria, TipoTecnica tecnica);
        void recuperarSesion();
        void alternarPausa();
        void finalizarSesion();
        void actualizarBorrador(String notas);
        void mostrarEstado();
        void mostrarTiempo();
    }
}
