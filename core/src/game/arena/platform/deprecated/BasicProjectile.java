package game.arena.platform.deprecated;

import com.badlogic.gdx.math.MathUtils;

public class BasicProjectile  extends Projectile {
	
	
	//Note that all of the constructors use radians

	public BasicProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 600, 0, 0, 1);
	}
	
	public BasicProjectile(float x1, float y1, float damage, float age) {
		super(x1, y1, 0, 0, 0, 0, 0, damage);
	}
	
	public BasicProjectile(float x1, float y1, float angle, float speed, float age) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), speed, 0, 0, 1);
	}
}
