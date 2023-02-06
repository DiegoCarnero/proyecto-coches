package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.ui.RecordsMenu;

import java.util.ArrayList;

public class MainMenu implements Screen {

    private final ArrayList<Actor> compMain = new ArrayList<>();
    private final ArrayList<Actor> compEvento = new ArrayList<>();
    private final Stage stage;
    private final String[] screens = new String[]{"Evento", "Records", "Ajustes", "Creditos"};
    private final InputMultiplexer multiplexer;
    private final RecordsMenu mRecords;

    public MainMenu(final Game miGame, final Skin skin) {

        stage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        int screenW, screenH;

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        stage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        TextButton btn1 = new TextButton(screens[0], skin);
        btn1.setHeight(screenH * 0.5f);
        btn1.setWidth(screenW * 0.5f);
        btn1.setPosition(0, -screenH / 2f);
        btn1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        this.mRecords = new RecordsMenu("", skin);
        TextButton btn2 = new TextButton(screens[1], skin);
        btn2.setHeight(screenH * 0.5f);
        btn2.setWidth(screenW * 0.5f);
        btn2.setPosition(screenW / 2f, -screenH / 2f);
        btn2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                mRecords.setShowing(true);
                return true;
            }
        });

        TextButton btn3 = new TextButton(screens[2], skin);
        btn3.setHeight(screenH * 0.5f);
        btn3.setWidth(screenW * 0.5f);
        btn3.setPosition(screenW / 2f, 0);
        btn3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        TextButton btn4 = new TextButton(screens[3], skin);
        btn4.setHeight(screenH * 0.5f);
        btn4.setWidth(screenW * 0.5f);
        btn4.setPosition(0, 0);
        btn4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        compMain.add(btn1);
        compMain.add(btn2);
        compMain.add(btn3);
        compMain.add(btn4);
        compMain.addAll(mRecords.getCompRecords());

        mRecords.getBackButton().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(true);
                }
                for (Actor a : mRecords.getCompRecords()) {
                    a.setVisible(false);
                }
                return true;
            }
        });

        for (Actor a : compMain) {
            stage.addActor(a);
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     *
     */
    @Override
    public void pause() {

    }

    /**
     *
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
