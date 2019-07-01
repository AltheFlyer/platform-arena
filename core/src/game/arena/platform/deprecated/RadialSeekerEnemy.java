package game.arena.platform.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RadialSeekerEnemy extends Enemy {

	public RadialSeekerEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 10, 10, 10);
		sprite = new Texture("seeker_enemy.png");
		flying = true;
	}
	
	public RadialSeekerEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}
	
	public void move(float x, float y, float frame) {
		//I have no clue anymore
		//This thing is buggy and i have no clue why anymore
		//It works 99% of the time
		if (Math.pow(x - xCentre, 2) + Math.pow(y - yCentre, 2) > 1) {
			float target = MathUtils.atan2(y - yCentre, x - xCentre);
			float velocity = MathUtils.atan2(yMove, xMove);
			float angle = (target - velocity) * MathUtils.radiansToDegrees;
			if (angle > 180) angle -= 360;
			if (angle < -180) angle += 360;
			
			if (angle > 100 * frame) {
				//System.out.println(target  * MathUtils.radiansToDegrees+ " " + " " + velocity  * MathUtils.radiansToDegrees+ " " + angle);
				if (angle > 0) {
					angle = (velocity * MathUtils.radiansToDegrees)  + (100 * frame);
				} else {
					angle = (velocity * MathUtils.radiansToDegrees) - (100 * frame);
				}
				xMove = 200 * MathUtils.cos(angle * MathUtils.degreesToRadians);
				yMove = 200 * MathUtils.sin(angle * MathUtils.degreesToRadians);
			} else {
				//System.out.println("AC");
				//System.out.println(target  * MathUtils.radiansToDegrees+ " " + " " + velocity  * MathUtils.radiansToDegrees+ " " + angle);
				xMove = 200 * MathUtils.cos(target);
				yMove = 200 * MathUtils.sin(target);
			}
			
			} else {
			//Prevent vibrating enemies
			xMove = 0;
			yMove = 0;
		}
		super.move(frame);
	}

}
