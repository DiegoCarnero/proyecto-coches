package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.DAMPING_DEFAULT;
import static com.mygdx.proyectocoches.Constantes.DAMPING_FRENANDO;
import static com.mygdx.proyectocoches.Constantes.DERRAPE_ALTO;
import static com.mygdx.proyectocoches.Constantes.DERRAPE_BAJO;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_BACK;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
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
        float max_velo;
        int direccion;

        if (inputOsd.isAdelante()) {
            max_velo = MAX_VELOCIDAD_FORW;
            direccion = 1;
        } else {
            max_velo = MAX_VELOCIDAD_BACK;
            direccion = -1;
        }

        if (inputOsd.isFrenando()) {
            jugador.setLinearDamping(DAMPING_FRENANDO);
        } else {
            jugador.setLinearDamping(DAMPING_DEFAULT);

            if (inputOsd.isAcelerando()) {
                // normalizar a la velocidad maxima
                nuevAcc = direccion * inputOsd.getAccValue() * max_velo / 100f;
                v.set(0, nuevAcc * (max_velo * 0.01f));
            }
        }

        // normalizar  entre -2.5 y 2.5. Cambiar signo para que se corresponda drcha e izq en el control y el coche
        nuevSteer = -(inputOsd.getSteerValue() - 50.0f)  * 0.05f;

        if (nuevSteer == 0 || jugador.getLinearVelocity().len() < 0.1) {
            jugador.setAngularVelocity(0.0f);
        } else {
            jugador.setAngularVelocity(nuevSteer);
        }

        float velActual = jugador.getLinearVelocity().len();
        float maxVeloPorInput = inputOsd.getAccValue() * max_velo / 100f;

        if (velActual < maxVeloPorInput && velActual < max_velo && !v.isZero()) {
            jugador.applyForceToCenter(jugador.getWorldVector(v), true);
        }

        // derrape
        Vector2 veloFrente = VeloFrenteDeCuerpo(jugador);
        Vector2 veloLateral = VeloLateralDeCuerpo(jugador);
        float derrape;
        MassData b2dmd = jugador.getMassData();

        if(Math.abs(jugador.getAngularVelocity()) > 1.5f && (jugador.getLinearVelocity().len() > MAX_VELOCIDAD_FORW/2)){
            b2dmd.center.set(0,-4);
            derrape = DERRAPE_ALTO;
        } else {
            b2dmd.center.set(0,0);
            derrape = DERRAPE_BAJO;
        }

        jugador.setLinearVelocity(veloFrente.x + veloLateral.x * derrape, veloFrente.y + veloLateral.y * derrape);
        nuevAcc = 0;
    }

}
