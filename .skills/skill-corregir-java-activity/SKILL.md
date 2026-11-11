---
name: skill-corregir-java-activity
description: Se activa al corregir, refactorizar o implementar Activities, Fragments, Services y Presenters en Java para Android nativo, garantizando la preservación del ciclo de vida, prevención de fugas de memoria, sincronización con servicios en primer/segundo plano y separación estricta de la capa de presentación sin acoplar lógica de negocio.
---

# Skill: Corrección e Implementación de Activities, Fragments, Presenters y Services (MVP Android Nativo)

## 1. Objetivo
Guiar la corrección, refactorización e implementación de componentes de la capa de presentación (Activities, Fragments, Contracts, Presenters y Android Services) en Android nativo con Java 21 / Java 11+, asegurando:
- Gestión impecable del ciclo de vida de Fragments y Activities.
- Desvinculación de listeners en `onDestroyView()` y `onDestroy()` para evitar fugas de memoria (`Memory Leaks`).
- Prohibición estricta de retener instancias de `Context` o `Service` en variables estáticas.
- Coordinación asíncrona no bloqueante entre la UI, los contratos MVP y los servicios de dominio/Room sin condiciones de carrera.
- Intercepción de navegación tanto por UI (menús, barras de navegación) como por sistema operativo (`OnBackPressedDispatcher`).

---

## 2. Pasos Metodológicos de Trabajo

### Paso 1: Verificación de Contratos y Acoplamiento
1. Revisar la interfaz `Contract` correspondiente (e.g. `EnfoqueContract.View`, `EnfoqueContract.Presenter`).
2. Comprobar que la vista sea pasiva: no ejecuta lógica de negocio, no calcula tiempos, no evalúa reglas como `RN-SES-05`.
3. Todo evento de usuario en la vista debe delegarse al Presenter.
4. Todo cambio visual debe ser ordenado por el Presenter a través de los métodos del contrato.

### Paso 2: Ciclo de Vida y Gestión de Memoria
1. **Binding y Listeners en Fragments:**
   - Todo listener registrado a un Service de larga duración (`timerService.setListener(...)`) debe ser limpiado con `setListener(null)` en `onDestroyView()`.
   - `unbindService(serviceConnection)` debe ejecutarse de forma segura tras anular el listener.
   - En `onDestroyView()`, invocar siempre `presenter.desvincularVista()` para que el Presenter no mantenga referencias a vistas destruidas.
2. **Servicios Android (`Service`):**
   - Nunca guardar `private static MyService instanciaServicio`. Si se necesita ordenar al servicio que se detenga desde fuera, enviar un `Intent` con una acción (ej. `ACTION_DETENER`) mediante `context.startService(intent)`.
   - Mantener estado global solo en primitivos thread-safe o banderas estáticas desacopladas del ciclo de vida del Context.

### Paso 3: Concurrencia y Sincronización UI vs. Base de Datos
1. Nunca arrancar un proceso visual o temporizador de forma optimista si la persistencia subyacente puede fallar o rechazar la acción.
2. El arranque del cronómetro debe ocurrir únicamente en el callback `onSuccess` del Presenter tras la confirmación del guardado en Room.
3. Evitar consultas duplicadas a Room: si una pantalla ya refresca datos en `onServiceConnected()` o `onResume()`, no volver a disparar la misma consulta al poblar vistas hijas o adaptadores.

### Paso 4: Navegación Segura e Intercepción de `Atrás`
1. Toda Activity con operaciones activas en progreso (como una sesión de estudio) debe registrar un `OnBackPressedCallback` mediante `getOnBackPressedDispatcher().addCallback(this, callback)`.
2. Si hay una operación activa, mostrar el diálogo de confirmación antes de permitir el retroceso o salida de la actividad.

---

## 3. Errores Comunes Detectados en este Proyecto

1. **Inversión booleana en alternar estado:** Pasar negado el parámetro booleano recibido (`!estaPausado` en vez de `estaPausado`), provocando que la UI indique un estado opuesto al cronómetro.
2. **Memory Leak por Service Listener:** Registrar `EnfoqueFragment.this` en un Foreground Service y no limpiar `listener = null` al destruirse la vista del Fragment en `onDestroyView()`.
3. **Condición de carrera al iniciar sesión:** Invocar `timerService.iniciar(0)` inmediatamente al pulsar el botón sin esperar que el Presenter confirme el alta de la sesión en Room.
4. **Violación de reglas de negocio (`RN-SES-05`):** Forzar `Math.max(1, segundos / 60)` en el Presenter ignorando el método del servicio de dominio que cancela sesiones menores a 1 minuto.
5. **Sobrescritura de estado en restauración:** Forzar `vista.actualizarEstadoSesion(true, false)` asumiendo que nunca está pausada al reanudar la app.
6. **Referencia estática a Context/Service:** Guardar `static StudyTimerService instanciaServicio` para poder llamar `instanciaServicio.detener()`.
7. **Evasión del diálogo de advertencia con botón Atrás:** Interceptar únicamente los clics del `BottomNavigationView` pero no el botón físico o gesto Atrás del sistema.
8. **Consultas redundantemente disparadas:** Disparar `restaurarSesionActivaSiExiste()` en `mostrarMaterias`, en `onResume` y en `onServiceConnected` simultáneamente.

---

## 4. Checklist de Verificación Final

- [ ] ¿El archivo modificado compila limpiamente sin errores de sintaxis ni warnings severos?
- [ ] ¿Todos los métodos del `Contract` están implementados en la Vista y en el Presenter?
- [ ] ¿En `onDestroyView()` se anulan todos los listeners (`setListener(null)`) y se desvincula el Presenter?
- [ ] ¿Se eliminaron todas las referencias estáticas a instancias de `Context` o `Service`?
- [ ] ¿El temporizador visual arranca exclusivamente cuando Room confirma la creación exitosa de la sesión?
- [ ] ¿Se respetan las reglas de negocio de dominio delegando en `SesionEstudioService` sin forzar minutos artificiales?
- [ ] ¿El `OnBackPressedDispatcher` intercepta correctamente cuando hay una sesión activa?
- [ ] ¿Se eliminaron llamadas redundantes a métodos de I/O en callbacks de UI?
- [ ] ¿Se ejecutó `./gradlew compileDebugJavaWithJavac` y `./gradlew test` con éxito?
