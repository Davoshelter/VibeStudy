package com.david.mystudio.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.david.mystudio.data.local.entity.SesionEstudioEntity;
import java.util.List;

/**
 * Data Access Object para operaciones sobre la tabla 'sesiones_estudio'.
 */
@Dao
public interface SesionEstudioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(SesionEstudioEntity sesion);

    @Update
    void actualizar(SesionEstudioEntity sesion);

    @Query("SELECT * FROM sesiones_estudio WHERE id = :id LIMIT 1")
    SesionEstudioEntity buscarPorId(int id);

    @Query("SELECT * FROM sesiones_estudio WHERE id_materia = :idMateria AND estado = 'COMPLETADA'")
    List<SesionEstudioEntity> obtenerCompletadasPorMateria(int idMateria);

    @Query("SELECT * FROM sesiones_estudio WHERE estado = 'EN_CURSO' LIMIT 1")
    SesionEstudioEntity obtenerSesionActiva();

    @Query("SELECT * FROM sesiones_estudio WHERE fecha_programada >= :inicioDia AND fecha_programada <= :finDia ORDER BY fecha_programada ASC")
    List<SesionEstudioEntity> obtenerSesionesProgramadasDelDia(long inicioDia, long finDia);

    @Query("SELECT * FROM sesiones_estudio WHERE fecha_hora_inicio >= :inicioPeriodo AND fecha_hora_inicio <= :finPeriodo AND estado = 'COMPLETADA' ORDER BY fecha_hora_inicio DESC")
    List<SesionEstudioEntity> obtenerCompletadasPorRango(long inicioPeriodo, long finPeriodo);

    @Query("SELECT * FROM sesiones_estudio WHERE id_materia = :idMateria AND fecha_hora_inicio >= :inicioSemana AND fecha_hora_inicio <= :finSemana AND estado = 'COMPLETADA'")
    List<SesionEstudioEntity> obtenerCompletadasPorMateriaYSemana(int idMateria, long inicioSemana, long finSemana);

    @Query("SELECT * FROM sesiones_estudio WHERE estado = 'COMPLETADA' ORDER BY fecha_hora_inicio DESC LIMIT :limite")
    List<SesionEstudioEntity> obtenerHistorialReciente(int limite);
}
