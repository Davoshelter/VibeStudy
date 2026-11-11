# UNIVERSIDAD PRIVADA DOMINGO SAVIO
## FACULTAD DE INGENIERÍA
### CARRERA DE INGENIERÍA DE SISTEMAS

**PROYECTO DE DESARROLLO DE SOFTWARE MÓVIL**

# VibeStudy: Aplicación Móvil para la Planificación y Gestión Integral del Tiempo de Estudio Académico

**Materia:** Aplicaciones Móviles I
**Docente:** Ing. Daniel Román Terrazas
**Estudiante:** David Gorena
**Gestión:** II / 2026

## Secciones Preliminares

### Lista de Tablas

- **Tabla 1:** Mapeo de objetivos específicos con componentes del sistema.
- **Tabla 2:** Comparación de metodologías de desarrollo de software.
- **Tabla 3:** Atributos del modelo de calidad INVEST aplicados a las historias de usuario.
- **Tabla 4:** Matriz de trazabilidad: requisitos funcionales vs. historias de usuario.
- **Tabla 5:** Requisitos no funcionales según la norma ISO/IEC 25010.

### Lista de Figuras

- **Figura 1:** Diagrama conceptual del flujo de planificación y registro de estudio.
- **Figura 2:** Arquitectura por capas de la plataforma Android.
- **Figura 3:** Ciclo de vida del sprint y desglose de requisitos.

# Capítulo 1. Introducción

## 1.1. Contexto del problema

El rendimiento académico universitario depende de la capacidad cognitiva del estudiante, pero también de su autorregulación y de su gestión del tiempo. Los programas con alta carga académica exigen repartir las horas de estudio independiente entre varias asignaturas, proyectos prácticos y evaluaciones periódicas.

En la práctica, muchos estudiantes organizan su tiempo con notas adhesivas, calendarios genéricos o alarmas sueltas. Estas herramientas no vinculan el tiempo invertido con una asignatura concreta ni permiten saber si se cumplieron las metas de la semana. El resultado habitual es la procrastinación y el estudio de último momento.

Se necesita, por tanto, una aplicación para el dispositivo que el estudiante usa todos los días, el teléfono móvil, orientada a gestionar, planificar, medir y analizar el tiempo de estudio. La aplicación debe registrar materias, programar y cronometrar sesiones, definir metas semanales y generar estadísticas que sostengan hábitos disciplinados.

## 1.2. Justificación del proyecto

El teléfono móvil es el dispositivo que el estudiante usa a diario, y Android es la plataforma con mayor cuota de mercado. VibeStudy aprovecha esa cercanía para ofrecer una herramienta que permite gestionar, planificar y medir el tiempo de estudio, con datos guardados localmente y sin depender de Internet. Académicamente, el proyecto pone en práctica los contenidos de Aplicaciones Móviles I, como la arquitectura de Android, el ciclo de vida de sus componentes, la POO, la ingeniería de requisitos ágil y la persistencia local. Para el estudiante, la aplicación entrega métricas claras sobre el tiempo dedicado a cada materia, lo que ayuda a reducir la procrastinación y el estudio de último momento, y a tomar decisiones informadas sobre su propia dedicación académica.

## 1.3. Objetivos

### 1.3.1. Objetivo general

Desarrollar una aplicación móvil nativa para Android que permita a los estudiantes universitarios gestionar sus materias, planificar y cronometrar sesiones de estudio, fijar metas semanales y evaluar su rendimiento mediante reportes y gráficos.

### 1.3.2. Objetivos específicos

1. **Relevar y formalizar los requisitos del sistema** con historias de usuario, criterios de aceptación en formato BDD (*Given-When-Then*) y criterios de calidad.
2. **Fundamentar el marco conceptual y tecnológico** de la aplicación: arquitectura de Android, patrones de diseño móvil y metodologías de gestión del tiempo.
3. **Implementar el módulo de materias**, con creación, edición, identificación por color y consulta de las asignaturas.
4. **Desarrollar el módulo de sesiones de estudio**, con temporizador (bloques de tiempo o técnicas de concentración) y registro del tiempo real por materia.
5. **Programar el sistema de metas semanales**, con horas objetivo por materia y seguimiento automático del porcentaje de cumplimiento.
6. **Diseñar el motor de reportes y estadísticas**, con resúmenes periódicos y gráficos de distribución del esfuerzo.
7. **Verificar y validar la calidad del software** en cuanto a usabilidad, rendimiento y consistencia funcional, en emuladores y dispositivos físicos.

