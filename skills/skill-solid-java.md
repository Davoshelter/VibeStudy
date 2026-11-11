# Skill: Principios SOLID en Java para Android (skill-solid-java)

## 1. Propósito
Establecer las directrices de diseño orientado a objetos para el proyecto **VibeStudy**, aplicando los 5 principios **SOLID** de manera pragmática y adecuada para un proyecto académico en Android Studio con Java. Cada principio cuenta con su justificación, una violación habitual en proyectos Android y su corrección arquitectónica.

---

## 2. Los 5 Principios SOLID Aplicados a VibeStudy

### 2.1. S - Principio de Responsabilidad Única (Single Responsibility Principle - SRP)
> *"Una clase debe tener una, y solo una, razón para cambiar."*

#### Aplicación en VibeStudy
- Las clases `Activity` o `Fragment` sólo son responsables del ciclo de vida y renderizado en pantalla.
- Los `Presenter` sólo coordinan el flujo entre la vista y el servicio.
- Los `Service` contienen exclusivamente las reglas de negocio y validaciones.
- Los `Repository` se limitan a orquestar el acceso a los datos.
- Las entidades de Room `@Entity` y los modelos `@Dao` solo se encargan del esquema de persistencia.

#### Violación Habitual (God Activity)
```java
// INCORRECTO: SesionEstudioActivity maneja UI, cronómetro, cálculo de racha y SQLite
public class SesionEstudioActivityViolacion extends AppCompatActivity {
    private TextView txtCronometro;
    private SQLiteDatabase baseDatos;

    public void terminarSesion() {
        // 1. Maneja UI
        txtCronometro.setText("00:00");
        // 2. Calcula persistencia directa
        baseDatos.execSQL("INSERT INTO sesiones ...");
        // 3. Regla de negocio de racha mezclada
        int racha = calcularRacha();
        // 4. Muestra notificación
        mostrarNotificacion("Racha aumentada a " + racha);
    }
}
```

#### Corrección con SRP
```java
// CORRECTO: Responsabilidades separadas en componentes especializados

// 1. Servicio de dominio con la regla de negocio de la racha y finalización
public class SesionEstudioService {
    private final SesionEstudioRepository sesionRepository;
    private final RachaService rachaService;

    public SesionEstudioService(SesionEstudioRepository sesionRepo, RachaService rachaServ) {
        this.sesionRepository = sesionRepo;
        this.rachaService = rachaServ;
    }

    public void finalizarSesion(SesionEstudio sesion, String notas) {
        sesion.finalizar(notas);
        sesionRepository.actualizar(sesion);
        rachaService.evaluarRachaTrasSesion(sesion.getFechaHoraFin());
    }
}

// 2. Presenter que solo reacciona a la acción del usuario
public class EnfoquePresenter implements EnfoqueContract.Presenter {
    private final EnfoqueContract.View vista;
    private final SesionEstudioService sesionService;

    public EnfoquePresenter(EnfoqueContract.View vista, SesionEstudioService sesionService) {
        this.vista = vista;
        this.sesionService = sesionService;
    }

    @Override
    public void alPulsarFinalizarEstudio(SesionEstudio sesion, String notas) {
        sesionService.finalizarSesion(sesion, notas);
        vista.mostrarResumenSesionCompletada(sesion);
    }
}
```

---

### 2.2. O - Principio de Abierto/Cerrado (Open/Closed Principle - OCP)
> *"Las entidades de software deben estar abiertas a la extensión, pero cerradas a la modificación."*

#### Aplicación en VibeStudy
El motor de temporización y cálculo de progreso puede admitir diferentes técnicas de estudio (`Pomodoro`, `Bloque Libre`, o en el futuro `52/17` o `Regla de los 90 min`) sin modificar el código interno del temporizador ni llenar el código de sentencias `switch/case` frágiles.

#### Violación Habitual
```java
// INCORRECTO: Cada nueva técnica de estudio obliga a modificar el método con nuevos 'else if'
public class TemporizadorViolacion {
    public int calcularDuracionEfectiva(String tipoTecnica, int tiempoActualSegundos) {
        if (tipoTecnica.equals("POMODORO")) {
            return Math.min(tiempoActualSegundos, 1500); // 25 min
        } else if (tipoTecnica.equals("BLOQUE_LIBRE")) {
            return tiempoActualSegundos;
        } else if (tipoTecnica.equals("REGLA_52_17")) { // Modificación del código original
            return Math.min(tiempoActualSegundos, 3120);
        }
        return 0;
    }
}
```

