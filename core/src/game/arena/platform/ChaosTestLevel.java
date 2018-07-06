package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ChaosTestLevel extends PlatformArena {
	
	final Vector2 CENTRE;
	final Vector2 LEFT;
	final Vector2 RIGHT;
	
	public ChaosTestLevel(Arena game) {
		super(game);
		arenaWidth = 1600;
		arenaHeight = 1200;
		
		//Spawn player on ground
		player.hitbox.x = arenaWidth / 2;
		player.hitbox.y = 10;
		
		//Enemy Spawns
		CENTRE = new Vector2(arenaWidth / 2, arenaHeight / 2);
		LEFT = new Vector2(-10, 10);
		RIGHT= new Vector2(arenaWidth, 10);
		enemies.clear();
		init();
	}
	
	public void initializePlatforms() {
		platforms.add(new Rectangle(300, 150, 160, 1));
		platforms.add(new Rectangle(460, 150, 160, 1));
		platforms.add(new Rectangle(300, 300, 160, 1));
		
		platforms.add(new Rectangle(800, 150, 160, 1));
		platforms.add(new Rectangle(960, 150, 160, 1));
		platforms.add(new Rectangle(1120, 300, 160, 1));
		
		platforms.add(new Rectangle(860, 450, 160, 1));
		platforms.add(new Rectangle(1020, 450, 160, 1));
		platforms.add(new Rectangle(1180, 450, 160, 1));
		
		platforms.add(new Rectangle(500, 600, 160, 1));
		
		platforms.add(new Rectangle(340, 750, 160, 1));
		platforms.add(new Rectangle(660, 750, 160, 1));
		platforms.add(new Rectangle(1140, 750, 160, 1));
		
		platforms.add(new Rectangle(820, 900, 160, 1));
		platforms.add(new Rectangle(980, 900, 160, 1));
	}
	
	public void initializeStars() {
		//2 stars per platform
		for (Rectangle r: platforms) {
			stars.add(new Star(r.x + 33, r.y + 35));
			stars.add(new Star(r.x + 113, r.y + 35));
		}
	}
	
	public void initializeWaves() {
		waves.put(new ShooterEnemy(CENTRE), 1f);
		waves.put(new ShooterEnemy(CENTRE), 3f);
		waves.put(new ShooterEnemy(LEFT), 4f);
		waves.put(new ShooterEnemy(RIGHT), 4f);
		
		waves.put(new WitchEnemy(CENTRE), 5f);
		waves.put(new WitchEnemy(CENTRE.x + 100, CENTRE.y), 6f);
		waves.put(new WitchEnemy(LEFT), 8f);
		waves.put(new WitchEnemy(RIGHT), 8f);
		waves.put(new WitchEnemy(LEFT), 11f);
		waves.put(new WitchEnemy(RIGHT), 11f);
	}
}
