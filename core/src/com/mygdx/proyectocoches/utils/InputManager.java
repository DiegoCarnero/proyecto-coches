package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.DERRAPE;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;

import static java.lang.Math.sqrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
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

    private static Vector2 VectorPorEscalar(Vector2 v, float f) {
        return new Vector2(f * v.x, f * v.y);
    }

    private static Vector2 VeloFrenteDeCuerpo(Body b) {
        Vector2 normal = b.getWorldVector(new Vector2(0, 1));
        float prodEscalar = normal.dot(b.getLinearVelocity());
        return VectorPorEscalar(normal, prodEscalar);
    }

    private static Vector2 VeloLateralDeCuerpo(Body b) {
        Vector2 normal = b.getWorldVector(new Vector2(1, 0));
        float prodEscalar = normal.dot(b.getLinearVelocity());
        return VectorPorEscalar(normal, prodEscalar);
    }

    public void update() {
        Vector2 v = new Vector2(0, 0);

        if (inputOsd.isAccelerating()) {
            // normalizar a la velocidad maxima
            nuevAcc = inputOsd.getAccValue() * MAX_VELOCIDAD_FORW / 100f;
            v.set(0, nuevAcc * (MAX_VELOCIDAD_FORW * 0.01f));
        }

        // normalizar  entre -50 y 50. Cambiar signo para que se corresponda drcha e izq en el control y el coche
        nuevSteer = -(inputOsd.getSteerValue() - 50.0f);

        if (nuevSteer == 0 || jugador.getLinearVelocity().len() < 0.1) {
            jugador.setAngularVelocity(0.0f);
        } else {
            jugador.setAngularVelocity(nuevSteer * 0.05f);
        }

        float velActual = jugador.getLinearVelocity().len();
        float maxVeloPorInput = inputOsd.getAccValue() * MAX_VELOCIDAD_FORW / 100f;

        if (velActual < maxVeloPorInput && velActual < MAX_VELOCIDAD_FORW && !v.isZero()) {
            jugador.applyForceToCenter(jugador.getWorldVector(v), true);
        }

        // derrape
        Vector2 veloFrente = VeloFrenteDeCuerpo(jugador);
        Vector2 veloLateral = VeloLateralDeCuerpo(jugador);
        jugador.setLinearVelocity(veloFrente.x + veloLateral.x * DERRAPE, veloFrente.y + veloLateral.y * DERRAPE);
        nuevAcc = 0;
    }

}
