package game.arena.platform;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import game.arena.platform.Enemy.AttackType;
import game.arena.platform.Projectile.CollisionType;

/**
 * A test level
 * @deprecated
 */
public class PlatformArena implements Screen {

	// Used for screen stuff
	final Arena game;

	Texture platformSprite;
	Texture starSprite;
	Character player;

	// Used for rendering
	OrthographicCamera camera;
	ShapeRenderer render;

	Array<Rectangle> platforms;
	Array<Projectile> projectiles;
	Array<Projectile> enemyProjectiles;
	Array<Enemy> enemies;
	Array<Star> stars;
	int arenaWidth;
	int arenaHeight;

	Vector3 Mouse;

	// Melee based, to be moved elsewhere
	float meleeCooldown;
	final float MELEE_TIMER = 1;
	Rectangle damage;
	boolean isLeft;

	// Time to TLAP - for jump physics
	// Max/min jump height will be slightly over this
	float maxJumpHeight;
	float minJumpHeight;
	float maxJumpTime;
	float minJumpVelocity;
	float jumpVelocity;
	float gravity;

	// ***************DEBUG***************//
	boolean sprites;
	boolean paused;

	// Enemy Waves
	Wave wave;
	Wave[] waves;
	int maxWaves;
	int currentWave;
	
	// Global timer
	float time;
	// Keeps delta time
	float frame;

	// Score
	int score;
	int displayHeight;

	public PlatformArena(final Arena game) {
		this.game = game;
		sprites = true;
		paused = false;

		// Arena settings
		arenaWidth = 800;
		arenaHeight = 600;

		// Initialize player
		player = new Knight();

		// Initialize camera, level view
		camera = new OrthographicCamera();
		camera.setToOrtho(false, arenaWidth, arenaHeight);

		// Initialize renderer
		// Set to shape until sprites happen
		render = new ShapeRenderer();
		render.setAutoShapeType(true);

		// Initialize images
		platformSprite = new Texture("platform.png");
		starSprite = new Texture("star15x15.png");

		// Initialize Lists
		platforms = new Array<Rectangle>();
		stars = new Array<Star>();
		projectiles = new Array<Projectile>();
		enemies = new Array<Enemy>();
		enemyProjectiles = new Array<Projectile>();

		// Enemy Waves
		time = 0;
		wave = new Wave();
		maxWaves = 1;
		currentWave = 0;
		waves = new Wave[maxWaves];

		// Initialize time count
		frame = 0;

		// Score
		score = 0;
		displayHeight = 100;

		// Used for melee (to be moved)
		meleeCooldown = 0;
		damage = new Rectangle(0, 0, 0, 0);
		isLeft = false;

		// The big 5 (Calculating gravity and jump forces with preset values)
		maxJumpHeight = 200;
		minJumpHeight = 25;
		maxJumpTime = 0.35f;
		gravity = (2 * maxJumpHeight) / (maxJumpTime * maxJumpTime);
		jumpVelocity = gravity * maxJumpTime;
		minJumpVelocity = (float) Math.sqrt(2 * gravity * minJumpHeight);
		// ***************DEBUG***************//
		// Values of gravity and jumpVelocity are:
		// 3265.3062, 1142.8572
		// System.out.println(gravity + " " + jumpVelocity);
	}

	// Because i need this separate to prevent crashes
	public void init() {
		// Default level
		initializePlatforms();
		initializeStars();
		waves = new Wave[maxWaves];
		for (int i = 0; i < maxWaves; ++i) {
			waves[i] = new Wave();
		}
		initializeWaves();
		wave = waves[0];
	}

