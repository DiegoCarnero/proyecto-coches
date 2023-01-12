package com.mygdx.proyectocoches.utils;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_META;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class miContactListener implements ContactListener {

    boolean cruzandoMeta;
    public miContactListener(){
        super();
    }

    @Override
    public void beginContact(Contact contact) {

        int a = contact.getFixtureA().getFilterData().categoryBits;
        int b = contact.getFixtureB().getFilterData().categoryBits;

        if (a == CAT_CIRCUITO_META || b == CAT_CIRCUITO_META){
            cruzandoMeta = true;
        }
    }

    @Override
    public void endContact(Contact contact) {

        int a = contact.getFixtureA().getFilterData().categoryBits;
        int b = contact.getFixtureB().getFilterData().categoryBits;

        if ((a == CAT_CIRCUITO_META || b == CAT_CIRCUITO_META)){
            cruzandoMeta = false;
            Gdx.app.log("meta cruzada","completado");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        Gdx.app.log("test pre",contact.getFixtureA().getFilterData().categoryBits+ " "+contact.getFixtureB().getFilterData().categoryBits);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

        Gdx.app.log("test post",contact.getFixtureA().getFilterData().categoryBits+ " "+contact.getFixtureB().getFilterData().categoryBits);
    }
}
