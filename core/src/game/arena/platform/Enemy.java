package game.arena.platform;

import com.badlogic.gdx.Gdx;

public class Enemy {
	
	float x;
	float y;
	float xMove;
	float yMove;
	float health;
	//More to come
	
	public Enemy(float x1, float y1, float xV, float yV, float hp) {
		x = x1;
		y = y1;
		xMove = xV;
		yMove = yV;
		health = hp;
	}
	
	public void move() {
		x += xMove * Gdx.graphics.getDeltaTime();
		y += yMove * Gdx.graphics.getDeltaTime();
	}
	
	public void damage(float damage) {
		health -= damage;
	}
}
