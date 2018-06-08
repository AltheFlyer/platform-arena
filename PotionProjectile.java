package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class PotionProjectile extends Projectile{

	public PotionProjectile(float x1, float y1, float xV, float yV) {
		super(x1, y1, xV, yV, 400, 0, 0, 1);
	}
	
	public PotionProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 400, 0, 0, 1);
	}
	
	public void move() {
		yMove -= 400 * Gdx.graphics.getDeltaTime();
		super.move();
	}
	
}