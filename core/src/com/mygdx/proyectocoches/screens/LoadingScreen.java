package com.mygdx.proyectocoches.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.utils.GameSettings;

public class LoadingScreen implements Screen {

    private Stage stage;
    private GameSettings gs;
    private Game g;
    private Skin s;
    private boolean b = true;
    private AssetManager am;

    public LoadingScreen(AssetManager am, Game juego, Skin skin, GameSettings gs, String cargando) {
        this.am = am;
        this.gs = gs;
        this.g = juego;
        this.s = skin;
        stage = new Stage(new ScreenViewport());

        BitmapFont font = am.get("fonts/Designer.otf");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label lblLoading = new Label(cargando, labelStyle);
        lblLoading.setPosition(0, 0);

        stage.addActor(lblLoading);

    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (b) {
            if (gs.getModo() == 0) {
                g.setScreen(new TestRace(g, s, gs, am));
            } else if (gs.getModo() == 1) {
                g.setScreen(new TestDrive(g, s, gs, am));
            }
            b = false;
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
