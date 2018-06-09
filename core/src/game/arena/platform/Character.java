package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;

public class Character {
	
	Rectangle hitbox;
	float xMove, yMove;
	float yLast;
	float primaryCooldown;
	final float PRIMARY_COOLDOWN;
	boolean onGround;
	boolean hasCollided;
	
	public Character(float x1, float y1, float pCD) {
		hitbox = new Rectangle(x1, y1, 50, 100);
		yLast = y1;
		xMove = 0;
		yMove = 0;
		primaryCooldown = 0;
		PRIMARY_COOLDOWN = pCD;
		onGround = false;
	}
	
	public void attack() {
		
	}
}
