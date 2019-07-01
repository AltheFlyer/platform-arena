package game.arena.platform.entities.constructs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.entities.GameEntity;
import game.arena.platform.entities.MobileEntity;
import game.arena.platform.entities.projectiles.Projectile;
import game.arena.platform.screen.Level;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;
import game.arena.platform.utils.MiniMath;

/**
 * [Cauldron]
 * A cauldron object, which can be created by the Witch Player
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public class Cauldron extends MobileEntity {

    private float timer;
    private final float MAX_TIME = 15f;
    private int power;

    private final float gravity = 1100f;
    private Vector2 velocity;

    private boolean isGrounded;

    public Cauldron(Level level, Vector2 position) {
        super(level);
        this.position = new Vector2(position);
        this.lastPosition =  new Vector2(position);
        setHitbox(new Rectangle(position.x, position.y, 50, 50));

        power = 0;
        timer = 0;
        velocity = new Vector2(0, 0);
    }

    @Override
    public void act(float delta) {
        timer += delta;
        if (timer > MAX_TIME) {
            System.out.println(power);
            setDestroyed(true);
        }
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
        render.setColor(Color.BLACK);

        render.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    @Override
    public void move(float delta) {
        lastPosition = new Vector2(position);
        if (!isGrounded) {
            velocity.y -= gravity * delta;
            position.y += velocity.y * delta;
            hitbox.setPosition(position);
        } else {
            velocity.y = 0;
        }
    }

    @Override
    public void collide(Collidable object) {
        if (object instanceof Projectile) {
            Projectile p = (Projectile) object;
            if (p.getHitbox().overlaps(hitbox)) {
                p.setDestroyed(true);
                power++;
                timer += 2;
            }
        }
        if (object instanceof Platform) {
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
                    isGrounded = true;
                }
            }
        }
    }

}
