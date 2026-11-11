# Skill: Diseño de Interfaces XML y Material Design 3 (skill-diseno-xml)

## 1. Propósito
Establecer las reglas de estandarización, internacionalización, coherencia visual y mantenibilidad para todos los archivos de interfaz XML en **VibeStudy**. El diseño debe reflejar con fidelidad el prototipo de alta fidelidad (`prototipo_HF.html`) respetando las directrices de **Material Design 3 (M3)**.

---

## 2. Reglas de Recursos y Centralización

### 2.1. Textos (`res/values/strings.xml`)
* **PROHIBIDO:** Textos literales ("hardcoded strings") directamente en los atributos `android:text`, `android:hint`, `app:title` o diálogos.
* **Obligatorio:** Todo texto debe residir en `strings.xml` con claves en minúsculas separadas por guion bajo:
  * Nombres de etiquetas: `lbl_nombre_materia`, `lbl_meta_global`.
  * Acciones: `btn_guardar_materia`, `btn_iniciar_enfoque`.
  * Mensajes de error/éxito: `msg_error_nombre_obligatorio`, `msg_exito_materia_guardada`.
  * Accesibilidad: `cd_boton_eliminar_materia`, `cd_icono_racha`.

### 2.2. Medidas y Espaciados (`res/values/dimens.xml`)
* **PROHIBIDO:** Valores numéricos en `dp` o `sp` escritos directamente en los layouts (salvo `0dp` en constraints de estiramiento).
* **Obligatorio:** Usar la cuadrícula estándar de 4dp / 8dp en `dimens.xml`:
  * Márgenes y paddings: `@dimen/spacing_micro` (4dp), `@dimen/spacing_small` (8dp), `@dimen/spacing_medium` (16dp), `@dimen/spacing_large` (24dp).
  * Radios de esquina: `@dimen/corner_radius_card` (12dp), `@dimen/corner_radius_button` (20dp).
  * Tamaños de texto: `@dimen/text_caption` (11sp), `@dimen/text_body` (13sp), `@dimen/text_title` (18sp), `@dimen/text_timer` (48sp).

### 2.3. Colores (`res/values/colors.xml`)
* Centralizar toda la paleta Material Design 3 y los colores temáticos de las asignaturas extraídos del prototipo:
  * Primario y Fondos: `color_primary` (`#1A73E8`), `color_surface`, `color_background`.
  * Colores de materias: `color_materia_moviles` (`#1E88E5`), `color_materia_so` (`#43A047`), `color_materia_redes` (`#FB8C00`), `color_materia_calculo` (`#8E24AA`).
  * Semánticos: `color_success` (`#2E7D32`), `color_error` (`#BA1A1A`), `color_zen_bg` (`#E8F5E9`).

---

## 3. Nomenclatura Estricta de Identificadores (`android:id`)

Todo elemento del layout debe tener un identificador con prefijo estandarizado en formato `camelCase` o `snake_case`, indicando el tipo de control:

| Componente | Prefijo | Ejemplo |
|---|---|---|
| `TextView` | `txt` | `@+id/txtNombreMateria`, `@+id/txtRachaDias` |
| `TextInputEditText` / `EditText` | `edt` | `@+id/edtNombreMateria`, `@+id/edtNotasSesion` |
| `TextInputLayout` | `til` | `@+id/tilNombreMateria` |
| `MaterialButton` / `Button` | `btn` | `@+id/btnGuardar`, `@+id/btnIniciarEnfoque` |
| `ImageButton` | `ibtn` | `@+id/ibtnCerrar` |
| `FloatingActionButton` | `fab` | `@+id/fabAgregarMateria` |
| `ImageView` | `img` | `@+id/imgIconoFuego`, `@+id/imgSwatchColor` |
| `RecyclerView` | `rv` | `@+id/rvMaterias`, `@+id/rvAgendaHoy` |
| `MaterialCardView` | `crd` | `@+id/crdMetaGlobal`, `@+id/crdMateriaItem` |
| `ProgressBar` / LinearProgress | `prg` | `@+id/prgMetaSemanal`, `@+id/prgCargando` |
| `Spinner` / `AutoCompleteTextView` | `spn` | `@+id/spnSeleccionarMateria` |
| `BottomNavigationView` | `nav` | `@+id/navInferiorPrincipal` |
| `MaterialToolbar` | `tb` | `@+id/tbPrincipal` |

