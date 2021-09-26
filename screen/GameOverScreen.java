package kunal.game.screen;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import kunal.game.SpaceGame;
import kunal.game.tools.ScrollingBackground;


public class GameOverScreen implements Screen{

	private static final int BANNER_WIDTH = 300;
	private static final int BANNER_HEIGHT = 100;
	SpaceGame game;
	int score,highscore;
	
	Texture gameoverBanner;
	BitmapFont scoreFont; 
	
	public GameOverScreen(SpaceGame game, int score) throws Throwable {
		this.game = game;
		this.score = score;
		
		//get high score from save file
		com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("SpaceGame");
		this.highscore = prefs.getInteger("highscore", 0);
		
		
		// check if score beats 
		if(score > highscore) {
			prefs.putInteger("highscore", score);
			prefs.flush();
			}
		
			gameoverBanner  = new Texture("game_over.png");
			scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
			
			game.scrollingBackground.setSpeedFixed(true);
			game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
	
	}
	
	
	
	@Override
	public void show() {

		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		

		game.scrollingBackground.updateAndRender(delta, game.batch);
		
		game.batch.draw(gameoverBanner, Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 1, BANNER_WIDTH, BANNER_HEIGHT);
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score \n" + score,Color.WHITE, 0,Align.left, false);
		GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "High Score \n" + highscore,Color.WHITE, 0,Align.left, false);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);
		scoreFont.draw(game.batch, highscoreLayout, Gdx.graphics.getWidth() / 2 - highscoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - scoreLayout.height - 15 * 2);
		
		GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "try again");
		GlyphLayout mainmenuLayout = new GlyphLayout(scoreFont, "Main Menu");
		float tryagainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2;
		float tryagainY = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2 - 300;
		float mainmenuX = Gdx.graphics.getWidth() / 2 - mainmenuLayout.width / 2;
		float mainmenuY = Gdx.graphics.getWidth() / 2 - mainmenuLayout.width / 2 - tryAgainLayout.height - 15-300;
		
		float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		// try again is being pressed
		if(Gdx.input.isTouched()) {
			//try again
			if(touchX > tryagainX && touchX < tryagainX + tryAgainLayout.width && touchY > tryagainY - tryAgainLayout.height && touchY < tryagainY ) {
				this.dispose();
				game.batch.end();
				game.setScreen(new MainGameScreen(game));
				return;
			}
			
			// main menu
			if(touchX > mainmenuX && touchX < mainmenuX + mainmenuLayout.width && touchY > mainmenuY - mainmenuLayout.height && touchY < mainmenuY ) {
				this.dispose();
				game.batch.end();
				game.setScreen(new MainManuScreen(game));
				return;
			}
		}
		// draw buttons 
		scoreFont.draw(game.batch, tryAgainLayout, tryagainX, tryagainY);
		scoreFont.draw(game.batch, mainmenuLayout, mainmenuX, mainmenuY);
		
		
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
	
		
	}

	@Override
	public void dispose() {
		
		
	}
	

}
