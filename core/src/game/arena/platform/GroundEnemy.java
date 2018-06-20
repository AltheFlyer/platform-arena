package game.arena.platform;

import com.badlogic.gdx.math.Vector2;

public class GroundEnemy extends Enemy{
	
	//Jumping stuff
	float jumpCooldown;
	
	public GroundEnemy(float x1, float y1) {
		super(x1, y1, 25, 50, 0, 0, 15);
	}
	
	public GroundEnemy(Vector2 pos) {
		super(pos.x, pos.y, 25, 50, 0, 0, 15);
	}

	public void move(float x, float y, float frame) {
		if (hitbox.x > x) {
			xMove = -100;
		} else if (hitbox.x < x) {
			xMove = 100;
		}
		//A little bit of padding room to prevent vibrating enemies
		if (Math.abs(hitbox.x - x) < 10) {
			xMove = 0;
		}
		//Jumping
		if (jumpCooldown <= 0 && onGround && y - 150 > hitbox.y) {
			yMove = 1100;
			jumpCooldown = 1.5f;
		}
		if (jumpCooldown > 0) {
			jumpCooldown -= frame;
			if (jumpCooldown < 0) {
				jumpCooldown = 0;
			}
		}
		super.move(frame);
	}
	
}
