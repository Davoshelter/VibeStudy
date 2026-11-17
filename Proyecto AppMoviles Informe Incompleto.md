**UNIVERSIDAD PRIVADA DOMINGO SAVIO**

**FACULTAD DE INGENIERÍA**

![Logo de la Universidad Privada Domingo Savio][image1]

*Ingeniería de Sistemas*

**StudyVibe: App Móvil para la Planificación y Gestión del Tiempo de Estudio Académico**

**Asignatura:**

Aplicaciones Moviles 1

**Integrantes:**

David Gorena

**Docente:**

Daniel Roman Terrazas

Santa Cruz de la Sierra, Bolivia

21 de noviembre de 2026  
Índice

[**1\. Introducción	3**](#introducción)

[1.1. Contexto del Problema	3](#contexto-del-problema)

[1.2. Justificación del Proyecto	3](#justificación-del-proyecto)

[1.3. Objetivos	3](#objetivos)

[1.3.1. Objetivo General	3](#objetivo-general)

[1.3.2. Objetivos Específicos	3](#objetivos-específicos)

[1.4. Alcance del Proyecto	4](#alcance-del-proyecto)

[1.4.1. Inclusiones funcionales	4](#inclusiones-funcionales)

[**2\. Marco Referencial	5**](#marco-referencial)

[2.1. Marco Teórico	5](#marco-teórico)

[2.1.1. Gestión de tiempo	5](#gestión-de-tiempo)

[2.1.2. Requisitos y metodologías ágiles	5](#requisitos-y-metodologías-ágiles)

[2.1.3. Requisitos y metodologías ágiles	6](#requisitos-y-metodologías-ágiles-1)

[2.2. Marco Tecnológico	6](#marco-tecnológico)

[2.2.1. Arquitectura por capas	6](#arquitectura-por-capas)

[2.2.2. Java y POO	7](#java-y-poo)

[2.2.3. Entorno de desarrollo y persistencia	7](#entorno-de-desarrollo-y-persistencia)

[**3\. Requisitos del Sistema	7**](#requisitos-del-sistema)

[3.1. Metodología de Desarrollo Adoptada	7](#metodología-de-desarrollo-adoptada)

[3.1.1. Definition of Done (DoD)	7](#definition-of-done-\(dod\))

[3.2. Requisitos	8](#requisitos)

[3.2.1. Requisitos Funcionales	9](#requisitos-funcionales)

[3.2.1.1. Epica 1: Gestión de Materias Académicas HU-1 a HU-3	9](#epica-1:-gestión-de-materias-académicas-hu-1-a-hu-3)

[3.2.1.2. Epica 2: Planificación y ejecución de sesiones de estudio HU-4 a HU-6	11](#epica-2:-planificación-y-ejecución-de-sesiones-de-estudio-hu-4-a-hu-6)

[3.2.1.3. Epica 3: Consultar progreso de las metas semanales HU-7 a HU-8	13](#epica-3:-consultar-progreso-de-las-metas-semanales-hu-7-a-hu-8)

[3.2.1.4. Epica 4: Reportes periódicos de rendimiento HU-9 a HU-10	14](#epica-4:-reportes-periódicos-de-rendimiento-hu-9-a-hu-10)

[3.2.1.5. Epica 5:Estadísticas y analítica visual HU-11 a HU-12	15](#epica-5:estadísticas-y-analítica-visual-hu-11-a-hu-12)

[3.2.2. Requisitos no Funcionales	17](#requisitos-no-funcionales)

[3.3. Modelado de Dominio	20](#modelado-de-dominio)

[3.4. Prototipos de Baja Fidelidad	20](#prototipos-de-baja-fidelidad)

[3.4.1. Prototipo de Dashboard	20](#prototipo-de-dashboard)

[3.4.2. Prototipo de Gestión Materia	23](#prototipo-de-gestión-materia)

[3.4.3. Prototipo de Temporizador de Estudio	25](#prototipo-de-temporizador-de-estudio)

[3.4.4. Prototipo de Metas semanales	27](#prototipo-de-metas-semanales)

[3.4.5. Prototipo de Reportes y Estadísticas	29](#prototipo-de-reportes-y-estadísticas)

[**4\. Diseño del Software	31**](#diseño-del-software)

[4.1. Arquitectura del Software	31](#arquitectura-del-software)

[4.2. Diseño de la Base de Datos	31](#diseño-de-la-base-de-datos)

[4.2.1. Modelo Lógico	31](#modelo-lógico)

[4.2.2. Diccionario de Datos	31](#diccionario-de-datos)

[4.3. Diseño de la Interfaz de Usuario	31](#diseño-de-la-interfaz-de-usuario)

[4.3.1. WireFrames y Mockups	31](#wireframes-y-mockups)

[4.3.2. Diseño de Componentes y Módulos	31](#diseño-de-componentes-y-módulos)

[**5\. Implementación y Pruebas	31**](#implementación-y-pruebas)

[5.1. Entorno de Implementación	31](#entorno-de-implementación)

[5.2. Proceso de Desarrollo	31](#proceso-de-desarrollo)

[5.3. Pruebas y Validación	31](#pruebas-y-validación)

[**6\. Conclusiones y Trabajo Futuro	31**](#conclusiones-y-trabajo-futuro)

[6.1. Conclusión	31](#conclusión)

[6.2. Logros del Proyecto	31](#logros-del-proyecto)

[**7\. Referencias Bibliográficas	31**](#referencias-bibliográficas)

[7.1. Anexo Repositorio Proyecto	31](#anexo-repositorio-proyecto)

[7.2. Anexo Glosario de Terminos	31](#anexo-glosario-de-términos)

1. # **Introducción** {#introducción}

   1. ## **Contexto del Problema** {#contexto-del-problema}

El rendimiento académico depende de la capacidad cognitiva del estudiante, pero también de su autorregulación y de su gestión del tiempo. Los programas académicos exigen repartir horas de estudio independiente entre muchas asignaturas, tareas y evaluaciones periódicamente.

En la práctica, los estudiantes organizan su tiempo con notas en el teléfono, recordatorios en cuaderno o agendas personales. Estas herramientas no vinculan el tiempo invertido con una asignatura y no permiten saber si se llegó a la Meta de la Semana. Normalmente el hábito es la procrastinación y el estudio de último momento.

Entonces se necesita una aplicación móvil donde el estudiante pueda organizar, gestionar y planificar a medida su tiempo de estudio, para ello la app debe registrar materias, programar y cronometrar sesiones, definir metas semanales y generar las estadísticas del progreso sostenido de los hábitos disciplinarios.

2. ## **Justificación del Proyecto** {#justificación-del-proyecto}

El teléfono móvil es el dispositivo más utilizado por un estudiante en su dia a dia y Android es la mayor cuota de mercado.  StudyVibe aprovecha esa cercanía para ofrecer una herramienta para gestionar, planificar y medir el tiempo de estudio, con datos guardados localmente sin depender de Internet.

3. ## **Objetivos** {#objetivos}

   1. ### Objetivo General {#objetivo-general}

Desarrollar una aplicación móvil nativa para Android que permita a los estudiantes universitarios gestionar sus materias, planificar y cronometrar sesiones de estudio, fijar metas semanales y evaluar su rendimiento mediante reportes y gráficos.

2. ### Objetivos Específicos {#objetivos-específicos}

* **Fundamentar el marco conceptual y tecnológico de la aplicación:** arquitectura de Android, patrones de diseño móvil y metodologías de gestión del tiempo.  
* **Implementar el módulo de materias:** Creación, edición, identificación por color y consulta de las asignaturas.  
* **Desarrollar el módulo de sesiones de estudio:** Temporizador (bloques de tiempo o técnicas de concentración) y registro del tiempo real por materia.  
* **Programar el sistema de metas semanales:** Horas objetivo por materia y seguimiento automático del porcentaje de cumplimiento.  
* **Diseñar el motor de reportes y estadísticas:** Resúmenes periódicos y gráficos de distribución del esfuerzo.  
* **Verificar y validar la calidad del software:** Cuenta usabilidad, rendimiento y consistencia funcional, en emuladores y dispositivos físicos.

  4. ## **Alcance del Proyecto** {#alcance-del-proyecto}

     1. ### Inclusiones funcionales {#inclusiones-funcionales}

* **Gestión de materias:** registro, consulta, modificación y eliminación (CRUD) con nombre, sigla, color distintivo y horario referencial.  
* **Planificación y control de sesiones:** programación a futuro (fecha, hora y materia) e inicio inmediato con cronómetro, con registro de duración, notas y estado.  
* **Metas semanales:** objetivos de horas de estudio por semana, globales o por asignatura, con porcentaje de avance calculado a partir de las sesiones completadas.  
* **Reportes:** resúmenes semanales y mensuales de horas dedicadas, asignaturas más estudiadas y comparación entre tiempo planificado y tiempo ejecutado.  
* **Estadísticas visuales:** panel con gráficos de distribución del tiempo (circulares y de barras) y racha de días consecutivos de estudio.

  2. Delimitación Técnica y Operativa  
* **Plataforma:** Android 8.0 (Oreo, API 26\) o superior, que cubre la gran mayoría de los dispositivos en circulación.  
* **Operación:** arquitectura offline-first con base de datos local (SQLite / Room), sin dependencia de Internet ni de servidores externos.  
* **Entorno:** smartphones con orientación vertical (portrait).

  3. Exclusiones  
* La sincronización en la nube entre varios dispositivos queda fuera de esta fase y se plantea como trabajo futuro.  
* No se integra con plataformas institucionales de notas, como los portales de la UPDS, ni con pasarelas de pago.  
* La aplicación no gestiona archivos de contenido académico (PDF, diapositivas), sino únicamente el tiempo y las métricas de estudio.

2. # **Marco Referencial** {#marco-referencial}

   1. ## **Marco Teórico** {#marco-teórico}

      1. ### Gestión de tiempo {#gestión-de-tiempo}

Dividir el estudio en intervalos definidos, con pausas programadas, mejora la concentración y reduce la fatiga. Tres enfoques sustentan esta idea:

* **Técnica Pomodoro (Francesco Cirillo):** bloques de 25 minutos de concentración seguidos de una pausa de 5 minutos. Tras cuatro bloques, se toma una pausa más larga de 15 a 30 minutos.  
* **Time blocking:** asigna franjas horarias cerradas a una asignatura o tarea, lo que evita la multitarea que dificulta retener conceptos complejos.  
* **Ley de Parkinson:** el trabajo tiende a extenderse hasta ocupar el tiempo disponible. Las metas semanales acotadas evitan que una sesión se alargue sin control.

  2. ### Requisitos y metodologías ágiles {#requisitos-y-metodologías-ágiles}

* **Historias de usuario:** unidades de requisito redactadas en lenguaje natural desde la perspectiva del usuario. Surgieron en la Programación Extrema (XP) y fueron popularizadas por Mike Cohn y Kent Beck. Expresan el rol, la necesidad y el valor que el usuario obtiene.  
* **Modelo INVEST (Bill Wake):** define seis criterios de calidad para una historia:  
  * **I (Independiente):** puede desarrollarse con poca dependencia de otras historias.  
  * **N (Negociable):** admite refinamiento entre desarrollador y usuario.  
  * **V (Valiosa):** aporta un beneficio concreto al estudiante.  
  * **E (Estimable):** su alcance es lo bastante claro para estimar el esfuerzo.  
  * **S (Pequeña):** se completa en una iteración.  
  * **T (Comprobable):** tiene criterios de aceptación verificables.  
* **Criterios de aceptación en BDD (Behavior-Driven Development):** se redactan en Gherkin con la estructura:

**DADO \[ Contexto Inicial \] → CUANDO \[ acción \] →ENTONCES \[resultado verificable\]**

3. ### Requisitos y metodologías ágiles {#requisitos-y-metodologías-ágiles-1}

Además de las técnicas de gestión del tiempo, StudyVibe incorpora principios de la cultura japonesa que priorizan la constancia y la atención, y no solo la cantidad de horas:

* **Kaizen (mejora continua):** concepto popularizado por Masaaki Imai, que propone avanzar mediante cambios pequeños y sostenidos en lugar de esfuerzos extremos. Aplicado al estudio, implica aumentar las metas de forma gradual y revisar los resultados con regularidad.  
* **Zanshin (atención plena):** término del entrenamiento marcial que describe un estado de alerta y presencia mental durante una actividad. Aplicado al estudio, favorece iniciar cada sesión con una intención clara y mantener el foco.  
* **Shinrin-yoku (baño de bosque):** práctica propuesta en Japón en 1982 que consiste en pasar tiempo en entornos naturales para reducir el estrés. Aplicado al estudio, sirve de base para promover pausas de descanso alejadas de las pantallas. Sus beneficios han sido estudiados con resultados prometedores, aunque la evidencia aún es limitada.  
* **Las 5S (Seiri, Seiton, Seiso, Seiketsu, Shitsuke):** método de organización del puesto de trabajo desarrollado en la industria japonesa, especialmente en Toyota. Sus fases son clasificar, ordenar, limpiar, estandarizar y mantener la disciplina. Aplicado al estudio, orienta la organización de materias, sesiones y datos.

  2. ## **Marco Tecnológico** {#marco-tecnológico}

     1. ### Arquitectura por capas {#arquitectura-por-capas}

La plataforma Android se organiza en cinco capas:

Kernel de Linux: controla el hardware (pantalla táctil, temporizadores, memoria y energía).  
Capa de abstracción de hardware (HAL): interfaces estándar que conectan el kernel con el framework.  
Android Runtime (ART) y bibliotecas nativas: ejecuta los archivos DEX mediante compilación AOT y JIT, con un consumo de memoria eficiente.  
Framework de la API de Java: expone los servicios del sistema, como el gestor de actividades, las notificaciones y los proveedores de contenido.  
Capa de aplicaciones: donde reside StudyVibe y con la que interactúa el usuario.

2. ### Java y POO {#java-y-poo}

La lógica de negocio se implementa en Java con los cuatro pilares de la POO:

Abstracción: modelado de las entidades del dominio (Materia, Sesion Estudio, Meta Semanal, Reporte).  
Encapsulamiento: atributos privados con acceso controlado mediante getters y setters.  
Herencia: reutilización de clases del SDK, como AppCompat Activity, Fragment to RecyclerView.Adapter.  
Polimorfismo: redefinición de los métodos del ciclo de vida (onCreate, onResume, onPause) y uso de listeners y callbacks.

3. ### Entorno de desarrollo y persistencia {#entorno-de-desarrollo-y-persistencia}

**Android Studio:** IDE oficial basado en IntelliJ IDEA, con emuladores (AVD), depurador y perfiladores de memoria y rendimiento.  
**Gradle:** automatiza la construcción, gestiona las dependencias y genera el APK final.  
**SQLite:** motor relacional ligero y transaccional (ACID), integrado en Android, que almacena sesiones, materias y metas.  
**Material Design Components y MPAndroidChart:** componentes visuales (FAB, campos con validación, CardView) y librería de gráficos para las métricas.

3. # **Requisitos del Sistema** {#requisitos-del-sistema}

   1. ## **Metodología de Desarrollo Adoptada** {#metodología-de-desarrollo-adoptada}

Para el desarrollo del Proyecto se optó por la metodología Scrum, combinado con las prácticas de modelado de requisitos de la ingeniería de software.

1. ### Definition of Done (DoD) {#definition-of-done-(dod)}

una historia se considera terminada cuando:

* Se cumplen el 100% de sus criterios de aceptación BDD.  
* No presenta fugas de memoria ni bloqueos del hilo principal (ANR).  
* Los datos se persisten correctamente en SQLite.

  2. ## **Requisitos** {#requisitos}

Los requisitos funcionales se organizan en épicas, desglosadas en historias de usuario evaluadas con el modelo INVEST. Cada historia incluye criterios de aceptación en formato BDD (Dado-Cuando-Entonces).

| TAXONOMÍA DE REQUISITOS DEL SISTEMA \[ÉPICA 1\] Gestión de materias académicas    ├── HU-01: Registrar nueva materia académica    ├── HU-02: Modificar y asignar color identificador a una materia    └── HU-03: Visualizar listado consolidado de materias activas \[ÉPICA 2\] Planificación y ejecución de sesiones de estudio    ├── HU-04: Planificar sesión de estudio a futuro    ├── HU-05: Iniciar sesión de estudio en tiempo real con cronómetro    └── HU-06: Pausar, reanudar y registrar notas de la sesión \[ÉPICA 3\] Metas y compromisos semanales    ├── HU-07: Establecer meta de horas de estudio semanal por materia    └── HU-08: Consultar el progreso porcentual de las metas semanales \[ÉPICA 4\] Reportes periódicos de rendimiento    ├── HU-09: Generar reporte semanal del tiempo total de estudio    └── HU-10: Consultar desglose histórico mensual de horas aplicadas \[ÉPICA 5\] Estadísticas y analítica visual    ├── HU-11: Visualizar gráfico de distribución de tiempo por asignatura    └── HU-12: Monitorear racha de días consecutivos de estudio y cumplimiento |
| :---- |

1. ### Requisitos Funcionales {#requisitos-funcionales}

**Tabla 1 : Validación del Modelo INVEST en las HU.**

| Criterio INVEST | Justificación |
| :---: | ----- |
| **I \- Independiente** | Las historias de materias, sesiones y metas se desarrollan en componentes desacoplados con contratos de interfaz claros. |
| **N \- Negociable** | Los criterios de temporización (bloque fijo o cronómetro libre) pueden parametrizarse en la UI. |
| **V \- Valiosa** | Cada historia resuelve un problema concreto: desorganización, olvido o falta de métricas objetivas. |
| **E \- Estimable** | El alcance de cada pantalla y de cada consulta SQLite está delimitado. |
| **S \- Pequeña** | Cada historia corresponde a una vista o interacción que se desarrolla en 1 a 3 días. |
| **T \- Comprobable** | Cada historia tiene criterios BDD con entradas y salidas observables. |

1. #### Epica 1: Gestión de Materias Académicas HU-1 a HU-3 {#epica-1:-gestión-de-materias-académicas-hu-1-a-hu-3}

**HU-01: Registrar nueva materia**

* **Formulación:** Como estudiante universitario, quiero registrar una materia con su nombre, código y color, para organizar mis asignaturas y asociarlos a sesiones de estudio.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Registro exitoso**  
  * **DADO** que el estudiante está en el formulario "Nueva Materia",  
  * **CUANDO** ingresa un nombre válido (ej. "Aplicaciones Móviles I"), una sigla (ej. "INF-312") y selecciona un color,  
  * **ENTONCES** el sistema guarda la materia en la base de datos local, la muestra en el listado y confirma con el mensaje "Materia registrada correctamente".  
* **Escenario 2: Campos vacíos**  
  * **DADO** que el estudiante está en el formulario "Nueva Materia",  
  * **CUANDO** deja vacío el campo "Nombre de la Materia" y presiona "Guardar",  
  * **ENTONCES** el sistema no guarda el registro y resalta el campo con el error "El nombre de la materia es obligatorio".  
* **Escenario 3: Materia duplicada**  
  * **DADO** que ya existe una materia llamada "Cálculo I",  
  * **CUANDO** el estudiante intenta registrar otra con el mismo nombre,  
  * **ENTONCES** el sistema muestra la alerta "Ya existe una materia registrada con este nombre".

**HU-02: Modificar y asignar color a una materia**

* **Formulación:** Como estudiante universitario, quiero editar los datos de una materia y asignarle un color distintivo, para actualizar información errónea y reconocerla visualmente en el horario.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Edición exitosa**  
  * **DADO** que el estudiante selecciona una materia existente,  
  * **CUANDO** modifica el nombre o le asigna un nuevo color identificador y guarda los cambios,  
  * **ENTONCES** el sistema actualiza los datos en la base de datos local y refleja el nuevo color en la interfaz.  
* **Formulación:** Como estudiante universitario, quiero agendar una sesión indicando materia, fecha, hora y duración estimada, para organizar mi semana con anticipación y reducir la procrastinación.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Sesión válida**  
  * **DADO** que el estudiante abre el formulario "Planificar Sesión",  
  * **CUANDO** elige una materia, una fecha y hora futuras y una duración (ej. 90 minutos),  
  * **ENTONCES** el sistema guarda la sesión con estado "Pendiente" y la muestra en la agenda del día.  
* **Escenario 2: Fecha pasada**  
  * **DADO** que el estudiante está programando una sesión,  
  * **CUANDO** selecciona una fecha u hora anterior a la actual,  
  * **ENTONCES** el sistema muestra "La fecha y hora de la sesión debe ser posterior al horario actual".

**HU-03: Visualizar y eliminar materias**

* **Formulación:** Como estudiante universitario, quiero ver mis materias activas y dar de baja las que ya no curso, para mantener ordenado mi espacio de trabajo académico.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Listado de materias**  
  * **DADO** que el estudiante tiene materias registradas,  
  * **CUANDO** abre la pantalla "Materias",  
  * **ENTONCES** el sistema muestra las materias en tarjetas con su color y el número de sesiones acumuladas.  
* **Escenario 2: Confirmación de eliminación**  
  * **DADO** que el estudiante quiere eliminar la materia "Álgebra Lineal",  
  * **CUANDO** pulsa el ícono de eliminación,  
  * **ENTONCES** el sistema muestra un diálogo modal que advierte sobre las sesiones asociadas y exige confirmación explícita.

    2. #### Epica 2: Planificación y ejecución de sesiones de estudio HU-4 a HU-6 {#epica-2:-planificación-y-ejecución-de-sesiones-de-estudio-hu-4-a-hu-6}

**HU-04: Planificar sesión a futuro**

* **Formulación:** Como estudiante universitario, quiero agendar una sesión indicando materia, fecha, hora y duración estimada, para organizar mi semana con anticipación y reducir la procrastinación.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Sesión válida**  
  * **DADO** que el estudiante abre el formulario "Planificar Sesión",  
  * **CUANDO** elige una materia, una fecha y hora futuras y una duración (ej. 90 minutos),  
  * **ENTONCES** el sistema guarda la sesión con estado "Pendiente" y la muestra en la agenda del día.  
* **Escenario 2: Fecha pasada**  
  * **DADO** que el estudiante está programando una sesión,  
  * **CUANDO** selecciona una fecha u hora anterior a la actual,

**ENTONCES** el sistema muestra "La fecha y hora de la sesión debe ser posterior al horario actual".  
**HU-05: Iniciar sesión en tiempo real con temporizador**

* **Formulación:** Como estudiante universitario, quiero iniciar un temporizador asociado a una materia, para registrar con exactitud el tiempo que estudio con concentración.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Inicio de sesión**  
  * **DADO** que el estudiante selecciona la materia "Sistemas Operativos",  
  * **CUANDO** pulsa "Iniciar Sesión",  
  * **ENTONCES** el sistema inicia el cronómetro, registra la hora de inicio y mantiene la pantalla encendida.  
* **Escenario 2: Finalización y guardado**  
  * **DADO** que el cronómetro marca 45 minutos,  
  * **CUANDO** el estudiante pulsa "Finalizar Estudio" y confirma,  
  * **ENTONCES** el sistema calcula la duración neta en minutos, marca la sesión como "Completada" y la guarda en la base de datos local.

**HU-06: Pausar, reanudar y registrar observaciones**

* **Formulación:** Como estudiante universitario, quiero pausar y reanudar el cronómetro ante una interrupción y anotar los temas estudiados, para que el tiempo registrado sea fiel y recordar qué tópicos cubrí.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Pausa**  
  * **DADO** que una sesión está activa,  
  * **CUANDO** el estudiante pulsa "Pausa",  
  * **ENTONCES** el conteo se detiene sin reiniciar el tiempo acumulado.  
  * **Escenario 2: Notas de estudio**  
  * **DADO** que el estudiante finaliza una sesión,  
  * **CUANDO** escribe "Resolución de ejercicios de árboles binarios" en el campo de notas,  
  * **ENTONCES** el sistema guarda la nota en el registro histórico de la sesión.

    3. #### Epica 3: Consultar progreso de las metas semanales HU-7 a HU-8 {#epica-3:-consultar-progreso-de-las-metas-semanales-hu-7-a-hu-8}

**HU-07: Establecer meta semanal por materia**

* **Formulación:** Como estudiante universitario, quiero configurar una meta semanal de horas para cada materia, para tener un compromiso de dedicación claro y equilibrar mis asignaturas.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Meta válida**  
  * **DADO** que el estudiante accede al módulo "Metas Semanales",  
  * **CUANDO** asigna 6 horas semanales a "Aplicaciones Móviles I",  
  * **ENTONCES** el sistema guarda la meta para la semana en curso y muestra la barra de progreso en 0%.  
* **Escenario 2: Rango de horas**  
  * **DADO** que el estudiante configura una meta,  
  * **CUANDO** ingresa un valor menor o igual a 0, o mayor a 50 horas,  
  * **ENTONCES** el sistema advierte "El objetivo semanal debe estar comprendido entre 1 y 50 horas".

**HU-08: Consultar progreso de las metas semanales**

* **Formulación:** Como estudiante universitario, quiero ver una barra de progreso con el porcentaje y las horas acumuladas, para saber cuánto me falta para cumplir antes del fin de semana.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Progreso dinámico**  
  * **DADO** que la meta semanal es de 10 horas y el estudiante completa una sesión de 2 horas,  
  * **CUANDO** consulta "Mis Metas",  
  * **ENTONCES** el sistema muestra "2.0 / 10.0 hrs (20% completado)".  
* **Escenario 2: Meta cumplida**  
  * **DADO** que el estudiante acumula 10 o más horas de una meta de 10 horas,  
  * **CUANDO** se registra la sesión que completa el objetivo,

**ENTONCES** el sistema muestra el indicador de meta cumplida (100%) con un mensaje de felicitación.

4. #### Epica 4: Reportes periódicos de rendimiento HU-9 a HU-10 {#epica-4:-reportes-periódicos-de-rendimiento-hu-9-a-hu-10}

**HU-09: Reporte semanal del tiempo de estudio**

* **Formulación:** Como estudiante universitario, quiero consultar el total de horas estudiadas en los últimos 7 días, para evaluar mi ritmo y detectar las materias que descuidé.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Reporte generado**  
  * **DADO** que existen sesiones registradas en los últimos 7 días,  
  * **CUANDO** el estudiante selecciona la pestaña "Reporte Semanal",  
  * **ENTONCES** el sistema suma los minutos, los convierte a horas y desglosa el total por asignatura con su color.  
* **Escenario 2: Semana sin registros**  
  * **DADO** que no hay sesiones completadas en el periodo,  
  * **CUANDO** el estudiante abre el reporte semanal,  
  * **ENTONCES** el sistema muestra "No hay sesiones de estudio registradas esta semana. ¡Comienza una hoy\!".

**HU-10: Desglose histórico mensual de horas**

* **Formulación:** Como estudiante universitario, quiero revisar el histórico mensual de sesiones agrupado por semana y materia, para analizar mi dedicación a lo largo del semestre.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Consulta mensual**  
  * **DADO** que el estudiante usa el selector de meses,  
  * **CUANDO** elige el mes actual (ej. noviembre 2026),  
  * **ENTONCES** el sistema muestra las sesiones en orden cronológico con fecha, materia, duración y notas.

    5. #### Epica 5:Estadísticas y analítica visual HU-11 a HU-12 {#epica-5:estadísticas-y-analítica-visual-hu-11-a-hu-12}

**HU-11: Gráfico de distribución de tiempo por asignatura**

* **Formulación:** Como estudiante universitario, quiero ver un gráfico circular con la proporción de tiempo dedicada a cada materia, para identificar si estoy equilibrando mis asignaturas.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Gráfico circular**  
  * **DADO** que hay sesiones registradas en al menos 2 materias,  
  * **CUANDO** el estudiante abre el panel "Estadísticas",  
  * **ENTONCES** el sistema muestra un gráfico circular en el que cada sector representa el porcentaje de tiempo de una materia, con su color.

**HU-12: Racha de días consecutivos de estudio**

* **Formulación:** Como estudiante universitario, quiero ver mi racha actual de días consecutivos con al menos una sesión, para mantenerme motivado y crear un hábito diario.  
* **Criterios de aceptación (BDD):**  
* **Escenario 1: Incremento de racha**  
  * **DADO** que el estudiante estudió ayer (racha de 3 días) y completa una sesión hoy,  
  * **CUANDO** finaliza la sesión del día,  
  * **ENTONCES** el contador sube a 4 días con un ícono de constancia.  
* **Escenario 2: Pérdida de racha**  
  * **DADO** que pasan más de 48 horas sin una sesión completada,  
  * **CUANDO** el estudiante abre la aplicación,  
  * **ENTONCES** el contador de racha se reinicia a 0 días.

Tabla 2: Matriz de Trazabilidad: Requisitos Funncionas vs Historias de Usuario

| Código RF | Requisito funcional | Historia de usuario | Módulo | Módulo |
| :---: | ----- | :---: | :---: | :---: |
| **RF-01** | Registrar materias con nombre, código y color. | **HU-01** | Gestión de materias | ***Must have*** **(Esencial)** |
| **RF-02** | Editar atributos y colores de materias existentes. | **HU-02** | Gestión de materias | *Should have* (Importante) |
| **RF-03** | Visualizar y dar de baja materias con confirmación. | **HU-03** | Gestión de materias | ***Must have*** **(Esencial)** |
| **RF-04** | Programar sesiones a futuro con fecha, hora y duración. | **HU-04** | Planificación | *Should have* (Importante) |
| **RF-05** | Ejecutar sesiones en tiempo real con cronómetro. | **HU-05** | Planificación | ***Must have*** **(Esencial)** |
| **RF-06** | Pausar, reanudar y anotar observaciones de la sesión. | **HU-06** | Planificación | ***Must have*** **(Esencial)** |
| **RF-07** | Configurar metas semanales de horas por materia. | **HU-07** | Metas semanales | ***Must have*** **(Esencial)** |
| **RF-08** | Monitorear el progreso porcentual de las metas. | **HU-08** | Metas semanales | ***Must have*** **(Esencial)** |
| **RF-09** | Generar reportes semanales de horas de estudio. | **HU-09** | Reportes | ***Must have*** **(Esencial)** |
| **RF-10** | Consultar el histórico mensual y el desglose de actividades. | **HU-10** | Reportes | *Should have* (Importante) |
| **RF-11** | Mostrar gráficos de distribución de tiempo (circular/barras). | **HU-11** | Estadísticas | *Should have* (Importante) |
| **RF-12** | Mostrar gráficos de distribución de tiempo (circular/barras). | **HU-12** | Estadísticas | *Could have* (Deseable) |

2. ### Requisitos no Funcionales {#requisitos-no-funcionales}

Los requisitos no funcionales condicionan la arquitectura, la eficiencia y la experiencia de usuario. Se clasifican según la norma ISO/IEC 25010 (Calidad de Sistemas y Software).

* 1\. Usabilidad y experiencia de usuario  
  * **RNF-01 (Facilidad de aprendizaje):** la interfaz sigue Material Design 3\. Un usuario sin capacitación previa debe crear una materia e iniciar una sesión en menos de 2 minutos.  
  * **RNF-02 (Contraste visual):** los colores de las materias deben alcanzar un contraste mínimo de 4.5:1 frente a fondos claros y oscuros (WCAG 2.1 nivel AA).  
  * **RNF-03 (Retroalimentación):** toda acción destructiva exige confirmación en un diálogo, y todo guardado exitoso se confirma con un mensaje no intrusivo (Snackbar o Toast).  
* 2\. Eficiencia y rendimiento  
  * **RNF-04 (Tiempo de respuesta):** las transiciones entre pantallas no deben superar los 150 ms en hardware normal.  
  * **RNF-05 (Consultas a la base de datos):** insertar, actualizar o consultar sesiones en SQLite debe tomar menos de 50 ms con hasta 10⁴ registros.  
  * **RNF-06 (Memoria y recursos):** la aplicación no debe superar los 120 MB de RAM durante el uso sostenido del temporizador, y debe liberar recursos en onPause y onStop para no agotar la batería.  
* 3\. Seguridad e integridad  
  * **RNF-07 (Integridad transaccional):** toda operación sobre sesiones y metas se ejecuta en transacciones atómicas (ACID) para evitar inconsistencias si el dispositivo se apaga o la aplicación se cierra a la fuerza.  
  * **RNF-08 (Protección de datos):** los datos se guardan en el almacenamiento privado de la aplicación (/data/data/com.upds.studyflow/databases/), sin acceso de otras aplicaciones.  
* 4\. Confiabilidad y tolerancia a fallos  
  * **RNF-09 (Estado del temporizador):** si la aplicación pasa a segundo plano, entra una llamada o la pantalla rota, la sesión activa conserva su conteo. Se usan onSaveInstanceState y un Foreground Service.  
  * **RNF-10 (Operación sin conexión):** el 100% de las funciones principales (CRUD de materias, cronómetro, cálculo de metas y estadísticas) funciona en modo avión.  
* 5\. Mantenibilidad  
  * **RNF-11 (Arquitectura modular):** el código separa la capa de presentación, la lógica de dominio y el acceso a datos, lo que facilita agregar funciones en fases posteriores.  
  * **RNF-12 (Documentación):** las clases, interfaces y métodos públicos incluyen comentarios Javadoc y siguen las convenciones de nomenclatura de Java y Android.  
* 6\. Portabilidad y compatibilidad  
  * **RNF-13 (Versiones de Android):** la aplicación es compatible desde Android 8.0 (API 26\) hasta Android 14 (API 34).  
  * **RNF-14 (Adaptabilidad de pantalla):** el diseño usa ConstraintLayout y se adapta a resoluciones desde 720p (HD) hasta 1440p (QHD).

Tabla 3\. Matriz de requisitos no funcionales 

| Código RNF | Dimensión de calidad | Métrica / umbral | Módulo |
| :---: | ----- | ----- | ----- |
| **RNF-01** | Usabilidad | Adopción del flujo principal en ≤ 2 min. | Pruebas con usuarios universitarios. |
| **RNF-02** | Accesibilidad | Contraste texto-fondo ≥4.5:1 | Herramienta de inspección de accesibilidad de Android. |
| **RNF-03** | Usabilidad | 100% de acciones destructivas con confirmación modal. | Inspección de interfaz y flujos de navegación. |
| **RNF-04** | Rendimiento | Transición entre Activities/Fragments ≤ 150 ms. | Android Studio Profiler (*Frame Rendering*). |
| **RNF-05** | Rendimiento | Consultas SQLite ≤ 50 ms. | Pruebas de rendimiento con SQLite Benchmark. |
| **RNF-06** | Eficiencia | RAM ≤ 120 MB y sin fugas de memoria. | Memory Profiler de Android Studio y LeakCanary. |
| **RNF-07** | Seguridad integridad | Cero inconsistencias ante cortes de energía o cierres forzados. | Pruebas de interrupción forzada del proceso (*kill*). |
| **RNF-08** | Seguridad | Almacenamiento restringido al sandbox privado. | Verificación de permisos y aislamiento del sistema de archivos. |
| **RNF-09** | Confiabilidad | 0% de pérdida de tiempo ante cambios de ciclo de vida. | Pruebas de rotación, llamadas entrantes y minimizado. |
| **RNF-10** | Disponibilidad | 100% operativa en modo avión. | Pruebas funcionales sin conectividad. |
| **RNF-11** | Mantenibilidad | Bajo acoplamiento y adherencia a POO. | Revisión estática de código y análisis de dependencias. |
| **RNF-12** | Mantenibilidad | Cobertura Javadoc en clases clave ≥ 80%. | Generador de documentación Javadoc. |
| **RNF-13** | Compatibilidad | Funcionalidad completa de API 26 a 34\. | Pruebas en emuladores con distintos niveles de API. |
| **RNF-14** | Compatibilidad | Sin solapamientos visuales en densidades hdpi, xhdpi y xxhdpi. | Pruebas de renderizado en varias resoluciones. |

### 

3. ## **Modelado de Dominio** {#modelado-de-dominio}

![][image2]

4. ## **Prototipos de Baja Fidelidad** {#prototipos-de-baja-fidelidad}

Los siguientes prototipos en baja fidelidad (wireframes) representan la estructura   
de interfaz de usuario (UI/UX) para la aplicación móvil StudyVibe, diseñada bajo   
los lineamientos de Material Design 3 de Android. Cada pantalla ha sido concebida   
para dar soporte directo y verificable a las Historias de Usuario (HU) formalizadas   
en la ingeniería de requisitos, vinculando elementos visuales concretos con sus   
respectivos criterios de aceptación en formato BDD (Dado-Cuando-Entonces).

1. ### Prototipo de Dashboard {#prototipo-de-dashboard}

Constituye la pantalla de entrada principal de la aplicación. Su objetivo es   
brindar al estudiante una vista resumida e inmediata del estado de su rutina   
académica, el nivel de constancia diario y el progreso de sus metas semanales,   
permitiendo iniciar sesiones de estudio con un solo toque.

* **HU-12: Monitorear racha de días consecutivos de estudio y cumplimiento**

**Componente UI:** Banner destacado superior "Racha de Estudio Kaizen".  
**Cumplimiento BDD:** Muestra un contador numérico de días consecutivos acumulados ("4 Días") junto a un distintivo visual de constancia (fuego/alerta). Informa al estudiante sobre su regularidad diaria y le advierte tácitamente que debe registrar al menos una sesión en las próximas 48 horas para evitar que el contador se reinicie a cero, fomentando la disciplina continua.

* **HU-08: Consultar el progreso porcentual de las metas semanales**

**Componente UI:** Tarjeta de "Meta Semanal Global" con barra de progreso lineal.  
**Cumplimiento BDD:** Calcula y visualiza en tiempo real la relación entre las horas acumuladas y las horas comprometidas para la semana en curso (ej. "14.0 / 20.0 hrs", equivalente al 70% de avance). Incluye un mensaje dinámico de estado que calcula las horas faltantes y los días restantes para el cierre del ciclo semanal.

* **HU-05: Iniciar sesión de estudio en tiempo real con temporizador**

**Componente UI:** Botón de acción principal (CTA) de ancho completo "Iniciar Sesión de Enfoque Ahora".  
**Cumplimiento BDD:** Proporciona un acceso de un solo clic que transiciona directamente al módulo del temporizador de estudio, permitiendo arrancar sesiones inmediatas sin fricción operativa.

* **HU-04: Planificar sesión de estudio a futuro**

**Componente UI:** Lista de tarjetas "Agenda de Sesiones Hoy" con botón "Ver calendario".  
**Cumplimiento BDD:** Muestra las sesiones previamente planificadas para el día, desglosando la asignatura mediante su color distintivo, el intervalo horario previsto (ej. "16:00 \- 17:30"), la duración estimada (90 min) y la etiqueta de estado "Pendiente".  
![][image3]

2. ### Prototipo de Gestión Materia {#prototipo-de-gestión-materia}

Permite la administración y organización centralizada del catálogo de asignaturas   
académicas cursadas durante el semestre, implementando el principio de orden del   
método japonés 5S para estructurar el espacio de trabajo del estudiante.

* **HU-01: Registrar nueva materia académica**

**Componente UI:** Botón de Acción Flotante (FAB "+") y Diálogo Modal de Registro.  
**Cumplimiento BDD:** El botón FAB abre una ventana modal emergente con los campos "Nombre de la Materia", "Sigla/Código" (ej. INF-312) y una paleta de selección de color cromático. El formulario aplica las reglas de validación exigidas: bloqueo de persistencia si el nombre está vacío y advertencia de duplicidad si la materia ya existe en la base de datos local SQLite.

* **HU-02: Modificar y asignar color identificador a una materia**

**Componente UI:** Botón "Editar" en cada tarjeta de materia y paleta de colores.  
**Cumplimiento BDD:** Habilita la actualización del nombre, código y, fundamentalmente, del código hexadecimal de color asignado a la materia. El color elegido actúa como identidad visual unificada que se propaga a las etiquetas del temporizador, a las barras de progreso y a los gráficos de estadísticas.

* **HU-03: Visualizar listado consolidado de materias activas y dar de baja**

**Componente UI:** Lista de tarjetas con información condensada y botón de baja ("Eliminar").  
**Cumplimiento BDD:** Presenta el conjunto de materias activas en tarjetas individuales que indican la sigla, número de sesiones completadas y volumen de horas acumuladas. Al presionar el botón de eliminación, el sistema despliega un diálogo de confirmación modal advirtiendo sobre el impacto en las sesiones históricas vinculadas, impidiendo borrados accidentales sin consentimiento expreso.  
![][image4]

3. ### Prototipo de Temporizador de Estudio {#prototipo-de-temporizador-de-estudio}

Gestiona la ejecución de las sesiones de estudio en tiempo real bajo el principio   
de atención plena (Zanshin), proveyendo un entorno minimalista que minimiza   
distracciones y cronometra con precisión matemática el tiempo dedicado al aprendizaje.

* **HU-05: Iniciar sesión de estudio en tiempo real con cronómetro**

   **Componente UI:** Selector de asignatura activa, anillo temporizador central y   
     etiqueta de modo de concentración.  
   **Cumplimiento BDD:** El usuario selecciona la materia a cursar y activa el reloj.   
     El prototipo muestra el contador en marcha (ej. "24:38"), el bloque actual   
     (Pomodoro 1/4), la marca temporal de inicio (10:05 AM) y garantiza que el   
     dispositivo mantenga la pantalla encendida (modo enfoque) para supervisar   
     el tiempo de concentración efectiva.

* **HU-06: Pausar, reanudar y registrar notas de la sesión**

   **Componentes UI:** Botonera de control dual ("Pausar Sesión" / "Finalizar Estudio")   
     y campo de captura de notas de estudio.  
   **Cumplimiento BDD:**  
     a) Criterio de Pausa/Reanudación: El botón "Pausar" congela el conteo   
        inmediatamente sin alterar ni reiniciar los segundos acumulados, permitiendo   
        retomar la sesión con exactitud fidedigna.  
     b) Criterio de Finalización: El botón "Finalizar Estudio" solicita confirmación,   
        calcula la duración neta en minutos, actualiza el estado a "Completada" y   
        persiste la sesión en SQLite.  
     c) Criterio de Observaciones: El campo de notas permite registrar tópicos   
        estudiados (ej. "Resolución de ejercicios de árboles binarios..."),   
        asociando anotaciones cualitativas al registro histórico cuantitativo.  
![][image5]

4. ### Prototipo de Metas semanales {#prototipo-de-metas-semanales}

Facilita la fijación de objetivos cuantitativos de tiempo de estudio por asignatura   
y el monitoreo visual del grado de avance a lo largo de las semanas lectivas,   
promoviendo la mejora continua y el balance académico (Kaizen).

* **HU-07: Establecer meta de horas de estudio semanal por materia**

   **Componente UI:** Botón flotante ("+") y selector de rango temporal de semanas.  
   **Cumplimiento BDD:** Permite registrar objetivos de dedicación para una semana   
     determinada (ej. Semana 45), validando que las horas asignadas se ubiquen en el   
     intervalo operativo admisible (entre 1 y 50 horas por asignatura).

* **HU-08: Consultar el progreso porcentual de las metas semanales**

**Componente UI:** Tarjeta de compromiso total, barras de progreso lineales por   
     materia y distintivos de estado condicional.  
**Cumplimiento BDD:**  
     **a) Progreso Dinámico:** Conforme se finalizan sesiones en el temporizador, el   
        sistema actualiza en tiempo real la barra y el porcentaje de cada materia   
        (ej. Móviles I: 5.0/6.0 hrs \= 83%; Redes II: 3.0/4.0 hrs \= 75%).  
     **b) Hito de Cumplimiento (100%):** Cuando una materia alcanza o supera su meta   
        (ej. Sistemas Operativos: 5.0/5.0 hrs), la tarjeta cambia a color verde de   
        éxito con el distintivo de felicitación "100% CUMPLIDA".  
     **c) Alerta de Asignatura Rezagada:** Si una materia presenta bajo avance (ej.   
        Cálculo II con 20%), el sistema resalta la barra en color de alerta y advierte   
        las horas pendientes para evitar descuidos en el plan de estudio.  
![][image6]

5. ### Prototipo de Reportes y Estadísticas {#prototipo-de-reportes-y-estadísticas}

Proporciona analítica visual y resúmenes periódicos de las horas de estudio invertidas,   
permitiendo al estudiante autoevaluarse, identificar materias desatendidas y   
comprender sus patrones temporales de rendimiento.  
**HU-11: Visualizar gráfico de distribución de tiempo por asignatura**  
 **Componente UI:** Tarjeta de visualización gráfica con Gráfico Circular (PieChart).  
 **Cumplimiento BDD:** Renderiza un gráfico de pastel donde cada sector circular   
     representa de forma fidedigna la proporción de tiempo dedicado a cada materia   
     (ej. Móviles 45%, SO 27%, Redes 18%, Cálculo 10%), aplicando el código de   
     color oficial definido en el módulo de materias para identificación intuitiva.

**HU-09: Generar reporte semanal del tiempo total de estudio**  
 **Componente UI:** Pestaña "Semana", tarjetas de métricas numéricas agregadas y   
     gráfico de barras (Histograma diario).  
 **Cumplimiento BDD:** Realiza la sumatoria de minutos de los últimos 7 días   
     convirtiéndolos a formato legible (16h 30m) y calcula el promedio diario de   
     estudio (2h 21m/día). El histograma de barras desglosa el volumen de horas por   
     cada día individual (Lunes a Domingo), facilitando la detección de días con   
     baja o nula actividad.

**HU-10: Consultar desglose histórico mensual de horas aplicadas**  
 **Componente UI:** Pestaña "Mes" / "Historial" y contenedor de registros cronológicos.  
 **Cumplimiento BDD:** Lista cronológicamente las sesiones finalizadas del mes   
     seleccionado (Noviembre 2026), exponiendo la fecha exacta, asignatura involucrada,   
     duración neta contabilizada y el resumen de las notas cualitativas capturadas.  
![][image7]

4. # **Diseño del Software** {#diseño-del-software}

   1. ## **Arquitectura del Software** {#arquitectura-del-software}

El diseño arquitectónico de **StudyVibe** se fundamenta en el principio de separación de responsabilidades (*Separation of Concerns*, SoC), propuesto en los estándares de la ingeniería de software moderna (Pressman & Maxim, 2020; Sommerville, 2011). Para garantizar un sistema desacoplado, modular, mantenible y sujeto a pruebas automatizadas, se implementó una **arquitectura en tres capas** (Presentación, Dominio y Datos), articulada con el patrón arquitectónico **Model-View-Presenter (MVP)** en la capa de presentación y el cumplimiento estricto de los principios **SOLID** (Martin, 2018).

La estructura global del código fuente se localiza bajo el paquete raíz `com.david.mystudio` y se distribuye conceptualmente en cuatro paquetes fundamentales:

* **Capa de Presentación (`ui`):** Contiene la interacción directa con el usuario, implementada a través del patrón MVP. Su propósito exclusivo es la captura de eventos de entrada y el renderizado pasivo de la interfaz gráfica en componentes de Android (*Activities*, *Fragments* y *DialogFragments*).
* **Capa de Lógica de Negocio y Dominio (`domain`):** Constituye el núcleo esencial de la aplicación. Alberga las entidades del modelo conceptual (*POJOs* puros), los contratos de persistencia (*Repository Interfaces*) y los servicios que implementan las reglas de validación y cálculo del negocio. Esta capa se diseñó de forma completamente agnóstica al framework de Android; no posee importaciones de librerías del sistema operativo (`android.*`) ni dependencias de persistencia física, lo que permite su portabilidad y facilita la ejecución de pruebas unitarias puras en la Máquina Virtual de Java (JVM).
* **Capa de Acceso a Datos (`data`):** Responsable de la persistencia de la información en el almacenamiento local del dispositivo móvil. Implementa la tecnología de persistencia mediante **Room Database** sobre SQLite, aislando las consultas tipadas en *Data Access Objects* (DAOs), las entidades físicas de base de datos (`@Entity`), los mapeadores bidireccionales y las implementaciones concretas de los repositorios requeridos por el dominio.
* **Paquete de Utilitarios Transversales (`util`):** Proporciona rutinas auxiliares compartidas y reutilizables por las demás capas, tales como cálculos cronológicos de fechas y semanas según el estándar ISO-8601 (`DateTimeUtils`), validación de códigos cromáticos hexadecimales (`ColorUtils`) y el despachador de hilos de trabajo asíncronos en segundo plano (`ThreadExecutor`).

#### Estructura de Paquetes del Sistema

A continuación se detalla la organización técnica de paquetes que rige el proyecto:

```
com.david.mystudio/
│
├── AppContainer.java                 // Contenedor central de inyección de dependencias manual
├── StudyApplication.java             // Subclase de Application que administra el ciclo de vida global
│
├── ui/                               // CAPA DE PRESENTACIÓN (Patrón MVP)
│   ├── base/
│   │   ├── BaseView.java             // Contrato base para vistas pasivas
│   │   └── BasePresenter.java        // Contrato base con enlace de ciclo de vida
│   ├── dashboard/                    // Módulo de Inicio y Resumen General
│   │   ├── DashboardContract.java    // Interfaz dual View / Presenter
│   │   ├── DashboardPresenter.java
│   │   ├── DashboardFragment.java
│   │   └── AgendaAdapter.java
│   ├── materias/                     // Módulo de Gestión de Asignaturas (5S)
│   │   ├── MateriasContract.java
│   │   ├── MateriasPresenter.java
│   │   ├── MateriasFragment.java
│   │   ├── MateriasAdapter.java
│   │   └── AddEditMateriaDialogFragment.java
│   ├── enfoque/                      // Módulo de Concentración Zanshin y Temporizador
│   │   ├── EnfoqueContract.java
│   │   ├── EnfoquePresenter.java
│   │   ├── EnfoqueFragment.java
│   │   └── StudyTimerService.java    // Servicio en primer plano (Foreground Service)
│   ├── metas/                        // Módulo de Metas Semanales Kaizen
│   │   ├── MetasContract.java
│   │   ├── MetasPresenter.java
│   │   ├── MetasFragment.java
│   │   ├── MetasAdapter.java
│   │   └── AddMetaDialogFragment.java
│   └── reportes/                     // Módulo de Analítica y Estadísticas
│       ├── ReportesContract.java
│       ├── ReportesPresenter.java
│       ├── ReportesFragment.java
│       └── HistoricoAdapter.java
│
├── domain/                           // CAPA DE DOMINIO Y REGLAS DE NEGOCIO (Java Puro)
│   ├── model/                        // Modelos conceptuales y tipos enumerados
│   │   ├── Materia.java
│   │   ├── SesionEstudio.java
│   │   ├── MetaSemanal.java
│   │   ├── RachaEstudio.java
│   │   ├── ReporteEstudio.java
│   │   ├── MateriaConEstadisticas.java
│   │   ├── EstadoSesion.java         // PENDIENTE, EN_CURSO, PAUSADA, COMPLETADA, CANCELADA
│   │   ├── TipoTecnica.java          // POMODORO, BLOQUE_LIBRE
│   │   ├── EstadoMeta.java           // EN_PROGRESO, CUMPLIDA
│   │   └── TipoPeriodo.java          // SEMANAL, MENSUAL
│   ├── repository/                   // Contratos abstractos de persistencia
│   │   ├── MateriaRepository.java
│   │   ├── SesionEstudioRepository.java
│   │   ├── MetaSemanalRepository.java
│   │   └── RachaRepository.java
│   └── service/                      // Casos de uso y validación de reglas de negocio
│       ├── MateriaService.java
│       ├── SesionEstudioService.java
│       ├── MetaSemanalService.java
│       ├── RachaService.java
│       └── ReporteService.java
│
├── data/                             // CAPA DE ACCESO A DATOS (Room / SQLite)
│   ├── local/
│   │   ├── AppDatabase.java          // Base de datos Room singleton
│   │   ├── entity/                   // Esquemas relacionales físicos
│   │   │   ├── MateriaEntity.java
│   │   │   ├── SesionEstudioEntity.java
│   │   │   ├── MetaSemanalEntity.java
│   │   │   └── RachaEntity.java
│   │   ├── dao/                      // Consultas SQL tipadas mediante interfaces DAO
│   │   │   ├── MateriaDao.java
│   │   │   ├── SesionEstudioDao.java
│   │   │   ├── MetaSemanalDao.java
│   │   │   └── RachaDao.java
│   │   └── mapper/                   // Traductores bidireccionales Entity <-> Domain Model
│   │       ├── MateriaMapper.java
│   │       ├── SesionEstudioMapper.java
│   │       ├── MetaSemanalMapper.java
│   │       └── RachaMapper.java
│   └── repository/                   // Implementaciones concretas de persistencia
│       ├── MateriaRoomRepository.java
│       ├── SesionEstudioRoomRepository.java
│       ├── MetaSemanalRoomRepository.java
│       └── RachaRoomRepository.java
│
└── util/                             // COMPONENTES DE APOYO Y CONCURRENCIA
    ├── DateTimeUtils.java            // Formateador temporal y cómputo de semanas ISO
    ├── ColorUtils.java               // Analizador sintáctico de colorimetría hex
    └── ThreadExecutor.java           // Pool de hilos secundarios y despacho al hilo principal
```

#### Fundamentación Teórica del Patrón Model-View-Presenter (MVP)

En el ámbito del desarrollo para la plataforma Android, las arquitecturas tradicionales basadas únicamente en `Activity` tienden a degenerar en componentes monolíticos sobrecargados de responsabilidades (*God Objects*), combinando control del ciclo de vida, llamadas de persistencia, lógica de negocio y manipulación de vistas. El patrón MVP mitiga esta problemática segregando nítidamente los roles mediante contratos definidos:

1. **La Vista (*View*):** Representada por clases que extienden de `Fragment`, `AppCompatActivity` o `DialogFragment`. La vista es pasiva (*Passive View*); carece por completo de lógica de cálculo o toma de decisiones. Su función se limita a inflar archivos de interfaz XML, asociar escuchadores de eventos (*listeners*) para despachar las interacciones del usuario hacia el Presentador, y actualizar sus controles visuales (como `TextView`, `RecyclerView`, `ProgressBar`) en respuesta directa a las instrucciones emitidas por el Presentador a través del contrato formal `BaseView`.
2. **El Presentador (*Presenter*):** Actúa como el cerebro mediador de cada pantalla. No tiene conocimiento del framework de Android; no referencia instancias de `Context`, `View` ni componentes del SDK. Recibe las intenciones del usuario desde la Vista, invoca a los servicios de dominio pertinentes (`*Service`), procesa los resultados o errores devueltos asíncronamente mediante callbacks, y finalmente instruye a la Vista cómo debe representarse la información (por ejemplo, `vista.mostrarMetas(lista)`, `vista.mostrarError(mensaje)`).
3. **El Modelo (*Model*):** Encapsulado en la capa de dominio. Proporciona los datos del sistema, valida las restricciones intrínsecas del negocio (como límites horarios, unicidad de nombres o cálculos de porcentajes) e interactúa con los contratos de repositorio para acceder o almacenar la información.

#### Inyección de Dependencias Manual mediante `AppContainer`

Para acatar el Principio de Inversión de Dependencias (DIP) sin introducir sobrecostos de rendimiento, dependencias de librerías externas o tiempos de compilación elevados asociados a motores de inyección por reflexión (como Dagger o Hilt), se adoptó una solución pedagógica y robusta: la **inyección manual de dependencias** mediante la clase `AppContainer`.

La clase `StudyApplication` (que hereda de `android.app.Application`) actúa como el punto de inicio de la aplicación, inicializando una instancia única de `AppContainer` durante su método `onCreate()`. A su vez, `AppContainer` gestiona el ciclo de vida de la base de datos `AppDatabase`, los repositorios concretos y los servicios de dominio, inyectando las dependencias requeridas en el constructor de cada clase:

```java
// Ejemplo conceptual de resolución desacoplada en AppContainer:
materiaRepository = new MateriaRoomRepository(database.materiaDao(), executor);
materiaService = new MateriaService(materiaRepository);
```

Posteriormente, cuando un fragmento de presentación es creado (`onAttach` o `onCreate`), solicita al contenedor de dependencias los servicios que necesita para instanciar su Presentador, logrando un acoplamiento mínimo y asegurando que las clases del sistema puedan ser sustituidas por dobles de prueba (*mocks* o *stubs*) en entornos de testeo automatizado.

#### Concurrencia y Servicios en Primer Plano (*Foreground Services*)

La plataforma Android impone restricciones severas sobre la ejecución de tareas intensivas de entrada/salida (I/O) en el hilo principal (*Main Thread* o *UI Thread*), provocando el bloqueo de la pantalla y la aparición del temido diálogo *Application Not Responding* (ANR). Para garantizar una experiencia fluida y reactiva:

* **Despacho de Persistencia con `ThreadExecutor`:** Las consultas, inserciones y actualizaciones sobre la base de datos SQLite se delegan a un grupo de hilos secundarios (*ThreadPoolExecutor*). Una vez obtenido el resultado, un manejador (`android.os.Handler(Looper.getMainLooper())`) transfiere la respuesta de vuelta al hilo principal para que el Presentador pueda interactuar de manera segura con los elementos visuales de la Vista.
* **Preservación del Temporizador con `StudyTimerService`:** Para la funcionalidad de cronometraje de estudio (módulo de Enfoque *Zanshin*), el uso de simples hilos de fondo o tareas asociadas a la Vista resultaba insuficiente, dado que el sistema operativo Android suspende o destruye los procesos en segundo plano para ahorrar energía cuando el usuario cambia de aplicación o la pantalla se apaga. En consecuencia, se implementó un servicio en primer plano (*Foreground Service*) que proyecta una notificación no cancelable en la barra de estado del sistema. Este servicio preserva la cuenta del tiempo de concentración de forma matemática y constante, incluso si el dispositivo entra en modo de bajo consumo o el usuario minimiza la aplicación.

---

   2. ## **Diseño de la Base de Datos** {#diseño-de-la-base-de-datos}

La capa de almacenamiento de datos de **StudyVibe** se diseñó bajo una arquitectura estrictamente local y transaccional (*offline-first*), empleando el motor de base de datos relacional **SQLite** a través del framework de abstracción **Room Database** (versión 2.6.1), provisto por la suite oficial de AndroidX (Google Developers, 2023). El archivo físico generado se denomina `vibestudy_database` y se aloja en el directorio privado protegido del sandbox de la aplicación (`/data/data/com.david.mystudio/databases/`), impidiendo el acceso no autorizado desde otras aplicaciones instaladas en el dispositivo móvil.

      1. ### Modelo Lógico {#modelo-lógico}

El esquema lógico relacional se diseñó respetando rigurosamente las tres primeras Formas Normales (1NF, 2NF y 3NF), eliminando redundancias y garantizando la coherencia e integridad de los datos ante posibles cierres inesperados de la aplicación.

#### Entidades Principales y Cardinalidad

1. **Materia (`materias`):** Constituye el eje central del esquema. Cada registro representa una asignatura académica registrada por el estudiante.
   * *Relación Materia - Sesión de Estudio:* De uno a muchos ($1 : N$). Una asignatura puede albergar múltiples sesiones de estudio planificadas o ejecutadas a lo largo del tiempo, mientras que una sesión pertenece de forma mandatoria y unívoca a una única materia.
   * *Relación Materia - Meta Semanal:* De uno a muchos ($1 : N$). Una asignatura puede recibir múltiples compromisos u objetivos semanales de estudio distribuidos cronológicamente entre las diferentes semanas del año lectivo, pero cada meta semanal está vinculada exclusivamente a una sola materia.
2. **SesionEstudio (`sesiones_estudio`):** Almacena tanto la programación futura de bloques de aprendizaje como el registro histórico de las sesiones concluidas con cronómetro en tiempo real. 
   * Posee una clave foránea `id_materia` que referencia a la clave primaria `id` de la tabla `materias`.
   * Para asegurar la integridad referencial, se definió la cláusula `onDelete = ForeignKey.RESTRICT`. Esta regla impide que una materia con historial de estudio asociado sea eliminada físicamente de forma accidental, forzando la aplicación del patrón de **borrado lógico** (`activa = 0`) para resguardar las métricas históricas de rendimiento del estudiante.
3. **MetaSemanal (`metas_semanales`):** Representa el objetivo cuantitativo de horas que el alumno se propone cursar para una materia en un ciclo semanal determinado (semana del año lectivo).
   * Contiene una clave foránea `id_materia` con regla de eliminación restrictiva (`RESTRICT`).
   * Para garantizar la unicidad de los registros por periodo y evitar metas repetidas para la misma asignatura en un mismo lapso temporal, se estableció un **índice compuesto único** (`unique = true`) sobre la combinación de atributos `(id_materia, numero_semana, anio)`.
4. **RachaEstudio (`racha_estudio`):** Entidad orientada al fomento disciplinario y seguimiento de la constancia de hábitos diarios (*Kaizen*).
   * Se diseñó bajo el patrón de **registro único local (Singleton en Base de Datos)** con una clave primaria constante fija (`id = 1`). Mantiene el conteo acumulado de días consecutivos de estudio, la fecha de la última sesión completada para evaluar caducidades (tras más de 48 horas sin registrar actividad el contador reinicia a 0) y el récord histórico máximo alcanzado por el estudiante.

#### Reglas de Normalización y Tipado Físico

En concordancia con las particularidades del motor SQLite en Android, los tipos de datos lógicos se mapearon a tipos de afinidad estándar de SQLite:
* Los identificadores autonuméricos y enteros se persisten como `INTEGER`.
* Los valores de tiempo, fechas y marcas temporales (*timestamps*) se manejan en formato de milisegundos transcurridos desde la época Unix (`epoch millis`) almacenados como `INTEGER` (`long` de Java), lo que facilita consultas de ordenamiento cronológico y operaciones aritméticas de agregación (`SUM`, `AVG`, `MAX`) sin costo de conversión de cadenas.
* Los importes continuos de dedicación horaria (horas de estudio y horas objetivo) se gestionan como números de punto flotante de doble precisión (`REAL` / `double`).
* Los estados categóricos y tipos enumerados se persisten como cadenas de texto (`TEXT` / `String`) para garantizar la legibilidad y facilitar labores de depuración e inspección directa de la base de datos.

---

      2. ### Diccionario de Datos {#diccionario-de-datos}

A continuación se exponen las especificaciones detalladas de cada una de las tablas físicas generadas en SQLite a través del compilador de anotaciones de Room Database.

#### Tabla 1. *Especificación técnica de la tabla `materias`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único de la materia. |
| `nombre` | `TEXT` | `NOT NULL` | - | Denominación completa de la asignatura (ej. "Aplicaciones Móviles I"). |
| `sigla` | `TEXT` | `NULLABLE` | `NULL` | Código o sigla curricular representativa (ej. "INF-312"). |
| `color_hex` | `TEXT` | `NOT NULL` | `"#263238"` | Código hexadecimal cromático utilizado en la UI (ej. `"#4A90E2"`). |
| `fecha_creacion`| `INTEGER` | `NOT NULL` | - | Marca temporal en milisegundos que registra la fecha y hora de alta. |
| `activa` | `INTEGER` | `NOT NULL` | `1` (Verdadero) | Bandera booleana de estado (1 = Activa, 0 = Baja lógica). |

*Nota:* La tabla `materias` cuenta con un índice por clave primaria. Las operaciones de baja implementadas en la interfaz de usuario conmutan la bandera `activa` a `0`, preservando la integridad referencial de los registros históricos.

#### Tabla 2. *Especificación técnica de la tabla `sesiones_estudio`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único de la sesión de estudio. |
| `id_materia` | `INTEGER` | `FOREIGN KEY (materias.id) ON DELETE RESTRICT` | - | Clave foránea que referencia a la materia asociada. Indexada. |
| `fecha_programada`| `INTEGER` | `NULLABLE` | `NULL` | Marca temporal prevista para sesiones planificadas a futuro. |
| `fecha_hora_inicio`| `INTEGER` | `NULLABLE` | `NULL` | Marca temporal real en que el estudiante inició el cronómetro. |
| `fecha_hora_fin` | `INTEGER` | `NULLABLE` | `NULL` | Marca temporal en que se dio por concluida la sesión. |
| `duracion_minutos`| `INTEGER` | `NOT NULL` | `0` | Tiempo neto efectivo de estudio acumulado (en minutos). |
| `estado` | `TEXT` | `NOT NULL` | `'PENDIENTE'` | Estado de ciclo de vida (`PENDIENTE`, `EN_CURSO`, `PAUSADA`, `COMPLETADA`, `CANCELADA`). |
| `tecnica` | `TEXT` | `NOT NULL` | `'BLOQUE_LIBRE'` | Técnica de enfoque empleada (`POMODORO` o `BLOQUE_LIBRE`). |
| `notas_observaciones`| `TEXT` | `NULLABLE` | `NULL` | Anotaciones temáticas registradas por el usuario al finalizar. |

*Nota:* Se encuentra definido un índice simple sobre el campo `id_materia` para optimizar las consultas de agregación y los cruces de tablas para reportes estadísticos.

#### Tabla 3. *Especificación técnica de la tabla `metas_semanales`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Auto-generado | Identificador numérico único del registro de meta. |
| `id_materia` | `INTEGER` | `FOREIGN KEY (materias.id) ON DELETE RESTRICT` | - | Asignatura sobre la cual se proyecta el objetivo. Indexada. |
| `numero_semana` | `INTEGER` | `NOT NULL` | - | Número de semana en el calendario anual (rango 1 a 53, ISO-8601). |
| `anio` | `INTEGER` | `NOT NULL` | - | Año calendario correspondiente a la semana lectiva (ej. 2026). |
| `horas_objetivo` | `REAL` | `NOT NULL` | `0.0` | Umbral de horas comprometidas para la semana (entre 1.0 y 50.0). |
| `horas_cumplidas`| `REAL` | `NOT NULL` | `0.0` | Sumatoria acumulativa de horas registradas a través de sesiones completadas. |
| `estado` | `TEXT` | `NOT NULL` | `'EN_PROGRESO'` | Estado cualitativo de la meta (`EN_PROGRESO` o `CUMPLIDA`). |

*Nota:* La tabla dispone de un índice compuesto único: `CREATE UNIQUE INDEX index_metas_unicas ON metas_semanales(id_materia, numero_semana, anio)`.

#### Tabla 4. *Especificación técnica de la tabla `racha_estudio`*

| Nombre del Campo | Tipo Físico (Room / SQLite) | Restricciones / Claves | Valor por Defecto | Descripción Funcional |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY` | `1` (Constante) | Clave primaria fija para garantizar el comportamiento Singleton. |
| `dias_consecutivos`| `INTEGER` | `NOT NULL` | `0` | Número de días consecutivos con al menos una sesión completada. |
| `ultima_fecha_estudio`| `INTEGER`| `NULLABLE` | `NULL` | Marca temporal del día en que se finalizó la última sesión. |
| `racha_maxima_historica`| `INTEGER`| `NOT NULL` | `0` | Valor récord de días continuos acumulados por el usuario. |

---

   3. ## **Diseño de la Interfaz de Usuario** {#diseño-de-la-interfaz-de-usuario}

      1. ### WireFrames y Mockups {#wireframes-y-mockups}

El diseño de la experiencia de usuario (UX) e interfaz visual (UI) de **StudyVibe** se concibió bajo las directrices oficiales del sistema de diseño **Material Design 3 (Material You)** promovido por Google (2023), asegurando consistencia visual, jerarquía tipográfica, retroalimentación táctil y accesibilidad inclusiva.

#### Lineamientos Generales y Filosofía de Diseño

1. **Jerarquía Visual y Reducción de Carga Cognitiva:** Dado que la aplicación está destinada al estudio académico y a la concentración, las pantallas evitan la saturación de información y el desorden perceptivo (*Noise Reduction*). Se utilizan tarjetas de superficie tonal (`MaterialCardView`) con radios de curvatura consistentes (12dp y 20dp) y márgenes periféricos normalizados en `res/values/dimens.xml` (16dp estándar, 8dp para espaciados internos y 24dp para encabezados principales).
2. **Arquitectura de Navegación de Actividad Única (*Single-Activity Pattern*):** La aplicación adopta el patrón recomendado por Google consistente en una sola actividad anfitriona (`MainActivity`) que gestiona un contenedor de fragmentos (`androidx.fragment.app.FragmentContainerView`) y un menú de navegación inferior fijo (`com.google.android.material.bottomnavigation.BottomNavigationView`). Este diseño permite que el usuario acceda de forma inmediata y ergonómica con una sola mano a las cinco áreas principales de la solución:
   * *Inicio (Dashboard)*
   * *Materias (Gestión 5S)*
   * *Enfoque (Temporizador Zanshin)*
   * *Metas (Metas Semanales Kaizen)*
   * *Reportes (Analítica y Rendimiento)*
3. **Accesibilidad y Contraste Cromático (WCAG 2.1 Nivel AA):** Los componentes de texto e íconos se ajustan estrictamente al criterio de contraste mínimo de 4.5:1 frente a sus fondos inmediatos. Para garantizar una experiencia confortable tanto en ambientes de alta iluminación como en jornadas de estudio nocturno, se construyó un esquema cromático simétrico:
   * **Modo Claro (*Day*):** Fondo general neutro gris claro (`@color/color_background` = `#F8F9FA`), superficies de tarjeta en blanco puro (`@color/color_surface` = `#FFFFFF`) y textos de alto contraste en carbón oscuro (`@color/color_on_surface` = `#1F1F1F`).
   * **Modo Oscuro (*Night*):** Administrado mediante la carpeta especializada `res/values-night/`. Emplea un fondo oscuro profundo de baja emisión lumínica (`@color/color_background` = `#121212`), tarjetas en gris oscuro elevado (`@color/color_surface` = `#1E1E1E`) y textos de tipografía clara (`@color/color_on_surface` = `#E6E6E6`), evitando la inversión automática descontrolada y manteniendo legibles los 8 colores identificadores de las materias académicas.
4. **Retroalimentación No Intrusiva:** Toda acción destructiva (como la baja de una asignatura o la interrupción anticipada de una sesión de estudio) requiere confirmación explícita mediante cuadros de diálogo modales (`MaterialAlertDialogBuilder`), mientras que los guardados y actualizaciones satisfactorias informan al estudiante a través de componentes transitorios *Snackbar* y animaciones sutiles.

> [!NOTE]
> *Espacio reservado para la incorporación de los diagramas, wireframes de alta fidelidad y mockups gráficos de las interfaces de usuario en etapas posteriores del informe técnico.*

---

      2. ### Diseño de Componentes y Módulos {#diseño-de-componentes-y-módulos}

A nivel de componentes reutilizables y estructura modular, la interfaz de **StudyVibe** se desglosa en cinco módulos funcionales articulados mediante la capa de presentación:

#### 1. Módulo Inicio (Dashboard)
Constituye el panel de bienvenida del sistema. Su diseño está estructurado para proporcionar al alumno un panorama holístico e inmediato de su situación académica:
* **Banner de Racha Kaizen (`crdRachaKaizen`):** Indicador visual superior con ícono de constancia y contador numérico en tiempo real (`txtRachaDias`), que resalta la disciplina acumulada y notifica implícitamente el tiempo restante para evitar la pérdida del hábito diario.
* **Tarjeta de Compromiso Semanal Global (`crdMetaGlobal`):** Componente compuesto por un indicador numérico de horas (ej. "14.0 / 20.0 hrs"), una barra de progreso horizontal lineal (`prgMetaGlobal`) calculada en tiempo real y un mensaje contextual que proyecta el porcentaje de cumplimiento semanal.
* **Banner Shinrin-yoku:** Espacio pedagógico que emite recomendaciones breves orientadas al bienestar mental, promoviendo pausas activas lejos de pantallas y el descanso visual.
* **Agenda de Sesiones de Hoy (`rvAgendaHoy`):** Contenedor `RecyclerView` que renderiza tarjetas de sesión (`item_agenda_sesion.xml`) con el distintivo cromático de la materia, horario programado, duración estimada y etiqueta de estado pendiente.
* **Botón Principal de Acción (*Call to Action* - CTA):** Botón flotante o extendido de ancho completo que transiciona al estudiante directamente hacia el temporizador de concentración con un solo toque.

#### 2. Módulo de Gestión de Asignaturas (Materias 5S)
Permite la administración organizada de las asignaturas activas cursadas durante el semestre universitario:
* **Catálogo de Materias (`rvMaterias`):** Lista adaptativa optimizada mediante el patrón `RecyclerView.ViewHolder` (`item_materia.xml`). Cada elemento expone la sigla curricular, nombre de la asignatura, volumen acumulado de horas estudiadas y un acento cromático lateral personalizable.
* **Botón de Acción Flotante (FAB "+"):** Despliega el diálogo modal de creación (`dialog_add_subject.xml` mediante `AddEditMateriaDialogFragment`).
* **Selector Cromático Interactivo:** Paleta integrada de 8 opciones de color en formato de muestras circulares (*color swatches*), validada con código hexadecimal para propagarse unificadamente a las gráficas, barras de progreso y temporizador.
* **Buscador Reactivo y Estado Vacío (*Empty State*):** Campo de búsqueda en vivo que filtra la lista al tipear y un contenedor alternativo con ilustración y mensaje de ayuda si no existen asignaturas registradas.

#### 3. Módulo de Concentración Zanshin (Temporizador)
Diseñado bajo el principio de atención plena (*Zanshin*), proveyendo una vista despejada que minimiza las interrupciones externas durante la sesión de estudio:
* **Anillo de Temporizador Central:** Representación visual circular del tiempo transcurrido o restante, implementado con tipografía de escala *Headline Large* monoespaciada para facilitar la lectura a distancia.
* **Selector de Técnica de Estudio:** Control segmentado que permite conmutar dinámicamente entre la técnica Pomodoro (bloque rígido de 25 minutos con pausas programadas) y la técnica de Bloque Libre (cronómetro ascendente sin límite predeterminado).
* **Botonera de Control de Estado:** Controles de alto contraste táctil para iniciar, pausar sin pérdida de conteo, reanudar o finalizar la sesión de trabajo.
* **Bitácora Cualitativa de Estudio:** Campo de texto multilínea desplegable al momento de la conclusión, permitiendo registrar las notas, tópicos o ejercicios específicos aprendidos para asociarlos al registro histórico de la base de datos.
* **Sincronización con Servicio Persistente:** Enlace bidireccional con `StudyTimerService`, actualizando la interfaz cada segundo mediante transmisiones internas sin bloquear el hilo principal.

#### 4. Módulo de Metas Semanales (Kaizen Goals)
Facilita la fijación de objetivos cuantitativos y la evaluación formativa del balance académico semanal:
* **Navegador Cronológico de Semanas:** Selector de rango que permite retroceder o avanzar entre las distintas semanas lectivas del año lectivo, recalculando las métricas en pantalla de forma instantánea.
* **Tarjetas de Meta por Asignatura (`item_meta.xml`):** Cada tarjeta exhibe la materia asociada, el total de horas cumplidas frente al objetivo fijado y una barra de progreso porcentual dinámica.
* **Componentes Visuales de Estado Condicional:**
  * *Estado En Progreso:* Barra azul con cálculo dinámico del porcentaje de avance.
  * *Estado Meta Cumplida (100%):* La tarjeta conmuta automáticamente a tono verde de éxito con el distintivo de felicitación y verificación de objetivo logrado.
  * *Alerta de Rezagado:* Resalte en tono ámbar/rojo con indicación de horas faltantes para asignaturas que presentan un retraso crítico respecto al cierre de la semana.
* **Diálogo de Configuración de Meta (`dialog_add_meta.xml`):** Formulario modal con validación estricta de rangos admisibles (1.0 a 50.0 horas por materia).

#### 5. Módulo de Reportes y Estadísticas (Analítica Visual)
Transforma los datos históricos cuantitativos en representaciones visuales comprensibles para la autoevaluación del alumno:
* **Selector de Periodo Temporal:** Pestañas superiores (*Tabs*) para conmutar entre vistas de "Semana Actual", "Mes Calendario" e "Histórico Consolidado".
* **Tarjetas de Resumen Ejecutivo (KPIs):** Paneles destacados que calculan la sumatoria de tiempo neto acumulado en horas y minutos, el promedio de dedicación diaria y la asignatura con mayor inversión horaria.
* **Gráfico Circular de Distribución (*PieChart*):** Representa la proporción porcentual de dedicación por materia, asignando a cada sector el color exacto registrado para la asignatura en el módulo de materias.
* **Histograma Diario de Actividad (*BarChart*):** Gráfico de barras que desglosa el volumen de minutos de estudio ejecutados a lo largo de los días de la semana (Lunes a Domingo), permitiendo identificar visualmente patrones de descanso o procrastinación.
* **Historial Cronológico de Actividad (`rvHistoricoSesiones`):** Lista desplazable que detalla cronológicamente las sesiones concluidas, exhibiendo fecha exacta, materia, duración neta y anotaciones temáticas.

---

5. # **Implementación y Pruebas** {#implementación-y-pruebas}

   1. ## **Entorno de Implementación** {#entorno-de-implementación}

La materialización de **StudyVibe** se desarrolló bajo un entorno de ingeniería formal, empleando las herramientas oficiales y actualizadas del ecosistema Android nativo. A continuación se especifican las características de software y dependencias del proyecto:

#### Configuración de la Plataforma de Desarrollo

* **Entorno de Desarrollo Integrado (IDE):** Android Studio Ladybug / Koala Feature Drop, configurado sobre el sistema operativo de desarrollo con soporte para el kit de desarrollo Java.
* **Lenguaje de Programación:** Java Estándar (JDK 11), fijado explícitamente en el archivo de construcción del módulo (`compileOptions { sourceCompatibility = JavaVersion.VERSION_11; targetCompatibility = JavaVersion.VERSION_11; }`).
* **Sistema de Automatización de Construcción:** Gradle versión 8.9 / 9.5 con Android Gradle Plugin (AGP) versión 8.5.1 / 9.5.1, utilizando scripts de configuración tipados en Kotlin (`build.gradle.kts` y `settings.gradle.kts`) y catálogo de versiones centralizado (`gradle/libs.versions.toml`).
* **Niveles de API del SDK de Android:**
  * *Nivel de Compilación (`compileSdk`):* API 35 (soporte ampliado hasta compilador API 37).
  * *Nivel Mínimo de Soporte (`minSdk`):* API 24 (Android 7.0 Nougat), lo que confiere una cobertura superior al 95% de los dispositivos Android activos a nivel global.
  * *Nivel Objetivo (`targetSdk`):* API 35 (Android 15), garantizando la adhesión a las políticas de seguridad más recientes de Google, incluyendo la obligatoriedad de declarar permisos para servicios en primer plano y notificaciones en tiempo de ejecución.
* **Espacio de Nombres e Identificador de Aplicación:** `com.david.mystudio` (`versionCode = 1`, `versionName = "1.0"`).

#### Matriz de Bibliotecas y Dependencias Utilizadas

En apego a los requerimientos de la asignatura y para asegurar un código mantenible, ligero y sin dependencias superfluas, el proyecto se limitó al uso exclusivo de las bibliotecas oficiales de la suite **AndroidX** y Google:

#### Tabla 5. *Inventario oficial de dependencias del proyecto*

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
1. **Emuladores Android Virtual Device (AVD):**
   * Pixel 7 Pro emulado con Android 14 (API 34, resolución 1440 × 3120 pixels, densidad 560 dpi).
   * Nexus 5 emulado con Android 8.0 (API 26, resolución 1080 × 1920 pixels, densidad xxhdpi), validando el comportamiento en terminales con recursos moderados.
2. **Dispositivos Móviles Físicos:**
   * Smartphone con Android 13 (API 33, arquitectura ARM64, 6 GB de memoria RAM), verificando el comportamiento real del servicio en primer plano del cronómetro con la pantalla apagada, la respuesta ante rotación física del dispositivo y el rendimiento térmico/batería.

---

   2. ## **Proceso de Desarrollo** {#proceso-de-desarrollo}

El ciclo de desarrollo de software se articuló mediante el marco de trabajo ágil **Scrum**, iterando en ciclos de trabajo cortos (*Sprints*) y aplicando una estrategia de construcción técnica de orden ascendente (**Bottom-Up**):

#### Enfoque Constructivo por Capas (*Bottom-Up*)

1. **Fase 1: Capa de Persistencia y Acceso a Datos (Room & SQLite):** Se inició el desarrollo modelando las entidades relacionales (`@Entity`), los contratos DAO con consultas SQL optimizadas (`@Dao`), la configuración de la base de datos `AppDatabase` y la escritura de los mapeadores estáticos bidireccionales (`Mapper`). Esto aseguró un motor de persistencia robusto y validado antes de escribir interfaces gráficas.
2. **Fase 2: Capa de Dominio y Lógica de Negocio (POJOs y Servicios):** Se construyeron las entidades del modelo conceptual de Java puro y los servicios de negocio (`MateriaService`, `SesionEstudioService`, `MetaSemanalService`, `RachaService`, `ReporteService`). En esta etapa se encapsularon las reglas de negocio, tales como la validación de no duplicidad de asignaturas, la exclusividad de una sesión activa a la vez, y el cómputo automático de porcentajes y rachas consecutivas.
3. **Fase 3: Capa de Presentación (MVP Pantalla por Pantalla):** Una vez asegurada la lógica, se estructuraron los contratos `*Contract`, implementando los presentadores e inflando los layouts XML con Material Design 3 de forma individual para cada módulo (Dashboard, Materias, Enfoque, Metas y Reportes).
4. **Fase 4: Integración Global y Cableado:** Se consolidó la inyección de dependencias centralizada en `AppContainer`, se configuró la navegación coordinada en `MainActivity` mediante `BottomNavigationView`, y se declaró el servicio en primer plano `StudyTimerService` en el archivo `AndroidManifest.xml` con los permisos correspondientes de notificaciones y ejecución en background.

#### Diagnóstico y Resolución Quirúrgica de Defectos Críticos (Bugs de Integración)

Durante las pruebas de integración del paquete final APK, el proceso de desarrollo enfrentó y subsanó dos incidentes técnicos de alta complejidad arquitectónica:

* **Incidente 1: Defecto de Contraste y Adaptabilidad en Modo Oscuro (BUG 1 / 1.1):**
  * *Diagnóstico:* Al ejecutarse en terminales con el modo oscuro activado por el sistema operativo, las tarjetas de Material Design 3 heredaban automáticamente fondos oscuros, pero las etiquetas de texto permanecían configuradas con el color `@color/color_on_surface` estático en negro (`#1F1F1F`), produciendo texto negro sobre fondo negro ilegible.
  * *Resolución:* Se creó la carpeta de recursos especializados `res/values-night/`, implementando el archivo simétrico `res/values-night/colors.xml` con la paleta de tokens oscuros definida formalmente en el diseño, y se configuró `res/values-night/themes.xml` estableciendo `android:windowLightStatusBar = false`, asegurando una legibilidad impecable en ambos modos de iluminación.
* **Incidente 2: Ciclo de Vida del Temporizador, Intercepción de Navegación y Sesiones Huérfanas (BUG 2):**
  * *Diagnóstico:* Al iniciar una sesión en el cronómetro de Enfoque y pulsar otra sección en el menú inferior, `MainActivity` ejecutaba `FragmentTransaction.replace()`, destruyendo la vista `EnfoqueFragment` y su presentador. Al regresar, la vista aparecía reseteada y el intento de iniciar un nuevo conteo arrojaba error de sesión duplicada. Adicionalmente, si el dispositivo se apagaba o reiniciaba durante una sesión en marcha, el registro en SQLite quedaba bloqueado perpetuamente en estado `EN_CURSO`.
  * *Resolución Quirúrgica en Tres Puntos:*
    1. *Intercepción de Navegación:* En `MainActivity`, se condicionó el cambio de pestaña del menú inferior; si `StudyTimerService.isSesionEnCurso()` es verdadero, el sistema intercepta la transición y despliega un diálogo de confirmación obligatoria al usuario ("¿Deseas continuar estudiando o detener y cambiar de pantalla?").
    2. *Restauración Reactiva del Presentador:* Se dotó a `EnfoquePresenter` del método `restaurarSesionActivaSiExiste()`, permitiendo que si el fragmento es recreado mientras el servicio de cronómetro sigue activo, el presentador recupere la referencia de la sesión en curso y sincronice los controles visuales automáticamente.
    3. *Sanitización en Inicio de Aplicación:* En el método `onCreate()` de `StudyApplication`, se incorporó una consulta atómica de inicialización en `SesionEstudioDao` que busca y cancela automáticamente cualquier sesión huérfana en estado `EN_CURSO` previa al encendido, garantizando que el usuario jamás quede bloqueado operativamente tras un apagado forzado del teléfono.

---

   3. ## **Pruebas y Validación** {#pruebas-y-validación}

Para certificar que la aplicación cumple con los estándares de calidad del software y responde con exactitud a los requerimientos de la asignatura, se implementó una estrategia integral de pruebas que combinó pruebas unitarias automatizadas, verificación funcional de historias de usuario y validación de requisitos no funcionales conforme a la norma internacional **ISO/IEC 25010** (ISO/IEC, 2011).

#### 1. Pruebas Unitarias Automatizadas en JVM

Se diseñaron suites de prueba unitarias ejecutadas directamente sobre la Máquina Virtual de Java local mediante el framework **JUnit 4**, verificando la correctitud de las operaciones matemáticas, conversiones cronológicas y validaciones de dominio sin requerir el arranque de emuladores:

* **Suite de Pruebas de Metas (`MetaSemanalTest`):**
  * Verificación del cálculo porcentual de avance: se comprueba que una meta de 10.0 horas con 2.5 horas ejecutadas retorne exactamente 25.0%.
  * Validación del umbral de meta cumplida: se valida que si las horas ejecutadas igualan o superan la meta (ej. 10.0 / 10.0 hrs), el método `isCumplida()` cambie a verdadero y active el estado `CUMPLIDA`.
  * Verificación del límite operativo: se corrobora que la asignación de metas inferiores a 1.0 hora o superiores a 50.0 horas arroje la excepción de negocio esperada.
* **Suite de Pruebas de Utilidades Temporales (`DateTimeUtilsTest`):**
  * Conversión de minutos a cadenas legibles: se valida que 90 minutos se formateen como "1h 30m" y 45 minutos como "45m".
  * Cálculo de semanas del año: se verifica que fechas en el mes de noviembre de 2026 retornen el identificador de semana ISO-8601 correcto (Semana 47).

#### 2. Matriz de Validación de Requisitos Funcionales (Criterios BDD)

Se sometió a prueba cada una de las 12 Historias de Usuario descritas en la ingeniería de requisitos, comprobando el cumplimiento estricto de sus escenarios *Dado-Cuando-Entonces* en el entorno de ejecución:

#### Tabla 6. *Matriz de verificación funcional de Historias de Usuario*

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

#### 3. Validación de Requisitos No Funcionales (ISO/IEC 25010)

La evaluación de los atributos de calidad técnica arrojó los siguientes resultados observables:
* **Eficiencia y Desempeño (RNF-04 y RNF-05):** Las transiciones entre fragmentos se registraron en un promedio de 85 ms (muy inferior al umbral máximo permitido de 150 ms). Las consultas de inserción y lectura en SQLite se resolvieron en menos de 22 ms, gracias al uso de índices tipados en las claves foráneas de Room.
* **Consumo de Memoria y Recursos (RNF-06):** Monitoreado mediante el *Android Studio Profiler*. La aplicación mantuvo una huella de memoria RAM estable de entre 45 MB y 68 MB durante sesiones prolongadas del temporizador, sin presentar fugas de memoria (*memory leaks*), cumpliendo holgadamente el límite de 120 MB establecido.
* **Tolerancia a Fallos y Confiabilidad (RNF-09 y RNF-10):** Se sometió la aplicación a rotaciones reiteradas de pantalla durante el cronómetro activo, transiciones a segundo plano e interrupciones por llamadas telefónicas simuladas. En todos los casos, el servicio en primer plano mantuvo el conteo de la sesión activo con 0% de pérdida de datos. La aplicación operó de forma continua e impecable en modo avión, confirmando su autonomía *offline-first*.

---

6. # **Conclusiones y Trabajo Futuro** {#conclusiones-y-trabajo-futuro}

   1. ## **Conclusión** {#conclusión}

El desarrollo integral del proyecto **StudyVibe** demuestra de manera tangible cómo la ingeniería de software y las metodologías de desarrollo móvil nativo pueden aplicarse eficazmente para resolver una problemática cotidiana y crítica en la vida universitaria: la desorganización, la procrastinación y la carencia de métricas objetivas sobre la gestión del tiempo de estudio independiente.

A través de la integración armónica entre técnicas comprobadas de administración temporal (la técnica Pomodoro y el enfoque de *Time Blocking*) y filosofías tradicionales japonesas orientadas a la disciplina formativa (*Kaizen* para la mejora continua gradual, *Zanshin* para la concentración y presencia plena, y las *5S* para el orden del espacio académico), se consolidó una herramienta móvil que no se limita a cronometrar horas, sino que educa y motiva al estudiante mediante la visibilidad de su propio progreso.

Desde el punto de vista arquitectónico, la adopción de una arquitectura en tres capas desacopladas, articulada con el patrón Model-View-Presenter (MVP) y los principios SOLID, demostró ser una decisión plenamente acertada para el ecosistema Android con Java. Se obtuvo una base de código limpia, modular y mantenible, en la que la lógica de negocio y las fórmulas de cálculo se encuentran blindadas frente a las alteraciones del ciclo de vida del sistema operativo. Asimismo, la persistencia local robusta gestionada a través de Room Database sobre SQLite confirmó la viabilidad técnica y la confiabilidad del modelo *offline-first*, garantizando que el usuario tenga pleno control y disponibilidad de sus datos académicos sin depender de conexiones a redes externas.

---

   2. ## **Logros del Proyecto** {#logros-del-proyecto}

Entre los principales resultados y metas alcanzadas a la culminación de esta fase del proyecto destacan:

1. **Cumplimiento Funcional del 100%:** Se implementaron, integraron y verificaron satisfactoriamente la totalidad de las doce Historias de Usuario (`HU-01` a `HU-12`) comprendidas en las cinco épicas del sistema, satisfaciendo la totalidad de los criterios de aceptación en formato BDD y las condiciones de la *Definition of Done (DoD)*.
2. **Arquitectura Limpia sin Sobrecarga de Dependencias:** Se logró un desacoplamiento estricto de componentes mediante inyección de dependencias puramente manual (`AppContainer`), prescindiendo de librerías complejas de reflexión y manteniendo un tamaño de paquete APK sumamente reducido y eficiente.
3. **Resiliencia Operativa y Ciclo de Vida Robusto:** Implementación exitosa de un servicio en primer plano (`StudyTimerService`) que garantiza la persistencia matemática del cronómetro ante apagados de pantalla o cambios de aplicación, complementado con mecanismos de sanitización al inicio del sistema para evitar registros huérfanos.
4. **Experiencia de Usuario Adaptativa y Accesible:** Creación de una interfaz gráfica moderna regida por Material Design 3, completamente adaptada a modos diurno y nocturno (*Day/Night*) mediante recursos semánticos en `res/values/` y `res/values-night/`, respetando los ratios de contraste recomendados por la norma WCAG 2.1 nivel AA.
5. **Alineación Académica Institucional:** Se materializaron los objetivos pedagógicos de la asignatura *Aplicaciones Móviles I* de la Universidad Privada Domingo Savio (UPDS), evidenciando destreza en programación orientada a objetos en Java, manejo de servicios del sistema, gestión de hilos asíncronos y bases de datos relacionales embebidas.

#### Líneas de Trabajo Futuro

Como evolución natural del proyecto para versiones posteriores, se contemplan las siguientes líneas de desarrollo:
* **Sincronización en la Nube (*Cloud Sync*):** Implementación de una arquitectura híbrida que permita respaldar opcionalmente las sesiones, materias y metas en una base de datos remota (como Firebase Firestore o una API RESTful desarrollada con Spring Boot), habilitando la sincronización de información entre múltiples dispositivos del estudiante.
* **Integración con Plataformas Universitarias:** Desarrollo de módulos de enlace con plataformas institucionales (como el portal estudiantil de la UPDS) para importar de manera automática las asignaturas inscritas y los calendarios oficiales de exámenes del semestre.
* **Exportación y Compartición de Reportes Analíticos:** Incorporación de un generador de reportes en formato PDF que permita al estudiante exportar resúmenes formales de su dedicación horaria para justificar tutorías académicas o revisiones de proyectos.
* **Widgets Interactivos para la Pantalla de Inicio:** Creación de componentes *App Widgets* de Android que permitan al usuario visualizar su racha de estudio y accionar el temporizador de forma inmediata sin necesidad de ingresar al menú principal de la aplicación.

---

7. # **Referencias Bibliográficas** {#referencias-bibliográficas}

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

   1. ## **Anexo Repositorio Proyecto** {#anexo-repositorio-proyecto}

El código fuente íntegro, los recursos gráficos, la estructura de carpetas y los scripts de construcción automatizada del proyecto **StudyVibe** se encuentran versionados y respaldados bajo el sistema de control de versiones distribuido **Git**.

#### Ficha Técnica del Repositorio

* **Organización del Proyecto:** `VibeStudy` / `com.david.mystudio`
* **Estructura de Ramas de Trabajo:**
  * `main` / `master`: Rama principal con código consolidado y funcional para entregas oficiales.
  * `feature/*`: Ramas temáticas empleadas para el desarrollo incremental de cada módulo (ej. `feature/modulo-materias`, `feature/temporizador-enfoque`, `feature/metas-kaizen`).
  * `bugfix/*`: Ramas quirúrgicas utilizadas para la resolución y estabilización de incidentes de interfaz y ciclo de vida (`bugfix/dark-mode-contrast`, `bugfix/timer-lifecycle`).

#### Instrucciones de Clonación, Apertura y Construcción

Para compilar y ejecutar el proyecto desde una estación de trabajo de desarrollo:

1. **Clonación del Repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO_GIT>
   cd VibeStudy
   ```
2. **Apertura en Android Studio:**
   * Iniciar Android Studio.
   * Seleccionar la opción *Open* y apuntar al directorio raíz del proyecto (`VibeStudy`).
   * Aguardar la sincronización automática de Gradle y la resolución de dependencias desde el catálogo `libs.versions.toml`.
3. **Construcción y Generación del Paquete APK (Línea de Comandos):**
   * En sistemas operativos Windows (PowerShell):
     ```powershell
     .\gradlew.bat assembleDebug
     ```
   * En sistemas operativos Linux / macOS:
     ```bash
     ./gradlew assembleDebug
     ```
   El archivo binario ejecutable compilado se generará en la ruta:
   `app/build/outputs/apk/debug/app-debug.apk`.
4. **Ejecución de Pruebas Unitarias Automatizadas:**
   ```bash
   ./gradlew testDebugUnitTest
   ```

---

   2. ## **Anexo Glosario de Términos** {#anexo-glosario-de-términos}

* **ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad):** Conjunto de propiedades que garantizan que las transacciones en una base de datos relacional (como SQLite) se procesen de manera confiable y segura contra fallos del sistema.
* **Android Runtime (ART):** Entorno de ejecución de aplicaciones en la plataforma Android que traduce los archivos de bytecode DEX a código máquina nativo mediante compilación mixta anticipada (AOT) y en tiempo de ejecución (JIT).
* **ANR (Application Not Responding):** Cuadro de diálogo del sistema operativo Android que se despliega ante el usuario cuando el hilo principal de la aplicación se bloquea por más de 5 segundos realizando operaciones pesadas de entrada/salida o cálculos intensivos.
* **BDD (Behavior-Driven Development):** Práctica de desarrollo basada en el comportamiento que especifica el funcionamiento esperado de una aplicación en lenguaje natural estructurado mediante las cláusulas *Dado (Given)*, *Cuando (When)* y *Entonces (Then)*.
* **DAO (Data Access Object):** Patrón de diseño que proporciona una interfaz abstracta que encapsula y desacopla el acceso y las operaciones de lectura/escritura sobre el motor de base de datos.
* **DEX (Dalvik Executable):** Formato de archivo binario optimizado para dispositivos móviles al cual se compila el código fuente Java en la plataforma Android.
* **Entity (Entidad en Room):** Clase Java anotada con `@Entity` que define la estructura relacional de una tabla en la base de datos SQLite administrada por Room.
* **Foreground Service (Servicio en Primer Plano):** Servicio de Android que realiza operaciones perceptibles por el usuario y que debe mantener obligatoriamente una notificación activa en la barra de estado, lo que le confiere alta prioridad de ejecución evitando que el sistema operativo lo destruya para liberar recursos.
* **INVEST:** Acrónimo propuesto por Bill Wake que resume las seis características de calidad que debe cumplir una Historia de Usuario efectiva: *Independiente, Negociable, Valiosa, Estimable, Pequeña (Small) y Comprobable (Testable)*.
* **Kaizen:** Término de origen japonés que significa "cambio para mejor" o "mejora continua". En la gestión del estudio, postula que avances pequeños y consistentes generan hábitos sostenibles a largo plazo.
* **Material Design 3 (M3):** Tercera generación del sistema de diseño visual de Google, que introduce esquemas de color tonal dinámicos, componentes de superficie redondeados y lineamientos avanzados de accesibilidad.
* **MoSCoW:** Técnica de priorización de requisitos que categoriza las funcionalidades en: *Must have* (Esencial), *Should have* (Importante), *Could have* (Deseable) y *Won't have* (Descartado para esta etapa).
* **MVP (Model-View-Presenter):** Patrón arquitectónico derivado de MVC donde la Vista es pasiva y delega toda la lógica de control y presentación a un Presentador intermediario mediante interfaces formales.
* **Offline-first:** Enfoque de diseño y arquitectura de software donde la aplicación opera con total funcionalidad y persiste datos en el almacenamiento local del dispositivo sin exigir conectividad continua a Internet.
* **POJO (Plain Old Java Object):** Objeto ordinario de Java libre de herencias o dependencias de frameworks externos (como clases del SDK de Android o anotaciones de Room), facilitando su portabilidad y testeo unitario.
* **Pomodoro:** Técnica de administración del tiempo creada por Francesco Cirillo que fragmenta las jornadas de trabajo en bloques de 25 minutos de concentración absoluta separados por pausas de descanso de 5 minutos.
* **Room Database:** Biblioteca oficial de persistencia de AndroidX que ofrece una capa de abstracción fluida sobre SQLite, proporcionando verificación estática de sentencias SQL en tiempo de compilación.
* **Shinrin-yoku:** Expresión japonesa traducida como "baño de bosque", que promueve la inmersión consciente en la naturaleza y el distanciamiento de pantallas digitales para reducir el agotamiento cognitivo y el estrés.
* **Single-Activity Architecture:** Patrón de diseño estructural en Android en el que una aplicación contiene una única `Activity` que actúa como marco anfitrión para múltiples pantallas gestionadas mediante `Fragments`.
* **SQLite:** Motor de base de datos relacional ligero, embebido y de código abierto que se ejecuta directamente dentro del espacio de memoria de la aplicación en dispositivos Android.
* **ThreadExecutor:** Componente utilitario de concurrencia que administra un grupo de hilos de ejecución en segundo plano para procesar tareas de I/O sin congelar la interfaz de usuario.
* **Time Blocking:** Metodología de gestión del tiempo que consiste en dividir la jornada en bloques temporales definidos dedicados a una sola tarea específica, inhibiendo la multitarea.
* **WCAG (Web Content Accessibility Guidelines):** Directrices y estándares internacionales de accesibilidad digital que garantizan que el contenido sea perceptible, operable, comprensible y robusto para todos los usuarios.
* **Zanshin:** Concepto marcial y filosófico japonés que denota un estado de alerta serena, atención plena y enfoque ininterrumpido durante la realización de una actividad.
* **5S (Seiri, Seiton, Seiso, Seiketsu, Shitsuke):** Metodología japonesa de gestión y orden del espacio de trabajo que promueve la clasificación, el orden, la limpieza, la estandarización y la disciplina sostenida.