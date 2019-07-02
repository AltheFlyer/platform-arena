package game.arena.platform.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;
import game.arena.platform.utils.MiniMath;

/**
 * [PotionProjectile]
 * A Projectile that creates an area splash an follows an arcing trail
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public class PotionProjectile extends Projectile {

    public PotionProjectile(Level level, Vector2 position, Vector2 velocity, int damage, boolean isFriendly) {
        super(level, position, velocity, damage, isFriendly);
    }

    @Override
    public void move(float delta) {
        addGravity(1000, delta);
        super.move(delta);

        if (position.y <= 0) {
            setDestroyed(true);
        }
    }

    @Override
    public void collide(Collidable object) {
        super.collide(object);
        if (hasPlatformCollision(object)) {
            setDestroyed(true);
        }
    }

    @Override
    public void destroy() {
        level.addProjectile(new AOECloud(level, new Vector2(position.x - 75 + (hitbox.width / 2), position.y - 75), damage, isFriendly, 150));
    }
}
