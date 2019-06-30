package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class LevelSelectScreen implements Screen {

	Arena game;
	OrthographicCamera camera;
	Vector3 Mouse;
	Texture button;
	Texture glowButton;
	BitmapFont desc;
	Array<LevelData> levels;

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
		levels = new Array<LevelData>();
		levels.add(new LevelData("Test Level", 20, 450, 75, 75, "TestLevel"));
		levels.add(new LevelData("Orbital Level", 115, 450, 75, 75, "OrbitalLevel"));
		levels.add(new LevelData("Big Level", 210, 450, 75, 75, "BigLevel"));
		levels.add(new LevelData("Chaos Level", 305, 450, 75, 75, "ChaosTestLevel"));
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

		for (LevelData l : levels) {
			l.check(Mouse.x, Mouse.y, game);
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
