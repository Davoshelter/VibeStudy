package com.david.mystudio.domain.repository;

import com.david.mystudio.domain.model.SesionEstudio;
import java.util.List;

/**
 * Contrato de repositorio para operaciones sobre sesiones de estudio.
 */
public interface SesionEstudioRepository {

    interface Callback<T> {
        void onResult(T resultado);
    }

    void guardar(SesionEstudio sesion, Callback<Long> callback);

    void actualizar(SesionEstudio sesion, Callback<Void> callback);

    void buscarPorId(int id, Callback<SesionEstudio> callback);

    void obtenerCompletadasPorMateria(int idMateria, Callback<List<SesionEstudio>> callback);

    void obtenerSesionActiva(Callback<SesionEstudio> callback);

    void obtenerSesionesProgramadasDelDia(long inicioDia, long finDia, Callback<List<SesionEstudio>> callback);

    void obtenerCompletadasPorRango(long inicioPeriodo, long finPeriodo, Callback<List<SesionEstudio>> callback);

    void obtenerCompletadasPorMateriaYSemana(int idMateria, long inicioSemana, long finSemana, Callback<List<SesionEstudio>> callback);

    void obtenerHistorialReciente(int limite, Callback<List<SesionEstudio>> callback);

    void sanearSesionesHuerfanas(String motivo, Callback<Integer> callback);
}
