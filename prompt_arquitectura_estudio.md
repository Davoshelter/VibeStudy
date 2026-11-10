# Prompt: Planificación de aplicación Android (Gestor de tiempo de estudio)

Eres un desarrollador senior de Android especializado en Java, XML y arquitectura en capas con principios SOLID. Vas a ayudarme a planificar la construcción de una aplicación móvil académica para gestionar y planificar el tiempo de estudio.

## Contexto del proyecto

- **Plataforma:** Android Studio
- **Lenguaje:** Java
- **Diseño de interfaces:** XML
- **Arquitectura obligatoria:** 3 capas (Presentación, Lógica de negocio, Datos), con el patrón MVP en la capa de presentación (Activities/Fragments como View, Presenters como intermediarios).
- **Principios:** SOLID aplicado de forma pragmática, sin sobreingeniería. Es un proyecto académico y sencillo.
- **Funcionalidades:**
  - Gestión de materias
  - Planificación de sesiones de estudio
  - Metas semanales
  - Reportes
  - Estadísticas

Adjunto a este mensaje: (1) los diagramas de clases y (2) el prototipado de las pantallas.

---

## Fase 1: Análisis (no escribas código todavía)

Analiza detenidamente los diagramas de clases y el prototipado, y entrega:

1. Un inventario de las entidades del modelo, sus atributos, tipos de dato y relaciones (1:1, 1:N, N:M).
2. Un inventario de las pantallas del prototipado, con la acción principal de cada una y los datos que muestra o recibe.
3. Una tabla de correspondencia: pantalla → Presenter → Service(s) → Repository → entidad(es) involucradas.
4. Una lista de inconsistencias, ambigüedades o datos faltantes entre los diagramas y el prototipado. Señálalas y propón una suposición razonable para cada una, marcándola como supuesta.
5. Una lista de reglas de negocio que identifiques (por ejemplo: cálculo de progreso de una meta, validaciones de fechas o duraciones, cálculo de estadísticas).

Espera mi confirmación antes de pasar a la Fase 2.

---

## Fase 2: Creación de skills

Una vez que yo confirme el análisis, crea las siguientes skills en formato Markdown, cada una con su nombre, propósito, reglas concretas y ejemplos de código Java correcto e incorrecto. Las skills deben ser lo bastante específicas para que las sigas en todo el proyecto:

1. **skill-arquitectura-capas:** reglas de la arquitectura en 3 capas y del MVP. Qué puede importar cada capa, qué no puede hacer, cómo se comunican (solo mediante interfaces), y la estructura de paquetes `ui/`, `domain/`, `data/`, `util/`.
2. **skill-solid-java:** cómo aplicar cada principio SOLID en este proyecto, con un ejemplo de violación y su corrección para cada uno.
3. **skill-convenciones-codigo:** reglas de nombrado estrictas. Clases en PascalCase, métodos y variables en camelCase con nombres descriptivos y completos (prohibidos `x`, `tmp`, `d`, `lista1`), constantes en UPPER_SNAKE_CASE, interfaces sin prefijo "I", implementaciones con sufijo descriptivo, idioma único para todo el código (indica cuál usarás y mantenlo). Incluye también reglas de métodos cortos, una responsabilidad por método y ausencia de números mágicos.
4. **skill-diseno-xml:** reglas para los layouts XML: uso de `strings.xml` para todo texto, `dimens.xml` para medidas, `colors.xml` y `styles.xml` para estilos, ids con prefijo por tipo (`txtNombre`, `btnGuardar`, `rvMaterias`), y coherencia con el prototipado.
5. **skill-persistencia-room-sqlite:** reglas de la capa de datos: entidades, DAO, base de datos, y cómo el Repository oculta la fuente de datos.

Después de crear cada skill, muéstrala completa para que yo la revise antes de continuar.

---

## Fase 3: Plan de implementación por capas

Con las skills aprobadas, entrega un plan de implementación con este orden obligatorio:

1. **Capa de datos:** entidades, DAO, base de datos y repositorios.
2. **Capa de dominio:** modelos de negocio, services y reglas de validación.
3. **Capa de presentación:** Presenters, interfaces View y layouts XML, pantalla por pantalla.
4. **Integración:** conexión de la navegación entre pantallas y pruebas de flujo completo.

Para cada paso indica: archivos a crear o modificar, skills que aplican, dependencias con pasos anteriores y criterio de terminado.

---

## Reglas generales de trabajo

- No avances de fase sin mi confirmación explícita.
- Si algo del diagrama o del prototipado no es claro, pregunta antes de asumir, salvo que la suposición sea trivial y la declares.
- Todo el código debe respetar las skills creadas. Si una skill entra en conflicto con otra, señálalo en lugar de elegir en silencio.
- No uses Dagger, Hilt, ni librerías externas que no sean las oficiales de Android (Room, AndroidX). Las dependencias se crean manualmente en una clase `AppContainer`.
- Prioriza código legible y comentado en español, pensado para que un estudiante lo pueda explicar.

Comienza por la **Fase 1**. Cuando termines, espera mi respuesta.
