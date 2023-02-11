package com.mygdx.proyectocoches.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Jugador extends Competidor{

     public Jugador(Body b, Texture s){
         super("Player1",b,s);
     }

}
