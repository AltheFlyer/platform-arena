package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;

public class Character {
	
	Rectangle hitbox;
	float xMove, yMove;
	float yLast;
	float primaryCooldown;
	float secondaryCooldown;
	final float PRIMARY_COOLDOWN;
	final float SECONDARY_COOLDOWN;
	boolean onGround;
	boolean hasCollided;
	
	public Character(float x1, float y1, float pCD, float sCD) {
		hitbox = new Rectangle(x1, y1, 50, 100);
		yLast = y1;
		xMove = 0;
		yMove = 0;
		primaryCooldown = 0;
		secondaryCooldown = 0;
		PRIMARY_COOLDOWN = pCD;
		SECONDARY_COOLDOWN = sCD;
		onGround = false;
	}
	
	public void attack() {
		
	}
}
