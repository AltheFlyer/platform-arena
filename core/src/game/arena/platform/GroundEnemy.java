package game.arena.platform;

public class GroundEnemy extends Enemy{

	public GroundEnemy(float x1, float y1) {
		super(x1, y1, 25, 50, 0, 0, 15);
	}

	public void move(float x, float y, float frame) {
		if (hitbox.x > x) {
			xMove = -100;
		} else if (hitbox.x < x) {
			xMove = 100;
		}
		super.move(frame);
	}
	
}
