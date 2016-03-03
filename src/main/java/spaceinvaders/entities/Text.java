package spaceinvaders.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import spaceinvaders.Main;

public class Text extends Drawable {
	private final String resourceFolder = "spaceinvaders/";
	private BFont ttf;
	
	private String message;
	private int size;
	private Color color;
	
	public Text(String message, float x, float y, int size, Color white) {
		super(x, y);
		
		this.message = message;
		this.size = size;
		this.color = white;
		
		try {
			ttf = new BFont(resourceFolder + "font/arcadepi.ttf", this.size);
			if(x == -1 && y == -1) {
				this.x = (Main.appgc.getWidth() - ttf.getWidth(message)) / 2;
				this.y = (Main.appgc.getHeight() - ttf.getHeight(message)) / 2;
			} else if(x == -1) {
				this.x = (Main.appgc.getWidth() - ttf.getWidth(message)) / 2;
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void changeColor(Color newColor) {
		this.color = newColor;
	}
	
	@Override
	public void draw(Graphics g) {
		ttf.drawString(x, y, message, color);
	}

	public int getWidth() {
		return ttf.getWidth(message);
	}
	
	public int getHeight() {
		return ttf.getHeight(message);
	}
	
	@Override
	public void update(int delta) {
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
