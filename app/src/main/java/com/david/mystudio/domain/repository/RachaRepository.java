package com.david.mystudio.domain.repository;

import com.david.mystudio.domain.model.RachaEstudio;

/**
 * Contrato de repositorio para operaciones sobre la racha de estudio.
 */
public interface RachaRepository {

    interface Callback<T> {
        void onResult(T resultado);
    }

    void obtenerRachaActual(Callback<RachaEstudio> callback);

    void actualizarRacha(RachaEstudio racha, Callback<Void> callback);
}
