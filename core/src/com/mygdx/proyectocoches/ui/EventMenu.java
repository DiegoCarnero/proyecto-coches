package com.mygdx.proyectocoches.ui;

import static com.mygdx.proyectocoches.Constantes.track_1_vGrid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.proyectocoches.screens.LoadingScreen;
import com.mygdx.proyectocoches.screens.TestDrive;
import com.mygdx.proyectocoches.screens.TestRace;
import com.mygdx.proyectocoches.utils.GameSettings;

import java.util.ArrayList;

public class EventMenu {

    private final ArrayList<Actor> compEvento = new ArrayList<>();

    private final Button btnCircuito;
    private final Label lblCircuito;
    private final Button btnModo;
    private final Label lblModo;
    private final Button btnAtras;
    private final Button btnJugar;
    private final Label lblNumOpos;
    private final Button opoUp;
    private final Button opoDown;
    private final Label lblNumVueltas;
    private final Button vueltaUp;
    private final Button vueltaDown;

    private final String[] modos = new String[]{"Carrera", "Contrarreloj"};
    private final String[] circuitos = new String[]{"test_loop", "track_1"};
    private final String[] circuitoNames = new String[]{"Dirtona 500", "Circuito Nacional de Zusuka"};
    private boolean showing;
    private int contModo = 0;
    private int contCircuito = 0;
    private int numOpos = 4;
    private int numVueltas = 3;
    private Sprite s;
    private final AssetManager am;

    public Sprite getS() {
        return s;
    }

    public EventMenu(final Skin skin, final Game g, final AssetManager am, final I18NBundle bundle) {
        this.am = am;
        final int screenH = Gdx.graphics.getHeight();
        final int screenW = Gdx.graphics.getWidth();
        s = new Sprite((Texture) am.get("worlds/test_loop_mini.png"));
        s.setPosition(screenW / 2f, screenH / 4f);
        s.setSize(screenH / 7f, screenH / 7f);

        btnCircuito = new TextButton("test_loop", skin);
        btnCircuito.setSize(screenH / 7f, screenH / 7f);
        btnCircuito.setPosition(screenW / 2f, screenH / 4f);
        btnCircuito.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                contCircuito = contCircuito == circuitos.length - 1 ? 0 : contCircuito + 1;
                s.set(new Sprite((Texture) am.get("worlds/" + circuitos[contCircuito] + "_mini.png")));
                s.setX(screenW / 2f);
                s.setY(screenH / 4f);
                s.setSize(screenH / 7f, screenH / 7f);
                lblCircuito.setText(circuitoNames[contCircuito]);
                return true;
            }
        });
        btnCircuito.setVisible(false);

        lblCircuito = new Label(circuitoNames[0], skin);
        lblCircuito.setAlignment(1);
        lblCircuito.setPosition(screenW / 2f, screenH / 4f - screenH / 7f);
        lblCircuito.setVisible(false);

        lblNumOpos = new Label(numOpos + "", skin);
        lblNumOpos.setPosition(2 * screenW / 12f + screenH / 10f, screenH / 2f - screenH / 10f);
        lblNumOpos.setVisible(false);

        opoUp = new TextButton(">", skin);
        opoUp.setSize(screenH / 10f, screenH / 10f);
        opoUp.setPosition(2 * screenW / 12f, screenH / 2f - screenH / 10f);
        opoUp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == track_1_vGrid.length - 1 ? 0 : numOpos + 1;
                lblNumOpos.setText(numOpos);
                return true;
            }
        });
        opoUp.setVisible(false);

        opoDown = new TextButton("<", skin);
        opoDown.setSize(screenH / 10f, screenH / 10f);
        opoDown.setPosition(2 * screenW / 12f + screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        opoDown.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == 0 ? 0 : contModo - 1;
                lblNumOpos.setText(numOpos);
                return true;
            }
        });
        opoDown.setVisible(false);

        lblNumVueltas = new Label(numVueltas + "", skin);
        lblNumVueltas.setPosition(10 * screenW / 12f + screenH / 10f, screenH / 2f - screenH / 10f);
        lblNumVueltas.setVisible(false);

        vueltaUp = new TextButton(">", skin);
        vueltaUp.setSize(screenH / 10f, screenH / 10f);
        vueltaUp.setPosition(10 * screenW / 12f, screenH / 2f - screenH / 10f);
        vueltaUp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 9 ? 1 : numVueltas + 1;
                lblNumVueltas.setText(numVueltas);
                return true;
            }
        });
        vueltaUp.setVisible(false);

        vueltaDown = new TextButton("<", skin);
        vueltaDown.setSize(screenH / 10f, screenH / 10f);
        vueltaDown.setPosition(10 * screenW / 12f + screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        vueltaDown.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 1 ? 1 : numVueltas - 1;
                lblNumVueltas.setText(numVueltas);
                return true;
            }
        });
        vueltaDown.setVisible(false);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        lblModo = new Label(modos[contModo], skin);
        lblModo.setSize(screenW / 10f, screenH / 10f);
        lblModo.setPosition(screenW / 2f - screenW / 20f, screenH / 10f);
        lblModo.setTouchable(Touchable.disabled);
        lblModo.setAlignment(1);
        lblModo.setVisible(false);

        btnModo = new Button(skin);
        btnModo.setSize(screenW / 10f, screenH / 10f);
        btnModo.setPosition(screenW / 2f - screenW / 20f, screenH / 10f);
        btnModo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                contModo = contModo == modos.length - 1 ? 0 : contModo + 1;
                lblModo.setText(modos[contModo]);

                // deshabilitar si contrarreloj
                opoDown.setVisible(contModo != 1);
                opoUp.setVisible(contModo != 1);
                lblNumOpos.setVisible(contModo != 1);
                vueltaDown.setVisible(contModo != 1);
                vueltaUp.setVisible(contModo != 1);
                lblNumVueltas.setVisible(contModo != 1);

                return true;
            }
        });
        btnModo.setVisible(false);

        btnJugar = new TextButton("Empezar", skin);
        btnJugar.setSize(screenW / 10f, screenH / 10f);
        btnJugar.setPosition(0, -screenH / 2f);
        btnJugar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameSettings gs = new GameSettings(numOpos, circuitos[contCircuito], contModo, 0, "AAA", numVueltas,bundle);

                g.setScreen(new LoadingScreen(am,g, skin, gs, "Caricamento"));

                return true;
            }
        });
        btnJugar.setVisible(false);

        compEvento.add(btnModo);
        compEvento.add(lblModo);
        compEvento.add(opoUp);
        compEvento.add(opoDown);
        compEvento.add(btnAtras);
        compEvento.add(btnJugar);
        compEvento.add(btnCircuito);
        compEvento.add(lblCircuito);
        compEvento.add(lblNumOpos);
        compEvento.add(vueltaDown);
        compEvento.add(vueltaUp);
        compEvento.add(lblNumVueltas);

    }

    public void setShowing(boolean showing) {
        this.showing = showing;
        contCircuito = 0;
        contModo = 0;
        lblCircuito.setText(circuitos[contCircuito]);
        lblModo.setText(modos[contModo]);
        for (Actor a : compEvento) {
            a.setVisible(showing);
        }

    }

    public ArrayList<Actor> getCompEvento() {
        return compEvento;
    }

    public Actor getBackBtn() {
        return btnAtras;
    }

    public boolean isShowing() {
        return showing;
    }
}
