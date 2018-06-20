package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen{
	
	Arena game;
	OrthographicCamera camera;
	Texture button;
	Texture glowButton;
	Vector3 Mouse;
	
	//Debug
	float debugX, debugY;
	
	//Buttons
	//Start Level
	Rectangle levelStart, instructions;
	
	public MenuScreen(final Arena game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		button = new Texture("normal_box.png");
		glowButton = new Texture("glow_box.png");
		
		//Buttons are 210px x 75px
		levelStart = new Rectangle(295, 263, 210, 75);
		instructions = new Rectangle(295, 178, 210, 75);
		
		//Debug
		debugX = 100;
		debugY = 100;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		//Set mouse
		Mouse = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
		
		game.batch.begin();
		
		//Start level button (to be changed)
		if (levelStart.contains(Mouse.x, Mouse.y)) {
			//Draw glowing button
			game.batch.draw(glowButton, levelStart.x, levelStart.y);
			//Set to level if clicking
			if (Gdx.input.isTouched()) {
				game.setScreen(new TestLevel(game));
				dispose();
			}
		} else {
			//Normal button
			game.batch.draw(button, levelStart.x, levelStart.y);
		}
		game.font.draw(game.batch, "Test Level", 327, 312);

		//Instructions button
		if (instructions.contains(Mouse.x, Mouse.y)) {
			//Draw glowing button
			game.batch.draw(glowButton, instructions.x, instructions.y);
			//Set to level if clicking
			if (Gdx.input.isTouched()) {
				//game.setScreen(new TestLevel(game));
				//dispose();
			}
		} else {
			//Normal button
			game.batch.draw(button, instructions.x, instructions.y);
		}
		game.font.draw(game.batch, "Instructions", 312, 228);
		
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
		button.dispose();
		glowButton.dispose();
		
	}

}
