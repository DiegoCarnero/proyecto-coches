package com.mygdx.proyectocoches.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Competidor {

    private boolean enVuelta = false;
    private boolean primeraVuelta = true;
    private boolean cruzandoMeta;
    private boolean cruzandoS1;
    private boolean cruzandoS2;
    private boolean cruzandoS3;
    private boolean cruzadoS1;
    private boolean cruzadoS2;
    private boolean cruzadoS3;
    private final Body body;

    public Competidor(Body b){
        this.body = b;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition(){
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

    public void setPrimeraVuelta(boolean b){
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
}
