package game.arena.platform.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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

    private float walkAngle;

    public WalkerEnemy(Level level, float x, float y) {
        super(level, 40);
        setHitbox(new Rectangle(x, y, 50, 100));
        walkAngle = 0;
    }

    @Override
    public void move(float delta) {
        lastPosition = new Vector2(position);

        addGravity(1100, delta);

        changePosition(new Vector2(0,velocity.y * delta));

        if (level.getPlayer().getX() < position.x - 5) {
            changePosition(new Vector2(-200 * delta, 0));
            if (walkAngle > 0) {
                position.y -= 200 * delta * MathUtils.sin(walkAngle);
                //velocity.y = -400 * MathUtils.sin(walkAngle);
            }
            //Move left when platform slope is negative: Move upwards, use lastPosition to allow for platform check
            if (walkAngle < 0) {
                lastPosition.y += 200 * delta * MathUtils.sin(Math.abs(walkAngle));
                //velocity.y = 400 * MathUtils.sin(Math.abs(walkAngle));
            }
        } else if (level.getPlayer().getX() > position.x + 5) {
            changePosition(new Vector2(200 * delta, 0));
            if (walkAngle > 0) {
                lastPosition.y += 200 * delta * MathUtils.sin(walkAngle);
                //velocity.y = 400 * MathUtils.sin(walkAngle);
            }
            //Move right when platform slope is negative: Move downwards, stick to platform
            if (walkAngle < 0) {
                position.y -= 200 * delta * MathUtils.sin(Math.abs(walkAngle));
                //velocity.y = -400 * MathUtils.sin(Math.abs(walkAngle));
            }
        }

        if (position.y < 0) {
            setPosition(position.x, 0);
            isGrounded = true;
        }

        hitbox.setPosition(position);

        isGrounded = false;
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
        if (hasPlatformCollision(object)) {
            walkAngle = ((Platform) object).getAngle();
        }
    }
}
