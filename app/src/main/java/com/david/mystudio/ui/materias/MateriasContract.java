package com.david.mystudio.ui.materias;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MateriaConEstadisticas;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.List;

/**
 * Contrato MVP para la Pantalla 2: Gestión de Materias (5S).
 */
public interface MateriasContract {

    interface View extends BaseView {
        void mostrarMaterias(List<MateriaConEstadisticas> materias);
        void mostrarDialogoCrear();
        void mostrarDialogoEditar(Materia materia);
        void mostrarDialogoConfirmarBaja(Materia materia);
        void mostrarMensajeExito(String mensaje);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarMaterias();
        void filtrarMaterias(String consulta);
        void alPulsarBotonAgregar();
        void alPulsarEditarMateria(Materia materia);
        void alPulsarEliminarMateria(Materia materia);
        void guardarNuevaMateria(String nombre, String sigla, String colorHex);
        void actualizarMateria(int id, String nombre, String sigla, String colorHex);
        void confirmarBajaMateria(int idMateria);
    }
}
