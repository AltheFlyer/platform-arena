package game.arena.platform.entities;

import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;

/**
 * [Mob]
 * basic class for any moving entity
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class Mob extends Entity {

    protected Vector2 lastPosition;

    public Mob(Level level) {
        super(level);
    }

    public abstract void move(float delta);

}
