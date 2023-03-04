package com.mygdx.proyectocoches.gamemodes;

import com.mygdx.proyectocoches.entidades.Competidor;

public interface Gamemode {

    /**
     * Establece si el Competidor pasado por parametro ha cruzado la meta
     *
     * @param b        si este Competidor ha cruzado la meta
     * @param userData competidor
     */
    void setCruzandoMeta(boolean b, Competidor userData);

    /**
     * Devuelve si el Competidor pasado por parametro se encuentra en su primera vuelta.
     *
     * @param userData competidor
     * @return true si aun no ha cruzado la meta, false si ya la ha cruzado
     */
    boolean isPrimeraVuelta(Competidor userData);

    /**
     * Devuelve si el Competidor pasado por parametro ha completado con exito en Sector3
     *
     * @param userData competidor
     * @return true si ha completado el Sector1, false si no
     */
    boolean hasCruzadoS1(Competidor userData);

    /**
     * Devuelve si el Competidor pasado por parametro ha completado con exito en Sector3
     *
     * @param userData competidor
     * @return true si ha completado el Sector2, false si no
     */
    boolean hasCruzadoS2(Competidor userData);

    /**
     * Establece si el Competidor pasado por parametro ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    void CompletadoSector3(Competidor userData);

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector1
     *
     * @param b        si este Competidor se encuentra en el Sector1
     * @param userData competidor
     */
    void setCruzandoS1(boolean b, Competidor userData);

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector2
     *
     * @param b        si este Competidor se encuentra en el Sector2
     * @param userData competidor
     */
    void setCruzandoS2(boolean b, Competidor userData);

    /**
     * Establece si el Competidor pasado por parametro esta cruzando el Sector3
     *
     * @param b        si este Competidor se encuentra en el Sector3
     * @param userData competidor
     */
    void setCruzandoS3(boolean b, Competidor userData);

    /**
     * Indica si el Competidor esta cruzando la meta. Se usa para verificar que el Competidor pasado por parametro lleva la direccion correcta cuando cruza el sensor del Sector1
     *
     * @param userData competidor
     * @return si esta cruzando la meta
     */
    boolean isCruzandoMeta(Competidor userData);

    void NuevaVuelta(Competidor userData);

    /**
     * Establece si el Competidor pasado por parametro esta en su primera vuelta o no, para las comprobaciones previas a que cruce la meta por primera vez
     *
     * @param b        si esta en su primera vuelta
     * @param userData competidor
     */
    void setPrimeraVuelta(boolean b, Competidor userData);

    /**
     * Indica si el Competidor pasado por parametro se encuentra en el Sector1. Si 'true' el Competidor ha cruzado la meta y el sensor del Sector1 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector1, false si no
     */
    boolean isCruzandoS1(Competidor userData);

    /**
     * Indica si el competidor se encuentra en el Sector2. Si 'true' el Competidor ha cruzado la meta, el Sector1 y el sensor del Sector2 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector2, false si no
     */
    boolean isCruzandoS2(Competidor userData);

    /**
     * Indica si el competidor se encuentra en el Sector3. Si 'true' el Competidor ha cruzado la meta, el Sector1, el Sector2 y el sensor Sector3 en ese orden
     *
     * @param userData competidor
     * @return true si esta cruzando el Sector3, false si no
     */
    boolean isCruzandoS3(Competidor userData);

    /**
     * Establece si este competidor ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    void CompletadoSector1(Competidor userData);

    /**
     * Establece si este competidor ha completado con exito el Sector1
     *
     * @param userData competidor
     */
    void CompletadoSector2(Competidor userData);
}
