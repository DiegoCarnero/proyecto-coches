package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;

public class MiOrthoCam extends OrthographicCamera {

    private static final float ZOOM_BAJO = 0.1f;
    private static final float ZOOM_ALTO = 0.4f;
    private static final float VELO_LIM_BAJO = 2f;
    private static final float VELO_LIM_ALTO = 3f;

    private float ultimaVelo;

    public MiOrthoCam() {
        super();
        this.zoom = ZOOM_BAJO;
    }

    public void AdjustaZoomPorVelo(Body jugador) {

        float velo = jugador.getLinearVelocity().len();
        float variacionMin = 0.05f;
        boolean variacionMinSuperada;

        variacionMinSuperada = Math.abs(ultimaVelo - velo) > variacionMin;

        if(variacionMinSuperada && ultimaVelo > velo){
            this.zoom -= 0.02f;
        } else if (variacionMinSuperada && velo > VELO_LIM_BAJO) {
            this.zoom += 0.01f;
        }

        if (this.zoom < ZOOM_BAJO || velo < VELO_LIM_BAJO) {
            this.zoom = ZOOM_BAJO;
        } else if (this.zoom > ZOOM_ALTO || velo > VELO_LIM_ALTO) {
            this.zoom = ZOOM_ALTO;
        }
        ultimaVelo = velo;
    }

}