#### Tabla 1: Mapeo de objetivos específicos con componentes del sistema

| Objetivo específico | Módulo / componente asociado | Entregable observable |
| :--- | :--- | :--- |
| **OE1: Requisitos** | Documentación de requisitos y análisis | Historias de usuario, criterios BDD, matriz de requisitos. |
| **OE2: Marco teórico y técnico** | Fundamentación tecnológica | Marco teórico y arquitectura de la solución móvil. |
| **OE3: Materias** | Módulo de asignaturas (`SubjectModule`) | CRUD de materias con color y créditos. |
| **OE4: Planificación** | Módulo de sesiones (`StudySessionModule`) | Temporizador, registro de inicio, fin y pausas. |
| **OE5: Metas** | Módulo de metas semanales (`GoalModule`) | Seguimiento de meta vs. tiempo ejecutado. |
| **OE6: Analítica** | Módulo de reportes y gráficos (`AnalyticsModule`) | Reportes semanales y gráficos de pastel y barras. |
| **OE7: Validación** | Aseguramiento de calidad (QA) | Casos de prueba ejecutados y matriz de conformidad. |

## 1.4. Alcance del proyecto

### 1.4.1. Inclusiones funcionales

1. **Gestión de materias:** registro, consulta, modificación y eliminación (CRUD) con nombre, sigla, color distintivo y horario referencial.
2. **Planificación y control de sesiones:** programación a futuro (fecha, hora y materia) e inicio inmediato con cronómetro, con registro de duración, notas y estado.
3. **Metas semanales:** objetivos de horas de estudio por semana, globales o por asignatura, con porcentaje de avance calculado a partir de las sesiones completadas.
4. **Reportes:** resúmenes semanales y mensuales de horas dedicadas, asignaturas más estudiadas y comparación entre tiempo planificado y tiempo ejecutado.
5. **Estadísticas visuales:** panel con gráficos de distribución del tiempo (circulares y de barras) y racha de días consecutivos de estudio.

### 1.4.2. Delimitación técnica y operativa

- **Plataforma:** Android 8.0 (Oreo, API 26) o superior, que cubre la gran mayoría de los dispositivos en circulación.
- **Operación:** arquitectura *offline-first* con base de datos local (SQLite / Room), sin dependencia de Internet ni de servidores externos.
- **Entorno:** smartphones con orientación vertical (*portrait*).

### 1.4.3. Exclusiones

- La sincronización en la nube entre varios dispositivos queda fuera de esta fase y se plantea como trabajo futuro.
- No se integra con plataformas institucionales de notas, como los portales de la UPDS, ni con pasarelas de pago.
- La aplicación no gestiona archivos de contenido académico (PDF, diapositivas), sino únicamente el tiempo y las métricas de estudio.

# Capítulo 2. Marco Teórico y Referencial

## 2.1. Marco teórico

### 2.1.1. Técnicas de gestión del tiempo

Dividir el estudio en intervalos definidos, con pausas programadas, mejora la concentración y reduce la fatiga. Tres enfoques sustentan esta idea:

- **Técnica Pomodoro (Francesco Cirillo):** bloques de 25 minutos de concentración seguidos de una pausa de 5 minutos. Tras cuatro bloques, se toma una pausa más larga de 15 a 30 minutos.
- **Time blocking:** asigna franjas horarias cerradas a una asignatura o tarea, lo que evita la multitarea que dificulta retener conceptos complejos.
- **Ley de Parkinson:** el trabajo tiende a extenderse hasta ocupar el tiempo disponible. Las metas semanales acotadas evitan que una sesión se alargue sin control.

### 2.1.2. Ingeniería de requisitos en metodologías ágiles

- **Historias de usuario:** unidades de requisito redactadas en lenguaje natural desde la perspectiva del usuario. Surgieron en la Programación Extrema (XP) y fueron popularizadas por Mike Cohn y Kent Beck. Expresan el rol, la necesidad y el valor que el usuario obtiene.
- **Modelo INVEST (Bill Wake):** define seis criterios de calidad para una historia:
  - **I (Independiente):** puede desarrollarse con poca dependencia de otras historias.
  - **N (Negociable):** admite refinamiento entre desarrollador y usuario.
  - **V (Valiosa):** aporta un beneficio concreto al estudiante.
  - **E (Estimable):** su alcance es lo bastante claro para estimar el esfuerzo.
  - **S (Pequeña):** se completa en una iteración.
  - **T (Comprobable):** tiene criterios de aceptación verificables.
