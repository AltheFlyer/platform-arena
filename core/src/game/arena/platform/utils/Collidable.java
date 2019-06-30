package game.arena.platform.utils;

/**
 * [Collidable]
 * Interface for objects that can collide with each other
 * @param <T>
 * @version 1.0
 * @author Allen Liu
 * @since June 28, 2019
 */
public interface Collidable<T> {

    public void collide(Collidable<T> object);

}
