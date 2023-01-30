package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.gamemodes.Gamemode;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;

public class miContactListener implements ContactListener {

    Gamemode gm;

    public miContactListener(Gamemode gm) {
        super();
        this.gm = gm;
    }

    @Override
    public void beginContact(Contact contact) {

        int a = contact.getFixtureA().getFilterData().categoryBits;
        int b = contact.getFixtureB().getFilterData().categoryBits;
        Competidor bUD = (Competidor) contact.getFixtureB().getUserData();

        if (((a | b) == 0x5)) {// jugador->meta
            gm.setCruzandoMeta(true,bUD);
            if (!gm.isPrimeraVuelta(bUD) && gm.hasCruzadoS1(bUD) && gm.hasCruzadoS2(bUD)) {
                gm.CompletadoSector3(bUD);
                gm.setCruzandoS1(false,bUD);
                gm.setCruzandoS2(false,bUD);
                gm.setCruzandoS3(false,bUD);
            }
        } else if ((a | b) == 0x11) {// jugador->sector1
            if (gm.isCruzandoMeta(bUD)) {
                gm.NuevaVuelta(bUD);
                gm.setPrimeraVuelta(false,bUD);
                gm.setCruzandoMeta(false,bUD);
                gm.setCruzandoS1(true,bUD);
            }
        } else if ((a | b) == 0x21) {// jugador->sector2
            if (gm.isCruzandoS1(bUD) && !gm.isCruzandoS2(bUD) && !gm.isCruzandoS3(bUD)) {
                gm.CompletadoSector1(bUD);
                gm.setCruzandoS2(true,bUD);
            }
        } else if ((a | b) == 0x31) {// jugador->sector3
            if (gm.isCruzandoS1(bUD) && gm.isCruzandoS2(bUD) && !gm.isCruzandoS3(bUD)) {
                gm.CompletadoSector2(bUD);
                gm.setCruzandoS3(true,bUD);
            }
        } else if ((a | b) == 0x48) {// IA->sensor
            if (contact.getFixtureA().getUserData() == bUD) {
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
