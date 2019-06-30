package game.arena.platform.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import game.arena.platform.ArenaGameWindow;
import game.arena.platform.entities.players.Player;
import game.arena.platform.terrain.Platform;

/**
 * [Level.java]
 * The base class for all normal levels
 * @version 1.0
 * @author Allen Liu
 * @since June 26, 2019
 */
public class Level implements Screen {

    private final ArenaGameWindow game;
    private OrthographicCamera camera;

    private final int LEVEL_WIDTH;
    private final int LEVEL_HEIGHT;

    private final int CAMERA_WIDTH;
    private final int CAMERA_HEIGHT;

    private Player player;

    //public Platform platform;
    private Array<Platform> platforms;

    SpriteBatch batch;
    ShapeRenderer render;

    private Vector3 mouse;

    public Level(ArenaGameWindow game, int width, int height) {
        this.game = game;

        batch = game.getSpriteBatch();
        render = game.getShapeRenderer();

        CAMERA_WIDTH = LEVEL_WIDTH = width;
        CAMERA_HEIGHT = LEVEL_HEIGHT = height;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

        player = new Player(this,  CAMERA_WIDTH/ 2, 0 + 400, 100);

        platforms = new Array<Platform>();

        platforms.add(new Platform(new Vector2(100, 100), new Vector2(500, 100)));
        platforms.add(new Platform(new Vector2(100, 320), new Vector2(500, 300)));
        platforms.add(new Platform(new Vector2(100, 100), new Vector2(500, 120)));


        for (int i = 0; i < platforms.size; ++i) {
            System.out.println(platforms.get(i).getAngle() / MathUtils.PI);
        }
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        camera.position.x = player.getHitbox().x;
        camera.position.y = player.getHitbox().y;

        if (camera.position.x < CAMERA_WIDTH / 2) {
            camera.position.x = CAMERA_WIDTH / 2;
        } else if (camera.position.x > LEVEL_WIDTH - (CAMERA_WIDTH / 2)) {
            camera.position.x = LEVEL_WIDTH - (CAMERA_WIDTH / 2);
        }
        if (camera.position.y < CAMERA_HEIGHT / 2) {
            camera.position.y = CAMERA_HEIGHT / 2;
        } else if (camera.position.y > LEVEL_HEIGHT - (CAMERA_HEIGHT / 2)) {
            camera.position.y = LEVEL_HEIGHT - (CAMERA_HEIGHT / 2);
        }

        //Graphics
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        camera.update();
        render.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

        player.move(delta);
        for (int i = 0; i < platforms.size; ++i) {
            player.collide(platforms.get(i));
        }


        //Keep in bounds, equality checks ensure that collisions exist even without constant gravity
        if (player.getX() <= 0) {
            player.setPosition(0, player.getY());
        } else if (player.getX() + player.getHitbox().width >= LEVEL_WIDTH) {
            player.setPosition(LEVEL_WIDTH - player.getHitbox().width, player.getY());
        }

        if (player.getY() <= 0) {
            player.setPosition(player.getX(), 0);
            player.setGrounded(true);
        } else if (player.getY() + player.getHitbox().height >= LEVEL_HEIGHT) {
            player.setPosition(player.getX(), LEVEL_HEIGHT - player.getHitbox().height);
        }


        draw();
    }

    /**
     * [draw]
     * ONLY USED TO DRAW ONTO THE SCREEN
     */
    public void draw() {
        render.begin();
        render.setColor(Color.GREEN);

        player.draw(render);
        for (int i = 0; i < platforms.size; ++i) {
            platforms.get(i).draw(render);
        }

        render.end();
    }









    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for Game.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
    @Override
    public void show() {

    }
}
