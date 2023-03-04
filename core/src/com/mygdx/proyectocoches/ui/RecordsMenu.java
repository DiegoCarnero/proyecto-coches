package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Componentes del submenu de Records
 */
public class RecordsMenu extends Actor {
    /**
     * Si este menu se esta mostrando o no
     */
    private boolean showing;
    /**
     * Texto donde se mostraran los records
     */
    private final Label lblRecords;
    /**
     * Boton para retroceder al menu anterior
     */
    private final Button btnAtras;
    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compRecords = new ArrayList<>();
    /**
     * Nombre interno del circuito
     */
    private final String nomCircuito;
    /**
     * Bundle de texto localizado
     */
    private final I18NBundle locale;

    /**
     * Componentes del submenu de Records
     * <br>Se muestran los records del circuito pasado por parametro.
     * <br>Este objeto tiene un btnAtras, pero la implementacion del InputListener depende de la pantalla donde se implementa.
     * Invocar getBackBtn() para añadirle un Listener
     *
     * @param nomCircuito nombre interno del circuito cuyos records se muestran. Si es una cadena vacia se muestran todos los records con los nombres de sus respectivos circuitos
     * @param skin        skin para la interfaz
     * @param am          AssetManager con los ttf necesarios
     */
    public RecordsMenu(String nomCircuito, Skin skin, AssetManager am) {

        locale = am.get("locale/locale");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        lblRecords = new Label("", labelStyle);
        this.nomCircuito = nomCircuito;
        int screenH;
        screenH = Gdx.graphics.getHeight();
        lblRecords.setPosition(screenH, 0);

        lblRecords.setTouchable(Touchable.disabled);
        lblRecords.setVisible(false);

        lblRecords.setText("");
        lblRecords.setAlignment(1);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        compRecords.add(lblRecords);
        compRecords.add(btnAtras);
    }

    /**
     * Devuelve los componentes de este submenu
     * @return lista con los componentes
     */
    public ArrayList<Actor> getCompRecords() {
        return compRecords;
    }
    /**
     * Si este menu se esta mostrando o no
     * @return 'true' si la pantalla se muestra, 'false' si no
     */
    public boolean isShowing() {
        return showing;
    }
    /**
     * Establece si esta pantalla ha de mostrarse o no
     * @param showing 'true' si la pantalla se muestra, 'false' si no
     */
    public void setShowing(boolean showing) {
        this.showing = showing;
        for (Actor a : compRecords) {
            a.setVisible(showing);
        }

        if (showing) {
            JsonReader json = new JsonReader();
            JsonValue base;
            FileHandle a = Gdx.files.external("records.json");
            if (!a.exists()) {
                JsonValue template = json.parse(Gdx.files.internal("records_template.json"));
                a.writeString(template.toString(), false);
            }

            base = json.parse(Gdx.files.external("records.json"));
            String recordsAux = "";
            if (nomCircuito.equals("")) {
                for (int i = 0; i < base.size; i++) {
                    String circuitNomLocal = locale.get("circuitos." + base.get(i).name);
                    recordsAux += String.format("%s\n", circuitNomLocal);
                    int longi = Math.min(base.get(i).size, 7);
                    for (int j = 0; j < longi; j++) {
                        recordsAux += String.format("%s      %s\n", base.get(i).get(j).name, getTiempoFormat(base.get(i).get(j).asFloat()));
                    }
                    recordsAux += "\n";
                }
            } else {

                int longi = Math.min(base.get(nomCircuito).size, 19);

                for (int i = 0; i < longi; i++) {
                    recordsAux += String.format("%s      %s\n", base.get(nomCircuito).get(i).name, getTiempoFormat(base.get(nomCircuito).get(i).asFloat()));
                }
            }
            lblRecords.setText(recordsAux);
        }
    }

    /**
     * Formatea a mm:ss.ddd el valor pasado por parametro
     *
     * @param t tiempo a formatear
     * @return tiempo formateado
     */
    @SuppressWarnings("DefaultLocale")
    private String getTiempoFormat(float t) {

        int milis = (int) (t * 1000 % 1000);
        int secs = (int) t;
        int mins = secs / 60;
        secs = secs % 60;

        return String.format("%d:%02d.%03d", mins, secs, milis);
    }
    /**
     * Devuelve el boton para retroceder al menu anterior
     * @return btnAtras
     */
    public Actor getBackBtn() {
        return btnAtras;
    }
}
