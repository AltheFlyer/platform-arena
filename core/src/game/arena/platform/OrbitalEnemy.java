package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

//An enemy that spawns close by the player and rotates 360 degrees around before attacking
public class OrbitalEnemy extends Enemy {
	
	//In degrees
	float angle;
	float distance;
	
	public OrbitalEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 3);
		angle = 90;
		sprite = new Texture("seeker_enemy.png");
		flying = true;
		distance = 200;
	}
	
	public OrbitalEnemy(float x1, float y1, float dist) {
		this(x1, y1);
		distance = dist;
	}
	
	public OrbitalEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}
	
	public OrbitalEnemy(Vector2 pos, float dist) {
		this(pos.x, pos.y);
		distance = dist;
	}
	
	public void move(float x, float y, float frame) {

		hitbox.x = x + distance * MathUtils.cos(angle * MathUtils.degreesToRadians);
		hitbox.y = y + distance * MathUtils.sin(angle * MathUtils.degreesToRadians);
		
		angle -= 72 * frame;
		
		//Something happens when 1 full revolution is made around the player
	}
}
