package game.arena.platform.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import game.arena.platform.entities.Mob;
import game.arena.platform.entities.enemies.Enemy;
import game.arena.platform.entities.players.Player;
import game.arena.platform.screen.Level;
import game.arena.platform.utils.Collidable;

/**
 * [Projectile]
 * abstract class for all projectiles, player or enemy
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public class Projectile extends Mob {

    protected boolean isFriendly;
    protected Vector2 velocity;

    protected int damage;
    protected boolean isDestroyed;

    public Projectile(Level level, Vector2 position, Vector2 velocity, int damage, boolean isFriendly) {
        super(level);
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.isFriendly = isFriendly;
    }

    @Override
    public void move(float delta) {
        lastPosition = position;
        position.add(velocity);
    }

    @Override
    public void collide(Collidable object) {
        if (!isFriendly && (object instanceof Player)) {
            ((Player) object).damagePlayer(damage);
            isDestroyed = true;
        } else if (isFriendly && (object instanceof Enemy)) {

        }
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }
}
