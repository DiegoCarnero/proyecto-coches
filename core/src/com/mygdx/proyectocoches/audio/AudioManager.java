package com.mygdx.proyectocoches.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
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
     * Volumen de los efectos de sonido
     */
    private final float sfxVol;
    /**
     * Volumen de la musica
     */
    private final float musicVol;

    /**
     * Cancion que se está reproduciendo
     */
    private Music cancion;

    /**
     * Crea un nuevo objeto AudioManager
     *
     * @param asM AssetManager que se va a utilizar
     */
    public AudioManager(AssetManager asM) {
        this.asM = asM;

        JsonValue base;
        JsonReader json = new JsonReader();
        base = json.parse(Gdx.files.external("usersettings.json"));
        musicVol = base.get("music").asFloat();
        sfxVol = base.get("sfx").asFloat();

        asM.load("audio/sfx/parado1.ogg", Sound.class);
        asM.load("audio/sfx/parado2.ogg", Sound.class);
        asM.load("audio/sfx/marcha1.ogg", Sound.class);
        asM.load("audio/sfx/max1.ogg", Sound.class);
        asM.load("audio/sfx/medio1.ogg", Sound.class);
        asM.load("audio/sfx/embragando1.ogg", Sound.class);
        asM.load("audio/music/EvanSchaeffer-Bounce.mp3", Music.class);
        asM.finishLoading();
    }

    /**
     * Isigna los archivos a las variables pertinentes
     */
    public void init() {
        sonParado1 = asM.get("audio/sfx/parado1.ogg");
        sonParado2 = asM.get("audio/sfx/parado2.ogg");

        sonAcc1 = asM.get("audio/sfx/marcha1.ogg");
        sonEmbr1 = asM.get("audio/sfx/embragando1.ogg");
        sonMax1 = asM.get("audio/sfx/max1.ogg");
        sonMedio1 = asM.get("audio/sfx/medio1.ogg");
        cancion = asM.get("audio/music/EvanSchaeffer-Bounce.mp3");

        soniAct = sonMedio1;
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
        }

        sonParado1.loop(sfxVol);
        sonParado2.loop(sfxVol);
        cancion.setLooping(true);
        cancion.play();
        cancion.setVolume(musicVol);
    }

    /**
     * Releases all resources of this object.
     * Si hay algun sonido reproduciendose lo para
     */
    @Override
    public void dispose() {
        sonParado1.stop();
        sonParado2.stop();
        sonAcc1.stop();
        sonEmbr1.stop();
        sonMedio1.stop();

        sonParado1.dispose();
        sonParado2.dispose();
        sonAcc1.dispose();
        sonEmbr1.dispose();
        sonMedio1.dispose();
        sonMax1.dispose();
    }

    /**
     * Cambia el efecto de sonido del coche según el mensaje recibido
     *
     * @param msg tipo de sonido que se debe reproducir
     */
    public void cambiaSonido(int msg) {
        switch (msg) {
            case Constantes.TELE_ACC:
                if (soniAct != sonAcc1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }

                    Gdx.input.vibrate(200);
                    soniAct = sonAcc1;
                    soniAct.play(sfxVol);
                    isPlaying = true;
                }
                break;
            case Constantes.TELE_EMBRAG:
                if (soniAct != this.sonEmbr1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }
                    Gdx.input.vibrate(200);
                    soniAct = this.sonEmbr1;
                    soniAct.play(sfxVol);
                }
                break;
            case Constantes.TELE_MEDIO:
                if (soniAct != sonMedio1) {
                    if (isPlaying) {
                        soniAct.stop();
                    }

                    Gdx.input.vibrate(200);
                    soniAct = sonMedio1;
                    soniAct.loop(sfxVol);
                    isPlaying = true;
                }
                break;
        }
    }

}
