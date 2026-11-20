package com.david.mystudio;

import android.content.Context;
import com.david.mystudio.data.local.AppDatabase;
import com.david.mystudio.data.repository.MateriaRoomRepository;
import com.david.mystudio.data.repository.MetaSemanalRoomRepository;
import com.david.mystudio.data.repository.RachaRoomRepository;
import com.david.mystudio.data.repository.SesionEstudioRoomRepository;
import com.david.mystudio.domain.repository.MateriaRepository;
import com.david.mystudio.domain.repository.MetaSemanalRepository;
import com.david.mystudio.domain.repository.RachaRepository;
import com.david.mystudio.domain.repository.SesionEstudioRepository;
import com.david.mystudio.domain.service.MateriaService;
import com.david.mystudio.domain.service.MetaSemanalService;
import com.david.mystudio.domain.service.RachaService;
import com.david.mystudio.domain.service.ReporteService;
import com.david.mystudio.domain.service.SesionEstudioService;
import com.david.mystudio.ui.enfoque.SesionEnfoque;

/**
 * Contenedor central de inyección de dependencias manual.
 * Suministra instancias únicas de bases de datos, repositorios y servicios.
 */
public class AppContainer {

    private final AppDatabase database;
    private final MateriaRepository materiaRepository;
    private final SesionEstudioRepository sesionEstudioRepository;
    private final MetaSemanalRepository metaSemanalRepository;
    private final RachaRepository rachaRepository;

    private final MateriaService materiaService;
    private final RachaService rachaService;
    private final MetaSemanalService metaSemanalService;
    private final SesionEstudioService sesionEstudioService;
    private final SesionEnfoque sesionEnfoque;
    private final ReporteService reporteService;

    public AppContainer(Context applicationContext) {
        this.database = AppDatabase.getInstance(applicationContext);

        this.materiaRepository = new MateriaRoomRepository(database.materiaDao());
        this.sesionEstudioRepository = new SesionEstudioRoomRepository(database.sesionEstudioDao());
        this.metaSemanalRepository = new MetaSemanalRoomRepository(database.metaSemanalDao());
        this.rachaRepository = new RachaRoomRepository(database.rachaDao());

        this.materiaService = new MateriaService(materiaRepository);
        this.rachaService = new RachaService(rachaRepository);
        this.metaSemanalService = new MetaSemanalService(metaSemanalRepository, sesionEstudioRepository);
        this.sesionEstudioService = new SesionEstudioService(sesionEstudioRepository, metaSemanalService, rachaService);
        this.sesionEnfoque = new SesionEnfoque(applicationContext, sesionEstudioService);
        this.reporteService = new ReporteService(sesionEstudioRepository, materiaRepository);
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public SesionEstudioService getSesionEstudioService() {
        return sesionEstudioService;
    }

    public SesionEnfoque getSesionEnfoque() {
        return sesionEnfoque;
    }

    public MetaSemanalService getMetaSemanalService() {
        return metaSemanalService;
    }

    public RachaService getRachaService() {
        return rachaService;
    }

    public ReporteService getReporteService() {
        return reporteService;
    }

    public SesionEstudioRepository getSesionEstudioRepository() {
        return sesionEstudioRepository;
    }
}
