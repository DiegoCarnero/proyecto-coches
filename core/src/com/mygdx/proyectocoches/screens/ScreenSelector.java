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
import com.mygdx.proyectocoches.utils.GameSettings;


public class ScreenSelector implements Screen {

    private Stage stage;
    private final String[] screens = new String[]{"TestDrive", "TestIA", "TestRace", "MainMenu"};
    private String seleccion;
    private InputMultiplexer multiplexer;
    private Skin skin;
    private Game pc;

    public ScreenSelector(final ProyectoCOCHES pc) {
        this.pc = pc;
        stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        final GameSettings gs = new GameSettings(10, "track_1", 0, 0, "AAA", 3);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        int screenW, screenH;

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        TextButton btn1 = new TextButton(screens[0], this.skin);
        btn1.setHeight(screenH * 0.5f);
        btn1.setWidth(screenW * 0.5f);
        btn1.setPosition(0, 0);
        btn1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                pc.setScreen(new LoadingScreen(pc, skin, gs, "loading"));
                return true;
            }
        });

        TextButton btn2 = new TextButton(screens[1], this.skin);
        btn2.setHeight(screenH * 0.5f);
        btn2.setWidth(screenW * 0.5f);
        btn2.setPosition(screenW / 2f, 0);
        btn2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                pc.setScreen(new TestIA(pc, skin,gs));
                return true;
            }
        });

        TextButton btn3 = new TextButton(screens[2], this.skin);
        btn3.setHeight(screenH * 0.5f);
        btn3.setWidth(screenW * 0.5f);
        btn3.setPosition(0, screenH / 2f);
        btn3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                pc.setScreen(new LoadingScreen(pc, skin, gs, "cargando"));
                return true;
            }
        });

        TextButton btn4 = new TextButton(screens[3], this.skin);
        btn4.setHeight(screenH * 0.5f);
        btn4.setWidth(screenW * 0.5f);
        btn4.setPosition(screenW / 2f, screenH / 2f);
        btn4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pc.setScreen(new MainMenu(pc, skin));
                return true;
            }
        });

        stage.addActor(btn1);
        stage.addActor(btn2);
        stage.addActor(btn3);
        stage.addActor(btn4);
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
