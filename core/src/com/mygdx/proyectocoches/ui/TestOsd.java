package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestOsd implements Screen {

    private float PPM = 10;

    private Viewport vp;

    private Stage gameStage;
    private Stage UIStage;

    private InputMultiplexer multiplexer;

    private Slider accSlider;
    private Slider steerSlider;
    private Label accLabel;
    private Label steerLabel;

    private TextureAtlas tAtlas;
    private Skin skin;


    public TestOsd(Game miGame){

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        accLabel = new Label("A",skin,"default");
        steerLabel = new Label("B",skin,"default");
        accSlider = new Slider(0F,100F, (float) 0.1,false, skin);

//        accSlider.setAnimateInterpolation(Interpolation.smooth);
        accSlider.setHeight(Gdx.graphics.getHeight()*0.4f);
        accSlider.setWidth(Gdx.graphics.getWidth()*0.4f);
        accSlider.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        accSlider.addListener(new InputListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Gdx.app.log("acc-dragged",pointer+" "+x+" "+y);
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("acc-up",pointer+" "+x+" "+y);
                super.touchUp(event,x,y,pointer,button);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("acc-down",pointer+" "+x+" "+y);

                super.touchUp(event,x,y,pointer,button);
                return true;
            }
            });

        steerSlider = new Slider(0F,100F, (float) 0.1,false, skin);

//        steerSlider.setAnimateInterpolation(Interpolation.smooth);
        steerSlider.setHeight(Gdx.graphics.getHeight()*0.4f);
        steerSlider.setWidth(Gdx.graphics.getWidth()*0.4f);
        steerSlider.setPosition(Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/2);

        steerSlider.addListener(new InputListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Gdx.app.log("steer-dragged",pointer+" "+x+" "+y);
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("steer-up",pointer+" "+x+" "+y);
                super.touchUp(event,x,y,pointer,button);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("steer-down",pointer+" "+x+" "+y);

                super.touchUp(event,x,y,pointer,button);
                return true;
            }
        });

        UIStage.addActor(accSlider);
        UIStage.addActor(steerSlider);
        UIStage.addActor(accLabel);
        UIStage.addActor(steerLabel);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
