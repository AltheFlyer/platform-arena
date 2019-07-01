package game.arena.platform.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class RiderEnemy extends Enemy {
	
	//Jumping stuff
	float jumpCooldown;
		
	public RiderEnemy(float x1, float y1) {
		super(x1, y1, 100, 50, 0, 0, 5);
		score = 5;
		sprite = new Texture("rider_enemy.png");
		hasDeathSummon = true;
	}
	
	public RiderEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}

	public void move(float x, float y, float frame) {
		//Make them charge
		if (x + 10 < xCentre && xMove >= -400) {
			xMove -= 400 * frame;
		} else if (x - 10 > xCentre && xMove <= 400) {
			xMove += 400 * frame;
		}
		if (xMove < -400) xMove = -400;
		else if (xMove > 400) xMove = 400;
		
		if (y - yCentre > 25  && jumpCooldown <= 0 && onGround) {
			yMove = 1100;
			jumpCooldown = 1.5f;
			onGround = false;
		}
		
		//Tick cooldown
		if (jumpCooldown > 0) {
			jumpCooldown -= frame;
			if (jumpCooldown < 0) {
				jumpCooldown = 0;
			}
		}
		
		super.move(frame);
		
		//Dropping through platforms
		if (jumpCooldown <= 1.3f && onGround && yCentre - y > 75) {
			hitbox.y -= 1;
			yLast = hitbox.y;
			onGround = false;
			jumpCooldown = 0.1f;
		}
		
		//Same reasoning as in Ground Enemy
		if (yLast != hitbox.y) {
			onGround = false;
		} else {
			onGround = true;
		}
	}
	
	public Enemy deathSummon() {
		return new GroundEnemy(hitbox.x, hitbox.y + 10);
	}
}
