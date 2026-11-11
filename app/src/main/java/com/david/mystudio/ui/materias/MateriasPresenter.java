package com.david.mystudio.ui.materias;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MateriaConEstadisticas;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.domain.service.MateriaService;
import java.util.ArrayList;
import java.util.List;

/**
 * Presentador para el módulo de Gestión de Materias (5S).
 */
public class MateriasPresenter implements MateriasContract.Presenter {

    private MateriasContract.View vista;
    private final MateriaService materiaService;
    private final SesionEstudioRepository sesionRepository;
    private final List<MateriaConEstadisticas> listaCompleta = new ArrayList<>();

    public MateriasPresenter(MateriaService materiaService, SesionEstudioRepository sesionRepository) {
        this.materiaService = materiaService;
        this.sesionRepository = sesionRepository;
    }

    @Override
    public void vincularVista(MateriasContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        this.vista = null;
    }

    @Override
    public void cargarMaterias() {
        if (vista == null) return;
        vista.mostrarCargando(true);

        materiaService.obtenerMateriasConEstadisticas(sesionRepository, new MateriaService.ResultadoCallback<List<MateriaConEstadisticas>>() {
            @Override
            public void onSuccess(List<MateriaConEstadisticas> materias) {
                listaCompleta.clear();
                if (materias != null) listaCompleta.addAll(materias);

                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMaterias(listaCompleta);
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void filtrarMaterias(String consulta) {
        if (vista == null) return;
        if (consulta == null || consulta.trim().isEmpty()) {
            vista.mostrarMaterias(listaCompleta);
            return;
        }

        String termino = consulta.trim().toLowerCase();
        List<MateriaConEstadisticas> filtradas = new ArrayList<>();
        for (MateriaConEstadisticas item : listaCompleta) {
            Materia m = item.getMateria();
            if (m.getNombre().toLowerCase().contains(termino) || m.getSigla().toLowerCase().contains(termino)) {
                filtradas.add(item);
            }
        }
        vista.mostrarMaterias(filtradas);
    }

    @Override
    public void alPulsarBotonAgregar() {
        if (vista != null) vista.mostrarDialogoCrear();
    }

    @Override
    public void alPulsarEditarMateria(Materia materia) {
        if (vista != null) vista.mostrarDialogoEditar(materia);
    }

    @Override
    public void alPulsarEliminarMateria(Materia materia) {
        if (vista != null) vista.mostrarDialogoConfirmarBaja(materia);
    }

    @Override
    public void guardarNuevaMateria(String nombre, String sigla, String colorHex) {
        if (vista == null) return;
        vista.mostrarCargando(true);

        materiaService.registrarMateria(nombre, sigla, colorHex, new MateriaService.ResultadoCallback<Long>() {
            @Override
            public void onSuccess(Long nuevoId) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMensajeExito("Materia registrada correctamente");
                    cargarMaterias();
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void actualizarMateria(int id, String nombre, String sigla, String colorHex) {
        if (vista == null) return;
        vista.mostrarCargando(true);

        materiaService.actualizarMateria(id, nombre, sigla, colorHex, new MateriaService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMensajeExito("Materia actualizada");
                    cargarMaterias();
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void confirmarBajaMateria(int idMateria) {
        if (vista == null) return;
        vista.mostrarCargando(true);

        materiaService.darDeBajaMateria(idMateria, new MateriaService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMensajeExito("Materia dada de baja");
                    cargarMaterias();
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }
}
