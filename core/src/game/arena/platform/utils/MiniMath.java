package game.arena.platform.utils;

/**
 * [MiniMath.java]
 * Used to store simple math methods
 * @version 1.0
 * @author Allen Liu
 * @since June 28, 2019
 */
public class MiniMath {

    /**
     * pisBetween]
     * Checks if a given value is between two bounds
     * @param value the value to compare
     * @param boundA one of the bounds to use in comparison
     * @param boundB one of the bounds to use in comparison
     * @return boolean, whether the value is between the two bounds
     */
    public static boolean isBetween(float value, float boundA, float boundB) {
        return (value >= boundA && value <= boundB) || (value <= boundA && value >= boundB);
    }
}
