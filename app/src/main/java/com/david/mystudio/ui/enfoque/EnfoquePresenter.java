package com.david.mystudio.ui.enfoque;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.domain.service.MateriaService;
import java.util.List;
import java.util.Locale;

/** Presenta el estado de la sesión y delega sus transiciones a SesionEnfoque. */
public class EnfoquePresenter implements EnfoqueContract.Presenter {

    private static final int SEGUNDOS_POMODORO = 25 * 60;
    private final MateriaService materiaService;
    private final SesionEnfoque enfoque;
    private EnfoqueContract.View vista;

    public EnfoquePresenter(MateriaService materiaService, SesionEnfoque enfoque) {
        this.materiaService = materiaService;
        this.enfoque = enfoque;
    }

    @Override
    public void vincularVista(EnfoqueContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        vista = null;
    }

    @Override
    public void cargarMaterias() {
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                if (vista != null) {
                    vista.mostrarMaterias(materias);
                    seleccionarMateriaActiva();
                }
            }

            @Override
            public void onError(String mensaje) {
                mostrarError(mensaje);
            }
        });
    }

    @Override
    public void iniciarSesion(int idMateria, TipoTecnica tecnica) {
        enfoque.iniciar(idMateria, tecnica, this::mostrarError);
    }

    @Override
    public void recuperarSesion() {
        enfoque.recuperar(this::mostrarError);
    }

    @Override
    public void alternarPausa() {
        enfoque.alternarPausa(this::mostrarError);
    }

    @Override
    public void finalizarSesion() {
        enfoque.finalizar(minutos -> {
            if (vista != null) vista.mostrarResumenSesionCompletada(minutos);
        }, this::mostrarError);
    }

    @Override
    public void actualizarBorrador(String notas) {
        enfoque.guardarBorrador(notas);
    }

    @Override
    public void mostrarEstado() {
        if (vista == null) return;
        vista.actualizarEstadoSesion(enfoque.getEstado(), enfoque.puedeAlternarPausa());
        vista.mostrarNotas(enfoque.getNotas());
        seleccionarMateriaActiva();
        mostrarTiempo();
    }

    @Override
    public void mostrarTiempo() {
        if (vista == null) return;
        SesionEstudio sesion = enfoque.getSesion();
        int segundos = enfoque.getSegundos();
        if (sesion == null || sesion.getTecnica() == TipoTecnica.POMODORO) {
            segundos = Math.max(0, SEGUNDOS_POMODORO - segundos);
        }
        vista.actualizarTiempo(String.format(Locale.getDefault(), "%02d:%02d", segundos / 60, segundos % 60));
    }

    private void seleccionarMateriaActiva() {
        SesionEstudio sesion = enfoque.getSesion();
        if (sesion != null) vista.seleccionarMateriaActiva(sesion.getIdMateria());
    }

    private void mostrarError(String mensaje) {
        if (vista != null) vista.mostrarError(mensaje);
    }
}
