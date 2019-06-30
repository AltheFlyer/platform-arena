package game.arena.platform;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.arena.platform.screen.Level;

/**
 * [ArenaGameWindow]
 * The main window for the Platform Arena Game
 * @version 1.0
 * @author Allen Liu
 * @since June 26, 2019
 */
public class ArenaGameWindow extends Game {

    private SpriteBatch batch;
    private ShapeRenderer render;

    public void create() {
        batch = new SpriteBatch();
        render = new ShapeRenderer();
        render.setAutoShapeType(true);

        setScreen(new Level(this, 800, 600));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        render.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return render;
    }
}
