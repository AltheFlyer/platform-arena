package game.arena.platform.utils;

/**
 * [CollisionType.java]
 * Used to recognize types of collision, based on the main object colliding on it's side
 * onto another object. (e.g the right of the player collides with a platform)
 * @version 1.0
 * @author Allen Liu
 * @since June 28, 2019
 */
public enum CollisionType {
    LEFT,
    RIGHT,
    FALLING
}
