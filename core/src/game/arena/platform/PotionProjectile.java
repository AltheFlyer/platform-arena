package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;

public class PotionProjectile extends Projectile{
	
	//This is for the player
	public PotionProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 400, 0, 0, 1);
		hasDeathCreate = true;
	}
	
	//This one is for enemies
	public PotionProjectile(float x1, float y1, float xV, float yV, float damage) {
		super(x1, y1, xV, yV, 1, 0, 0, damage);
		hasDeathCreate = false;
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
		return new SplashProjectile(hitbox.x - 10, hitbox.y - 10, 23, 23, damage, 0.2f);
	}
	
}
