package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;

public class miContactListener implements ContactListener {

    TimeTrialManager rlm;

    public miContactListener(TimeTrialManager rlm) {
        super();
        this.rlm = rlm;
    }

    @Override
    public void beginContact(Contact contact) {

        int a = contact.getFixtureA().getFilterData().categoryBits;
        int b = contact.getFixtureB().getFilterData().categoryBits;

        if (((a | b) == 0x5)) {// jugador->meta
            rlm.setCruzandoMeta(true);
            if (!rlm.isPrimeraVuelta() && rlm.hasCruzadoS1() && rlm.hasCruzadoS2()) {
                rlm.CompletadoSector3();
                rlm.setCruzandoS1(false);
                rlm.setCruzandoS2(false);
                rlm.setCruzandoS3(false);
            }
        } else if ((a | b) == 0x11) {// jugador->sector1
            if (rlm.isCruzandoMeta()) {
                rlm.NuevaVuelta();
                rlm.setPrimeraVuelta(false);
                rlm.setCruzandoMeta(false);
                rlm.setCruzandoS1(true);
            }
        } else if ((a | b) == 0x21) {// jugador->sector2
            if (rlm.isCruzandoS1() && !rlm.isCruzandoS2() && !rlm.isCruzandoS3()) {
                rlm.CompletadoSector1();
                rlm.setCruzandoS2(true);
            }
        } else if ((a | b) == 0x31) {// jugador->sector3
            if (rlm.isCruzandoS1() && rlm.isCruzandoS2() && !rlm.isCruzandoS3()) {
                rlm.CompletadoSector2();
                rlm.setCruzandoS3(true);
            }
        } else if ((a | b) == 0x48) {// IA->sensor
            if (contact.getFixtureA().getUserData() == contact.getFixtureB().getUserData()) {
                ((CocheIA) contact.getFixtureA().getUserData()).nextDestino();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
