package game.arena.platform.entities.enemies;

import game.arena.platform.entities.Mob;
import game.arena.platform.screen.Level;
import game.arena.platform.utils.Collidable;

/**
 * [Enemy]
 * Basic class for all enemies
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public class Enemy extends Mob {

    public Enemy(Level level) {
        super(level);
    }

    @Override
    public void move(float delta) {

    }

    @Override
    public void collide(Collidable object) {

    }
}
