package game.arena.platform;

import com.badlogic.gdx.Gdx;

public class BasicProjectile  extends Projectile {
	
	public BasicProjectile(float x1, float y1, float xV, float yV, float a, float maxA) {
		super(x1, y1, xV, yV, 600, a, maxA);
	}
	
	public void move() {
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
	}
}
