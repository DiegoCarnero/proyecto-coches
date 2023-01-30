package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;
import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.formas.Circuito;
import com.mygdx.proyectocoches.formas.Coche;
import com.mygdx.proyectocoches.formas.Sensor;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;
import com.mygdx.proyectocoches.utils.miContactListener;

public class TestIA implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final OrthographicCamera miCam;
    private final Viewport miViewport;
    private final Circuito circuito;
    private final CatmullRomSpline<Vector2>[] rutas;
    private final ShapeRenderer sr;

    private Seek seekSB;

    private Skin skin;

    public TestIA(Game juego, Skin skin) {
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new OrthographicCamera();
        //miWorld.setContactListener(new miContactListener(new TimeTrialManager()));
        miCam.zoom = 4f;
        miCam.position.set(new Vector2(0,16),0);
        this.miViewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, miCam);

        this.circuito = new Circuito(miWorld, "track_1");
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();
        circuito.prepararParrilla(9);

        this.rutas = circuito.cargarRutas();
        for (Competidor c : circuito.getCompetidores()) {
            if (c instanceof CocheIA) {
                ((CocheIA) c).setMaxLinearSpeed(MAX_VELOCIDAD_FORW);
                ((CocheIA) c).setMaxLinearAcceleration(10);
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

    @Override
    public void show() {

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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        update(delta);

        miCam.update();
    }

    private void draw() {
        miBatch.setProjectionMatrix(miCam.combined);
        sr.setProjectionMatrix(miCam.combined);
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

    }
}
