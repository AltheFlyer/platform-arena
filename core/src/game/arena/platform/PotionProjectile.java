package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;

public class PotionProjectile extends Projectile{

	public PotionProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 400, 0, 0, 1);
		hasDeathCreate = true;
	}
	
	public void move(float frame) {
		//Gravity
		yMove -= 400 * frame;
		super.move(frame);
	}
	
	public void collide(CollisionType type) {
		//Don't create splashes if out of bounds
		if (type == CollisionType.wall) {
			hasDeathCreate = false;
		}
		super.collide(type);
	}
	
	public Projectile deathCreate() {
		return new SplashProjectile(hitbox.x - 10, hitbox.y - 10, 23, 23, 1, 0.2f);
	}
	
}