- **Criterios de aceptación en BDD (*Behavior-Driven Development*):** se redactan en *Gherkin* con la estructura:

  $$\text{DADO } [\text{contexto inicial}] \longrightarrow \text{CUANDO } [\text{acción}] \longrightarrow \text{ENTONCES } [\text{resultado verificable}]$$

### 2.1.3. Enfoques japoneses para la mejora de hábitos de estudio

Además de las técnicas de gestión del tiempo, VibeStudy incorpora principios de la cultura japonesa que priorizan la constancia y la atención, y no solo la cantidad de horas:

- **Kaizen (mejora continua):** concepto popularizado por Masaaki Imai, que propone avanzar mediante cambios pequeños y sostenidos en lugar de esfuerzos extremos. Aplicado al estudio, implica aumentar las metas de forma gradual y revisar los resultados con regularidad.
- **Zanshin (atención plena):** término del entrenamiento marcial que describe un estado de alerta y presencia mental durante una actividad. Aplicado al estudio, favorece iniciar cada sesión con una intención clara y mantener el foco.
- **Shinrin-yoku (baño de bosque):** práctica propuesta en Japón en 1982 que consiste en pasar tiempo en entornos naturales para reducir el estrés. Aplicado al estudio, sirve de base para promover pausas de descanso alejadas de las pantallas. Sus beneficios han sido estudiados con resultados prometedores, aunque la evidencia aún es limitada.
- **Las 5S (Seiri, Seiton, Seiso, Seiketsu, Shitsuke):** método de organización del puesto de trabajo desarrollado en la industria japonesa, especialmente en Toyota. Sus fases son clasificar, ordenar, limpiar, estandarizar y mantener la disciplina. Aplicado al estudio, orienta la organización de materias, sesiones y datos.

## 2.2. Marco tecnológico

### 2.2.1. Arquitectura por capas de Android

La plataforma Android se organiza en cinco capas:

1. **Kernel de Linux:** controla el hardware (pantalla táctil, temporizadores, memoria y energía).
2. **Capa de abstracción de hardware (HAL):** interfaces estándar que conectan el kernel con el framework.
3. **Android Runtime (ART) y bibliotecas nativas:** ejecuta los archivos DEX mediante compilación AOT y JIT, con un consumo de memoria eficiente.
4. **Framework de la API de Java:** expone los servicios del sistema, como el gestor de actividades, las notificaciones y los proveedores de contenido.
5. **Capa de aplicaciones:** donde reside VibeStudy y con la que interactúa el usuario.

### 2.2.2. Java y programación orientada a objetos

La lógica de negocio se implementa en Java con los cuatro pilares de la POO:

- **Abstracción:** modelado de las entidades del dominio (`Materia`, `SesionEstudio`, `MetaSemanal`, `Reporte`).
- **Encapsulamiento:** atributos privados con acceso controlado mediante *getters* y *setters*.
- **Herencia:** reutilización de clases del SDK, como `AppCompatActivity`, `Fragment` o `RecyclerView.Adapter`.
- **Polimorfismo:** redefinición de los métodos del ciclo de vida (`onCreate`, `onResume`, `onPause`) y uso de *listeners* y *callbacks*.

### 2.2.3. Entorno de desarrollo y persistencia

- **Android Studio:** IDE oficial basado en IntelliJ IDEA, con emuladores (AVD), depurador y perfiladores de memoria y rendimiento.
- **Gradle:** automatiza la construcción, gestiona las dependencias y genera el APK final.
- **SQLite:** motor relacional ligero y transaccional (ACID), integrado en Android, que almacena sesiones, materias y metas.
- **Material Design Components y MPAndroidChart:** componentes visuales (FAB, campos con validación, CardView) y librería de gráficos para las métricas.

# Capítulo 3. Requisitos y Análisis del Sistema

## 3.1. Metodología de desarrollo

El proyecto adopta **Scrum**, combinado con las prácticas de modelado de requisitos de la ingeniería de software.

