package kunal.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
	
	public static final int DEFAULT_SPEED = 5;
	public static final int ACCELERATION = 300;
	public static final int GOAL_REACH_ACCELERATION = 200;
	
	Texture image;
	float y1,y2;
	int speed;
	int goalSpeed;
	float imageScale;
	boolean speedFixed;
	
	public ScrollingBackground(){
		image = new Texture("stars_background.png");
		
		y1 = 0;
		y2 = image.getHeight();
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		imageScale = 0;
		speedFixed = true;
		
	}
	
	public void updateAndRender(float deltaTime,SpriteBatch batch) {
		//speed adjustment to reach goal
		if(speed < goalSpeed) {
			speed += GOAL_REACH_ACCELERATION + deltaTime;
			if(speed > goalSpeed) 
				speed = goalSpeed;
			}else if(speed > goalSpeed) {
				speed -= GOAL_REACH_ACCELERATION + deltaTime;
				if(speed < goalSpeed)
					speed = goalSpeed;
			}
		
		if(!speedFixed)
			speed += ACCELERATION * deltaTime;
		
			y1 -= speed + deltaTime;
			y2 -= speed + deltaTime;
			
			//if image reaches the bottom of screen is not visible , put back on top
			if(y1 + image.getHeight() * imageScale <= 0 )
				y1 = y2 + image.getHeight() * imageScale;
			
			if(y2 + image.getHeight() * imageScale<= 0 )
				y2 = y1 + image.getHeight() * imageScale;
			
			//render
			batch.draw(image, 0, y1,Gdx.graphics.getWidth(),image.getHeight() * imageScale);
			batch.draw(image, 0, y2,Gdx.graphics.getWidth(),image.getHeight() * imageScale);
		}
	
		public void resize(int width, int height) {
			imageScale = width / image.getWidth();
			
		}
		
		public void setSpeed(int goalSpeed) {
			this.goalSpeed = goalSpeed;
			
		}
		
		public void setSpeedFixed(boolean speedFixed) {
			this.speedFixed = speedFixed;
			
		}
	}
	
