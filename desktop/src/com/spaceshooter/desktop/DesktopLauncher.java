package com.spaceshooter.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.spaceshooter.SpaceShooterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setWindowedMode(SpaceShooterGame.WIDTH, SpaceShooterGame.HEIGHT);
			config.setResizable(false);
			config.setForegroundFPS(60);
			config.setTitle("AstroShip");
			new Lwjgl3Application(new SpaceShooterGame(), config);
		} catch(Exception e) {
			System.out.println(e);
		}

	}
}
