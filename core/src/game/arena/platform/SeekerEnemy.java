package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SeekerEnemy extends Enemy {

	public SeekerEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 10);
		sprite = new Texture("seeker_enemy.png");
		flying = true;
	}
	
	//I'm going to assume this doesn't break anything
	public SeekerEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}
	
	public void move(float x, float y, float frame) {
		if (Math.pow(x - hitbox.x, 2) + Math.pow(y - hitbox.y, 2) > 1) {
			xMove = 100 * MathUtils.cos(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
			yMove = 100 * MathUtils.sin(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		} else {
			//PRevent vibrating enemies
			xMove = 0;
			yMove = 0;
		}
		super.move(frame);
	}
	
}
