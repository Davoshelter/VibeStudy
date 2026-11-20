package com.david.mystudio.ui.enfoque;

import android.content.Context;
import android.content.SharedPreferences;
import com.david.mystudio.ui.enfoque.CronometroEstudio.PuntoControl;

/** Guarda el reloj y el borrador de la sesión local. */
final class RegistroCronometro {

    private final SharedPreferences preferencias;

    RegistroCronometro(Context contexto) {
        preferencias = contexto.getSharedPreferences("cronometro_enfoque", Context.MODE_PRIVATE);
    }

    PuntoControl cargar() {
        int id = preferencias.getInt("id", 0);
        if (id <= 0) return null;
        return new PuntoControl(id, preferencias.getLong("tiempo", 0),
                preferencias.getLong("monotonico", 0), preferencias.getLong("civil", 0),
                preferencias.getInt("arranque", -1), preferencias.getBoolean("activo", false));
    }

    boolean guardar(PuntoControl punto, String notas) {
        PuntoControl anterior = cargar();
        String notasAnteriores = cargarNotas();
        if (editar(punto, notas).commit()) return true;
        if (anterior == null) {
            limpiar();
        } else {
            editar(anterior, notasAnteriores).apply();
        }
        return false;
    }

    private SharedPreferences.Editor editar(PuntoControl punto, String notas) {
        return preferencias.edit()
                .putInt("id", punto.idSesion)
                .putLong("tiempo", punto.tiempoMs)
                .putLong("monotonico", punto.relojMonotonicoMs)
                .putLong("civil", punto.relojCivilMs)
                .putInt("arranque", punto.arranque)
                .putBoolean("activo", punto.activo)
                .putString("notas", notas);
    }

    String cargarNotas() {
        return preferencias.getString("notas", "");
    }

    void guardarNotas(String notas) {
        preferencias.edit().putString("notas", notas).apply();
    }

    void limpiar() {
        preferencias.edit().clear().apply();
    }
}
