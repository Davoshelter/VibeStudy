package com.david.mystudio;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.david.mystudio.domain.service.SesionEstudioService;
import com.david.mystudio.ui.dashboard.DashboardFragment;
import com.david.mystudio.ui.enfoque.EnfoqueFragment;
import com.david.mystudio.ui.enfoque.StudyTimerService;
import com.david.mystudio.ui.materias.MateriasFragment;
import com.david.mystudio.ui.metas.MetasFragment;
import com.david.mystudio.ui.reportes.ReportesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Actividad principal Shell que aloja la navegación inferior y los 5 fragmentos clave
 * con soporte para gestos táctiles (Swipe con ViewPager2) y Edge-to-Edge accesible.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navInferior;
    private ViewPager2 viewPager;
    private int pestanaActualId = R.id.menu_inicio;
    private boolean navegandoProgramaticamente = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        navInferior = findViewById(R.id.navInferiorPrincipal);
        viewPager = findViewById(R.id.vpContenedorPrincipal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            if (navInferior != null) {
                navInferior.setPadding(0, 0, 0, systemBars.bottom);
            }
            return insets;
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        viewPager.setAdapter(new MainPagerAdapter(this));
        viewPager.setOffscreenPageLimit(1);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int targetMenuId = obtenerMenuIdPorPosicion(position);
                pestanaActualId = targetMenuId;
                if (!navegandoProgramaticamente && navInferior != null) {
                    navInferior.getMenu().getItem(position).setChecked(true);
                }
            }
        });

        navInferior.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == pestanaActualId) {
                return true;
            }

            if (StudyTimerService.isSesionEnCurso() && itemId != R.id.menu_enfoque) {
                mostrarDialogoAdvertenciaSesion(itemId);
                return false;
            }

            pestanaActualId = itemId;
            int posicion = obtenerPosicionPorMenuId(itemId);
            navegandoProgramaticamente = true;
            viewPager.setCurrentItem(posicion, true);
            navegandoProgramaticamente = false;
            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (StudyTimerService.isSesionEnCurso()) {
                    mostrarDialogoAdvertenciaAtras(this);
                } else if (viewPager.getCurrentItem() != 0) {
                    seleccionarPestana(R.id.menu_inicio);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarBloqueoSwipeSegunSesion();
    }

    public void actualizarBloqueoSwipeSegunSesion() {
        if (viewPager != null) {
            // Si hay sesión activa en Enfoque, deshabilitamos el swipe accidental;
            // de lo contrario permitimos navegación táctil por deslizamiento fluido.
            viewPager.setUserInputEnabled(!StudyTimerService.isSesionEnCurso());
        }
    }

    public void seleccionarPestana(int menuId) {
        if (navInferior != null) {
            navInferior.setSelectedItemId(menuId);
        }
    }

    private int obtenerPosicionPorMenuId(int menuId) {
        if (menuId == R.id.menu_inicio) return 0;
        if (menuId == R.id.menu_materias) return 1;
        if (menuId == R.id.menu_enfoque) return 2;
        if (menuId == R.id.menu_metas) return 3;
        if (menuId == R.id.menu_reportes) return 4;
        return 0;
    }

    private int obtenerMenuIdPorPosicion(int position) {
        switch (position) {
            case 0: return R.id.menu_inicio;
            case 1: return R.id.menu_materias;
            case 2: return R.id.menu_enfoque;
            case 3: return R.id.menu_metas;
            case 4: return R.id.menu_reportes;
            default: return R.id.menu_inicio;
        }
    }

    private void mostrarDialogoAdvertenciaSesion(int targetItemId) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.enfoque_alerta_sesion_activa_titulo)
                .setMessage(R.string.enfoque_alerta_sesion_activa_mensaje)
                .setPositiveButton(R.string.enfoque_alerta_detener_y_cambiar, (dialog, which) -> {
                    detenerYFinalizarSesionActiva(() -> {
                        int pos = obtenerPosicionPorMenuId(targetItemId);
                        pestanaActualId = targetItemId;
                        navegandoProgramaticamente = true;
                        viewPager.setCurrentItem(pos, true);
                        navInferior.setSelectedItemId(targetItemId);
                        navegandoProgramaticamente = false;
                        actualizarBloqueoSwipeSegunSesion();
                    });
                })
                .setNegativeButton(R.string.enfoque_alerta_continuar_estudio, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void mostrarDialogoAdvertenciaAtras(OnBackPressedCallback callback) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.enfoque_alerta_sesion_activa_titulo)
                .setMessage(R.string.enfoque_alerta_sesion_activa_mensaje)
                .setPositiveButton(R.string.enfoque_alerta_detener_y_cambiar, (dialog, which) -> {
                    detenerYFinalizarSesionActiva(() -> {
                        actualizarBloqueoSwipeSegunSesion();
                        callback.setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    });
                })
                .setNegativeButton(R.string.enfoque_alerta_continuar_estudio, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void detenerYFinalizarSesionActiva(Runnable onCompletado) {
        int segundos = StudyTimerService.getUltimosSegundos();
        StudyTimerService.detenerServicio(this);

        StudyApplication app = (StudyApplication) getApplication();
        app.getAppContainer().getSesionEstudioService().finalizarOCancelarSesionActiva(
                segundos,
                "Sesión finalizada al cambiar de apartado",
                new SesionEstudioService.ResultadoCallback<Void>() {
                    @Override
                    public void onSuccess(Void resultado) {
                        if (onCompletado != null) {
                            runOnUiThread(onCompletado);
                        }
                    }

                    @Override
                    public void onError(String mensajeError) {
                        if (onCompletado != null) {
                            runOnUiThread(onCompletado);
                        }
                    }
                }
        );
    }

    private static class MainPagerAdapter extends FragmentStateAdapter {
        public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: return new DashboardFragment();
                case 1: return new MateriasFragment();
                case 2: return new EnfoqueFragment();
                case 3: return new MetasFragment();
                case 4: return new ReportesFragment();
                default: return new DashboardFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
