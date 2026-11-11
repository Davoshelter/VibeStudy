---
name: skill-corregir-layout-xml
description: Se activa al corregir, ajustar o modernizar layouts XML, gestión de WindowInsets (Edge-to-Edge), navegación táctil (BottomNavigationView, ViewPager2, swipe), contraste accesible en temas claro/oscuro de Material Design 3, y coherencia visual con ViewBinding y recursos del sistema.
---

# Skill: Corrección de Layouts XML, Navegación Táctil y Accesibilidad MD3

## 1. Objetivo
Asegurar que las vistas y layouts XML de Android:
- Se adapten correctamente a pantallas modernas con `WindowCompat.enableEdgeToEdge()` sin que la barra de navegación del sistema tape los controles táctiles (`BottomNavigationView`).
- Soporten gestos táctiles naturales como deslizamiento horizontal (`ViewPager2`) sincronizado con barras de navegación.
- Cumplan con las pautas de Material Design 3 y ratios de contraste WCAG tanto en tema claro (`values/colors.xml`) como en tema oscuro (`values-night/colors.xml`), evitando colores duros o quemados (`Color.WHITE`, `#000000`).
- Mantengan jerarquía visual clara y retroalimentación táctil adecuada.

---

## 2. Pasos Metodológicos de Trabajo

### Paso 1: Gestión de WindowInsets en Edge-to-Edge
1. Cuando la Activity use `WindowCompat.enableEdgeToEdge()`, nunca fijar el padding inferior en `0` ciegamente.
2. La barra de navegación inferior (`BottomNavigationView`) debe aplicar el inset `systemBars.bottom` como padding inferior o margen para que sus botones queden completamente por encima de los 3 botones del sistema o de la barra de gestos de Android.

### Paso 2: Navegación Táctil y Gestos (Swipe)
1. Si el usuario requiere alternar pantallas tanto por toques como por deslizamiento lateral (`swipe`), reemplazar o complementar contenedores estáticos con `androidx.viewpager2.widget.ViewPager2`.
2. Conectar el `ViewPager2` con el `BottomNavigationView` mediante callbacks sincronizados:
   - Al pulsar un ítem del menú: `viewPager.setCurrentItem(posicion, true)`.
   - Al deslizar la página (`onPageSelected`): `bottomNav.getMenu().getItem(posicion).setChecked(true)`.
3. Interceptar gestos de cambio de página cuando existan operaciones críticas en curso (ej. cronómetro activo) impidiendo el deslizamiento o mostrando la advertencia de confirmación antes de transicionar.

### Paso 3: Contraste y Adaptabilidad a Modo Oscuro
1. Prohibir el uso de colores quemados estáticos como `Color.WHITE` o `#FFFFFF` en fondos de chips, tarjetas o textos cuando el fondo circundante cambia entre claro y oscuro.
2. Utilizar atributos de tema o referencias semánticas a recursos:
   - `@color/color_surface` / `@color/color_surface_variant` para fondos de chips y tarjetas.
   - `@color/color_on_surface` / `@color/color_on_surface_variant` para textos principales y secundarios.
   - Variantes de contenedor (`color_success_container`, `color_error_container`) acompañadas de sus respectivos colores de contenido (`color_success`, `color_error`).

---

## 3. Checklist de Verificación Final
- [ ] ¿El `BottomNavigationView` es completamente cliqueable y sus botones no quedan tapados por la barra del sistema?
- [ ] ¿El deslizamiento táctil horizontal (`ViewPager2`) funciona de manera fluida y coherente con las pestañas?
- [ ] ¿Los chips y textos en Metas tienen contraste legible tanto en modo claro como en modo oscuro?
- [ ] ¿La compilación con Gradle es exitosa?
