package kunal.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kunal.game.tools.CollisionRect;

public class Asteroid {
	public static final int speed = 5;
	public static final int width = 40;
	public static final int height = 40;
	private static Texture texture;
	
	float x,y;
	CollisionRect rect;
	public boolean remove = false;
	
	public Asteroid (float x) {
		this.x = 10+ (float)Math.random() *(700);
		this.y = Gdx.graphics.getHeight();
		
		this.rect = new CollisionRect(x, y ,width, height);
		
		if(texture == null) 
			texture = new Texture("spaceship6.png");
	}
	public void update(float deltaTime) {
		y -= speed + deltaTime;
		if(y <- height)
			remove = true;
		
		rect.move(x, y);
	}
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
	public CollisionRect getCollisionRect() {
		return rect;
	} 
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}

