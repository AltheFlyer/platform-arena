package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

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
	
	public void move(float frame) {
		hitbox.x += xMove * frame;
		hitbox.y += yMove * frame;
		age += Gdx.graphics.getDeltaTime();
	}
	
	//If this isn't included, age values are ignored.
	public boolean checkAge() {
		return false;
	}

}
