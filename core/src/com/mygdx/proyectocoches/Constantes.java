package com.mygdx.proyectocoches;

import com.badlogic.gdx.math.Vector2;

public class Constantes {

    private Constantes(){}

    public static final float PPM = 50.0f;
    public static final float DENSIDAD_COCHE = 1f;
    public static final float TILE_SIZE = 32F;
    public static final String LAYER_MUROS = "Muros";
    public static final String LAYER_GRID = "Grid";
    public static final float MAX_VELOCIDAD_FORW = 13.0f;
    public static final float MAX_VELOCIDAD_BACK = 4.0f;
    public static final float DERRAPE_BAJO = 0.5f;
    public static final float DERRAPE_ALTO = 0.9f;
    public static final float DAMPING_FRENANDO = 5.0f;
    public static final float DAMPING_DEFAULT = 0.7F;

    public static final short CAT_COCHE_JUG = 0x3;
    public static final short CAT_CIRCUITO_MUROS = 0x3;

    public static final Vector2[] test_loop_vGrid = {new Vector2(0,-5)};
    public static final float test_loop_ang = -90;
}
