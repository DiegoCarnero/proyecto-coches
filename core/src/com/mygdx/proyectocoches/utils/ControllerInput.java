package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.mygdx.proyectocoches.ui.PauseMenu;

public class ControllerInput implements PlayerInput {

    private final Controller mando;
    private boolean adelante;

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

    @Override
    public boolean isAdelante() {
        return adelante;
    }

    @Override
    public boolean isFrenando() {
        // L2
        return mando.getAxis(5) > 0;
    }

    @Override
    public boolean isAcelerando() {
        return mando.getAxis(0) > 0;
    }

    @Override
    public float getSteerValue() {
        // L stick horizontal
        return -mando.getAxis(7) * 50;
    }

    @Override
    public float getAccValue() {
        // R2
        return mando.getAxis(0);
    }
}
