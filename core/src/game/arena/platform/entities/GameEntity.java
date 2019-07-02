package game.arena.platform.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;
import game.arena.platform.utils.Collidable;

/**
 * [GameEntity]
 * abstract class for all basic entities in game
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class GameEntity implements Collidable {

    protected Vector2 position;
    protected Rectangle hitbox;

    protected final Level level;

    protected boolean isDestroyed;

    public GameEntity(Level level) {
        this.level = level;
        isDestroyed = false;
    }

    /**
     * [setPosition]
     * sets the players position while ignoring previous position
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
        hitbox.setPosition(position);
    }

    public abstract void act(float delta);

    public void destroy() {}

    /**
     * [draw]
     * draws the entity
     * @param render the ShapeRenderer to use for drawing
     */
    public abstract void draw(ShapeRenderer render);

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
        position = new Vector2(hitbox.x, hitbox.y);
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

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

}
