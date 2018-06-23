package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelSelectScreen implements Screen {

	Arena game;
	OrthographicCamera camera;
	Vector3 Mouse;
	Texture button;
	Texture glowButton;
	BitmapFont desc;
	Rectangle testLevelSelect, orbitalLevelSelect, bigLevelSelect;

	// Debug
	int debugX = 20;
	int debugY = 550;

	public LevelSelectScreen(Arena game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		button = new Texture("level_box.png");
		glowButton = new Texture("glow_level_box.png");
		desc = new BitmapFont(Gdx.files.internal("desc_20px.fnt"));

		// Level Buttons
		testLevelSelect = new Rectangle(20, 450, 75, 75);
		orbitalLevelSelect = new Rectangle(115, 450, 75, 75);
		bigLevelSelect = new Rectangle(210, 450, 75, 75);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		// Set mouse
		Mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

		game.batch.begin();
		// Draw Title:
		game.font.draw(game.batch, "Level Select", 20, 587);

		// Test Level
		if (testLevelSelect.contains(Mouse.x, Mouse.y)) {
			game.batch.draw(glowButton, testLevelSelect.x, testLevelSelect.y, testLevelSelect.width,
					testLevelSelect.height);
			if (Gdx.input.isTouched()) {
				game.setScreen(new TestLevel(game));
				dispose();
			}
			// Descriptions
			game.font.draw(game.batch, "Test Level", 20, 100);
			desc.draw(game.batch, "The default testing level, showing off whatever new content or features are added.",
					20, 60);
		} else {
			game.batch.draw(button, testLevelSelect.x, testLevelSelect.y, testLevelSelect.width,
					testLevelSelect.height);
		}

		// Orbital Level
		if (orbitalLevelSelect.contains(Mouse.x, Mouse.y)) {
			game.batch.draw(glowButton, orbitalLevelSelect.x, orbitalLevelSelect.y, orbitalLevelSelect.width,
					orbitalLevelSelect.height);
			if (Gdx.input.isTouched()) {
				game.setScreen(new OrbitalLevel(game));
				dispose();
			}
			// Descriptions
			game.font.draw(game.batch, "Orbital Level", 20, 100);
			desc.draw(game.batch, "A Level to showcase the orbital enemy (with varying distances from player).",
					20, 60);
		} else {
			game.batch.draw(button, orbitalLevelSelect.x, orbitalLevelSelect.y, orbitalLevelSelect.width,
					orbitalLevelSelect.height);
		}
		
		if (bigLevelSelect.contains(Mouse.x, Mouse.y)) {
			game.batch.draw(glowButton, bigLevelSelect.x, bigLevelSelect.y, bigLevelSelect.width, bigLevelSelect.height);
			if (Gdx.input.isTouched()) {
				game.setScreen(new BigLevel(game));
				dispose();
			}
			//Descriptions
			game.font.draw(game.batch, "Big Level", 20, 100);
			desc.draw(game.batch, "A large level (1600px x 1200px) to show orthographic camera movement and new Ground Enemy movement.", 20, 60, 780, 780, true);
		} else {
			game.batch.draw(button, bigLevelSelect.x, bigLevelSelect.y, bigLevelSelect.width, bigLevelSelect.height);
		}
		
		game.batch.end();

		// Use this to adjust text placements

		if (Gdx.input.isKeyPressed(Keys.W)) {
			++debugY;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			--debugX;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			--debugY;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			++debugX;
		}
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			System.out.println(debugX + " " + debugY);
		}

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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
