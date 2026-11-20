package com.david.mystudio.ui.dashboard;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.ui.base.BasePresenter;
import com.david.mystudio.ui.base.BaseView;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Contrato MVP para la Pantalla 1: Inicio (Dashboard).
 */
public interface DashboardContract {

    interface View extends BaseView {
        void mostrarRacha(int diasConsecutivos);
        void mostrarMetaGlobal(int minutosCumplidos, int minutosObjetivo, int porcentajeAvance);
        void mostrarAgendaHoy(List<SesionEstudio> sesiones, Map<Integer, Materia> mapaMaterias);
        void navegarAEnfoque();
        void mostrarDialogoPlanificar(List<Materia> materiasDisponibles);
        void mostrarMensajeExito(String mensaje);
    }

    interface Presenter extends BasePresenter<View> {
        void cargarDatosDashboard();
        void alPulsarIniciarEnfoque();
        void alPulsarPlanificarSesion();
        void planificarSesion(int idMateria, Date fechaHora, int duracionMinutos, TipoTecnica tecnica);
    }
}
