# Skill: Persistencia con Room y SQLite (skill-persistencia-room-sqlite)

## 1. Propósito
Establecer las directrices obligatorias para la capa de acceso a datos (`data/`) de **VibeStudy** utilizando la biblioteca oficial **Android Room** sobre SQLite. La capa debe asegurar la integridad transaccional (ACID), operaciones asíncronas libres de bloqueos de interfaz y el completo aislamiento de la persistencia frente a las capas de dominio y presentación.

---

## 2. Reglas Estrictas de la Capa de Datos

### 2.1. Entidades Room (`@Entity`)
1. Residen exclusivamente en el paquete `data/local/entity/`.
2. Las tablas deben nombrarse en minúsculas y plural con snake_case: `materias`, `sesiones_estudio`, `metas_semanales`, `racha_estudio`.
3. Cada columna debe usar `@ColumnInfo(name = "nombre_columna")`.
4. Las relaciones foráneas deben declararse explícitamente mediante la propiedad `foreignKeys` de la anotación `@Entity`.
5. **Aislamiento:** Jamás se deben utilizar las clases `@Entity` fuera del paquete `data/`. Para interactuar con el dominio, deben mapearse a los modelos POJO correspondientes de `domain/model/`.

### 2.2. Objetos de Acceso a Datos (`@Dao`)
1. Residen en `data/local/dao/`.
2. Son interfaces anotadas con `@Dao`.
3. No deben contener lógica de negocio ni validaciones complejas.
4. Las consultas de inserción, actualización y borrado deben estar tipadas (`@Insert`, `@Update`, `@Delete`).
5. Las consultas personalizadas usan `@Query("SELECT ... FROM ...")` con parámetros parametrizados `:nombreParametro` para prevenir inyecciones SQL.

### 2.3. Base de Datos Central (`@Database`)
1. Reside en `data/local/AppDatabase.java`.
2. Debe extender `RoomDatabase` y listar todas las entidades.
3. Debe implementar el patrón **Singleton con sincronización segura (Double-Checked Locking)** para evitar múltiples instancias de la base de datos en memoria.

### 2.4. Repositorio (`Repository`) y Ocultamiento de la Fuente
1. Reside en `data/repository/` e implementa la interfaz del contrato definida en `domain/repository/`.
2. **Prohibido:** Ejecutar consultas de base de datos en el hilo principal de Android (`Main Thread / UI Thread`).
3. El repositorio utiliza un ejecutor en segundo plano (`Executor` o `ThreadExecutor`) para interactuar con el DAO y notifica los resultados a través de callbacks despachados al hilo principal (`Handler(Looper.getMainLooper())`).
4. Realiza la conversión mediante un Mapper: `MateriaEntity` $\leftrightarrow$ `Materia`.

---

## 3. Ejemplos de Código

### 3.1. Ejemplo INCORRECTO (Filtrado de Room al Dominio y Llamada en Hilo Principal)
```java
// INCORRECTO: Expone Room al exterior y ejecuta en el hilo principal de UI
package com.david.mystudio.data.repository;

import com.david.mystudio.data.local.dao.MateriaDao;
import com.david.mystudio.data.local.entity.MateriaEntity;

public class MateriaRepositoryViolacion {
    private MateriaDao materiaDao;

    public MateriaRepositoryViolacion(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    // VIOLACIÓN: Devuelve la entidad de persistencia directamente al dominio
    // VIOLACIÓN: No maneja hilos secundarios; bloquea el UI Thread (provocará ANR)
    public MateriaEntity obtenerPorId(int id) {
        return materiaDao.buscarPorId(id);
    }
}
```

---

### 3.2. Ejemplo CORRECTO (Entidad, DAO, Mapper y Repositorio Asíncrono)

#### 1. Entidad Room (`data/local/entity/MateriaEntity.java`)
```java
package com.david.mystudio.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "materias")
public class MateriaEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "sigla")
    private String sigla;

    @ColumnInfo(name = "color_hex")
    private String colorHex;

    @ColumnInfo(name = "fecha_creacion")
    private long fechaCreacion;

    @ColumnInfo(name = "activa")
    private boolean activa;

    public MateriaEntity() {
    }

    public MateriaEntity(int id, String nombre, String sigla, String colorHex, long fechaCreacion, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.sigla = sigla;
        this.colorHex = colorHex;
        this.fechaCreacion = fechaCreacion;
        this.activa = activa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public long getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(long fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}
```

