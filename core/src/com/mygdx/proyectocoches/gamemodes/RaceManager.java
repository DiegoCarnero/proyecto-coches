package com.mygdx.proyectocoches.gamemodes;

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

/**
 * Sistema de gestiones logicas para el modo de juego 'Carrera'
 */
public class RaceManager implements Gamemode {

    /**
     * Coleccion de los Competidores en este evento
     */
    private HashMap<Competidor, Integer> competidores;
    /**
     * Ruta usada para calcular la posicion de los Competidores
     */
    private CatmullRomSpline<Vector2> spline;
    /**
     * Numero de vueltas en este evento
     */
    private final int nVueltas;
    /**
     * Vuelta actual del jugador
     */
    private int nVueltaJugador = 0;
    /**
     * Posicion del jugador
     */
    private int posJugador = 0;
    /**
     * Si el jugador a terminado la carrera o no
     */
    private boolean jugadorAcabo;
    /**
     * Cuenta atras
     */
    private float cuentaAtras = 8;

    /**
     * Devuelve el tiempo actual de la cuenta atras
     *
     * @return
     */
    public float getCuentaAtras() {
        return cuentaAtras;
    }

    /**
     * Indica si el jugador a terminado el evento
     *
     * @return true si el jugador a terminado, false si no
     */
    public boolean isJugadorAcabo() {
        return jugadorAcabo;
    }

    /**
     * Devuelve el numero de competidores en este evento
     *
     * @return numero de competidores
     */
    public int getnCompetidores() {
        return nCompetidores;
    }

    /**
     * numero de competidores
     */
    private final int nCompetidores;

    /**
     * Devuelve el numero de vuelta actual del jugador
     *
     * @return vuelta actual del jugador
     */
    public int getVueltaJugador() {
        return nVueltaJugador;
    }

    /**
     * Calcula el tiempo restante para que empieze la carrera
     *
     * @param delta tiempo pasado desde la ultima vez que se hizo el calculo
     * @return si la cuenta atras ha terminado
     */
    public boolean CuentaAtras(float delta) {

        cuentaAtras -= delta;
        if (cuentaAtras <= 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Sistema de gestiones logicas para el modo de juego 'Carrera'
     */
    public RaceManager(ArrayList<Competidor> competidores, CatmullRomSpline<Vector2> s, int nVueltas) {
        this.competidores = new HashMap<>();
        for (Competidor c : competidores) {
            this.competidores.put(c, 0);
        }
        nCompetidores = this.competidores.size();
        this.nVueltas = nVueltas;
        spline = s;
    }

    /**
     * Actualiza las posiciones de los competidores en la clasificacion
     */
    public void update() {
        for (Competidor c : competidores.keySet()) {
            int a = c.getVuelta();
            int nuevoVal = spline.nearest(c.getPosition()) + (a * 1000);
            competidores.put(c, nuevoVal);
        }
    }

    /**
     * Establece si el Competidor pasado por parametro ha cruzado la meta
     *
     * @param b        si este Competidor ha cruzado la meta
     * @param userData competidor
     */
    @Override
    public void setCruzandoMeta(boolean b, Competidor userData) {
        userData.setCruzandoMeta(b);
    }

    /**
     * Devuelve si el Competidor pasado por parametro se encuentra en su primera vuelta.
     *
     * @param userData competidor
     * @return true si aun no ha cruzado la meta, false si ya la ha cruzado
     */
    @Override
    public boolean isPrimeraVuelta(Competidor userData) {
        return userData.isPrimeraVuelta();
    }

    /**
     * Devuelve si el Competidor pasado por parametro ha completado con exito en Sector3
     *
     * @param userData competidor
     * @return true si ha completado el Sector1, false si no
     */
    @Override
    public boolean hasCruzadoS1(Competidor userData) {
        return userData.hasCruzadoS1();
    }

    /**
     * Devuelve si el Competidor pasado por parametro ha completado con exito en Sector3
     *
     * @param userData competidor
     * @return true si ha completado el Sector2, false si no
     */
    @Override
    public boolean hasCruzadoS2(Competidor userData) {
        return userData.hasCruzadoS2();
    }

    /**
     * Establece si el Competidor pasado por parametro ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    @Override
    public void CompletadoSector3(Competidor userData) {
        userData.CompletadoSector3(true);
    }

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector1
     *
     * @param b        si este Competidor se encuentra en el Sector1
     * @param userData competidor
     */
    @Override
    public void setCruzandoS1(boolean b, Competidor userData) {
        userData.setCruzandoS1(b);
    }

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector2
     *
     * @param b        si este Competidor se encuentra en el Sector2
     * @param userData competidor
     */
    @Override
    public void setCruzandoS2(boolean b, Competidor userData) {
        userData.setCruzandoS2(b);
    }

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector3
     *
     * @param b        si este Competidor se encuentra en el Sector3
     * @param userData competidor
     */
    @Override
    public void setCruzandoS3(boolean b, Competidor userData) {
        userData.setCruzandoS3(b);
    }

    /**
     * Indica si el Competidor esta cruzando la meta. Se usa para verificar que el Competidor pasado por parametro lleva la direccion correcta cuando cruza el sensor del Sector1
     *
     * @param userData competidor
     * @return
     */
    @Override
    public boolean isCruzandoMeta(Competidor userData) {
        return userData.isCruzandoMeta();
    }

    /**
     * Se lanza cuando el Competidor pasado por parametro cruza la meta.
     * Actualiza el numero de vuelta del jugador si el parametro 'userData' de tipo 'Jugador'
     *
     * @param userData competidor
     * @param userData
     */
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
            if (nVueltaJugador > nVueltas) {
                jugadorAcabo = true;
            }
        }
    }

