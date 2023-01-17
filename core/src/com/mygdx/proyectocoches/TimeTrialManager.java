package com.mygdx.proyectocoches;

public class TimeTrialManager {

    private float tMejorVuelta = Float.MAX_VALUE;
    private float tVueltaActual;
    private float tSector1;
    private float tSector2;
    private float tSector3;
    private long tiempoGlobalStart;
    private boolean enVuelta = false;
    private boolean cruzandoMeta;
    private boolean cruzandoS1;
    private boolean cruzandoS2;
    private boolean cruzandoS3;
    private boolean primeraVuelta = true;

    public boolean isCruzandoMeta() {
        return cruzandoMeta;
    }

    public void setCruzandoMeta(boolean cruzandoMeta) {
        this.cruzandoMeta = cruzandoMeta;
    }

    public boolean isCruzandoS1() {
        return cruzandoS1;
    }

    public void setCruzandoS1(boolean cruzandoS1) {
        this.cruzandoS1 = cruzandoS1;
    }

    public boolean isCruzandoS2() {
        return cruzandoS2;
    }

    public void setCruzandoS2(boolean cruzandoS2) {
        this.cruzandoS2 = cruzandoS2;
    }

    public boolean isCruzandoS3() {
        return cruzandoS3;
    }

    public void setCruzandoS3(boolean cruzandoS3) {
        this.cruzandoS3 = cruzandoS3;
    }

    public boolean isPrimeraVuelta() {
        return primeraVuelta;
    }

    public void setPrimeraVuelta(boolean primeraVuelta) {
        this.primeraVuelta = primeraVuelta;
    }

    public TimeTrialManager() {
    }

    public void NuevaVuelta() {

        if (enVuelta) {
            tMejorVuelta = tVueltaActual < tMejorVuelta? tVueltaActual : tMejorVuelta;
        }

        enVuelta = true;
        tSector1 = 0;
        tSector2 = 0;
        tSector3 = 0;
        tiempoGlobalStart = System.currentTimeMillis();

    }

    public void CompletadoSector3() {

        tSector3 = System.currentTimeMillis() - tiempoGlobalStart - tSector1 - tSector2;

    }

    public void CompletadoSector2() {

        tSector2 = System.currentTimeMillis() - tiempoGlobalStart - tSector1;

    }

    public void CompletadoSector1() {

        tSector1 = System.currentTimeMillis() - tiempoGlobalStart;

    }

    public String gettVueltaActualStr() {

        String retorno = "";
        tVueltaActual = System.currentTimeMillis() - tiempoGlobalStart;
        if(enVuelta){
           retorno = getTiempoFormat(tVueltaActual);
        }

        return retorno;
    }

    private String getTiempoFormat(float t) {

        int milis = (int) (t % 1000);
        int secs = (int) (t / 1000);
        int mins = secs / 60;
        secs = secs % 60;

        return String.format("%d:%02d.%03d", mins, secs, milis);
    }

    public String gettVueltaMejorStr() {

        String retorno = "";
        if (tMejorVuelta < Float.MAX_VALUE){
            retorno = getTiempoFormat(tMejorVuelta);
        }

        return retorno;
    }

    public String gettSector1Str() {
        String retorno = "";
        if (isCruzandoS1() && !isCruzandoS2()) {
            tSector1 = System.currentTimeMillis() - tiempoGlobalStart;
            retorno = getTiempoFormat(tSector1);
        } else if (isCruzandoS2()){
            retorno = getTiempoFormat(tSector1);
        }
        return retorno;
    }

    public String gettSector2Str() {
        String retorno = "";
        if (isCruzandoS2() && !isCruzandoS3()) {
            tSector2 = System.currentTimeMillis() - tiempoGlobalStart - tSector1;
            retorno = getTiempoFormat(tSector2);
        } else if (isCruzandoS3()){
            retorno = getTiempoFormat(tSector2);
        }
        return retorno;
    }

    public String gettSector3Str() {
        String retorno = "";
        if (isCruzandoS3()) {
            tSector3 = System.currentTimeMillis() - tiempoGlobalStart - tSector1 - tSector2;
            retorno = getTiempoFormat(tSector3);
        }
        return retorno;
    }
}
