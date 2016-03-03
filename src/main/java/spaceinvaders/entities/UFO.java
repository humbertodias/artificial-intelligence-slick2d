package spaceinvaders.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.openal.Audio;

import invaders.states.GameState;
import spaceinvaders.Main;
import spaceinvaders.managers.SoundManager;
import spaceinvaders.managers.TextureManager;

public class UFO extends Drawable {

	private Text pointText;
	private Image ufoTex;
	public int point;
	
	private float speed = 0.1f;
	private int direction;
	
	public boolean isDead = false;
	
	Audio ufoSound;
	
	public UFO(float x, float y) {
		super(x, y);
		
		ufoTex = GameState.texMan.getTexture(TextureManager.UFO);
		
		Random rand = new Random();
		point = 50 * (rand.nextInt(6) + 1);
		pointText = new Text("+" + Integer.toString(point), x, y, 16, Color.white);
		this.y = 70;
			
		switch(rand.nextInt(2)) {
		case 0:
			this.x = -1 * ufoTex.getWidth();
			direction = 1;
			break;
		case 1:
			this.x = Main.appgc.getWidth();
			direction = -1;
			break;
		}
		
		SoundManager.playSound(SoundManager.UFO, 0.6f, true);
		
	}

	public float getLeftBorder() {		
		return this.x;
	}
	
	public float getRightBorder() {		
		return this.x + ufoTex.getWidth();
	}
	
	public float getTopBorder() {
		return this.y;
	}
	
	public float getBottomBorder() {
		return this.y + ufoTex.getHeight();
	}
	
	@Override
	public void draw(Graphics g) {
		if(isDead)
			pointText.draw(g);
		else
			g.drawImage(ufoTex, x, y);
	}

	@Override
	public void update(int delta) {
		
		if(isDead && GameState.time >= 1000 * (1 / GameState.gameSpeed)) {
			GameState.removeObject(this);
			GameState.score += point;
			SoundManager.stopSound(SoundManager.UFO);
		}
		
		if(this.x > Main.appgc.getWidth() || this.x < -1 * ufoTex.getWidth()) {
			GameState.removeObject(this);
			SoundManager.stopSound(SoundManager.UFO);
		}
		
		if(!isDead)
			this.x += direction * speed * delta;
		else
			pointText.setPosition(this.x, this.y);
	}

}
