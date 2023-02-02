package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;

public class PauseMenu extends Actor {

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    private ArrayList<Actor> compPausa;
    private Button btnContinua;
    private Button btnSalir;
    private Button btnCameraMode;
    private boolean isPaused;

    public PauseMenu(int modo){

    }



}
