package com.david.mystudio.ui.base;

/**
 * Interfaz base para todas las Vistas en el patrón MVP.
 */
public interface BaseView {
    void mostrarCargando(boolean cargando);
    void mostrarError(String mensaje);
}
