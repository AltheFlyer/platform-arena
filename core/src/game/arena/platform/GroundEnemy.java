package game.arena.platform;

import com.badlogic.gdx.math.Vector2;

public class GroundEnemy extends Enemy{

	public GroundEnemy(float x1, float y1) {
		super(x1, y1, 25, 50, 0, 0, 15);
	}
	
	public GroundEnemy(Vector2 pos) {
		super(pos.x, pos.y, 25, 50, 0, 0, 15);
	}

	public void move(float x, float y, float frame) {
		if (hitbox.x > x) {
			xMove = -100;
		} else if (hitbox.x < x) {
			xMove = 100;
		}
		super.move(frame);
	}
	
}
