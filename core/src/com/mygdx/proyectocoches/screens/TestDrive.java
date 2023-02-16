package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.LOOP_CENTER_X;
import static com.mygdx.proyectocoches.Constantes.LOOP_CENTER_Y;
import static com.mygdx.proyectocoches.Constantes.LOOP_ESCALA;
import static com.mygdx.proyectocoches.Constantes.PPM;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_CENTER_X;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_CENTER_Y;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_ESCALA;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.audio.AudioManager;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;
import com.mygdx.proyectocoches.entidades.Jugador;
import com.mygdx.proyectocoches.formas.Circuito;
import com.mygdx.proyectocoches.ui.TestOsd;
import com.mygdx.proyectocoches.ui.TimeTrialOsd;
import com.mygdx.proyectocoches.utils.ControllerInput;
import com.mygdx.proyectocoches.utils.GameSettings;
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.MiOrthoCam;
import com.mygdx.proyectocoches.utils.PlayerInput;
import com.mygdx.proyectocoches.utils.miContactListener;

public class TestDrive implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final MiOrthoCam miCam;
    private final Viewport miViewport;
    private final Jugador jugador;
    private final Circuito circuito;
    private final PlayerInput pi;
    private final TestOsd osd;
    private final TimeTrialOsd ttOsd;
    private final InputManager im;
    private final TimeTrialManager ttm;
    private final AudioManager am;
    private final AssetManager asM;
    private boolean init = true;

    private final float circuitoCenterX;
    private final float circuitoCenterY;
    private final float circuitoEscala;

    private final Skin skin;

    public TestDrive(Game juego, Skin skin, GameSettings gs, AssetManager am){

        String nomCircuito = gs.getCircuito();
        asM = new AssetManager();
        this.am = new AudioManager(asM);
        this.skin = skin;
        osd = new TestOsd(1, juego, skin, gs);

        asM.load("vehicles/ford_focus_m.png", Texture.class);
        asM.finishLoading();

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new MiOrthoCam();

        float aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        this.miViewport = new FitViewport(aspectRatio * 720 / PPM, 720 / PPM, miCam);

        this.circuito = new Circuito(miWorld, nomCircuito, asM);
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();
        this.jugador = circuito.prepararParrilla(-1, 0);

        switch (nomCircuito) {
            default:
            case "test_loop":
                circuitoCenterX = LOOP_CENTER_X;
                circuitoCenterY = LOOP_CENTER_Y;
                circuitoEscala = LOOP_ESCALA;
                break;
            case "track_1":
                circuitoCenterX = TRACK_1_CENTER_X;
                circuitoCenterY = TRACK_1_CENTER_Y;
                circuitoEscala = TRACK_1_ESCALA;
                break;
        }

        this.ttm = new TimeTrialManager(this.jugador, nomCircuito);
        this.ttOsd = new TimeTrialOsd(skin, ttm, am);
        miWorld.setContactListener(new miContactListener(ttm));

        if (Controllers.getControllers().size > 0) {
            pi = new ControllerInput(Controllers.getControllers().get(0));
        } else {
            this.pi = osd;
        }

        im = new InputManager(pi, jugador, this.am);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(osd.getMultiplexer());
        osd.getmPausa().setScreen(this);
    }

    @Override
    public void render(float delta) {
        if (asM.update()) {
            draw();
            if (!osd.isPaused()) {
                if (init) {
                    am.init();
                    init = false;
                }
                update(delta);
                im.update();
                ttOsd.render(delta);
            }
            osd.render(delta);
            updateCam();
        }
    }

    private void updateCam() {
        switch (osd.camMode()) {
            case 0:
                this.miCam.zoom = 0.4f;
                break;
            case 1:
                this.miCam.zoom = 0.8f;
                break;
            case 2:
                this.miCam.AdjustaZoomPorVelo(jugador.getBody());
                break;
        }
        miCam.position.set(jugador.getPosition(), 0);
        miCam.update();
    }

    private void update(float delta) {

        miWorld.step(delta, 6, 2);
    }

    private void draw() {
        miBatch.setProjectionMatrix(miCam.combined);
        miBatch.begin();
        circuito.getS().setOrigin(0, 0);
        circuito.getS().setCenter(circuitoCenterX,circuitoCenterY);
        circuito.getS().setSize(circuitoEscala * 2f, circuitoEscala);
        circuito.getS().setOriginCenter();
        circuito.getS().draw(miBatch);

        jugador.getS().setOrigin(0, 0);
        jugador.getS().setCenterX(jugador.getBody().getPosition().x);
        jugador.getS().setCenterY(jugador.getBody().getPosition().y);
        jugador.getS().setOriginCenter();
        jugador.getS().setSize(0.4f, 0.8f);
        float rotation = (float) Math.toDegrees(jugador.getBody().getAngle());
        jugador.getS().setRotation(rotation);
        jugador.getS().draw(miBatch);

        miBatch.end();
        miB2dr.render(miWorld, miCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        miViewport.update(width, height);
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
        miBatch.dispose();
        miWorld.dispose();
        miB2dr.dispose();
        osd.dispose();
        ttOsd.dispose();
        am.dispose();
        asM.dispose();
    }

}
