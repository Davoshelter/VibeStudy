package com.david.mystudio.ui.enfoque;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ServiceCompat;
import com.david.mystudio.MainActivity;
import com.david.mystudio.R;

/**
 * Servicio en primer plano (Foreground Service) que gestiona el cronómetro
 * persistente de la sesión de estudio aún cuando la aplicación pasa a segundo plano.
 */
public class StudyTimerService extends Service {

    public static final String CANAL_NOTIFICACION_ID = "canal_estudio_zanshin";
    public static final String ACTION_DETENER = "com.david.mystudio.ACTION_DETENER_TIMER";
    private static final int NOTIFICACION_ID = 1001;

    private static boolean servicioCorriendo = false;
    private static boolean servicioPausado = false;
    private static int ultimosSegundos = 0;

    public static boolean isSesionEnCurso() {
        return servicioCorriendo;
    }

    public static boolean isSesionPausada() {
        return servicioPausado;
    }

    public static int getUltimosSegundos() {
        return ultimosSegundos;
    }

    public static void detenerServicio(android.content.Context context) {
        servicioCorriendo = false;
        servicioPausado = false;
        if (context != null) {
            Intent intent = new Intent(context, StudyTimerService.class);
            intent.setAction(ACTION_DETENER);
            context.startService(intent);
        }
    }

    public interface OnTimerTickListener {
        void onTick(int segundosTranscurridos, boolean estaPausado);
    }

    private final IBinder binder = new LocalBinder();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int segundosTranscurridos = 0;
    private boolean estaPausado = false;
    private boolean estaCorriendo = false;
    private OnTimerTickListener listener;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (estaCorriendo && !estaPausado) {
                segundosTranscurridos++;
                ultimosSegundos = segundosTranscurridos;
                if (listener != null) {
                    listener.onTick(segundosTranscurridos, estaPausado);
                }
                actualizarNotificacion();
                handler.postDelayed(this, 1000);
            }
        }
    };

    public class LocalBinder extends Binder {
        public StudyTimerService getService() {
            return StudyTimerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        crearCanalNotificacion();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        servicioCorriendo = false;
        servicioPausado = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_DETENER.equals(intent.getAction())) {
            detener();
            return START_NOT_STICKY;
        }
        if (estaCorriendo) {
            lanzarForegroundSeguro();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setListener(OnTimerTickListener listener) {
        this.listener = listener;
        if (listener != null) {
            listener.onTick(segundosTranscurridos, estaPausado);
        }
    }

    public void iniciar(int segundosIniciales) {
        this.segundosTranscurridos = segundosIniciales;
        ultimosSegundos = segundosIniciales;
        this.estaCorriendo = true;
        servicioCorriendo = true;
        this.estaPausado = false;
        servicioPausado = false;
        lanzarForegroundSeguro();
        handler.removeCallbacks(timerRunnable);
        handler.post(timerRunnable);
    }

    private void lanzarForegroundSeguro() {
        try {
            Notification notif = construirNotificacion();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceCompat.startForeground(this, NOTIFICACION_ID, notif, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
            } else {
                ServiceCompat.startForeground(this, NOTIFICACION_ID, notif, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pausar() {
        this.estaPausado = true;
        servicioPausado = true;
        actualizarNotificacion();
        if (listener != null) {
            listener.onTick(segundosTranscurridos, estaPausado);
        }
    }

    public void reanudar() {
        this.estaPausado = false;
        servicioPausado = false;
        handler.removeCallbacks(timerRunnable);
        handler.post(timerRunnable);
        if (listener != null) {
            listener.onTick(segundosTranscurridos, estaPausado);
        }
    }

    public void detener() {
        this.estaCorriendo = false;
        servicioCorriendo = false;
        this.estaPausado = false;
        servicioPausado = false;
        handler.removeCallbacks(timerRunnable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE);
        } else {
            stopForeground(true);
        }
        stopSelf();
    }

    public int getSegundosTranscurridos() {
        return segundosTranscurridos;
    }

    public boolean isEstaCorriendo() {
        return estaCorriendo;
    }

    public boolean isEstaPausado() {
        return estaPausado;
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CANAL_NOTIFICACION_ID,
                    "Sesión de Estudio Activa",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Mantiene activo el conteo del temporizador en segundo plano");
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification construirNotificacion() {
        int minutos = segundosTranscurridos / 60;
        int segs = segundosTranscurridos % 60;
        String tiempoStr = String.format("%02d:%02d", minutos, segs);
        String estadoStr = estaPausado ? "Sesión pausada" : "Enfoque Zanshin en curso";

        Intent intentApp = new Intent(this, MainActivity.class);
        intentApp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intentApp,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        return new NotificationCompat.Builder(this, CANAL_NOTIFICACION_ID)
                .setContentTitle(tiempoStr + " • StudyVibe")
                .setContentText(estadoStr)
                .setSmallIcon(R.drawable.ic_timer)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
                .build();
    }

    private void actualizarNotificacion() {
        if (!estaCorriendo) return;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            try {
                manager.notify(NOTIFICACION_ID, construirNotificacion());
            } catch (Exception ignored) {
            }
        }
    }
}
