package game.arena.platform;

import com.badlogic.gdx.Gdx;

public abstract class Projectile {

	float x;
	float y;
	float xMove;
	float yMove;
	float speed;
	float age;
	float maxAge;
	float damage;
	boolean destroy;

	public Projectile(float x1, float y1, float xV, float yV, float spd, float a, float maxA, float dmg) {
		x = x1;
		y = y1;
		speed = spd;
		xMove = xV * speed;
		yMove = yV * speed;
		age = a;
		maxAge = maxA;
		destroy = false;
		damage = dmg;
	}
	
	public void move() {
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
		age += Gdx.graphics.getDeltaTime();
	}
	
	//If this isn't included, age values are ignored.
	public boolean checkAge() {
		return false;
	}

}
