package game.arena.platform.entities.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import game.arena.platform.entities.Mob;
import game.arena.platform.entities.MobileEntity;
import game.arena.platform.screen.Level;
import com.badlogic.gdx.math.Rectangle;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;
import game.arena.platform.utils.MiniMath;

/**
 * [Player.java]
 * The class for a basic player character
 * @version 1.1
 * @author Allen Liu
 * @since June 30, 2019
 */
public abstract class Player extends Mob {

    //Player physics values, in pixels
    private static float maxJumpHeight = 200;
    private static float minJumpHeight = 25;
    private static float maxJumpTime = 0.35f;
    private static float minJumpVelocity;
    private static float jumpVelocity;
    private static float gravity;

    private float walkAngle;

    private float invincibleTime;
    private final float MAX_INVINCIBILITY_TIME = 1f;

    protected boolean ignorePlatforms;

    Array<Vector2> locations;

    static {
        gravity = (2 * maxJumpHeight) / (maxJumpTime * maxJumpTime);
        jumpVelocity = gravity * maxJumpTime;
        minJumpVelocity = (float) Math.sqrt(2 * gravity * minJumpHeight);

        System.out.println("Gravity " + gravity);
        System.out.println("Jump Velocity " + jumpVelocity);
        System.out.println("Min jump velocity " + minJumpVelocity);
    }

    /**
     *
     * @param level The level that the player is in
     * @param x the left x position of the player
     * @param y the bottom y position of the player
     * @param hp the maximum health of the player
     */
    public Player(Level level, float x, float y, float hp) {
        super(level, hp);

        position = new Vector2(x, y);
        lastPosition = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        hitbox = new Rectangle(x, y, 50, 100);
        invincibleTime = MAX_INVINCIBILITY_TIME;

        isGrounded = false;
        ignorePlatforms = false;
        walkAngle = 0;

        locations = new Array<Vector2>();
    }

    /**
     * [move]
     * Moves the player and updates velocity values
     * @param delta the time since the last move in seconds
     */
    @Override
    public void move(float delta) {
        lastPosition = new Vector2(position);

        velocity.x = 0;

        //Input
        if (Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x = -400 * MathUtils.cos(walkAngle);
            //Move left when platform slope is positive: Move downwards, stick to platform
            if (walkAngle > 0) {
                position.y -= 400 * delta * MathUtils.sin(walkAngle);
                //velocity.y = -400 * MathUtils.sin(walkAngle);
            }
            //Move left when platform slope is negative: Move upwards, use lastPosition to allow for platform check
            if (walkAngle < 0) {
                lastPosition.y += 400 * delta * MathUtils.sin(Math.abs(walkAngle));
                //velocity.y = 400 * MathUtils.sin(Math.abs(walkAngle));
            }
        }

        if (Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x = 400 * MathUtils.cos(walkAngle);
            //Move right when platform slope is positive: Move upwards, use lastPosition to allow for platform check
            if (walkAngle > 0) {
                lastPosition.y += 400 * delta * MathUtils.sin(walkAngle);
                //velocity.y = 400 * MathUtils.sin(walkAngle);
            }
            //Move right when platform slope is negative: Move downwards, stick to platform
            if (walkAngle < 0) {
                position.y -= 400 * delta * MathUtils.sin(Math.abs(walkAngle));
                //velocity.y = -400 * MathUtils.sin(Math.abs(walkAngle));
            }
        }

        ignorePlatforms = Gdx.input.isKeyPressed(Keys.S);

        addGravity(gravity, delta);

        //Gravity
        if (isGrounded) {
            //Jumping
            if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
                velocity.y = jumpVelocity;
                isGrounded = false;
            }
        }

        if (!Gdx.input.isKeyPressed(Keys.SPACE)) {
            if (velocity.y > minJumpVelocity) {
                velocity.y = minJumpVelocity;
            }
        }

        Vector2 unModVelocity = new Vector2(velocity);

        position.add(velocity.scl(delta));

        velocity = unModVelocity;

        hitbox.setPosition(position);

        isGrounded = false;

        walkAngle = 0;

        locations.add(new Vector2(position));
    }

    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * [draw]
     * draws the player
     * @param render the shape renderer that is used to draw
     */
    @Override
    public void draw(ShapeRenderer render) {
        if (isGrounded) {
            render.setColor(Color.RED);
        } else {
            render.setColor(Color.BLACK);
        }
        render.set(ShapeRenderer.ShapeType.Filled);
        render.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        render.setColor(new Color(0, 0, 1, 0.5f));
        render.rect(hitbox.x - 400 * (5/30.0f) * MathUtils.cos(walkAngle), hitbox.y - 400 * (5/30.0f) * MathUtils.sin(walkAngle), hitbox.width, hitbox.height);
        render.setColor(new Color(0, 1, 0, 0.5f));
        render.rect(hitbox.x + 400 * (5/30.0f) * MathUtils.cos(walkAngle), hitbox.y + 400 * (5/30.0f) * MathUtils.sin(walkAngle), hitbox.width, hitbox.height);

        render.set(ShapeRenderer.ShapeType.Line);
        render.setColor(Color.RED);
        for (int i = 1; i < locations.size; ++i) {
            render.line(locations.get(i-1), locations.get(i));
        }
    }

    @Override
    public void collide(Collidable object) {
        if (!ignorePlatforms && hasPlatformCollision(object)) {
            walkAngle = ((Platform) object).getAngle();// % (MathUtils.PI / 2);
        }
    }

    /**
     * [damagePlayer]
     * damages the player by some amount
     * @param damage the amount of damage taken
     */
    @Override
    public void damageEntity(float damage) {
        if (invincibleTime <= 0) {
            health -= damage;
            invincibleTime = MAX_INVINCIBILITY_TIME;
        }
    }

    /**
     * [getCenter]
     * gets the exact center of the player's hitbox
     * @return Vector2, the point representing the center of the player
     */
    public Vector2 getCenter() {
        Vector2 center = new Vector2();
        return hitbox.getCenter(center);
    }

    public void setGrounded(boolean isGround) {
        isGrounded = isGround;
    }
}
