package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.formas.Circuito;
import com.mygdx.proyectocoches.formas.Coche;
import com.mygdx.proyectocoches.formas.Sensor;

public class TestIA implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final OrthographicCamera miCam;
    private final Viewport miViewport;
    private final Circuito circuito;
    private final CocheIA c;
    private final CatmullRomSpline<Vector2>[] rutas;
    private final ShapeRenderer sr;

    private Vector2 posSplineActual;
    private int contSplineActual = 0;
    private Sensor b;

    private Vector2 aux;
    private Arrive arriveSB;

    private Skin skin;

    public TestIA(Game juego, Skin skin) {
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new OrthographicCamera();
        miCam.zoom = 0.2f;
        this.miViewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, miCam);

        this.circuito = new Circuito(miWorld, "test_loop");
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();

        this.c = new CocheIA(Coche.generaCoche(new Vector2(2, -5), miWorld, new Vector2(5, 10)));
        c.getBody().setTransform(c.getBody().getPosition(), (float) -(90 * Math.PI / 180));
        c.getBody().setAwake(true);

        this.rutas = circuito.cargarRutas();
        this.sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        Sensor b = new Sensor(posSplineActual,miWorld);

         arriveSB = new Arrive<Vector2>(c, b).setTimeToTarget(0.01f)
                .setArrivalTolerance(1f)
                .setDecelerationRadius(0.01f);
        c.setSteeringBehavior(arriveSB);
        c.getBody().setLinearVelocity(new Vector2(0,2));
        c.update(delta, b.getVarPos());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin();
        sr.setColor(Color.WHITE);
        int precision = 100;
        for (CatmullRomSpline<Vector2> s : rutas) {

            for (int i = 0; i < precision; ++i) {
                float t = i / (float) precision;
                Vector2 ini = new Vector2();
                Vector2 fin = new Vector2();

                s.valueAt(ini, t);

                s.valueAt(fin, t - (1f / (float) precision));

                sr.line(ini.x, ini.y, fin.x, fin.y);

                if (i == contSplineActual) {
                    posSplineActual = ini;
                }
            }
        }
        sr.end();
        draw();
        update(delta);

        miCam.position.set(c.getPosition().x,c.getPosition().y, 0);
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
