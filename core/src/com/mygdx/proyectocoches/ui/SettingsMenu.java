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

import java.util.ArrayList;

public class SettingsMenu {
    private final ArrayList<Actor> compSettings = new ArrayList<>();
    private final ArrayList<Label> lblsNombre = new ArrayList<>();
    private final ArrayList<Button> btnsSfx = new ArrayList<>();
    private final ArrayList<Button> btnsMusic = new ArrayList<>();

    private final Button btnAtras;
    private final Button btnCameraMode;
    private final Label lblCameroMode;
    private final Label lblSfx;
    private final Label lblMusic;
    private final Label lblCam;
    private final Label lblNom;
    private final Label lblVisuals;
    private final Label lblVisualsNom;
    private final Button btnVisuals;
    private final String[] camModes = new String[3];
    private final String[] visualModes = new String[2];
    private final char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private int contLetras[] = new int[3];
    private int contCam = 0;
    private int contVisuals = 0;
    private float musicVol = 0;
    private float sfxVol = 0;
    private boolean showing;
    private final AssetManager am;

    public SettingsMenu(final Skin skin, AssetManager am, I18NBundle locale) {

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
            b2.setPosition(x, -80);
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
            x += 80;
        }

        float volX = screenW / 2f;
        float volY = screenH / 6f;
        //sfx
        lblSfx = new Label(locale.get("settings.efectos"), labelStyle);
        lblSfx.setPosition(volX, volY);
        compSettings.add(lblSfx);
        lblSfx.setVisible(false);
        volX += 120;
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
        volX += 120;
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

        Gdx.app.log("aqui",sfxVol+" "+musicVol);

    }

    public ArrayList<Actor> getCompSettings() {
        return compSettings;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;

        for (Actor a : compSettings) {
            a.setVisible(showing);
        }

        if (showing){
            setDesdeUserSettings();
        }
    }

    private String getName() {
        String retorno = "";
        for (Label a : lblsNombre) {
            retorno += a.getText();
        }

        return retorno;
    }

    public Button getBackBtn() {
        return btnAtras;
    }
}
