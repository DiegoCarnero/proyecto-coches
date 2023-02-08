package com.mygdx.proyectocoches.ui;

import static com.mygdx.proyectocoches.Constantes.track_1_vGrid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
//    private final Label lblNumVueltas;
//    private final Button vueltaUp;
//    private final Button vueltaDown;

    private final String[] modos = new String[]{"Carrera", "Contrarreloj"};
    private final String[] circuitos = new String[]{"test_loop", "track_1"};
    private int contModo = 0;
    private int contCircuito = 0;
    private int numOpos = 0;

    public EventMenu(final Skin skin, final Game g) {

        int screenH = Gdx.graphics.getHeight();
        int screenW = Gdx.graphics.getWidth();

        Texture texture = new Texture("worlds/test_loop_mini.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        btnCircuito = new ImageButton(drawable);
        btnCircuito.setSize(screenH / 7f, screenH / 7f);
        btnCircuito.setPosition(screenW / 2f, screenH / 4f);
        btnCircuito.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                contCircuito = contCircuito == circuitos.length - 1 ? 0 : contCircuito + 1;
                Texture texture = new Texture("worlds/"+circuitos[contCircuito]+"_mini.png");
                Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
                lblCircuito.setText(circuitos[contCircuito]);
                return true;
            }
        });
        btnCircuito.setVisible(false);

        lblCircuito = new Label(circuitos[0], skin);
        lblCircuito.setAlignment(1);
        lblCircuito.setPosition(screenW / 2f, screenH / 4f - screenH / 7f);
        lblCircuito.setVisible(false);

        lblNumOpos = new Label("0", skin);
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
                GameSettings gs = new GameSettings(numOpos,circuitos[contCircuito], contModo,0,"AAA",3);
                g.setScreen(new TestRace(g,skin,gs));
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

    }

    public void setShowing(boolean showing) {

        for (Actor a : compEvento) {
            a.setVisible(true);
        }

    }

    public ArrayList<Actor> getCompEvento() {
        return compEvento;
    }

    public Actor getBackButton() {
        return btnAtras;
    }
}
