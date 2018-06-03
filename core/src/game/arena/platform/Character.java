package game.arena.platform;

public class Character {
	
	float x;
	float y;
	float xMove, yMove;
	float yLast;
	boolean onGround;
	boolean hasCollided;
	
	public Character(float x1, float y1) {
		x = x1;
		y = y1;
		yLast = y;
		xMove = 0;
		yMove = 0;
		onGround = false;
	}
	
}