	@Override
	public void render(float delta) {
		// *************** DEBUG ***************//
		// System.out.println(player.hitbox.x + " " + player.hitbox.y);
		// Update Camera (does nothing if in 800x600 level or smaller)
		camera.position.x = (int) player.hitbox.x;
		camera.position.y = (int) player.hitbox.y - (displayHeight / 2);
		if (camera.position.x < 400) {
			camera.position.x = 400;
		} else if (camera.position.x > arenaWidth - 400) {
			camera.position.x = arenaWidth - 400;
		}
		if (camera.position.y < 300 - displayHeight) {
			camera.position.y = 300 - displayHeight;
		} else if (camera.position.y > arenaHeight - 300) {
			camera.position.y = arenaHeight - 300;
		}

		// Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Prepare camera for drawing
		camera.update();
		render.setProjectionMatrix(camera.combined);
		game.batch.setProjectionMatrix(camera.combined);

		// Calculating frame
		frame = Gdx.graphics.getDeltaTime();
		// Prevent time skips
		if (frame > 0.3f)
			frame = 0.3f;
		// Save mouse location
		Mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

		// Increment global time
		time += frame;

		draw();

		if (!paused) {
			
			//Player movements
			move();

			playerPlatformCollisions();
			
			//Enemy movements and attacks
			enemyStuff();
			
			//Player attacks
			doPlayerAttacks();

			//Projectile things
			doProjectileStuff(projectiles);

			doProjectileStuff(enemyProjectiles);

			playerProjectileCollisions();

			// Collectibles
			for (Star s : stars) {
				if (s.collect(player.hitbox))
					++score;
				s.spawn(frame);
			}

			// Exit level
			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) || player.health <= 0) {
				game.setScreen(new MenuScreen(game));
			}
		}
	}

	@Override
	public void dispose() {
		game.batch.dispose();
		render.dispose();
		starSprite.dispose();
		platformSprite.dispose();
		platforms.clear();
		enemies.clear();
		stars.clear();
	}

	public void draw() {
		// ***************DEBUG***************//
		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			sprites = !sprites;
		}
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			paused = !paused;
		}

		render.begin();
		render.set(ShapeType.Filled);
		// Blank screen when game is paused
		// Should be changed to transparent
		if (paused) {
			render.setColor(1, 1, 1, 0.2f);
			render.box(0, 0, 0, arenaWidth, arenaHeight, 0);
		}

		// Draw player
		if (!sprites && !paused) {
			render.setColor(Color.GREEN);
			render.box(player.hitbox.x, player.hitbox.y, 0, player.hitbox.width, player.hitbox.height, 0);

			// Draw platforms
			render.set(ShapeType.Filled);
			render.setColor(Color.BROWN);
			for (Rectangle platform : platforms) {
				render.box(platform.x, platform.y, 0, platform.width, platform.height, 0);
			}

			// Draw stars
			render.setColor(Color.GOLD);
			for (Star s : stars) {
				if (!s.collected) {
					render.box(s.hitbox.x, s.hitbox.y, 0, s.hitbox.width, s.hitbox.height, 0);
				}
			}

			// Draw enemies
			render.setColor(Color.RED);
			for (Enemy e : enemies) {
				render.box(e.hitbox.x, e.hitbox.y, 0, e.hitbox.width, e.hitbox.height, 0);
			}
		}

		// Draw projectiles
		render.setColor(Color.BLACK);
		for (Projectile p : projectiles) {
			render.circle(p.hitbox.x + p.hitbox.width / 2, p.hitbox.y + p.hitbox.width / 2, p.hitbox.width);
		}

		for (Projectile p : enemyProjectiles) {
			render.circle(p.hitbox.x + p.hitbox.width / 2, p.hitbox.y + p.hitbox.width / 2, p.hitbox.width);
		}

		// Experimental melee
		render.setColor(Color.GRAY);
		if (meleeCooldown > 0.8f && isLeft) {
			render.arc(damage.x + 150, damage.y, 150, 90, 90);
		} else if (meleeCooldown > 0.8f && !isLeft) {
			render.arc(damage.x, damage.y, 150, 90, -90, 10);
		}

		// Draw display with info
		// Draw health bar
		// Get fixed value:
		// In Vector3, Y is down...
		Vector3 p = camera.unproject(new Vector3(20, 560, 0));
		Vector3 b = camera.unproject(new Vector3(0, 600, 0));

		render.setColor(Color.GRAY);
		render.box(b.x, b.y, 0, 800, displayHeight, 0);

		render.setColor(Color.RED);
		render.box(p.x, p.y, 0, 200 * (player.health / player.MAX_HEALTH), 30, 0);
		render.setColor(Color.BLACK);
		render.set(ShapeType.Line);
		render.box(p.x, p.y, 0, 200, 30, 0);

		render.end();

		game.batch.begin();
		if (sprites && !paused) {

			// Platform sprites
			for (Rectangle platform : platforms) {
				game.batch.draw(platformSprite, platform.x, platform.y - 10);
			}

			// Enemy sprites
			for (Enemy e : enemies) {
				game.batch.draw(e.getState(), e.hitbox.x, e.hitbox.y);
			}

			// Star sprites
			for (Star s : stars) {
				if (!s.collected) {
					game.batch.draw(starSprite, s.hitbox.x, s.hitbox.y);
				}
			}

			// Player sprite
			game.batch.draw(player.getCharacterState(), player.hitbox.x, player.hitbox.y);

		}

		// Text for menu:
		// Health
		game.desc.draw(game.batch, "Health", p.x + 5, p.y + 50);
		game.desc.draw(game.batch, String.format("%.0f / %.0f", player.health, player.MAX_HEALTH), p.x + 5, p.y + 22);

		// Score
		game.desc.draw(game.batch, "Score", p.x + 250, p.y + 50);
		game.desc.draw(game.batch, String.format("%d", score), p.x + 250, p.y + 22);

		// Time
		game.desc.draw(game.batch, "Time", p.x + 400, p.y + 50);
		game.desc.draw(game.batch, String.format("%02d:%02d:%2.0f", (int) time / 60, (int) time % 60, 100 * (time % 1)),
				p.x + 400, p.y + 22);

		game.batch.end();
	}

	public void move() {
		// TODO Move this
		// Player invincibility, to be moved
		if (player.invincible > 0) {
			player.invincible -= frame;
		}
		if (player.invincible < 0) {
			player.invincible = 0;
		}

		// Save last player y value
		player.xLast = player.hitbox.x;
		player.yLast = player.hitbox.y;

		// ***************Movement***************//
		// All values are in pixels/second
		// Anything that is added is based on how many seconds have passed,
		// This is based on frame variable (found in render())
		// Some of this will be moved to the character class

		// Gravity
		player.yMove -= gravity * frame;

		// Jumping
		if (player.onGround && (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP))) {
			player.yMove = jumpVelocity;
			player.onGround = false;
		}
		// When jump is released, stop moving upwards (as quickly)
		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.UP)) {
			// Velocity isn't added by this, only removed
			if (player.yMove > minJumpVelocity) {
				player.yMove = minJumpVelocity;
			}
		}

		// Move the player
		player.hitbox.y += player.yMove * frame;

		// Horizontal movement
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.hitbox.x -= 400 * frame;
		} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.hitbox.x += 400 * frame;
		}

		// Drop through platforms (experimental)
		if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			// This works with platforms with a thickness of 1 or 0 pixels
			player.hitbox.y -= 1;
			// Prevent the platform from 'catching' the player dropping
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
		} else if (player.hitbox.x > arenaWidth - 50) {
			player.hitbox.x = arenaWidth - 50;
		}

		// Check if player is on the ground (messy)
		if (player.yLast != player.hitbox.y) {
			player.onGround = false;
		} else {
			player.onGround = true;
		}

	}

	public void playerPlatformCollisions() {
		for (Rectangle platform : platforms) {
			// Check if:
			// Player is above platform, but would drop below it
			// Player looks like they could actually stand on the platform
			if (player.hitbox.x + player.hitbox.width > platform.x && player.hitbox.x < platform.x + platform.width
					&& player.yLast >= platform.y && player.hitbox.y < platform.y) {
				player.yMove = 0;
				player.hitbox.y = platform.y;
				// This is needed due to how onGround checks are made
				// Should not be necessary but i dunno
				player.onGround = true;
			}
		}
		player.xCentre = player.hitbox.x + (player.hitbox.width / 2);
		player.yCentre = player.hitbox.y + (player.hitbox.height / 2);
	}

	public void enemyStuff() {
		// Enemy stuff
		for (Enemy e : enemies) {
			// Save this
			e.yLast = e.hitbox.y;

			// Gravity
			if (!e.flying) {
				e.yMove -= gravity * frame;
			}

			e.move(player.xCentre, player.yCentre, frame);

			// Invinc
			e.tick(frame);

			// Keep in bounds
			if (e.hitbox.y < 0 && !e.flying) {
				e.hitbox.y = 0;
				e.onGround = true;
			}

			// Platform Collisions
			for (Rectangle platform : platforms) {

				if (!e.flying && e.hitbox.x + e.hitbox.width > platform.x && e.hitbox.x < platform.x + platform.width
						&& e.yLast >= platform.y && e.hitbox.y < platform.y) {
					e.yMove = 0;
					e.hitbox.y = platform.y;
					e.onGround = true;
				}
			}

			e.xCentre = e.hitbox.x + (e.hitbox.width / 2);
			e.yCentre = e.hitbox.y + (e.hitbox.height / 2);

			// Enemy-projectile collision
			for (Projectile p : projectiles) {
				// Projectiles are now single hit
				if (e.hitbox.overlaps(p.hitbox) && p.collisions < p.maxCollisions) {
					// Mark projectiles as destroyed, instead of removing them now
					// All projectiles are AOE for the time being
					p.collide(CollisionType.enemy);
					e.damage(p.damage);
				}
			}

			// Player Collision
			if (e.hasCollided(player.hitbox)) {
				player.damage(e.collisionDamage);
			}

			// Attacking
			if (e.canAttack(player.xCentre, player.yCentre, frame)) {

				// Single projectile attacks
				if (e.type == AttackType.single) {
					enemyProjectiles.add(e.attackSingle(player.xCentre, player.yCentre, frame));

					// Multi projectile attacks
				} else if (e.type == AttackType.multi) {
					enemyProjectiles.addAll(e.attackMulti(player.xCentre, player.yCentre, frame));
				}
			}
		}

		// ***************DEBUG***************//
		// System.out.println(enemies.size);

		// Remove dead enemies
		Iterator<Enemy> e = enemies.iterator();
		while (e.hasNext()) {
			Enemy en = e.next();
			if (en.destroy) {
				// Increment score:
				// TODO (Change to be based on enemy type)
				score += en.score;
				if (en.hasDeathSummon) {
					enemies.add(en.deathSummon());
				}

				e.remove();

				// Make respawning enemies for now
				/*
				 * if (MathUtils.random(0, 1) == 1 || enemies.size < 7) enemies.add(new
				 * SeekerEnemy(800 * MathUtils.random(0, 1), 600 * MathUtils.random(0, 1))); if
				 * (MathUtils.random(0, 1) == 1) enemies.add(new SeekerEnemy(800 *
				 * MathUtils.random(0, 1), 600 * MathUtils.random(0, 1)));
				 */
			}
		}

		// Spawn enemies
		wave.time += frame;
		//System.out.println(wave.time);
		for (Enemy en : wave.enemies.keys()) {
			if (wave.enemies.get(en) <= wave.time) {
				enemies.add(en);
				wave.enemies.remove(en);
			}
		}
		if (((enemies.size == 0 && wave.enemies.size == 0) || wave.time > wave.maxTime) && currentWave < maxWaves - 1 && wave.time > wave.minTime) {
			++currentWave;
			wave = waves[currentWave];
		}
		if (wave.enemies.size == 0 && enemies.size == 0 && currentWave == maxWaves - 1) System.out.println("Level Complete");
	}

	public void doPlayerAttacks() {
		// Shooting:
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && player.primaryCooldown == 0) {
			// Generate angle from horizontal to player and player to cursor
			// Angles in radians
			float angle = MathUtils.atan2(Mouse.y - (player.yCentre), Mouse.x - (player.xCentre));
			projectiles.add(new BasicProjectile(player.xCentre, player.yCentre, angle));
			projectiles.add(new PotionProjectile(player.xCentre, player.yCentre, angle));
			projectiles.add(new BurstProjectile(player.xCentre, player.yCentre, angle));
			// Set cooldown
			player.primaryCooldown = player.PRIMARY_COOLDOWN;
		}
		// Tick cooldown
		if (player.primaryCooldown >= 0) {
			player.primaryCooldown -= frame;
		} else {
			// Prevents underflow?
			player.primaryCooldown = 0;
		}

		// Melee attack?
		if (Gdx.input.isButtonPressed(Buttons.RIGHT) && meleeCooldown <= 0) {
			// Check player direction
			if (Mouse.x < player.hitbox.x) {
				// Creates a rectangle that immediately deals damage
				damage = new Rectangle(player.hitbox.x - 125, player.hitbox.y, 150, 150);
				for (Enemy e : enemies) {
					if (damage.overlaps(e.hitbox)) {
						e.damage(5);
					}
				}
				isLeft = true;
			} else {
				// Same as above
				damage = new Rectangle(player.xCentre, player.hitbox.y, 150, 150);
				for (Enemy e : enemies) {
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
	}

	public void doProjectileStuff(Array<Projectile> proj) {

		// Projectile work
		for (Projectile p : proj) {
			// Projectiles moving:
			p.move(frame);
			// Mark projectiles for removal
			if (p.hitbox.x < 0 || p.hitbox.x > arenaWidth || p.hitbox.y > arenaHeight || p.hitbox.y < 0) {
				p.collide(CollisionType.wall);
			}
			if (p.checkAge()) {
				p.destroy = true;
			}
		}

		// The removal code
		Iterator<Projectile> iter = proj.iterator();
		while (iter.hasNext()) {
			Projectile p = iter.next();
			if (p.destroy) {
				if (p.hasDeathCreate) {
					proj.add(p.deathCreate());
				}
				iter.remove();
			}
		}
	}

	public void playerProjectileCollisions() {
		for (Projectile p : enemyProjectiles) {
			if (player.hitbox.overlaps(p.hitbox)) {
				p.collide(CollisionType.player);
				player.damage(p.damage);
			}
		}
	}

	public void initializePlatforms() {
		// This creates a row of 3 evenly spaced platforms
		platforms.add(new Rectangle(0, 150, 160, 1));
		platforms.add(new Rectangle(320, 150, 160, 1));
		platforms.add(new Rectangle(640, 150, 160, 1));
		// Then 2 spaced above them
		platforms.add(new Rectangle(160, 300, 160, 1));
		platforms.add(new Rectangle(480, 300, 160, 1));
		// The another 3, above that
		platforms.add(new Rectangle(0, 450, 160, 1));
		platforms.add(new Rectangle(320, 450, 160, 1));
		platforms.add(new Rectangle(640, 450, 160, 1));
	}

	public void initializeStars() {
		// Top platform level
		for (int i = 1; i < 16; i++) {
			stars.add(new Star(i * 50, 465));
		}

		// Ring of stars (for fun)
		for (int i = 0; i < 12; i++) {
			stars.add(new Star(400 + 200 * MathUtils.sin(i * 30 * MathUtils.degreesToRadians),
					300 + 200 * MathUtils.cos(i * 30 * MathUtils.degreesToRadians)));
		}
	}

	public void initializeWaves() {
		
		// Enemy of the day/week
		waves[0].put(new GroundEnemy(0, 0), 1f);

		// From 4 corners
		waves[0].put(new SeekerEnemy(0, 0), 10f);
		waves[0].put(new SeekerEnemy(0, arenaHeight), 10f);
		waves[0].put(new SeekerEnemy(arenaWidth, arenaHeight), 10f);
		waves[0].put(new SeekerEnemy(arenaWidth, 0), 10f);

		// From both sides
		waves[0].put(new SeekerEnemy(0, arenaHeight / 2 + 50), 16f);
		waves[0].put(new SeekerEnemy(0, arenaHeight / 2), 16f);
		waves[0].put(new SeekerEnemy(0, arenaHeight / 2 - 50), 16f);

		waves[0].put(new SeekerEnemy(arenaWidth, arenaHeight / 2 + 50), 16f);
		waves[0].put(new SeekerEnemy(arenaWidth, arenaHeight / 2), 16f);
		waves[0].put(new SeekerEnemy(arenaWidth, arenaHeight / 2 - 50), 16f);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