#### Corrección con OCP
```java
// CORRECTO: Abierto a extensión mediante polimorfismo, cerrado a modificación

public interface EstrategiaTecnicaEstudio {
    int calcularMinutosEfectivos(int segundosTranscurridos);
    boolean debeEmitirAlarmaPausa(int segundosTranscurridos);
}

public class TecnicaPomodoro implements EstrategiaTecnicaEstudio {
    private static final int DURACION_BLOQUE_SEGUNDOS = 25 * 60;

    @Override
    public int calcularMinutosEfectivos(int segundosTranscurridos) {
        return Math.min(segundosTranscurridos, DURACION_BLOQUE_SEGUNDOS) / 60;
    }

    @Override
    public boolean debeEmitirAlarmaPausa(int segundosTranscurridos) {
        return segundosTranscurridos >= DURACION_BLOQUE_SEGUNDOS;
    }
}

public class TecnicaBloqueLibre implements EstrategiaTecnicaEstudio {
    @Override
    public int calcularMinutosEfectivos(int segundosTranscurridos) {
        return segundosTranscurridos / 60;
    }

    @Override
    public boolean debeEmitirAlarmaPausa(int segundosTranscurridos) {
        return false; // El bloque libre no tiene pausa prefijada
    }
}
```

---

### 2.3. L - Principio de Sustitución de Liskov (Liskov Substitution Principle - LSP)
> *"Los subtipos deben ser sustituibles por sus tipos base sin alterar la corrección del programa."*

#### Aplicación en VibeStudy
Si se extiende una clase base o se implementa una interfaz de repositorio o servicio, la implementación no debe lanzar excepciones inesperadas como `UnsupportedOperationException`, ni anular comportamientos esenciales esperados por el cliente.

#### Violación Habitual
```java
// INCORRECTO: Una implementación de repositorio de solo lectura lanza excepciones inesperadas
public interface Repositorio<T> {
    List<T> obtenerTodos();
    void guardar(T elemento);
    void eliminar(int id);
}

public class ReporteSoloLecturaRepositoryViolacion implements Repositorio<ReporteEstudio> {
    @Override
    public List<ReporteEstudio> obtenerTodos() {
        return new ArrayList<>();
    }

    @Override
    public void guardar(ReporteEstudio elemento) {
        // VIOLACIÓN LSP: Rompe el contrato esperado por el llamador
        throw new UnsupportedOperationException("No se permite guardar reportes directamente");
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("No se permite eliminar");
    }
}
```

#### Corrección con LSP
```java
// CORRECTO: Se definen contratos de interfaz coherentes que cada subtipo puede honrar al 100%

public interface LectorRepositorio<T> {
    List<T> obtenerTodos();
    T buscarPorId(int id);
}

public interface EscritorRepositorio<T> {
    void guardar(T elemento);
    void eliminar(int id);
}

// Un repositorio de solo lectura solo implementa lo que verdaderamente puede cumplir
public class ReporteEstudioRepositoryImpl implements LectorRepositorio<ReporteEstudio> {
    @Override
    public List<ReporteEstudio> obtenerTodos() {
        // Retorna datos de agregación reales sin violar el contrato
        return obtenerReportesAgregados();
    }

    @Override
    public ReporteEstudio buscarPorId(int id) {
        return null;
    }
}
```

---

### 2.4. I - Principio de Segregación de Interfaces (Interface Segregation Principle - ISP)
> *"Los clientes no deben verse obligados a depender de interfaces que no utilizan."*

#### Aplicación en VibeStudy
En el patrón MVP, no debemos crear una súper-interfaz `BaseView` o `BasePresenter` con 20 métodos (como `mostrarGrafico()`, `actualizarCronometro()`, `mostrarDialogoColor()`) que obligue a pantallas simples como "Materias" a dejar métodos vacíos.

