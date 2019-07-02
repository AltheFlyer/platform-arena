package game.arena.platform.entities.projectiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;

/**
 * [AOECloud]
 * Used for aoe effects (explosions, etc)
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public class AOECloud extends Projectile {

    public AOECloud(Level level, Vector2 position, int damage, boolean isFriendly, float size) {
        super(level, position, new Vector2(0, 0), damage, isFriendly);
        setHitbox(new Rectangle(position.x, position.y, size, size));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDestroyed(true);
    }
}
