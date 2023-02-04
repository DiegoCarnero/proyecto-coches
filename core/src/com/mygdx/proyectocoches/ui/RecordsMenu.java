package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class RecordsMenu extends Actor {

    private boolean showing;
    private final Label lblRecords;
    private final Button btnAtras;
    private final ArrayList<Actor> compRecords = new ArrayList<>();

    public RecordsMenu(String nomCircuito, Skin skin) {

        lblRecords = new Label("", skin);

        int screenH;
        screenH = Gdx.graphics.getHeight();
        lblRecords.setPosition(screenH, 0);

        lblRecords.setTouchable(Touchable.disabled);
        lblRecords.setVisible(false);

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
                recordsAux += base.get(i).toString();
                Gdx.app.log("toString", base.get(i).toString());
            }
        } else {
            recordsAux += base.get(nomCircuito).toString();
        }
        lblRecords.setText(recordsAux);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        compRecords.add(lblRecords);
        compRecords.add(btnAtras);
    }

    public ArrayList<Actor> getCompRecords() {
        return compRecords;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
        for (Actor a : compRecords) {
            a.setVisible(showing);
        }
    }

    public Actor getBackButton() {
        return btnAtras;
    }
}
