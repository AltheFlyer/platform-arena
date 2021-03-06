package game.arena.platform.deprecated;

import com.badlogic.gdx.math.Rectangle;

/**
 * <p>
 * The screen collectible object in the game.
 * </p>
 * 
 * @author Allen Liu
 */
public class Star {
	
	Rectangle hitbox;
	float cooldown;
	final float COOLDOWN_TIME;
	boolean collected;
	
	public Star(float x1, float y1) {
		hitbox = new Rectangle(x1, y1, 15, 15);
		cooldown = 0;
		COOLDOWN_TIME = 30;
		collected = false;
	}
	
	public boolean collect(Rectangle player) {
		if (hitbox.overlaps(player) && !collected) {
			collected = true;
			cooldown = COOLDOWN_TIME;
			return true;
		}
		return false;
	}
	
	public void spawn(float frame) {
		if (cooldown <= 0) {
			collected = false;
		} else if (collected) {
			cooldown -= frame;
		}
	}
}
