package com.david.mystudio.util;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gestor centralizado de concurrencia para ejecutar tareas en segundo plano
 * y despachar callbacks en el hilo principal (UI Thread).
 */
public class ThreadExecutor {

    private static volatile ThreadExecutor instancia;
    private final ExecutorService backgroundExecutor;
    private final Handler mainThreadHandler;

    private ThreadExecutor() {
        this.backgroundExecutor = Executors.newFixedThreadPool(4);
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public static ThreadExecutor getInstance() {
        if (instancia == null) {
            synchronized (ThreadExecutor.class) {
                if (instancia == null) {
                    instancia = new ThreadExecutor();
                }
            }
        }
        return instancia;
    }

    public void executeOnBackground(Runnable runnable) {
        backgroundExecutor.execute(runnable);
    }

    public void executeOnMain(Runnable runnable) {
        mainThreadHandler.post(runnable);
    }
}
