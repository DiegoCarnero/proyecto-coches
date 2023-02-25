package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.proyectocoches.screens.LoadingScreen;
import com.mygdx.proyectocoches.utils.GameSettings;

import java.util.ArrayList;

public class EventMenu {

    private final ArrayList<Actor> compEvento = new ArrayList<>();

    private final Label lblOpos;
    private final Label lblVueltas;
    private final Label lblModo;

    private final Button btnCircuito;
    private final Label lblCircuito;
    private final Button btnModo;
    private final Label lblModoName;
    private final Button btnAtras;
    private final Button btnJugar;
    private final Label lblJugar;
    private final Label lblNumOpos;
    private final Button opoUp;
    private final Button opoDown;
    private final Label lblNumVueltas;
    private final Button vueltaUp;
    private final Button vueltaDown;

    private final String[] modos = new String[]{"carrera", "contrarreloj"};
    private final String[] circuitos = new String[]{"test_loop", "track_1"};
    private final String[] circuitoNames = new String[]{"Dirtona 500", "track_1"};
    private boolean showing;
    private int contModo = 0;
    private int contCircuito = 0;
    private int numOpos = 4;
    private int numVueltas = 3;
    private final Sprite s;
    private final AssetManager am;
    final int screenH = Gdx.graphics.getHeight();
    final int screenW = Gdx.graphics.getWidth();

    public Sprite getS() {
        return s;
    }

