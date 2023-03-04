package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.proyectocoches.screens.MainMenu;

import java.util.ArrayList;

/**
 * Componentes del menu 'Ajustes'
 */
public class SettingsMenu {
    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compSettings = new ArrayList<>();
    /**
     * Lista de labels para el nombre del jugador
     */
    private final ArrayList<Label> lblsNombre = new ArrayList<>();
    /**
     * Lista de botones para ajustar el volumen de los efectos de sonido
     */
    private final ArrayList<Button> btnsSfx = new ArrayList<>();
    /**
     * Lista de botones para ajustar el volumen de la musica
     */
    private final ArrayList<Button> btnsMusic = new ArrayList<>();
    /**
     * Boton para retroceder al menu anterior
     */
    private final Button btnAtras;
    /**
     * Boton selector de camara
     */
    private final Button btnCameraMode;
    /**
     * Texto representando la camara seleccionada
     */
    private final Label lblCameroMode;
    /**
     * Texto denotando el control de volumen de efectos de sonido
     */
    private final Label lblSfx;
    /**
     * Texto denotando el control de volumen de musica
     */
    private final Label lblMusic;
    /**
     * Texto denotando el control de cambio de camara
     */
    private final Label lblCam;
    /**
     * Texto denotando el control para establecer el nombre del jugador
     */
    private final Label lblNom;
    /**
     * Texto denotando el control de seleccion de modo de renderizado
     */
    private final Label lblVisuals;
    /**
     * Texto mostrando el modo de renderizado seleccionado actualmente
     */
    private final Label lblVisualsNom;
    /**
     * Boton para cambar el modo de renderizado
     */
    private final Button btnVisuals;
    /**
     * Lista con los nombres localziados de modos de camara disponibles
     */
    private final String[] camModes = new String[3];
    /**
     * Lista con los nombres localziados de modos de renderizado disponibles
     */
    private final String[] visualModes = new String[2];
    /**
     * Lista de letras disponibles para establecer el nombre del jugador
     */
    private final char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /**
     * Indices de las letras para establecer el nombre del jugador
     */
    private int contLetras[] = new int[3];
    /**
     * Modo de camara seleccionado actualmente
     */
    private int contCam = 0;
    /**
     * Modo de renderizado seleccionado actualmente
     */
    private int contVisuals = 0;
    /**
     * Volumen de musica seleccionado actualmente
     */
    private float musicVol = 0;
    /**
     * Volumen de efectos de sonido seleccionado actualmente
     */
    private float sfxVol = 0;
    /**
     * Si este menu se esta mostrando o no
     */
    private boolean showing;
    /**
     * AssetManager con los archivos relevantas ya cargados
     */
    private final AssetManager am;

    /**
     * Componentes del menu 'Ajustes'
     * <br>Este objeto tiene un btnAtras, pero la implementacion del InputListener depende de la pantalla donde se implementa.
     * Invocar getBackBtn() para añadirle un Listener
     *
     * @param skin   skin para los botones
     * @param am     AssetManager con los archivos relevantas ya cargados
     * @param locale bundle con los textos localizados
     * @param mm     menu principal donde se incorporan los componetes de este SettingsMenu
     */
    public SettingsMenu(final Skin skin, AssetManager am, I18NBundle locale, final MainMenu mm) {

        this.am = am;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        camModes[0] = locale.get("camera.cerca");
        camModes[1] = locale.get("camera.lejos");
        camModes[2] = locale.get("camera.dinamica");

        visualModes[0] = locale.get("settings.visuals.normal");
        visualModes[1] = locale.get("settings.visuals.HC");

        final int screenH = Gdx.graphics.getHeight();
        final int screenW = Gdx.graphics.getWidth();

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);
        btnAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                JsonReader json = new JsonReader();
                JsonValue base;
                FileHandle a = Gdx.files.external("usersettings.json");
                if (!a.exists()) {
                    JsonValue template = json.parse(Gdx.files.internal("usersettings_template.json"));
                    a.writeString(template.toString(), false);
                }

                base = json.parse(Gdx.files.external("usersettings.json"));

                JsonValue j;
                base.remove("nom");
                j = new JsonValue(getName());
                base.addChild("nom", j);
                base.remove("cam");
                j = new JsonValue(contCam);
                base.addChild("cam", j);
                base.remove("music");
                j = new JsonValue(musicVol);
                base.addChild("music", j);
                base.remove("sfx");
                j = new JsonValue(sfxVol);
                base.addChild("sfx", j);
                base.remove("rendermode");
                j = new JsonValue(contVisuals);
                base.addChild("rendermode", j);

