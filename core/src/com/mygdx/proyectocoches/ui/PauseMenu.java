package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.proyectocoches.screens.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Componentes menu de pausa
 */
public class PauseMenu extends Actor {

    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compPausa = new ArrayList<>();
    /**
     * Conjunto de todos los elementos {@link Actor} deñ submenu 'Records'
     */
    private final ArrayList<Actor> compRecords = new ArrayList<>();
    /**
     * Boton continua, sale del menu de pausa
     */
    private final Button btnContinua;
    /**
     * Boton selector de camara
     */
    private final Button btnCameraMode;
    /**
     * Boton para salir de la partida
     */
    private final Button btnSalir;
    /**
     * Boton para mostrar los records en el circuito actual
     */
    private final Button btnRecords;
    /**
     * Texto representando la camara seleccionada
     */
    private final Label lblCameroMode;
    /**
     * Texto representando btnContinua
     */
    private final Label lblContinua;
    /**
     * Texto representando btnRecords
     */
    private final Label lblRecords;
    /**
     * Texto representando btnSalir
     */
    private final Label lblSalir;
    /**
     * Boton para pausar el juego y mostrar este menu
     */
    private final Button btnPausa;
    /**
     * Submenu de records
     */
    private final RecordsMenu mRecords;
    /**
     * Si el juego está pausado o no
     */
    private boolean paused;
    /**
     * Texto de los tipos de camara localizados
     */
    private final String[] camModes = {"Cerca", "Lejos", "Dinamica"};
    /**
     * Contador modo de camara
     */
    private int cont;
    /**
     * {@link Screen} en la que se pone este menu de pausa
     */
    private Screen screen;

    /**
     * Establece la {@link Screen} en la que se pone este menu de pausa
     *
     * @param screen pantalla
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    /**
     * Componentes menu de pausa
     *
     * @param game        base del proyecto
     * @param modo        modo de juego paramostrar o no el boton de records
     * @param skin        skin para los botones
     * @param nomCircuito nombre interno del circuito actual
     * @param am          AssetManager con los ttf para los textos
     */
    public PauseMenu(final Game game, int modo, final Skin skin, String nomCircuito, AssetManager am) {

        int screenW, screenH;
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        int vertOffset = 0;

        final I18NBundle locale = am.get("locale/locale");

        camModes[0] = locale.get("camera.cerca");
        camModes[1] = locale.get("camera.lejos");
        camModes[2] = locale.get("camera.dinamica");

        lblContinua = new Label(locale.get("pausemenu.continuar"), labelStyle);
        lblContinua.setSize(screenW / 7f, screenH / 10f);
        lblContinua.setPosition(screenW / 2f - screenW / 14f, 0);
        lblContinua.setTouchable(Touchable.disabled);
        lblContinua.setAlignment(1);
        lblContinua.setVisible(false);

        btnContinua = new Button(skin);
        btnContinua.setSize(screenW / 7f, screenH / 10f);
        btnContinua.setPosition(screenW / 2f - screenW / 14f, 0);
        btnContinua.addListener(new InputListener() {
            /**
             * Sale de la pausa y coninua el juego
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                paused = !paused;
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                return true;
            }
        });
        btnContinua.setVisible(false);

        vertOffset++;

        JsonValue base;
        JsonReader json = new JsonReader();
        base = json.parse(Gdx.files.external("usersettings.json"));
        cont = base.getInt("cam");

        lblCameroMode = new Label(camModes[cont], labelStyle);
        lblCameroMode.setSize(screenW / 7f, screenH / 10f);
        lblCameroMode.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        lblCameroMode.setTouchable(Touchable.disabled);
        lblCameroMode.setAlignment(1);
        lblCameroMode.setVisible(false);

        btnCameraMode = new Button(skin);
        btnCameraMode.setSize(screenW / 7f, screenH / 10f);
        btnCameraMode.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        btnCameraMode.addListener(new InputListener() {
            /**
             * Pasa a seleccionar el siguiente modo de camara de la lista
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cont = cont == 2 ? 0 : cont + 1;
                lblCameroMode.setText(camModes[cont]);

                JsonReader json = new JsonReader();
                JsonValue base;

                base = json.parse(Gdx.files.external("usersettings.json"));
                JsonValue c = new JsonValue(cont);

                base.remove("cam");
                base.addChild("cam", c);

                FileHandle file = Gdx.files.external("usersettings.json");
                file.writeString(base.toString(), false);

                return true;
            }
        });
        btnCameraMode.setVisible(false);

        if (modo == 1) {
            vertOffset++;
        }

        this.mRecords = new RecordsMenu(nomCircuito, skin, am);

        lblRecords = new Label(locale.get("pausemenu.records"), labelStyle);
        lblRecords.setSize(screenW / 7f, screenH / 10f);
        lblRecords.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        lblRecords.setTouchable(Touchable.disabled);
        lblRecords.setAlignment(1);
        lblRecords.setVisible(false);

        btnRecords = new Button(skin);
        btnRecords.setSize(screenW / 7f, screenH / 10f);
        btnRecords.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        btnRecords.addListener(new InputListener() {
            /**
             * Muestra el menu de tiempos record para este circuito
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                for (Actor a : compPausa) {
                    a.setVisible(false);
                }
                mRecords.setShowing(true);
                mRecords.getBackBtn().setVisible(true);
                return true;
            }
        });
        btnRecords.setVisible(false);

        vertOffset++;

        lblSalir = new Label(locale.get("pausemenu.salir"), labelStyle);
        lblSalir.setSize(screenW / 7f, screenH / 10f);
        lblSalir.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        lblSalir.setTouchable(Touchable.disabled);
        lblSalir.setAlignment(1);
        lblSalir.setVisible(false);

        btnSalir = new Button(skin);
        btnSalir.setSize(screenW / 7f, screenH / 10f);
        btnSalir.setPosition(screenW / 2f - screenW / 14f, -vertOffset * screenH / 10f);
        btnSalir.addListener(new InputListener() {
            /**
             * Sale de la partida
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screen.dispose();
                game.setScreen(new MainMenu(game));
                return true;
            }
        });
        btnSalir.setVisible(false);

        btnPausa = new TextButton("||", skin);
        btnPausa.setSize(screenH / 10f, screenH / 10f);
        btnPausa.setPosition(0, screenH / 2f - screenH / 10f);
        btnPausa.addListener(new InputListener() {
            /**
             * Pausa el juego y muestra el menu
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                paused = !paused;
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                for (Actor a : compRecords) {
                    a.setVisible(false);
                }
                return true;
            }
        });

        compPausa.add(btnContinua);
        compPausa.add(lblContinua);
        compPausa.add(btnCameraMode);
        compPausa.add(lblCameroMode);
        compPausa.add(btnSalir);
        compPausa.add(lblSalir);
        compRecords.addAll(mRecords.getCompRecords());
        // 1 = contrarreloj
        if (modo == 1) {
            compPausa.addAll(mRecords.getCompRecords());
            compPausa.add(btnRecords);
            compPausa.add(lblRecords);
        }
        mRecords.getBackBtn().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compPausa) {
                    a.setVisible(paused);
                }
                for (Actor a : compRecords) {
                    a.setVisible(!paused);
                }
                return true;
            }
        });
    }

    public int getCamMode() {
        return cont;
    }

    public ArrayList<Actor> getCompPausa() {
        return compPausa;
    }

    public Button getBtnPausa() {
        return btnPausa;
    }

    public Label getLblSalir() {
        return lblSalir;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Actor getSalir() {
        return btnSalir;
    }
}
