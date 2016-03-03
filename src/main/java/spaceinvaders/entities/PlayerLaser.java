package spaceinvaders.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import invaders.states.GameState;
import spaceinvaders.managers.SoundManager;
import spaceinvaders.managers.TextureManager;

public class PlayerLaser extends Drawable {

	private Image laserTex;
	
	public PlayerLaser(float x, float y) {
		super(x, y);
		laserTex = GameState.texMan.getTexture(TextureManager.PLAYER_LASER);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(laserTex, x, y);
	}

	public boolean isColliding(Invader i) {
		
		if( i.getLeftBorder() < this.x + laserTex.getWidth() && 
			i.getRightBorder() > this.x &&
			i.getBottomBorder() > this.y + laserTex.getHeight() &&
			i.getTopBorder() < this.y ) {
			return true;
		}
		
		return false;
	}
	
	public boolean isColliding(UFO i) {
		
		if( i.getLeftBorder() < this.x + laserTex.getWidth() && 
			i.getRightBorder() > this.x &&
			i.getBottomBorder() > this.y + laserTex.getHeight() &&
			i.getTopBorder() < this.y ) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void update(int delta) {
		
		for(Invader i : GameState.invaders) {
			if(isColliding(i)) {
				i.isDead = true;
				GameState.removeObject(this);
				GameState.laserOnAir = false;
				GameState.increaseSpeed();
				
				SoundManager.playSound(SoundManager.INVADER_KILLED);
			}
		}
		
		for(Drawable d : GameState.objects) {
			if(d instanceof UFO) {
				if(isColliding((UFO) d)) {
					((UFO) d).isDead = true;
					GameState.removeObject(this);
					GameState.laserOnAir = false;
					GameState.increaseSpeed();
					
					SoundManager.playSound(SoundManager.INVADER_KILLED);
				}
			}
		}
		
		if(this.y <= GameState.upBorder) {
			GameState.removeObject(this);
			GameState.laserOnAir = false;
		}
		
		this.y -= GameState.laserSpeed * delta;
	}

}
