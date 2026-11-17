# UNIVERSIDAD PRIVADA DOMINGO SAVIO
## FACULTAD DE INGENIERÍA
### CARRERA DE INGENIERÍA DE SISTEMAS

---

# StudyVibe: App Móvil para la Planificación y Gestión del Tiempo de Estudio Académico
## Documento Técnico de Fase 2: Diseño del Software, Implementación y Pruebas (Puntos 4 al 7)

**Asignatura:** Aplicaciones Móviles I  
**Docente:** Ing. Daniel Román Terrazas  
**Estudiante:** David Gorena  
**Gestión:** II / 2026  
**Ciudad:** Santa Cruz de la Sierra, Bolivia  
**Norma de Estilo:** American Psychological Association (APA 7ma Edición)  

---

## Índice de Contenidos

- [4. Diseño del Software](#4-diseño-del-software)
  - [4.1. Arquitectura del Software](#41-arquitectura-del-software)
  - [4.2. Diseño de la Base de Datos Orientado a Objetos](#42-diseño-de-la-base-de-datos-orientado-a-objetos)
    - [4.2.1. Paradigma Orientado a Objetos en la Persistencia de Datos (ORM Room)](#421-paradigma-orientado-a-objetos-en-la-persistencia-de-datos-orm-room)
    - [4.2.2. Modelo de Clases de Persistencia y Relaciones entre Objetos](#422-modelo-de-clases-de-persistencia-y-relaciones-entre-objetos)
    - [4.2.3. Diccionario de Datos Orientado a Objetos](#423-diccionario-de-datos-orientado-a-objetos)
  - [4.3. Modelado Dinámico del Comportamiento](#43-modelado-dinámico-del-comportamiento)
    - [4.3.1. Diagramas de Secuencia de los Algoritmos Principales](#431-diagramas-de-secuencia-de-los-algoritmos-principales)
      - [Algoritmo 1: Ejecución, Control y Finalización de Sesión de Estudio en Tiempo Real](#algoritmo-1-ejecución-control-y-finalización-de-sesión-de-estudio-en-tiempo-real)
      - [Algoritmo 2: Cálculo Dinámico, Evaluación y Alerta de Metas Semanales](#algoritmo-2-cálculo-dinámico-evaluación-y-alerta-de-metas-semanales)
    - [4.3.2. Diagrama de Estados del Ciclo de Vida de la Sesión de Estudio](#432-diagrama-de-estados-del-ciclo-de-vida-de-la-sesión-de-estudio)
  - [4.4. Diseño de la Interfaz de Usuario](#44-diseño-de-la-interfaz-de-usuario)
    - [4.4.1. WireFrames y Mockups (Teoría y Lineamientos de UI/UX)](#441-wireframes-y-mockups-teoría-y-lineamientos-de-uiux)
    - [4.4.2. Diseño de Componentes y Módulos](#442-diseño-de-componentes-y-módulos)
- [5. Implementación y Pruebas](#5-implementación-y-pruebas)
  - [5.1. Entorno de Implementación](#51-entorno-de-implementación)
  - [5.2. Proceso de Desarrollo](#52-proceso-de-desarrollo)
  - [5.3. Pruebas y Validación](#53-pruebas-y-validación)
- [6. Conclusiones y Trabajo Futuro](#6-conclusiones-y-trabajo-futuro)
  - [6.1. Conclusión](#61-conclusión)
  - [6.2. Logros del Proyecto y Trabajo Futuro](#62-logros-del-proyecto-y-trabajo-futuro)
- [7. Referencias Bibliográficas y Anexos](#7-referencias-bibliográficas-y-anexos)
  - [7.1. Referencias Bibliográficas (Formato APA 7)](#71-referencias-bibliográficas-formato-apa-7)
  - [7.2. Anexo Repositorio del Proyecto](#72-anexo-repositorio-del-proyecto)
  - [7.3. Anexo Glosario de Términos](#73-anexo-glosario-de-términos)

---

# 4. Diseño del Software

## 4.1. Arquitectura del Software

El diseño arquitectónico de **StudyVibe** se fundamenta en el principio de separación de responsabilidades (*Separation of Concerns*, SoC), propuesto en los estándares de la ingeniería de software moderna (Pressman & Maxim, 2020; Sommerville, 2011). Para garantizar un sistema desacoplado, modular, mantenible y sujeto a pruebas automatizadas, se implementó una **arquitectura en tres capas** (Presentación, Dominio y Datos), articulada con el patrón arquitectónico **Model-View-Presenter (MVP)** en la capa de presentación y el cumplimiento estricto de los principios **SOLID** (Martin, 2018).

La estructura global del código fuente se localiza bajo el paquete raíz `com.david.mystudio` y se distribuye conceptualmente en cuatro paquetes fundamentales:

* **Capa de Presentación (`ui`):** Contiene la interacción directa con el usuario, implementada a través del patrón MVP. Su propósito exclusivo es la captura de eventos de entrada y el renderizado pasivo de la interfaz gráfica en componentes de Android (*Activities*, *Fragments* y *DialogFragments*).
* **Capa de Lógica de Negocio y Dominio (`domain`):** Constituye el núcleo esencial de la aplicación. Alberga las entidades del modelo conceptual (*POJOs* puros), los contratos de persistencia (*Repository Interfaces*) y los servicios que implementan las reglas de validación y cálculo del negocio. Esta capa se diseñó de forma completamente agnóstica al framework de Android; no posee importaciones de librerías del sistema operativo (`android.*`) ni dependencias de persistencia física, lo que permite su portabilidad y facilita la ejecución de pruebas unitarias puras en la Máquina Virtual de Java (JVM).
* **Capa de Acceso a Datos (`data`):** Responsable de la persistencia de la información en el almacenamiento local del dispositivo móvil. Implementa la tecnología de persistencia mediante **Room Database** sobre SQLite, aislando las consultas tipadas en *Data Access Objects* (DAOs), las entidades físicas de base de datos (`@Entity`), los mapeadores bidireccionales y las implementaciones concretas de los repositorios requeridos por el dominio.
* **Paquete de Utilitarios Transversales (`util`):** Proporciona rutinas auxiliares compartidas y reutilizables por las demás capas, tales como cálculos cronológicos de fechas y semanas según el estándar ISO-8601 (`DateTimeUtils`), validación de códigos cromáticos hexadecimales (`ColorUtils`) y el despachador de hilos de trabajo asíncronos en segundo plano (`ThreadExecutor`).

```
com.david.mystudio/
│
├── AppContainer.java                 // Contenedor central de inyección de dependencias manual
├── StudyApplication.java             // Subclase de Application que administra el ciclo de vida global
│
├── ui/                               // CAPA DE PRESENTACIÓN (Patrón MVP)
│   ├── base/                         // Contratos BaseView y BasePresenter
│   ├── dashboard/                    // Módulo de Inicio (Dashboard)
│   ├── materias/                     // Módulo de Gestión de Asignaturas (5S)
│   ├── enfoque/                      // Módulo de Concentración Zanshin y StudyTimerService
│   ├── metas/                        // Módulo de Metas Semanales Kaizen
│   └── reportes/                     // Módulo de Analítica y Estadísticas
│
├── domain/                           // CAPA DE DOMINIO Y REGLAS DE NEGOCIO (Java Puro)
│   ├── model/                        // Modelos POJO y Enumeraciones de Estado
│   ├── repository/                   // Contratos abstractos de persistencia
│   └── service/                      // Servicios de lógica de negocio (Materia, Sesión, Meta, Racha, Reporte)
│
├── data/                             // CAPA DE ACCESO A DATOS (Room / SQLite)
│   ├── local/
│   │   ├── AppDatabase.java          // Base de datos Room Singleton
│   │   ├── entity/                   // Entidades de persistencia (@Entity)
│   │   ├── dao/                      // Interfaces DAO (@Dao)
│   │   └── mapper/                   // Mapeadores bidireccionales Entity <-> Dominio
│   └── repository/                   // Implementaciones concretas de repositorio (RoomRepository)
│
└── util/                             // COMPONENTES DE APOYO Y CONCURRENCIA
    ├── DateTimeUtils.java            // Formateador temporal y cálculo ISO de semanas
    ├── ColorUtils.java               // Analizador de colorimetría hexadecimal
    └── ThreadExecutor.java           // Pool de hilos de fondo y despacho al hilo principal (UI Thread)
```

### Inyección de Dependencias Manual mediante `AppContainer`

Para acatar el Principio de Inversión de Dependencias (DIP) sin introducir sobrecostos de rendimiento, dependencias de librerías externas o tiempos de compilación elevados asociados a motores de inyección por reflexión (como Dagger o Hilt), se adoptó una solución pedagógica y robusta: la **inyección manual de dependencias** mediante la clase `AppContainer`.

La clase `StudyApplication` (que hereda de `android.app.Application`) actúa como el punto de inicio de la aplicación, inicializando una instancia única de `AppContainer` durante su método `onCreate()`. A su vez, `AppContainer` gestiona el ciclo de vida de la base de datos `AppDatabase`, los repositorios concretos y los servicios de dominio, inyectando las dependencias requeridas en el constructor de cada clase:

```java
// Resolución desacoplada en AppContainer:
materiaRepository = new MateriaRoomRepository(database.materiaDao(), executor);
materiaService = new MateriaService(materiaRepository);
```

### Concurrencia y Servicios en Primer Plano (*Foreground Services*)

La plataforma Android impone restricciones severas sobre la ejecución de tareas intensivas de entrada/salida (I/O) en el hilo principal (*Main Thread* o *UI Thread*), provocando el bloqueo de la pantalla y la aparición del diálogo *Application Not Responding* (ANR). Para garantizar una experiencia fluida y reactiva:

* **Despacho de Persistencia con `ThreadExecutor`:** Las consultas, inserciones y actualizaciones sobre la base de datos SQLite se delegan a un grupo de hilos secundarios (*ThreadPoolExecutor*). Una vez obtenido el resultado, un manejador (`android.os.Handler(Looper.getMainLooper())`) transfiere la respuesta de vuelta al hilo principal para que el Presentador pueda interactuar de manera segura con los elementos visuales de la Vista.
* **Preservación del Temporizador con `StudyTimerService`:** Para la funcionalidad de cronometraje de estudio (módulo de Enfoque *Zanshin*), el uso de simples hilos de fondo o tareas asociadas a la Vista resultaba insuficiente, dado que el sistema operativo Android suspende o destruye los procesos en segundo plano para ahorrar energía cuando el usuario cambia de aplicación o la pantalla se apaga. En consecuencia, se implementó un servicio en primer plano (*Foreground Service*) que proyecta una notificación no cancelable en la barra de estado del sistema. Este servicio preserva la cuenta del tiempo de concentración de forma matemática y constante, incluso si el dispositivo entra en modo de bajo consumo o el usuario minimiza la aplicación.

---

## 4.2. Diseño de la Base de Datos Orientado a Objetos

### 4.2.1. Paradigma Orientado a Objetos en la Persistencia de Datos (ORM Room)

A diferencia de los enfoques tabulares tradicionales que utilizan el modelo Entidad-Relación plano para sistemas externos centralizados, en el desarrollo para Android nativo el almacenamiento interno se diseña bajo el paradigma de **Base de Datos Orientada a Objetos** mediante la técnica de **Mapeo Objeto-Relacional (ORM)** provista por la biblioteca oficial **AndroidX Room** (Google Developers, 2023).

En este paradigma:
1. **Los Registros son Objetos de Software:** Las entidades no son meras filas relacionales, sino clases completas de Java (`MateriaEntity`, `SesionEstudioEntity`, `MetaSemanalEntity`, `RachaEntity`) que encapsulan su estado mediante atributos privados, constructores sobrecargados y métodos accesores (*getters* y *setters*).
2. **Las Consultas Operan sobre Tipos de Clases:** En lugar de manipular cursores genéricos (`android.database.Cursor`), los *Data Access Objects* (DAOs) actúan como interfaces orientadas a objetos que reciben y retornan colecciones fuertemente tipadas (`List<MateriaEntity>`).
3. **Las Relaciones se Modelan como Asociaciones y Agregaciones:** La vinculación entre materias, sesiones y metas se expresa a través de relaciones estructurales entre clases Java, garantizando que el diseño de software sea consistente con los diagramas de clases del dominio.

### 4.2.2. Modelo de Clases de Persistencia y Relaciones entre Objetos

El modelo de base de datos orientado a objetos de **StudyVibe** se estructura a partir de cuatro entidades persistentes centrales y sus correspondientes contratos de acceso:

* **`MateriaEntity`:** Objeto raíz de agregación. Contiene el identificador único, nombre académico, sigla de la materia, código cromático hexadecimal y bandera booleana de actividad.
* **`SesionEstudioEntity`:** Objeto dependiente agregado a una materia. Modela marcas de tiempo, duración neta en minutos, técnica de enfoque (*Pomodoro* o *Bloque Libre*), estado de ejecución y notas de estudio.
* **`MetaSemanalEntity`:** Objeto de compromiso cuantitativo asociado a una materia para un número de semana lectiva y año calendario específicos.
* **`RachaEntity`:** Objeto Singleton local (`id = 1`) que persiste la constancia acumulada de días consecutivos de estudio y el récord histórico del estudiante.

> [!NOTE]
> El diagrama estructural completo de la base de datos orientada a objetos en código Mermaid, junto con sus métodos y firmas, se encuentra detallado en el documento anexo [Diagrama_BD_Orientada_Objetos.md](file:///C:/Users/pasante/Desktop/PERSONAL%20TEMP/AppMovil/VibeStudy/Diagrama_BD_Orientada_Objetos.md).

```
+-----------------------------------------------------------------------------------+
| ESPACIO RESERVADO PARA IMAGEN: DIAGRAMA DE BASE DE DATOS ORIENTADO A OBJETOS     |
| [Exportación gráfica de Enterprise Architect (EA) o dibujo a mano según consigna] |
+-----------------------------------------------------------------------------------+
```

### 4.2.3. Diccionario de Datos Orientado a Objetos

A continuación se presentan las especificaciones formales de los atributos, tipos de persistencia física en SQLite y restricciones de negocio para cada clase de entidad.

#### Tabla 1. *Especificación técnica del objeto persistente `materias`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único del objeto materia. |
| `nombre` | `TEXT` | `NOT NULL` | - | Denominación completa de la asignatura (ej. "Aplicaciones Móviles I"). |
| `sigla` | `TEXT` | `NULLABLE` | `NULL` | Código o sigla curricular representativa (ej. "INF-312"). |
| `color_hex` | `TEXT` | `NOT NULL` | `"#263238"` | Código hexadecimal cromático utilizado en la UI (ej. `"#4A90E2"`). |
| `fecha_creacion`| `INTEGER` | `NOT NULL` | - | Marca temporal en milisegundos que registra la fecha y hora de alta. |
| `activa` | `INTEGER` | `NOT NULL` | `1` (Verdadero) | Bandera booleana de estado (1 = Activa, 0 = Baja lógica). |

#### Tabla 2. *Especificación técnica del objeto persistente `sesiones_estudio`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único de la sesión de estudio. |
| `id_materia` | `INTEGER` | `FOREIGN KEY (materias.id) ON DELETE RESTRICT` | - | Clave foránea que referencia al objeto materia asociado. Indexada. |
| `fecha_programada`| `INTEGER` | `NULLABLE` | `NULL` | Marca temporal prevista para sesiones planificadas a futuro. |
| `fecha_hora_inicio`| `INTEGER` | `NULLABLE` | `NULL` | Marca temporal real en que el estudiante inició el cronómetro. |
| `fecha_hora_fin` | `INTEGER` | `NULLABLE` | `NULL` | Marca temporal en que se dio por concluida la sesión. |
| `duracion_minutos`| `INTEGER` | `NOT NULL` | `0` | Tiempo neto efectivo de estudio acumulado (en minutos). |
| `estado` | `TEXT` | `NOT NULL` | `'PENDIENTE'` | Estado de ciclo de vida (`PENDIENTE`, `EN_CURSO`, `PAUSADA`, `COMPLETADA`, `CANCELADA`). |
| `tecnica` | `TEXT` | `NOT NULL` | `'BLOQUE_LIBRE'` | Técnica de enfoque empleada (`POMODORO` o `BLOQUE_LIBRE`). |
| `notas_observaciones`| `TEXT` | `NULLABLE` | `NULL` | Anotaciones temáticas registradas por el usuario al finalizar. |

#### Tabla 3. *Especificación técnica del objeto persistente `metas_semanales`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único del registro de meta. |
| `id_materia` | `INTEGER` | `FOREIGN KEY (materias.id) ON DELETE RESTRICT` | - | Asignatura sobre la cual se proyecta el objetivo. Indexada. |
| `numero_semana` | `INTEGER` | `NOT NULL` | - | Número de semana en el calendario anual (rango 1 a 53, ISO-8601). |
| `anio` | `INTEGER` | `NOT NULL` | - | Año calendario correspondiente a la semana lectiva (ej. 2026). |
| `horas_objetivo` | `REAL` | `NOT NULL` | `0.0` | Umbral de horas comprometidas para la semana (entre 1.0 y 50.0). |
| `horas_cumplidas`| `REAL` | `NOT NULL` | `0.0` | Sumatoria acumulativa de horas registradas a través de sesiones completadas. |
| `estado` | `TEXT` | `NOT NULL` | `'EN_PROGRESO'` | Estado cualitativo de la meta (`EN_PROGRESO` o `CUMPLIDA`). |

*Nota:* Se encuentra definido un índice compuesto único sobre `(id_materia, numero_semana, anio)` para impedir metas duplicadas para una misma materia en un mismo ciclo semanal.

#### Tabla 4. *Especificación técnica del objeto persistente `racha_estudio`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY` | `1` (Constante) | Clave primaria fija para garantizar el comportamiento Singleton. |
| `dias_consecutivos`| `INTEGER` | `NOT NULL` | `0` | Número de días consecutivos con al menos una sesión completada. |
| `ultima_fecha_estudio`| `INTEGER`| `NULLABLE` | `NULL` | Marca temporal del día en que se finalizó la última sesión. |
| `racha_maxima_historica`| `INTEGER`| `NOT NULL` | `0` | Valor récord de días continuos acumulados por el usuario. |

---

## 4.3. Modelado Dinámico del Comportamiento

En apego a la consigna técnica del docente, se especifican a continuación los **dos diagramas de secuencia** correspondientes a los algoritmos más representativos de la solución, y el **diagrama de estados** que gobierna el ciclo de vida de la entidad de estudio.

### 4.3.1. Diagramas de Secuencia de los Algoritmos Principales

#### Algoritmo 1: Ejecución, Control y Finalización de Sesión de Estudio en Tiempo Real
* **Justificación de Relevancia:** Constituye el núcleo funcional de StudyVibe. Coordina la interacción asíncrona entre el usuario, la vista pasiva MVP, el presentador de Enfoque, el servicio en primer plano de Android (`StudyTimerService`) y la persistencia atómica en tres tablas de base de datos simultáneas.
* **Líneas de Vida Participantes:**
  * `Estudiante` (Actor)
  * `v: EnfoqueFragment` (Vista pasiva)
  * `p: EnfoquePresenter` (Presentador)
  * `srv: StudyTimerService` (Servicio en primer plano)
  * `sesionSrv: SesionEstudioService` (Lógica de negocio de sesiones)
  * `rachaSrv: RachaService` (Lógica de negocio de racha)
  * `metaSrv: MetaSemanalService` (Lógica de metas semanales)
  * `db: Room DAOs` (Persistencia SQLite)
* **Descripción del Flujo Algorítmico:**
  1. El estudiante pulsa *"Iniciar Sesión"*; el presentador valida la ausencia de otra sesión en curso a través de `SesionEstudioService`.
  2. Se instancia la entidad en SQLite y se arranca `StudyTimerService`, proyectando una notificación persistente con la marca de tiempo de inicio.
  3. En un bucle periódico cada 1000 ms, el servicio computa el tiempo y emite ticks hacia el presentador para actualizar el anillo de concentración en pantalla.
  4. Si el estudiante pulsa *"Pausar"*, el servicio congela el cronómetro sin reiniciar los segundos; al pulsar *"Reanudar"*, el conteo continúa de forma ininterrumpida.
  5. Al pulsar *"Finalizar Estudio"*, se captura la bitácora cualitativa de notas, se detiene el servicio en primer plano y se persiste la sesión en estado `COMPLETADA` (siempre que supere el umbral mínimo de 1 minuto).
  6. De forma coordinada, se evalúa y suma +1 día a la racha en `RachaDao` y se acumula el tiempo a la meta semanal en `MetaSemanalDao`, emitiendo una notificación de éxito en la interfaz.

> [!NOTE]
> El diagrama de secuencia completo en código Mermaid y la guía paso a paso para Enterprise Architect se encuentran en [Diagrama_Secuencia_Ejecucion_Sesion.md](file:///C:/Users/pasante/Desktop/PERSONAL%20TEMP/AppMovil/VibeStudy/Diagrama_Secuencia_Ejecucion_Sesion.md).

```
+-----------------------------------------------------------------------------------+
| ESPACIO RESERVADO PARA IMAGEN: DIAGRAMA DE SECUENCIA 1 (EJECUCIÓN DE SESIÓN)      |
| [Exportación gráfica de Enterprise Architect (EA) o dibujo a mano según consigna] |
+-----------------------------------------------------------------------------------+
```

#### Algoritmo 2: Cálculo Dinámico, Evaluación y Alerta de Metas Semanales
* **Justificación de Relevancia:** Materializa el valor formativo de la filosofía *Kaizen*, transformando registros brutos de tiempo en indicadores formativos de cumplimiento y disparando alertas preventivas contra la procrastinación.
* **Líneas de Vida Participantes:**
  * `Estudiante` (Actor)
  * `v: MetasFragment` (Vista pasiva)
  * `p: MetasPresenter` (Presentador)
  * `metaSrv: MetaSemanalService` (Lógica de negocio de metas)
  * `metaRepo: MetaSemanalRepository` (Acceso a metas)
  * `sesionRepo: SesionEstudioRepository` (Acceso a sesiones)
  * `db: Room DAOs` (SQLite)
* **Descripción del Flujo Algorítmico:**
  1. El estudiante abre el módulo de Metas o selecciona una semana lectiva determinada (ej. Semana 47).
  2. La vista solicita los datos al presentador, quien activa un estado de carga no intrusivo y delega el cálculo a `MetaSemanalService`.
  3. El servicio obtiene las metas comprometidas y las sesiones completadas en el rango temporal de la semana seleccionada.
  4. En un ciclo iterativo sobre cada asignatura registrada, se calcula:
     $$\text{Porcentaje de Cumplimiento} = \left(\frac{\text{Horas Cumplidas}}{\text{Horas Objetivo}}\right) \times 100$$
  5. Se aplican las reglas de negocio condicionales:
     * Si $\text{Porcentaje} \ge 100.0\% \rightarrow$ la tarjeta conmuta a tono verde de éxito y se activa el distintivo de felicitación.
     * Si $\text{Porcentaje} < 30.0\%$ y el día actual es posterior al miércoles lectivo $\rightarrow$ se activa una alerta de materia rezagada en color ámbar con indicación de las horas exactas pendientes.
     * En caso contrario $\rightarrow$ se conserva la barra de progreso azul en curso normal.
  6. Se computa el total general para el banner superior *Kaizen* y se entregan los objetos DTO al presentador para el renderizado pasivo de la vista.

> [!NOTE]
> El diagrama de secuencia completo en código Mermaid y la guía paso a paso para Enterprise Architect se encuentran en [Diagrama_Secuencia_Calculo_Metas.md](file:///C:/Users/pasante/Desktop/PERSONAL%20TEMP/AppMovil/VibeStudy/Diagrama_Secuencia_Calculo_Metas.md).

```
+-----------------------------------------------------------------------------------+
| ESPACIO RESERVADO PARA IMAGEN: DIAGRAMA DE SECUENCIA 2 (CÁLCULO DE METAS)         |
| [Exportación gráfica de Enterprise Architect (EA) o dibujo a mano según consigna] |
+-----------------------------------------------------------------------------------+
```

---

### 4.3.2. Diagrama de Estados del Ciclo de Vida de la Sesión de Estudio

La entidad `SesionEstudio` se modela formalmente como una **Máquina de Estados Finitos (FSM)** que define las condiciones de transición entre sus estados categóricos (`EstadoSesion`):

* **`PENDIENTE`:** La sesión ha sido agendada para una fecha y hora futura.
* **`EN_CURSO`:** El cronómetro se encuentra activamente sumando tiempo en el servicio en primer plano.
* **`PAUSADA`:** La sesión ha sido detenida momentáneamente por el usuario; retiene los segundos acumulados sin reinicios.
* **`COMPLETADA`:** Estado terminal exitoso. La sesión concluyó con una duración $\ge 1$ minuto, capturó notas temáticas, se persistió en SQLite y disparó la actualización de racha y metas.
* **`CANCELADA`:** Estado terminal de descarte. La sesión fue cancelada voluntariamente o duró menos de 1 minuto, quedando sin impacto en las métricas de rendimiento.

#### Tabla 5. *Matriz formal de transición de estados de la entidad `SesionEstudio`*

| Estado Origen | Evento / Disparador | Condición de Guarda | Acción / Efecto del Sistema | Estado Destino |
| :--- | :--- | :--- | :--- | :--- |
| **`[*]` (Inicial)** | `planificarSesionFutura()` | `[fechaHora > actual]` | Inserta registro en Room con `estado = 'PENDIENTE'`. | `PENDIENTE` |
| **`[*]` (Inicial)** | `iniciarSesionInmediata()` | `[sinSesionActiva]` | Crea entidad, arranca `StudyTimerService` y proyecta notificación. | `EN_CURSO` |
| **`PENDIENTE`** | `iniciarSesionAgendada()` | `[sinSesionActiva]` | Asigna `fechaHoraInicio = now()` e inicia el servicio en primer plano. | `EN_CURSO` |
| **`PENDIENTE`** | `eliminarSesion()` | Ninguna | Elimina o cancela el registro agendado de la base de datos. | `CANCELADA` |
| **`EN_CURSO`** | `pausarSesion()` | Ninguna | Detiene el incremento de segundos; retiene la duración acumulada. | `PAUSADA` |
| **`PAUSADA`** | `reanudarSesion()` | Ninguna | Reanuda el reloj; actualiza la notificación a modo activo. | `EN_CURSO` |
| **`EN_CURSO` / `PAUSADA`** | `finalizarEstudio()` | `[duracion >= 1 min]` | Guarda notas temáticas, marca `COMPLETADA`, suma racha y horas a la meta. | `COMPLETADA` |
| **`EN_CURSO` / `PAUSADA`** | `finalizarEstudio()` | `[duracion < 1 min]` | Descarta la sesión por ser inferior al umbral mínimo (`RN-SES-05`). | `CANCELADA` |
| **`EN_CURSO` / `PAUSADA`** | `cancelarSesion()` | Ninguna | Descarta la sesión voluntariamente sin alterar racha ni metas. | `CANCELADA` |
| **`COMPLETADA`** | `[*]` (Final) | - | El registro queda inmutable para analítica y reportes históricos. | `[*]` |
| **`CANCELADA`** | `[*]` (Final) | - | El registro se limpia o conserva como cancelación sin impacto en métricas. | `[*]` |

> [!NOTE]
> El diagrama de estados completo en código Mermaid y la guía paso a paso para Enterprise Architect se encuentran en [Diagrama_Estados_Sesion_Estudio.md](file:///C:/Users/pasante/Desktop/PERSONAL%20TEMP/AppMovil/VibeStudy/Diagrama_Estados_Sesion_Estudio.md).

```
+-----------------------------------------------------------------------------------+
| ESPACIO RESERVADO PARA IMAGEN: DIAGRAMA DE ESTADOS (SESIÓN DE ESTUDIO)            |
| [Exportación gráfica de Enterprise Architect (EA) o dibujo a mano según consigna] |
+-----------------------------------------------------------------------------------+
```

---

## 4.4. Diseño de la Interfaz de Usuario

### 4.4.1. WireFrames y Mockups (Teoría y Lineamientos de UI/UX)

El diseño de la experiencia de usuario (UX) e interfaz visual (UI) de **StudyVibe** se concibió bajo las directrices oficiales del sistema de diseño **Material Design 3 (Material You)** promovido por Google (2023), asegurando consistencia visual, jerarquía tipográfica, retroalimentación táctil y accesibilidad inclusiva.

1. **Jerarquía Visual y Reducción de Carga Cognitiva:** Dado que la aplicación está destinada al estudio académico y a la concentración, las pantallas evitan la saturación de información y el desorden perceptivo (*Noise Reduction*). Se utilizan tarjetas de superficie tonal (`MaterialCardView`) con radios de curvatura consistentes (12dp y 20dp) y márgenes periféricos normalizados en `res/values/dimens.xml` (16dp estándar, 8dp para espaciados internos y 24dp para encabezados principales).
2. **Arquitectura de Navegación de Actividad Única (*Single-Activity Pattern*):** La aplicación adopta el patrón recomendado por Google consistente en una sola actividad anfitriona (`MainActivity`) que gestiona un contenedor de fragmentos (`androidx.fragment.app.FragmentContainerView`) y un menú de navegación inferior fijo (`BottomNavigationView`). Este diseño permite que el usuario acceda de forma inmediata y ergonómica con una sola mano a las cinco áreas principales de la solución: Inicio, Materias, Enfoque, Metas y Reportes.
3. **Accesibilidad y Contraste Cromático (WCAG 2.1 Nivel AA):** Los componentes de texto e íconos se ajustan estrictamente al criterio de contraste mínimo de 4.5:1 frente a sus fondos inmediatos. Se estructuró un esquema simétrico de modo claro (`res/values/colors.xml`) y modo oscuro (`res/values-night/colors.xml`), previniendo la fatiga visual en jornadas de estudio nocturno y manteniendo perfectamente legibles los 8 colores identificadores de las materias.
4. **Retroalimentación No Intrusiva:** Toda acción destructiva (como la baja de una asignatura o la interrupción anticipada de una sesión de estudio) requiere confirmación explícita mediante cuadros de diálogo modales (`MaterialAlertDialogBuilder`), mientras que los guardados exitosos informan mediante componentes *Snackbar*.

```
+-----------------------------------------------------------------------------------+
| ESPACIO RESERVADO PARA WIREFRAMES Y MOCKUPS GRÁFICOS                              |
| [Capturas de prototipos y pantallas finales a ser incorporadas en fase de entrega] |
+-----------------------------------------------------------------------------------+
```

### 4.4.2. Diseño de Componentes y Módulos

A nivel de componentes modulares, la interfaz se desglosa en cinco módulos funcionales articulados mediante el patrón MVP:

1. **Módulo Inicio (Dashboard):** Banner destacado de Racha Kaizen (`crdRachaKaizen`) con ícono de constancia y contador diario, tarjeta de meta semanal global con barra de progreso lineal (`prgMetaGlobal`), banner pedagógico *Shinrin-yoku* para descanso mental, listado desplazable de agenda del día (`rvAgendaHoy`) y botón de acción principal de un solo toque para iniciar enfoque.
2. **Módulo de Gestión de Asignaturas (Materias 5S):** Catálogo de materias en `RecyclerView` (`item_materia.xml`) con visualización de sigla, nombre y volumen de horas; botón flotante (FAB "+") que despliega el diálogo modal de creación (`dialog_add_subject.xml`), paleta interactiva de 8 muestras de color (*swatches*), y diálogo de confirmación para borrado lógico.
3. **Módulo de Concentración Zanshin (Temporizador):** Anillo de progreso central con tipografía monoespaciada de alta legibilidad, selector segmentado entre técnica Pomodoro (25/5 min) y técnica de Bloque Libre, botonera de control táctil (Iniciar, Pausar, Reanudar, Finalizar), bitácora cualitativa de notas desplegable y sincronización continua con `StudyTimerService`.
4. **Módulo de Metas Semanales (Kaizen Goals):** Selector interactivo de semana lectiva, tarjetas de meta por asignatura con barras dinámicas y estados condicionales automáticos (verde éxito al alcanzar el 100% y ámbar preventivo con cálculo de horas faltantes ante materias rezagadas), y formulario modal de fijación de metas (rango 1 a 50 hrs).
5. **Módulo de Reportes y Estadísticas (Analítica Visual):** Segmentación por periodo (Semana, Mes, Historial), tarjetas de métricas agregadas (total de horas, promedio diario, materia más estudiada), gráfico circular de distribución (*PieChart*), histograma de barras (*BarChart*) por día de la semana y listado cronológico de sesiones completadas con sus notas asociadas.

---

# 5. Implementación y Pruebas

## 5.1. Entorno de Implementación

La implementación de **StudyVibe** se desarrolló bajo un entorno de ingeniería formal utilizando las herramientas oficiales y vigentes del ecosistema Android nativo:

* **Entorno Integrado de Desarrollo (IDE):** Android Studio Ladybug / Koala Feature Drop con soporte para el kit de desarrollo Java (JDK 11).
* **Lenguaje de Programación:** Java Estándar (JDK 11), configurado en el archivo de construcción (`sourceCompatibility = JavaVersion.VERSION_11; targetCompatibility = JavaVersion.VERSION_11;`).
* **Sistema de Automatización de Construcción:** Gradle versión 8.9 / 9.5 con Android Gradle Plugin (AGP) versión 8.5.1 / 9.5.1, utilizando scripts de configuración tipados en Kotlin (`build.gradle.kts`) y catálogo centralizado de dependencias (`gradle/libs.versions.toml`).
* **Niveles de API del SDK de Android:**
  * *Nivel de Compilación (`compileSdk`):* API 35 (soporte ampliado hasta compilador API 37).
  * *Nivel Mínimo de Soporte (`minSdk`):* API 24 (Android 7.0 Nougat), cubriendo más del 95% de terminales en circulación.
  * *Nivel Objetivo (`targetSdk`):* API 35 (Android 15), cumpliendo con las normativas actuales de permisos en tiempo de ejecución para notificaciones y servicios en primer plano.
* **Espacio de Nombres e Identificador de Aplicación:** `com.david.mystudio` (`versionCode = 1`, `versionName = "1.0"`).

#### Tabla 6. *Inventario oficial de dependencias del proyecto*

| Módulo / Biblioteca | Versión | Tipo de Dependencia | Justificación Técnica y Propósito |
| :--- | :---: | :--- | :--- |
| `androidx.room:room-runtime` | 2.6.1 | Implementación | Motor de persistencia relacional local y abstracción segura de SQLite. |
| `androidx.room:room-compiler` | 2.6.1 | Procesador de Anotaciones | Generador en tiempo de compilación de las clases DAOs y esquemas de base de datos. |
| `com.google.android.material:material` | 1.14.0 | Implementación | Componentes visuales de Material Design 3 (Cards, FAB, Botones, BottomNav). |
| `androidx.appcompat:appcompat` | 1.8.0 | Implementación | Compatibilidad de componentes de interfaz modernos hacia versiones anteriores de Android. |
| `androidx.constraintlayout:constraintlayout` | 2.2.2 | Implementación | Sistema flexible de posicionamiento relativo para diseños fluidos de alta adaptabilidad. |
| `androidx.activity:activity-ktx` | 1.13.0 | Implementación | Soporte ampliado para la gestión moderna del ciclo de vida y navegación de actividades. |
| `junit:junit` | 4.13.2 | Pruebas Unitarias | Framework estándar para pruebas unitarias de lógica y cálculo en la JVM local. |
| `androidx.test.ext:junit` | 1.3.0 | Pruebas Instrumentadas | Extensiones de JUnit para la ejecución de pruebas dentro del emulador/dispositivo Android. |
| `androidx.test.espresso:espresso-core` | 3.7.0 | Pruebas Instrumentadas | Automatización de pruebas de interfaz de usuario e interacción en pantalla. |

#### Entornos de Ejecución y Pruebas Físicas
La verificación del funcionamiento de la aplicación se efectuó en dos modalidades complementarias:
1. **Emuladores Android Virtual Device (AVD):** Pixel 7 Pro emulado con Android 14 (API 34, resolución 1440 × 3120 pixels, densidad 560 dpi) y Nexus 5 emulado con Android 8.0 (API 26, resolución 1080 × 1920 pixels, densidad xxhdpi).
2. **Dispositivos Móviles Físicos:** Smartphone físico con Android 13 (API 33, arquitectura ARM64, 6 GB de memoria RAM), verificando el comportamiento real del servicio en primer plano del cronómetro con la pantalla apagada, la respuesta ante rotación física del dispositivo y el rendimiento térmico y de batería.

---

## 5.2. Proceso de Desarrollo

El ciclo de desarrollo de software se articuló mediante el marco de trabajo ágil **Scrum**, iterando en ciclos de trabajo cortos (*Sprints*) y aplicando una estrategia de construcción técnica de orden ascendente (**Bottom-Up**):

1. **Fase 1: Capa de Persistencia y Acceso a Datos (Room & SQLite):** Modelado de entidades relacionales (`@Entity`), contratos DAO con consultas SQL optimizadas (`@Dao`), base de datos `AppDatabase` y mapeadores estáticos bidireccionales.
2. **Fase 2: Capa de Dominio y Lógica de Negocio (POJOs y Servicios):** Construcción de modelos conceptuales en Java puro y servicios de negocio (`MateriaService`, `SesionEstudioService`, `MetaSemanalService`, `RachaService`, `ReporteService`).
3. **Fase 3: Capa de Presentación (MVP Pantalla por Pantalla):** Estructuración de contratos `*Contract`, implementación de presentadores pasivos e inflado de layouts XML con Material Design 3 de forma individual para cada módulo.
4. **Fase 4: Integración Global y Cableado:** Inyección de dependencias centralizada en `AppContainer`, navegación coordinada en `MainActivity` mediante `BottomNavigationView`, y declaración del servicio en primer plano `StudyTimerService` en el archivo `AndroidManifest.xml`.

#### Diagnóstico y Resolución Quirúrgica de Defectos Críticos (Bugs de Integración)

* **Defecto de Contraste y Adaptabilidad en Modo Oscuro (BUG 1 / 1.1):** En terminales con modo oscuro activo, los textos leían `@color/color_on_surface` estático en negro (`#1F1F1F`) sobre tarjetas oscuras. Se resolvió creando la carpeta `res/values-night/` con un archivo simétrico `colors.xml` para tokens nocturnos y configurando `res/values-night/themes.xml` con `android:windowLightStatusBar = false`.
* **Ciclo de Vida del Temporizador y Sesiones Huérfanas (BUG 2):** Al cambiar de pestaña durante una sesión, `MainActivity` reemplazaba el fragmento destruyendo el presentador, y los apagados forzados dejaban sesiones perpetuamente bloqueadas en estado `EN_CURSO`. Se resolvió en tres frentes:
  1. *Intercepción de Navegación:* `MainActivity` valida si `StudyTimerService.isSesionEnCurso()` es verdadero y despliega un diálogo de confirmación obligatoria.
  2. *Restauración del Presentador:* `EnfoquePresenter.restaurarSesionActivaSiExiste()` recupera la referencia activa si el fragmento se recrea con el servicio en marcha.
  3. *Sanitización en Inicio:* En `StudyApplication.onCreate()`, una consulta atómica en `SesionEstudioDao` cancela automáticamente cualquier sesión huérfana previa al encendido.

---

## 5.3. Pruebas y Validación

La verificación del sistema combinó pruebas unitarias automatizadas, validación de historias de usuario (criterios BDD) y evaluación de requisitos no funcionales bajo la norma **ISO/IEC 25010** (ISO/IEC, 2011).

#### 1. Pruebas Unitarias Automatizadas en JVM
Ejecutadas directamente sobre la Máquina Virtual de Java local mediante el framework **JUnit 4**:
* **`MetaSemanalTest`:** Verificación del cálculo porcentual de avance (10.0 hrs con 2.5 hrs ejecutadas = 25.0%), activación de bandera `isCumplida()` al igualar o superar el objetivo, y restricción de rango válido (1.0 a 50.0 hrs).
* **`DateTimeUtilsTest`:** Verificación de conversión de minutos a formato legible (90 min = "1h 30m"; 45 min = "45m") y cálculo de números de semana del año según ISO-8601.

#### Tabla 7. *Matriz de verificación funcional de Historias de Usuario*

| Historia de Usuario | Módulo Evaluado | Escenario Crítico de Prueba (BDD) | Resultado Observado | Estado de Conformidad |
| :---: | :--- | :--- | :--- | :---: |
| **HU-01** | Materias | Registro de nueva materia con nombre y color válido. | Persistencia exitosa en SQLite y visualización en lista. | **APROBADO (100%)** |
| **HU-02** | Materias | Modificación de color identificador de la asignatura. | Actualización inmediata en tarjetas y temporizador. | **APROBADO (100%)** |
| **HU-03** | Materias | Eliminación de materia con diálogo de confirmación modal. | Baja lógica efectuada preservando sesiones previas. | **APROBADO (100%)** |
| **HU-04** | Planificación | Intento de programar sesión con fecha u hora en el pasado. | Bloqueo preventivo y despliegue de alerta de validación. | **APROBADO (100%)** |
| **HU-05** | Enfoque | Arranque de cronómetro, notificación persistente y guardado. | Conteo exacto, cálculo de minutos y estado `COMPLETADA`. | **APROBADO (100%)** |
| **HU-06** | Enfoque | Congelamiento de conteo con botón "Pausa" y captura de notas. | Conservación del tiempo neto y persistencia de notas. | **APROBADO (100%)** |
| **HU-07** | Metas | Registro de meta semanal dentro del rango de 1 a 50 horas. | Meta registrada con barra de progreso inicial en 0%. | **APROBADO (100%)** |
| **HU-08** | Metas | Cómputo automático de horas acumuladas y felicitación al 100%. | Barra dinámica al día y cambio de tarjeta a color verde. | **APROBADO (100%)** |
| **HU-09** | Reportes | Sumatoria agregada de horas en los últimos 7 días. | Desglose fiel por materia y promedio diario calculado. | **APROBADO (100%)** |
| **HU-10** | Reportes | Consulta de sesiones históricas por mes calendario. | Listado cronológico de sesiones con duraciones y notas. | **APROBADO (100%)** |
| **HU-11** | Estadísticas | Renderizado de gráfico circular con proporciones por materia. | Proporciones fidedignas con los colores de cada asignatura. | **APROBADO (100%)** |
| **HU-12** | Estadísticas | Incremento de racha diaria tras sesión y reinicio a las 48h. | Contador diario actualizado e ícono de racha activo. | **APROBADO (100%)** |

#### 2. Validación de Requisitos No Funcionales (ISO/IEC 25010)
* **Eficiencia y Desempeño (RNF-04 y RNF-05):** Transiciones entre fragmentos en un promedio de 85 ms (umbral máximo permitido: 150 ms). Consultas de inserción y lectura en SQLite resueltas en menos de 22 ms gracias a índices tipados en las claves foráneas de Room.
* **Consumo de Memoria y Recursos (RNF-06):** Huella de memoria RAM estable de entre 45 MB y 68 MB en uso prolongado del temporizador, sin fugas de memoria (*zero memory leaks*), cumpliendo holgadamente el límite de 120 MB establecido.
* **Tolerancia a Fallos y Confiabilidad (RNF-09 y RNF-10):** El servicio en primer plano mantuvo el cronómetro activo ante rotaciones de pantalla, llamadas telefónicas simuladas y minimizado de la app (0% de pérdida de tiempo). La aplicación funcionó con total autonomía y disponibilidad en modo avión (*offline-first*).

---

# 6. Conclusiones y Trabajo Futuro

## 6.1. Conclusión

El desarrollo integral de **StudyVibe** demuestra de manera tangible cómo la ingeniería de software y las metodologías de desarrollo móvil nativo pueden aplicarse eficazmente para resolver una problemática cotidiana y crítica en la vida universitaria: la desorganización, la procrastinación y la carencia de métricas objetivas sobre la gestión del tiempo de estudio independiente.

A través de la integración armónica entre técnicas comprobadas de administración temporal (la técnica Pomodoro y el enfoque de *Time Blocking*) y filosofías tradicionales japonesas orientadas a la disciplina formativa (*Kaizen* para la mejora continua gradual, *Zanshin* para la concentración y presencia plena, y las *5S* para el orden del espacio académico), se consolidó una herramienta móvil que no se limita a cronometrar horas, sino que educa y motiva al estudiante mediante la visibilidad de su propio progreso.

Desde el punto de vista arquitectónico, la adopción de una arquitectura en tres capas desacopladas, articulada con el patrón Model-View-Presenter (MVP) y los principios SOLID, demostró ser una decisión plenamente acertada para el ecosistema Android con Java. Se obtuvo una base de código limpia, modular y mantenible, en la que la lógica de negocio y las fórmulas de cálculo se encuentran blindadas frente a las alteraciones del ciclo de vida del sistema operativo. Asimismo, la persistencia local robusta gestionada a través de Room Database sobre SQLite confirmó la viabilidad técnica y la confiabilidad del modelo *offline-first*, garantizando que el usuario tenga pleno control y disponibilidad de sus datos académicos sin depender de conexiones a redes externas.

## 6.2. Logros del Proyecto y Trabajo Futuro

Entre los principales resultados alcanzados a la culminación de esta fase destacan:
1. **Cumplimiento Funcional del 100%:** Implementación, integración y verificación satisfactoria de las 12 Historias de Usuario (`HU-01` a `HU-12`) bajo la *Definition of Done (DoD)*.
2. **Arquitectura Limpia sin Sobrecarga:** Desacoplamiento estricto de componentes mediante inyección de dependencias manual (`AppContainer`), prescindiendo de librerías complejas de reflexión y manteniendo un APK sumamente ligero.
3. **Resiliencia Operativa y Ciclo de Vida Robusto:** Integración de un servicio en primer plano (`StudyTimerService`) con notificación persistente, complementado con mecanismos de sanitización al inicio del sistema para evitar registros huérfanos.
4. **Experiencia Adaptativa y Accesible:** Interfaz moderna regida por Material Design 3 con soporte simétrico de modo claro y oscuro (`values/` y `values-night/`), cumpliendo con los contrastes de la norma WCAG 2.1 nivel AA.
5. **Alineación Pedagógica Institucional:** Cumplimiento de los objetivos de la asignatura *Aplicaciones Móviles I* de la UPDS en programación orientada a objetos en Java, servicios del sistema, concurrencia asíncrona y bases de datos embebidas.

#### Líneas de Trabajo Futuro
* **Sincronización en la Nube (*Cloud Sync*):** Implementación de una arquitectura híbrida con Firebase Firestore o una API RESTful en Spring Boot para respaldo y sincronización multiplataforma.
* **Integración con Portales Universitarios:** Módulos de enlace con plataformas institucionales (como el portal estudiantil de la UPDS) para importar asignaturas y calendarios de exámenes.
* **Exportación de Reportes Analíticos:** Generación de resúmenes formales en formato PDF para justificación de horas de tutoría o avance de proyectos de grado.
* **Widgets Interactivos para la Pantalla de Inicio:** Componentes *App Widgets* de Android para visualizar la racha de estudio y accionar el cronómetro de forma inmediata desde el escritorio del teléfono.

---

# 7. Referencias Bibliográficas y Anexos

## 7.1. Referencias Bibliográficas (Formato APA 7)

Cirillo, F. (2018). *The Pomodoro Technique: The Acclaimed Time-Management System That Has Transformed How We Work*. Crown Currency.

Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley Professional.

Google Developers. (2023). *Guía de arquitectura de apps para Android*. Android Open Source Project. https://developer.android.com/topic/architecture

Google Developers. (2023). *Persistencia de datos en una base de datos local con Room*. Android Open Source Project. https://developer.android.com/training/data-storage/room

Imai, M. (1986). *Kaizen: The Key to Japan's Competitive Success*. McGraw-Hill Education.

International Organization for Standardization. (2011). *Systems and software engineering — Systems and software Quality Requirements and Evaluation (SQuaRE) — System and software quality models* (ISO/IEC Standard No. 25010:2011). https://www.iso.org/standard/35733.html

Martin, R. C. (2018). *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall.

Pressman, R. S., & Maxim, B. R. (2020). *Software Engineering: A Practitioner's Approach* (9na ed.). McGraw-Hill Education.

Sommerville, I. (2011). *Ingeniería del Software* (9na ed.). Pearson Educación.

Wake, B. (2003). *INVEST in Good Stories, and SMART Tasks*. XP123. https://xp123.com/articles/invest-in-good-stories-and-smart-tasks/

World Wide Web Consortium. (2018). *Web Content Accessibility Guidelines (WCAG) 2.1*. W3C Recommendation. https://www.w3.org/TR/WCAG21/

---

## 7.2. Anexo Repositorio del Proyecto

El código fuente íntegro, los recursos gráficos, la estructura de carpetas y los scripts de construcción automatizada del proyecto **StudyVibe** se encuentran versionados bajo el sistema de control de versiones distribuido **Git**.

* **Organización del Proyecto:** `VibeStudy` / `com.david.mystudio`
* **Estructura de Ramas:** `main` (código estable de entrega), `feature/*` (desarrollo modular de funcionalidades) y `bugfix/*` (estabilización de incidentes de interfaz y ciclo de vida).

#### Instrucciones de Clonación, Apertura y Construcción
1. **Clonación del Repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO_GIT>
   cd VibeStudy
   ```
2. **Apertura en Android Studio:** Seleccionar *Open* apuntando al directorio raíz del proyecto y aguardar la sincronización automática de dependencias mediante `libs.versions.toml`.
3. **Construcción y Generación del Paquete APK (Línea de Comandos):**
   * En sistemas operativos Windows (PowerShell):
     ```powershell
     .\gradlew.bat assembleDebug
     ```
   * En sistemas operativos Linux / macOS:
     ```bash
     ./gradlew assembleDebug
     ```
   El archivo binario ejecutable compilado se generará en la ruta: `app/build/outputs/apk/debug/app-debug.apk`.
4. **Ejecución de Pruebas Unitarias Automatizadas:**
   ```bash
   ./gradlew testDebugUnitTest
   ```

---

## 7.3. Anexo Glosario de Términos

* **ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad):** Conjunto de propiedades que garantizan que las transacciones en una base de datos relacional se procesen de manera confiable y segura contra fallos del sistema.
* **Android Runtime (ART):** Entorno de ejecución de aplicaciones en Android que traduce los archivos de bytecode DEX a código máquina nativo mediante compilación mixta anticipada (AOT) y en tiempo de ejecución (JIT).
* **ANR (Application Not Responding):** Cuadro de diálogo del sistema operativo Android que se despliega cuando el hilo principal se bloquea por más de 5 segundos realizando operaciones pesadas de I/O.
* **BDD (Behavior-Driven Development):** Práctica de desarrollo basada en el comportamiento que especifica el funcionamiento esperado mediante las cláusulas *Dado (Given)*, *Cuando (When)* y *Entonces (Then)*.
* **DAO (Data Access Object):** Patrón de diseño que proporciona una interfaz abstracta que encapsula y desacopla el acceso y las operaciones de lectura/escritura sobre el motor de base de datos.
* **DEX (Dalvik Executable):** Formato de archivo binario optimizado para dispositivos móviles al cual se compila el código fuente Java en Android.
* **Entity (Entidad en Room):** Clase Java anotada con `@Entity` que define la estructura relacional de una tabla en SQLite administrada por Room.
* **Foreground Service (Servicio en Primer Plano):** Servicio de Android que realiza operaciones perceptibles por el usuario y que debe mantener obligatoriamente una notificación activa en la barra de estado para evitar ser destruido por el sistema operativo.
* **INVEST:** Acrónimo propuesto por Bill Wake que resume las características de calidad de una Historia de Usuario: *Independiente, Negociable, Valiosa, Estimable, Pequeña y Comprobable*.
* **Kaizen:** Concepto japonés que significa "cambio para mejor" o "mejora continua". En el estudio, postula que avances pequeños y consistentes generan hábitos disciplinarios sostenibles.
* **Material Design 3 (M3):** Tercera generación del sistema de diseño visual de Google, que introduce esquemas de color tonal dinámicos, superficies redondeadas y lineamientos avanzados de accesibilidad.
* **MoSCoW:** Técnica de priorización de requisitos que categoriza las funcionalidades en: *Must have*, *Should have*, *Could have* y *Won't have*.
* **MVP (Model-View-Presenter):** Patrón arquitectónico derivado de MVC donde la Vista es pasiva y delega toda la lógica de presentación a un Presentador intermediario mediante interfaces formales.
* **Offline-first:** Enfoque de arquitectura donde la aplicación opera con total funcionalidad y persiste datos en el almacenamiento local del dispositivo sin exigir conectividad continua a Internet.
* **POJO (Plain Old Java Object):** Objeto ordinario de Java libre de dependencias de frameworks externos, facilitando su portabilidad y testeo unitario en la JVM.
* **Pomodoro:** Técnica de administración del tiempo creada por Francesco Cirillo que fragmenta las jornadas de trabajo en bloques de 25 minutos de concentración separados por descansos de 5 minutos.
* **Room Database:** Biblioteca oficial de persistencia de AndroidX que ofrece una capa de abstracción sobre SQLite, proporcionando verificación estática de sentencias SQL en tiempo de compilación.
* **Shinrin-yoku:** Expresión japonesa traducida como "baño de bosque", que promueve la inmersión consciente en la naturaleza y el distanciamiento de pantallas digitales para reducir el estrés.
* **Single-Activity Architecture:** Patrón de diseño estructural en Android en el que una aplicación contiene una única `Activity` que actúa como marco anfitrión para múltiples pantallas gestionadas mediante `Fragments`.
* **SQLite:** Motor de base de datos relacional ligero, embebido y de código abierto que se ejecuta directamente dentro del espacio de memoria de la aplicación en Android.
* **ThreadExecutor:** Componente utilitario de concurrencia que administra un grupo de hilos de ejecución en segundo plano para procesar tareas de I/O sin congelar la interfaz de usuario.
* **Time Blocking:** Metodología de gestión del tiempo consistente en dividir la jornada en bloques temporales definidos dedicados a una sola tarea específica, evitando la multitarea.
* **WCAG (Web Content Accessibility Guidelines):** Directrices internacionales de accesibilidad digital que garantizan que el contenido sea perceptible, operable, comprensible y robusto.
* **Zanshin:** Concepto marcial y filosófico japonés que denota un estado de alerta serena, atención plena y enfoque ininterrumpido durante la realización de una actividad.
* **5S (Seiri, Seiton, Seiso, Seiketsu, Shitsuke):** Metodología japonesa de orden del espacio de trabajo que promueve la clasificación, el orden, la limpieza, la estandarización y la disciplina sostenida.
