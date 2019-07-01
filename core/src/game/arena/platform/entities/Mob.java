package game.arena.platform.entities;

import game.arena.platform.screen.Level;
import game.arena.platform.utils.Collidable;

/**
 * [Mob]
 * Class for all players and enemies
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public abstract class Mob extends MobileEntity {

    protected float health;
    protected final float MAX_HEALTH;

    public Mob(Level level, float maxHealth) {
        super(level);
        this.MAX_HEALTH = maxHealth;
    }

    /**
     * [isFriendly]
     * @return boolean, whether the entity is friendly (player) or not (enemy)
     */
    public abstract boolean isFriendly();

    /**
     * [damageEntity]
     * damages the entity by a certain amount
     * @param damage the amount of damage to deal
     */
    public void damageEntity(float damage) {
        health -= damage;
        if (health < 0) {
            setDestroyed(true);
        }
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return MAX_HEALTH;
    }
}
