# Skill: Arquitectura en 3 Capas y Patrón MVP (skill-arquitectura-capas)

## 1. Propósito
Establecer las directrices arquitectónicas obligatorias para la aplicación Android **VibeStudy**, garantizando la estricta separación de responsabilidades mediante una **arquitectura en 3 capas** (Presentación, Dominio y Datos) combinada con el patrón **MVP (Model-View-Presenter)** en la capa de presentación. Todas las capas deben comunicarse exclusivamente a través de contratos (interfaces) e inyección manual de dependencias mediante un contenedor de dependencias centralizado (`AppContainer`), sin utilizar frameworks de reflexión como Dagger o Hilt.

---

## 2. Estructura de Paquetes
El código fuente dentro de `com.david.mystudio` (o el paquete base acordado) debe organizarse obligatoriamente de la siguiente forma:

```
com.david.mystudio/
│
├── AppContainer.java                 // Contenedor central de inyección de dependencias manual
├── StudyApplication.java             // Application de Android que instancia AppContainer
│
├── ui/                               // CAPA DE PRESENTACIÓN (MVP)
│   ├── base/
│   │   ├── BaseView.java
│   │   └── BasePresenter.java
│   ├── dashboard/                    // Pantalla 1: Inicio
│   │   ├── DashboardContract.java    // Interface View y Presenter
│   │   ├── DashboardPresenter.java
│   │   └── DashboardFragment.java
│   ├── materias/                     // Pantalla 2: Materias
│   │   ├── MateriasContract.java
│   │   ├── MateriasPresenter.java
│   │   ├── MateriasFragment.java
│   │   ├── MateriasAdapter.java
│   │   └── AddEditMateriaDialogFragment.java
│   ├── enfoque/                      // Pantalla 3: Temporizador Zanshin
│   │   ├── EnfoqueContract.java
│   │   ├── EnfoquePresenter.java
│   │   ├── EnfoqueFragment.java
│   │   └── StudyTimerService.java    // Foreground Service para el temporizador
│   ├── metas/                        // Pantalla 4: Metas Semanales
│   │   ├── MetasContract.java
│   │   ├── MetasPresenter.java
│   │   ├── MetasFragment.java
│   │   └── MetasAdapter.java
│   └── reportes/                     // Pantalla 5: Estadísticas
│       ├── ReportesContract.java
│       ├── ReportesPresenter.java
│       └── ReportesFragment.java
│
├── domain/                           // CAPA DE NEGOCIO / DOMINIO (POJO puro)
│   ├── model/                        // Modelos de negocio puros (sin anotaciones de Android ni Room)
│   │   ├── Materia.java
│   │   ├── SesionEstudio.java
│   │   ├── MetaSemanal.java
│   │   ├── RachaEstudio.java
│   │   ├── ReporteEstudio.java
│   │   ├── EstadoSesion.java
│   │   ├── TipoTecnica.java
│   │   └── EstadoMeta.java
│   ├── repository/                   // Contratos de persistencia requeridos por el dominio
│   │   ├── MateriaRepository.java
│   │   ├── SesionEstudioRepository.java
│   │   ├── MetaSemanalRepository.java
│   │   └── RachaRepository.java
│   └── service/                      // Lógica y casos de uso con reglas de negocio
│       ├── MateriaService.java
│       ├── SesionEstudioService.java
│       ├── MetaSemanalService.java
│       ├── RachaService.java
│       └── ReporteService.java
│
├── data/                             // CAPA DE ACCESO A DATOS (Room / SQLite)
│   ├── local/
│   │   ├── AppDatabase.java          // Base de datos Room
│   │   ├── entity/                   // Entidades de persistencia (@Entity)
│   │   │   ├── MateriaEntity.java
│   │   │   ├── SesionEstudioEntity.java
│   │   │   ├── MetaSemanalEntity.java
│   │   │   └── RachaEntity.java
│   │   ├── dao/                      // Data Access Objects (@Dao)
│   │   │   ├── MateriaDao.java
│   │   │   ├── SesionEstudioDao.java
│   │   │   ├── MetaSemanalDao.java
│   │   │   └── RachaDao.java
│   │   └── mapper/                   // Mapeadores Entity <-> Domain Model
│   │       ├── MateriaMapper.java
│   │       ├── SesionEstudioMapper.java
│   │       └── MetaSemanalMapper.java
│   └── repository/                   // Implementaciones de los repositorios
│       ├── MateriaRoomRepository.java
│       ├── SesionEstudioRoomRepository.java
│       ├── MetaSemanalRoomRepository.java
│       └── RachaRoomRepository.java
│
└── util/                             // UTILITARIOS COMPARTIDOS
    ├── DateTimeUtils.java            // Formateadores de fecha y hora
    ├── ColorUtils.java               // Helpers de validación de color
    └── ThreadExecutor.java           // Manejo de hilos secundarios (Background Worker)
```

---

## 3. Reglas Concretas de las Capas

