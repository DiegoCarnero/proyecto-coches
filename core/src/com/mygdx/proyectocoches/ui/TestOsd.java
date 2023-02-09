package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.utils.GameSettings;
import com.mygdx.proyectocoches.utils.PlayerInput;

import java.util.ArrayList;

public class TestOsd implements Screen, PlayerInput {

    private boolean acelerando = false;
    private boolean adelante = true;
    private boolean frenando = false;
    private final Stage UIStage;

    private final InputMultiplexer multiplexer;

    private final Slider accSlider;
    private final Slider steerSlider;

    private final Button btnD;
    private final Button btnR;
    private final Button btnB;

    private final PauseMenu mPausa;
    private final ArrayList<Actor> compControles = new ArrayList<>();

    public TestOsd(int modo, Game miGame, Skin skin, GameSettings gs) {

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        btnD = new TextButton("D", skin, "toggle");
        btnD.setHeight(screenH * 0.1f);
        btnD.setWidth(screenH * 0.1f);
        btnD.setPosition(14 * screenW / 15f, screenH / 4f);
        btnD.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                adelante = true;
                btnR.setChecked(false);
                return true;
            }
        });
        btnD.setChecked(true);

        btnR = new TextButton("R", skin, "toggle");
        btnR.setHeight(screenH * 0.1f);
        btnR.setWidth(screenH * 0.1f);
        btnR.setPosition(14 * screenW / 15f, screenH / 4f - screenH * 0.13f);
        btnR.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                adelante = false;
                btnD.setChecked(false);
                return true;
            }
        });

        btnB = new TextButton("B", skin);
        btnB.setHeight(screenH * 0.1f);
        btnB.setWidth(screenH * 0.1f);
        btnB.setPosition(14 * screenW / 15f, screenH / 4f - screenH * 0.4f);
        btnB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                frenando = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                frenando = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        accSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        accSlider.setHeight(screenH * 0.3f);
        accSlider.setWidth(screenW * 0.3f);
        accSlider.setPosition(13 * screenW / 20f, -screenH / 2f);

        accSlider.addListener(new InputListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                acelerando = true;
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                accSlider.setValue(0.0f);
                acelerando = false;
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                acelerando = true;
                super.touchUp(event, x, y, pointer, button);
                return true;
            }
        });

        steerSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        steerSlider.setHeight(screenH * 0.3f);
        steerSlider.setWidth(screenW * 0.3f);
        steerSlider.setPosition(screenW / 20f, screenH / -2f);
        steerSlider.setValue(50.0f);

        steerSlider.addListener(new InputListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                steerSlider.setValue(50.0f);
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                return true;
            }
        });

        UIStage.addActor(accSlider);
        UIStage.addActor(steerSlider);
        UIStage.addActor(btnD);
        UIStage.addActor(btnR);
        UIStage.addActor(btnB);

        this.mPausa = new PauseMenu(miGame, modo, skin, gs.getCircuito());
        UIStage.addActor(mPausa.getBtnPausa());

        for (Actor a : mPausa.getCompPausa()) {
            UIStage.addActor(a);
        }

        compControles.add(accSlider);
        compControles.add(steerSlider);
        compControles.add(btnD);
        compControles.add(btnR);
        compControles.add(btnB);

    }

    public PauseMenu getmPausa(){
        return mPausa;
    }

    public boolean isPaused() {
        return mPausa.isPaused();
    }

    public int camMode() {
        return mPausa.getCamMode();
    }

    public boolean isAcelerando() {
        return acelerando;
    }

    public boolean isAdelante() {
        return adelante;
    }

    public boolean isFrenando() {
        return frenando;
    }

    public float getAccValue() {
        return accSlider.getValue() / 100f;
    }

    public float getSteerValue() {
        return -(steerSlider.getValue() - 50.0f);
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    @Override
    public void show() {
        mPausa.setPaused(false);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        for (Actor a : compControles) {
            a.setVisible(!isPaused());
        }

        UIStage.act();
        UIStage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        UIStage.dispose();
    }
}
