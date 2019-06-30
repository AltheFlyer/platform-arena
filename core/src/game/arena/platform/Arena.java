package game.arena.platform;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @deprecated
 */
public class Arena extends Game {
	
	SpriteBatch batch;
	ShapeRenderer render;
	//Open Sans 32px
	BitmapFont font;
	//Open Sans 16px
	BitmapFont desc;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		render = new ShapeRenderer();
		render.setAutoShapeType(true);
		font = new BitmapFont(Gdx.files.internal("font_test.fnt"));
		desc = new BitmapFont(Gdx.files.internal("desc_20px.fnt"));
		
		this.setScreen(new MenuScreen(this));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		render.dispose();
		font.dispose();
	}
}
