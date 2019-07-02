package game.arena.platform.entities.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import game.arena.platform.entities.constructs.Cauldron;
import game.arena.platform.entities.projectiles.PotionProjectile;
import game.arena.platform.entities.projectiles.Projectile;
import game.arena.platform.screen.Level;

/**
 * [WitchPlayer]
 *
 * @version 1.0
 * @author Allen Liu
 * @since July 1, 2019
 */
public class WitchPlayer extends Player {

    float chargePower;

    /**
     * @param level The level that the player is in
     * @param x     the left x position of the player
     * @param y     the bottom y position of the player
     */
    public WitchPlayer(Level level, float x, float y) {
        super(level, x, y, 100);
    }


    /**
     * [attack]
     * Used to attack or use actions in the level, use level hooks to do these
     * @param delta the time since the last method call
     */
    @Override
    public void act(float delta) {

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (chargePower == 0) {
                chargePower = 350;
            } else if (chargePower < 1100) {
                chargePower += 500 * delta;
                if (chargePower > 1100) {
                    chargePower = 1100;
                }
            }
        } else if (chargePower > 400) {
            float theta = MathUtils.atan2(level.getMouse().y - getCenter().y, level.getMouse().x - getCenter().x);
            level.addProjectile(
                    new PotionProjectile(
                            level,
                            getCenter(),
                            new Vector2(chargePower * MathUtils.cos(theta), chargePower * MathUtils.sin(theta)),
                            35,
                            true)
            );
            chargePower = 0;
        }



        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            float theta = MathUtils.atan2(level.getMouse().y - getCenter().y, level.getMouse().x - getCenter().x);
            level.addProjectile(
                    new Projectile(
                            level,
                            getCenter(),
                            new Vector2(400 * MathUtils.cos(theta), 400 * MathUtils.sin(theta)),
                            1,
                            true)
            );
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            level.addConstruct(
                    new Cauldron(
                            level,
                            position
                    )
            );
        }
    }
}
