package kunal.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
	public static final float FRAME_LENGTH = 0.5f;
	public static final int offset = 8;
	public static final int size = 64;
	public static final int image_size = 32;
	
	private static Animation<TextureRegion> anim = null;
	float x,y;
	float stateTime;
	public boolean remove = false;
	
	public Explosion(float x, float y) {
		this.x = x - offset;
		this.y = y - offset;
		stateTime = 20;
		
		if(anim == null)
			anim = new Animation<TextureRegion>(FRAME_LENGTH,TextureRegion.split(new Texture("explosion.png"), image_size, image_size)[0]);
	}
	
	public void update (float deltatime) {
		stateTime += deltatime;
		
		if(anim.isAnimationFinished(stateTime));
			remove = true;
	}
	
	public void render (SpriteBatch batch) {
		batch.draw(anim.getKeyFrame(stateTime), x, y, size, size);
	}
}