    /**
     * Establece si el Competidor pasado por parametro esta en su primera vuelta o no, para las comprobaciones previas a que cruce la meta por primera vez
     *
     * @param b        si esta en su primera vuelta
     * @param userData competidor
     */
    @Override
    public void setPrimeraVuelta(boolean b, Competidor userData) {
        userData.setPrimeraVuelta(b);
    }

    /**
     * Indica si el Competidor pasado por parametro se encuentra en el Sector1. Si 'true' el Competidor ha cruzado la meta y el sensor del Sector1 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector1, false si no
     */
    @Override
    public boolean isCruzandoS1(Competidor userData) {
        return userData.isCruzandoS1();
    }

    /**
     * Indica si el competidor se encuentra en el Sector2. Si 'true' el Competidor ha cruzado la meta, el Sector1 y el sensor del Sector2 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector2, false si no
     */
    @Override
    public boolean isCruzandoS2(Competidor userData) {
        return userData.isCruzandoS2();
    }

    /**
     * Indica si el competidor se encuentra en el Sector3. Si 'true' el Competidor ha cruzado la meta, el Sector1, el Sector2 y el sensor Sector3 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector3, false si no
     */
    @Override
    public boolean isCruzandoS3(Competidor userData) {
        return userData.isCruzandoS3();
    }

    /**
     * Establece si este competidor ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    @Override
    public void CompletadoSector1(Competidor userData) {
        userData.CompletadoSector1(true);
    }

    /**
     * Establece si este competidor ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    @Override
    public void CompletadoSector2(Competidor userData) {
        userData.CompletadoSector2(true);
    }

    /**
     * Devuelve la lista  de competidores del evento ordenada por posicion
     *
     * @return lista de competidores como String
     */
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
                        if (!jugadorAcabo) {
                            posJugador = cont;
                        }
                    }
                    cont++;
                }
            }
        }

        return listaComp;
    }

    /**
     * Devuelve el numero de vueltas  de esta carrera
     *
     * @return numero de vueltas de esta carrera
     */
    public int getvVueltas() {
        return nVueltas;
    }

    /**
     * Devuelve la posicion del jugador en la clasificacion
     *
     * @return
     */
    public int getPosJugador() {
        return posJugador;
    }

    /**
     * Resetea la cuenta atras a '3'
     */
    public void CuentaAtrasSet() {
        cuentaAtras = 3;
    }
}
