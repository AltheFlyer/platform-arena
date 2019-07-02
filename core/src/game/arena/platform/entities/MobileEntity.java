package game.arena.platform.entities;

import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;

/**
 * [MobileEntity]
 * basic class for any moving entity
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class MobileEntity extends GameEntity {

    protected Vector2 lastPosition;

    public MobileEntity(Level level) {
        super(level);
    }

    public abstract void move(float delta);

    public void changePosition(Vector2 displacement) {
        position.add(displacement);
        hitbox.setPosition(position);
    }
}
