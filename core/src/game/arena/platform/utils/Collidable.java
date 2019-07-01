package game.arena.platform.utils;

/**
 * [Collidable]
 * Interface for objects that can collide with each other
 * @version 1.0
 * @author Allen Liu
 * @since June 28, 2019
 */
public interface Collidable {

    public void collide(Collidable object);

}
