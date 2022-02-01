package com.spaceinvaders.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spaceinvaders.game.SpaceInvaders;

/**
 *  Noah Newton
 *  Space Invaders Clone for CSC455
 *  Homework #1
 *  Credits for Images and Sounds
 *
 *
 *
 */

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SpaceInvaders(), config);
	}
}
