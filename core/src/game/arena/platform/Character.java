package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;

public class Character {
	
	Rectangle hitbox;
	float xMove, yMove;
	float yLast;
	float cooldown;
	float primaryCooldown;
	boolean onGround;
	boolean hasCollided;
	
	public Character(float x1, float y1, float pCD) {
		hitbox = new Rectangle(x1, y1, 50, 100);
		yLast = y1;
		xMove = 0;
		yMove = 0;
		cooldown = 0;
		primaryCooldown = pCD;
		onGround = false;
	}
	
	public void attack() {
		
	}
}