### 3.1.1. Justificación de Scrum

Las aplicaciones móviles dependen mucho de la experiencia de usuario, por lo que necesitan retroalimentación frecuente sobre prototipos y flujos. Scrum organiza el trabajo en *Sprints* de 1 a 2 semanas, al final de los cuales se entrega un incremento funcional y probado. El modelo en cascada, en cambio, entrega todo al final y detecta los problemas tarde.

#### Tabla 2: Comparación metodológica (cascada vs. Scrum)

| Criterio | Modelo en cascada | Scrum (adoptado) |
| :--- | :--- | :--- |
| **Flexibilidad ante cambios** | Rígida; los cambios tardíos son costosos. | Alta; los requisitos se refinan en el Product Backlog. |
| **Entregas** | Una sola entrega al final. | Entregas parciales y funcionales tras cada Sprint. |
| **Gestión de requisitos** | Especificación cerrada desde el inicio. | Historias priorizadas por valor (criterios INVEST). |
| **Validación con el usuario** | Tardía, en las pruebas finales. | Continua, con prototipos iterativos. |
| **Mitigación de riesgos** | Los fallos de integración aparecen tarde. | Detección temprana en cada iteración. |

### 3.1.2. Roles, artefactos y Definition of Done

- **Product Owner (PO) y estudiante:** define y prioriza el *Product Backlog* según el valor que aporta cada historia.
- **Development Team:** diseña, codifica en Java, modela la base de datos, maqueta las interfaces XML y realiza las pruebas. Como el proyecto es individual, el mismo autor cumple los roles de PO y de desarrollador, con apoyo de herramientas de IA en el proceso de desarrollo.
- **Definition of Done (DoD):** una historia se considera terminada cuando:
  1. El código cumple las pautas de POO y las buenas prácticas de Java.
  2. Se cumplen el 100% de sus criterios de aceptación BDD.
  3. No presenta fugas de memoria ni bloqueos del hilo principal (ANR).
  4. Los datos se persisten correctamente en SQLite.

## 3.2. Requisitos del sistema

Los requisitos funcionales se organizan en épicas, desglosadas en historias de usuario evaluadas con el modelo INVEST. Cada historia incluye criterios de aceptación en formato BDD (Dado-Cuando-Entonces).

```
TAXONOMÍA DE REQUISITOS DEL SISTEMA

[ÉPICA 1] Gestión de materias académicas
   ├── HU-01: Registrar nueva materia académica
   ├── HU-02: Modificar y asignar color identificador a una materia
   └── HU-03: Visualizar listado consolidado de materias activas

[ÉPICA 2] Planificación y ejecución de sesiones de estudio
   ├── HU-04: Planificar sesión de estudio a futuro
   ├── HU-05: Iniciar sesión de estudio en tiempo real con cronómetro
   └── HU-06: Pausar, reanudar y registrar notas de la sesión

[ÉPICA 3] Metas y compromisos semanales
   ├── HU-07: Establecer meta de horas de estudio semanal por materia
   └── HU-08: Consultar el progreso porcentual de las metas semanales

[ÉPICA 4] Reportes periódicos de rendimiento
   ├── HU-09: Generar reporte semanal del tiempo total de estudio
   └── HU-10: Consultar desglose histórico mensual de horas aplicadas

[ÉPICA 5] Estadísticas y analítica visual
   ├── HU-11: Visualizar gráfico de distribución de tiempo por asignatura
   └── HU-12: Monitorear racha de días consecutivos de estudio y cumplimiento
```

### 3.2.1. Requisitos funcionales: historias de usuario y criterios de aceptación

#### Tabla 3: Validación del modelo INVEST en las historias de usuario

| Criterio INVEST | Justificación |
| :---: | :--- |
| **I - Independiente** | Las historias de materias, sesiones y metas se desarrollan en componentes desacoplados con contratos de interfaz claros. |
| **N - Negociable** | Los criterios de temporización (bloque fijo o cronómetro libre) pueden parametrizarse en la UI. |
| **V - Valiosa** | Cada historia resuelve un problema concreto: desorganización, olvido o falta de métricas objetivas. |
| **E - Estimable** | El alcance de cada pantalla y de cada consulta SQLite está delimitado. |
| **S - Pequeña** | Cada historia corresponde a una vista o interacción que se desarrolla en 1 a 3 días. |
| **T - Comprobable** | Cada historia tiene criterios BDD con entradas y salidas observables. |

