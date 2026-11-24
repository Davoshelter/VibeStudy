package com.david.mystudio.ui.enfoque;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.david.mystudio.MainActivity;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.ui.enfoque.CronometroEstudio.PuntoControl;

/**
 * Resguarda la sesión al apagar el teléfono y notifica para recuperarla al reiniciar.
 */
public final class RecuperarCronometroReceiver extends BroadcastReceiver {

    private static final String CANAL_RECUPERACION = "canal_recuperacion_sesion";
    private static final int NOTIF_RECUPERAR_ID = 2002;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;
        String accion = intent.getAction();

        // 1. Apagado del teléfono: Pausar de emergencia para no perder el tiempo
        if (Intent.ACTION_SHUTDOWN.equals(accion)
                || "android.intent.action.QUICKBOOT_POWEROFF".equals(accion)
                || "com.htc.intent.action.QUICKBOOT_POWEROFF".equals(accion)) {
            try {
                StudyApplication app = (StudyApplication) context.getApplicationContext();
                SesionEnfoque enfoque = app.getAppContainer().getSesionEnfoque();
                if (enfoque.getEstado() == SesionEnfoque.Estado.ACTIVA) {
                    enfoque.alternarPausa(error -> Log.w("RecuperarCronometro", error));
                }
            } catch (Exception error) {
                Log.w("RecuperarCronometro", "Error al pausar sesión durante apagado", error);
            }
            return;
        }

        // 2. Encendido del teléfono: Verificar si había sesión previa y emitir notificación interactiva
        if (Intent.ACTION_BOOT_COMPLETED.equals(accion)) {
            RegistroCronometro registro = new RegistroCronometro(context);
            PuntoControl punto = registro.cargar();
            if (punto != null && punto.idSesion > 0) {
                mostrarNotificacionRecuperacion(context);
            }
        }
    }

    private void mostrarNotificacionRecuperacion(Context context) {
        NotificationManager notificaciones = context.getSystemService(NotificationManager.class);
        if (notificaciones == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_RECUPERACION,
                    context.getString(R.string.enfoque_notif_recuperar_titulo),
                    NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription(context.getString(R.string.enfoque_notif_recuperar_mensaje));
            notificaciones.createNotificationChannel(canal);
        }

        Intent intent = new Intent(context, MainActivity.class)
                .setAction(StudyTimerService.ACTION_ABRIR_ENFOQUE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent abrir = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CANAL_RECUPERACION)
                .setContentTitle(context.getString(R.string.enfoque_notif_recuperar_titulo))
                .setContentText(context.getString(R.string.enfoque_notif_recuperar_mensaje))
                .setSmallIcon(R.drawable.ic_timer)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(abrir);

        notificaciones.notify(NOTIF_RECUPERAR_ID, builder.build());
    }
}
