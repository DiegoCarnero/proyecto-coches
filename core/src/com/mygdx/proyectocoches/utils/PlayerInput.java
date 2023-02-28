package com.mygdx.proyectocoches.utils;

public interface PlayerInput {

    /**
     * Devuelve si el coche debe moverse marcha adelante o marcha atras
     *
     * @return 'true' si marcha adelante, 'false' si marcha atras
     */
    boolean isAdelante();

    /**
     * Devuelve si el jugador esta frenando
     *
     * @return 'true' si esta frenando, 'false' si no
     */
    boolean isFrenando();

    /**
     * Devuelve si el jugador esta acelerando
     *
     * @return 'true' si esta acelerando, 'false' si no
     */
    boolean isAcelerando();

    /**
     * Devuelve el valor de giro, normalizado entre -50 y 50
     *
     * @return valor de giro entre -50 y 50
     */
    float getSteerValue();

    /**
     * Devuelve cuanto esta acelerando el jugador
     *
     * @return valor de la aceleracion entre 0 y 1
     */
    float getAccValue();
}
