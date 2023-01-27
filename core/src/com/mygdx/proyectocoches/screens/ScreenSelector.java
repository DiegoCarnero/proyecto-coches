package com.mygdx.proyectocoches.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.ProyectoCOCHES;


public class ScreenSelector implements Screen {

    private Stage stage;
    private final String[] screens = new String[]{"TestDrive", "TestIA","TestRace"};
    private String seleccion;
    private InputMultiplexer multiplexer;
    private Skin skin;
    private Game pc;

    public ScreenSelector(final ProyectoCOCHES pc) {
        this.pc = pc;
        stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        int screenW, screenH;

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        TextButton btn1 = new TextButton(screens[0], this.skin);
        btn1.setHeight(screenH);
        btn1.setWidth(screenW * 0.3f);
        btn1.setPosition(0, 0);
        btn1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pc.setScreen(new TestDrive(pc, skin));
                return true;
            }
        });

        TextButton btn2 = new TextButton(screens[1], this.skin);
        btn2.setHeight(screenH);
        btn2.setWidth(screenW * 0.3f);
        btn2.setPosition(screenW / 3f, 0);
        btn2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pc.setScreen(new TestIA(pc,skin));
                return true;
            }
        });

        TextButton btn3 = new TextButton(screens[2], this.skin);
        btn3.setHeight(screenH);
        btn3.setWidth(screenW * 0.3f);
        btn3.setPosition(2 * screenW / 3f, 0);
        btn3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pc.setScreen(new TestRace(pc,skin));
                return true;
            }
        });


        stage.addActor(btn1);
        stage.addActor(btn2);
        stage.addActor(btn3);
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        stage.act();
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
        stage.dispose();
    }
}
