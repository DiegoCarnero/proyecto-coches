package com.mygdx.proyectocoches.gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.entidades.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RaceManager implements Gamemode {

    private HashMap<Competidor, Integer> competidores;
    private CatmullRomSpline<Vector2> spline;
    private final int nVueltas;
    private int nVueltaJugador = 0;
    private int posJugador = 0;
    private boolean jugadorAcabo;

    public boolean isJugadorAcabo() {
        return jugadorAcabo;
    }

    public int getnCompetidores() {
        return nCompetidores;
    }

    private int nCompetidores;

    public int getVueltaJugador() {
        return nVueltaJugador;
    }

    public RaceManager(ArrayList<Competidor> competidores, CatmullRomSpline<Vector2> s, int nVueltas) {
        this.competidores = new HashMap<>();
        for (Competidor c : competidores) {
            this.competidores.put(c, 0);
        }
        nCompetidores = this.competidores.size();
        this.nVueltas = nVueltas;
        spline = s;
    }

    public void update() {
        for (Competidor c : competidores.keySet()) {
            int a = c.getVuelta();
            int nuevoVal = spline.nearest(c.getPosition()) + (a * 1000);
            competidores.put(c, nuevoVal);
        }
    }

    @Override
    public void setCruzandoMeta(boolean b, Competidor userData) {
        userData.setCruzandoMeta(b);
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
    public void NuevaVuelta(Competidor userData) {

        if (userData.isPrimeraVuelta() || (userData.isEnVuelta() && userData.hasCruzadoS3() && userData.hasCruzadoS1() && userData.hasCruzadoS2())) {
            int nVuelta = userData.getVuelta();
            userData.setVuelta(nVuelta + 1);
        }
		
        userData.setEnVuelta(true);
        userData.setCruzadoS1(false);
        userData.setCruzadoS2(false);
        userData.setCruzadoS3(false);

        if (userData.getClass() == Jugador.class) {
            nVueltaJugador = userData.getVuelta();
            if(nVueltaJugador > nVueltas){
                jugadorAcabo = true;
            }
        }
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

    @Override
    public String toString() {
        String listaComp = "";
        int cont = 1;

        List<Integer> auxList = new ArrayList<>();
        for (Map.Entry<Competidor, Integer> entry : competidores.entrySet()) {
            auxList.add(entry.getValue());
        }
        // eliminar repetidos metiendolo en un HashSet
        Set<Integer> set = new HashSet<Integer>(auxList);
        auxList.clear();
        // volver a convertirlo a arraylist para ordenarlo
        auxList.addAll(set);

        Collections.sort(auxList);
        Collections.reverse(auxList);

        for (Integer num : auxList) {
            for (Map.Entry<Competidor, Integer> entry : competidores.entrySet()) {
                if (entry.getValue().equals(num)) {
                    listaComp += String.format(Locale.ROOT, "%d. %s\n", cont, entry.getKey().getNombre());
                    if (entry.getKey() instanceof Jugador) {
                        if(!jugadorAcabo) {
                            posJugador = cont;
                        }
                    }
                    cont++;
                }
            }
        }

        return listaComp;
    }

    public int getvVueltas() {
        return nVueltas;
    }

    public int getPosJugador() {
        return posJugador;
    }

}
