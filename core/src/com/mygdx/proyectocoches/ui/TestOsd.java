package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestOsd extends ApplicationAdapter implements Screen{

    private float PPM = 50;

    private Stage stage;
    private Viewport vp;
    private Table table;

    private Slider accSlider;
    private Slider steerSlider;
    private Label accLabel;
    private Label steerLabel;

    private TextureAtlas tAtlas;
    private Skin skin;


    public TestOsd(){

        vp = new FitViewport(800F/PPM,480F/PPM, new OrthographicCamera());
        stage = new Stage(vp);

        table = new Table();
        table.top();
        table.setFillParent(true);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        accLabel = new Label("A",skin,"default");
        steerLabel = new Label("B",skin,"default");
        accSlider = new Slider(0F,100F, (float) 0.1,true, skin);
        accSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Slider slider = (Slider) actor;

                accLabel.setText((int) slider.getValue());

            }
        });
        steerSlider = new Slider(0F,100F, (float) 0.1,true, skin);
        steerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Slider slider = (Slider) actor;

                steerLabel.setText((int) slider.getValue());

            }
        });

        table.add(steerSlider).expandX().padTop(10);
        table.add(accSlider).expandX().padTop(10);
        table.row();
        table.add(steerLabel).expandX();
        table.add(accLabel).expandX();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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

    }
}
