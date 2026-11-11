package com.david.mystudio.ui.enfoque;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.List;

/**
 * Contrato MVP para la Pantalla 3: Enfoque Zanshin (Temporizador).
 */
public interface EnfoqueContract {

    interface View extends BaseView {
        void mostrarMaterias(List<Materia> materias);
        void actualizarTiempo(String tiempoFormateado);
        void actualizarEstadoSesion(boolean corriendo, boolean pausado);
        void mostrarResumenSesionCompletada(String mensaje);
        void limpiarNotas();
        void seleccionarMateriaActiva(int idMateria);
        void iniciarTemporizadorVisual(int segundosIniciales);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarMaterias();
        void iniciarSesion(int idMateria, TipoTecnica tecnica);
        void alternarPausa(boolean estaPausado);
        void finalizarSesion(String notas);
        void procesarTickSegundos(int segundosTranscurridos, boolean estaPausado);
        void restaurarSesionActivaSiExiste();
        void sincronizarConServicio(int segundosTranscurridos, boolean estaPausado);
    }
}
