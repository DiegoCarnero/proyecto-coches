package com.mygdx.proyectocoches.gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.entidades.Jugador;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TimeTrialManager implements Gamemode {

    private float tMejorVuelta = Float.MAX_VALUE;

    private float tVueltaActual;
    private float tSector1;
    private float tSector2;
    private float tSector3;
    private final Competidor jugador;

    private void GrabaRecords() {
        Json j = new Json();
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.external("records.json"));
        JsonValue t = new JsonValue(tMejorVuelta);
        JsonValue trackRecords = base.get("test_loop");
        if (trackRecords.has(jugador.getNombre())) {
            trackRecords.remove(jugador.getNombre());
        }
        trackRecords.addChild(jugador.getNombre(), t);
        Gdx.app.log("json", base.get("test_loop").toString());

        FileHandle file = Gdx.files.external("records.json");
        file.writeString(base.toString(), false);
    }

    public void update(float delta) {
        tVueltaActual += delta;
    }

    public boolean isCruzandoS1() {
        return jugador.isCruzandoS1();
    }

    public boolean isCruzandoS2() {
        return jugador.isCruzandoS2();
    }

    public TimeTrialManager(Competidor jugador) {
        this.jugador = jugador;
    }

    @Override
    public void NuevaVuelta(Competidor userData) {

        if (jugador.isEnVuelta() && jugador.hasCruzadoS3() && jugador.hasCruzadoS1() && jugador.hasCruzadoS2()) {
            tMejorVuelta = tVueltaActual < tMejorVuelta ? tVueltaActual : tMejorVuelta;
            GrabaRecords();
        }

        jugador.setEnVuelta(true);
        tVueltaActual = 0;
        tSector1 = 0;
        tSector2 = 0;
        tSector3 = 0;
        jugador.setCruzadoS1(false);
        jugador.setCruzadoS2(false);
        jugador.setCruzadoS3(false);

    }

    public void CompletadoSector3() {
        jugador.setCruzadoS3(true);
        tSector3 = tVueltaActual - tSector1 - tSector2;

    }

    public void CompletadoSector2() {
        jugador.setCruzadoS2(true);
        tSector2 = tVueltaActual - tSector1;

    }

    public void CompletadoSector1() {
        jugador.setCruzadoS1(true);
        tSector1 = tVueltaActual;

    }

    public String gettVueltaActualStr() {

        String retorno = "";
        if (jugador.isEnVuelta()) {
            retorno = getTiempoFormat(tVueltaActual);
        }

        return retorno;
    }

    @SuppressWarnings("DefaultLocale")
    private String getTiempoFormat(float t) {

        int milis = (int) (t * 1000 % 1000);
        int secs = (int) t;
        int mins = secs / 60;
        secs = secs % 60;

        return String.format("%d:%02d.%03d", mins, secs, milis);
    }

    public String gettVueltaMejorStr() {

        String retorno = "";
        if (tMejorVuelta < Float.MAX_VALUE) {
            retorno = getTiempoFormat(tMejorVuelta);
        }

        return retorno;
    }

    public String gettSector1Str() {
        String retorno = "";
        if (isCruzandoS1() && !isCruzandoS2()) {
            tSector1 = tVueltaActual;
            retorno = getTiempoFormat(tSector1);
        } else if (isCruzandoS2()) {
            retorno = getTiempoFormat(tSector1);
        }
        return retorno;
    }

    public String gettSector2Str() {
        String retorno = "";
        if (jugador.isCruzandoS2() && !jugador.isCruzandoS3()) {
            tSector2 = tVueltaActual - tSector1;
            retorno = getTiempoFormat(tSector2);
        } else if (jugador.isCruzandoS3()) {
            retorno = getTiempoFormat(tSector2);
        }
        return retorno;
    }

    public String gettSector3Str() {
        String retorno = "";
        if (jugador.isCruzandoS3()) {
            tSector3 = tVueltaActual - tSector1 - tSector2;
            retorno = getTiempoFormat(tSector3);
        }
        return retorno;
    }


    @Override
    public void setCruzandoMeta(boolean cruzandoMeta, Competidor userData) {
        userData.setCruzandoMeta(cruzandoMeta);
    }

    @Override
    public boolean isPrimeraVuelta(Competidor userData) {
        return userData.isPrimeraVuelta();
    }

    @Override
    public boolean hasCruzadoS1(Competidor userData) {
        return userData.hasCruzadoS1();
    }

    @Override
    public boolean hasCruzadoS2(Competidor userData) {
        return userData.hasCruzadoS2();
    }

    @Override
    public void CompletadoSector3(Competidor userData) {
        userData.CompletadoSector3(true);
    }

    @Override
    public void setCruzandoS1(boolean b, Competidor userData) {
        userData.setCruzandoS1(b);
    }

    @Override
    public void setCruzandoS2(boolean b, Competidor userData) {
        userData.setCruzandoS2(b);
    }

    @Override
    public void setCruzandoS3(boolean b, Competidor userData) {
        userData.setCruzandoS3(b);
    }

    @Override
    public boolean isCruzandoMeta(Competidor userData) {
        return userData.isCruzandoMeta();
    }

    @Override
    public void setPrimeraVuelta(boolean b, Competidor userData) {
        userData.setPrimeraVuelta(b);
    }

    @Override
    public boolean isCruzandoS1(Competidor userData) {
        return userData.isCruzandoS1();
    }

    @Override
    public boolean isCruzandoS2(Competidor userData) {
        return userData.isCruzandoS2();
    }

    @Override
    public boolean isCruzandoS3(Competidor userData) {
        return userData.isCruzandoS3();
    }

    @Override
    public void CompletadoSector1(Competidor userData) {
        userData.CompletadoSector1(true);
    }

    @Override
    public void CompletadoSector2(Competidor userData) {
        userData.CompletadoSector2(true);
    }
}
