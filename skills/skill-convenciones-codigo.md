# Skill: Convenciones de Código y Buenas Prácticas Java (skill-convenciones-codigo)

## 1. Propósito
Garantizar la máxima legibilidad, mantenibilidad y uniformidad estilística en todo el código fuente Java del proyecto **VibeStudy**. El código está diseñado para un entorno académico universitario, por lo que debe ser autoexplicativo, limpio y fácilmente defendible ante un tribunal docente.

---

## 2. Idioma Oficial del Código
* **Idioma seleccionado:** **Español** para todas las entidades del dominio, lógica de negocio, nombres de variables, nombres de métodos, mensajes de log y comentarios.
* **Excepción técnica:** Se conservan en inglés únicamente las palabras reservadas del lenguaje Java (`class`, `interface`, `public`, `return`), patrones de diseño universales (`Presenter`, `Repository`, `Adapter`, `ViewHolder`, `Mapper`, `Factory`, `Callback`) y métodos del ciclo de vida del SDK de Android (`onCreate`, `onDestroy`, `onBindViewHolder`).

---

## 3. Reglas de Nombrado Estrictas

### 3.1. Clases y Tipos (`PascalCase`)
* Deben ser sustantivos claros y descriptivos.
* **Ejemplos correctos:** `Materia`, `SesionEstudioService`, `MetasPresenter`, `MateriaRoomRepository`.
* **Prohibido:** Abreviaturas confusas (`Mat`, `SesEst`, `MgrSesion`).

### 3.2. Interfaces (`PascalCase` sin prefijo "I")
* Las interfaces expresan un contrato o capacidad sin el prefijo húngaro `I`.
* **Correcto:** `MateriaRepository`, `DashboardContract.View`, `TemporizadorListener`.
* **INCORRECTO:** `IMateriaRepository`, `IDashboardView`.

### 3.3. Implementaciones de Interfaces (Sufijo Descriptivo)
* Toda clase que implemente un contrato debe indicar la tecnología o propósito en su sufijo:
  * Implementación Room: `MateriaRoomRepository` (no `MateriaRepoImpl` genérico).
  * Adaptadores: `MateriasAdapter`.
  * Presentadores: `MateriasPresenter`.

### 3.4. Métodos y Variables (`camelCase`)
* **Métodos:** Deben comenzar con un verbo en infinitivo que indique con precisión la acción.
  * **Correcto:** `calcularDuracionNeta()`, `obtenerMateriasActivas()`, `registrarNuevaSesion()`, `isActiva()`.
  * **INCORRECTO:** `calc()`, `hacer()`, `proceso()`, `datos()`.
* **Variables y Parámetros:** Nombres descriptivos y completos que expresen su contenido.
  * **Correcto:** `materiaSeleccionada`, `contadorDiasConsecutivos`, `duracionMinutosObjetivo`.
  * **TOTALMENTE PROHIBIDO:** Variables de una sola letra o genéricas (`x`, `y`, `i` excepto en bucles `for` simples, `tmp`, `temp`, `d`, `lista1`, `data`, `obj`, `aux`).

### 3.5. Constantes (`UPPER_SNAKE_CASE`) y Prohibición de Números Mágicos
* No debe existir ningún número literal en la lógica de negocio sin una constante que explique su significado.
* **Correcto:**
  ```java
  public static final int DURACION_MINIMA_SESION_MINUTOS = 1;
  public static final double META_HORAS_MINIMA = 1.0;
  public static final double META_HORAS_MAXIMA = 50.0;
  public static final int SEGUNDOS_POR_MINUTO = 60;
  public static final String COLOR_POR_DEFECTO_HEX = "#263238";
  ```
* **INCORRECTO:** Usar `if (horas > 50)` o `tiempo / 60` directamente en medio del código sin declarar la constante.

### 3.6. Métodos Cortos y Responsabilidad Única
* **Longitud:** Ningún método debe exceder las **25 líneas de código** efectivo.
* Si un método realiza validación, transformación y guardado, debe descomponerse en métodos auxiliares privados: `validarCampos()`, `crearEntidad()`, `notificarExito()`.

---

## 4. Ejemplos de Código

### 4.1. Ejemplo INCORRECTO (Violaciones de Nombrado, Magia y Monolito)
```java
// INCORRECTO: Múltiples violaciones de nomenclatura, idioma mezclado y números mágicos
package com.david.mystudio;

import java.util.List;

public class IMgrViolacion { // Prefijo "I" y abreviatura confusa
    public static final String C = "#123456"; // Constante no descriptiva

    public boolean proc(List<Object> lista1, int d, String s) { // Parámetros crípticos
        if (d <= 0 || d > 50) { // NÚMEROS MÁGICOS sin contexto
            return false;
        }

        int x = d * 60; // Variable "x" y número mágico 60
        Object tmp = null; // Variable "tmp"
        
        for (int i = 0; i < lista1.size(); i++) {
            // Método largo que hace cálculo, logging y formateo a la vez
            System.out.println("Item: " + lista1.get(i));
        }
        return true;
    }
}
```

---

### 4.2. Ejemplo CORRECTO (Convenciones Estrictas y Limpias)
```java
// CORRECTO: Autoexplicativo, constantes claras, métodos breves y en español
package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MetaSemanal;
import java.util.Objects;

public class MetaSemanalService {

    public static final double MINIMO_HORAS_OBJETIVO_SEMANAL = 1.0;
    public static final double MAXIMO_HORAS_OBJETIVO_SEMANAL = 50.0;
    public static final double PORCENTAJE_COMPLETO = 100.0;

    /**
     * Valida si la cantidad de horas objetivo de una meta está en el rango permitido (1 a 50 hrs).
     *
     * @param horasObjetivo Cantidad de horas asignadas a la meta semanal.
     * @return true si cumple con el rango válido de negocio.
     */
    public boolean validarRangoHorasObjetivo(double horasObjetivo) {
        return horasObjetivo >= MINIMO_HORAS_OBJETIVO_SEMANAL 
                && horasObjetivo <= MAXIMO_HORAS_OBJETIVO_SEMANAL;
    }

    /**
     * Calcula el porcentaje de avance de la meta sin superar el 100%.
     *
     * @param horasCumplidas Horas efectivas acumuladas.
     * @param horasObjetivo  Horas totales comprometidas.
     * @return Porcentaje entre 0.0 y 100.0.
     */
    public double calcularPorcentajeAvance(double horasCumplidas, double horasObjetivo) {
        if (horasObjetivo <= 0.0) {
            return 0.0;
        }
        double porcentajeCalculado = (horasCumplidas / horasObjetivo) * PORCENTAJE_COMPLETO;
        return Math.min(PORCENTAJE_COMPLETO, Math.max(0.0, porcentajeCalculado));
    }
}
```
