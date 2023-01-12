package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class miContactFilter implements ContactFilter {

    public miContactFilter(){
        super();
    }

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        Filter dataA = fixtureA.getFilterData();
        Filter dataB = fixtureB.getFilterData();

        if((dataA.categoryBits & dataB.maskBits) == 0x3){// coche->circuito
            return true;
        } if((dataA.categoryBits & dataB.maskBits) == 0x10){ // jugador->oponente
            return true;
        } else {
            return false;
        }
    }
}