### ÉPICA 1: Gestión de materias académicas

*Propósito:* permitir al estudiante organizar las asignaturas del periodo lectivo y asignar a cada una una identidad visual.

#### HU-01: Registrar nueva materia

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* registrar una materia con su nombre, código y color,
  *para* organizar mis asignaturas y asociarles sesiones de estudio.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Registro exitoso**
    - **DADO** que el estudiante está en el formulario "Nueva Materia",
    - **CUANDO** ingresa un nombre válido (ej. "Aplicaciones Móviles I"), una sigla (ej. "INF-312") y selecciona un color,
    - **ENTONCES** el sistema guarda la materia en la base de datos local, la muestra en el listado y confirma con el mensaje "Materia registrada correctamente".
  - **Escenario 2: Campos vacíos**
    - **DADO** que el estudiante está en el formulario "Nueva Materia",
    - **CUANDO** deja vacío el campo "Nombre de la Materia" y presiona "Guardar",
    - **ENTONCES** el sistema no guarda el registro y resalta el campo con el error "El nombre de la materia es obligatorio".
  - **Escenario 3: Materia duplicada**
    - **DADO** que ya existe una materia llamada "Cálculo I",
    - **CUANDO** el estudiante intenta registrar otra con el mismo nombre,
    - **ENTONCES** el sistema muestra la alerta "Ya existe una materia registrada con este nombre".

#### HU-02: Modificar y asignar color a una materia

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* editar una materia existente y cambiar su color,
  *para* mantener mis datos actualizados y distinguir la materia en las gráficas.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Edición exitosa**
    - **DADO** que existe la materia "Física Básica",
    - **CUANDO** el estudiante la edita, cambia su color de azul a naranja y presiona "Actualizar",
    - **ENTONCES** el sistema refleja el nuevo color en todas las vistas de sesiones y reportes.

#### HU-03: Visualizar y eliminar materias

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* ver mis materias activas y dar de baja las que ya no curso,
  *para* mantener ordenado mi espacio de trabajo académico.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Listado de materias**
    - **DADO** que el estudiante tiene materias registradas,
    - **CUANDO** abre la pantalla "Materias",
    - **ENTONCES** el sistema muestra las materias en tarjetas con su color y el número de sesiones acumuladas.
  - **Escenario 2: Confirmación de eliminación**
    - **DADO** que el estudiante quiere eliminar la materia "Álgebra Lineal",
    - **CUANDO** pulsa el ícono de eliminación,
    - **ENTONCES** el sistema muestra un diálogo modal que advierte sobre las sesiones asociadas y exige confirmación explícita.

### ÉPICA 2: Planificación y ejecución de sesiones de estudio

*Propósito:* agendar sesiones futuras y cronometrar las sesiones activas.

#### HU-04: Planificar sesión a futuro

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* agendar una sesión indicando materia, fecha, hora y duración estimada,
  *para* organizar mi semana con anticipación y reducir la procrastinación.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Sesión válida**
    - **DADO** que el estudiante abre el formulario "Planificar Sesión",
    - **CUANDO** elige una materia, una fecha y hora futuras y una duración (ej. 90 minutos),
    - **ENTONCES** el sistema guarda la sesión con estado "Pendiente" y la muestra en la agenda del día.
  - **Escenario 2: Fecha pasada**
    - **DADO** que el estudiante está programando una sesión,
    - **CUANDO** selecciona una fecha u hora anterior a la actual,
    - **ENTONCES** el sistema muestra "La fecha y hora de la sesión debe ser posterior al horario actual".

#### HU-05: Iniciar sesión en tiempo real con temporizador

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* iniciar un temporizador asociado a una materia,
  *para* registrar con exactitud el tiempo que estudio con concentración.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Inicio de sesión**
    - **DADO** que el estudiante selecciona la materia "Sistemas Operativos",
    - **CUANDO** pulsa "Iniciar Sesión",
    - **ENTONCES** el sistema inicia el cronómetro, registra la hora de inicio y mantiene la pantalla encendida.
  - **Escenario 2: Finalización y guardado**
    - **DADO** que el cronómetro marca 45 minutos,
    - **CUANDO** el estudiante pulsa "Finalizar Estudio" y confirma,
    - **ENTONCES** el sistema calcula la duración neta en minutos, marca la sesión como "Completada" y la guarda en la base de datos local.

