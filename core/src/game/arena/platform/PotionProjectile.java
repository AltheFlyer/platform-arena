package game.arena.platform;

import com.badlogic.gdx.Gdx;

public class PotionProjectile extends Projectile{

	public PotionProjectile(float x1, float y1, float xV, float yV, float a, float maxA) {
		super(x1, y1, xV, yV, 400, a, maxA);
	}
	
	public void move() {
		yMove -= 400 * Gdx.graphics.getDeltaTime();
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
	}
	
}
