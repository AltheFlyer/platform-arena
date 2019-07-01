package game.arena.platform.deprecated;

public class SplashProjectile extends Projectile {

	public SplashProjectile(float x1, float y1, float h, float w, float dmg, float maxAge) {
		super(x1, y1, w, h, 0, 0, 0, 0, maxAge, dmg);
		maxCollisions = 100;
	}
	
	public void collide(CollisionType type) {
		if (type != CollisionType.wall) {
			super.collide(type);
		}
	}
	
	public boolean checkAge() {
		return age > maxAge;
	}

}
