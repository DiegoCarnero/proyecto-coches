package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.formas.Coche;
import com.mygdx.proyectocoches.formas.GeneradorCircuito;
import com.mygdx.proyectocoches.ui.TestOsd;
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.MiOrthoCam;

import java.util.ArrayList;

public class TestDrive implements Screen {

    private final SpriteBatch miBatch;
    private final World miWorld;
    private final Box2DDebugRenderer miB2dr;
    private final MiOrthoCam miCam;
    private final Viewport miViewport;
    private final Body jugador;
    private final ArrayList<Body> circuito;
    private final TestOsd osd;
    private final InputManager im;

    public TestDrive(Game juego) {

        osd = new TestOsd(juego);

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0,0),true);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new MiOrthoCam();
        this.miViewport = new FitViewport(1440F/PPM,720F/PPM,miCam);

        this.circuito = GeneradorCircuito.Cargar(miWorld,"worlds/test_loop.tmx");
        this.jugador = Coche.generaCoche(new Vector2(0,0),miWorld,new Vector2(5,10));

        im = new InputManager(osd,jugador);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(osd.getMultiplexer());
    }

    @Override
    public void render(float delta) {

        update(delta);
        draw();
        im.update();
        osd.render(delta);
    }

    private void update(float delta) {
        miCam.position.set(jugador.getPosition(),0);
        this.miCam.AdjustaZoomPorVelo(jugador);
        miCam.update();
        miWorld.step(delta,6,2);
    }

    private void draw() {
        miBatch.setProjectionMatrix(miCam.combined);
        miB2dr.render(miWorld,miCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        miViewport.update(width,height);
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
    }
}
