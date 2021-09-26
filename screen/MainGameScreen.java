package kunal.game.screen;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.decorator.Random;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import kunal.game.SpaceGame;
import kunal.game.entities.bullet;
import kunal.game.tools.CollisionRect;
import kunal.game.entities.Asteroid;
import kunal.game.entities.Explosion;
public class MainGameScreen implements Screen {
	public static final float speed = 10;
	public static final float ANIMATION_SPEED = 0.5f;
	public static final int SHIP_WIDTH_PIXAL = 17;
	public static final int SHIP_HEIGHT_PIXAL = 32;
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXAL * 3;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXAL * 3;
	int roll;
	
	public static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
	public static final float SHOOT_WAIT_TIME = 0.3f;
	public static final float MIN_ASTEROID_SPAWN_TIME = 0.1f;
	public static final float MAX_ASTEROID_SPAWN_TIME = 0.3f;
	
	Animation<TextureRegion>[] rolls; 
	float rollTimer;
	float x;
	float y;
	float stateTime;
	float shootTimer;
	float asteroidSpawnTimer;
	
	Random<TextureRegion> random;
	
	public SpaceGame game;
	
	ArrayList<bullet> bullets;
	ArrayList<Asteroid> asteroids;
	ArrayList<Explosion> explosions;
	
	Texture blank;
	
	BitmapFont scoreFont;
	
	CollisionRect playerRect;
	
	Sound sound;
	
	float health = 1;
	
	int score=0;
	
	public MainGameScreen (SpaceGame game) {
		
		this.game = game; 
		y = 15;
		x = SpaceGame.width/2-SHIP_WIDTH/2;
		
		bullets = new ArrayList<bullet>();
		asteroids = new ArrayList<Asteroid>();
		explosions = new ArrayList<Explosion>();
		
		scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
		
		sound = Gdx.audio.newSound(Gdx.files.internal("inn.mp3"));
		
		blank = new Texture("blank.png");
		
		playerRect = new CollisionRect(0,0, SHIP_WIDTH,SHIP_HEIGHT);
		score = 0;
		
		Random random = new Random();
		asteroidSpawnTimer = (float)Math.random() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
		shootTimer = 0;
		
		rollTimer = 0;
		roll = 2;
		rolls = new Animation[5];
		
		
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"),SHIP_WIDTH_PIXAL, SHIP_HEIGHT_PIXAL);
		rolls[0]  = new Animation<TextureRegion>(ANIMATION_SPEED,rollSpriteSheet[2]);
		rolls[1]  = new Animation<TextureRegion>(ANIMATION_SPEED,rollSpriteSheet[1]);
		rolls[2]  = new Animation<TextureRegion>(ANIMATION_SPEED,rollSpriteSheet[0]);
		rolls[3]  = new Animation<TextureRegion>(ANIMATION_SPEED,rollSpriteSheet[3]);
		rolls[4]  = new Animation<TextureRegion>(ANIMATION_SPEED,rollSpriteSheet[4]);
		
		game.scrollingBackground.setSpeedFixed(false);
		
	}
	
	@Override
	public void show() {

		sound.play();
		
	}

