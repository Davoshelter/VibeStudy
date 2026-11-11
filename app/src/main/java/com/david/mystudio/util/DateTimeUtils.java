package com.david.mystudio.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utilitario para manipulación, cálculo de semanas y formateo de fechas.
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static String formatearMinutos(int minutosTotales) {
        if (minutosTotales <= 0) {
            return "0m";
        }
        int horas = minutosTotales / 60;
        int minutos = minutosTotales % 60;
        if (horas > 0 && minutos > 0) {
            return horas + "h " + minutos + "m";
        } else if (horas > 0) {
            return horas + "h 00m";
        } else {
            return minutos + "m";
        }
    }

    public static String formatearHora(Date fecha) {
        if (fecha == null) return "--:--";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(fecha);
    }

    public static long obtenerInicioDia(Date fecha) {
        Calendar cal = Calendar.getInstance();
        if (fecha != null) cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long obtenerFinDia(Date fecha) {
        Calendar cal = Calendar.getInstance();
        if (fecha != null) cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public static int obtenerNumeroSemana(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        if (fecha != null) cal.setTime(fecha);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static int obtenerAnio(Date fecha) {
        Calendar cal = Calendar.getInstance();
        if (fecha != null) cal.setTime(fecha);
        return cal.get(Calendar.YEAR);
    }

    public static long obtenerInicioSemana(int numeroSemana, int anio) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, anio);
        cal.set(Calendar.WEEK_OF_YEAR, numeroSemana);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long obtenerFinSemana(int numeroSemana, int anio) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, anio);
        cal.set(Calendar.WEEK_OF_YEAR, numeroSemana);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public static int diferenciaDiasCalendario(Date fechaReciente, Date fechaAntigua) {
        if (fechaReciente == null || fechaAntigua == null) {
            return -1;
        }
        long inicioReciente = obtenerInicioDia(fechaReciente);
        long inicioAntigua = obtenerInicioDia(fechaAntigua);
        long diferenciaMs = inicioReciente - inicioAntigua;
        return (int) (diferenciaMs / (24L * 60L * 60L * 1000L));
    }
}
