package com.minimal.sandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.minimal.sandbox.SandboxMain;

import static com.minimal.sandbox.desktop.TexturePackKt.texturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		texturePacker(arg);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SandboxMain(), config);
	}
}
