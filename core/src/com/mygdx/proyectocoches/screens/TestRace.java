package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.LOOP_CENTER_X;
import static com.mygdx.proyectocoches.Constantes.LOOP_CENTER_Y;
import static com.mygdx.proyectocoches.Constantes.LOOP_ESCALA;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;
import static com.mygdx.proyectocoches.Constantes.PPM;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_CENTER_X;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_CENTER_Y;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_ESCALA;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.audio.AudioManager;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.entidades.Jugador;
import com.mygdx.proyectocoches.formas.Circuito;
import com.mygdx.proyectocoches.gamemodes.RaceManager;
import com.mygdx.proyectocoches.ui.RaceOsd;
import com.mygdx.proyectocoches.ui.TestOsd;
import com.mygdx.proyectocoches.utils.GameSettings;
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.MiOrthoCam;
import com.mygdx.proyectocoches.utils.PlayerInput;
import com.mygdx.proyectocoches.utils.miContactListener;

public class TestRace implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final MiOrthoCam miCam;
    private final Viewport miViewport;
    private final Circuito circuito;
    private final CatmullRomSpline<Vector2>[] rutas;
    private final ShapeRenderer sr;
    private final Jugador jugador;
    private final PlayerInput pi;
    private final TestOsd osd;
    private final RaceOsd rOsd;
    private final InputManager im;
    private final RaceManager rm;
    private final AudioManager am;
    private final AssetManager asM;
    private boolean init = true;

    private final float circuitoCenterX;
    private final float circuitoCenterY;
    private final float circuitoEscala;

    private Seek seekSB;

    private final Skin skin;

    public TestRace(Game juego, Skin skin, GameSettings gs) {
        String nomCircuito = gs.getCircuito();
        asM = new AssetManager();
        this.am = new AudioManager(asM);
        this.skin = skin;
        osd = new TestOsd(0, juego, skin, gs);

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new MiOrthoCam();

        asM.load("vehicles/citroen_xsara_m.png", Texture.class);
        asM.load("vehicles/ford_escort_rs_m.png", Texture.class);
        asM.load("vehicles/ford_focus_m.png", Texture.class);
        asM.finishLoading();

        float aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        this.miViewport = new FitViewport(aspectRatio * 720 / PPM, 720 / PPM, miCam);

        this.circuito = new Circuito(miWorld, nomCircuito, asM);
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();

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

        this.jugador = circuito.prepararParrilla(gs.getNumOpos(), gs.getNumOpos() + 1);
        this.rm = new RaceManager(circuito.getCompetidores(), circuito.cargarSplineControl(), gs.getnVueltas());
        miWorld.setContactListener(new miContactListener(rm));
        this.rOsd = new RaceOsd(skin, rm);
        this.pi = osd;
        im = new InputManager(osd, jugador, this.am);

        this.rutas = circuito.cargarRutas();
        for (Competidor c : circuito.getCompetidores()) {
            if (c instanceof CocheIA) {
                ((CocheIA) c).setMaxLinearSpeed(MAX_VELOCIDAD_FORW);
                ((CocheIA) c).setMaxLinearAcceleration(12);
                ((CocheIA) c).setMaxAngularAcceleration(15000);
                ((CocheIA) c).setMaxAngularSpeed(5000);
                this.seekSB = new Seek<>((CocheIA) c, ((CocheIA) c).getDestinoSensor());
                ((CocheIA) c).setSteeringBehavior(this.seekSB);
                int rnd = (int) (Math.random() * rutas.length);
                ((CocheIA) c).setRutaSelect(rnd);
                ((CocheIA) c).setNumDestinosRuta(rutas[rnd].controlPoints.length);
            }
        }
        this.sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
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
                if (delta > 1) {
                    rm.CuentaAtrasSet();
                } else if (rm.CuentaAtras(delta)) {
                    update(delta);
                }
                im.update();
                if (rm.isJugadorAcabo()) {
                    osd.getmPausa().getSalir().setVisible(true);
                }
            }
            osd.render(delta);
            rOsd.render(delta);
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
        for (Competidor c : circuito.getCompetidores()) {
            if (c instanceof CocheIA) {
                int destino = ((CocheIA) c).getDestinoActualNdx();
                int ndx = ((CocheIA) c).getRutaSelect();
                ((CocheIA) c).setDestinoSensorPosition(rutas[ndx].controlPoints[destino]);
                ((CocheIA) c).update(delta);
            }
        }

    }

    private void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        miBatch.setProjectionMatrix(miCam.combined);
        miBatch.begin();
        circuito.getS().setOrigin(0, 0);
        circuito.getS().setCenter(circuitoCenterX, circuitoCenterY);
        circuito.getS().setSize(circuitoEscala * 2f, circuitoEscala);
        circuito.getS().setOriginCenter();
        circuito.getS().draw(miBatch);

        for (Competidor c : circuito.getCompetidores()) {

            c.getS().setOrigin(0, 0);
            c.getS().setCenterX(c.getBody().getPosition().x);
            c.getS().setCenterY(c.getBody().getPosition().y);
            c.getS().setOriginCenter();
            c.getS().setSize(0.25f, 0.5f);
            float rotation = (float) Math.toDegrees(c.getBody().getAngle());
            c.getS().setRotation(rotation);

            c.getS().draw(miBatch);
        }

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
        rOsd.dispose();
        asM.dispose();
        am.dispose();
    }

}
