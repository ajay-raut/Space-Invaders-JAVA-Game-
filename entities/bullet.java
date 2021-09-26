package kunal.game.entities;

import com.badlogic.gdx.Gdx;
import kunal.game.tools.CollisionRect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class bullet {

	public static final int speed = 20;
	public static final int default_y = 40;
	public static final int width = 5;
	public static final int height = 20;
	private static Texture texture;
	
	float x,y;
	CollisionRect rect;
	public boolean remove = false;
	
	public bullet (float x, float y) {
		this.x = x;
		this.y = y;
		
		this.rect = new CollisionRect(x, y ,width, height);
		
		if(texture == null) 
			texture = new Texture("Bullet.png");
	}
	public void update(float deltaTime) {
		y += speed + deltaTime;
		
		if(y > Gdx.graphics.getHeight())
			remove = true;
	
		rect.move(x, y);
	}
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
	public CollisionRect getCollisionRect() {
		return rect;
	} 
}
