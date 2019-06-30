package game.arena.platform.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;
import game.arena.platform.utils.Collidable;

/**
 * [Entity]
 * abstract class for all basic entities in game
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class Entity implements Collidable {

    protected Vector2 position;
    protected Rectangle hitbox;

    protected final Level level;

    public Entity(Level level) {
        this.level = level;
    }

    /**
     * [setPosition]
     * sets the players position while ignoring previous position
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
        hitbox.setPosition(position);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
}
