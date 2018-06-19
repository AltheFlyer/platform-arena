package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SeekerEnemy extends Enemy {

	public SeekerEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 10);
		sprite = new Texture("seeker_enemy.png");
	}
	
	public SeekerEnemy(Vector2 pos) {
		super(pos.x, pos.y, 25, 25, 0, 0, 10);
		sprite = new Texture("seeker_enemy.png");
	}
	
	public void move(float x, float y, float frame) {
		xMove = 100 * MathUtils.cos(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		yMove = 100 * MathUtils.sin(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		super.move(frame);
	}
	
}
