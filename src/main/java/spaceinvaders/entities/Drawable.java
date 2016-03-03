package spaceinvaders.entities;

import org.newdawn.slick.Graphics;

public abstract class Drawable {

	protected float x;
	protected float y;
	
	public Drawable(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void draw(Graphics g);
	public abstract void update(int delta);
	
}
