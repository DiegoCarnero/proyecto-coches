package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;

/**
 * Interfaz con la informacion del modo de juego contrarreloj
 */
public class TimeTrialOsd implements Screen {

    /**
     * Stage donde se ponen los Actores de esta interfaz
     */
    private final Stage UIStage;
    /**
     * Texto con el tiempo en el primer sector
     */
    private final Label lblSector1;
    /**
     * Texto con el tiempo en el segundo sector
     */
    private final Label lblSector2;
    /**
     * Texto con el tiempo en el tercer sector
     */
    private final Label lblSector3;
    /**
     * Texto con el tiempo totalde la vuelta actual
     */
    private final Label lblVueltaActual;
    /**
     * Texto con el mejor tiempo de la sesion
     */
    private final Label lblMejorVuelta;
    /**
     * Gestor de logica del modo de juego contrarreloj
     */
    private final TimeTrialManager ttm;
    /**
     * AssetManager con los archivos necesarios ya cargados
     */
    private AssetManager am;

    /**
     * Interfaz con la informacion del modo de juego contrarreloj
     *
     * @param ttm Gestor de logica del modo de juego contrarreloj
     * @param am  AssetManager con los archivos necesarios ya cargados
     */
    public TimeTrialOsd(TimeTrialManager ttm, AssetManager am) {

        this.am = am;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        UIStage = new Stage(new ScreenViewport());
        this.ttm = ttm;
        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        lblSector1 = new Label("Sector1", labelStyle);
        lblSector1.setPosition(6 * screenW / 15f, -screenH / 3f);
        lblSector2 = new Label("Sector2", labelStyle);
        lblSector2.setPosition(6 * screenW / 15f, -screenH / 3f - 0.5f * (0.041167f * screenW));
        lblSector3 = new Label("Sector3", labelStyle);
        lblSector3.setPosition(6 * screenW / 15f, -screenH / 3f - (0.041167f * screenW));
        lblVueltaActual = new Label("overallTime", labelStyle);
        lblVueltaActual.setPosition(6 * screenW / 15f, -screenH / 3f - 1.5f * (0.041167f * screenW));
        lblMejorVuelta = new Label("mejorVuelta", labelStyle);
        lblMejorVuelta.setPosition(6 * screenW / 15f, -screenH / 3f - 2f * (0.041167f * screenW));

        UIStage.addActor(lblSector1);
        UIStage.addActor(lblSector2);
        UIStage.addActor(lblSector3);
        UIStage.addActor(lblVueltaActual);
        UIStage.addActor(lblMejorVuelta);
    }

    /**
     * Called when this screen becomes the current screen for a Game
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     * Actualiza los tiempso
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ttm.update(delta);
        lblSector1.setText("Sector1: " + ttm.gettSector1Str());
        lblSector2.setText("Sector2: " + ttm.gettSector2Str());
        lblSector3.setText("Sector3: " + ttm.gettSector3Str());
        lblVueltaActual.setText("Actual: " + ttm.gettVueltaActualStr());
        lblMejorVuelta.setText("Mejor: " + ttm.gettVueltaMejorStr());
        UIStage.act();
        UIStage.draw();
    }

    /**
     * @param width  ancho pantalla en pixeles
     * @param height alto pantalla en pixeles
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
     * Called when this screen is no longer the current screen for a Game
     */
    @Override
    public void hide() {

    }


    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        UIStage.dispose();
    }

}