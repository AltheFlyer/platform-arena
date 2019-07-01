package game.arena.platform.deprecated;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class AirChargerEnemy extends Enemy {
	
	float chargeCooldown;
	final float MAX_CHARGE_COOLDOWN = 5f;
	float chargeTime;
	final float MAX_CHARGE_TIME = 3f;
	float angle;
	
	public AirChargerEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 10);
		flying = true;
		score = 3;
		chargeCooldown = MAX_CHARGE_COOLDOWN;
		chargeTime = 0f;
		angle = 0;
	}
	
	public AirChargerEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}
	
	public void move(float x, float y, float frame) {
		if (chargeCooldown > 3f && (Math.pow(x - xCentre, 2) + Math.pow(y - yCentre, 2) > 1)) {
			xMove = ((chargeCooldown - 3f) / (MAX_CHARGE_COOLDOWN - 3f)) * 100 * MathUtils.cos(MathUtils.atan2(y - yCentre, x - xCentre));
			yMove = ((chargeCooldown - 3f) / (MAX_CHARGE_COOLDOWN - 3f)) * 100 * MathUtils.sin(MathUtils.atan2(y - yCentre, x - xCentre));
		} else {
			xMove = 0;
			yMove = 0;
		}
		
		chargeCooldown -= frame;
		if (chargeCooldown <= 0f && chargeTime <= 0) {
			angle = MathUtils.atan2(y - yCentre, x - xCentre);
			chargeTime = MAX_CHARGE_TIME;
		}
		
		if (chargeTime >= 0f) {
			xMove = (chargeTime / 3) * 500 * MathUtils.cos(angle);
			yMove = (chargeTime / 3) * 500 * MathUtils.sin(angle);
			chargeTime -= frame;
			if (chargeTime <= 0) {
				chargeCooldown = MAX_CHARGE_COOLDOWN;
			}
			
		}
		super.move(frame);
	}

}
