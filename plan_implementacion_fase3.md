# VibeStudy: Plan de Implementación por Capas (Fase 3)

Este documento define la hoja de ruta técnica paso a paso para la construcción modular de la aplicación **VibeStudy**. El plan sigue un enfoque **Bottom-Up** estricto: desde la persistencia de datos hasta la integración final de la interfaz de usuario, garantizando el cumplimiento de la arquitectura en 3 capas, el patrón MVP y los principios SOLID.

---

## Índice del Plan
1. [Etapa 1: Capa de Datos (Room & SQLite)](#etapa-1-capa-de-datos-room--sqlite)
2. [Etapa 2: Capa de Dominio (Modelos & Servicios de Negocio)](#etapa-2-capa-de-dominio-modelos--servicios-de-negocio)
3. [Etapa 3: Capa de Presentación (MVP Pantalla por Pantalla)](#etapa-3-capa-de-presentación-mvp-pantalla-por-pantalla)
4. [Etapa 4: Integración, Inyección Manual y Pruebas de Flujo](#etapa-4-integración-inyección-manual-y-pruebas-de-flujo)

---

## Etapa 1: Capa de Datos (Room & SQLite)

### Paso 1.1: Configuración de Dependencias de Persistencia
* **Archivos a crear/modificar:**
  * `gradle/libs.versions.toml`: Declarar versión de Room (`androidx.room:room-runtime`, `androidx.room:room-compiler`).
  * `app/build.gradle.kts`: Agregar dependencias de Room Runtime y Room Annotation Processor (`annotationProcessor`).
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Respetar el uso exclusivo de librerías oficiales de AndroidX.
  * `skill-persistencia-room-sqlite`: Configuración de Room sin dependencias de reflexión externas.
* **Dependencias con pasos anteriores:** Ninguna (punto de partida del proyecto).
* **Criterio de terminado:** El proyecto sincroniza y compila con Gradle (`./gradlew assembleDebug`) reconociendo las anotaciones de Room sin errores de compatibilidad.

---

### Paso 1.2: Entidades de Persistencia Room (`@Entity`)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/data/local/entity/MateriaEntity.java`
  * `app/src/main/java/com/david/mystudio/data/local/entity/SesionEstudioEntity.java`
  * `app/src/main/java/com/david/mystudio/data/local/entity/MetaSemanalEntity.java`
  * `app/src/main/java/com/david/mystudio/data/local/entity/RachaEntity.java`
* **Skills que aplican:**
  * `skill-persistencia-room-sqlite`: Tablas en plural (`materias`, `sesiones_estudio`, `metas_semanales`, `racha_estudio`), `@PrimaryKey(autoGenerate = true)`, `@ColumnInfo`, y `@ForeignKey` con borrado/actualización restringida o en cascada.
  * `skill-convenciones-codigo`: PascalCase, nombres completos en español, getters/setters estándar.
* **Dependencias con pasos anteriores:** Paso 1.1 (dependencias de Room listas).
* **Criterio de terminado:** Clases de entidad compiladas, con claves foráneas mapeando `id_materia` entre `materias` y sus tablas dependientes (`sesiones_estudio`, `metas_semanales`).

---

### Paso 1.3: Data Access Objects (`@Dao`)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/data/local/dao/MateriaDao.java`
  * `app/src/main/java/com/david/mystudio/data/local/dao/SesionEstudioDao.java`
  * `app/src/main/java/com/david/mystudio/data/local/dao/MetaSemanalDao.java`
  * `app/src/main/java/com/david/mystudio/data/local/dao/RachaDao.java`
* **Skills que aplican:**
  * `skill-persistencia-room-sqlite`: Consultas tipadas (`@Insert`, `@Update`), consultas SQL con parámetros nombrados (`:nombreParametro`), transacciones `@Transaction` para agregaciones.
  * `skill-solid-java` (SRP): Cada DAO se limita a operaciones sobre su entidad y tablas directamente relacionadas.
* **Dependencias con pasos anteriores:** Paso 1.2 (Entidades Room creadas).
* **Criterio de terminado:** DAOs implementados con métodos para:
  * `MateriaDao`: `insertar`, `actualizar`, `darDeBajaLogica`, `obtenerTodasActivas`, `buscarPorNombre`.
  * `SesionEstudioDao`: `insertar`, `actualizar`, `obtenerPorId`, `obtenerPorRangoFechas`, `obtenerSesionesCompletadasPorMateria`, `obtenerSesionesDia`.
  * `MetaSemanalDao`: `insertarOActualizar`, `obtenerPorSemanaYAnio`, `obtenerPorMateriaYSemana`.
  * `RachaDao`: `obtenerRachaActual`, `actualizarRacha`.

---

### Paso 1.4: Base de Datos Central (`AppDatabase`)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/data/local/AppDatabase.java`
* **Skills que aplican:**
  * `skill-persistencia-room-sqlite`: Extensión de `RoomDatabase`, Singleton con Double-Checked Locking sincronizado, registro de las 4 entidades y versión 1.
* **Dependencias con pasos anteriores:** Pasos 1.2 y 1.3 (Entidades y DAOs listos).
* **Criterio de terminado:** `AppDatabase` genera correctamente las clases abstractas de Room al compilar (`kapt`/`annotationProcessor`).

---

### Paso 1.5: Mappers Bidireccionales (`Entity` $\leftrightarrow$ `Domain Model`)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/data/local/mapper/MateriaMapper.java`
  * `app/src/main/java/com/david/mystudio/data/local/mapper/SesionEstudioMapper.java`
  * `app/src/main/java/com/david/mystudio/data/local/mapper/MetaSemanalMapper.java`
  * `app/src/main/java/com/david/mystudio/data/local/mapper/RachaMapper.java`
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Garantizar que ninguna entidad `@Entity` de Room escape de la capa `data/`.
  * `skill-solid-java` (SRP): Clases de transformación pura sin efectos colaterales.
* **Dependencias con pasos anteriores:** Paso 1.2 (Entidades de datos) y modelos POJO del dominio (Paso 2.1).
* **Criterio de terminado:** Métodos estáticos puros `toDomain()`, `toEntity()`, `toDomainList()` que convierten campos de forma exhaustiva incluyendo marcas temporales `Date` $\leftrightarrow$ `long`.

---

### Paso 1.6: Implementación Concreta de Repositorios con Room
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/util/ThreadExecutor.java` (Ejecutor en hilo secundario y despachador a Main Thread).
  * `app/src/main/java/com/david/mystudio/data/repository/MateriaRoomRepository.java`
  * `app/src/main/java/com/david/mystudio/data/repository/SesionEstudioRoomRepository.java`
  * `app/src/main/java/com/david/mystudio/data/repository/MetaSemanalRoomRepository.java`
  * `app/src/main/java/com/david/mystudio/data/repository/RachaRoomRepository.java`
* **Skills que aplican:**
  * `skill-persistencia-room-sqlite`: Ocultar DAOs y SQLite, realizar consultas fuera del hilo principal (`ThreadExecutor`), devolver resultados mediante callbacks en el hilo principal de Android.
  * `skill-solid-java` (DIP & LSP): Implementar las interfaces del dominio sin alteración de contratos.
  * `skill-convenciones-codigo`: Sufijo descriptivo `RoomRepository`.
* **Dependencias con pasos anteriores:** Paso 1.3, 1.4, 1.5 y contratos de repositorio (Paso 2.2).
* **Criterio de terminado:** Todos los repositorios implementan sus métodos asíncronos y garantizan cero operaciones de I/O en el hilo de UI.

---

## Etapa 2: Capa de Dominio (Modelos & Servicios de Negocio)

### Paso 2.1: Modelos de Dominio POJO y Enumeraciones
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/domain/model/EstadoSesion.java` (Enum: `PENDIENTE`, `EN_CURSO`, `PAUSADA`, `COMPLETADA`, `CANCELADA`)
  * `app/src/main/java/com/david/mystudio/domain/model/TipoTecnica.java` (Enum: `POMODORO`, `BLOQUE_LIBRE`)
  * `app/src/main/java/com/david/mystudio/domain/model/EstadoMeta.java` (Enum: `EN_PROGRESO`, `CUMPLIDA`)
  * `app/src/main/java/com/david/mystudio/domain/model/TipoPeriodo.java` (Enum: `SEMANAL`, `MENSUAL`)
  * `app/src/main/java/com/david/mystudio/domain/model/Materia.java`
  * `app/src/main/java/com/david/mystudio/domain/model/SesionEstudio.java`
  * `app/src/main/java/com/david/mystudio/domain/model/MetaSemanal.java`
  * `app/src/main/java/com/david/mystudio/domain/model/RachaEstudio.java`
  * `app/src/main/java/com/david/mystudio/domain/model/ReporteEstudio.java`
  * `app/src/main/java/com/david/mystudio/domain/model/MateriaConEstadisticas.java` (DTO para Pantalla 2)
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Java puro, sin anotaciones de Room, Android ni librerías externas.
  * `skill-convenciones-codigo`: Idioma español, getters, setters y constructores descriptivos.
* **Dependencias con pasos anteriores:** Ninguna (dominio agnóstico).
* **Criterio de terminado:** Modelos POJO compilables con lógica interna de encapsulamiento (`calcularPorcentajeAvance()`, `calcularDuracionNeta()`, etc.).

---

### Paso 2.2: Contratos de Repositorio (Interfaces del Dominio)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/domain/repository/MateriaRepository.java`
  * `app/src/main/java/com/david/mystudio/domain/repository/SesionEstudioRepository.java`
  * `app/src/main/java/com/david/mystudio/domain/repository/MetaSemanalRepository.java`
  * `app/src/main/java/com/david/mystudio/domain/repository/RachaRepository.java`
* **Skills que aplican:**
  * `skill-solid-java` (DIP & ISP): Interfaces declaradas en el dominio que definen qué necesita el negocio, sin importar cómo se persiste.
  * `skill-convenciones-codigo`: Sin prefijo "I" (`MateriaRepository`).
* **Dependencias con pasos anteriores:** Paso 2.1 (Modelos de dominio).
* **Criterio de terminado:** Interfaces completas con interfaces internas `Callback<T>` para comunicación asíncrona desacoplada.

---

### Paso 2.3: Servicios de Dominio con Reglas de Negocio
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/domain/service/MateriaService.java`
  * `app/src/main/java/com/david/mystudio/domain/service/SesionEstudioService.java`
  * `app/src/main/java/com/david/mystudio/domain/service/MetaSemanalService.java`
  * `app/src/main/java/com/david/mystudio/domain/service/RachaService.java`
  * `app/src/main/java/com/david/mystudio/domain/service/ReporteService.java`
* **Skills que aplican:**
  * `skill-solid-java` (SRP & DIP): Inyección de repositorios por constructor; métodos especializados de regla de negocio.
  * `skill-convenciones-codigo`: Constantes en `UPPER_SNAKE_CASE` (ausencia de números mágicos), métodos cortos (<25 líneas) y validaciones en español.
* **Reglas a implementar en este paso:**
  * `MateriaService`: Validar nombre no vacío (`RN-MAT-01`), validar no duplicidad (`RN-MAT-02`), dar de baja lógica (`RN-MAT-03`).
  * `SesionEstudioService`: Validar fecha y hora futura en planificación (`RN-SES-01`), validar duración 15-360 min (`RN-SES-02`), exclusividad de sesión activa (`RN-SES-03`), cálculo de duración neta (`RN-SES-04`) y umbral mínimo de 1 minuto (`RN-SES-05`).
  * `MetaSemanalService`: Validar rango 1 a 50 hrs (`RN-MET-01`), cómputo dinámico de porcentaje (`RN-MET-03`), marcado de 100% cumplida (`RN-MET-04`) y detección de rezago (`RN-MET-05`).
  * `RachaService`: Lógica de incremento diario continuo (`RN-RAC-01`), reinicio a 0 tras más de 48 hrs (`RN-RAC-02`) y cálculo de racha máxima (`RN-RAC-03`).
  * `ReporteService`: Consolidación semanal y mensual (`RN-REP-01`), distribución para PieChart y promedio diario.
* **Dependencias con pasos anteriores:** Pasos 2.1 y 2.2.
* **Criterio de terminado:** Pruebas unitarias en JVM sobre los servicios verificando que todas las reglas de negocio (1 a 5) arrojen los errores o resultados esperados.

---

### Paso 2.4: Utilitarios del Dominio y Formato
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/util/DateTimeUtils.java` (Conversión de minutos a "Xh Ym", cálculo de semana ISO-8601, inicio/fin de semana).
  * `app/src/main/java/com/david/mystudio/util/ColorUtils.java` (Validación de colores hexadecimales `#RRGGBB`).
* **Skills que aplican:**
  * `skill-convenciones-codigo`: Métodos estáticos utilitarios con nombres claros y cobertura de casos nulos.
* **Dependencias con pasos anteriores:** Ninguna.
* **Criterio de terminado:** Utilitarios funcionales y probados para fechas de calendario y formateo textual de duraciones.

---

## Etapa 3: Capa de Presentación (MVP Pantalla por Pantalla)

### Paso 3.1: Recursos Centralizados y Tema Material Design 3
* **Archivos a crear/modificar:**
  * `app/src/main/res/values/strings.xml`: Definición de todos los textos de las 5 pantallas, botones, errores y diálogos (cero hardcoded strings).
  * `app/src/main/res/values/dimens.xml`: Medidas M3 (márgenes, paddings, radios de esquinas de 12dp/20dp, tamaños de tipografía).
  * `app/src/main/res/values/colors.xml`: Paleta completa (primario, fondo, swatches de asignaturas, colores semánticos).
  * `app/src/main/res/values/themes.xml`: Tema `Theme.VibeStudy` basado en Material 3 (`Theme.Material3.DayNight.NoActionBar`).
  * `app/src/main/res/drawable/`: Drawables para swatches redondeados, fondos Zen, e iconos vectoriales M3.
* **Skills que aplican:**
  * `skill-diseno-xml`: Centralización total de recursos y estricta fidelidad al prototipo `prototipo_HF.html`.
* **Dependencias con pasos anteriores:** Prototipo aprobado.
* **Criterio de terminado:** Recursos creados y validados visualmente sin advertencias de lint por textos literales ni medidas en bruto.

---

### Paso 3.2: Contratos Base de MVP
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/base/BaseView.java`
  * `app/src/main/java/com/david/mystudio/ui/base/BasePresenter.java`
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Métodos de ciclo de vida mínimos (`iniciar()`, `destruir()`).
  * `skill-solid-java` (ISP): Mantener interfaces base ultra ligeras para no forzar implementaciones innecesarias.
* **Dependencias con pasos anteriores:** Ninguna.
* **Criterio de terminado:** Contratos base listos para ser extendidos por cada pantalla.

---

### Paso 3.3: Pantalla 1 - Inicio (Dashboard)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/dashboard/DashboardContract.java`
  * `app/src/main/java/com/david/mystudio/ui/dashboard/DashboardPresenter.java`
  * `app/src/main/java/com/david/mystudio/ui/dashboard/DashboardFragment.java`
  * `app/src/main/java/com/david/mystudio/ui/dashboard/AgendaAdapter.java`
  * `app/src/main/res/layout/fragment_dashboard.xml`
  * `app/src/main/res/layout/item_agenda_sesion.xml`
* **Skills que aplican:**
  * `skill-arquitectura-capas`: MVP limpio; `DashboardPresenter` coordina `RachaService`, `MetaSemanalService` y `SesionEstudioService`.
  * `skill-diseno-xml`: Prefijos de ID (`txtRachaDias`, `crdMetaGlobal`, `prgMetaGlobal`, `rvAgendaHoy`).
* **Dependencias con pasos anteriores:** Etapa 1, Etapa 2 y Paso 3.1.
* **Criterio de terminado:** Dashboard muestra la racha real calculada, la meta semanal global agregada con su barra de progreso porcentual, el banner *Shinrin-yoku*, y el listado de sesiones pendientes del día. El botón "Iniciar Enfoque" transiciona a la Pantalla 3.

---

### Paso 3.4: Pantalla 2 - Gestión de Materias (5S)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/materias/MateriasContract.java`
  * `app/src/main/java/com/david/mystudio/ui/materias/MateriasPresenter.java`
  * `app/src/main/java/com/david/mystudio/ui/materias/MateriasFragment.java`
  * `app/src/main/java/com/david/mystudio/ui/materias/MateriasAdapter.java`
  * `app/src/main/java/com/david/mystudio/ui/materias/AddEditMateriaDialogFragment.java`
  * `app/src/main/res/layout/fragment_materias.xml`
  * `app/src/main/res/layout/item_materia.xml`
  * `app/src/main/res/layout/dialog_add_subject.xml`
* **Skills que aplican:**
  * `skill-diseno-xml`: Implementación fiel de `dialog_add_subject.xml`, selector de swatches y tarjetas con botones "Editar" y "Eliminar".
  * `skill-arquitectura-capas`: Diálogo desacoplado que notifica al `MateriasPresenter`.
* **Dependencias con pasos anteriores:** Paso 2.3 (`MateriaService`).
* **Criterio de terminado:** CRUD completo operativo: registro con validaciones visuales de error, listado reactivo con RecyclerView, edición con precarga de datos, filtrado por texto en tiempo real, confirmación modal para baja lógica y estado vacío (*Empty State*).

---

### Paso 3.5: Pantalla 3 - Enfoque Zanshin (Temporizador & Servicio en Primer Plano)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/enfoque/EnfoqueContract.java`
  * `app/src/main/java/com/david/mystudio/ui/enfoque/EnfoquePresenter.java`
  * `app/src/main/java/com/david/mystudio/ui/enfoque/EnfoqueFragment.java`
  * `app/src/main/java/com/david/mystudio/ui/enfoque/StudyTimerService.java` (Servicio Android en primer plano con notificación persistente)
  * `app/src/main/res/layout/fragment_enfoque.xml`
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Desacoplamiento entre la vista, el presentador y el `StudyTimerService`.
  * `skill-solid-java` (SRP & OCP): Estrategia de técnicas de temporización (`Pomodoro` con cuenta regresiva / descanso vs `Bloque libre` ascendente).
  * `skill-diseno-xml`: Renderizado del anillo circular, swatches y bitácora de tópicos.
* **Dependencias con pasos anteriores:** Paso 2.3 (`SesionEstudioService`, `RachaService`).
* **Criterio de terminado:** El temporizador permite iniciar, pausar y finalizar sesiones; mantiene el conteo activo si se minimiza la app o rota la pantalla gracias a `StudyTimerService`; y al presionar "Finalizar Estudio", captura las observaciones, persiste la sesión completada e incrementa la racha diaria.

---

### Paso 3.6: Pantalla 4 - Metas Semanales (Kaizen Goals)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/metas/MetasContract.java`
  * `app/src/main/java/com/david/mystudio/ui/metas/MetasPresenter.java`
  * `app/src/main/java/com/david/mystudio/ui/metas/MetasFragment.java`
  * `app/src/main/java/com/david/mystudio/ui/metas/MetasAdapter.java`
  * `app/src/main/java/com/david/mystudio/ui/metas/AddMetaDialogFragment.java`
  * `app/src/main/res/layout/fragment_metas.xml`
  * `app/src/main/res/layout/item_meta.xml`
  * `app/src/main/res/layout/dialog_add_meta.xml`
* **Skills que aplican:**
  * `skill-diseno-xml`: Variantes tonales de las tarjetas (azul en progreso, verde con felicitación 🎉 para 100%, rojo/ámbar ⚠️ para materias rezagadas).
  * `skill-arquitectura-capas`: Presenter coordina el cálculo dinámico entre horas comprometidas y horas reales.
* **Dependencias con pasos anteriores:** Paso 2.3 (`MetaSemanalService`).
* **Criterio de terminado:** Selector interactivo de semana (anterior/siguiente), tarjeta global de compromiso Kaizen, listado de metas con cálculo dinámico del porcentaje de avance y diálogo de registro de meta semanal por asignatura validando el rango 1 a 50 hrs.

---

### Paso 3.7: Pantalla 5 - Reportes y Estadísticas (Analítica Visual)
* **Archivos a crear:**
  * `app/src/main/java/com/david/mystudio/ui/reportes/ReportesContract.java`
  * `app/src/main/java/com/david/mystudio/ui/reportes/ReportesPresenter.java`
  * `app/src/main/java/com/david/mystudio/ui/reportes/ReportesFragment.java`
  * `app/src/main/java/com/david/mystudio/ui/reportes/HistoricoAdapter.java`
  * `app/src/main/res/layout/fragment_reportes.xml`
  * `app/src/main/res/layout/item_historico_sesion.xml`
* **Skills que aplican:**
  * `skill-diseno-xml`: Integración de vistas para métricas clave, gráficos y RecyclerView de sesiones históricas.
  * `skill-solid-java` (SRP): Gráficos delegados a vistas/helpers desacoplados.
* **Dependencias con pasos anteriores:** Paso 2.3 (`ReporteService`).
* **Criterio de terminado:** Visualización de horas totales y promedio diario según el periodo seleccionado (Semana/Mes/Historial), gráfico circular (*PieChart*) con la proporción y color de cada materia, gráfico de barras (*BarChart*) de los últimos 7 días y listado cronológico de notas de estudio.

---

## Etapa 4: Integración, Inyección Manual y Pruebas de Flujo

### Paso 4.1: Contenedor Central de Dependencias (`AppContainer`)
* **Archivos a crear/modificar:**
  * `app/src/main/java/com/david/mystudio/AppContainer.java`
  * `app/src/main/java/com/david/mystudio/StudyApplication.java`
  * `app/src/main/AndroidManifest.xml`: Declarar `android:name=".StudyApplication"` y permisos (`POST_NOTIFICATIONS`, `FOREGROUND_SERVICE`).
* **Skills que aplican:**
  * `skill-arquitectura-capas`: Inyección manual de dependencias sin Dagger ni Hilt; instanciación centralizada de la base de datos Room, repositorios y servicios de dominio.
* **Dependencias con pasos anteriores:** Etapas 1, 2 y 3.
* **Criterio de terminado:** `StudyApplication` inicializa `AppContainer` en `onCreate()`, permitiendo a cualquier Fragment o Activity obtener sus servicios sin acoplamiento.

---

### Paso 4.2: Navegación Principal y Shell de la Aplicación
* **Archivos a crear/modificar:**
  * `app/src/main/res/menu/bottom_nav_menu.xml`: Íconos y etiquetas de las 5 secciones (Inicio, Materias, Enfoque, Metas, Reportes).
  * `app/src/main/res/layout/activity_main.xml`: `FragmentContainerView` con `BottomNavigationView`.
  * `app/src/main/java/com/david/mystudio/MainActivity.java`: Manejo de cambios de pestaña conservando el estado de cada fragment.
* **Skills que aplican:**
  * `skill-diseno-xml`: Configuración de navegación inferior M3 según el prototipo.
* **Dependencias con pasos anteriores:** Pasos 3.3 a 3.7 y Paso 4.1.
* **Criterio de terminado:** La aplicación inicia fluidamente en el Dashboard y permite alternar entre las 5 pantallas sin parpadeos, recreaciones indebidas ni fugas de memoria.

---

### Paso 4.3: Matriz de Verificación y Pruebas de Flujo Extremo a Extremo (E2E)
* **Objetivo:** Validar el cumplimiento del 100% de las Historias de Usuario (`HU-01` a `HU-12`) mediante un flujo continuo:
  1. **Flujo de Materias (HU-01 a HU-03):** Crear 3 materias con nombres y colores distintos $\rightarrow$ verificar que aparezcan en el listado $\rightarrow$ editar una materia $\rightarrow$ verificar cambio en todas las vistas.
  2. **Flujo de Metas (HU-07, HU-08):** Fijar una meta semanal de 5.0 hrs para una materia $\rightarrow$ verificar barra en 0%.
  3. **Flujo de Temporizador y Racha (HU-05, HU-06, HU-12):** Iniciar sesión en tiempo real para esa materia $\rightarrow$ probar pausa y reanudación $\rightarrow$ finalizar tras acumular tiempo $\rightarrow$ ingresar notas de estudio $\rightarrow$ verificar que la racha aumente a +1 día con ícono de constancia.
  4. **Flujo de Progreso y Alertas (HU-08):** Abrir Metas y comprobar que el tiempo estudiado se refleje en la barra de progreso de la materia y en la meta global del Dashboard.
  5. **Flujo de Reportes y Estadísticas (HU-09 a HU-11):** Abrir Reportes y comprobar que el PieChart desglosa el porcentaje de la materia con su color oficial, el BarChart muestra la barra del día actual y el historial cronológico muestra la sesión recién completada con sus notas.
  6. **Flujo de Resiliencia (RNF-09, RNF-10):** Probar rotación de pantalla durante el cronómetro y verificar operatividad en modo avión (offline).
* **Criterio de terminado:** Todos los flujos se ejecutan sin caídas (*crashes*), sin bloqueos de interfaz (ANR) y con persistencia íntegra verificada en SQLite.