---

## 4. Ejemplos de Layouts XML

### 4.1. Ejemplo INCORRECTO (Hardcoded, IDs Caóticos y Sin Recursos)
```xml
<!-- INCORRECTO: Strings hardcoded, dp hardcoded, ids sin prefijo y vistas desalineadas -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="13dp"> <!-- Medida arbitraria sin dimen -->

    <TextView
        android:id="@+id/titulo1" <!-- ID no descriptivo -->
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Aplicaciones Móviles I" <!-- VIOLACIÓN: Hardcoded string -->
        android:textColor="#1E88E5" <!-- VIOLACIÓN: Color hardcoded -->
        android:textSize="17sp" /> <!-- VIOLACIÓN: Tamaño hardcoded -->

    <Button
        android:id="@+id/button1" <!-- ID pésimo -->
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Guardar" /> <!-- VIOLACIÓN: Hardcoded string -->
</LinearLayout>
```

---

### 4.2. Ejemplo CORRECTO (Recursos Centralizados, IDs Claros, M3 Card)
Item de Materia (`res/layout/item_materia.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/crdMateriaItem"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="@dimen/spacing_medium"
    android:layout_marginVertical="@dimen/spacing_small"
    app:cardCornerRadius="@dimen/corner_radius_card"
    app:cardElevation="@dimen/elevation_card_default"
    app:strokeColor="@color/color_outline_variant"
    app:strokeWidth="@dimen/stroke_width_card">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/spacing_medium">

        <!-- Indicador de color distintivo de la materia -->
        <View
            android:id="@+id/viewSwatchColor"
            android:layout_width="@dimen/swatch_indicator_size"
            android:layout_height="@dimen/swatch_indicator_size"
            android:background="@drawable/shape_swatch_rounded"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:backgroundTint="@color/color_materia_moviles" />

        <!-- Nombre de la Materia -->
        <TextView
            android:id="@+id/txtNombreMateria"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/spacing_small"
            android:layout_marginEnd="@dimen/spacing_small"
            android:fontFamily="sans-serif-medium"
            android:textColor="@color/color_on_surface"
            android:textSize="@dimen/text_size_subtitle"
            app:layout_constraintEnd_toStartOf="@+id/btnEditarMateria"
            app:layout_constraintStart_toEndOf="@+id/viewSwatchColor"
            app:layout_constraintTop_toTopOf="parent"
            tools:text="Aplicaciones Móviles I" />

        <!-- Resumen de Sigla, Sesiones y Horas -->
        <TextView
            android:id="@+id/txtDetalleMateria"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/spacing_small"
            android:layout_marginTop="@dimen/spacing_micro"
            android:layout_marginEnd="@dimen/spacing_small"
            android:textColor="@color/color_on_surface_variant"
            android:textSize="@dimen/text_size_caption"
            app:layout_constraintEnd_toStartOf="@+id/btnEditarMateria"
            app:layout_constraintStart_toEndOf="@+id/viewSwatchColor"
            app:layout_constraintTop_toBottomOf="@+id/txtNombreMateria"
            tools:text="INF-312 • 5 sesiones • 7.5 hrs" />

        <!-- Botón Editar -->
        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnEditarMateria"
            style="@style/Widget.Material3.Button.OutlinedButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginEnd="@dimen/spacing_micro"
            android:text="@string/btn_editar"
            android:textSize="@dimen/text_size_caption"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/ibtnEliminarMateria"
            app:layout_constraintTop_toTopOf="parent" />

        <!-- Botón Eliminar -->
        <ImageButton
            android:id="@+id/ibtnEliminarMateria"
            android:layout_width="@dimen/btn_icon_size"
            android:layout_height="@dimen/btn_icon_size"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="@string/cd_eliminar_materia"
            android:src="@drawable/ic_delete_outline"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:tint="@color/color_error" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</com.google.android.material.card.MaterialCardView>
```
