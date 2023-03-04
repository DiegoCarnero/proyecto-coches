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

/**
 * Componentes de la pantalla de creditos
 */
public class CreditsScreen {

    /**
     * Boton para retroceder al menu anterior
     */
    private final Button btnAtras;
    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compCredits = new ArrayList<>();
    /**
     * Texto de creditos
     */
    private final Label creditos;
    /**
     * Si esta pantalla esta mostrandose o no
     */
    private boolean showing;

    /**
     * Devuelve el boton para retroceder al menu anterior
     * @return boton 'Atras'
     */
    public Actor getBackBtn() {
        return btnAtras;
    }

    /**
     * Componentes de la pantalla de creditos.
     * <br>Este objeto tiene un btnAtras, pero la implementacion del InputListener depende de la pantalla donde se implementa.
     * Invocar getBackBtn() para añadirle un Listener
     *
     * @param skin skin para los actores
     */
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

    /**
     * Devuelve un array con los componentes {@link Actor} de esta pantalla
     * @return array de los componentes de esta pantalla
     */
    public ArrayList<Actor> getCompCredits() {
        return compCredits;
    }

    /**
     * Establece si esta pantalla debe mostrarse o no
     * @param b true si ha de mostrarse, false si no
     */
    public void setShowing(boolean b) {
        showing = b;
        for (Actor a : compCredits) {
            a.setVisible(showing);
        }
    }

    /**
     * Devuelve si los componentes de esta pantalla se estan motrando
     * @return true si se estan mostrando, fals e si no
     */
    public boolean isShowing() {
        return showing;
    }
}