    public EventMenu(final Skin skin, final Game g, final AssetManager am, final I18NBundle bundle) {
        this.am = am;
        s = new Sprite((Texture) am.get("worlds/test_loop_mini.png"));
        s.setPosition(screenW / 2f, 2 * screenH / 5f);
        s.setSize(screenH / 17f, screenH / 17f);

        final I18NBundle locale = am.get("locale/locale");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        modos[0] = locale.get("modos.carrera");
        modos[1] = locale.get("modos.contrarreloj");
        circuitoNames[0] = locale.get("circuitos.test_loop");
        circuitoNames[1] = locale.get("circuitos.track_1");

        btnCircuito = new TextButton("", skin);
        btnCircuito.setSize(screenW / 5f, screenH / 9f);
        btnCircuito.setPosition(screenW / 2f, -3 * screenH / 10f);
        btnCircuito.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contCircuito = contCircuito == circuitos.length - 1 ? 0 : contCircuito + 1;
                s.set(new Sprite((Texture) am.get("worlds/" + circuitos[contCircuito] + "_mini.png")));
                s.setX(screenW / 2f);
                s.setY(2 * screenH / 5f);
                s.setSize(screenH / 7f, screenH / 7f);
                lblCircuito.setText(circuitoNames[contCircuito]);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnCircuito.setVisible(false);

        lblCircuito = new Label(circuitoNames[0], labelStyle);
        lblCircuito.setSize(screenW / 5f, screenH / 9f);
        lblCircuito.setAlignment(1);
        lblCircuito.setTouchable(Touchable.disabled);
        lblCircuito.setWrap(true);
        lblCircuito.setPosition(screenW / 2f, -3 * screenH / 10f);
        lblCircuito.setVisible(false);

        lblModo = new Label(locale.get("event.modo"), labelStyle);
        lblModo.setSize(screenH / 10f, screenH / 10f);
        lblModo.setPosition(screenW / 12f, screenH / 2f - 5 * screenH / 10f);
        lblModo.setAlignment(1);
        lblModo.setVisible(false);

        lblNumOpos = new Label(numOpos + "", labelStyle);
        lblNumOpos.setSize(screenH / 10f, screenH / 10f);
        lblNumOpos.setPosition(screenW / 12f + screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        lblNumOpos.setAlignment(1);
        lblNumOpos.setVisible(false);

        opoUp = new TextButton(">", skin);
        opoUp.setSize(screenH / 10f, screenH / 10f);
        opoUp.setPosition(screenW / 12f + 2 * screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        opoUp.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == 17 ? 0 : numOpos + 1;
                lblNumOpos.setText(numOpos);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        opoUp.setVisible(false);

        opoDown = new TextButton("<", skin);
        opoDown.setSize(screenH / 10f, screenH / 10f);
        opoDown.setPosition(screenW / 12f, screenH / 2f - 2 * screenH / 10f);
        opoDown.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == 1 ? 17 : numOpos - 1;
                lblNumOpos.setText(numOpos);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        opoDown.setVisible(false);

        lblNumVueltas = new Label(numVueltas + "", labelStyle);
        lblNumVueltas.setSize(screenH / 10f, screenH / 10f);
        lblNumVueltas.setPosition(screenW / 12f + screenH / 10f, screenH / 2f - 4 * screenH / 10f);
        lblNumVueltas.setVisible(false);
        lblNumVueltas.setAlignment(1);

        lblVueltas = new Label(locale.get("event.vueltas"), labelStyle);
        lblVueltas.setPosition(screenW / 12f, screenH / 2f - 3 * screenH / 10f);
        lblVueltas.setTouchable(Touchable.disabled);
        lblVueltas.setVisible(false);

        lblOpos = new Label(locale.get("event.opos"), labelStyle);
        lblOpos.setSize(screenH / 10f, screenH / 10f);
        lblOpos.setPosition(screenW / 12f, screenH / 2f - screenH / 10f);
        lblOpos.setTouchable(Touchable.disabled);
        lblOpos.setVisible(false);

        vueltaUp = new TextButton(">", skin);
        vueltaUp.setSize(screenH / 10f, screenH / 10f);
        vueltaUp.setPosition(screenW / 12f + 2 * screenH / 10f, screenH / 2f - 4 * screenH / 10f);
        vueltaUp.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 9 ? 1 : numVueltas + 1;
                lblNumVueltas.setText(numVueltas);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vueltaUp.setVisible(false);

        vueltaDown = new TextButton("<", skin);
        vueltaDown.setSize(screenH / 10f, screenH / 10f);
        vueltaDown.setPosition(screenW / 12f, screenH / 2f - 4 * screenH / 10f);
        vueltaDown.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 1 ? 9 : numVueltas - 1;
                lblNumVueltas.setText(numVueltas);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vueltaDown.setVisible(false);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        lblModoName = new Label(modos[contModo], labelStyle);
        lblModoName.setSize(screenW / 7f, screenH / 10f);
        lblModoName.setPosition(screenW / 12f, screenH / 2f - 6 * screenH / 10f);
        lblModoName.setTouchable(Touchable.disabled);
        lblModoName.setAlignment(1);
        lblModoName.setVisible(false);

        btnModo = new Button(skin);
        btnModo.setSize(screenW / 7f, screenH / 10f);
        btnModo.setPosition(screenW / 12f, screenH / 2f - 6 * screenH / 10f);
        btnModo.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contModo = contModo == modos.length - 1 ? 0 : contModo + 1;
                lblModoName.setText(modos[contModo]);

                // deshabilitar si contrarreloj
                opoDown.setVisible(contModo != 1);
                opoUp.setVisible(contModo != 1);
                lblNumOpos.setVisible(contModo != 1);
                vueltaDown.setVisible(contModo != 1);
                vueltaUp.setVisible(contModo != 1);
                lblNumVueltas.setVisible(contModo != 1);
                lblOpos.setVisible(contModo != 1);
                lblVueltas.setVisible(contModo != 1);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnModo.setVisible(false);

        lblJugar = new Label(locale.get("event.empezar"), labelStyle);
        lblJugar.setAlignment(1);
        lblJugar.setSize(screenW / 10f, screenH / 10f);
        lblJugar.setPosition(screenW / 2f - screenW / 10f, -screenH / 2f);
        lblJugar.setVisible(false);
        lblJugar.setTouchable(Touchable.disabled);

        btnJugar = new TextButton("", skin);
        btnJugar.setSize(screenW / 10f, screenH / 10f);
        btnJugar.setPosition(screenW / 2f - screenW / 10f, -screenH / 2f);
        btnJugar.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameSettings gs = new GameSettings(numOpos, circuitos[contCircuito], contModo, numVueltas);
                setShowing(false);
                g.setScreen(new LoadingScreen(am, g, skin, gs));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnJugar.setVisible(false);

        compEvento.add(btnModo);
        compEvento.add(lblModoName);
        compEvento.add(opoUp);
        compEvento.add(opoDown);
        compEvento.add(btnAtras);
        compEvento.add(btnJugar);
        compEvento.add(lblJugar);
        compEvento.add(lblVueltas);
        compEvento.add(lblOpos);
        compEvento.add(btnCircuito);
        compEvento.add(lblCircuito);
        compEvento.add(lblNumOpos);
        compEvento.add(vueltaDown);
        compEvento.add(vueltaUp);
        compEvento.add(lblNumVueltas);
        compEvento.add(lblModo);

    }

    public void setShowing(boolean showing) {
        this.showing = showing;
        contCircuito = 0;
        contModo = 0;
        s.set(new Sprite((Texture) am.get("worlds/" + circuitos[contCircuito] + "_mini.png")));
        s.setX(screenW / 2f);
        s.setY(2 * screenH / 5f);
        lblCircuito.setText(circuitoNames[contCircuito]);
        lblModoName.setText(modos[contModo]);
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