                FileHandle file = Gdx.files.external("usersettings.json");
                file.writeString(base.toString(), false);
                super.touchDown(event, x, y, pointer, button);
                return true;
            }

        });
        compSettings.add(btnAtras);

        int x = 20;
        lblNom = new Label(locale.get("settings.nombre"), labelStyle);
        lblNom.setPosition(x, 90 + screenH / 10f);
        lblNom.setAlignment(1);
        lblNom.setVisible(false);
        compSettings.add(lblNom);

        for (int i = 0; i < 3; i++) {
            final Label l = new Label("A", labelStyle);
            l.setSize(screenH / 10f, screenH / 10f);
            l.setPosition(x, 0);
            l.setAlignment(1);
            l.setVisible(false);
            final int iaux = i;
            Button b1 = new TextButton("^", skin);
            b1.setPosition(x, 60);
            b1.setSize(screenH / 10f, screenH / 10f);
            b1.addListener(new ClickListener() {
            /**
             * Pasa a seleccionar el siguiente circuito de la lista
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    contLetras[iaux] = contLetras[iaux] == letras.length - 1 ? 0 : contLetras[iaux] + 1;
                    String aux = String.valueOf(letras[contLetras[iaux]]);
                    l.setText(aux);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            b1.setVisible(false);
            Button b2 = new TextButton("v", skin);
            b2.setPosition(x, 60-screenH / 6f);
            b2.setSize(screenH / 10f, screenH / 10f);
            b2.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    contLetras[iaux] = contLetras[iaux] == 0 ? letras.length - 1 : contLetras[iaux] - 1;
                    String aux = String.valueOf(letras[contLetras[iaux]]);
                    l.setText(aux);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            b2.setVisible(false);
            lblsNombre.add(l);
            compSettings.add(l);
            compSettings.add(b1);
            compSettings.add(b2);
            x += screenH / 10f;
        }

        float volX = screenW / 2f;
        float volY = screenH / 6f;
        //sfx
        lblSfx = new Label(locale.get("settings.efectos"), labelStyle);
        lblSfx.setPosition(volX, volY);
        compSettings.add(lblSfx);
        lblSfx.setVisible(false);
        volX += screenW / 12f;
        for (int i = 0; i < 4; i++) {
            final Button b = new Button(skin, "toggle");
            b.setPosition(volX, volY);
            b.setSize(50, 50);
            btnsSfx.add(b);
            compSettings.add(b);
            b.setVisible(false);
            b.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    for (Button btn : btnsSfx) {
                        btn.setChecked(false);
                    }
                    sfxVol = 0;
                    for (Button btn : btnsSfx) {
                        if (btn == b) {
                            btn.setChecked(true);
                            sfxVol++;
                            break;
                        } else {
                            btn.setChecked(true);
                            sfxVol++;
                        }
                    }
                    sfxVol = sfxVol / btnsSfx.size();
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            volX += 80;
        }

        volX = screenW / 2f;
        volY -= 80;
        //music
        lblMusic = new Label(locale.get("settings.music"), labelStyle);
        lblMusic.setPosition(volX, volY);
        volX += screenW / 12f;
        compSettings.add(lblMusic);
        lblMusic.setVisible(false);
        for (int i = 0; i < 4; i++) {
            final Button b = new Button(skin, "toggle");
            b.setPosition(volX, volY);
            b.setSize(50, 50);
            btnsMusic.add(b);
            compSettings.add(b);
            b.setVisible(false);
            b.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    for (Button btn : btnsMusic) {
                        btn.setChecked(false);
                    }
                    musicVol = 0;
                    for (Button btn : btnsMusic) {
                        if (btn == b) {
                            btn.setChecked(true);

                            musicVol++;
                            break;
                        } else {
                            btn.setChecked(true);
                            musicVol++;
                        }
                    }
                    musicVol = musicVol / btnsMusic.size();
                    mm.setVolMusic(musicVol);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            volX += 80;
        }

        lblVisuals = new Label(locale.get("settings.visuals"), labelStyle);
        lblVisuals.setSize(screenW / 10f, screenH / 10f);
        lblVisuals.setPosition(screenW / 2f, -screenH / 3f);
        lblVisuals.setAlignment(1);
        lblVisuals.setVisible(false);

        lblVisualsNom = new Label(visualModes[contVisuals], labelStyle);
        lblVisualsNom.setSize(screenW / 7f, screenH / 10f);
        lblVisualsNom.setPosition(screenW / 2f + screenH / 5f, -screenH / 3f);
        lblVisualsNom.setTouchable(Touchable.disabled);
        lblVisualsNom.setAlignment(1);
        lblVisualsNom.setVisible(false);

        btnVisuals = new Button(skin);
        btnVisuals.setSize(screenW / 7f, screenH / 10f);
        btnVisuals.setPosition(screenW / 2f + screenH / 5f, -screenH / 3f);
        btnVisuals.setVisible(false);
        btnVisuals.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contVisuals = contVisuals == 1 ? 0 : 1;
                lblVisualsNom.setText(visualModes[contVisuals]);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        compSettings.add(btnVisuals);
        compSettings.add(lblVisuals);
        compSettings.add(lblVisualsNom);

        lblCam = new Label(locale.get("settings.camara"), labelStyle);
        lblCam.setSize(screenW / 10f, screenH / 10f);
        lblCam.setPosition(4 * screenW / 10f - screenW / 20f, 4 * screenH / 10f);
        lblCam.setAlignment(1);
        lblCam.setVisible(false);

        lblCameroMode = new Label(camModes[contCam], labelStyle);
        lblCameroMode.setSize(screenW / 10f, screenH / 10f);
        lblCameroMode.setPosition(screenW / 2f - screenW / 20f, 4 * screenH / 10f);
        lblCameroMode.setTouchable(Touchable.disabled);
        lblCameroMode.setAlignment(1);
        lblCameroMode.setVisible(false);

        btnCameraMode = new Button(skin);
        btnCameraMode.setSize(screenW / 10f, screenH / 10f);
        btnCameraMode.setPosition(screenW / 2f - screenW / 20f, 4 * screenH / 10f);
        btnCameraMode.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contCam = contCam == 2 ? 0 : contCam + 1;
                lblCameroMode.setText(camModes[contCam]);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnCameraMode.setVisible(false);

        compSettings.add(btnCameraMode);
        compSettings.add(lblCameroMode);
        compSettings.add(lblCam);

    }

    /**
     * Establece los controles con los valores guardados por ultima vez
     */
    public void setDesdeUserSettings() {

        JsonReader json = new JsonReader();
        JsonValue base;
        FileHandle a = Gdx.files.external("usersettings.json");
        if (!a.exists()) {
            JsonValue template = json.parse(Gdx.files.internal("usersettings_template.json"));
            a.writeString(template.toString(), false);
        }
        base = json.parse(Gdx.files.external("usersettings.json"));
        String nom = base.getString("nom");

        contVisuals = base.getInt("rendermode");
        lblVisualsNom.setText(visualModes[contVisuals]);

        int ndxNom = 0;
        for (Label l : lblsNombre) {
            l.setText(String.valueOf(nom.charAt(ndxNom)));
            contLetras[ndxNom] = nom.charAt(ndxNom) - 65;
            ndxNom++;
        }

        contCam = base.getInt("cam");
        lblCameroMode.setText(camModes[contCam]);

        musicVol = base.getFloat("music");
        for (int i = 0; i < musicVol * btnsMusic.size(); i++) {
            btnsMusic.get(i).setChecked(true);
        }
        sfxVol = base.getFloat("sfx");
        for (int i = 0; i < sfxVol * btnsSfx.size(); i++) {
            btnsSfx.get(i).setChecked(true);
        }

    }

    /**
     * Devuelve los componentes de este submenu
     *
     * @return lista con los componentes
     */
    public ArrayList<Actor> getCompSettings() {
        return compSettings;
    }

    /**
     * Establece si esta pantalla ha de mostrarse o no
     *
     * @param showing 'true' si la pantalla se muestra, 'false' si no
     */
    public void setShowing(boolean showing) {
        this.showing = showing;

        for (Actor a : compSettings) {
            a.setVisible(showing);
        }

        if (showing) {
            setDesdeUserSettings();
        }
    }

    /**
     * Devuelve el nombre del jugador, de tres letras de largo, establecido por las letras en lblsNobre
     *
     * @return el nombre del jugador
     */
    private String getName() {
        String retorno = "";
        for (Label a : lblsNombre) {
            retorno += a.getText();
        }

        return retorno;
    }

    /**
     * Devuelve el boton para retroceder al menu anterior
     *
     * @return btnAtras
     */
    public Button getBackBtn() {
        return btnAtras;
    }

    /**
     * Si este menu se esta mostrando o no
     *
     * @return 'true' si la pantalla se muestra, 'false' si no
     */
    public boolean isShowing() {
        return showing;
    }

    /**
     * Devuelve el volumen de la musica establecido por su respectivo control
     * @return volumen de la musica, entre 0.25 y 1
     */
    public float getMusicVolume() {
        return musicVol;
    }
}
