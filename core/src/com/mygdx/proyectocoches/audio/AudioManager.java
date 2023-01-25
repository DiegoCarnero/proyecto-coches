package com.mygdx.proyectocoches.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.proyectocoches.Constantes;
import com.mygdx.proyectocoches.utils.InputManager;
import com.mygdx.proyectocoches.utils.PlayerInput;

public class AudioManager implements Disposable, Telegraph {

    private Music soniAct;
    private Music soniSig;
    private Music sonParado;
    private Music sonAcc1;
    private Music sonAcc2;
    private Music sonEmbr1;
    private Music sonEmbr2;
    private Music sonMedio1;
    private Music sonBajo1;
    private Music sonMax1;
    private Music sonMax2;
    private boolean isPlaying = false;
    private float MAX_SFX_VOLUME = 1f;
    private boolean fade = true;
    private boolean init = true;

    public AudioManager() {
        this.sonParado = Gdx.audio.newMusic(Gdx.files.internal("audio/parado1.ogg"));
        this.sonAcc1 = Gdx.audio.newMusic(Gdx.files.internal("audio/acelerando1.ogg"));
        this.sonAcc2 = Gdx.audio.newMusic(Gdx.files.internal("audio/acelerando2.ogg"));
        this.sonEmbr1 = Gdx.audio.newMusic(Gdx.files.internal("audio/embragando1.ogg"));
        this.sonEmbr2 = Gdx.audio.newMusic(Gdx.files.internal("audio/embragando2.ogg"));
        this.sonMedio1 = Gdx.audio.newMusic(Gdx.files.internal("audio/medio1.ogg"));
        this.sonBajo1 = Gdx.audio.newMusic(Gdx.files.internal("audio/bajo1.ogg"));
        this.sonMax1 = Gdx.audio.newMusic(Gdx.files.internal("audio/max1.ogg"));
        this.sonMax2 = Gdx.audio.newMusic(Gdx.files.internal("audio/max2.ogg"));
    }

    public void init() {
        soniAct = sonParado;
        soniAct.play();
    }

    public void update(float delta) {
        while (fade) {
            fade = fade(delta, soniAct, soniSig);
        }
        soniAct = soniSig;
    }

    private boolean fade(float delta, Music s1, Music s2) {

        s1.setVolume(s1.getVolume() - (delta));
        s2.setVolume(s2.getVolume() + (delta));
        if (s1.getVolume() == 0 && s2.getVolume() >= MAX_SFX_VOLUME) {
            s2.setVolume(MAX_SFX_VOLUME);
            return false;
        } else {
            return true;
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

    /**
     * Handles the telegram just received.
     *
     * @param msg The telegram
     * @return {@code true} if the telegram has been successfully handled; {@code false} otherwise.
     */
    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case Constantes.TELE_ACC:
                if (this.soniAct != this.sonAcc1) {
                    this.soniAct.stop();
                    this.soniAct = this.sonAcc1;
                    this.soniAct.play();
                }
                break;
            case Constantes.TELE_EMBRAG:
                if (this.soniAct != this.sonEmbr1) {
                    this.soniAct.stop();
                    this.soniAct = this.sonEmbr1;
                    this.soniAct.play();
                }
                break;
            case Constantes.TELE_PARADO:
                if (this.soniAct != this.sonParado) {
                    this.soniAct.stop();
                    this.soniAct = this.sonParado;
                    this.soniAct.play();
                }
                break;
        }
        return true;
    }
}
