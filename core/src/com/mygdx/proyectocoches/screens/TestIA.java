package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.proyectocoches.formas.Circuito;

public class TestIA implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final OrthographicCamera miCam;
    private final Viewport miViewport;
    private final Circuito circuito;
    private final CatmullRomSpline<Vector2>[] rutas;
    private final ShapeRenderer sr;

    private Skin skin;

    public TestIA(Game juego) {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new OrthographicCamera();
        miCam.zoom = 2f;
        this.miViewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, miCam);

        this.circuito = new Circuito(miWorld, "test_loop");
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();

        this.rutas = circuito.cargarRutas();
        this.sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sr.begin();
        sr.setColor(Color.WHITE);
        int precision = 100;
        for (CatmullRomSpline<Vector2> c : rutas) {

            for (int i = 0; i < precision; ++i) {
                float t = i / (float) precision;
                Vector2 ini = new Vector2();
                Vector2 fin = new Vector2();

                c.valueAt(ini, t);

                c.valueAt(fin, t - (1f / (float) precision));

                sr.line(ini.x, ini.y, fin.x, fin.y);

            }
        }
        sr.end();
        miCam.position.set(0, 0, 0);
        miCam.update();
        draw();
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
