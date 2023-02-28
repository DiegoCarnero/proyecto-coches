package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.mygdx.proyectocoches.ui.PauseMenu;

/**
 * Gestion de control por mando para el jugador
 * Solo soporta DualShock 4
 */
public class ControllerInput implements PlayerInput {

    /**
     * Mando asigando
     */
    private final Controller mando;
    /**
     * 'true' si el vehiculo del jugador se desplaza hacia delante, false si hacia atras
     */
    private boolean adelante;

    /**
     * Gestion de control por mando para el jugador
     * Solo soporta DualShock 4
     *
     * @param c mando asignado
     */
    public ControllerInput(Controller c) {

        this.mando = c;
        adelante = true;

        c.addListener(new ControllerListener() {
            @Override
            public void connected(Controller controller) {
            }

            @Override
            public void disconnected(Controller controller) {
            }

            /**
             * Si se pulsa triangulo, se alterna entre marcha adelante y marcha atras
             * @param controller mando que envia el input
             * @param buttonCode codigo del boton presionado
             * @return 'true'
             */
            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                // triangulo
                if (buttonCode == 100) {
                    adelante = !adelante;
                }
                return true;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                return false;
            }
        });

    }

    /**
     * Devuelve si el coche debe moverse marcha adelante o marcha atras
     *
     * @return 'true' si marcha adelante, 'false' si marcha atras
     */
    @Override
    public boolean isAdelante() {
        return adelante;
    }

    /**
     * Devuelve si el jugador esta frenando (presionando L2)
     *
     * @return 'true' si esta frenando, 'false' si no
     */
    @Override
    public boolean isFrenando() {
        // L2
        return mando.getAxis(5) > 0;
    }

    /**
     * Devuelve si el jugador esta acelerando (presionando R2)
     *
     * @return 'true' si esta acelerando, 'false' si no
     */
    @Override
    public boolean isAcelerando() {
        return mando.getAxis(0) > 0;
    }

    /**
     * Devuelve el valor de giro (strick izquierdo, eje horizontal), normalizado entre -50 y 50
     *
     * @return valor de giro entre -50 y 50
     */
    @Override
    public float getSteerValue() {
        // L stick horizontal
        return -mando.getAxis(7) * 50;
    }

    /**
     * Devuelve cuanto esta acelerando el jugador
     *
     * @return valor de la aceleracion entre 0 y 1
     */
    @Override
    public float getAccValue() {
        // R2
        return mando.getAxis(0);
    }
}
