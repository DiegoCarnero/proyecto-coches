package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

public class PauseMenu extends Actor {

    private final ArrayList<Actor> compPausa = new ArrayList<>();
    private final Button btnContinua;
    private final Button btnCameraMode;
    private final Label lblCameroMode;
    private final Button btnPausa;
    private boolean paused;
    private final String[] camModes = {"Cerca", "Lejos", "Dinamica"};
    private int cont = 0;

    public PauseMenu(int modo, Skin skin) {

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        btnContinua = new TextButton("Continuar", skin);
        btnContinua.setSize(screenW / 10f, screenH / 10f);
        btnContinua.setPosition(screenW / 2f - screenW / 20f, 0);
        btnContinua.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                paused = !paused;
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                return true;
            }
        });
        btnContinua.setVisible(false);

        lblCameroMode = new Label(camModes[cont], skin);
        lblCameroMode.setSize(screenW / 10f, screenH / 10f);
        lblCameroMode.setPosition(screenW / 2f - screenW / 20f, -screenH / 10f);
        lblCameroMode.setTouchable(Touchable.disabled);
        lblCameroMode.setAlignment(1);
        lblCameroMode.setVisible(false);

        btnCameraMode = new Button(skin);
        btnCameraMode.setSize(screenW / 10f, screenH / 10f);
        btnCameraMode.setPosition(screenW / 2f - screenW / 20f, -screenH / 10f);
        btnCameraMode.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cont = cont == 2 ? 0 : cont + 1;
                lblCameroMode.setText(camModes[cont]);
                return true;
            }
        });
        btnCameraMode.setVisible(false);

        btnPausa = new TextButton("||", skin);
        btnPausa.setSize(screenH / 10f, screenH / 10f);
        btnPausa.setPosition(0, screenH / 2f - screenH / 10f);
        btnPausa.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                paused = !paused;
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                return true;
            }
        });

        compPausa.add(btnContinua);
        compPausa.add(btnCameraMode);
        compPausa.add(lblCameroMode);

    }

    public int getCamMode() {
        return cont;
    }

    public ArrayList<Actor> getCompPausa() {
        return compPausa;
    }

    public Button getBtnPausa() {
        return btnPausa;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

}
