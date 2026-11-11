package com.david.mystudio.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.david.mystudio.data.local.entity.MateriaEntity;
import java.util.List;

/**
 * Data Access Object para operaciones sobre la tabla 'materias'.
 */
@Dao
public interface MateriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(MateriaEntity materia);

    @Update
    void actualizar(MateriaEntity materia);

    @Query("SELECT * FROM materias WHERE activa = 1 ORDER BY nombre ASC")
    List<MateriaEntity> obtenerTodasActivas();

    @Query("SELECT * FROM materias WHERE id = :id LIMIT 1")
    MateriaEntity buscarPorId(int id);

    @Query("SELECT * FROM materias WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(:nombre)) LIMIT 1")
    MateriaEntity buscarPorNombre(String nombre);

    @Query("UPDATE materias SET activa = 0 WHERE id = :idMateria")
    void darDeBajaLogica(int idMateria);
}
