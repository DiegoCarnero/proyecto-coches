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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.proyectocoches.audio.AudioManager;
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

/**
 * Pantalla de juego para el modo de juego 'Contrarreloj'
 */
public class TestDrive implements Screen {

    /**
     * SpriteBatch que dibuja en pantalla las texturas
     */
    private final SpriteBatch miBatch;
    /**
     * Mundo Box2D donde se insertaran el Body representando el coche del jugador y circuito
     */
    private final World miWorld;
//    private final Box2DDebugRenderer miB2dr;
    /**
     * Camara ortogonal que se usa para dibujar el mundo y sus cuerpos con la perspectiva correcta
     */
    private final MiOrthoCam miCam;
    /**
     * Calcula como las coordenadas del {@link World} estan asociadas a la pantalla usando {@link MiOrthoCam}
     */
    private final Viewport miViewport;
    /**
     * Jugador
     */
    private final Jugador jugador;
    /**
     * Objeto que contiene todas las entidades en el mundo:
     * <ul>
     *   <li>Muros</li>
     *   <li>Puntos de control</li>
     *   <li>Linea de meta</li>
     *   <li>Competidores IA</li>
     *   <li>Jugador</li>
     * </ul>
     * Tiene metodos para generar el número deseado de oponentes, cargar rutas, etc
     */
    private final Circuito circuito;
    /**
     * Gestor de input para que el {@link Jugador} controle el Competidor que le representa
     */
    private final PlayerInput pi;
    /**
     * Elementos de la interfaz: menu de pausa y controles
     */
    private final TestOsd osd;
    /**
     * Elementos de la interfaz con informacion relativa al modo de juego
     */
    private final TimeTrialOsd ttOsd;
    /**
     * Hace que el Body del jugador responda a los inputs del jugador
     */
    private final InputManager im;
    /**
     * Sistema de gestiones logicas para el modo de juego 'Contrarreloj'
     */
    private final TimeTrialManager ttm;
    /**
     * Almacena los sonidos y música, y gestiona el cambio de efectos de sonido durante el gameplay
     */
    private final AudioManager am;
    /**
     * AssetManager donde se cargaran todos los archivos binarios necesarios para el juego
     */
    private final AssetManager asM;
    /**
     * Control de si el {@link AudioManager} ha empezado a reproducir sonido
     */
    private boolean init = true;
    /**
     * Posición central de X del {@link Sprite} del circuito
     */
    private final float circuitoCenterX;
    /**
     * Posición central de Y del {@link Sprite} del circuito
     */
    private final float circuitoCenterY;
    /**
     * Proporcion de escalado del {@link Sprite} del circuito
     */
    private final float circuitoEscala;
    /**
     * Skin que se aplica a los elementosde la interfaz
     */
    private final Skin skin;

    /**
     * Pantalla de juego para el modo de juego 'Contrarreloj'
     *
     * @param juego base del proyecto
     * @param skin  Skin que se aplica a los elementosde la interfaz
     * @param gs    Configuracion del evento
     * @param am    AssetManager donde se cargaran todos los archivos binarios necesarios para el juego
     */
    public TestDrive(Game juego, Skin skin, GameSettings gs, AssetManager am) {
        String nomCircuito = gs.getCircuito();
        asM = new AssetManager();
        this.am = new AudioManager(asM);
        this.skin = skin;
        osd = new TestOsd(1, juego, skin, gs, am);

        JsonReader json = new JsonReader();
        JsonValue base;
        FileHandle a = Gdx.files.external("usersettings.json");
        if (!a.exists()) {
            JsonValue template = json.parse(Gdx.files.internal("usersettings_template.json"));
            a.writeString(template.toString(), false);
        }
        base = json.parse(Gdx.files.external("usersettings.json"));
        int rendermode = base.getInt("rendermode");

        if (rendermode == 1) {
            asM.load("vehicles/cochejugador_HC.png", Texture.class);
        } else {
            asM.load("vehicles/ford_focus_m.png", Texture.class);
        }
        asM.finishLoading();

        this.miBatch = new SpriteBatch();
        this.miWorld = new World(new Vector2(0, 0), true);
//        this.miB2dr = new Box2DDebugRenderer();
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
        this.ttOsd = new TimeTrialOsd(ttm, am);
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

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (asM.update()) {
            draw();
            if (!osd.isPaused()) {
                if (init) {
                    am.init();
                    init = false;
                }
                updateWorld(delta);
                im.update();
                ttOsd.render(delta);
            }
            osd.render(delta);
            updateCam();
        }
    }

    /**
     * Actualiza la posicion de la camara centrandola en el jugador, y ajusta el zoom si aplicable
     */
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

    /**
     * Actualiza un paso en el {@link World}
     * @param delta Tiempo desde la ultima actualizacion
     */
    private void updateWorld(float delta) {

        miWorld.step(delta, 6, 2);
    }

    /**
     * Dibuja los {@link Sprite} del jugador y el circuito, ajustados en posicion y tamaño relativo a una {@link MiOrthoCam}
     */
    private void draw() {
        miBatch.setProjectionMatrix(miCam.combined);
        miBatch.begin();
        circuito.getS().setOrigin(0, 0);
        circuito.getS().setCenter(circuitoCenterX, circuitoCenterY);
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
//        miB2dr.render(miWorld, miCam.combined);
    }

    /**
     * @param width ancho de pantalla en pixeles
     * @param height alto de pantalla en pixeles
     */
    @Override
    public void resize(int width, int height) {
        miViewport.update(width, height);
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
        miBatch.dispose();
        miWorld.dispose();
//        miB2dr.dispose();
        osd.dispose();
        ttOsd.dispose();
        am.dispose();
        asM.dispose();
    }

}
