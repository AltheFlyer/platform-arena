package game.arena.platform.entities.enemies;

import game.arena.platform.entities.Mob;
import game.arena.platform.screen.Level;

/**
 * [Enemy]
 * Basic class for all enemies
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class Enemy extends Mob {

    public Enemy(Level level, float maxHealth) {
        super(level, maxHealth);
    }

    @Override
    public abstract void move(float delta);

    @Override
    public boolean isFriendly() {
        return false;
    }
}