#### HU-06: Pausar, reanudar y registrar observaciones

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* pausar y reanudar el cronómetro ante una interrupción y anotar los temas estudiados,
  *para* que el tiempo registrado sea fiel y recordar qué tópicos cubrí.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Pausa**
    - **DADO** que una sesión está activa,
    - **CUANDO** el estudiante pulsa "Pausa",
    - **ENTONCES** el conteo se detiene sin reiniciar el tiempo acumulado.
  - **Escenario 2: Notas de estudio**
    - **DADO** que el estudiante finalizó una sesión,
    - **CUANDO** escribe "Resolución de ejercicios de árboles binarios" en el campo de notas,
    - **ENTONCES** el sistema guarda la nota en el registro histórico de la sesión.

### ÉPICA 3: Metas y compromisos semanales

*Propósito:* permitir al estudiante fijar metas cuantificables de dedicación y seguir su cumplimiento.

#### HU-07: Establecer meta semanal por materia

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* configurar una meta semanal de horas para cada materia,
  *para* tener un compromiso de dedicación claro y equilibrar mis asignaturas.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Meta válida**
    - **DADO** que el estudiante accede al módulo "Metas Semanales",
    - **CUANDO** asigna 6 horas semanales a "Aplicaciones Móviles I",
    - **ENTONCES** el sistema guarda la meta para la semana en curso y muestra la barra de progreso en 0%.
  - **Escenario 2: Rango de horas**
    - **DADO** que el estudiante configura una meta,
    - **CUANDO** ingresa un valor menor o igual a 0, o mayor a 50 horas,
    - **ENTONCES** el sistema advierte "El objetivo semanal debe estar comprendido entre 1 y 50 horas".

#### HU-08: Consultar progreso de las metas semanales

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* ver una barra de progreso con el porcentaje y las horas acumuladas,
  *para* saber cuánto me falta para cumplir antes del fin de semana.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Progreso dinámico**
    - **DADO** que la meta semanal es de 10 horas y el estudiante completa una sesión de 2 horas,
    - **CUANDO** consulta "Mis Metas",
    - **ENTONCES** el sistema muestra "2.0 / 10.0 hrs (20% completado)".
  - **Escenario 2: Meta cumplida**
    - **DADO** que el estudiante acumula 10 o más horas de una meta de 10 horas,
    - **CUANDO** se registra la sesión que completa el objetivo,
    - **ENTONCES** el sistema muestra el indicador de meta cumplida (100%) con un mensaje de felicitación.

### ÉPICA 4: Reportes periódicos de rendimiento

*Propósito:* resumir el tiempo total de estudio y su distribución por periodo.

#### HU-09: Reporte semanal del tiempo de estudio

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* consultar el total de horas estudiadas en los últimos 7 días,
  *para* evaluar mi ritmo y detectar las materias que descuidé.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Reporte generado**
    - **DADO** que existen sesiones registradas en los últimos 7 días,
    - **CUANDO** el estudiante selecciona la pestaña "Reporte Semanal",
    - **ENTONCES** el sistema suma los minutos, los convierte a horas y desglosa el total por asignatura con su color.
  - **Escenario 2: Semana sin registros**
    - **DADO** que no hay sesiones completadas en el periodo,
    - **CUANDO** el estudiante abre el reporte semanal,
    - **ENTONCES** el sistema muestra "No hay sesiones de estudio registradas esta semana. ¡Comienza una hoy!".

#### HU-10: Desglose histórico mensual de horas

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* revisar el histórico mensual de sesiones agrupado por semana y materia,
  *para* analizar mi dedicación a lo largo del semestre.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Consulta mensual**
    - **DADO** que el estudiante usa el selector de meses,
    - **CUANDO** elige el mes actual (ej. noviembre 2026),
    - **ENTONCES** el sistema muestra las sesiones en orden cronológico con fecha, materia, duración y notas.

### ÉPICA 5: Estadísticas y analítica visual

*Propósito:* ofrecer representaciones gráficas para la autoevaluación.

