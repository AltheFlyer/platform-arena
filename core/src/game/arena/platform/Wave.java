package game.arena.platform;

import com.badlogic.gdx.utils.ObjectMap;

public class Wave {
	
	ObjectMap<Enemy, Float> enemies;
	float delay;
	float time;
	float minTime;
	float maxTime;
	
	public Wave(float del, float min, float max) {
		delay = del;
		minTime = min;
		maxTime = max;
		time = 0;
		enemies = new ObjectMap<Enemy, Float>();
	}
	
	public Wave() {
		this(0, 0, Integer.MAX_VALUE);
	}
	
	public void put(Enemy e, float time) {
		enemies.put(e, time + delay);
	}
}
