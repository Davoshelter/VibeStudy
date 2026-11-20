package com.david.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.david.mystudio.ui.dashboard.DashboardFragment;
import com.david.mystudio.ui.enfoque.EnfoqueFragment;
import com.david.mystudio.ui.enfoque.SesionEnfoque;
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
    private SesionEnfoque enfoque;
    private AlertDialog dialogoSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        navInferior = findViewById(R.id.navInferiorPrincipal);
        viewPager = findViewById(R.id.vpContenedorPrincipal);
        enfoque = ((StudyApplication) getApplication()).getAppContainer().getSesionEnfoque();

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
        viewPager.setUserInputEnabled(navegacionLibre(enfoque.getEstado()));
        enfoque.observarEstado().observe(this, estado -> {
            viewPager.setUserInputEnabled(navegacionLibre(estado));
            if ((estado == SesionEnfoque.Estado.ACTIVA || estado == SesionEnfoque.Estado.PAUSADA)
                    && viewPager.getCurrentItem() != obtenerPosicionPorMenuId(R.id.menu_enfoque)) {
                abrirPestana(R.id.menu_enfoque);
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navInferior.getMenu().findItem(obtenerMenuIdPorPosicion(position)).setChecked(true);
            }
        });

        navInferior.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (obtenerPosicionPorMenuId(itemId) == viewPager.getCurrentItem()) return true;
            if (itemId != R.id.menu_enfoque
                    && interceptarSalidaDeEnfoque(() -> abrirPestana(itemId), R.string.enfoque_alerta_detener_y_cambiar)) {
                return false;
            }
            abrirPestana(itemId);
            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (interceptarSalidaDeEnfoque(MainActivity.this::finish, R.string.enfoque_alerta_detener_y_salir)) return;
                if (viewPager.getCurrentItem() != 0) {
                    seleccionarPestana(R.id.menu_inicio);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
        if (StudyTimerService.ACTION_ABRIR_ENFOQUE.equals(getIntent().getAction())) {
            abrirPestana(R.id.menu_enfoque);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (enfoque.getEstado() == SesionEnfoque.Estado.RECUPERABLE) {
            abrirPestana(R.id.menu_enfoque);
        }
        enfoque.recuperar(mensaje -> {
            if (!isDestroyed()) Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (StudyTimerService.ACTION_ABRIR_ENFOQUE.equals(intent.getAction())) {
            abrirPestana(R.id.menu_enfoque);
        }
    }

    @Override
    protected void onDestroy() {
        if (dialogoSalida != null) dialogoSalida.dismiss();
        super.onDestroy();
    }

    private void abrirPestana(int menuId) {
        viewPager.setCurrentItem(obtenerPosicionPorMenuId(menuId), true);
        navInferior.getMenu().findItem(menuId).setChecked(true);
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

    private boolean interceptarSalidaDeEnfoque(Runnable alSalir, int etiquetaConfirmacion) {
        SesionEnfoque.Estado estado = enfoque.getEstado();
        if (navegacionLibre(estado)) return false;
        if (dialogoSalida != null || estado == SesionEnfoque.Estado.INICIANDO
                || estado == SesionEnfoque.Estado.FINALIZANDO) return true;

        dialogoSalida = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.enfoque_alerta_sesion_activa_titulo)
                .setMessage(R.string.enfoque_alerta_sesion_activa_mensaje)
                .setPositiveButton(etiquetaConfirmacion, (dialog, which) -> enfoque.finalizar(
                        minutos -> {
                            if (!isDestroyed() && !isFinishing()) alSalir.run();
                        },
                        mensaje -> {
                            if (!isDestroyed()) Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                        }))
                .setNegativeButton(R.string.enfoque_alerta_continuar_estudio, null)
                .create();
        dialogoSalida.setOnDismissListener(dialog -> dialogoSalida = null);
        dialogoSalida.show();
        return true;
    }

    private boolean navegacionLibre(SesionEnfoque.Estado estado) {
        return estado == SesionEnfoque.Estado.INACTIVA || estado == SesionEnfoque.Estado.RECUPERABLE;
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
