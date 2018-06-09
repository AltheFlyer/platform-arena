package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;

/**
 * <p>
 * The base class for all enemies
 * </p>
 * 
 * @author Allen Liu
 *
 */
public class Enemy {
	
	Rectangle hitbox;
	float xMove;
	float yMove;
	float health;
	boolean destroy;
	//More to come
	
	/**
	 * @param x1 The starting x - coordinate
	 * @param y1 The starting y - coordinate
	 * @param w  Width of hitbox
	 * @param h  Height of hitbox
	 * @param xV Starting x velocity
	 * @param yV Starting y velocity
	 * @param hp Health
	 */
	public Enemy(float x1, float y1, float w, float h, float xV, float yV, float hp) {
		hitbox = new Rectangle(x1, y1, w, h);
		xMove = xV;
		yMove = yV;
		health = hp;
		destroy = false;
	}
	
	public void move(float frame) {
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
	}
	
	public void move(float x, float y, float frame) {
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
	}
	
	public void damage(float damage) {
		health -= damage;
		if (health <= 0) destroy = true;
	}
}
