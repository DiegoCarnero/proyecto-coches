package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;

public class TutorialMenu {

    private ArrayList<Actor> compTutorial = new ArrayList<>();
    private final Label lblTitulo;
    private final Label lblDescip;
    private final Button btnSig;
    private final Button btnPrev;
    private final String[] titulos = {"tit1", "tit2", "tit3", "tit4", "tit5"};
    private final String[] descrips = {"descrip1", "descrip2", "descrip3", "descrip4", "descrip5"};
    private final Sprite[] sprites;
    private int cont = 0;
    private final Button btnAtras;
    private boolean showing;

    private Sprite s;
    public Sprite getS() {
        return s;
    }

    public TutorialMenu(final Skin skin, final AssetManager am) {

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        int screenH;
        screenH = Gdx.graphics.getHeight();
        int screenW;
        screenW = Gdx.graphics.getWidth();

        I18NBundle locale = am.get("locale/locale");
        titulos[0] = locale.get("tutorial.t1");
        descrips[0] = locale.get("tutorial.d1");
        titulos[1] = locale.get("tutorial.t2");
        descrips[1] = locale.get("tutorial.d2");
        titulos[2] = locale.get("tutorial.t3");
        descrips[2] = locale.get("tutorial.d3");
        titulos[3] = locale.get("tutorial.t4");
        descrips[3] = locale.get("tutorial.d4");
        titulos[4] = locale.get("tutorial.t5");
        descrips[4] = locale.get("tutorial.d5");

        lblTitulo = new Label(titulos[0], labelStyle);
        lblTitulo.setPosition(screenW * 2 / 4f, screenH / 2f - screenH / 10f);
        lblTitulo.setVisible(false);
        lblDescip = new Label(descrips[0], labelStyle);
        lblDescip.setPosition(screenW * 2 / 4f, -screenH / 10f);
        lblDescip.setWidth(screenW * 2 / 5f);
        lblDescip.setWrap(true);
        lblDescip.setVisible(false);

        sprites = new Sprite[5];
        sprites[0] = new Sprite((Texture) am.get("ui/tutorial/tutorial1.jpg"));
        sprites[0].setPosition(screenH / 20f, 5 * screenH / 20f);
        sprites[1] = new Sprite((Texture) am.get("ui/tutorial/tutorial1.jpg"));
        sprites[1].setPosition(screenH / 20f, 5 * screenH / 20f);
        sprites[2] = new Sprite((Texture) am.get("ui/tutorial/tutorial1.jpg"));
        sprites[2].setPosition(screenH / 20f, 5 * screenH / 20f);
        sprites[3] = new Sprite((Texture) am.get("ui/tutorial/tutorial1.jpg"));
        sprites[3].setPosition(screenH / 20f, 5 * screenH / 20f);
        sprites[4] = new Sprite((Texture) am.get("ui/tutorial/tutorial5.jpg"));
        sprites[4].setPosition(screenH / 20f, 5 * screenH / 20f);

        s = sprites[0];

        btnSig = new TextButton(">", skin);
        btnSig.setSize(screenH / 10f, screenH / 10f);
        btnSig.setPosition(15 * screenW / 20f, -screenH / 2f + screenH / 10f);
        btnSig.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                cont = cont == titulos.length - 1 ? 0 : cont + 1;
                lblTitulo.setText(titulos[cont]);
                lblDescip.setText(descrips[cont]);
                s = sprites[cont];
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnSig.setVisible(false);

        btnPrev = new TextButton("<", skin);
        btnPrev.setSize(screenH / 10f, screenH / 10f);
        btnPrev.setPosition(15 * screenW / 20f - 2 * screenH / 10f, -screenH / 2f + screenH / 10f);
        btnPrev.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                cont = cont == 0 ? titulos.length - 1 : cont - 1;
                lblTitulo.setText(titulos[cont]);
                lblDescip.setText(descrips[cont]);
                s = sprites[cont];
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnPrev.setVisible(false);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        compTutorial.add(btnAtras);
        compTutorial.add(lblDescip);
        compTutorial.add(btnPrev);
        compTutorial.add(btnSig);
        compTutorial.add(lblTitulo);

    }

    public Button getBackBtn() {
        return btnAtras;
    }

    public ArrayList<Actor> getCompTutorial() {
        return compTutorial;
    }

    public void setShowing(boolean b) {
        showing = b;
        for (Actor a : compTutorial) {
            a.setVisible(showing);
        }
    }

    public boolean isShowing() {
        return showing;
    }
}
