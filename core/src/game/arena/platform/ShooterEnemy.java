package game.arena.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ShooterEnemy extends Enemy {

	float cooldown;
	static final float ATTACK_COOLDOWN = 3f;

	public ShooterEnemy(float x1, float y1) {
		super(x1, y1, 25, 25, 0, 0, 7);
		score = 2;
		sprite = new Texture("seeker_enemy.png");
		flying = true;
		cooldown = 3f;
		type = AttackType.single;
	}

	public ShooterEnemy(Vector2 pos) {
		this(pos.x, pos.y);
	}

	public void move(float x, float y, float frame) {
		if (Math.pow(x - xCentre, 2) + Math.pow(y - yCentre, 2) > 1) {
			float speed;
			if (cooldown > 1) speed = 75;
			else speed = 25;
			xMove = speed * MathUtils.cos(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
			yMove = speed * MathUtils.sin(MathUtils.atan2(y - hitbox.y, x - hitbox.x));
		} else {
			// Prevent vibrating enemies
			xMove = 0;
			yMove = 0;
		}
		super.move(frame);

		cooldown -= frame;
		if (cooldown < 0) {
			cooldown = 0;
		}
	}

	public boolean canAttack(float x, float y, float frame) {
		return cooldown <= 0;
	}

	public Projectile attackSingle(float x, float y, float frame) {
		cooldown = ATTACK_COOLDOWN;
		BasicProjectile p = new BasicProjectile(xCentre, yCentre, MathUtils.atan2(y - yCentre, x - xCentre), 200, 0);
		p.damage = 10;
		return p;
	}

}
