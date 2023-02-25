package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.gamemodes.Gamemode;
import com.mygdx.proyectocoches.gamemodes.TimeTrialManager;

/**
 * ContactListener personalizado para gestionar contacto con cuerpos de control e informa al {@link Gamemode} asociado de cada contacto relevante
 *
 * <ul>
 *     <li>jugador->meta</li>
 *     <li>IA->meta</li>
 *     <li>jugador->sector1</li>
 *     <li>IA->sector1</li>
 *     <li>jugador->sector2</li>
 *     <li>IA->sector2</li>
 *     <li>jugador->sector3</li>
 *     <li>IA->sector3</li>
 *     <li>IA->sensor</li>
 * </ul>
 */
public class miContactListener implements ContactListener {

    /**
     * Modo de juego al que se le informara de cada contacto relevante
     */
    Gamemode gm;

    /**
     * ContactListener personalizado para gestionar contacto con cuerpos de control e informa al {@link Gamemode} asociado de cada contacto relevante
     *
     * <ul>
     *     <li>jugador->meta</li>
     *     <li>IA->meta</li>
     *     <li>jugador->sector1</li>
     *     <li>IA->sector1</li>
     *     <li>jugador->sector2</li>
     *     <li>IA->sector2</li>
     *     <li>jugador->sector3</li>
     *     <li>IA->sector3</li>
     *     <li>IA->sensor</li>
     * </ul>
     * @param gm modo de juego al que se informara de cada contacto relevante
     */
    public miContactListener(Gamemode gm) {
        super();
        this.gm = gm;
    }

    /**
     * Called when two fixtures begin to touch.
     * Informa al {@link Gamemode} asociado de que tipo de contacto ha ocurrido
     * <p>
     *     Si es un uno de los contactos es un {@link CocheIA} contra su sensor, advierte de un cambio a la siguiente posicion
     * </p>
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

        int a = contact.getFixtureA().getFilterData().categoryBits;
        int b = contact.getFixtureB().getFilterData().categoryBits;
        Competidor bUD = (Competidor) contact.getFixtureB().getUserData();

        if (((a | b) == 0x5) || ((a | b) == 0xC)) {// jugador->meta || IA->meta
            gm.setCruzandoMeta(true,bUD);

            if (!gm.isPrimeraVuelta(bUD) && gm.hasCruzadoS1(bUD) && gm.hasCruzadoS2(bUD)) {
                gm.CompletadoSector3(bUD);
                gm.setCruzandoS1(false,bUD);
                gm.setCruzandoS2(false,bUD);
                gm.setCruzandoS3(false,bUD);
            }
        } else if ((a | b) == 0x11 || ((a | b) == 0x18)){// jugador->sector1 || IA->sector1
            if (gm.isCruzandoMeta(bUD)) {
                gm.NuevaVuelta(bUD);
                gm.setPrimeraVuelta(false,bUD);
                gm.setCruzandoMeta(false,bUD);
                gm.setCruzandoS1(true,bUD);
            }
        } else if (((a | b) == 0x21) || ((a | b) == 0x28)) {// jugador->sector2 || IA->sector2
            if (gm.isCruzandoS1(bUD) && !gm.isCruzandoS2(bUD) && !gm.isCruzandoS3(bUD)) {
                gm.CompletadoSector1(bUD);
                gm.setCruzandoS2(true,bUD);
            }
        } else if (((a | b) == 0x31) || ((a | b) == 0x38)) {// jugador->sector3 || IA->sector3
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

    /**
     * Called when two fixtures cease to touch.
     *
     * @param contact
     */
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
