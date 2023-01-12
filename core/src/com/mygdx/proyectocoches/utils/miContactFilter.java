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

        Filter dataA = fixtureA.getFilterData();
        Filter dataB = fixtureB.getFilterData();

        Gdx.app.log("shouldCollide",(dataA.categoryBits & dataB.maskBits)+"");

        if((dataA.categoryBits & dataB.maskBits) == 0x1){// jugador->circuito,meta
            return true;
        } else if((dataB.categoryBits & dataA.maskBits) == 0x1){// jugador->circuito,meta
            return true;
        }else{
            return false;
        }
    }
}
