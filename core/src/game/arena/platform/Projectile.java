package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;

/**
 * <p>
 * The base class for all projectiles (currently only including friendlies).<br>
 * Includes position, velocity, age, and damage values.<br></br>
 * 
 * By default, projectiles do not use age checks, and move in straight lines<br>
 * based on direction.
 * </p>
 * 
 * @author Allen
 *
 */
public abstract class Projectile {

	Rectangle hitbox;
	float xMove;
	float yMove;
	float speed;
	float age;
	float maxAge;
	float damage;
	boolean destroy;
	
	//Default hitbox
	/**
	 * 
	 * @param x1 The x position
	 * @param y1 The y position
	 * @param xV x movement
	 * @param yV y movement
	 * @param spd Predefined speed
	 * @param a age
	 * @param maxA maximum age
	 * @param dmg damage
	 */
	public Projectile(float x1, float y1, float xV, float yV, 
			float spd, float a, float maxA, float dmg) {
		hitbox = new Rectangle(x1, y1, 3, 3);
		speed = spd;
		xMove = xV * speed;
		yMove = yV * speed;
		age = a;
		maxAge = maxA;
		destroy = false;
		damage = dmg;
	}
	
	//Predefined hitbox
	/**
	 * 
	 * @param x1 The x position
	 * @param y1 The y position
	 * @param w Projectile width
	 * @param h Projectile height
	 * @param xV x movement
	 * @param yV y movement
	 * @param spd Predefined speed
	 * @param a age
	 * @param maxA maximum age
	 * @param dmg damage
	 */
	public Projectile(float x1, float y1, float w, float h, float xV, float yV, 
			float spd, float a, float maxA, float dmg) {
		hitbox = new Rectangle(x1, y1, w, h);
		speed = spd;
		xMove = xV * speed;
		yMove = yV * speed;
		age = a;
		maxAge = maxA;
		destroy = false;
		damage = dmg;
	}
	
	/**
	 * <p>
	 * Default movement code, moves projectile by its x and y velocity.<br></br>
	 * Also ticks age.
	 * </p>
	 * 
	 * @param frame The time that has passed since last call (in seconds)
	 */
	public void move(float frame) {
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
		age += frame;
	}
	
	/**
	 * <p>This by default will do nothing (projectiles won't have a time limit).</p>
	 * 
	 * @return whether or not the projectile has gone over its age limit
	 */
	public boolean checkAge() {
		return false;
	}

}
