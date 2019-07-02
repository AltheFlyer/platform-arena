package game.arena.platform.screen;

import com.badlogic.gdx.Game;
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
import game.arena.platform.entities.GameEntity;
import game.arena.platform.entities.MobileEntity;
import game.arena.platform.entities.enemies.Enemy;
import game.arena.platform.entities.enemies.WalkerEnemy;
import game.arena.platform.entities.players.Player;
import game.arena.platform.entities.players.WitchPlayer;
import game.arena.platform.entities.projectiles.Projectile;
import game.arena.platform.terrain.Platform;
import game.arena.platform.utils.Collidable;

import java.util.Iterator;

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

    private Array<Projectile> projectiles;
    private Array<Enemy> enemies;
    private Array<MobileEntity> constructs;

    private Array<MobileEntity> mobileEntities;

    //Includes Player + projectiles + enemies
    private Array<GameEntity> entities;

    //Includes platforms + entities;
    private Array<Collidable> collisionObjects;

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

        player = new WitchPlayer(this,  CAMERA_WIDTH/ 2, 0 + 400);

        platforms = new Array<Platform>();

        projectiles = new Array<Projectile>();
        enemies = new Array<Enemy>();
        constructs = new Array<MobileEntity>();
        mobileEntities = new Array<MobileEntity>();
        entities = new Array<GameEntity>();
        collisionObjects = new Array<Collidable>();

        //Debug
        platforms.add(new Platform(new Vector2(100, 400), new Vector2(500, 400)));
        platforms.add(new Platform(new Vector2(100, 320), new Vector2(500, 300)));
        platforms.add(new Platform(new Vector2(100, 100), new Vector2(500, 120)));

        enemies.add(new WalkerEnemy(this, 100, 100));

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

        moveEntities(delta);

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

        manageCollisions();

        runEntityActions(delta);

        draw();

        manageCollections();
    }

    /**
     * [draw]
     * ONLY USED TO DRAW ONTO THE SCREEN
     */
    private void draw() {
        render.begin();

        for (int i = 0; i < platforms.size; ++i) {
            platforms.get(i).draw(render);
        }

        for (int i = 0; i < entities.size; ++i) {
            entities.get(i).draw(render);
        }

        render.end();
    }

    /**
     * [runMobActions]
     * performs actions for all players and enemies if possible
     */
    private void runEntityActions(float delta) {
        for (int i = 0; i < entities.size; ++i) {
            entities.get(i).act(delta);
        }
    }

    private void moveEntities(float delta) {
        for (int i = 0; i < mobileEntities.size; ++i) {
            mobileEntities.get(i).move(delta);
        }
    }

    /**
     * [manageCollections]
     * updates collections of objects including: collisionObject, entities, etc.
     */
    private void manageCollections() {
        //Projectile check
        for (int i = 0; i < projectiles.size; ++i) {
            Projectile p = projectiles.get(i);
            //Check if out of bounds
            if ((-100 > p.getX()) || (LEVEL_WIDTH + 100 < p.getX()) || (-100 > p.getY()) || (LEVEL_HEIGHT + 100 < p.getY())) {
                p.setDestroyed(true);
            }
        }

        //Iterators
        iterateEntityArray(entities);
        iterateEntityArray(enemies);
        iterateEntityArray(projectiles);
        iterateEntityArray(constructs);

        mobileEntities.clear();
        mobileEntities.add(player);
        mobileEntities.addAll(enemies);
        mobileEntities.addAll(projectiles);
        mobileEntities.addAll(constructs);

        //Reset entities;
        entities.clear();
        entities.add(player);
        entities.addAll(enemies);
        entities.addAll(projectiles);
        entities.addAll(constructs);

        collisionObjects.clear();
        collisionObjects.addAll(platforms);
        collisionObjects.addAll(entities);
    }

    /**
     * [manageCollisions]
     * Performs collision checks using all objects. Very inefficient as of now O(n^2)
     */
    private void manageCollisions() {
        for (int i = 0; i < collisionObjects.size; ++i) {
            for (int j = i + 1; j < collisionObjects.size; ++j) {
                collisionObjects.get(i).collide(collisionObjects.get(j));
                collisionObjects.get(j).collide(collisionObjects.get(i));
            }
        }
    }

    /**
     * [iterateEntityArray]
     * iterates through an array containing GameEntity objects and removes those flagged by isDestroyed
     * @param array the Array of GameEntities
     */
    private void iterateEntityArray(Array array) {
        Iterator iter = array.iterator();
        while (iter.hasNext()) {
            GameEntity e = (GameEntity) iter.next();
            if (e.isDestroyed()) {
                e.destroy();
                iter.remove();
            }
        }
    }

    /**
     * [addProjectile]
     * Adds a projectile to the level
     * @param p the projectile to add
     */
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    /**
     * [addEnemy]
     * Adds an enemy to the level
     * @param e the enemy to add
     */
    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    /**
     * [addConstruct]
     * Adds a non-player/enemy/projectile object
     * @param construct the object to add
     */
    public void addConstruct(MobileEntity construct) {
        constructs.add(construct);
    }

    public Vector3 getMouse() {
        return mouse;
    }

    public Player getPlayer() {
        return player;
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
