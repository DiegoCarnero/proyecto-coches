package com.mygdx.proyectocoches.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.utils.PlayerInput;

public class AiTestOsd implements Screen, PlayerInput {

    private boolean acelerando = false;
    private boolean adelante = true;
    private boolean frenando = false;
    private Stage UIStage;

    private InputMultiplexer multiplexer;

    private Button btnD;
    private Button btnR;
    private Button btnB;
    private Button btnA;

    public AiTestOsd(Game miGame, Skin skin) {

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        btnD = new TextButton("D",skin);
        btnD.setHeight(screenH * 0.1f);
        btnD.setWidth(screenH * 0.1f);
        btnD.setPosition(screenW / 2f, screenH / 4f);
        btnD.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                adelante = true;
                return true;
            }
        });

        btnR = new TextButton("R",skin);
        btnR.setHeight(screenH * 0.1f);
        btnR.setWidth(screenH * 0.1f);
        btnR.setPosition(screenW / 2f + screenH * 0.13f, screenH / 4f);
        btnR.setChecked(true);
        btnR.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                adelante = false;
                return true;
            }
        });

        btnB = new TextButton("B",skin);
        btnB.setHeight(screenH * 0.1f);
        btnB.setWidth(screenH * 0.1f);
        btnB.setPosition(screenW / 2f + screenH * 0.4f, screenH / 4f);
        btnB.setChecked(true);
        btnB.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                acelerando = false;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                frenando = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });


        btnA = new TextButton("A",skin);
        btnA.setHeight(screenH * 0.1f);
        btnA.setWidth(screenH * 0.1f);
        btnA.setPosition(screenW / 2f + screenH * 0.6f, screenH / 4f);
        btnA.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                acelerando = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        UIStage.addActor(btnD);
        UIStage.addActor(btnR);
        UIStage.addActor(btnB);
        UIStage.addActor(btnA);
    }

    public boolean isAcelerando() {
        return acelerando;
    }

    @Override
    public float getSteerValue() {
        return 0;
    }

    @Override
    public float getAccValue() {
        return 0;
    }

    public boolean isAdelante(){
        return adelante;
    }

    public boolean isFrenando() {
        return frenando;
    }


    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

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
