package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class BasicProjectile  extends Projectile {
	
	public BasicProjectile(float x1, float y1, float xV, float yV) {
		super(x1, y1, xV, yV, 600, 0, 0, 1);
	}
	
	public BasicProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 600, 0, 0, 1);
	}
	
	public void move() {
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
	}
}
