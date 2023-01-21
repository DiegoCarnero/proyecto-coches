package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private final TimeTrialManager rlm;

    public TimeTrialOsd(Skin skin, TimeTrialManager rlm) {

        UIStage = new Stage(new ScreenViewport());
        this.rlm = rlm;
        int screenW,screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        lblSector1 = new Label("Sector1", skin);
        lblSector1.setPosition(screenW/2f, -screenH/3f);
        lblSector2 = new Label("Sector2", skin);
        lblSector2.setPosition(screenW/2f, -screenH/3f-20);
        lblSector3 = new Label("Sector3", skin);
        lblSector3.setPosition(screenW/2f, -screenH/3f-40);
        lblVueltaActual = new Label("overallTime", skin);
        lblVueltaActual.setPosition(screenW/2f, -screenH/3f-60);
        lblMejorVuelta = new Label("mejorVuelta", skin);
        lblMejorVuelta.setPosition(screenW/2f, -screenH/3f-80);

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
        lblSector1.setText("Sector1: "+rlm.gettSector1Str());
        lblSector2.setText("Sector2: "+rlm.gettSector2Str());
        lblSector3.setText("Sector3: "+rlm.gettSector3Str());
        lblVueltaActual.setText("Actual: "+rlm.gettVueltaActualStr());
        lblMejorVuelta.setText("Mejor: "+rlm.gettVueltaMejorStr());
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