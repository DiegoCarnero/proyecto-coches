package com.mygdx.proyectocoches.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Competidor {

    /**
     * Numero de vueltas completadas
     */
    private int vuelta = 0;
    /**
     * Si el {@link Competidor} a comenzado una vuelta. Se establece a true cuando se cruza la meta por primera vez
     */
    private boolean enVuelta = false;
    /**
     * Si el {@link Competidor} está en la primera vuelta del Evento
     */
    private boolean primeraVuelta = true;
    /**
     * Si el {@link Competidor} está cruzando la meta
     */
    private boolean cruzandoMeta;
    /**
     * Si el {@link Competidor} está cruzando el Sector 1
     */
    private boolean cruzandoS1;
    /**
     * Si el {@link Competidor} está cruzando el Sector 2
     */
    private boolean cruzandoS2;
    /**
     * Si el {@link Competidor} está cruzando el Sector 3
     */
    private boolean cruzandoS3;
    /**
     * Si el {@link Competidor} ha completado el Sector 1
     */
    private boolean cruzadoS1;
    /**
     * Si el {@link Competidor} ha completado el Sector 2
     */
    private boolean cruzadoS2;
    /**
     * Si el {@link Competidor} ha completado el Sector 3
     */
    private boolean cruzadoS3;
    /**
     * {@link Body} asociado a este Competidor
     */
    private final Body body;
    /**
     * Nombre de este {@link Competidor}
     */
    private String nombre;
    /**
     * {@link Sprite} que representa a este Competidor
     */
    private final Sprite s;

    /**
     * Devuelve el Sprite representa a este Competidor
     * @return sprite asociado a este {@link Competidor}
     */
    public Sprite getS() {
        return s;
    }

    /**
     * Devuelve el numero de vuelta actual
     * @return numero de vuelta
     */
    public int getVuelta() {
        return vuelta;
    }

    /**
     * Establece el número de vuelta en la que se encuentra este {@link Competidor}
     * @param vuelta nuevo número de vuelta
     */
    public void setVuelta(int vuelta) {
        this.vuelta = vuelta;
    }

    /**
     * 
     * @param nombre nombre de este competido
     * @param b {@link Body} de este competido
     * @param s textura asociada a este competidor
     */
    public Competidor(String nombre, Body b, Texture s) {
        this.nombre = nombre;
        this.body = b;
        this.s = new Sprite(s);
    }

    /**
     * Devuelve el cuerpo de este Competidor
     * @return cuerpo del competidor
     */
    public Body getBody() {
        return body;
    }

    /**
     * Devuelve la posición de Competidor en el World Box2d en el que se encuentra
     * @return coordenadas del {@link Body}
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Devuelve si este Competidor a compenzado una vuelta
     * @return true si se encuentra en vuelta, false si no
     */
    public boolean isEnVuelta() {
        return enVuelta;
    }

    /**
     * Establece si este Competidor ha comenzado una vuelta. Se establece cuando el Competidor cruza la meta
     * @param enVuelta si este Competidor está en vuelta
     */
    public void setEnVuelta(boolean enVuelta) {
        this.enVuelta = enVuelta;
    }

    /**
     * Indica si el Competidor está cruzando la meta. Se usa para verificar que el Competidor lleva la dirección correcta cuando cruza el sensor del Sector1
     * @return
     */
    public boolean isCruzandoMeta() {
        return cruzandoMeta;
    }

    /**
     * Establece si este Competidor ha cruzado la meta
     * @param cruzandoMeta si este Competidor ha cruzado la meta
     */
    public void setCruzandoMeta(boolean cruzandoMeta) {
        this.cruzandoMeta = cruzandoMeta;
    }

    /**
     * Indica si el competidor se encuentra en el Sector1. Si 'true' el Competidor ha cruzado la meta y el sensor del Sector1 en ese orden
     * @return true si está cruzando el Sector1, false si no
     */
    public boolean isCruzandoS1() {
        return cruzandoS1;
    }

    /**
     * Establece si este Competidor está cruzando el Sector1
     * @param cruzandoS1 si este Competidor se encuentra en el Sector1
     */
    public void setCruzandoS1(boolean cruzandoS1) {
        this.cruzandoS1 = cruzandoS1;
    }

    /**
     * Indica si el competidor se encuentra en el Sector2. Si 'true' el Competidor ha cruzado la meta, el Sector1 y el sensor del Sector2 en ese orden
     * @return true si está cruzando el Sector2, false si no
     */
    public boolean isCruzandoS2() {
        return cruzandoS2;
    }

    /**
     * Establece si este Competidor está cruzando el Sector2
     * @param cruzandoS2 si este Competidor se encuentra en el Sector2
     */
    public void setCruzandoS2(boolean cruzandoS2) {
        this.cruzandoS2 = cruzandoS2;
    }

    /**
     * Indica si el competidor se encuentra en el Sector3. Si 'true' el Competidor ha cruzado la meta, el Sector1, el Sector2 y el sensor Sector3 en ese orden
     * @return true si está cruzando el Sector3, false si no
     */
    public boolean isCruzandoS3() {
        return cruzandoS3;
    }

    /**
     * Establece si este Competidor está cruzando el Sector3
     * @param cruzandoS3 si este Competidor se encuentra en el Sector3
     */
    public void setCruzandoS3(boolean cruzandoS3) {
        this.cruzandoS3 = cruzandoS3;
    }

    /**
     * Establece si el Competidor ha completado el Sector1
     * @param cruzadoS1 si este Competidor ha completado el Sector1
     */
    public void setCruzadoS1(boolean cruzadoS1) {
        this.cruzadoS1 = cruzadoS1;
    }

    /**
     * Establece si el Competidor ha completado el Sector2
     * @param cruzadoS2 si este Competidor ha completado el Sector2
     */
    public void setCruzadoS2(boolean cruzadoS2) {
        this.cruzadoS2 = cruzadoS2;
    }

    /**
     * Establece si el Competidor ha completado el Sector3
     * @param cruzadoS3 si este Competidor ha completado el Sector3
     */
    public void setCruzadoS3(boolean cruzadoS3) {
        this.cruzadoS3 = cruzadoS3;
    }

    /**
     * Establece si este Competidor está en su primera vuelta o no, para las comprobaciones previas a que cruce la meta por primera vez
     * @param b si está en su primera vuelta
     */
    public void setPrimeraVuelta(boolean b) {
        primeraVuelta = b;
    }

    /**
     * Devuelve si este Competidor se encuentra en su primera vuelta.
     * @return true si aún no ha cruzado la meta, false si ya la ha cruzado
     */
    public boolean isPrimeraVuelta() {
        return primeraVuelta;
    }

    /**
     * Devuelve si este Competidor ha completado con éxito en Sector3
     * @return true si ha completado el Sector1, false si no
     */
    public boolean hasCruzadoS1() {
        return cruzadoS1;
    }

    /**
     * Devuelve si este Competidor ha completado con éxito en Sector3
     * @return true si ha completado el Sector2, false si no
     */
    public boolean hasCruzadoS2() {
        return cruzadoS2;
    }

    /**
     * Devuelve si este Competidor ha completado con éxito en Sector3
     * @return true si ha completado el Sector3, false si no
     */
    public boolean hasCruzadoS3() {
        return cruzadoS3;
    }

    /**
     * Establece si este competidor ha completado con ñexito el Sector1
     * @param b si ha completado el Sector1
     */
    public void CompletadoSector1(boolean b) {
        cruzadoS1 = b;
    }

    /**
     * Establece si este competidor ha completado con ñexito el Sector1
     * @param b si ha completado el Sector2
     */
    public void CompletadoSector2(boolean b) {
        cruzadoS2 = b;
    }

    /**
     * Establece si este competidor ha completado con ñexito el Sector1
     * @param b si ha completado el Sector3
     */
    public void CompletadoSector3(boolean b) {
        cruzadoS3 = b;
    }

    /**
     * Devuelve el nombre de este Competidor
     * @return nombre de este Competidor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre que este Competidor tendrá en la lista clasificatoria durante carrera. Si es un Jugador, este nombre se usa para guardar sus tiempos de vuelta
     * @param nombre nombre que se asignará a este Competidor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