#### HU-11: Gráfico de distribución de tiempo por asignatura

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* ver un gráfico circular con la proporción de tiempo dedicada a cada materia,
  *para* identificar si estoy equilibrando mis asignaturas.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Gráfico circular**
    - **DADO** que hay sesiones registradas en al menos 2 materias,
    - **CUANDO** el estudiante abre el panel "Estadísticas",
    - **ENTONCES** el sistema muestra un gráfico circular en el que cada sector representa el porcentaje de tiempo de una materia, con su color.

#### HU-12: Racha de días consecutivos de estudio

- **Formulación:**
  *Como* estudiante universitario,
  *quiero* ver mi racha actual de días consecutivos con al menos una sesión,
  *para* mantenerme motivado y crear un hábito diario.
- **Criterios de aceptación (BDD):**
  - **Escenario 1: Incremento de racha**
    - **DADO** que el estudiante estudió ayer (racha de 3 días) y completa una sesión hoy,
    - **CUANDO** finaliza la sesión del día,
    - **ENTONCES** el contador sube a 4 días con un ícono de constancia.
  - **Escenario 2: Pérdida de racha**
    - **DADO** que pasan más de 48 horas sin una sesión completada,
    - **CUANDO** el estudiante abre la aplicación,
    - **ENTONCES** el contador de racha se reinicia a 0 días.

#### Tabla 4: Matriz de trazabilidad: requisitos funcionales vs. historias de usuario

| Código RF | Requisito funcional | Historia de usuario | Módulo | Prioridad (MoSCoW) |
| :---: | :--- | :---: | :--- | :---: |
| **RF-01** | Registrar materias con nombre, código y color. | HU-01 | Gestión de materias | *Must have* (Esencial) |
| **RF-02** | Editar atributos y colores de materias existentes. | HU-02 | Gestión de materias | *Should have* (Importante) |
| **RF-03** | Visualizar y dar de baja materias con confirmación. | HU-03 | Gestión de materias | *Must have* (Esencial) |
| **RF-04** | Programar sesiones a futuro con fecha, hora y duración. | HU-04 | Planificación | *Should have* (Importante) |
| **RF-05** | Ejecutar sesiones en tiempo real con cronómetro. | HU-05 | Planificación | *Must have* (Esencial) |
| **RF-06** | Pausar, reanudar y anotar observaciones de la sesión. | HU-06 | Planificación | *Must have* (Esencial) |
| **RF-07** | Configurar metas semanales de horas por materia. | HU-07 | Metas semanales | *Must have* (Esencial) |
| **RF-08** | Monitorear el progreso porcentual de las metas. | HU-08 | Metas semanales | *Must have* (Esencial) |
| **RF-09** | Generar reportes semanales de horas de estudio. | HU-09 | Reportes | *Must have* (Esencial) |
| **RF-10** | Consultar el histórico mensual y el desglose de actividades. | HU-10 | Reportes | *Should have* (Importante) |
| **RF-11** | Mostrar gráficos de distribución de tiempo (circular/barras). | HU-11 | Estadísticas | *Should have* (Importante) |
| **RF-12** | Calcular y mostrar la racha de días de estudio. | HU-12 | Estadísticas | *Could have* (Deseable) |

### 3.2.2. Requisitos no funcionales (criterios de calidad)

Los requisitos no funcionales condicionan la arquitectura, la eficiencia y la experiencia de usuario. Se clasifican según la norma **ISO/IEC 25010 (Calidad de Sistemas y Software)**.

#### 1. Usabilidad y experiencia de usuario

- **RNF-01 (Facilidad de aprendizaje):** la interfaz sigue Material Design 3. Un usuario sin capacitación previa debe crear una materia e iniciar una sesión en menos de 2 minutos.
- **RNF-02 (Contraste visual):** los colores de las materias deben alcanzar un contraste mínimo de 4.5:1 frente a fondos claros y oscuros (WCAG 2.1 nivel AA).
- **RNF-03 (Retroalimentación):** toda acción destructiva exige confirmación en un diálogo, y todo guardado exitoso se confirma con un mensaje no intrusivo (*Snackbar* o *Toast*).

#### 2. Eficiencia y rendimiento

- **RNF-04 (Tiempo de respuesta):** las transiciones entre pantallas no deben superar los 150 ms en hardware normal.
- **RNF-05 (Consultas a la base de datos):** insertar, actualizar o consultar sesiones en SQLite debe tomar menos de 50 ms con hasta 10⁴ registros.
- **RNF-06 (Memoria y recursos):** la aplicación no debe superar los 120 MB de RAM durante el uso sostenido del temporizador, y debe liberar recursos en `onPause` y `onStop` para no agotar la batería.

