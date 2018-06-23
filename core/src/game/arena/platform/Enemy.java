package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
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
	float yLast;
	float health;
	boolean destroy;
	boolean onGround;
	boolean flying;
	Texture sprite;
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
		yLast = hitbox.y;
		xMove = xV;
		yMove = yV;
		health = hp;
		destroy = false;
		onGround = false;
		flying = false;
		//By default, do not use
		sprite = new Texture("question_mark50x100.png");
	}
	
	public void move(float frame) {
		yLast = hitbox.y;
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
	}
	
	/**
	 * 
	 * @param x Player x
	 * @param y Player y
	 * @param frame time since last frame
	 */
	public void move(float x, float y, float frame) {
		yLast = hitbox.y;
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
	}
	
	public void damage(float damage) {
		health -= damage;
		if (health <= 0) destroy = true;
	}
	
	public Texture getState(){
		return sprite;
	}
}
