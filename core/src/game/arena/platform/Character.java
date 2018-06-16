package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
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
	Texture playerSprite;
	Texture jumpSprite;
	Texture fallSprite;
	
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
		/*By default, I'll use this:*/
		playerSprite = new Texture("question_mark50x100.png");
		jumpSprite = new Texture("arrow_up.png");
		fallSprite = new Texture("arrow_down.png");
	}
	
	public void attack() {
		
	}
	
	public Texture getCharacterState() {
		//sprite changes based on movement
		//If near the top of a jump, a different sprite should be used.
		//For now, it just resorts to default
		if (yMove > 300) {
			return jumpSprite;
		} else if (yMove < -300) {
			return fallSprite;
		} else {
			return playerSprite;
		}
	}
}