### 3.1. Capa de Presentación (`ui/`)
1. **Rol:** Mostrar información al usuario en pantalla y capturar eventos de entrada (toques, texto).
2. **Componentes:** `Activity`, `Fragment`, `DialogFragment`, `RecyclerView.Adapter`, implementaciones de interfaces `View` y clases `Presenter`.
3. **Reglas de importación:**
   - **PUEDE importar:** Framework de Android (`android.*`, `androidx.*`), contratos de la vista, clases del paquete `domain/model/` (para renderizar datos) y los `Service`s del dominio.
   - **PROHIBIDO importar:** Clases de `data/` (DAOs, Entidades Room, `AppDatabase`), llamadas a SQL o SQLite directas.
4. **Reglas del patrón MVP:**
   - Cada pantalla tiene un contrato unificado `XxxContract` con dos interfaces internas: `View` y `Presenter`.
   - La `View` no toma decisiones de negocio ni valida reglas de negocio complejas; únicamente informa al `Presenter` sobre eventos del usuario (`presenter.onBotonGuardarClick(...)`).
   - El `Presenter` recibe una instancia de `View` como interfaz y las dependencias de servicio necesarias. No debe importar `android.content.Context` ni clases gráficas de Android (`View`, `TextView`, etc.) para permitir pruebas unitarias puras en JVM.
   - El `Presenter` interactúa con el `Service` de dominio correspondiente y notifica a la `View` los cambios a mostrar (`view.mostrarMaterias(...)`, `view.mostrarErrorNombreVacio()`).

### 3.2. Capa de Lógica de Negocio / Dominio (`domain/`)
1. **Rol:** Contener el modelo conceptual, las reglas de negocio, los cálculos y orquestaciones.
2. **Componentes:** Entidades de dominio (POJOs), contratos de Repositorio (`interfaces`) y Clases de Servicio (`Service`).
3. **Reglas de importación:**
   - **Código Java puro:** No debe importar paquetes de Android UI (`android.widget.*`, `android.app.*`, `android.view.*`).
   - **PUEDE importar:** Clases estándar de Java (`java.util.*`, `java.time.*` o `java.util.Date`).
   - **PROHIBIDO importar:** Clases de la capa `ui/` ni implementaciones de la capa `data/` (`*RoomRepository`, `*Entity`, `*Dao`, Room annotations).
4. **Reglas de comunicación:**
   - Si un `Service` requiere datos, los solicita a través de una interfaz de repositorio definida dentro de `domain/repository/` (Inversión de Dependencias).

### 3.3. Capa de Datos (`data/`)
1. **Rol:** Gestionar la persistencia de datos (base de datos Room / SQLite, preferencias locales).
2. **Componentes:** `AppDatabase`, clases `@Entity`, interfaces `@Dao`, mapeadores y las clases concretas que implementan los repositorios del dominio (`XxxRoomRepository`).
3. **Reglas de importación:**
   - **PUEDE importar:** Librería de persistencia de Room (`androidx.room.*`), clases de `domain/model/` y `domain/repository/`.
   - **PROHIBIDO importar:** Clases de la capa `ui/` (`Activity`, `Fragment`, `Presenter`).
4. **Ocultamiento de la fuente de datos:**
   - El repositorio mapea internamente las entidades Room (`MateriaEntity`) a los modelos de dominio (`Materia`) antes de devolver los datos al exterior. Las entidades de Room jamás deben filtrarse a las capas superiores.
   - Las operaciones de base de datos deben ejecutarse en hilos secundarios (background thread) mediante `Executors` o `ThreadExecutor`, sin bloquear el hilo principal (UI Thread).

### 3.4. Inyección de Dependencias Manual (`AppContainer`)
- Se implementa una clase central `AppContainer` inicializada en `StudyApplication`.
- `AppContainer` crea la instancia singleton de `AppDatabase`, instancia los repositorios y crea los servicios del dominio.
- Las Activities y Fragments obtienen su `Presenter` solicitando las dependencias al `AppContainer` de la aplicación:
  ```java
  StudyApplication app = (StudyApplication) requireActivity().getApplication();
  presenter = new MateriasPresenter(this, app.getAppContainer().getMateriaService());
  ```

---

## 4. Ejemplos de Código

### 4.1. Ejemplo INCORRECTO (Violación de Arquitectura y MVP)
En este ejemplo, la Activity hace consultas directas a la base de datos, valida reglas de negocio, maneja hilos a mano y no utiliza Presenter ni Service:

```java
// INCORRECTO: MainActivity / Fragment con lógica de negocio y persistencia acoplada
package com.david.mystudio.ui.materias;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.david.mystudio.data.local.AppDatabase;
import com.david.mystudio.data.local.entity.MateriaEntity;

public class MateriasActivityIncorrecta extends AppCompatActivity {
    private EditText txtNombreMateria;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(this); // VIOLACIÓN: Acceso directo a BD desde UI

        Button btnGuardar = findViewById(0);
        btnGuardar.setOnClickListener(v -> {
            String nombre = txtNombreMateria.getText().toString();
            
            // VIOLACIÓN: Regla de negocio validada directamente en la Activity
            if (nombre == null || nombre.trim().isEmpty()) {
                Toast.makeText(this, "Nombre inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            // VIOLACIÓN: Entidad de persistencia Room manipulada en la Vista
            MateriaEntity entity = new MateriaEntity();
            entity.setNombre(nombre);
            
            // VIOLACIÓN: Consulta de datos ejecutada directamente desde la vista
            new Thread(() -> {
                database.materiaDao().insertar(entity);
            }).start();
        });
    }
}
```

