package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MateriaConEstadisticas;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.repository.MateriaRepository;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.ColorUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio de dominio que implementa las reglas de negocio de materias (RN-MAT).
 */
public class MateriaService {

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
        void onError(String mensajeError);
    }

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public void registrarMateria(String nombre, String sigla, String colorHex, ResultadoCallback<Long> callback) {
        // RN-MAT-01: Obligatoriedad del nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            callback.onError("El nombre de la materia es obligatorio");
            return;
        }

        // RN-MAT-04: Color distintivo válido
        String colorFinal = ColorUtils.esColorHexValido(colorHex) ? colorHex : ColorUtils.COLOR_POR_DEFECTO_HEX;

        // RN-MAT-02: Unicidad de nombre
        materiaRepository.buscarPorNombre(nombre.trim(), materiaExistente -> {
            if (materiaExistente != null && materiaExistente.isActiva()) {
                callback.onError("Ya existe una materia registrada con este nombre");
                return;
            }

            Materia nuevaMateria = new Materia(
                    0,
                    nombre.trim(),
                    sigla != null ? sigla.trim() : "",
                    colorFinal,
                    new Date(),
                    true
            );

            materiaRepository.guardar(nuevaMateria, callback::onSuccess);
        });
    }

    public void actualizarMateria(int id, String nombre, String sigla, String colorHex, ResultadoCallback<Void> callback) {
        if (nombre == null || nombre.trim().isEmpty()) {
            callback.onError("El nombre de la materia es obligatorio");
            return;
        }

        String colorFinal = ColorUtils.esColorHexValido(colorHex) ? colorHex : ColorUtils.COLOR_POR_DEFECTO_HEX;

        materiaRepository.buscarPorId(id, materiaActual -> {
            if (materiaActual == null) {
                callback.onError("La materia a actualizar no existe");
                return;
            }

            materiaActual.setNombre(nombre.trim());
            materiaActual.setSigla(sigla != null ? sigla.trim() : "");
            materiaActual.setColorHex(colorFinal);

            materiaRepository.actualizar(materiaActual, callback::onSuccess);
        });
    }

    public void darDeBajaMateria(int idMateria, ResultadoCallback<Void> callback) {
        // RN-MAT-03: Borrado lógico
        materiaRepository.darDeBajaLogica(idMateria, callback::onSuccess);
    }

    public void obtenerMateriasActivas(ResultadoCallback<List<Materia>> callback) {
        materiaRepository.obtenerTodasActivas(callback::onSuccess);
    }

    public void obtenerMateriasConEstadisticas(SesionEstudioRepository sesionRepository,
                                               ResultadoCallback<List<MateriaConEstadisticas>> callback) {
        materiaRepository.obtenerTodasActivas(materias -> {
            if (materias == null || materias.isEmpty()) {
                callback.onSuccess(new ArrayList<>());
                return;
            }

            List<MateriaConEstadisticas> listaResultado = new ArrayList<>();
            final int totalMaterias = materias.size();
            final int[] procesadas = {0};

            for (Materia materia : materias) {
                sesionRepository.obtenerCompletadasPorMateria(materia.getId(), sesiones -> {
                    int totalSesiones = sesiones != null ? sesiones.size() : 0;
                    int minutosTotales = 0;
                    if (sesiones != null) {
                        for (SesionEstudio sesion : sesiones) {
                            minutosTotales += sesion.getDuracionMinutos();
                        }
                    }
                    double horasTotales = Math.round((minutosTotales / 60.0) * 10.0) / 10.0;
                    listaResultado.add(new MateriaConEstadisticas(materia, totalSesiones, horasTotales));

                    procesadas[0]++;
                    if (procesadas[0] == totalMaterias) {
                        callback.onSuccess(listaResultado);
                    }
                });
            }
        });
    }
}
