package com.spaceshooter;

import com.badlogic.gdx.Game;
import com.spaceshooter.configuration.AudioConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.screens.MainMenuScreen;

public class SpaceShooterGame extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	@Override
	public void create () {
		TextureConfiguration.getInstance();
		AudioConfiguration.getInstance().MUSIC_BGM.setLooping(true);
		AudioConfiguration.getInstance().MUSIC_BGM.setVolume(0.1f);
		AudioConfiguration.getInstance().MUSIC_BGM.play();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		getScreen().resize(width, height);
	}

	@Override
	public void dispose () {
		getScreen().dispose();
	}

}
