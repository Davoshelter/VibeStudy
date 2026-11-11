package com.david.mystudio.domain.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para las reglas de negocio de MetaSemanal (RN-MET).
 */
public class MetaSemanalTest {

    @Test
    public void calcularPorcentajeAvance_calculaCorrectamente() {
        MetaSemanal meta = new MetaSemanal(1, 10, 45, 2026, 10.0, 5.0, EstadoMeta.EN_PROGRESO);
        assertEquals(50.0, meta.calcularPorcentajeAvance(), 0.01);
    }

    @Test
    public void calcularPorcentajeAvance_noSuperaCienPorCiento() {
        MetaSemanal meta = new MetaSemanal(1, 10, 45, 2026, 10.0, 15.0, EstadoMeta.CUMPLIDA);
        assertEquals(100.0, meta.calcularPorcentajeAvance(), 0.01);
    }

    @Test
    public void estaCumplida_retornaTrueCuandoAlcanzaObjetivo() {
        MetaSemanal meta = new MetaSemanal(1, 10, 45, 2026, 6.0, 6.0, EstadoMeta.CUMPLIDA);
        assertTrue(meta.estaCumplida());
    }

    @Test
    public void estaCumplida_retornaFalseCuandoFaltanHoras() {
        MetaSemanal meta = new MetaSemanal(1, 10, 45, 2026, 6.0, 4.5, EstadoMeta.EN_PROGRESO);
        assertFalse(meta.estaCumplida());
    }
}
