package game.arena.platform.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.arena.platform.ArenaGameWindow;
import game.arena.platform.PlatformArena;
import game.arena.platform.Arena;

public class PlatformArenaLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 800;
		if (!arg[0].equals("NEW")) {

			new LwjglApplication(new Arena(), config);
		} else {
			new LwjglApplication(new ArenaGameWindow(), config);
		}
	}
}
