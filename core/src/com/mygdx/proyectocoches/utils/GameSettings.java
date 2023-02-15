package com.mygdx.proyectocoches.utils;

import com.badlogic.gdx.utils.I18NBundle;

public class GameSettings {

    private final int numOpos;
    private final String circuito;
    private final int modo;
    private final int camMode;
    private final String PlayerName;
    private final int nVueltas;
    private final I18NBundle bundle;

    public I18NBundle getBundle() {
        return bundle;
    }

    public int getnVueltas() {
        return nVueltas;
    }

    public int getNumOpos() {
        return numOpos;
    }

    public String getCircuito() {
        return circuito;
    }

    public int getModo() {
        return modo;
    }

    public int getCamMode() {
        return camMode;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public GameSettings(int numOpos, String circuito, int modo, int camMode, String playerName, int nVueltas, I18NBundle bundle) {
        this.numOpos = numOpos;
        this.circuito = circuito;
        this.modo = modo;
        this.camMode = camMode;
        PlayerName = playerName;
        this.nVueltas = nVueltas;
        this.bundle = bundle;
    }
}
