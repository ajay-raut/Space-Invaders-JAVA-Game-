package kunal.game;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kunal.game.screen.GameOverScreen;
import kunal.game.screen.MainManuScreen;
import kunal.game.tools.ScrollingBackground;

public class SpaceGame extends Game {
	
	public static final int width = 1280;
	public static final int height = 650;
	public static int score;
	
	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		this.scrollingBackground = new ScrollingBackground();
		try {
			this.setScreen(new MainManuScreen(this));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		super.render();
		//ScreenUtils.clear(1, 0, 0, 1);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
		
		
	}
	
	@Override
	
	public void resize(int width, int height) {
		this.scrollingBackground.resize(width, height);
		super.resize(width, height);
	}
	//public void dispose () {
		//batch.dispose();
		//img.dispose();
	//}
}
