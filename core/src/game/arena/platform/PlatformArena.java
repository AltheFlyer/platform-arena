package game.arena.platform;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * A fun project to learn some more code stuffs.
 * <br>
 * A platformer-arena style game where you try to not
 * die from enemies while trying to get collectibles.
 * 
 * @author Allen Liu
 *
 */
public class PlatformArena extends ApplicationAdapter {

	Texture img;
	Character player;
	OrthographicCamera camera;
	ShapeRenderer render;
	Array<Rectangle> platforms;
	Array<Projectile> projectiles;
	Array<Enemy> enemies;
	Array<Star> stars;
	final int ARENA_WIDTH = 800;
	final int ARENA_HEIGHT = 600;
	float frame;
	Vector3 Mouse;
	float meleeCooldown;
	final float MELEE_TIMER = 1;
	Rectangle damage;
	boolean isLeft;

	@Override
	public void create() {
		player = new Knight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, ARENA_WIDTH, ARENA_HEIGHT);

		render = new ShapeRenderer();
		render.setAutoShapeType(true);

		platforms = new Array<Rectangle>();
		// Default level
		initializePlatforms();

		projectiles = new Array<Projectile>();
		enemies = new Array<Enemy>();
		enemies.add(new DummyEnemy(400, 400));
		enemies.add(new SeekerEnemy(300, 300));
		player.primaryCooldown = 0;
		frame = 0;
		meleeCooldown = 0;
		damage = new Rectangle(0, 0, 0, 0);
		isLeft = false;
		
		//Collectibles
		stars = new Array<Star>();
		initializeStars();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		render.setProjectionMatrix(camera.combined);

		draw();
		
		frame = Gdx.graphics.getDeltaTime();
		if (frame > 0.3f) frame = 0.3f;
		Mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
		
		move();

		platformCollisions();

		enemyStuff();

		doProjectileStuff();
		
		//Collectibles
		for (Star s: stars) {
			s.collect(player.hitbox);
			s.spawn(frame);
		}
		
