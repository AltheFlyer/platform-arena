package game.arena.platform.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.screen.Level;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;
import game.arena.platform.utils.MiniMath;

/**
 * [WalkerEnemy]
 * A simple enemy with the ability to walk towards the player
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public class WalkerEnemy extends Enemy {

    private final float GRAVITY = 1100;
    private boolean isGrounded;

    public WalkerEnemy(Level level, float x, float y) {
        super(level, 40);
        setHitbox(new Rectangle(x, y, 50, 100));
    }

    @Override
    public void move(float delta) {
        lastPosition = new Vector2(position);
        if (level.getPlayer().getX() < position.x - 5) {
            changePosition(new Vector2(-200 * delta, 0));
        } else if (level.getPlayer().getX() > position.x + 5) {
            changePosition(new Vector2(200 * delta, 0));
        }
    }

    @Override
    public void act(float delta) {

    }

    /**
     * [draw]
     * draws the entity
     *
     * @param render the ShapeRenderer to use for drawing
     */
    @Override
    public void draw(ShapeRenderer render) {
        render.set(ShapeRenderer.ShapeType.Filled);
        render.setColor(Color.RED);
        render.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    @Override
    public void collide(Collidable object) {
        if (object instanceof Platform) {
            //Check if player was above the platform previously
            //Done by intersection of line

            if (position.y <= lastPosition.y) {
                Platform platform = (Platform) object;
                float platformSlope = (platform.getEndPoint().y - platform.getStartPoint().y) / (platform.getEndPoint().x - platform.getStartPoint().x);
                float platformIntercept = platform.getStartPoint().y - (platformSlope * platform.getStartPoint().x);

                float x = position.x + (hitbox.width / 2);
                float y = platformSlope * x + platformIntercept;

                if ((MiniMath.isBetween(y, position.y, lastPosition.y) &&
                        MiniMath.isBetween(x, platform.getStartPoint().x, platform.getEndPoint().x) &&
                        MiniMath.isBetween(y, platform.getStartPoint().y, platform.getEndPoint().y))) {
                    setPosition(position.x, y);
                }
            }
        }
    }
}
