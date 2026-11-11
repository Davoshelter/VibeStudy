package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.ReporteEstudio;
import com.david.mystudio.domain.model.SesionEstudio;
import com.david.mystudio.domain.model.TipoPeriodo;
import com.david.mystudio.domain.repository.MateriaRepository;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio de dominio para generación y consolidación de reportes analíticos (RN-REP).
 */
public class ReporteService {

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
    }

    private final SesionEstudioRepository sesionRepository;
    private final MateriaRepository materiaRepository;

    public ReporteService(SesionEstudioRepository sesionRepository, MateriaRepository materiaRepository) {
        this.sesionRepository = sesionRepository;
        this.materiaRepository = materiaRepository;
    }

    public void generarReporteSemanal(Date fechaReferencia, ResultadoCallback<ReporteEstudio> callback) {
        Date ref = fechaReferencia != null ? fechaReferencia : new Date();
        int numeroSemana = DateTimeUtils.obtenerNumeroSemana(ref);
        int anio = DateTimeUtils.obtenerAnio(ref);

        long inicioSemana = DateTimeUtils.obtenerInicioSemana(numeroSemana, anio);
        long finSemana = DateTimeUtils.obtenerFinSemana(numeroSemana, anio);

        procesarReporte(new Date(inicioSemana), new Date(finSemana), TipoPeriodo.SEMANAL, 7, callback);
    }

    public void generarReporteMensual(Date fechaReferencia, ResultadoCallback<ReporteEstudio> callback) {
        Date ref = fechaReferencia != null ? fechaReferencia : new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        long inicioMes = DateTimeUtils.obtenerInicioDia(cal.getTime());
        int diasEnMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, diasEnMes);
        long finMes = DateTimeUtils.obtenerFinDia(cal.getTime());

        procesarReporte(new Date(inicioMes), new Date(finMes), TipoPeriodo.MENSUAL, diasEnMes, callback);
    }

    private void procesarReporte(Date fechaInicio, Date fechaFin, TipoPeriodo periodo, int diasPeriodo,
                                 ResultadoCallback<ReporteEstudio> callback) {
        sesionRepository.obtenerCompletadasPorRango(fechaInicio.getTime(), fechaFin.getTime(), sesiones -> {
            materiaRepository.obtenerTodasActivas(materias -> {
                Map<Integer, Materia> mapaMaterias = new HashMap<>();
                if (materias != null) {
                    for (Materia m : materias) mapaMaterias.put(m.getId(), m);
                }

                int totalMinutos = 0;
                Map<Integer, Integer> minutosPorMateriaId = new HashMap<>();
                Map<String, Integer> minutosPorDia = new HashMap<>();

                if (sesiones != null) {
                    for (SesionEstudio s : sesiones) {
                        totalMinutos += s.getDuracionMinutos();
                        int actualMateria = minutosPorMateriaId.getOrDefault(s.getIdMateria(), 0);
                        minutosPorMateriaId.put(s.getIdMateria(), actualMateria + s.getDuracionMinutos());

                        // Agrupar por día
                        if (s.getFechaHoraInicio() != null) {
                            Calendar c = Calendar.getInstance();
                            c.setTime(s.getFechaHoraInicio());
                            int diaSemana = c.get(Calendar.DAY_OF_WEEK);
                            String claveDia = obtenerNombreDia(diaSemana);
                            minutosPorDia.put(claveDia, minutosPorDia.getOrDefault(claveDia, 0) + s.getDuracionMinutos());
                        }
                    }
                }

                // RN-REP-01: Distribución porcentual por materia
                Map<String, Double> distribucionPorcentual = new HashMap<>();
                if (totalMinutos > 0) {
                    for (Map.Entry<Integer, Integer> entry : minutosPorMateriaId.entrySet()) {
                        Materia mat = mapaMaterias.get(entry.getKey());
                        String nombreMat = mat != null ? mat.getNombre() : "Otra Asignatura";
                        double porcentaje = Math.round(((double) entry.getValue() / totalMinutos * 100.0) * 10.0) / 10.0;
                        distribucionPorcentual.put(nombreMat, porcentaje);
                    }
                }

                // RN-REP-02: Promedio diario
                double promedioDiario = diasPeriodo > 0
                        ? Math.round(((double) totalMinutos / diasPeriodo) * 10.0) / 10.0
                        : 0.0;

                ReporteEstudio reporte = new ReporteEstudio(
                        fechaInicio,
                        fechaFin,
                        periodo,
                        totalMinutos,
                        promedioDiario,
                        distribucionPorcentual,
                        minutosPorDia
                );

                callback.onSuccess(reporte);
            });
        });
    }

    private String obtenerNombreDia(int diaCalendar) {
        switch (diaCalendar) {
            case Calendar.MONDAY: return "Lun";
            case Calendar.TUESDAY: return "Mar";
            case Calendar.WEDNESDAY: return "Mié";
            case Calendar.THURSDAY: return "Jue";
            case Calendar.FRIDAY: return "Vie";
            case Calendar.SATURDAY: return "Sáb";
            case Calendar.SUNDAY: return "Dom";
            default: return "Otro";
        }
    }
}
