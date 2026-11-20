package com.david.mystudio.ui.enfoque;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.david.mystudio.R;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoTecnica;
import com.david.mystudio.domain.service.SesionEstudioService;
import com.david.mystudio.ui.enfoque.CronometroEstudio.PuntoControl;
import java.util.function.Consumer;

/** Coordina el cronómetro, la sesión persistida y el servicio en el hilo principal. */
public final class SesionEnfoque {

    public enum Estado { INACTIVA, RECUPERABLE, INICIANDO, ACTIVA, PAUSADA, FINALIZANDO }

    private final Context contexto;
    private final SesionEstudioService servicio;
    private final RegistroCronometro registro;
    private final int arranque;
    private final MutableLiveData<Estado> estado;
    private final MutableLiveData<Integer> segundos = new MutableLiveData<>(0);
    private PuntoControl puntoControl;
    private SesionEstudio sesion;
    private CronometroEstudio cronometro;
    private String notas;
    private boolean servicioEnPrimerPlano;
    private Consumer<String> errorDeInicio;

    public SesionEnfoque(Context contexto, SesionEstudioService servicio) {
        this.contexto = contexto.getApplicationContext();
        this.servicio = servicio;
        registro = new RegistroCronometro(this.contexto);
        arranque = leerArranque(this.contexto);
        puntoControl = registro.cargar();
        notas = puntoControl == null ? "" : registro.cargarNotas();
        estado = new MutableLiveData<>(puntoControl == null ? Estado.INACTIVA : Estado.RECUPERABLE);
    }

    private static int leerArranque(Context contexto) {
        try {
            return Settings.Global.getInt(contexto.getContentResolver(), Settings.Global.BOOT_COUNT, -1);
        } catch (SecurityException error) {
            Log.w("SesionEnfoque", "Android no expone el número de arranque", error);
            return -1;
        }
    }

    public LiveData<Estado> observarEstado() { return estado; }
    public LiveData<Integer> observarSegundos() { return segundos; }
    public Estado getEstado() { return estado.getValue(); }
    public int getSegundos() { return cronometro == null ? 0 : cronometro.getSegundos(); }
    public int getIdGuardado() { return puntoControl == null ? 0 : puntoControl.idSesion; }
    public SesionEstudio getSesion() { return sesion; }
    public String getNotas() { return notas; }

    public void guardarBorrador(String notas) {
        if (!this.notas.equals(notas)) {
            this.notas = notas;
            if (puntoControl != null) registro.guardarNotas(notas);
        }
    }

    public void iniciar(int idMateria, TipoTecnica tecnica, Consumer<String> alFallar) {
        if (getEstado() != Estado.INACTIVA) return;
        estado.setValue(Estado.INICIANDO);
        servicio.iniciarSesion(idMateria, tecnica, new SesionEstudioService.ResultadoCallback<SesionEstudio>() {
            @Override
            public void onSuccess(SesionEstudio guardada) {
                sesion = guardada;
                cronometro = new CronometroEstudio(SystemClock::elapsedRealtime);
                solicitarServicio(alFallar);
            }

            @Override
            public void onError(String mensaje) {
                estado.setValue(Estado.INACTIVA);
                alFallar.accept(mensaje);
            }
        });
    }

    public void recuperar(Consumer<String> alFallar) {
        if (getEstado() != Estado.RECUPERABLE) return;
        estado.setValue(Estado.INICIANDO);
        solicitarServicio(alFallar);
    }

    private void solicitarServicio(Consumer<String> alFallar) {
        errorDeInicio = alFallar;
        try {
            ContextCompat.startForegroundService(contexto, new Intent(contexto, StudyTimerService.class));
        } catch (IllegalStateException | SecurityException error) {
            Log.w("SesionEnfoque", "Android no pudo iniciar el servicio de estudio", error);
            fallarInicioDelServicio();
        }
    }

    void prepararReinicioDelServicio() {
        if (getEstado() == Estado.RECUPERABLE) {
            errorDeInicio = mensaje -> Log.w("SesionEnfoque", mensaje);
            estado.setValue(Estado.INICIANDO);
        }
    }

    boolean debeIniciarServicio() {
        return !servicioEnPrimerPlano && getEstado() == Estado.INICIANDO
                && (sesion != null || puntoControl != null);
    }

    void confirmarInicioDelServicio() {
        servicioEnPrimerPlano = true;
        if (sesion == null) {
            recuperarSesionGuardada();
        } else if (guardarEstadoCronometro(true)) {
            errorDeInicio = null;
            estado.setValue(Estado.ACTIVA);
        } else {
            fallarInicioDelServicio();
        }
    }

