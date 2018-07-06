package game.arena.platform;

import com.badlogic.gdx.math.Vector2;

public class WitchEnemy extends Enemy {

	float cooldown;
	static final float ATTACK_COOLDOWN = 3f;

	public WitchEnemy(float x1, float y1) {
		super(x1, y1, 75, 75, 0, 0, 23);
		type = AttackType.single;
		cooldown = 3f;
		score = 4;
	}
	
	public WitchEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}
	
	public void move(float x, float y, float frame) {
		if (xCentre > x) {
			xMove = -20;
		} else if (xCentre < x) {
			xMove = 20;
		}
		
		//A little bit of padding room to prevent vibrating enemies
		if (Math.abs(xCentre - x) < 10) {
			xMove = 0;
		}
		
		super.move(x, y, frame);
		
		if (cooldown > 0) {
			cooldown -= frame;
		}
		if (cooldown < 0) {
			cooldown = 0;
		}
	}
	
	public boolean canAttack(float x, float y, float frame) {
		return cooldown <= 0;
	}
	
	public Projectile attackSingle(float x, float y, float frame) {
		cooldown = ATTACK_COOLDOWN;
		float xTemp = (x - xCentre);
		float yTemp = (float) 200 + (y - yCentre);
		float pow = xTemp * xTemp + yTemp * yTemp;
		if (pow > 422500) {
			xTemp = (float) (xTemp / Math.sqrt(pow)) * 650;
			yTemp = (float) (yTemp / Math.sqrt(pow)) * 650;
		}
		return new PotionProjectile(xCentre, yCentre, xTemp, 
				yTemp
				, 10);
	}
}
