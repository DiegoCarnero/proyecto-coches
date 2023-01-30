package com.mygdx.proyectocoches.gamemodes;

import com.mygdx.proyectocoches.entidades.Competidor;

public interface Gamemode {
    void setCruzandoMeta(boolean b, Competidor userData);

    boolean isPrimeraVuelta(Competidor userData);

    boolean hasCruzadoS1(Competidor userData);

    boolean hasCruzadoS2(Competidor userData);

    void CompletadoSector3(Competidor userData);

    void setCruzandoS1(boolean b, Competidor userData);

    void setCruzandoS2(boolean b, Competidor userData);

    void setCruzandoS3(boolean b, Competidor userData);

    boolean isCruzandoMeta(Competidor userData);

    void NuevaVuelta(Competidor userData);

    void setPrimeraVuelta(boolean b, Competidor userData);

    boolean isCruzandoS1(Competidor userData);

    boolean isCruzandoS2(Competidor userData);

    boolean isCruzandoS3(Competidor userData);

    void CompletadoSector1(Competidor userData);

    void CompletadoSector2(Competidor userData);
}
