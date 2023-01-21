package com.mygdx.proyectocoches.utils;

public interface PlayerInput {

    boolean isAdelante();

    boolean isFrenando();

    boolean isAcelerando();

    float getSteerValue();

    float getAccValue();
}
