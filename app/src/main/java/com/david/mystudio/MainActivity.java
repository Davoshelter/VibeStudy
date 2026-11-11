package com.david.mystudio;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.david.mystudio.ui.dashboard.DashboardFragment;
import com.david.mystudio.ui.enfoque.EnfoqueFragment;
import com.david.mystudio.ui.materias.MateriasFragment;
import com.david.mystudio.ui.metas.MetasFragment;
import com.david.mystudio.ui.reportes.ReportesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Actividad principal Shell que aloja la navegación inferior y los 5 fragmentos clave.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        navInferior = findViewById(R.id.navInferiorPrincipal);
        navInferior.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_inicio) {
                cambiarFragmento(new DashboardFragment());
                return true;
            } else if (itemId == R.id.menu_materias) {
                cambiarFragmento(new MateriasFragment());
                return true;
            } else if (itemId == R.id.menu_enfoque) {
                cambiarFragmento(new EnfoqueFragment());
                return true;
            } else if (itemId == R.id.menu_metas) {
                cambiarFragmento(new MetasFragment());
                return true;
            } else if (itemId == R.id.menu_reportes) {
                cambiarFragmento(new ReportesFragment());
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            cambiarFragmento(new DashboardFragment());
        }
    }

    public void seleccionarPestana(int menuId) {
        if (navInferior != null) {
            navInferior.setSelectedItemId(menuId);
        }
    }

    private void cambiarFragmento(Fragment fragmento) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fcvContenedorPrincipal, fragmento)
                .commit();
    }
}