---

### 4.2. Ejemplo CORRECTO (Cumplimiento de Arquitectura y MVP)

#### 1. Contrato de la Vista y Presenter (`ui/materias/MateriasContract.java`)
```java
package com.david.mystudio.ui.materias;

import com.david.mystudio.domain.model.Materia;
import java.util.List;

public interface MateriasContract {

    interface View {
        void mostrarCargando(boolean cargando);
        void mostrarMaterias(List<Materia> materias);
        void mostrarError(String mensaje);
        void mostrarMensajeExito(String mensaje);
        void limpiarFormulario();
    }

    interface Presenter {
        void iniciar();
        void cargarMaterias();
        void registrarMateria(String nombre, String sigla, String colorHex);
        void eliminarMateria(Materia materia);
        void destruir();
    }
}
```

#### 2. Implementación del Presenter (`ui/materias/MateriasPresenter.java`)
```java
package com.david.mystudio.ui.materias;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.service.MateriaService;
import java.util.List;

public class MateriasPresenter implements MateriasContract.Presenter {

    private MateriasContract.View vista;
    private final MateriaService materiaService;

    public MateriasPresenter(MateriasContract.View vista, MateriaService materiaService) {
        this.vista = vista;
        this.materiaService = materiaService;
    }

    @Override
    public void iniciar() {
        cargarMaterias();
    }

    @Override
    public void cargarMaterias() {
        if (vista == null) return;
        vista.mostrarCargando(true);
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> resultado) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMaterias(resultado);
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void registrarMateria(String nombre, String sigla, String colorHex) {
        if (vista == null) return;
        vista.mostrarCargando(true);
        materiaService.registrarNuevaMateria(nombre, sigla, colorHex, new MateriaService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarMensajeExito("Materia registrada correctamente");
                    vista.limpiarFormulario();
                    cargarMaterias();
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarCargando(false);
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void eliminarMateria(Materia materia) {
        if (vista == null) return;
        materiaService.darDeBajaMateria(materia.getId(), new MateriaService.ResultadoCallback<Void>() {
            @Override
            public void onSuccess(Void resultado) {
                if (vista != null) {
                    vista.mostrarMensajeExito("Materia dada de baja");
                    cargarMaterias();
                }
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) {
                    vista.mostrarError(mensajeError);
                }
            }
        });
    }

    @Override
    public void destruir() {
        this.vista = null; // Previene fugas de memoria si la vista se destruye
    }
}
```

#### 3. Servicio de Dominio (`domain/service/MateriaService.java`)
```java
package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.repository.MateriaRepository;
import java.util.Date;
import java.util.List;

public class MateriaService {

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
        void onError(String mensajeError);
    }

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public void registrarNuevaMateria(String nombre, String sigla, String colorHex, ResultadoCallback<Void> callback) {
        // Regla de negocio 1: Nombre obligatorio
        if (nombre == null || nombre.trim().isEmpty()) {
            callback.onError("El nombre de la materia es obligatorio");
            return;
        }

        // Regla de negocio 2: Color obligatorio
        if (colorHex == null || !colorHex.matches("^#[0-9A-Fa-f]{6}$")) {
            callback.onError("El color seleccionado no es válido");
            return;
        }

        // Regla de negocio 3: Verificar no duplicados
        materiaRepository.buscarPorNombre(nombre.trim(), new MateriaRepository.Callback<Materia>() {
            @Override
            public void onResult(Materia materiaExistente) {
                if (materiaExistente != null && materiaExistente.isActiva()) {
                    callback.onError("Ya existe una materia registrada con este nombre");
                    return;
                }

                Materia nuevaMateria = new Materia(
                        0,
                        nombre.trim(),
                        sigla != null ? sigla.trim() : "",
                        colorHex,
                        new Date(),
                        true
                );

                materiaRepository.guardar(nuevaMateria, new MateriaRepository.Callback<Long>() {
                    @Override
                    public void onResult(Long nuevoId) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    public void obtenerMateriasActivas(ResultadoCallback<List<Materia>> callback) {
        materiaRepository.obtenerTodasActivas(new MateriaRepository.Callback<List<Materia>>() {
            @Override
            public void onResult(List<Materia> materias) {
                callback.onSuccess(materias);
            }
        });
    }

    public void darDeBajaMateria(int idMateria, ResultadoCallback<Void> callback) {
        materiaRepository.darDeBajaLogica(idMateria, new MateriaRepository.Callback<Void>() {
            @Override
            public void onResult(Void resultado) {
                callback.onSuccess(null);
            }
        });
    }
}
```
