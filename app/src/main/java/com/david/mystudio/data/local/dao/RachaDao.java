package com.david.mystudio.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.david.mystudio.data.local.entity.RachaEntity;

/**
 * Data Access Object para operaciones sobre la tabla 'racha_estudio'.
 */
@Dao
public interface RachaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertar(RachaEntity racha);

    @Update
    void actualizar(RachaEntity racha);

    @Query("SELECT * FROM racha_estudio WHERE id = 1 LIMIT 1")
    RachaEntity obtenerRachaActual();
}
