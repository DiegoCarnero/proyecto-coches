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

    private float PPM = 50;

    private Stage UIStage;

    private InputMultiplexer multiplexer;

    private Slider accSlider;
    private Slider steerSlider;
    private Label accLabel;
    private Label steerLabel;

    private Skin skin;


    public TestOsd(Game miGame){

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW/2, 0, 0);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Gdx.app.log("slider",skin.getDrawable("default-slider").getBottomHeight()+"");

        accLabel = new Label("acc",skin,"default");
        accLabel.setPosition(screenW/2,screenH/3);
        steerLabel = new Label("steer",skin,"default");
        steerLabel.setPosition(screenW/20,screenH/3);
        accSlider = new Slider(0F,100F, (float) 0.1,false, skin);

        accSlider.setHeight(screenH*0.1f);
        accSlider.setWidth(screenW*0.4f);
        accSlider.setPosition(screenW/2,screenH/4);

        accSlider.addListener(new InputListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Gdx.app.log("acc-dragged",accSlider.getValue()+"");
                accLabel.setText(String.format("%.2f",accSlider.getValue()));
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
        steerSlider.setHeight(screenH*0.1f);
        steerSlider.setWidth(screenW*0.4f);
        steerSlider.setPosition(screenW/20,screenH/4);

        steerSlider.addListener(new InputListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Gdx.app.log("steer-dragged",pointer+" "+x+" "+y);
                steerLabel.setText(String.format("%.2f",steerSlider.getValue()));
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

    public InputMultiplexer getMultiplexer(){
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