    private void recuperarSesionGuardada() {
        PuntoControl punto = puntoControl;
        servicio.buscarSesionRecuperable(punto.idSesion, new SesionEstudioService.ResultadoCallback<SesionEstudio>() {
            @Override
            public void onSuccess(SesionEstudio guardada) {
                if (!servicioEnPrimerPlano || getEstado() != Estado.INICIANDO) return;
                if (guardada == null) {
                    limpiarSesion();
                    return;
                }

                sesion = guardada;
                long ahora = SystemClock.elapsedRealtime();
                long tiempo = punto.calcularTiempo(ahora, System.currentTimeMillis(), arranque);
                cronometro = new CronometroEstudio(SystemClock::elapsedRealtime);
                cronometro.restablecer(tiempo, ahora, punto.activo);
                guardarEstadoCronometro(punto.activo);
                errorDeInicio = null;
                estado.setValue(punto.activo ? Estado.ACTIVA : Estado.PAUSADA);
                publicarTiempo();
            }

            @Override
            public void onError(String mensaje) {
                if (servicioEnPrimerPlano && getEstado() == Estado.INICIANDO) {
                    informarFalloDeRecuperacion(mensaje);
                    contexto.stopService(new Intent(contexto, StudyTimerService.class));
                }
            }
        });
    }

    void fallarInicioDelServicio() {
        if (puntoControl != null) {
            informarFalloDeRecuperacion(contexto.getString(R.string.enfoque_error_recuperar));
            return;
        }
        Consumer<String> alFallar = errorDeInicio;
        errorDeInicio = null;
        estado.setValue(Estado.PAUSADA);
        finalizar(minutos -> alFallar.accept(contexto.getString(R.string.enfoque_error_temporizador)), alFallar);
    }

    private void informarFalloDeRecuperacion(String mensaje) {
        Consumer<String> alFallar = errorDeInicio;
        errorDeInicio = null;
        servicioEnPrimerPlano = false;
        estado.setValue(Estado.RECUPERABLE);
        if (alFallar != null) alFallar.accept(mensaje);
    }

    void desconectarServicio() {
        servicioEnPrimerPlano = false;
        Estado actual = getEstado();
        if (puntoControl != null && (actual == Estado.INICIANDO || actual == Estado.ACTIVA || actual == Estado.PAUSADA)) {
            sesion = null;
            cronometro = null;
            errorDeInicio = null;
            estado.setValue(Estado.RECUPERABLE);
            publicarTiempo();
        }
    }

    public boolean puedeAlternarPausa() {
        return servicioEnPrimerPlano && (getEstado() == Estado.ACTIVA || getEstado() == Estado.PAUSADA);
    }

    public void alternarPausa(Consumer<String> alFallar) {
        if (!puedeAlternarPausa()) return;
        boolean activo = getEstado() == Estado.PAUSADA;
        if (!guardarEstadoCronometro(activo)) {
            alFallar.accept(contexto.getString(R.string.enfoque_error_guardar_reloj));
            return;
        }
        estado.setValue(activo ? Estado.ACTIVA : Estado.PAUSADA);
        publicarTiempo();
    }

    public void finalizar(Consumer<Integer> alGuardar, Consumer<String> alFallar) {
        Estado anterior = getEstado();
        if (anterior != Estado.ACTIVA && anterior != Estado.PAUSADA) return;

        if (puntoControl != null && !guardarEstadoCronometro(false)) {
            alFallar.accept(contexto.getString(R.string.enfoque_error_guardar_reloj));
            return;
        }
        cronometro.pausar();
        int segundosEfectivos = getSegundos();
        estado.setValue(Estado.FINALIZANDO);
        publicarTiempo();

        servicio.cerrarSesion(sesion, segundosEfectivos, notas, new SesionEstudioService.ResultadoCallback<Integer>() {
            @Override
            public void onSuccess(Integer minutos) {
                limpiarSesion();
                alGuardar.accept(minutos);
            }

            @Override
            public void onError(String mensaje) {
                if (!servicioEnPrimerPlano && puntoControl != null) {
                    sesion = null;
                    cronometro = null;
                    estado.setValue(Estado.RECUPERABLE);
                } else {
                    boolean seguir = servicioEnPrimerPlano && anterior == Estado.ACTIVA && guardarEstadoCronometro(true);
                    estado.setValue(seguir ? Estado.ACTIVA : Estado.PAUSADA);
                }
                alFallar.accept(mensaje);
            }
        });
    }

    private boolean guardarEstadoCronometro(boolean activo) {
        long ahora = SystemClock.elapsedRealtime();
        PuntoControl nuevo = new PuntoControl(sesion.getId(), cronometro.getMilisegundosEn(ahora), ahora,
                System.currentTimeMillis(), arranque, activo);
        if (!registro.guardar(nuevo, notas)) {
            Log.w("SesionEnfoque", "No se pudo conservar el estado del cronómetro");
            return false;
        }
        puntoControl = nuevo;
        cronometro.restablecer(nuevo.tiempoMs, ahora, activo);
        return true;
    }

    private void limpiarSesion() {
        puntoControl = null;
        sesion = null;
        cronometro = null;
        notas = "";
        errorDeInicio = null;
        servicioEnPrimerPlano = false;
        registro.limpiar();
        contexto.stopService(new Intent(contexto, StudyTimerService.class));
        estado.setValue(Estado.INACTIVA);
        publicarTiempo();
    }

    void publicarTiempo() {
        int valor = getSegundos();
        if (valor != segundos.getValue()) segundos.setValue(valor);
    }
}
