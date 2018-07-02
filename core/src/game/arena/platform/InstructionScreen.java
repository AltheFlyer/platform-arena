package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class InstructionScreen implements Screen {
	
	Arena game;
	OrthographicCamera camera;
	Vector3 Mouse;
	
	//Debug
	float debugX;
	float debugY;
	
	public InstructionScreen(final Arena game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		debugX = 400;
		debugY = 300;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//Use this to adjust text placements
		/*
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
		*/
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "Controls", 20, 590);
		game.desc.draw(game.batch, 
				"Move with WASD or Arrow Keys.\n"
				+ "Jump with W or UP arrow key.\n"
				+ "Press S or DOWN to drop through platforms.\n"
				+ "Shoot with Left Mouse Button.\n"
				+ "Melee with Right Mouse Button.\n"
				, 22, 531);
		game.font.draw(game.batch, "Debug Fun", 20, 320);
		game.desc.draw(game.batch, 
				"Press Q to change between sprites and shape drawing.\n"
				+ "Press E to pause (This was poorly made).\n"
				+ "Press Esc to go back to menu (Yes that works here)."
				, 22, 261);
		game.batch.end();
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