	@Override
	public void render(float delta) {
		//shooting
		shootTimer += delta;
		
		if(Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
			shootTimer = 0;
			
			int offset = 4;
			
			if(roll == 1 || roll == 3)
				offset = 8;
			
			if(roll == 0 || roll == 4)
				offset= 5;
			if(Gdx.input.isKeyPressed(Keys.UP))
					offset = 2;
				
			bullets.add(new bullet(x + offset, y + offset + SHIP_HEIGHT / 2 ));
			bullets.add(new bullet(x+ SHIP_WIDTH - offset,y + offset + SHIP_HEIGHT / 2));
			
		}
		
		//asteroids spawn
		
		asteroidSpawnTimer -= delta;
		if(asteroidSpawnTimer <= 0) {
			asteroidSpawnTimer = (float)Math.random() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
			asteroids.add(new Asteroid(3));
			
		}
		
		// update asteroids
		ArrayList<Asteroid> asteroidToRemove = new ArrayList<Asteroid>();
		for(Asteroid asteroid : asteroids) {
			asteroid.update(delta);
			if(asteroid.remove)  
				asteroidToRemove.add(asteroid);
			
		}
		
		
		
		//update
		ArrayList<bullet> bulletsToRemove = new ArrayList<bullet>();
		for(bullet bullet:bullets) {
		bullet.update(delta);
		if(bullet.remove)
			bulletsToRemove.add(bullet);
		}
		
		// update
		ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
		for(Explosion explosion:explosions) {
		explosion.update(delta);
		if(explosion.remove)
			explosionsToRemove.add(explosion);
		}
		explosions.removeAll(explosionsToRemove);
		
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			y += speed + Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			y -= speed + Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) { //left
			x -= speed + Gdx.graphics.getDeltaTime();
			if(x<0)
				x=0;
			
			//update roll if button just clicked
			if(Gdx.input.isKeyJustPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll > 0) {
				rollTimer = 0;
				roll--;
			}
	
			// update roll
			rollTimer -= Gdx.graphics.getDeltaTime();
			if(Math.abs(rollTimer) < ROLL_TIMER_SWITCH_TIME) {
				rollTimer -= ROLL_TIMER_SWITCH_TIME;
				roll--;
				if(roll<0)
					roll=0;
			}
			
			
		}
		else {
			
			if(roll < 2)
			{
				// update roll
				rollTimer += Gdx.graphics.getDeltaTime();
				if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME) {
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll++;
					if(roll>4)
						roll=0;
				}
			}
		}
		
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) { //right
			x += speed + Gdx.graphics.getDeltaTime();
			
			if(x + SHIP_WIDTH > Gdx.graphics.getWidth())
				x = Gdx.graphics.getWidth() - SHIP_WIDTH;
			
			//update roll if button just clicked
			if(Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && roll > 0) {
				rollTimer = 0;
				roll--;
			}
			
			// update roll
						rollTimer += Gdx.graphics.getDeltaTime();
						if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME) {
							rollTimer -= ROLL_TIMER_SWITCH_TIME;
							roll++;
							if(roll>4)
								roll=4;
						}
			
		}
		else {
			// update roll
			if(roll > 2) {
				// update roll
				rollTimer -= Gdx.graphics.getDeltaTime();
				if(Math.abs(rollTimer) < ROLL_TIMER_SWITCH_TIME) {
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll--;
					if(roll<0)
						roll=0;
				}
			}
		}
		
		//afterPlayer moves, update collision test
		playerRect.move(x, y);
		
		
		//after roll update,  check for collisions
		for(bullet bullet:bullets) {
			for(Asteroid asteroid : asteroids) {
				if(bullet.getCollisionRect().collidesWidth(asteroid.getCollisionRect())) {
					bulletsToRemove.add(bullet);
					asteroidToRemove.add(asteroid);
					explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
					
					
					score += 100;
				}
					
			}
		}
		bullets.removeAll(bulletsToRemove);
		asteroids.removeAll(asteroidToRemove); 
		
		for(Asteroid asteroid : asteroids) {
			if(asteroid.getCollisionRect().collidesWidth(playerRect)){
				asteroidToRemove.add(asteroid);
				health -= 0.1;
				
				// if health is depleted
				if(health <= 0) {
					
					game.dispose();
					try {
						game.setScreen(new GameOverScreen(game,score));
						return;
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		asteroids.removeAll(asteroidToRemove); 
		stateTime += delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		game.batch.begin();
		
		game.scrollingBackground.updateAndRender(delta, game.batch);
	
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10);
		
		for(bullet bullets : bullets) {
			bullets.render(game.batch);
		}
		
		for(Asteroid asteroid : asteroids) {
			asteroid.render(game.batch);
		}
		
		for(Explosion explosion : explosions) {
			explosion.render(game.batch);
		}
		
		if(health > 0.6f)
			game.batch.setColor(Color.GREEN);
		else if(health > 0.2f)
			game.batch.setColor(Color.ORANGE);
		else 
			game.batch.setColor(Color.RED);
		game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * health, 15);
		
		
		game.batch.setColor(Color.WHITE);
		game.batch.draw( rolls[roll].getKeyFrame(stateTime,true),x,y,SHIP_WIDTH,SHIP_HEIGHT );
		
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
