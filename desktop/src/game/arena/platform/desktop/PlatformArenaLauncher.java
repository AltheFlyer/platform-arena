package game.arena.platform.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.arena.platform.PlatformArena;

public class PlatformArenaLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width= 800;
		new LwjglApplication(new PlatformArena(), config);
	}
}