#### Violación Habitual
```java
// INCORRECTO: Interfaz monolítica que obliga a implementar métodos no utilizados
public interface VistaGeneralAplicacionViolacion {
    void mostrarMaterias(List<Materia> materias);
    void actualizarSegundosCronometro(int segundos);
    void mostrarGraficoTorta(Map<String, Double> datos);
    void mostrarMensajeMetaCumplida();
}

public class MateriasFragmentViolacion implements VistaGeneralAplicacionViolacion {
    @Override
    public void mostrarMaterias(List<Materia> materias) {
        // Usado
    }

    @Override
    public void actualizarSegundosCronometro(int segundos) {
        // VIOLACIÓN ISP: Método forzado sin sentido en la pantalla de materias
    }

    @Override
    public void mostrarGraficoTorta(Map<String, Double> datos) {
        // VIOLACIÓN ISP: Método irrelevante aquí
    }

    @Override
    public void mostrarMensajeMetaCumplida() {
        // VIOLACIÓN ISP
    }
}
```

#### Corrección con ISP
```java
// CORRECTO: Contratos segregados específicos para cada pantalla

public interface MateriasContract {
    interface View {
        void mostrarMaterias(List<Materia> materias);
        void mostrarError(String mensaje);
    }
}

public interface EnfoqueContract {
    interface View {
        void actualizarSegundosCronometro(int segundos);
        void cambiarEstadoBotonPausa(boolean estaPausado);
    }
}

public interface ReportesContract {
    interface View {
        void mostrarDistribucionMaterias(Map<String, Double> porcentajes);
        void mostrarHorasTotales(double horas);
    }
}
```

---

### 2.5. D - Principio de Inversión de Dependencias (Dependency Inversion Principle - DIP)
> *"Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones. Las abstracciones no deben depender de los detalles; los detalles deben depender de las abstracciones."*

#### Aplicación en VibeStudy
- La capa de dominio (`MateriaService`) no debe depender de Room (`MateriaDao` o `MateriaRoomRepository`).
- La capa de dominio define la interfaz del repositorio (`MateriaRepository`).
- La capa de datos (`data/`) implementa dicha interfaz (`MateriaRoomRepository`).
- La inyección se realiza por constructor mediante `AppContainer`.

#### Violación Habitual
```java
// INCORRECTO: Módulo de alto nivel (Service) depende directamente de un detalle de bajo nivel (Room SQLite)
package com.david.mystudio.domain.service;

import com.david.mystudio.data.local.AppDatabase;
import com.david.mystudio.data.local.dao.MateriaDao; // VIOLACIÓN: Dominio acoplado a Room

public class MateriaServiceViolacion {
    private MateriaDao materiaDao;

    public MateriaServiceViolacion() {
        // Alto nivel acoplado directamente al detalle técnico
        this.materiaDao = AppDatabase.getInstance(null).materiaDao();
    }
}
```

#### Corrección con DIP
```java
// CORRECTO: 
// 1. Abstracción definida en la capa de DOMINIO (alto nivel)
package com.david.mystudio.domain.repository;

import com.david.mystudio.domain.model.Materia;
import java.util.List;

public interface MateriaRepository {
    void guardar(Materia materia, Callback<Long> callback);
    void obtenerTodasActivas(Callback<List<Materia>> callback);
    void buscarPorNombre(String nombre, Callback<Materia> callback);
    void darDeBajaLogica(int idMateria, Callback<Void> callback);

    interface Callback<T> {
        void onResult(T resultado);
    }
}

// 2. El Servicio de dominio depende ÚNICAMENTE de la abstracción
package com.david.mystudio.domain.service;

import com.david.mystudio.domain.repository.MateriaRepository;

public class MateriaService {
    private final MateriaRepository materiaRepository; // Dependencia de interfaz

    // Inyección por constructor
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }
}

// 3. El detalle de implementación (bajo nivel) reside en la capa de DATOS
package com.david.mystudio.data.repository;

import com.david.mystudio.domain.repository.MateriaRepository;
import com.david.mystudio.data.local.dao.MateriaDao;

public class MateriaRoomRepository implements MateriaRepository {
    private final MateriaDao materiaDao;

    public MateriaRoomRepository(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }
    // Implementación concreta con Room...
}
```
