package com.david.mystudio.ui.enfoque;

import com.david.mystudio.ui.enfoque.CronometroEstudio.PuntoControl;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CronometroEstudioTest {

    private final AtomicLong reloj = new AtomicLong();
    private final CronometroEstudio cronometro = new CronometroEstudio(reloj::get);

    @Test
    public void cuentaTodoElTiempoAunqueLaPantallaNoRecibaTicks() {
        cambiarEstado(true);
        assertEquals(0, cronometro.getSegundos());
        reloj.set(59999);
        assertEquals(59, cronometro.getSegundos());
        reloj.set(60000);
        cronometro.pausar();
        assertEquals(60, cronometro.getSegundos());
    }

    @Test
    public void conservaFraccionesYExcluyeElTiempoPausado() {
        cambiarEstado(true);
        reloj.set(1500);
        cronometro.pausar();
        reloj.set(60000);
        cronometro.pausar();
        assertEquals(1, cronometro.getSegundos());

        cambiarEstado(true);
        reloj.set(60250);
        cambiarEstado(true);
        reloj.set(60500);
        assertEquals(2, cronometro.getSegundos());
    }

    @Test
    public void recuperaUsandoElRelojMonotonicoDentroDelMismoArranque() {
        PuntoControl punto = new PuntoControl(7, 30000, 120000, 1000000, 3, true);

        assertEquals(60000, punto.calcularTiempo(150000, 500000, 3));
    }

    @Test
    public void recuperaElTramoActivoTrasReiniciarYAceptaLaPausa() {
        PuntoControl activo = new PuntoControl(7, 30000, 120000, 1000000, 3, true);
        PuntoControl pausado = new PuntoControl(7, 30000, 120000, 1000000, 3, false);

        assertEquals(150000, activo.calcularTiempo(12000, 1120000, 4));
        assertEquals(30000, pausado.calcularTiempo(12000, 1120000, 4));
        assertEquals(30000, activo.calcularTiempo(12000, 900000, 4));
    }

    private void cambiarEstado(boolean activo) {
        long ahora = reloj.get();
        cronometro.restablecer(cronometro.getMilisegundosEn(ahora), ahora, activo);
    }
}
