package com.david.mystudio.ui.base;

/**
 * Interfaz base para todos los Presenters en el patrón MVP.
 */
public interface BasePresenter<V extends BaseView> {
    void vincularVista(V vista);
    void desvincularVista();
}
