package com.david.mystudio.ui.metas;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.List;
import java.util.Map;

/**
 * Contrato MVP para la Pantalla 4: Metas Semanales (Kaizen Goals).
 */
public interface MetasContract {

    interface View extends BaseView {
        void mostrarSemana(String textoSemana);
        void mostrarCompromisoTotal(double cumplidas, double objetivo, int porcentaje);
        void mostrarMetas(List<MetaSemanal> metas, Map<Integer, Materia> mapaMaterias);
        void mostrarDialogoFijarMeta(List<Materia> materiasDisponibles);
        void mostrarMensajeExito(String mensaje);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarMetasSemanaActual();
        void cambiarSemana(int desplazamientoSemanas);
        void alPulsarAgregarMeta();
        void guardarMeta(int idMateria, double horasObjetivo);
    }
}
