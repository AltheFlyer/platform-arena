package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

//This is just a fun level to experiment with the orbital enemies
public class OrbitalLevel extends PlatformArena {

	public OrbitalLevel(Arena game) {
		super(game);
		player.hitbox.x = 375;
		player.hitbox.y = 600;
		enemies.clear();
		init();
	}
	
	public void initializePlatforms() {
		platforms.add(new Rectangle(340, 150, 160, 1));
		platforms.add(new Rectangle(340, 300, 160, 1));
	}

	public void initializeStars() {
		// Ring of stars (for fun)
		for (int i = 0; i < 12; i++) {
			stars.add(new Star(400 + 200 * MathUtils.sin(i * 30 * MathUtils.degreesToRadians),
					300 + 200 * MathUtils.cos(i * 30 * MathUtils.degreesToRadians)));
		}
	}
	
	public void initializeWaves() {
		//Rotation enemy?
		waves.put(new OrbitalEnemy(-100, -100, 200), 0f);
		waves.put(new OrbitalEnemy(-100, -100, 200), 1f);
		waves.put(new OrbitalEnemy(-100, -100, 200), 2f);
		waves.put(new OrbitalEnemy(-100, -100, 200), 3f);
		waves.put(new OrbitalEnemy(-100, -100, 200), 4f);
		
		waves.put(new OrbitalEnemy(-100, -100, 400), 5f);
		waves.put(new OrbitalEnemy(-100, -100, 400), 6f);
		waves.put(new OrbitalEnemy(-100, -100, 400), 7f);
		waves.put(new OrbitalEnemy(-100, -100, 400), 8f);
		waves.put(new OrbitalEnemy(-100, -100, 400), 9f);
		
		waves.put(new OrbitalEnemy(-100, -100, 150), 10f);
		waves.put(new OrbitalEnemy(-100, -100, 160), 10.1f);
		waves.put(new OrbitalEnemy(-100, -100, 170), 10.2f);
		waves.put(new OrbitalEnemy(-100, -100, 180), 10.3f);
		waves.put(new OrbitalEnemy(-100, -100, 190), 10.4f);
		waves.put(new OrbitalEnemy(-100, -100, 200), 10.5f);
		waves.put(new OrbitalEnemy(-100, -100, 210), 10.6f);
		waves.put(new OrbitalEnemy(-100, -100, 220), 10.7f);
		waves.put(new OrbitalEnemy(-100, -100, 230), 10.8f);
		waves.put(new OrbitalEnemy(-100, -100, 240), 10.9f);
		waves.put(new OrbitalEnemy(-100, -100, 250), 11f);
		waves.put(new OrbitalEnemy(-100, -100, 260), 11.1f);
		waves.put(new OrbitalEnemy(-100, -100, 270), 11.2f);
	}

}
