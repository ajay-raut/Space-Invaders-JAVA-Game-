package kunal.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import kunal.game.SpaceGame;
import kunal.game.tools.ScrollingBackground;

public class MainManuScreen implements Screen {
	
	private static int EXIT_BUTTON_WIDTH = 250;
	private static int EXIT_BUTTON_HEIGHT = 120;
	private static int PLAY_BUTTON_WIDTH = 300;
	private static int PLAY_BUTTON_HEIGHT = 120;
	private static final int EXIT_BUTTON_Y = 100;
	private static final int PLAY_BUTTON_Y = 200;
	
	private static final int logo_width = 400;
	private static final int logo_height = 250;
	private static final int logo_Y = 320;
	
	final SpaceGame game;
	
	Texture playButtonActive;
	Texture playbuttonInactive;
	
	
	Texture exitButtonActive;
	Texture exitbuttonInactive;
	
	Texture logo;
	
	public MainManuScreen (final SpaceGame game) {
		this.game = game;
		playButtonActive = new Texture("play_button_active.png");
		playbuttonInactive = new Texture("play_button_inactive.png");
		exitButtonActive = new Texture("exit_button_active.png");
		exitbuttonInactive = new Texture("exit_button_inactive.png");
		
		logo = new Texture("logo.png");
		
		game.scrollingBackground.setSpeedFixed(true);
		game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
		
		final MainManuScreen mainMenuScreen = this;
		Gdx.input.setInputProcessor(new InputAdapter(){
			
			public boolean touchDown(int screenX, int screenY,int pointer, int button) {
				// exit button
				int x = SpaceGame.width / 2 - EXIT_BUTTON_WIDTH / 2;
				if(Gdx.input.getX()<x + EXIT_BUTTON_WIDTH && Gdx.input.getX()>x && SpaceGame.height -Gdx.input.getY()< EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && SpaceGame.height - Gdx.input.getY()>EXIT_BUTTON_Y) {
					mainMenuScreen.dispose();
					Gdx.app.exit();
				}
				
				x = SpaceGame.width / 2 - PLAY_BUTTON_WIDTH / 2;
				if(Gdx.input.getX()<x + PLAY_BUTTON_WIDTH && Gdx.input.getX()>x && SpaceGame.height -Gdx.input.getY()< PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && SpaceGame.height - Gdx.input.getY()>PLAY_BUTTON_Y) {
					mainMenuScreen.dispose();
					game.setScreen(new MainGameScreen(game));
				}
				
				return super.touchUp(screenX, screenY, pointer, button);
			}
			
		});
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		

		game.scrollingBackground.updateAndRender(delta, game.batch);
		
		int x = SpaceGame.width/2-EXIT_BUTTON_WIDTH/2;
				
		if(Gdx.input.getX()<x + EXIT_BUTTON_WIDTH && Gdx.input.getX()>x && SpaceGame.height -Gdx.input.getY()< EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && SpaceGame.height - Gdx.input.getY()>EXIT_BUTTON_Y) {
			
		game.batch.draw(exitButtonActive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		}
		else {
			game.batch.draw(exitbuttonInactive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		}
		
		x = SpaceGame.width/2-PLAY_BUTTON_WIDTH/2;
		
		if(Gdx.input.getX()<x + PLAY_BUTTON_WIDTH && Gdx.input.getX()>x && SpaceGame.height -Gdx.input.getY()< PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && SpaceGame.height - Gdx.input.getY()>PLAY_BUTTON_Y) {
			
		game.batch.draw(playButtonActive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		
		}
		else {
			game.batch.draw(playbuttonInactive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		}
		
		game.batch.draw(logo, SpaceGame.width / 2 - logo_width / 2, logo_Y, logo_width, logo_height);
		
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
		Gdx.input.setInputProcessor(null);
		
	}

}
