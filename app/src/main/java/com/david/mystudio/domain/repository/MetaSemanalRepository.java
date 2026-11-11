package com.david.mystudio.domain.repository;

import com.david.mystudio.domain.model.MetaSemanal;
import java.util.List;

/**
 * Contrato de repositorio para operaciones sobre metas semanales.
 */
public interface MetaSemanalRepository {

    interface Callback<T> {
        void onResult(T resultado);
    }

    void guardarOActualizar(MetaSemanal meta, Callback<Long> callback);

    void buscarPorId(int id, Callback<MetaSemanal> callback);

    void obtenerPorSemanaYAnio(int numeroSemana, int anio, Callback<List<MetaSemanal>> callback);

    void obtenerPorMateriaYSemana(int idMateria, int numeroSemana, int anio, Callback<MetaSemanal> callback);
}
