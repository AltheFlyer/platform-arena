package game.arena.platform.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.entities.Mob;
import game.arena.platform.entities.MobileEntity;
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
public class Projectile extends MobileEntity {

    protected boolean isFriendly;
    protected Vector2 velocity;

    protected int damage;

    public Projectile(Level level, Vector2 position, Vector2 velocity, int damage, boolean isFriendly) {
        super(level);
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.isFriendly = isFriendly;

        this.setHitbox(new Rectangle(position.x, position.y, 5, 5));
    }

    @Override
    public void move(float delta) {
        lastPosition = position;
        Vector2 preModVelocity = new Vector2(velocity);

        position.add(velocity.scl(delta));
        hitbox.setPosition(position);

        velocity = preModVelocity;
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void collide(Collidable object) {
        if (object instanceof Mob) {
            Mob mob = (Mob) object;
            if (mob.isFriendly() != isFriendly && mob.getHitbox().overlaps(hitbox)) {
                isDestroyed = true;
                mob.damageEntity(damage);
            }
        }
    }

    @Override
    public void draw(ShapeRenderer render) {
        render.set(ShapeRenderer.ShapeType.Filled);
        render.setColor(Color.YELLOW);
        render.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

}
