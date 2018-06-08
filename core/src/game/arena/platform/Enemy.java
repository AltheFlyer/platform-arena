package game.arena.platform;

import com.badlogic.gdx.math.Rectangle;


public class Enemy {
	
	Rectangle hitbox;
	float xMove;
	float yMove;
	float health;
	boolean destroy;
	//More to come
	
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
