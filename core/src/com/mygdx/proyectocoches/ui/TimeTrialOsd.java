package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;

public class TimeTrialOsd implements Screen {

    private final Stage UIStage;
    private final Label lblSector1;
    private final Label lblSector2;
    private final Label lblSector3;
    private final Label lblVueltaActual;
    private final Label lblMejorVuelta;
    private final TimeTrialManager ttm;
    private AssetManager am;

    public TimeTrialOsd(Skin skin, TimeTrialManager ttm, AssetManager am) {

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
        lblSector1.setPosition(7 * screenW / 15f, -screenH / 3f);
        lblSector2 = new Label("Sector2", labelStyle);
        lblSector2.setPosition(7 * screenW / 15f, -screenH / 3f - 20);
        lblSector3 = new Label("Sector3", labelStyle);
        lblSector3.setPosition(7 * screenW / 15f, -screenH / 3f - 40);
        lblVueltaActual = new Label("overallTime", labelStyle);
        lblVueltaActual.setPosition(7 * screenW / 15f, -screenH / 3f - 60);
        lblMejorVuelta = new Label("mejorVuelta", labelStyle);
        lblMejorVuelta.setPosition(7 * screenW / 15f, -screenH / 3f - 80);

        UIStage.addActor(lblSector1);
        UIStage.addActor(lblSector2);
        UIStage.addActor(lblSector3);
        UIStage.addActor(lblVueltaActual);
        UIStage.addActor(lblMejorVuelta);
    }

    @Override
    public void show() {

    }

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