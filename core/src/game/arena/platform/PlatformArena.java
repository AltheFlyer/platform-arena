package game.arena.platform;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PlatformArena extends ApplicationAdapter {

	Texture img;
	Character player;
	OrthographicCamera camera;
	ShapeRenderer render;
	Array<Rectangle> platforms;
	Array<Projectile> projectiles;
	Array<Enemy> enemies;
	final int ARENA_WIDTH = 800;
	final int ARENA_HEIGHT = 600;

	@Override
	public void create() {
		player = new Knight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, ARENA_WIDTH, ARENA_HEIGHT);

		render = new ShapeRenderer();
		render.setAutoShapeType(true);

		platforms = new Array<Rectangle>();
		//Default level
		initializePlatforms();
		
		projectiles = new Array<Projectile>();
		enemies = new Array<Enemy>();
		enemies.add(new DummyEnemy(400, 400));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		render.setProjectionMatrix(camera.combined);

		render.begin();
		// Draw player
		render.set(ShapeType.Filled);
		render.setColor(Color.GREEN);
		render.box(player.x, player.y, 0, 50, 100, 0);

		// Draw platforms
		render.set(ShapeType.Filled);
		render.setColor(Color.BROWN);
		for (Rectangle platform : platforms) {
			render.box(platform.x, platform.y, 0, platform.width, platform.height, 0);
		}

		// Draw projectiles
		render.setColor(Color.BLACK);
		for (Projectile p : projectiles) {
			render.circle(p.x, p.y, 3);
		}
		
		//Draw enemies
		render.setColor(Color.RED);
		for (Enemy e: enemies) {
			render.box(e.x, e.y, 0, 50, 100, 0);
		}
		render.end();

		move();

		platformCollisions();
		
		//Enemy stuff
		for (Enemy e: enemies) {
			e.move();
			for (Projectile p: projectiles) {
				if (new Rectangle(e.x, e.y, 50, 100).contains(new Vector2(p.x, p.y))) {
					p.destroy = true;
					e.damage(p.damage);
				}
			}
		}
		
		doProjectileStuff();
		

	}

	@Override
	public void dispose() {
		// batch.dispose();
	}

	public void move() {
		player.yLast = player.y;
		// Movement
		// Some of this will be moved to the character class
		// Gravity
		float deltaY = 0;
		// if (!player.onGround)
		deltaY -= 120 * Gdx.graphics.getDeltaTime();
		// If the up key is help while jumping
		// Jump a bit higher
		// if (!player.onGround && Gdx.input.isKeyPressed(Keys.W) && player.yMove > 0) {
		// deltaY += player.yMove * Gdx.graphics.getDeltaTime() * 2.8;
		// }
		player.yMove += deltaY;
		if (player.yMove < -400)
			player.yMove = -400;

		// Jumping
		if (player.onGround && Gdx.input.isKeyJustPressed(Keys.W)) {
			player.yMove = 30;
			player.onGround = false;
		}
		player.y += player.yMove;

		// Horizontal movement
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.x -= 400 * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			player.x += 400 * Gdx.graphics.getDeltaTime();
		}

		// Keep player in bounds
		if (player.y <= 0) {
			player.y = 0;
			player.yMove = 0;
			player.onGround = true;
		}
		if (player.x < 0) {
			player.x = 0;
		} else if (player.x > 800 - 50) {
			player.x = 800 - 50;
		}

		if (player.yLast != player.y) {
			player.onGround = false;
		}
	}

	public void initializePlatforms() {
		platforms.add(new Rectangle(0, 150, 160, 1));
		platforms.add(new Rectangle(320, 150, 160, 1));
		platforms.add(new Rectangle(640, 150, 160, 1));
		platforms.add(new Rectangle(160, 300, 160, 1));
		platforms.add(new Rectangle(480, 300, 160, 1));
		platforms.add(new Rectangle(0, 450, 160, 1));
		platforms.add(new Rectangle(320, 450, 160, 1));
		platforms.add(new Rectangle(640, 450, 160, 1));
	}

	public void platformCollisions() {
		// Collisions
		for (Rectangle platform : platforms) {
			if (platform.overlaps(new Rectangle(player.x, player.y, 50, 100)) && player.yMove < 0
					&& player.y > platform.y - 25) {
				player.yMove = 0;
				player.y = platform.y;
				player.onGround = true;
			}
		}
	}
	
	public void doProjectileStuff() {
		// Attacking:
		if (Gdx.input.isTouched()) {
			Vector3 Mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
			//degrees
			float angle = MathUtils.atan2(Mouse.y - (player.y + 50), Mouse.x - (player.x + 25));
			projectiles.add(new BasicProjectile(player.x + 25, player.y + 50, angle));
			projectiles.add(new PotionProjectile(player.x + 25, player.y + 50, angle));
			projectiles.add(new BurstProjectile(player.x + 25, player.y + 50, angle));
		}
		
		//Projectile work
		for (Projectile p : projectiles) {
			// Projectiles moving:
			p.move();
			//Mark projectiles for removal
			if (p.x < 0 || p.x > ARENA_WIDTH || p.y > ARENA_HEIGHT || p.y < 0 || p.checkAge()) {
				p.destroy = true;
			}
		}
		
		//The removal code
		Iterator<Projectile> iter = projectiles.iterator();
		while (iter.hasNext()) {
			Projectile p = iter.next();
			if (p.destroy) {
				iter.remove();
			}
		}
	}
}
