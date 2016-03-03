package spaceinvaders.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import invaders.states.GameState;
import spaceinvaders.managers.TextureManager;

public class RemainingLives extends Drawable {

	Image live;
	
	public RemainingLives(float x, float y) {
		super(x, y);
		
		live = GameState.texMan.getTexture(TextureManager.LASER);
		
	}

	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < GameState.remainingLives; i++) {
			g.drawImage(live, x + i*30, y);
		}
	}

	@Override
	public void update(int delta) {
		
	}

}
