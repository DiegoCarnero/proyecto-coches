package com.mygdx.proyectocoches.screens;

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;
import com.mygdx.proyectocoches.entidades.Jugador;
import com.mygdx.proyectocoches.formas.Circuito;
import com.mygdx.proyectocoches.ui.TestOsd;
import com.mygdx.proyectocoches.ui.TimeTrialOsd;
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
    private final TimeTrialManager rlm;

    private final Skin skin;

    public TestDrive(Game juego,Skin skin) {

        this.skin = skin;
        osd = new TestOsd(juego,skin);

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0,0),true);
        this.rlm = new TimeTrialManager();
        miWorld.setContactListener(new miContactListener(rlm));
        this.ttOsd = new TimeTrialOsd(skin,rlm);
        this.miB2dr = new Box2DDebugRenderer();
        this.miCam = new MiOrthoCam();
        this.miViewport = new FitViewport(Gdx.graphics.getWidth()/PPM,Gdx.graphics.getHeight()/PPM,miCam);

        this.circuito = new Circuito(miWorld,"test_loop");
        circuito.cargarMuros();
        circuito.cargarMeta();
        circuito.cargarCheckpoints();
        this.jugador = circuito.prepararParrilla(0,0);
        this.pi = osd;
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
        ttOsd.render(delta);
    }

    private void update(float delta) {
        miCam.position.set(jugador.getPosition(),0);
        this.miCam.AdjustaZoomPorVelo(jugador.getBody());
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
        ttOsd.dispose();
    }
}