		//Melee attack?
		if (Gdx.input.isButtonPressed(Buttons.RIGHT) && meleeCooldown <= 0) {
			if (Mouse.x < player.hitbox.x) {
				damage = new Rectangle(player.hitbox.x - 125, player.hitbox.y, 150, 150);
				for (Enemy e: enemies) {
					if (damage.overlaps(e.hitbox)) {
						e.damage(5);
					}
				}
				isLeft = true;
			} else {
				damage = new Rectangle(player.hitbox.x + 25, player.hitbox.y, 150, 150);
				for (Enemy e: enemies) {
					if (damage.overlaps(e.hitbox)) {
						e.damage(5);
					}
				}
				isLeft = false;
			}
			meleeCooldown = MELEE_TIMER;
		}
		if (meleeCooldown > 0) {
			meleeCooldown -= frame;
		}
		if (meleeCooldown < 0) {
			meleeCooldown = 0;
		}
		
		
		// DEBUG//
		// System.out.println(player.y);
	}

	@Override
	public void dispose() {
		// batch.dispose();
		render.dispose();
		platforms.clear();
		enemies.clear();
		stars.clear();
	}

	public void draw() {
		render.begin();
		// Draw player
		render.set(ShapeType.Filled);
		render.setColor(Color.GREEN);
		render.box(player.hitbox.x, player.hitbox.y, 0, player.hitbox.width, player.hitbox.height, 0);
	
		// Draw platforms
		render.set(ShapeType.Filled);
		render.setColor(Color.BROWN);
		for (Rectangle platform : platforms) {
			render.box(platform.x, platform.y, 0, platform.width, platform.height, 0);
		}
	
		// Draw projectiles
		render.setColor(Color.BLACK);
		for (Projectile p : projectiles) {
			render.circle(p.hitbox.x + p.hitbox.width / 2, p.hitbox.y + p.hitbox.width / 2, p.hitbox.width);
		}
	
		// Draw enemies
		render.setColor(Color.RED);
		for (Enemy e : enemies) {
			render.box(e.hitbox.x, e.hitbox.y, 0, e.hitbox.width, e.hitbox.height, 0);
		}
		
		//Draw stars
		render.setColor(Color.GOLD);
		for (Star s: stars) {
			if (!s.collected) {
				render.box(s.hitbox.x, s.hitbox.y, 0, s.hitbox.width, s.hitbox.height, 0);
			}
		}
		
		//Experimental melee
		render.setColor(Color.GRAY);
		if (meleeCooldown > 0.8f && isLeft) {
			render.arc(damage.x + 150, damage.y, 150, 90, 90);
		} else if (meleeCooldown > 0.8f && !isLeft) {
			render.arc(damage.x, damage.y, 150, 90, -90, 10);
		}
		
		render.end();
	}

	public void move() {
		player.yLast = player.hitbox.y;
		// Movement
		// Some of this will be moved to the character class
		// Gravity
		float deltaY = 0;
		// if (!player.onGround)
		deltaY -= 120 * frame;
		// If the up key is help while jumping
		// Jump a bit higher
		// if (!player.onGround && Gdx.input.isKeyPressed(Keys.W) && player.yMove > 0) {
		// deltaY += player.yMove * frame * 2.8;
		// }
		// Still not sure how to fine tune this one
		player.yMove += deltaY;
		if (player.yMove < -30)
			player.yMove = -30;

		// Jumping
		if (player.onGround && Gdx.input.isKeyJustPressed(Keys.W)) {
			player.yMove = 30;
			player.onGround = false;
		}
		player.hitbox.y += player.yMove;

		// Horizontal movement
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.hitbox.x -= 400 * frame;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			player.hitbox.x += 400 * frame;
		}
		
		//Drop through platforms (experimental)
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			player.hitbox.y -= 1;
			player.yLast = player.hitbox.y;
		}
		
		// Keep player in bounds
		if (player.hitbox.y <= 0) {
			player.hitbox.y = 0;
			player.yMove = 0;
			player.onGround = true;
		}
		if (player.hitbox.x < 0) {
			player.hitbox.x = 0;
		} else if (player.hitbox.x > 800 - 50) {
			player.hitbox.x = 800 - 50;
		}

		if (player.yLast != player.hitbox.y) {
			player.onGround = false;
		}
	}

	public void platformCollisions() {
		// Collisions
		for (Rectangle platform : platforms) {
			if (platform.overlaps(player.hitbox) && player.yMove < 0
					&& player.yLast >= platform.y && player.hitbox.y < platform.y) {
				player.yMove = 0;
				player.hitbox.y = platform.y;
				player.onGround = true;
			}
		}
	}

	public void enemyStuff() {
		// Enemy stuff
		for (Enemy e : enemies) {
			e.move(player.hitbox.x, player.hitbox.y, frame);
			for (Projectile p : projectiles) {
				if (e.hitbox.contains(new Vector2(p.hitbox.x, p.hitbox.y))) {
					p.destroy = true;
					e.damage(p.damage);
				}
			}
		}
		
		//DEBUG//
		System.out.println(enemies.size);

		// Remove dead enemies
		Iterator<Enemy> e = enemies.iterator();
		while (e.hasNext()) {
			Enemy en = e.next();
			if (en.destroy) {
				e.remove();
				// Make respawning enemies for now
				if (MathUtils.random(0, 1) == 1 || enemies.size < 30)
					enemies.add(new SeekerEnemy(800 * MathUtils.random(0, 1), 600 * MathUtils.random(0, 1)));
				if (MathUtils.random(0, 1) == 1)
					enemies.add(new SeekerEnemy(800 * MathUtils.random(0, 1), 600 * MathUtils.random(0, 1)));
			}
		}
	}
	
	public void doProjectileStuff() {
		// Attacking:
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && player.primaryCooldown == 0) {
			// degrees
			float angle = MathUtils.atan2(Mouse.y - (player.hitbox.y + 50), Mouse.x - (player.hitbox.x + 25));
			projectiles.add(new BasicProjectile(player.hitbox.x + 25, player.hitbox.y + 50, angle));
			projectiles.add(new PotionProjectile(player.hitbox.x + 25, player.hitbox.y + 50, angle));
			projectiles.add(new BurstProjectile(player.hitbox.x + 25, player.hitbox.y + 50, angle));
			player.primaryCooldown = player.PRIMARY_COOLDOWN;
		}
		if (player.primaryCooldown >= 0) {
			player.primaryCooldown -= frame;
		} else {
			player.primaryCooldown = 0;
		}
	
		// Projectile work
		for (Projectile p : projectiles) {
			// Projectiles moving:
			p.move(frame);
			// Mark projectiles for removal
			if (p.hitbox.x < 0 || p.hitbox.x > ARENA_WIDTH || p.hitbox.y > ARENA_HEIGHT || p.hitbox.y < 0 || p.checkAge()) {
				p.destroy = true;
			}
		}
	
		// The removal code
		Iterator<Projectile> iter = projectiles.iterator();
		while (iter.hasNext()) {
			Projectile p = iter.next();
			if (p.destroy) {
				iter.remove();
			}
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

	public void initializeStars() {
		//Top platform level
		for (int i = 1; i < 16; i ++) {
			stars.add(new Star(i * 50, 465));
		}
		
		//Ring of stars
		for (int i = 0; i < 12; i ++) {
			stars.add(new Star(
					400 + 200 * MathUtils.sin(i * 30 * MathUtils.degreesToRadians),
					300 + 200 * MathUtils.cos(i * 30 * MathUtils.degreesToRadians)
					));
		}
	}
}
