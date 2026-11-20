package com.david.mystudio.ui.enfoque;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.david.mystudio.StudyApplication;

/** Pide recuperar la sesión al desbloquear el dispositivo después de arrancar. */
public final class RecuperarCronometroReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
        StudyApplication app = (StudyApplication) context.getApplicationContext();
        app.getAppContainer().getSesionEnfoque().recuperar(mensaje -> Log.w("RecuperarCronometro", mensaje));
    }
}
