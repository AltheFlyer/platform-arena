package game.arena.platform;

import com.badlogic.gdx.math.Vector2;

public class DummyEnemy extends Enemy{

	public DummyEnemy(float x1, float y1) {
		super(x1, y1, 50, 100, 0, 0, Integer.MAX_VALUE);
	}
	
	public DummyEnemy(Vector2 pos) {
		super(pos.x, pos.y, 50, 100, 0, 0, Integer.MAX_VALUE);
	}
	
	public void damage(float damage) {
		System.out.println(damage + " at " + System.currentTimeMillis() % 100000);
	}

}
