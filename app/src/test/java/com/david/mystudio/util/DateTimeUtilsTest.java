package com.david.mystudio.util;

import org.junit.Test;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para formateo y cálculos temporales (DateTimeUtils).
 */
public class DateTimeUtilsTest {

    @Test
    public void formatearMinutos_convierteAHorasYMinutos() {
        assertEquals("1h 30m", DateTimeUtils.formatearMinutos(90));
        assertEquals("2h 00m", DateTimeUtils.formatearMinutos(120));
        assertEquals("45m", DateTimeUtils.formatearMinutos(45));
        assertEquals("0m", DateTimeUtils.formatearMinutos(0));
    }

    @Test
    public void diferenciaDiasCalendario_calculaDiasExactos() {
        Calendar hoy = Calendar.getInstance();
        Date fechaHoy = hoy.getTime();

        Calendar ayer = Calendar.getInstance();
        ayer.add(Calendar.DAY_OF_YEAR, -1);
        Date fechaAyer = ayer.getTime();

        Calendar haceTresDias = Calendar.getInstance();
        haceTresDias.add(Calendar.DAY_OF_YEAR, -3);
        Date fechaHaceTresDias = haceTresDias.getTime();

        assertEquals(0, DateTimeUtils.diferenciaDiasCalendario(fechaHoy, fechaHoy));
        assertEquals(1, DateTimeUtils.diferenciaDiasCalendario(fechaHoy, fechaAyer));
        assertEquals(3, DateTimeUtils.diferenciaDiasCalendario(fechaHoy, fechaHaceTresDias));
    }
}
