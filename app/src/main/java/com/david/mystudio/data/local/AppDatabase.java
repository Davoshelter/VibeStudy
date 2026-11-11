package com.david.mystudio.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.david.mystudio.data.local.dao.MateriaDao;
import com.david.mystudio.data.local.dao.MetaSemanalDao;
import com.david.mystudio.data.local.dao.RachaDao;
import com.david.mystudio.data.local.dao.SesionEstudioDao;
import com.david.mystudio.data.local.entity.MateriaEntity;
import com.david.mystudio.data.local.entity.MetaSemanalEntity;
import com.david.mystudio.data.local.entity.RachaEntity;
import com.david.mystudio.data.local.entity.SesionEstudioEntity;

/**
 * Base de datos central Room de la aplicación VibeStudy.
 */
@Database(
        entities = {
                MateriaEntity.class,
                SesionEstudioEntity.class,
                MetaSemanalEntity.class,
                RachaEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instancia;
    private static final String NOMBRE_BASE_DATOS = "vibestudy_database";

    public abstract MateriaDao materiaDao();
    public abstract SesionEstudioDao sesionEstudioDao();
    public abstract MetaSemanalDao metaSemanalDao();
    public abstract RachaDao rachaDao();

    public static AppDatabase getInstance(Context context) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            NOMBRE_BASE_DATOS
                    ).build();
                }
            }
        }
        return instancia;
    }
}
