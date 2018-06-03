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
	boolean destroyed;

	public Projectile(float x1, float y1, float xV, float yV, float spd, float a, float maxA) {
		x = x1;
		y = y1;
		speed = spd;
		xMove = xV * speed;
		yMove = yV * speed;
		age = a;
		maxAge = maxA;
		destroyed = false;
	}
	
	public void move() {
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
	}

}
