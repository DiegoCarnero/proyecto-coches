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

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public boolean isEnVuelta() {
        return enVuelta;
    }

    public void setEnVuelta(boolean enVuelta) {
        this.enVuelta = enVuelta;
    }

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

    public boolean isCruzadoS1() {
        return cruzadoS1;
    }

    public void setCruzadoS1(boolean cruzadoS1) {
        this.cruzadoS1 = cruzadoS1;
    }

    public void setCruzadoS2(boolean cruzadoS2) {
        this.cruzadoS2 = cruzadoS2;
    }

    public void setCruzadoS3(boolean cruzadoS3) {
        this.cruzadoS3 = cruzadoS3;
    }

    public void setPrimeraVuelta(boolean b) {
        primeraVuelta = b;
    }

    public boolean isPrimeraVuelta() {
        return primeraVuelta;
    }

    public boolean hasCruzadoS1() {
        return cruzadoS1;
    }

    public boolean hasCruzadoS2() {
        return cruzadoS2;
    }

    public boolean hasCruzadoS3() {
        return cruzadoS3;
    }

    public void CompletadoSector1(boolean b) {
        cruzadoS1 = b;
    }

    public void CompletadoSector2(boolean b) {
        cruzadoS2 = b;
    }

    public void CompletadoSector3(boolean b) {
        cruzadoS3 = b;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
