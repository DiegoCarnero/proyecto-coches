package com.mygdx.proyectocoches.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Competidor que será controlado por el jugador
 */
public class Jugador extends Competidor{

    /**
     * Competidor sobre el cual el jugador tendrá control
     * nombre es establacido a partir del fichero UserSettings.json
     * @param b Cuerpo que representa este comeptidor
     * @param s {@link Sprite} que representa a este Jugador
     */
     public Jugador(Body b, Texture s){
         super("A",b,s);

         JsonValue base;
         JsonReader json = new JsonReader();
         base = json.parse(Gdx.files.external("usersettings.json"));
         setNombre(base.get("nom").asString());
     }

}
