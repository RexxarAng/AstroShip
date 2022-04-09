package com.spaceshooter.configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public final class AudioConfiguration {

    private static AudioConfiguration INSTANCE;

    public static AudioConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioConfiguration();
        }
        return INSTANCE;
    }

    //Music
    public final Music MUSIC_BGM;

    // In-game sounds effect
    public final Sound AUDIO_RAILGUN;
    public final Sound AUDIO_LASER;
    public final Sound AUDIO_EXPLOSION;

    public final Sound AUDIO_PICKUP_POSITIVE;
    public final Sound AUDIO_PICKUP_NEGATIVE;

    public final Sound AUDIO_GAME_START;
    public final Sound AUDIO_MOUSE_OVER;

    private AudioConfiguration() {
        MUSIC_BGM = Gdx.audio.newMusic(Gdx.files.internal("sounds/cant-stop-wont-stop-final.mp3"));

        AUDIO_RAILGUN = Gdx.audio.newSound(Gdx.files.internal("sounds/railgun.mp3"));
        AUDIO_LASER = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.mp3"));
        AUDIO_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion1.mp3"));

        AUDIO_PICKUP_POSITIVE = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup-positive.mp3"));
        AUDIO_PICKUP_NEGATIVE = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup-negative.mp3"));

        AUDIO_GAME_START = Gdx.audio.newSound(Gdx.files.internal("sounds/start.mp3"));
        AUDIO_MOUSE_OVER = Gdx.audio.newSound(Gdx.files.internal("sounds/mouse-hover.mp3"));
    }
}
