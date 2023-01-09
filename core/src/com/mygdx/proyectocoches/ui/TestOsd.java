package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TestOsd implements Screen {

    private float PPM = 50;

    private boolean accelerating = false;
    private Stage UIStage;

    private InputMultiplexer multiplexer;

    private Slider accSlider;
    private Slider steerSlider;

    private Skin skin;


    public TestOsd(Game miGame) {

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        accSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        accSlider.setHeight(screenH * 0.2f);
        accSlider.setWidth(screenW * 0.4f);
        accSlider.setPosition(screenW/2f,screenH/3f);

        accSlider.addListener(new InputListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                accelerating = true;
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                accSlider.setValue(0.0f);
                accelerating = false;
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                accelerating = true;
                super.touchUp(event, x, y, pointer, button);
                return true;
            }
        });

        steerSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        steerSlider.setHeight(screenH * 0.2f);
        steerSlider.setWidth(screenW * 0.4f);
        steerSlider.setPosition(screenW/20f,screenH/3f);
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
    }

    public boolean isAccelerating(){
        return accelerating;
    }

    public float getAccValue() {
        return accSlider.getValue();
    }

    public float getSteerValue() {
        return steerSlider.getValue();
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
