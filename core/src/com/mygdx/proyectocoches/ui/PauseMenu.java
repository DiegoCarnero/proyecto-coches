package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.proyectocoches.ProyectoCOCHES;
import com.mygdx.proyectocoches.screens.ScreenSelector;

import java.util.ArrayList;

public class PauseMenu extends Actor {

    private final ArrayList<Actor> compPausa = new ArrayList<>();
    private final ArrayList<Actor> compRecords = new ArrayList<>();
    private final Button btnContinua;
    private final Button btnCameraMode;
    private final Button btnSalir;
    private final Button btnRecords;
    private final Label lblCameroMode;
    private final Button btnPausa;
    private final RecordsMenu mRecords;
    private boolean paused;

    public boolean isRecordShowing() {
        return recordShowing;
    }

    public void setRecordShowing(boolean recordShowing) {
        this.recordShowing = recordShowing;
    }

    private boolean recordShowing = false;
    private final String[] camModes = {"Cerca", "Lejos", "Dinamica"};
    private int cont = 0;

    public PauseMenu(final Game game, int modo, final Skin skin, String nomCircuito) {

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        int vertOffset = 0;

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

        vertOffset++;

        lblCameroMode = new Label(camModes[cont], skin);
        lblCameroMode.setSize(screenW / 10f, screenH / 10f);
        lblCameroMode.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        lblCameroMode.setTouchable(Touchable.disabled);
        lblCameroMode.setAlignment(1);
        lblCameroMode.setVisible(false);

        btnCameraMode = new Button(skin);
        btnCameraMode.setSize(screenW / 10f, screenH / 10f);
        btnCameraMode.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        btnCameraMode.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cont = cont == 2 ? 0 : cont + 1;
                lblCameroMode.setText(camModes[cont]);
                return true;
            }
        });
        btnCameraMode.setVisible(false);

        if (modo == 1) {
            vertOffset++;
        }

        this.mRecords = new RecordsMenu(nomCircuito, skin);

        btnRecords = new TextButton("Records", skin);
        btnRecords.setSize(screenW / 10f, screenH / 10f);
        btnRecords.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        btnRecords.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                for (Actor a : compPausa) {
                    a.setVisible(false);
                }
                mRecords.setShowing(true);
                mRecords.getBackButton().setVisible(true);
                return true;
            }
        });
        btnRecords.setVisible(false);

        vertOffset++;

        btnSalir = new TextButton("Salir", skin);
        btnSalir.setSize(screenW / 10f, screenH / 10f);
        btnSalir.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        btnSalir.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ScreenSelector(((ProyectoCOCHES) game)));
                return true;
            }
        });
        btnSalir.setVisible(false);

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
                for (Actor a : compRecords) {
                    a.setVisible(false);
                }
                return true;
            }
        });

        compPausa.add(btnContinua);
        compPausa.add(btnCameraMode);
        compPausa.add(lblCameroMode);
        compPausa.add(btnSalir);
        compRecords.addAll(mRecords.getCompRecords());
        // 1 = contrarreloj
        if (modo == 1) {
            compPausa.addAll(mRecords.getCompRecords());
            compPausa.add(btnRecords);
        }
        mRecords.getBackButton().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                for (Actor a : compRecords) {
                    a.setVisible(!paused);
                }
                return true;
            }
        });
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
