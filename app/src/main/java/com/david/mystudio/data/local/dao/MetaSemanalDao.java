package com.david.mystudio.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.david.mystudio.data.local.entity.MetaSemanalEntity;
import java.util.List;

/**
 * Data Access Object para operaciones sobre la tabla 'metas_semanales'.
 */
@Dao
public interface MetaSemanalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(MetaSemanalEntity meta);

    @Update
    void actualizar(MetaSemanalEntity meta);

    @Query("SELECT * FROM metas_semanales WHERE id = :id LIMIT 1")
    MetaSemanalEntity buscarPorId(int id);

    @Query("SELECT * FROM metas_semanales WHERE numero_semana = :numeroSemana AND anio = :anio")
    List<MetaSemanalEntity> obtenerPorSemanaYAnio(int numeroSemana, int anio);

    @Query("SELECT * FROM metas_semanales WHERE id_materia = :idMateria AND numero_semana = :numeroSemana AND anio = :anio LIMIT 1")
    MetaSemanalEntity obtenerPorMateriaYSemana(int idMateria, int numeroSemana, int anio);

    @Query("SELECT * FROM metas_semanales WHERE id_materia = :idMateria")
    List<MetaSemanalEntity> obtenerPorMateria(int idMateria);
}
