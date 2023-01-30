package com.mygdx.proyectocoches.gamemodes;

import com.mygdx.proyectocoches.entidades.Competidor;

public class RaceManager implements Gamemode {
    @Override
    public void setCruzandoMeta(boolean b, Competidor userData) {

    }

    @Override
    public boolean isPrimeraVuelta(Competidor userData) {
        return false;
    }

    @Override
    public boolean hasCruzadoS1(Competidor userData) {
        return false;
    }

    @Override
    public boolean hasCruzadoS2(Competidor userData) {
        return false;
    }

    @Override
    public void CompletadoSector3(Competidor userData) {

    }

    @Override
    public void setCruzandoS1(boolean b, Competidor userData) {

    }

    @Override
    public void setCruzandoS2(boolean b, Competidor userData) {

    }

    @Override
    public void setCruzandoS3(boolean b, Competidor userData) {

    }

    @Override
    public boolean isCruzandoMeta(Competidor userData) {
        return false;
    }

    @Override
    public void NuevaVuelta(Competidor userData) {

    }

    @Override
    public void setPrimeraVuelta(boolean b, Competidor userData) {

    }

    @Override
    public boolean isCruzandoS1(Competidor userData) {
        return false;
    }

    @Override
    public boolean isCruzandoS2(Competidor userData) {
        return false;
    }

    @Override
    public boolean isCruzandoS3(Competidor userData) {
        return false;
    }

    @Override
    public void CompletadoSector1(Competidor userData) {

    }

    @Override
    public void CompletadoSector2(Competidor userData) {

    }
}
