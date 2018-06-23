package game.arena.platform;

import com.badlogic.gdx.math.MathUtils;

public class PotionProjectile extends Projectile{

	public PotionProjectile(float x1, float y1, float angle) {
		super(x1, y1, MathUtils.cos(angle), MathUtils.sin(angle), 400, 0, 0, 1);
	}
	
	public void move(float frame) {
		//Gravity
		yMove -= 400 * frame;
		super.move(frame);
	}
	
}
