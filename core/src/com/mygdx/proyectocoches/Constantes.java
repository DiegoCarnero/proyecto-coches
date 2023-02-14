package com.mygdx.proyectocoches;

import com.badlogic.gdx.math.Vector2;

public class Constantes {

    private Constantes(){}

    /**
     * Pixeles por metro para determinar la escala del mundo Box2D
     */
    public static final float PPM = 50.0f;
    /**
     * Densidad del coche para calculos Box2D
     */
    public static final float DENSIDAD_COCHE = 1f;
    /**
     * Tamaño de los tiles del archivo del circuito
     */
    public static final float TILE_SIZE = 32F;
    /**
     * Nombre de la capa donde están los poligonos que marcan los limites del circuito
     */
    public static final String LAYER_MUROS = "Muros";
    /**
     * Nombre de la capa donde están los poligonos que representan la meta
     */
    public static final String LAYER_META = "Meta";
    /**
     * Nombre de la capa donde están los poligonos que delimitan los sectores del circuito
     */
    public static final String LAYER_CHECKP = "Checkpoint";
    /**
     * Nombre de la capa donde están los poligonos que marcan las rutas que puede tomar la IA
     */
    public static final String LAYER_PATH = "Path";

    /**
     * Velocidad máxima para la IA
     */
    public static final float MAX_VELO_IA = 50.0f;
    /**
     * Velocidad máxima para el jugador hacia delante
     */
    public static final float MAX_VELOCIDAD_FORW = 14.0f;
    /**
     * Velocidad máxima para el jugador hacia atrás
     */
    public static final float MAX_VELOCIDAD_BACK = 6.0f;
    /**
     * Factor por el que se restringe la velocidad lateral de un coche por debajo de una cierta velocidad lineal
     */
    public static final float DERRAPE_BAJO = 0.5f;
    /**
     * Factor por el que se restringe la velocidad lateral de un coche por encima de una cierta velocidad lineal
     */
    public static final float DERRAPE_ALTO = 0.9f;
    /**
     * Factor por el que se decelera el coche mientras el jugador está frenando activamente
     */
    public static final float DAMPING_FRENANDO = 0.7f;
    /**
     * Factor por el que se decelera el coche de forma natural
     */
    public static final float DAMPING_DEFAULT = 0.2F;

    /**
     * Categoría de cada tipo de Body
     */
    public static final short CAT_COCHE_JUG = 0x1;
    public static final short CAT_CIRCUITO_MUROS = 0x2;
    public static final short CAT_CIRCUITO_META = 0x4;
    public static final short CAT_COCHE_IA = 0x8;
    public static final short CAT_CIRCUITO_CHECKP1 = 0x10;
    public static final short CAT_CIRCUITO_CHECKP2 = 0x20;
    public static final short CAT_CIRCUITO_CHECKP3 = 0x30;
    public static final short CAT_COCHE_IA_SENSOR = 0x40;

    /**
     * Tamaño del sensor que guía a la IA
     */
    public static final float SENSOR_SIZE = 80f;

    /**
     * Nombre del archivo del circuito
     */
    public static final String TEST_LOOP_NAME = "test_loop";
    public static final String TRACK_1_NAME = "track_1";
    /**
     * Coordenadas para las posiciones en la parrilla de salida
     */
    public static final Vector2[] test_loop_vGrid = {new Vector2(2,-5),new Vector2(2f,-5.5f),new Vector2(2,-6f),
                                                    new Vector2(3,-5),new Vector2(3f,-5.5f),new Vector2(3,-6f),
                                                    new Vector2(4,-5),new Vector2(4f,-5.5f),new Vector2(4,-6f),
                                                    new Vector2(5,-5),new Vector2(5f,-5.5f),new Vector2(5,-6f),
                                                    new Vector2(6,-5),new Vector2(6f,-5.5f),new Vector2(6,-6f),
                                                    new Vector2(7,-5),new Vector2(7f,-5.5f),new Vector2(7,-6f),
                                                    new Vector2(8,-5),new Vector2(8f,-5.5f),new Vector2(8,-6f)};

    public static final Vector2[] track_1_vGrid = {new Vector2(10f,-7f),new Vector2(10,-6f),new Vector2(10,-6.5f),
            new Vector2(11f,-7f),new Vector2(11,-6f),new Vector2(11,-6.5f),
            new Vector2(12f,-7f),new Vector2(12,-6f),new Vector2(12,-6.5f),
            new Vector2(13f,-7f),new Vector2(13,-6f),new Vector2(13,-6.5f),
            new Vector2(14f,-7f),new Vector2(14,-6f),new Vector2(14,-6.5f),
            new Vector2(15f,-7f),new Vector2(15,-6f),new Vector2(15,-6.5f),

    };

    /**
     * Angulo al que deben orientarse los competidores en la parrilla
     */
    public static final float test_loop_ang = -90;
    public static final float track_1_ang = -90;
    /**
     * Número de rutas en el circuito
     */
    public static final int TEST_LOOP_PATHS = 5;
    public static final int TRACK_1_PATHS = 5;

    /**
     * Tipo de sonido que debe reproducirse según la velocidad del coche
     */
    public static final int TELE_ACC = 1;
    public static final int TELE_EMBRAG = 2;
    public static final int TELE_MEDIO = 4;
    public static final int TELE_MAX = 5;
    public static final int TELE_PARADO = 0;

    /**
     * Valores para renderizar test_loop
     */
    public static final float LOOP_CENTER_X = 0.25f;
    public static final float LOOP_CENTER_Y = -0.9f;
    public static final float LOOP_ESCALA = 27f;

    /**
     * Valores para renderizar track_1
     */
    public static final float TRACK_1_CENTER_X = 1.5f;
    public static final float TRACK_1_CENTER_Y = 19f;
    public static final float TRACK_1_ESCALA = 66f;
}
