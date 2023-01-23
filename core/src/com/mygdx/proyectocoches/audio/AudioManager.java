package com.mygdx.proyectocoches.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.proyectocoches.utils.PlayerInput;

public class AudioManager implements Disposable {

    private Music soniAct;
    private Music soniSig;
    private final PlayerInput pi;
    private boolean isPlaying = false;

    public AudioManager(PlayerInput pi) {
        this.pi = pi;
    }

    public void update(float delta) {

        if (pi.isAcelerando()) {
        } else {
        }

        if(!isPlaying) {
            soniSig = Gdx.audio.newMusic(Gdx.files.internal("audio/parado1.ogg"));
            soniSig.setLooping(true);
            soniSig.setVolume(1.0f);
            soniSig.play();
            isPlaying = true;
        }
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        soniSig.dispose();
    }
}
