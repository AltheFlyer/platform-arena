package game.arena.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class LevelData {

	String name;
	static BitmapFont descFont = new BitmapFont(Gdx.files.internal("desc_20px.fnt"));
	Rectangle button;
	Texture buttonSprite;
	Texture glowSprite;
	String level;

	public LevelData(String name1, float x, float y, float w, float h, String level1) {
		name = name1;
		button = new Rectangle(x, y, w, h);
		buttonSprite = new Texture("level_box.png");
		glowSprite = new Texture("glow_level_box.png");
		level = level1;
	}

	public void check(float x, float y, Arena game) {
		if (button.contains(x, y)) {
			game.batch.draw(glowSprite, button.x, button.y, button.width, button.height);
			if (Gdx.input.isTouched()) {
				// This is the best way I can think of that doesn't preload every level
				game.setScreen(nameToLevel(game, level));
			}
			// Descriptions
			game.font.draw(game.batch, name, 20, 100);
			descFont.draw(game.batch, nameToDescription(level), 20, 60, 780, 780, true);
		} else {
			game.batch.draw(buttonSprite, button.x, button.y, button.width, button.height);
		}
	}

	// I don't know about any better ways as of now
	public PlatformArena nameToLevel(Arena game, String level) {
		if (level.equals("TestLevel")) {
			return new TestLevel(game);
		} else if (level.equals("OrbitalLevel")) {
			return new OrbitalLevel(game);
		} else if (level.equals("BigLevel")) {
			return new BigLevel(game);
		} else {
			return new TestLevel(game);
		}
	}

	public String nameToDescription(String level) {
		if (level.equals("TestLevel")) {
			return "The default testing level, showing off whatever new content or features are added.";
		} else if (level.equals("OrbitalLevel")) {
			return "A Level to showcase the orbital enemy (with varying distances from player). "
					+ "Their normal attacks are disabled in this level.";
		} else if (level.equals("BigLevel")) {
			return "A large level (1600px x 1200px) to show orthographic camera movement and"
					+ " new Ground Enemy and Rider Enemy movement.";
		} else {
			return "DEFAULT DATA";
		}
	}

}
