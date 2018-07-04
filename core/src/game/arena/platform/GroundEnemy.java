package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GroundEnemy extends Enemy{
	
	//Jumping stuff
	float jumpCooldown;
	
	public GroundEnemy(float x1, float y1) {
		super(x1, y1, 25, 50, 0, 0, 15);
		sprite = new Texture("tmp_ground_enemy.png");
	}
	
	public GroundEnemy(Vector2 pos) {
		this(pos.x, pos.y);
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
		if (jumpCooldown <= 0 && onGround && y - 100 > hitbox.y) {
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
		
		//Keeping this order prevents enemies from phasing through platforms
		//Dropping through platforms
		if (jumpCooldown <= 0 && onGround && y + 100 < hitbox.y) {
			hitbox.y -= 1;
			yLast = hitbox.y;
			onGround = false;
			jumpCooldown = 1.5f;
		}
		
		// Check if enemy is on the ground (messy)
		/*
		This seems to be necessary to prevent air jumps-
		I can't find any other place to do this.
		I should only need to set onGround to false
		and the level would do the rest, but Im ay change this later.
		 */
		
		if (yLast != hitbox.y) {
			onGround = false;
		} else {
			onGround = true;
		}
	}

}
