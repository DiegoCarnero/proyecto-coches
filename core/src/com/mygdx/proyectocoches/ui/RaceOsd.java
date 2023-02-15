package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.gamemodes.RaceManager;

public class RaceOsd implements Screen {

    private final Stage UIStage;
    private final RaceManager rm;
    private final Label lblLista;
    private final Label lblPosicion;
    private final Label lblVuelta;
    private final Label lblFin;

    private final Label lblCountdown;
    private float contGo;
    private AssetManager am;

    public RaceOsd(Skin skin, RaceManager rm, AssetManager am) {
        this.am = am;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        UIStage = new Stage(new ScreenViewport());
        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);
        this.rm = rm;

        this.lblFin = new Label("", labelStyle);
        lblFin.setPosition(screenW / 2f, 0);
        lblFin.setVisible(false);

        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Segment7Standard.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 50;
        param.shadowColor = Color.BLACK;
        param.shadowOffsetX = 1;
        param.shadowOffsetY = 1;
        BitmapFont font = ftfg.generateFont(param);
        ftfg.dispose();

        Label.LabelStyle lblStyleCountdown = new Label.LabelStyle();
        lblStyleCountdown.font = font;

        this.lblCountdown = new Label("GO", lblStyleCountdown);
        lblCountdown.setPosition(screenW / 2f, 0);
        lblCountdown.setVisible(false);
        lblCountdown.setFontScale(2f);
        lblCountdown.setAlignment(1);

        this.lblLista = new Label("lista\nlista2", labelStyle);
        lblLista.setPosition(0, 0);

        this.lblPosicion = new Label("Pos 0/0", labelStyle);
        lblPosicion.setPosition(screenW / 2f, -9 * screenH / 20f);

        this.lblVuelta = new Label("Vuelta 0/0", labelStyle);
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
    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {

        rm.update();
        if (rm.getCuentaAtras() > 0) {
            lblCountdown.setVisible(true);
            lblCountdown.setText(String.format("%.0f", rm.getCuentaAtras() + 1));
        } else if (rm.getCuentaAtras() < 0 && rm.getCuentaAtras() > -1.5) {
            lblCountdown.setText("GO");
        } else {
            lblCountdown.setVisible(false);
        }

        if (rm.isJugadorAcabo()) {
            lblFin.setVisible(true);
            lblFin.setText(String.format("%s %d", "Has terminado ", rm.getPosJugador()));
            lblLista.setVisible(false);
            lblPosicion.setVisible(false);
            lblVuelta.setVisible(false);
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
