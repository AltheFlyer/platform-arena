package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class SeekerEnemy extends Enemy {

	public SeekerEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 10);
	}
	
	public void move(float x, float y) {
		xMove = 100 * MathUtils.cos(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		yMove = 100 * MathUtils.sin(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		hitbox.x += xMove * Gdx.graphics.getDeltaTime();
		hitbox.y += yMove * Gdx.graphics.getDeltaTime();
	}
	
}
