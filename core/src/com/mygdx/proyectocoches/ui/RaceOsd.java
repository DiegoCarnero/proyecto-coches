package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.gamemodes.RaceManager;

public class RaceOsd implements Screen {

    private final Stage UIStage;
    private final RaceManager rm;
    private Label lblLista;
    private Label lblPosicion;
    private Label lblVuelta;
    private Label lblFin;

    private Label lblCountdown;

    public RaceOsd(Skin skin, RaceManager rm) {
        UIStage = new Stage(new ScreenViewport());
        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);
        this.rm = rm;

        this.lblFin = new Label("GO", skin);
        lblFin.setPosition(screenW / 2f, 0);
        lblFin.setVisible(false);

        this.lblCountdown = new Label("GO", skin);
        lblCountdown.setPosition(screenW / 2f, 0);

        this.lblLista = new Label("lista\nlista2", skin);
        lblLista.setPosition(0, 0);

        this.lblPosicion = new Label("Pos 0/0", skin);
        lblPosicion.setPosition(screenW / 2f, -9 * screenH / 20f);

        this.lblVuelta = new Label("Vuelta 0/0", skin);
        lblVuelta.setPosition(screenW / 2f, -8 * screenH / 20f);

        UIStage.addActor(lblLista);
        UIStage.addActor(lblPosicion);
        UIStage.addActor(lblVuelta);
        UIStage.addActor(lblCountdown);
        UIStage.addActor(lblFin);
    }

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

        rm.update();
        if (rm.isJugadorAcabo()){
            lblFin.setText("Has acabado "+rm.getPosJugador());
        }
        lblVuelta.setText("Vuelta " + rm.getVueltaJugador() + "/" + rm.getvVueltas());
        lblPosicion.setText("Pos " + rm.getPosJugador() + "/" + rm.getnCompetidores());
        lblLista.setText(rm.toString());
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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        UIStage.dispose();
    }
}
