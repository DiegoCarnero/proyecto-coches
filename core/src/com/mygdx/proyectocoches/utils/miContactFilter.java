package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class miContactFilter implements ContactFilter {

    public miContactFilter(){
        super();
    }

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        return true;
    }
}
