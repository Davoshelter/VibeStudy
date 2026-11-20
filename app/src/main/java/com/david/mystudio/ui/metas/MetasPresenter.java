package com.david.mystudio.ui.metas;

import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.MetaSemanal;
import com.david.mystudio.domain.service.MateriaService;
import com.david.mystudio.domain.service.MetaSemanalService;
import com.david.mystudio.util.DateTimeUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presentador para el módulo de Metas Semanales.
 */
public class MetasPresenter implements MetasContract.Presenter {

    private MetasContract.View vista;
    private final MetaSemanalService metaService;
    private final MateriaService materiaService;

    private int numeroSemanaActual;
    private int anioActual;

    public MetasPresenter(MetaSemanalService metaService, MateriaService materiaService) {
        this.metaService = metaService;
        this.materiaService = materiaService;

        Date ahora = new Date();
        this.numeroSemanaActual = DateTimeUtils.obtenerNumeroSemana(ahora);
        this.anioActual = DateTimeUtils.obtenerAnio(ahora);
    }

    @Override
    public void vincularVista(MetasContract.View vista) {
        this.vista = vista;
    }

    @Override
    public void desvincularVista() {
        this.vista = null;
    }

    @Override
    public void cargarMetasSemanaActual() {
        if (vista == null) return;
        vista.mostrarCargando(true);

        String textoSemana = "Semana " + numeroSemanaActual + " (" + anioActual + ")";
        vista.mostrarSemana(textoSemana);

        metaService.sincronizarMetasConSesionesReales(numeroSemanaActual, anioActual, () -> {
            materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
                @Override
                public void onSuccess(List<Materia> materias) {
                    Map<Integer, Materia> mapa = new HashMap<>();
                    if (materias != null) {
                        for (Materia m : materias) mapa.put(m.getId(), m);
                    }

                    metaService.obtenerMetasSemana(numeroSemanaActual, anioActual, new MetaSemanalService.ResultadoCallback<List<MetaSemanal>>() {
                        @Override
                        public void onSuccess(List<MetaSemanal> metas) {
                            int totalObj = 0;
                            int totalCumpl = 0;
                            if (metas != null) {
                                for (MetaSemanal m : metas) {
                                    totalObj += m.getMinutosObjetivo();
                                    totalCumpl += m.getMinutosCumplidos();
                                }
                            }
                            int porcentaje = MetaSemanal.porcentajeEntero(totalCumpl, totalObj);

                            if (vista != null) {
                                vista.mostrarCargando(false);
                                vista.mostrarCompromisoTotal(totalCumpl, totalObj, porcentaje);
                                vista.mostrarMetas(metas, mapa);
                            }
                        }

                        @Override
                        public void onError(String mensajeError) {
                            if (vista != null) {
                                vista.mostrarCargando(false);
                                vista.mostrarError(mensajeError);
                            }
                        }
                    });
                }

                @Override
                public void onError(String mensajeError) {
                    if (vista != null) {
                        vista.mostrarCargando(false);
                        vista.mostrarError(mensajeError);
                    }
                }
            });
        });
    }

    @Override
    public void cambiarSemana(int desplazamientoSemanas) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, anioActual);
        cal.set(Calendar.WEEK_OF_YEAR, numeroSemanaActual);
        cal.add(Calendar.WEEK_OF_YEAR, desplazamientoSemanas);

        this.numeroSemanaActual = cal.get(Calendar.WEEK_OF_YEAR);
        this.anioActual = cal.get(Calendar.YEAR);

        cargarMetasSemanaActual();
    }

    @Override
    public void alPulsarAgregarMeta() {
        if (vista == null) return;
        materiaService.obtenerMateriasActivas(new MateriaService.ResultadoCallback<List<Materia>>() {
            @Override
            public void onSuccess(List<Materia> materias) {
                if (vista != null) vista.mostrarDialogoFijarMeta(materias);
            }

            @Override
            public void onError(String mensajeError) {
                if (vista != null) vista.mostrarError(mensajeError);
            }
        });
    }

    @Override
    public void guardarMeta(int idMateria, double horasObjetivo) {
        if (vista == null) return;
        vista.mostrarCargando(true);

        metaService.establecerMetaSemanal(idMateria, numeroSemanaActual, anioActual, horasObjetivo,
                new MetaSemanalService.ResultadoCallback<Long>() {
                    @Override
                    public void onSuccess(Long resultado) {
                        if (vista != null) {
                            vista.mostrarCargando(false);
                            vista.mostrarMensajeExito("Meta semanal configurada exitosamente");
                            cargarMetasSemanaActual();
                        }
                    }

                    @Override
                    public void onError(String mensajeError) {
                        if (vista != null) {
                            vista.mostrarCargando(false);
                            vista.mostrarError(mensajeError);
                        }
                    }
                });
    }
}
