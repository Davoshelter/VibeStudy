package com.david.mystudio.domain.model;

/**
 * Modelo de presentación/dominio compuesto para la lista de materias en la Pantalla 2.
 */
public class MateriaConEstadisticas {

    private final Materia materia;
    private final int totalSesiones;
    private final double totalHoras;

    public MateriaConEstadisticas(Materia materia, int totalSesiones, double totalHoras) {
        this.materia = materia;
        this.totalSesiones = totalSesiones;
        this.totalHoras = totalHoras;
    }

    public Materia getMateria() {
        return materia;
    }

    public int getTotalSesiones() {
        return totalSesiones;
    }

    public double getTotalHoras() {
        return totalHoras;
    }
}
