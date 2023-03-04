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
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.gamemodes.RaceManager;

import java.util.Locale;

/**
 * Interfaz con la informacion del modo de juego carrera
 */
public class RaceOsd implements Screen {

    /**
     * Stage donde se ponen los Actores de esta interfaz
     */
    private final Stage UIStage;
    /**
     * Gestor de logica del modo de juego carrera
     */
    private final RaceManager rm;
    /**
     * Texto mostrando la clasificacion de competidores
     */
    private final Label lblLista;
    /**
     * Texto mostrando la posicion actual de jugador
     */
    private final Label lblPosicion;
    /**
     * Texto mostrando la vuelta actual del jugador
     */
    private final Label lblVuelta;
    /**
     * Texto del final de la partida
     */
    private final Label lblFin;
    /**
     * Texto mostrando la cuenta atras antes de que empieze la carrera
     */
    private final Label lblCountdown;
    /**
     * Bundle de texto localizado
     */
    private final I18NBundle locale;
    /**
     * AssetManagercon los archivos necesarios ya cargados
     */
    private AssetManager am;

    /**
     * Interfaz con la informacion del modo de juego carrera
     *
     * @param skin skin para la lista de competidores
     * @param rm control de la logica del modo de juego carrera
     * @param am AssetManager con los archivos necesarios ya cargados
     */
    public RaceOsd(Skin skin, RaceManager rm, AssetManager am) {
        this.am = am;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        locale = am.get("locale/locale");

        UIStage = new Stage(new ScreenViewport());
        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);
        this.rm = rm;

        this.lblFin = new Label("", labelStyle);
        lblFin.setAlignment(1);
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

        this.lblLista = new Label("lista\nlista2",skin);
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

    /**
     * Called when this screen becomes the current screen for a Game
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     * Actualiza las posiciones
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

            String msg = locale.get("osd.fin");
            if (locale.getLocale() == Locale.ENGLISH) {
                msg += " " + locale.get("osd.simboloPos") + "" + rm.getPosJugador();
            } else {
                msg += " " + rm.getPosJugador() + locale.get("osd.simboloPos");
            }

            lblFin.setText(msg);
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

    /**
     * @param width ancho pantalla en pixeles
     * @param height alto pantalla en pixeles
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
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
