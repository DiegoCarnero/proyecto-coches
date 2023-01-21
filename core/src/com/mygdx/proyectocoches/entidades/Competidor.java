package com.mygdx.proyectocoches.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Competidor {

    private final Body body;

    public Competidor(Body b){
        this.body = b;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getWorldPosition(){
        return body.getPosition();
    }
}
