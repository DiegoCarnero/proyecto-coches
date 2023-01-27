package com.mygdx.proyectocoches.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.proyectocoches.Constantes;
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.PlayerInput;

public class AudioManager implements Disposable {

    private AssetManager asM;
    private Sound soniAct;
    private Sound soniSig;
    private Sound sonParado1;
    private Sound sonParado2;
    private Sound sonAcc1;
    private Sound sonAcc2;
    private Sound sonEmbr1;
    private Sound sonEmbr2;
    private Sound sonMedio1;
    private Sound sonBajo1;
    private Sound sonMax1;
    private Sound sonMax2;
    private float MAX_SFX_VOLUME = 1f;
    private boolean init, isPlaying;

    public AudioManager(AssetManager asM) {
        this.asM = asM;
        asM.load("audio/parado1.ogg", Sound.class);
        asM.load("audio/parado2.ogg", Sound.class);
        asM.load("audio/marcha1.ogg", Sound.class);
        asM.load("audio/embragando1.ogg", Sound.class);
    }

    public void init() {
        init = true;
        sonParado1 = asM.get("audio/parado1.ogg");
        sonParado2 = asM.get("audio/parado2.ogg");
        sonAcc1 = asM.get("audio/marcha1.ogg");
        sonEmbr1 = asM.get("audio/embragando1.ogg");
        try {
            Thread.sleep(500);
            sonParado1.loop();
            sonParado2.loop();
        } catch (InterruptedException e) {
            sonParado1.loop();
            sonParado2.loop();
        }
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        soniAct.dispose();
        soniSig.dispose();
    }

    public void cambiaSonido(int msg) {
        switch (msg) {
            case Constantes.TELE_ACC:
                if (soniAct != sonAcc1) {
                    if (isPlaying){
                        soniAct.stop();
                    }
                    soniAct = sonAcc1;
                    soniAct.play();
                    isPlaying = true;
                }
                break;
            case Constantes.TELE_EMBRAG:
                if (soniAct != this.sonEmbr1) {
                    if (isPlaying){
                        soniAct.stop();
                    }
                    soniAct = this.sonEmbr1;
                    soniAct.play();
                }
                break;
            case Constantes.TELE_PARADO:
                break;
        }
    }

}
