package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;
import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.MiOrthoCam;
import com.mygdx.proyectocoches.utils.PlayerInput;
import com.mygdx.proyectocoches.utils.miContactListener;

public class TestRace  implements Screen {

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

    private Seek seekSB;

    private final Skin skin;

    public TestRace(Game juego, Skin skin) {

        asM = new AssetManager();
        this.am = new AudioManager(asM);
        this.skin = skin;
        osd = new TestOsd(juego, skin);

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new MiOrthoCam();

        float aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        this.miViewport = new FitViewport(aspectRatio * 720 / PPM, 720 / PPM, miCam);

        this.circuito = new Circuito(miWorld, "track_1");
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();

        this.jugador = circuito.prepararParrilla(25,10);
        this.rm = new RaceManager(circuito.getCompetidores(),circuito.cargarSplineControl());
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
    }

    @Override
    public void render(float delta) {
        if (asM.update()) {
            if(init){
                am.init();
                init = false;
            }
            Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            update(delta);
            im.update();
            osd.render(delta);
            rOsd.render(delta);

            sr.begin();
            sr.setColor(Color.WHITE);
            int precision = 1000;
            for (CatmullRomSpline<Vector2> s : rutas) {

                for (int i = 0; i < precision; ++i) {
                    float t = i / (float) precision;
                    Vector2 ini = new Vector2();
                    Vector2 fin = new Vector2();

                    s.valueAt(ini, t);
                    s.valueAt(fin, t - (1f / (float) precision));

                    sr.line(ini.x, ini.y, fin.x, fin.y);
                }
            }
            sr.end();
            draw();
        }
    }

    private void update(float delta) {

        miWorld.step(delta, 6, 2);
        for (Competidor c : circuito.getCompetidores()) {
            if (c instanceof CocheIA) {
                int destino = ((CocheIA) c).getDestinoActualNdx();
                int ndx =  ((CocheIA) c).getRutaSelect();
                ((CocheIA) c).setDestinoSensorPosition(rutas[ndx].controlPoints[destino]);
                ((CocheIA) c).update(delta);
            }
        }

        miCam.position.set(jugador.getPosition(), 0);
        this.miCam.AdjustaZoomPorVelo(jugador.getBody());
        miCam.update();
    }

    private void draw() {
        miBatch.setProjectionMatrix(miCam.combined);
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
    }

}
