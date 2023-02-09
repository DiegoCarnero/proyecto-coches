package com.mygdx.proyectocoches.utils;

public class GameSettings {

    private final int numOpos;
    private final String circuito;
    private final int modo;
    private final int camMode;
    private final String PlayerName;
    private final int nVueltas;

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

    public GameSettings(int numOpos, String circuito, int modo, int camMode, String playerName,int nVueltas) {
        this.numOpos = numOpos;
        this.circuito = circuito;
        this.modo = modo;
        this.camMode = camMode;
        PlayerName = playerName;
        this.nVueltas = nVueltas;
    }
}
