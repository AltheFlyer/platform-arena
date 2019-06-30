package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class BigLevel extends PlatformArena {
	
	public BigLevel(Arena game) {
		super(game);
		arenaWidth = 1600;
		arenaHeight = 1200;
		
		init();
	}
	
	public void initializePlatforms() {
		for (int i = 0; i < 16; i ++) {
			platforms.add(new Rectangle(i * 160, 150, 160, 1));
		}
		
		for (int i = 0; i < 8; i ++) {
			platforms.add(new Rectangle((arenaWidth / 2) - 80 + 200 * MathUtils.cos(i * 0.785398f),
					(arenaHeight / 2) + 200 * MathUtils.sin(i * 0.785398f),
					160, 1));
			platforms.add(new Rectangle((arenaWidth / 2) - 80 + 325 * MathUtils.cos(i * 0.785398f),
					(arenaHeight / 2) + 325 * MathUtils.sin(i * 0.785398f),
					160, 1));
		}
	}
	
	public void initializeStars() {
		for (int i = 0; i < 32; i ++) {
			stars.add(new Star(i * 50 + 10, 175));
		}
		
		stars.add(new Star(arenaWidth / 2 + 50, arenaHeight / 2 + 50));
		stars.add(new Star(arenaWidth / 2 - 50, arenaHeight / 2 - 50));
		stars.add(new Star(arenaWidth / 2 + 50, arenaHeight / 2 - 50));
		stars.add(new Star(arenaWidth / 2 - 50, arenaHeight / 2 + 50));
		
	}

	public void initializeWaves() {
		for (int i = 0; i< 20; i ++) {
			//waves[0].put(new GroundEnemy(arenaWidth, 0), (float) i);
			waves[0].put(new RiderEnemy(arenaWidth, 0), (float) i);
		}
	}
}
