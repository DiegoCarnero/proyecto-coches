package com.mygdx.proyectocoches.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.proyectocoches.Constantes;

/**
 * Almacena los sonidos y música, y gestiona el cambio de efectos de sonido durante el gameplay
 */
public class AudioManager implements Disposable {

    /**
     * AssetManager en el que se cargarán los archivos
     */
    private final AssetManager asM;
    /**
     * Sonido que se está reproduciendo actualmente.
     * <p>
     * Si se quiere cambiar el sonido que se etá reproduciendo reasignar esta variable con el nuevo sonido
     */
    private Sound soniAct;
    /**
     * Sonido del coche parado. Primera parte del bucle
     */
    private Sound sonParado1;
    /**
     * Sonido del coche parado. Segunda parte del bucle
     */
    private Sound sonParado2;
    /**
     * Sonido del coche accelerando
     */
    private Sound sonAcc1;
    /**
     * Sonido del coche bajando de marcha
     */
    private Sound sonEmbr1;
    /**
     * Sonido del coche a velocidad media
     */
    private Sound sonMedio1;
    /**
     * Sonido del coche a la máxima velocidad
     */
    private Sound sonMax1;
    /**
     * Indica si sonAct está reproduciendose
     */
    private boolean isPlaying;

    /**
     * Crea un nuevo objeto AudioManager
     * @param asM AssetManager que se va a utilizar
     */
    public AudioManager(AssetManager asM) {
        this.asM = asM;
        asM.load("audio/parado1.ogg", Sound.class);
        asM.load("audio/parado2.ogg", Sound.class);
        asM.load("audio/marcha1.ogg", Sound.class);
        asM.load("audio/max1.ogg", Sound.class);
        asM.load("audio/medio1.ogg", Sound.class);
        asM.load("audio/embragando1.ogg", Sound.class);
    }

    /**
     * Isigna los archivos a las variables pertinentes
     */
    public void init() {
        sonParado1 = asM.get("audio/parado1.ogg");
        sonParado2 = asM.get("audio/parado2.ogg");

        sonAcc1 = asM.get("audio/marcha1.ogg");
        sonEmbr1 = asM.get("audio/embragando1.ogg");
        sonMax1 = asM.get("audio/max1.ogg");
        sonMedio1 = asM.get("audio/medio1.ogg");

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
        }

        sonParado1.loop();
        sonParado2.loop();
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        soniAct.dispose();
    }

    /**
     * Cambia el efecto de sonido del coche según el mensaje recibido
     * @param msg tipo de sonido que se debe reproducir
     */
    public void cambiaSonido(int msg) {
        switch (msg) {
            case Constantes.TELE_ACC:
                if (soniAct != sonAcc1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }
                    soniAct = sonAcc1;
                    soniAct.play(0.3f);
                    isPlaying = true;
                }
                break;
            case Constantes.TELE_EMBRAG:
                if (soniAct != this.sonEmbr1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }
                    soniAct = this.sonEmbr1;
                    soniAct.play(0.3f);
                }
                break;
            case Constantes.TELE_MAX:
                if (soniAct != sonMax1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }

                    soniAct = sonMax1;
                    soniAct.loop(.2f);
                    isPlaying = true;
                }
                break;
            case Constantes.TELE_MEDIO:
                if (soniAct != sonMedio1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }

                    soniAct = sonMedio1;
                    soniAct.loop(.3f);
                    isPlaying = true;
                }
                break;
            case Constantes.TELE_PARADO:
                break;
        }


    }

}