#### 3. Seguridad e integridad

- **RNF-07 (Integridad transaccional):** toda operación sobre sesiones y metas se ejecuta en transacciones atómicas (ACID) para evitar inconsistencias si el dispositivo se apaga o la aplicación se cierra a la fuerza.
- **RNF-08 (Protección de datos):** los datos se guardan en el almacenamiento privado de la aplicación (`/data/data/com.upds.vibestudy/databases/`), sin acceso de otras aplicaciones.

#### 4. Confiabilidad y tolerancia a fallos

- **RNF-09 (Estado del temporizador):** si la aplicación pasa a segundo plano, entra una llamada o la pantalla rota, la sesión activa conserva su conteo. Se usan `onSaveInstanceState` y un *Foreground Service*.
- **RNF-10 (Operación sin conexión):** el 100% de las funciones principales (CRUD de materias, cronómetro, cálculo de metas y estadísticas) funciona en modo avión.

#### 5. Mantenibilidad

- **RNF-11 (Arquitectura modular):** el código separa la capa de presentación, la lógica de dominio y el acceso a datos, lo que facilita agregar funciones en fases posteriores.
- **RNF-12 (Documentación):** las clases, interfaces y métodos públicos incluyen comentarios Javadoc y siguen las convenciones de nomenclatura de Java y Android.

#### 6. Portabilidad y compatibilidad

- **RNF-13 (Versiones de Android):** la aplicación es compatible desde Android 8.0 (API 26) hasta Android 14 (API 34).
- **RNF-14 (Adaptabilidad de pantalla):** el diseño usa `ConstraintLayout` y se adapta a resoluciones desde 720p (HD) hasta 1440p (QHD).

#### Tabla 5: Matriz de requisitos no funcionales (ISO/IEC 25010)

| Código RNF | Dimensión de calidad | Métrica / umbral | Método de verificación |
| :---: | :--- | :--- | :--- |
| **RNF-01** | Usabilidad | Adopción del flujo principal en ≤ 2 min. | Pruebas con usuarios universitarios. |
| **RNF-02** | Usabilidad / accesibilidad | Contraste texto-fondo ≥ 4.5:1 (WCAG AA). | Herramienta de inspección de accesibilidad de Android. |
| **RNF-03** | Usabilidad | 100% de acciones destructivas con confirmación modal. | Inspección de interfaz y flujos de navegación. |
| **RNF-04** | Rendimiento | Transición entre Activities/Fragments ≤ 150 ms. | Android Studio Profiler (*Frame Rendering*). |
| **RNF-05** | Rendimiento | Consultas SQLite ≤ 50 ms. | Pruebas de rendimiento con SQLite Benchmark. |
| **RNF-06** | Eficiencia | RAM ≤ 120 MB y sin fugas de memoria. | Memory Profiler de Android Studio y LeakCanary. |
| **RNF-07** | Seguridad e integridad | Cero inconsistencias ante cortes de energía o cierres forzados. | Pruebas de interrupción forzada del proceso (*kill*). |
| **RNF-08** | Seguridad | Almacenamiento restringido al sandbox privado. | Verificación de permisos y aislamiento del sistema de archivos. |
| **RNF-09** | Confiabilidad | 0% de pérdida de tiempo ante cambios de ciclo de vida. | Pruebas de rotación, llamadas entrantes y minimizado. |
| **RNF-10** | Disponibilidad | 100% operativa en modo avión. | Pruebas funcionales sin conectividad. |
| **RNF-11** | Mantenibilidad | Bajo acoplamiento y adherencia a POO. | Revisión estática de código y análisis de dependencias. |
| **RNF-12** | Mantenibilidad | Cobertura Javadoc en clases clave ≥ 80%. | Generador de documentación Javadoc. |
| **RNF-13** | Compatibilidad | Funcionalidad completa de API 26 a 34. | Pruebas en emuladores con distintos niveles de API. |
| **RNF-14** | Portabilidad | Sin solapamientos visuales en densidades hdpi, xhdpi y xxhdpi. | Pruebas de renderizado en varias resoluciones. |
