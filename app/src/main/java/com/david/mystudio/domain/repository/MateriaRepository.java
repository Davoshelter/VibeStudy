package com.david.mystudio.domain.repository;

import com.david.mystudio.domain.model.Materia;
import java.util.List;

/**
 * Contrato de repositorio para operaciones sobre materias.
 */
public interface MateriaRepository {

    interface Callback<T> {
        void onResult(T resultado);
    }

    void guardar(Materia materia, Callback<Long> callback);

    void actualizar(Materia materia, Callback<Void> callback);

    void obtenerTodasActivas(Callback<List<Materia>> callback);

    void buscarPorId(int id, Callback<Materia> callback);

    void buscarPorNombre(String nombre, Callback<Materia> callback);

    void darDeBajaLogica(int idMateria, Callback<Void> callback);
}
