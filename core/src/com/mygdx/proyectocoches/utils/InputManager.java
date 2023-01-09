package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.proyectocoches.ui.TestOsd;

public class InputManager {

    TestOsd inputOsd;
    Body jugador;
    float nuevAcc;
    float nuevSteer = 0;

    public InputManager(TestOsd osd, Body jugador) {
        inputOsd = osd;
        this.jugador = jugador;
    }

    public void update() {
        Vector2 v = new Vector2(0, 0);

        nuevAcc = inputOsd.getAccValue();
        nuevSteer = inputOsd.getSteerValue() - 50.0f;
        v.set(0, nuevAcc * 0.25f);

        if (nuevSteer == 0) {
            jugador.setAngularVelocity(0.0f);
        } else {
            jugador.setAngularVelocity(nuevSteer * 0.05f);
        }

        if (jugador.getLinearVelocity().len() < MAX_VELOCIDAD_FORW && !v.isZero()) {
            jugador.applyForceToCenter(jugador.getWorldVector(v), true);
        }
    }

}
