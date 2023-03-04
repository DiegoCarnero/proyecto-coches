package com.mygdx.proyectocoches.gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.proyectocoches.entidades.Competidor;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Sistema de gestiones logicas para el modo de juego 'Contrarreloj'
 */
public class TimeTrialManager implements Gamemode {

    /**
     * Mejor tiempo de vuelta en la sesion actual
     */
    private float tMejorVuelta = Float.MAX_VALUE;
    /**
     * Tiempo de vuelta actual
     */
    private float tVueltaActual;
    /**
     * Tiempo en el sector 1
     */
    private float tSector1;
    /**
     * Tiempo en el sector 2
     */
    private float tSector2;
    /**
     * Tiempo en el sector 3
     */
    private float tSector3;
    /**
     * Jugador
     */
    private final Competidor jugador;
    /**
     * Nombre del circuito (no localizado) para gestion interna del programa
     */
    private final String nomCircuito;

    /**
     * Guarda el mejor tiempo de vuelta al archivo de records
     */
    private void GrabaRecords() {
        JsonReader json = new JsonReader();
        JsonValue base;
        FileHandle a = Gdx.files.external("records.json");
        if (!a.exists()) {
            JsonValue template = json.parse(Gdx.files.internal("records_template.json"));
            a.writeString(template.toString(), false);
        }

        base = json.parse(Gdx.files.external("records.json"));
        JsonValue t = new JsonValue(tMejorVuelta);
        JsonValue trackRecords = base.get(nomCircuito);

        if (trackRecords.has(jugador.getNombre())) {
            if (trackRecords.get(jugador.getNombre()).asFloat() > tMejorVuelta) {
                trackRecords.remove(jugador.getNombre());
                trackRecords.addChild(jugador.getNombre(), t);
            }
        } else {
            trackRecords.addChild(jugador.getNombre(), t);
        }

        HashMap<Float, String> hmRecords = new HashMap<>();

        for (int i = trackRecords.size - 1; i > -1; i--) {
            hmRecords.put(trackRecords.get(i).asFloat(), trackRecords.get(i).name);
            trackRecords.remove(i);
        }

        TreeMap<Float, String> orden = new TreeMap<>(hmRecords);

        for (Map.Entry<Float, String> entry : orden.entrySet()) {
            JsonValue t1 = new JsonValue(entry.getKey());
            trackRecords.addChild(entry.getValue(), t1);
        }

        FileHandle file = Gdx.files.external("records.json");
        file.writeString(base.toString(), false);
    }

    /**
     * Actualiza el tiempo de vuelta actual
     *
     * @param delta tiempo pasado desde el ultimo calculo
     */
    public void update(float delta) {
        tVueltaActual += delta;
    }

    /**
     * Indica si el Competidor pasado por parametro se encuentra en el Sector1. Si 'true' el Competidor ha cruzado la meta y el sensor del Sector1 en ese orden
     *
     * @return true si esta cruzando el Sector1, false si no
     */
    public boolean isCruzandoS1() {
        return jugador.isCruzandoS1();
    }

    /**
     * Indica si el competidor se encuentra en el Sector2. Si 'true' el Competidor ha cruzado la meta, el Sector1 y el sensor del Sector2 en ese orden
     *
     * @return true si esta cruzando el Sector2, false si no
     */
    public boolean isCruzandoS2() {
        return jugador.isCruzandoS2();
    }

    /**
     * Sistema de gestiones logicas para el modo de juego 'Contrarreloj'
     *
     * @param jugador jugador
     * @param nomCircuito nombre interno del circuito
     */
    public TimeTrialManager(Competidor jugador, String nomCircuito) {
        this.jugador = jugador;
        this.nomCircuito = nomCircuito;
    }

    /**
     * Se lanza cuando el Competidor pasado por parametro cruza la meta. Resetea los valores de tiempos de los sectores y guarda el nuevo tiempo si es mejor que el ultimo obtenido
     *
     * @param userData competidor
     */
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

    /**
     * Devuelte el tiempo de vuelta como un {@link String} formateado
     *
     * @return tiempo de vuelta
     */
    public String gettVueltaActualStr() {

        String retorno = "";
        if (jugador.isEnVuelta()) {
            retorno = getTiempoFormat(tVueltaActual);
        }

        return retorno;
    }

    /**
     * Formatea a mm:ss.ddd el valor pasado por parametro
     *
     * @param t tiempo a formatear
     * @return tiempo formateado
     */
    @SuppressWarnings("DefaultLocale")
    private String getTiempoFormat(float t) {

        int milis = (int) (t * 1000 % 1000);
        int secs = (int) t;
        int mins = secs / 60;
        secs = secs % 60;

        return String.format("%d:%02d.%03d", mins, secs, milis);
    }

    /**
     * Devuelte el mejor tiempo como un {@link String} formateado
     *
     * @return mejor tiempo de vuelta formateado
     */
    public String gettVueltaMejorStr() {

        String retorno = "";
        if (tMejorVuelta < Float.MAX_VALUE) {
            retorno = getTiempoFormat(tMejorVuelta);
        }

        return retorno;
    }

    /**
     * Devuelte el tiempo en el Sector1 como un {@link String} formateado
     *
     * @return tiempo Sector1 formateado
     */
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

    /**
     * Devuelte el tiempo en el Sector2 como un {@link String} formateado
     *
     * @return tiempo Sector2 formateado
     */
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

    /**
     * Devuelte el tiempo en el Sector3 como un {@link String} formateado
     *
     * @return tiempo Sector3 formateado
     */
    public String gettSector3Str() {
        String retorno = "";
        if (jugador.isCruzandoS3()) {
            tSector3 = tVueltaActual - tSector1 - tSector2;
            retorno = getTiempoFormat(tSector3);
        }
        return retorno;
    }

    /**
     * Establece si el Competidor pasado por parametro ha cruzado la meta
     *
     * @param cruzandoMeta si este Competidor ha cruzado la meta
     * @param userData     competidor
     */
    @Override
    public void setCruzandoMeta(boolean cruzandoMeta, Competidor userData) {
        userData.setCruzandoMeta(cruzandoMeta);
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
     * @return 'true' si el Competidor esta cruzando la meta, 'false' si no
     */
    @Override
    public boolean isCruzandoMeta(Competidor userData) {
        return userData.isCruzandoMeta();
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
}
