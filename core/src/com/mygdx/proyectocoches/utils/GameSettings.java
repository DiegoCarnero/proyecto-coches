package com.mygdx.proyectocoches.utils;

/**
 * Configuracion del evento
 */
public class GameSettings {

    /**
     * Numero de coponentes
     */
    private final int numOpos;
    /**
     * Nombre del circuito (no localizado) para uso interno
     */
    private final String circuito;
    /**
     * Modo de juego. <p>0 = carrera.</p><p>1=contrarreloj</p>
     */
    private final int modo;
    /**
     * Numero de vueltas (solo modo carrera)
     */
    private final int nVueltas;

    /**
     * Devuelve el numero de vueltas para un evento carrera. Este valor no tiene relevancia en el modo Contrarreloj
     *
     * @return numero de vueltas
     */
    public int getnVueltas() {
        return nVueltas;
    }


    /**
     * Devuelve el numero de oponentes para un evento carrera. Este valor no tiene relevancia en el modo Contrarreloj
     *
     * @return numero de oponentes
     */
    public int getNumOpos() {
        return numOpos;
    }

    /**
     * Devuelve el nombre del circuito (no localizado) para uso interno
     *
     * @return nombre interno del circuito
     */
    public String getCircuito() {
        return circuito;
    }

    /**
     * Devuelve el modo de juego
     *
     * @return entero representado el modo de juego<p>0 = carrera.</p><p>1 = contrarreloj</p>
     */
    public int getModo() {
        return modo;
    }

    /**
     * Conjunto de valores para establecer un nuevo evento
     *
     * @param numOpos numero de oponentes
     * @param circuito nombre interno del circuito
     * @param modo modo de juego. 0 = carrera, 1 = contrarreloj
     * @param nVueltas numero de vueltas (solo relevante en el modo carrera)
     */
    public GameSettings(int numOpos, String circuito, int modo, int nVueltas) {
        this.numOpos = numOpos;
        this.circuito = circuito;
        this.modo = modo;
        this.nVueltas = nVueltas;
    }
}
