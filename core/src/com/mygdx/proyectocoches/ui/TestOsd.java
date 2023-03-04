package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.utils.GameSettings;
import com.mygdx.proyectocoches.utils.PlayerInput;

import java.util.ArrayList;

/**
 * Interfaz de usuario para controlar el juego
 */
public class TestOsd implements Screen, PlayerInput {

    /**
     * Si se esta acelerando o no
     */
    private boolean acelerando = false;
    /**
     * Sentido de la marcha. 'true' si adelante, 'false' si atras
     */
    private boolean adelante = true;
    /**
     * Si se esta frenando
     */
    private boolean frenando = false;
    /**
     * Stage donde se ponen los Actores de esta interfaz
     */
    private final Stage UIStage;
    /**
     * Multiplexor para procesar los inputs del jugador
     */
    private final InputMultiplexer multiplexer;
    /**
     * Control de la aceleracion
     */
    private final Slider accSlider;
    /**
     * Control de la direccion
     */
    private final Slider steerSlider;
    /**
     * Boton seleccionar marcha adelante
     */
    private final Button btnD;
    /**
     * Boton seleccionar marcha atras
     */
    private final Button btnR;
    /**
     * Boton frenar
     */
    private final Button btnB;
    /**
     * Menu de pausa
     */
    private final PauseMenu mPausa;
    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compControles = new ArrayList<>();

    /**
     * Interfaz de usuario para controlar el juego
     *
     * @param modo modo de juego para determinar que elementos mostrar
     * @param miGame base del proyecto para salir de la partida
     * @param skin skin para los botones
     * @param gs Configuracion del evento
     * @param am AssetManager con los archivos necesarios ya cargados
     */
    public TestOsd(int modo, Game miGame, Skin skin, GameSettings gs, AssetManager am) {

        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        UIStage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        btnD = new TextButton("D", skin, "toggle");
        btnD.setHeight(screenH * 0.1f);
        btnD.setWidth(screenH * 0.1f);
        btnD.setPosition(14 * screenW / 15f, screenH / 4f);
        btnD.addListener(new InputListener() {
            /**
             * Pone la marcha hacia delante
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                adelante = true;
                btnR.setChecked(false);
                return true;
            }
        });
        btnD.setChecked(true);

        btnR = new TextButton("R", skin, "toggle");
        btnR.setHeight(screenH * 0.1f);
        btnR.setWidth(screenH * 0.1f);
        btnR.setPosition(14 * screenW / 15f, screenH / 4f - screenH * 0.13f);
        btnR.addListener(new InputListener() {
            /**
             * Pone la marcha atras
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                adelante = false;
                btnD.setChecked(false);
                return true;
            }
        });

        btnB = new TextButton("B", skin);
        btnB.setHeight(screenH * 0.1f);
        btnB.setWidth(screenH * 0.1f);
        btnB.setPosition(14 * screenW / 15f, screenH / 4f - screenH * 0.4f);
        btnB.addListener(new InputListener() {
            /**
             * Establece el 
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                frenando = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                frenando = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        accSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        accSlider.setHeight(screenH * 0.3f);
        accSlider.setWidth(screenW * 0.3f);
        accSlider.setPosition(13 * screenW / 20f, -screenH / 2f);

        accSlider.addListener(new InputListener() {
            /**
             * Establece el control como 'acelerando'
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             */
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                acelerando = true;
                super.touchDragged(event, x, y, pointer);
            }

            /**
             * Establece 'acelerando' a false y pone el slider a 0
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                accSlider.setValue(0.0f);
                acelerando = false;
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Establece el control como 'acelerando'
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                acelerando = true;
                super.touchUp(event, x, y, pointer, button);
                return true;
            }
        });

        steerSlider = new Slider(0F, 100F, (float) 0.1, false, skin);
        steerSlider.setHeight(screenH * 0.3f);
        steerSlider.setWidth(screenW * 0.3f);
        steerSlider.setPosition(screenW / 20f, screenH / -2f);
        steerSlider.setValue(50.0f);

        steerSlider.addListener(new InputListener() {
            /**
             * Monitoriza el control
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             */
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
            }

            /**
             * Centra el control a mitad del slider
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                steerSlider.setValue(50.0f);
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Empieza a monitorizar el control
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                return true;
            }
        });

        UIStage.addActor(accSlider);
        UIStage.addActor(steerSlider);
        UIStage.addActor(btnD);
        UIStage.addActor(btnR);
        UIStage.addActor(btnB);

        this.mPausa = new PauseMenu(miGame, modo, skin, gs.getCircuito(), am);
        UIStage.addActor(mPausa.getBtnPausa());

        for (Actor a : mPausa.getCompPausa()) {
            UIStage.addActor(a);
        }

        compControles.add(accSlider);
        compControles.add(steerSlider);
        compControles.add(btnD);
        compControles.add(btnR);
        compControles.add(btnB);

    }

    public PauseMenu getmPausa() {
        return mPausa;
    }

    public boolean isPaused() {
        return mPausa.isPaused();
    }

    public int camMode() {
        return mPausa.getCamMode();
    }

    public boolean isAcelerando() {
        return acelerando;
    }

    public boolean isAdelante() {
        return adelante;
    }

    public boolean isFrenando() {
        return frenando;
    }

    /**
     * Devuelve el valor de la aceleracion formalizado entre 0 y 1
     * @return valor del slider normalizadoentre 0 y 1
     */
    public float getAccValue() {
        return accSlider.getValue() / 100f;
    }

    /**
     * Devuelve el valor de giro, normalizado entre -50 y 50
     *
     * @return valor de giro entre -50 y 50
     */
    public float getSteerValue() {
        return -(steerSlider.getValue() - 50.0f);
    }

    /**
     * Devuelve el multiplexor de esta interfaz
     * @return multiplexor de esta interfaz
     */
    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    /**
     * Called when this screen becomes the current screen for a Game
     * Establece el multiplexor y pone el juego fuera de pausa
     */
    @Override
    public void show() {
        mPausa.setPaused(false);
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        for (Actor a : compControles) {
            a.setVisible(!isPaused());
        }

        if(Controllers.getControllers().size > 0){
            for (Actor a : compControles) {
                a.setVisible(false);
            }
        }

        UIStage.act();
        UIStage.draw();

    }


    /**
     * @param width ancho pantalla en pixeles
     * @param height alto pantalla en pixeles
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a Game
     */
    @Override
    public void hide() {

    }


    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        UIStage.dispose();
    }
}
