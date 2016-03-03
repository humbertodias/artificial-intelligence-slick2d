package spaceinvaders.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import invaders.states.GameState;
import spaceinvaders.managers.SoundManager;
import spaceinvaders.managers.TextureManager;

public class PlayerObject extends Drawable {
	
	private Image playTex;
	private Animation deadTex;
	
	private boolean isDead = false;
	
	private Input inp;
	
	private float currentSpeed;
	private final float speed = 0.25f;
	
	public PlayerObject(float x, float y) {
		super(x, y);
		
		this.currentSpeed = 0;
		this.inp = new Input(0);
		
		playTex = GameState.texMan.getTexture(TextureManager.LASER);
		deadTex = GameState.texMan.getAnimatedTexture(TextureManager.PLAYER_EXPLOSION, GameState.animSpeed);
	}

	@Override
	public void draw(Graphics g) {
		if(isDead)
			g.drawAnimation(deadTex, x, y);
		else
			g.drawImage(playTex, this.x, this.y);
		
	}

	public boolean isColliding(Laser i) {
		
		if( i.getLeftBorder() <= this.x + playTex.getWidth() && 
			i.getRightBorder() >= this.x &&
			i.getBottomBorder() >= this.y &&
			i.getTopBorder() <= this.y + playTex.getHeight() ) {
			return true;
		}
		
		return false;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getX() {
		return this.x;
	}
	
	private int time = 0;
	
	@Override
	public void update(int delta) {
		
		if(isDead)
			this.time += delta;
		
		if(this.time >= 1000) {
			if(isDead) {
				GameState.restart();
				isDead = false;
				GameState.playHandle = true;
			}
			this.time = 0;
		}
		
		if(!isDead) {
			
			for(Drawable d : GameState.objects) {
				if(d instanceof Laser) {
					if(isColliding((Laser) d)) {
						GameState.remainingLives--;
						isDead = true;
						GameState.playHandle = false;
						GameState.removeObject(d);
						SoundManager.playSound(SoundManager.EXPLOSION);
						return;
					}
				}
			}
			
			for(int i = 0; i < 2; i++)
				deadTex.setDuration(i, GameState.animSpeed);
			
			if(inp.isKeyDown(Input.KEY_SPACE)) {
				if(!GameState.laserOnAir) {
					GameState.addObject(new PlayerLaser((getLeftBorder() + getRightBorder()) / 2, y));
					GameState.laserOnAir = true;
					SoundManager.playSound(SoundManager.SHOOT);
				}
			}
			
			if(inp.isKeyDown(Input.KEY_LEFT) || inp.isKeyDown(Input.KEY_A))
				this.currentSpeed = -1 * speed;
			
			if(inp.isKeyDown(Input.KEY_RIGHT) || inp.isKeyDown(Input.KEY_D))
				this.currentSpeed = 1 * speed;
			
			this.x += currentSpeed * delta * (GameState.gameSpeed / 2);
			currentSpeed = 0;
			
			if(getLeftBorder() < GameState.leftBorder)
				this.x = GameState.leftBorder;
			if(getRightBorder() > GameState.rightBorder)
				this.x = GameState.rightBorder - playTex.getWidth();
		}
		
	}

	public float getLeftBorder() {		
		return this.x;
	}
	
	public float getRightBorder() {		
		return this.x + playTex.getWidth();
	}
	
}
