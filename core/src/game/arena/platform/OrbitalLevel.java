package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import game.arena.platform.Enemy.AttackType;

//This is just a fun level to experiment with the orbital enemies
public class OrbitalLevel extends PlatformArena {

	public OrbitalLevel(Arena game) {
		super(game);
		player.hitbox.x = 375;
		player.hitbox.y = 600;
		init();
		//Prevents enemy attacks, this is a level for display
		for (Enemy e: waves[0].enemies.keys()) {
			e.type = AttackType.none;
		}
	}
	
	public void initializePlatforms() {
		platforms.add(new Rectangle(340, 125, 160, 1));
		platforms.add(new Rectangle(340, 275, 160, 1));
	}

	public void initializeStars() {
		// Ring of stars (for fun)
		for (int i = 0; i < 12; i++) {
			stars.add(new Star(400 + 200 * MathUtils.sin(i * 30 * MathUtils.degreesToRadians),
					275 + 200 * MathUtils.cos(i * 30 * MathUtils.degreesToRadians)));
		}
	}
	
	public void initializeWaves() {
		System.out.println(0f + " " + 1f);
		//Rotation enemy?
		//Don't start at 0f, it offsets things
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 1f);
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 2f);
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 3f);
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 4f);
		
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 5f);
		
		waves[0].put(new OrbitalEnemy(-100, -100, 400), 6f);
		waves[0].put(new OrbitalEnemy(-100, -100, 400), 7f);
		waves[0].put(new OrbitalEnemy(-100, -100, 400), 8f);
		waves[0].put(new OrbitalEnemy(-100, -100, 400), 9f);
		waves[0].put(new OrbitalEnemy(-100, -100, 400), 10f);
		
		waves[0].put(new OrbitalEnemy(-100, -100, 150), 11f);
		waves[0].put(new OrbitalEnemy(-100, -100, 160), 11.1f);
		waves[0].put(new OrbitalEnemy(-100, -100, 170), 11.2f);
		waves[0].put(new OrbitalEnemy(-100, -100, 180), 11.3f);
		waves[0].put(new OrbitalEnemy(-100, -100, 190), 11.4f);
		waves[0].put(new OrbitalEnemy(-100, -100, 200), 11.5f);
		waves[0].put(new OrbitalEnemy(-100, -100, 210), 11.6f);
		waves[0].put(new OrbitalEnemy(-100, -100, 220), 11.7f);
		waves[0].put(new OrbitalEnemy(-100, -100, 230), 11.8f);
		waves[0].put(new OrbitalEnemy(-100, -100, 240), 11.9f);
		waves[0].put(new OrbitalEnemy(-100, -100, 250), 12f);
		waves[0].put(new OrbitalEnemy(-100, -100, 260), 12.1f);
		waves[0].put(new OrbitalEnemy(-100, -100, 270), 12.2f);
		
	}

}
