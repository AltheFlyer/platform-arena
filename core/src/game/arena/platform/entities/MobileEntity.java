package game.arena.platform.entities;

import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;
import game.arena.platform.utils.MiniMath;

import java.awt.*;

/**
 * [MobileEntity]
 * basic class for any moving entity
 * @version 1.0
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class MobileEntity extends GameEntity {

    protected Vector2 lastPosition;

    protected Vector2 velocity;

    public MobileEntity(Level level) {
        super(level);
        velocity = new Vector2(0, 0);
        lastPosition = new Vector2();
    }

    public abstract void move(float delta);

    /**
     * [changePosition]
     * changes the position and hitbox location of the entity
     * @param displacement the Vector2 to move the entity by
     */
    public void changePosition(Vector2 displacement) {
        position.add(displacement);
        hitbox.setPosition(position);
    }

    /**
     * [checkPlatformCollision]
     * checks if a proper platform collision should happen, at sets the objects position if to the colliding point
     * @param object the object to check collision for (not necessarily a platform)
     * @return boolean, whether a platform collision has occured or not
     */
    public boolean hasPlatformCollision(Collidable object) {
        if (object instanceof Platform) {
            //Check if player was above the platform previously
            //Done by intersection of line

            if (position.y <= lastPosition.y) {
                Platform platform = (Platform) object;
                float platformSlope = (platform.getEndPoint().y - platform.getStartPoint().y) / (platform.getEndPoint().x - platform.getStartPoint().x);
                float platformIntercept = platform.getStartPoint().y - (platformSlope * platform.getStartPoint().x);

                float x = position.x + (hitbox.width / 2);
                float y = platformSlope * x + platformIntercept;

                if (MiniMath.isBetween(y, position.y, lastPosition.y) &&
                        MiniMath.isBetween(x, platform.getStartPoint().x, platform.getEndPoint().x) &&
                        MiniMath.isBetween(y, platform.getStartPoint().y, platform.getEndPoint().y)) {
                    setPosition(position.x, y);
                    setGrounding(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * [addGravity]
     * adds the effect of gravity to the entity's velocity
     * @param gravity the force of gravity in pixels/s, assumed to be positive
     * @param delta the amount of time passed since the last gravity tick
     */
    public void addGravity(float gravity, float delta) {
        if (isGrounded()) {
            velocity.y = 0;
        } else {
            velocity.y -= gravity * delta;
        }
    }
}
