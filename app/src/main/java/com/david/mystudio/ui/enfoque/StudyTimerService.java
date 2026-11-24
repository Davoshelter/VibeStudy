package com.david.mystudio.ui.enfoque;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ServiceCompat;
import androidx.lifecycle.Observer;
import com.david.mystudio.MainActivity;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import java.util.Locale;

/** Mantiene la sesión en primer plano y publica el tiempo efectivo. */
public class StudyTimerService extends Service {

    public static final String ACTION_ABRIR_ENFOQUE = "com.david.mystudio.ABRIR_ENFOQUE";
    public static final String ACTION_ALTERNAR_PAUSA = "com.david.mystudio.ALTERNAR_PAUSA";
    private static final int SEGUNDOS_POMODORO = 25 * 60;
    private static final String CANAL = "canal_estudio_zanshin";
    private static final int NOTIFICACION_ID = 1001;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private SesionEnfoque enfoque;
    private NotificationManager notificaciones;
    private boolean enPrimerPlano;
    private final Observer<SesionEnfoque.Estado> estadoNotificacion = this::actualizarNotificacion;

    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            if (!enPrimerPlano || enfoque.getEstado() == SesionEnfoque.Estado.INACTIVA) return;
            enfoque.publicarTiempo();
            handler.postDelayed(this, 250);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        enfoque = ((StudyApplication) getApplication()).getAppContainer().getSesionEnfoque();
        notificaciones = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificaciones != null) {
            NotificationChannel canal = new NotificationChannel(CANAL,
                    getString(R.string.enfoque_alerta_sesion_activa_titulo), NotificationManager.IMPORTANCE_LOW);
            canal.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificaciones.createNotificationChannel(canal);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_ALTERNAR_PAUSA.equals(intent.getAction())) {
            if (enfoque != null && enfoque.puedeAlternarPausa()) {
                enfoque.alternarPausa(error -> Log.w("StudyTimerService", error));
            }
            return START_STICKY;
        }

        enfoque.prepararReinicioDelServicio();
        if (!enfoque.debeIniciarServicio()) {
            if (enPrimerPlano) return START_STICKY;
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        try {
            int tipo = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                    ? ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE : 0;
            ServiceCompat.startForeground(this, NOTIFICACION_ID,
                    construirNotificacion(0, SesionEnfoque.Estado.INICIANDO), tipo);
        } catch (IllegalArgumentException | IllegalStateException | SecurityException error) {
            Log.e("StudyTimerService", "No se pudo activar el servicio en primer plano", error);
            stopSelf(startId);
            enfoque.fallarInicioDelServicio();
            return START_NOT_STICKY;
        }
        enPrimerPlano = true;
        enfoque.observarEstado().observeForever(estadoNotificacion);
        enfoque.confirmarInicioDelServicio();
        handler.post(tick);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(tick);
        if (enPrimerPlano) {
            enPrimerPlano = false;
            enfoque.observarEstado().removeObserver(estadoNotificacion);
            stopForeground(STOP_FOREGROUND_REMOVE);
            enfoque.desconectarServicio();
        }
        super.onDestroy();
    }

    private void actualizarNotificacion(SesionEnfoque.Estado estado) {
        if (notificaciones != null && estado != SesionEnfoque.Estado.INACTIVA
                && estado != SesionEnfoque.Estado.RECUPERABLE) {
            notificaciones.notify(NOTIFICACION_ID, construirNotificacion(enfoque.getSegundos(), estado));
        }
    }

    private Notification construirNotificacion(int segundos, SesionEnfoque.Estado estado) {
        SesionEstudio sesion = enfoque.getSesion();
        boolean esPomodoro = sesion == null || sesion.getTecnica() == TipoTecnica.POMODORO;
        int segundosVisuales = esPomodoro ? Math.max(0, SEGUNDOS_POMODORO - segundos) : segundos;
        String tiempo = String.format(Locale.getDefault(), "%02d:%02d", segundosVisuales / 60, segundosVisuales % 60);

        Intent intent = new Intent(this, MainActivity.class)
                .setAction(ACTION_ABRIR_ENFOQUE)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent abrir = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent pausaIntent = new Intent(this, StudyTimerService.class)
                .setAction(ACTION_ALTERNAR_PAUSA);
        PendingIntent accionPausa = PendingIntent.getService(this, 1, pausaIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        int descripcion = R.string.enfoque_estado_activo;
        if (estado == SesionEnfoque.Estado.INICIANDO) descripcion = R.string.enfoque_estado_iniciando;
        if (estado == SesionEnfoque.Estado.PAUSADA) descripcion = R.string.enfoque_estado_pausado;
        if (estado == SesionEnfoque.Estado.FINALIZANDO) descripcion = R.string.enfoque_estado_guardando;
        boolean activa = estado == SesionEnfoque.Estado.ACTIVA;
        boolean pausada = estado == SesionEnfoque.Estado.PAUSADA;

        long tiempoReferencia;
        if (activa) {
            tiempoReferencia = esPomodoro
                    ? System.currentTimeMillis() + (segundosVisuales * 1000L)
                    : System.currentTimeMillis() - (segundos * 1000L);
        } else {
            tiempoReferencia = System.currentTimeMillis();
        }

        String tituloNotificacion = activa
                ? (esPomodoro ? getString(R.string.enfoque_notif_pomodoro) : getString(R.string.enfoque_notif_libre))
                : tiempo + " • " + getString(R.string.enfoque_estado_pausado);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL)
                .setContentTitle(tituloNotificacion)
                .setContentText(getString(descripcion))
                .setSmallIcon(R.drawable.ic_timer)
                .setContentIntent(abrir)
                .setOngoing(activa)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE);

        if (activa) {
            builder.setShowWhen(true)
                   .setUsesChronometer(true)
                   .setChronometerCountDown(esPomodoro)
                   .setWhen(tiempoReferencia);
        } else {
            builder.setShowWhen(false)
                   .setUsesChronometer(false);
        }

        if (activa || pausada) {
            int textoAccion = pausada ? R.string.enfoque_btn_reanudar : R.string.enfoque_btn_pausar;
            int iconoAccion = pausada ? R.drawable.ic_play_arrow : R.drawable.ic_pause;
            builder.addAction(iconoAccion, getString(textoAccion), accionPausa);
        }

        return builder.build();
    }
}
