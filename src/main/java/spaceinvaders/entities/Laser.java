package spaceinvaders.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import invaders.states.GameState;

public class Laser extends Drawable {

	protected Image laserTex;
	public Laser(float x, float y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics g) {

	}

	@Override
	public void update(int delta) {
		
		if( this.y >= GameState.bottomBorder )
			GameState.removeObject(this);
		
		this.y += GameState.laserSpeed * delta;
	}
	
	public float getLeftBorder() {		
		return this.x;
	}
	
	public float getRightBorder() {		
		return this.x + laserTex.getWidth();
	}
	
	public float getTopBorder() {
		return this.y;
	}
	
	public float getBottomBorder() {
		return this.y + laserTex.getHeight();
	}

}
