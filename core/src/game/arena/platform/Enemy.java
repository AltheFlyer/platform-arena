package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
	boolean hasDeathSummon;
	Texture sprite;
	float collisionDamage;
	int score;
	AttackType type;
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
		hasDeathSummon = false;
		collisionDamage = 10;
		score = 1;
		//By default, do not use
		sprite = new Texture("question_mark50x100.png");
		type = AttackType.none;
	}
	
	public void move(float frame) {
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
	
	public boolean hasCollided(Rectangle box) {
		return this.hitbox.overlaps(box);
	}
	
	//Use this for summoning things
	public Enemy summon() {
		return null;
	}
	
	//Same as above, but only on enemy death
	public Enemy deathSummon() {
		return null;
	}
	
	//Attacking check
	public boolean canAttack(float x, float y, float frame) {
		return false;
	}
	
	//Attack with projectiles
	public Projectile attackSingle(float x, float y, float frame) {
		return null;
	}
	
	//Attack with projectiles
	public Array<Projectile> attackMulti(float x, float y, float frame) {
		return null;
	}
	
	public enum AttackType {
		none, single, multi 
	}
	
}
