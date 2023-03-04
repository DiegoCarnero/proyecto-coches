package com.mygdx.proyectocoches.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.utils.GameSettings;

/**
 * Pantalla de carga
 */
public class LoadingScreen implements Screen {

    /**
     * Escena que contiene los actores
     */
    private final Stage stage;
    /**
     * Configuracion para el evento que se va a cargar
     */
    private final GameSettings gs;
    /**
     * Clase base del proyecto
     */
    private final Game g;
    /**
     * skin para la interfaz
     */
    private final Skin s;
    /**
     * Comprueba si ya se ha lanzado la nueva pantalla
     */
    private boolean b = true;
    /**
     * Cuenta cuando comenzar a cargar la siguiente pantalla
     */
    private float contDelta = 0;
    /**
     * AssetManager para
     */
    private final AssetManager am;
    /**
     * Sprites animados para el icono de carga
     */
    private final Sprite[] sprites = new Sprite[4];
    /**
     * Indice del sprite del icono de carag
     */
    private int ndxSprite = 0;
    /**
     * Batch usado para renderizar las texturas
     */
    private final SpriteBatch batch;

    /**
     * Pantalla de carga
     * @param am debe tener la fuente para la pantalla de carga y sprites para el icono de carga
     * @param juego Clase base del proyecto
     * @param skin skin para la interfaz
     * @param gs configuracion para el evento que se va a cargar
     */
    public LoadingScreen(AssetManager am, Game juego, Skin skin, GameSettings gs) {
        this.am = am;
        this.gs = gs;
        this.g = juego;
        this.s = skin;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        I18NBundle locale = am.get("locale/locale");
        BitmapFont font = am.get("fonts/Designer.otf");

        sprites[0] = new Sprite((Texture) am.get("ui/loading_spin_1.png"));
        sprites[1] = new Sprite((Texture) am.get("ui/loading_spin_2.png"));
        sprites[2] = new Sprite((Texture) am.get("ui/loading_spin_3.png"));
        sprites[3] = new Sprite((Texture) am.get("ui/loading_spin_4.png"));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label lblLoading = new Label(locale.get("cargando") , labelStyle);
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
        contDelta += delta;
        if (contDelta > 2 && b) {
            if (gs.getModo() == 0) {
                g.setScreen(new TestRace(g, s, gs, am));
            } else if (gs.getModo() == 1) {
                g.setScreen(new TestDrive(g, s, gs, am));
            }
            b = false;
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        Sprite s = sprites[ndxSprite];
        s.setBounds(250, 0, 50, 50);
        batch.begin();
        s.draw(batch);
        ndxSprite = ndxSprite == 3 ? 0 : ndxSprite + 1;
        batch.end();
    }

    /**
     * @param width ancho de pantalla en pixeles
     * @param height alto de pantalla en pixeles
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
        am.dispose();
        batch.dispose();
        stage.dispose();
    }

}
