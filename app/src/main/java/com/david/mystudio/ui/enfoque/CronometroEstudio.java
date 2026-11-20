package com.david.mystudio.ui.enfoque;

import java.util.function.LongSupplier;

/** Mide el tiempo efectivo a partir de un reloj monotónico. */
final class CronometroEstudio {

    private final LongSupplier reloj;
    private long acumuladoMs;
    private long inicioTramoMs = -1;

    CronometroEstudio(LongSupplier reloj) {
        this.reloj = reloj;
    }

    void pausar() {
        if (inicioTramoMs >= 0) {
            acumuladoMs += reloj.getAsLong() - inicioTramoMs;
            inicioTramoMs = -1;
        }
    }

    int getSegundos() {
        return (int) Math.min(Integer.MAX_VALUE, getMilisegundosEn(reloj.getAsLong()) / 1000);
    }

    long getMilisegundosEn(long instanteMs) {
        long tramoMs = inicioTramoMs < 0 ? 0 : Math.max(0, instanteMs - inicioTramoMs);
        return acumuladoMs + tramoMs;
    }

    void restablecer(long tiempoMs, long instanteMs, boolean activo) {
        acumuladoMs = tiempoMs;
        inicioTramoMs = activo ? instanteMs : -1;
    }

    /** Conserva la pausa y permite reconstruir el tramo activo tras un reinicio. */
    static final class PuntoControl {
        final int idSesion;
        final long tiempoMs;
        final long relojMonotonicoMs;
        final long relojCivilMs;
        final int arranque;
        final boolean activo;

        PuntoControl(int idSesion, long tiempoMs, long relojMonotonicoMs, long relojCivilMs,
                     int arranque, boolean activo) {
            this.idSesion = idSesion;
            this.tiempoMs = tiempoMs;
            this.relojMonotonicoMs = relojMonotonicoMs;
            this.relojCivilMs = relojCivilMs;
            this.arranque = arranque;
            this.activo = activo;
        }

        long calcularTiempo(long ahoraMonotonicoMs, long ahoraCivilMs, int arranqueActual) {
            if (!activo) return tiempoMs;
            boolean mismoArranque = arranque >= 0 && arranque == arranqueActual
                    && ahoraMonotonicoMs >= relojMonotonicoMs;
            long tramo = mismoArranque ? ahoraMonotonicoMs - relojMonotonicoMs : ahoraCivilMs - relojCivilMs;
            return tiempoMs + Math.max(0, tramo);
        }
    }
}
