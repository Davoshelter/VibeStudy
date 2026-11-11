package com.david.mystudio;

import android.app.Application;

/**
 * Clase Application personalizada para inicializar el contenedor de dependencias.
 */
public class StudyApplication extends Application {

    private AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(this);
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }
}
