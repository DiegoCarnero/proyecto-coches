package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class CreditsScreen {

    private final Button btnAtras;
    private final ArrayList<Actor> compCredits = new ArrayList<>();
    private final Label creditos;
    private boolean showing;

    public Actor getBackBtn() {
        return btnAtras;
    }

    public CreditsScreen(Skin skin) {
        final int screenH = Gdx.graphics.getHeight();
        final int screenW = Gdx.graphics.getWidth();

        creditos = new Label("Creditos", skin);
        creditos.setAlignment(1);
        creditos.setWrap(true);
        creditos.setSize(screenW, screenH);
        creditos.setPosition(0, -screenH / 2f);
        creditos.setTouchable(Touchable.disabled);
        creditos.setVisible(false);

        char caracter;
        String credsText = "";
        FileHandle fh = Gdx.files.internal("creditos.txt");
        try (Reader r = fh.reader()) {
            caracter = (char) r.read();
            while (caracter != '\uFFFF') {
                credsText += caracter;
                caracter = (char) r.read();
            }
            creditos.setText(credsText);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        compCredits.add(btnAtras);
        compCredits.add(creditos);
    }

    public ArrayList<Actor> getCompCredits() {
        return compCredits;
    }

    public void setShowing(boolean b) {
        showing = b;
        for (Actor a : compCredits) {
            a.setVisible(showing);
        }
    }

    public boolean isShowing() {
        return showing;
    }
}
