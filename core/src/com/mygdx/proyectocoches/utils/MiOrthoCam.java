package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Camara con proyección ortográfica
 */
public class MiOrthoCam extends OrthographicCamera {

    private static final float ZOOM_BAJO = 0.4f;
    private static final float ZOOM_ALTO = 0.8f;
    private static final float VELO_LIM_BAJO = 3f;
    private static final float VELO_LIM_ALTO = 5f;

    private float ultimaVelo;

    /**
     * Crea un new objeto OrthographicCamera, con opción añadida de controlar el zoom según la velocidad de un cuerpo
     */
    public MiOrthoCam() {
        super();
        this.zoom = ZOOM_BAJO;
    }

    /**
     * Manipula el zoom de esta OrthographicCamera según la velocidad de un cuerpo pasado por parámetro.
     * Limites superioes e inferiores determinan a partir de qué velocidades el zoom permance constante.
     * @param jugador cuerpo cuya velocidad se usará para los cálculos
     */
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
