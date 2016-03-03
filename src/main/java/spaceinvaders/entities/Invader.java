package spaceinvaders.entities;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import invaders.states.GameState;
import spaceinvaders.managers.SoundManager;

public class Invader extends Drawable {

	protected boolean isDead;
	
	protected Image deadTex;
	protected Animation liveTex;
	
	protected static Random rand;
	protected static boolean turnTimer;
	
	protected float speed;
	
	private boolean fireConfirmed;
	
	public Invader(float x, float y) {
		super(x, y);
		
		rand = new Random();
		
		isDead = false;
		turnTimer = false;
		speed = 0.5f;
		
		fireConfirmed = false;
		
	}

	@Override
	public void draw(Graphics g) {
		//g.drawLine(0, this.y + 16 + 20, 600, this.y + 16 + 20);
	}

	private boolean checkBottom() {
		
		float pointX = this.x + 10;
		float pointY = this.y + 16 + 20;
		
		for(Invader i : GameState.invaders) {
			if( i.getLeftBorder() < pointX && 
					i.getRightBorder() > pointX &&
					i.getBottomBorder() > pointY &&
					i.getTopBorder() < pointY ) {
					return true;
				}
		}
		
		return false;
	}
	
	private boolean wantsToFire() {
		if(rand.nextInt(150) % 7 == 0 &&
			this.x - 16 < GameState.player.getX() &&
			this.x + 16 > GameState.player.getX())
			return true;
		
		return false;
	}
	
	@Override
	public void update(int delta) {
		
		if(!isDead)
			for(int i = 0; i < 2; i++)
				liveTex.setDuration(i, GameState.animSpeed);
		
		if(GameState.time >= 1000 * (1 / GameState.gameSpeed)) {
			
			if(isDead) {
				GameState.removeObject(this);
				if(this instanceof SmallInvader)
					GameState.score += 30;
				else if(this instanceof MediumInvader)
					GameState.score += 20;
				else if(this instanceof LargeInvader)
					GameState.score += 10;
				
			}
			
			if(!checkBottom() && wantsToFire()) {
				fireConfirmed = true;
			}
			
			this.x += GameState.invaderDirection * speed * delta;
			
		}
		
		if(fireConfirmed) {
			GameState.addObject(new Laser1(this.x + ((getRightBorder() - getLeftBorder()) / 2), getBottomBorder()));
			fireConfirmed = false;
			SoundManager.playSound(SoundManager.SHOOT);
		}
		
	}
	
	public float getLeftBorder() {		
		return this.x;
	}
	
	public float getRightBorder() {		
		return this.x + liveTex.getWidth();
	}
	
	public float getTopBorder() {
		return this.y;
	}
	
	public float getBottomBorder() {
		return this.y + liveTex.getHeight();
	}
	
	public Image getImage() {
		return liveTex.getCurrentFrame();
	}
	
	public void moveDown(int delta) {
		this.y += speed * delta;
	}

}
