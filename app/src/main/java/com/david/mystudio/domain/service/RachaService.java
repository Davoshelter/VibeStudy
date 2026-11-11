package com.david.mystudio.domain.service;

import com.david.mystudio.domain.model.RachaEstudio;
import com.david.mystudio.domain.repository.RachaRepository;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Date;

/**
 * Servicio de dominio que implementa las reglas de negocio de racha Kaizen (RN-RAC).
 */
public class RachaService {

    public interface ResultadoCallback<T> {
        void onSuccess(T resultado);
    }

    private final RachaRepository rachaRepository;

    public RachaService(RachaRepository rachaRepository) {
        this.rachaRepository = rachaRepository;
    }

    public void obtenerRachaActual(ResultadoCallback<RachaEstudio> callback) {
        rachaRepository.obtenerRachaActual(racha -> {
            // Verificar si pasaron más de 48 horas (RN-RAC-02)
            if (racha.getUltimaFechaEstudio() != null) {
                int diasDiferencia = DateTimeUtils.diferenciaDiasCalendario(new Date(), racha.getUltimaFechaEstudio());
                if (diasDiferencia > 1) { // Pasó al menos 1 día calendario completo sin estudiar
                    racha.setDiasConsecutivos(0);
                    rachaRepository.actualizarRacha(racha, v -> callback.onSuccess(racha));
                    return;
                }
            }
            callback.onSuccess(racha);
        });
    }

    public void evaluarRachaTrasSesion(Date fechaSesion, Runnable onCompleto) {
        rachaRepository.obtenerRachaActual(racha -> {
            Date ahora = fechaSesion != null ? fechaSesion : new Date();

            if (racha.getUltimaFechaEstudio() == null) {
                // Primera sesión registrada en la historia de la app
                racha.setDiasConsecutivos(1);
            } else {
                int diferenciaDias = DateTimeUtils.diferenciaDiasCalendario(ahora, racha.getUltimaFechaEstudio());

                if (diferenciaDias == 0) {
                    // Ya había estudiado hoy; la racha se mantiene
                } else if (diferenciaDias == 1) {
                    // Estudió ayer y hoy completó sesión: incremento (RN-RAC-01)
                    racha.setDiasConsecutivos(racha.getDiasConsecutivos() + 1);
                } else {
                    // Pasaron más de 48 horas: se rompió la racha anterior e inicia una nueva (RN-RAC-02)
                    racha.setDiasConsecutivos(1);
                }
            }

            racha.setUltimaFechaEstudio(ahora);

            // RN-RAC-03: Récord histórico
            if (racha.getDiasConsecutivos() > racha.getRachaMaximaHistorica()) {
                racha.setRachaMaximaHistorica(racha.getDiasConsecutivos());
            }

            rachaRepository.actualizarRacha(racha, v -> {
                if (onCompleto != null) onCompleto.run();
            });
        });
    }
}
