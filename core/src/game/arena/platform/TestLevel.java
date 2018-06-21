package game.arena.platform;

//import java.util.Iterator;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ObjectMap;

/**
 * A test level
 *
 */
public class TestLevel extends PlatformArena {
	
	//Spawns
	final Vector2 SPAWN_TOP_LEFT;
	final Vector2 SPAWN_MID_LEFT;
	final Vector2 SPAWN_BOT_LEFT;
	
	final Vector2 SPAWN_TOP_RIGHT;
	final Vector2 SPAWN_MID_RIGHT;
	final Vector2 SPAWN_BOT_RIGHT;
	
	public TestLevel(final Arena game) {
		super(game);
		//Set spawns:
		SPAWN_TOP_LEFT = new Vector2(0, 525);
		SPAWN_MID_LEFT = new Vector2(0, 375);
		SPAWN_BOT_LEFT = new Vector2(0, 225);
		
		SPAWN_TOP_RIGHT = new Vector2(800, 525);
		SPAWN_MID_RIGHT = new Vector2(800, 375);
		SPAWN_BOT_RIGHT = new Vector2(800, 225);
		player.hitbox.x = 400 - player.hitbox.width;
		player.hitbox.y = 0;
		//Remove when debug spawns are removed
		enemies.clear();
		super.init();
		
	}

	public void initializePlatforms() {
		//Platforms everywhere:
		platforms.add(new Rectangle(0, 150, 160, 1));
		platforms.add(new Rectangle(160, 150, 160, 1));
		platforms.add(new Rectangle(480, 150, 160, 1));
		platforms.add(new Rectangle(640, 150, 160, 1));
		
		platforms.add(new Rectangle(0, 300, 160, 1));
		platforms.add(new Rectangle(160, 300, 160, 1));
		platforms.add(new Rectangle(480, 300, 160, 1));
		platforms.add(new Rectangle(640, 300, 160, 1));
		
		platforms.add(new Rectangle(0, 450, 160, 1));
		platforms.add(new Rectangle(160, 450, 160, 1));
		platforms.add(new Rectangle(480, 450, 160, 1));
		platforms.add(new Rectangle(640, 450, 160, 1));
	}

	public void initializeStars() {
		// Ring of stars (for fun)
		for (int i = 0; i < 12; i++) {
			stars.add(new Star(400 + 200 * MathUtils.sin(i * 30 * MathUtils.degreesToRadians),
					300 + 200 * MathUtils.cos(i * 30 * MathUtils.degreesToRadians)));
		}
	}
	
	public void initializeWaves() {
		//I need this for testing:
		waves.put(new GroundEnemy(SPAWN_TOP_LEFT), 0f);
		waves.put(new GroundEnemy(SPAWN_MID_LEFT), 0f);
		waves.put(new GroundEnemy(SPAWN_BOT_LEFT), 0f);
		
		waves.put(new GroundEnemy(SPAWN_TOP_RIGHT), 0f);
		waves.put(new GroundEnemy(SPAWN_MID_RIGHT), 0f);
		waves.put(new GroundEnemy(SPAWN_BOT_RIGHT), 0f);
		
		//Lets try something legit:
		waves.put(new SeekerEnemy(SPAWN_MID_LEFT), 5f);
		waves.put(new SeekerEnemy(SPAWN_MID_RIGHT), 5f);
		
		waves.put(new SeekerEnemy(SPAWN_TOP_LEFT), 10f);
		waves.put(new SeekerEnemy(SPAWN_TOP_RIGHT), 10f);
		
		waves.put(new SeekerEnemy(SPAWN_BOT_LEFT), 15f);
		waves.put(new SeekerEnemy(SPAWN_BOT_RIGHT), 15f);
		
		waves.put(new GroundEnemy(SPAWN_BOT_LEFT), 20f);
		waves.put(new GroundEnemy(SPAWN_BOT_RIGHT), 20f);
		
		waves.put(new SeekerEnemy(SPAWN_MID_LEFT), 22f);
		waves.put(new SeekerEnemy(SPAWN_MID_RIGHT), 22f);
		
		waves.put(new SeekerEnemy(SPAWN_TOP_LEFT), 22f);
		waves.put(new SeekerEnemy(SPAWN_TOP_RIGHT), 22f);
		
		waves.put(new SeekerEnemy(SPAWN_BOT_LEFT), 22f);
		waves.put(new SeekerEnemy(SPAWN_BOT_RIGHT), 22f);
	}

}