package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;

public class BurstProjectile extends Projectile{

	public BurstProjectile(float x1, float y1, float angle) {
		super(x1, y1, 0, 0, 800, 0, 1, 1);
		//Random generation
		//Needs to be done out of super as the random value must be the same for both x and y
		int variance = MathUtils.random(-35, 35);
		xMove = MathUtils.cos((angle + variance * MathUtils.degreesToRadians)) * speed;
		yMove = MathUtils.sin((angle + variance * MathUtils.degreesToRadians)) * speed;
		age = 0;
		maxAge = 0.15f;
	}
	
	public boolean checkAge() {
		return age > maxAge;
	}

}