#### 2. DAO (`data/local/dao/MateriaDao.java`)
```java
package com.david.mystudio.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.david.mystudio.data.local.entity.MateriaEntity;
import java.util.List;

@Dao
public interface MateriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(MateriaEntity materia);

    @Update
    void actualizar(MateriaEntity materia);

    @Query("SELECT * FROM materias WHERE activa = 1 ORDER BY nombre ASC")
    List<MateriaEntity> obtenerTodasActivas();

    @Query("SELECT * FROM materias WHERE LOWER(nombre) = LOWER(:nombre) LIMIT 1")
    MateriaEntity buscarPorNombre(String nombre);

    @Query("UPDATE materias SET activa = 0 WHERE id = :idMateria")
    void darDeBajaLogica(int idMateria);
}
```

#### 3. Base de Datos Singleton (`data/local/AppDatabase.java`)
```java
package com.david.mystudio.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.david.mystudio.data.local.dao.MateriaDao;
import com.david.mystudio.data.local.entity.MateriaEntity;

@Database(entities = {MateriaEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instancia;
    private static final String NOMBRE_BASE_DATOS = "vibestudy_db";

    public abstract MateriaDao materiaDao();

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
```

#### 4. Mapeador Entity $\leftrightarrow$ Model (`data/local/mapper/MateriaMapper.java`)
```java
package com.david.mystudio.data.local.mapper;

import com.david.mystudio.data.local.entity.MateriaEntity;
import com.david.mystudio.domain.model.Materia;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MateriaMapper {

    public static Materia toDomain(MateriaEntity entity) {
        if (entity == null) return null;
        return new Materia(
                entity.getId(),
                entity.getNombre(),
                entity.getSigla(),
                entity.getColorHex(),
                new Date(entity.getFechaCreacion()),
                entity.isActiva()
        );
    }

    public static MateriaEntity toEntity(Materia model) {
        if (model == null) return null;
        return new MateriaEntity(
                model.getId(),
                model.getNombre(),
                model.getSigla(),
                model.getColorHex(),
                model.getFechaCreacion() != null ? model.getFechaCreacion().getTime() : System.currentTimeMillis(),
                model.isActiva()
        );
    }

    public static List<Materia> toDomainList(List<MateriaEntity> entities) {
        List<Materia> lista = new ArrayList<>();
        if (entities != null) {
            for (MateriaEntity entity : entities) {
                lista.add(toDomain(entity));
            }
        }
        return lista;
    }
}
```

#### 5. Repositorio Asíncrono (`data/repository/MateriaRoomRepository.java`)
```java
package com.david.mystudio.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.david.mystudio.data.local.dao.MateriaDao;
import com.david.mystudio.data.local.entity.MateriaEntity;
import com.david.mystudio.data.local.mapper.MateriaMapper;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.repository.MateriaRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MateriaRoomRepository implements MateriaRepository {

    private final MateriaDao materiaDao;
    private final ExecutorService executor;
    private final Handler mainHandler;

    public MateriaRoomRepository(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void guardar(Materia materia, Callback<Long> callback) {
        executor.execute(() -> {
            MateriaEntity entity = MateriaMapper.toEntity(materia);
            long idGenerado = materiaDao.insertar(entity);
            mainHandler.post(() -> callback.onResult(idGenerado));
        });
    }

    @Override
    public void obtenerTodasActivas(Callback<List<Materia>> callback) {
        executor.execute(() -> {
            List<MateriaEntity> entities = materiaDao.obtenerTodasActivas();
            List<Materia> materias = MateriaMapper.toDomainList(entities);
            mainHandler.post(() -> callback.onResult(materias));
        });
    }

    @Override
    public void buscarPorNombre(String nombre, Callback<Materia> callback) {
        executor.execute(() -> {
            MateriaEntity entity = materiaDao.buscarPorNombre(nombre);
            Materia materia = MateriaMapper.toDomain(entity);
            mainHandler.post(() -> callback.onResult(materia));
        });
    }

    @Override
    public void darDeBajaLogica(int idMateria, Callback<Void> callback) {
        executor.execute(() -> {
            materiaDao.darDeBajaLogica(idMateria);
            mainHandler.post(() -> callback.onResult(null));
        });
    }
}
```
