package invaders.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceinvaders.Main;
import spaceinvaders.entities.Text;
import spaceinvaders.managers.SoundManager;

public class WonState extends BasicGameState {
	
	Text text;
	
	private int time;
	private String wonMessage = "YOU WON THIS TIME EARTHLING";
	private int currentLetter = 0;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		text = new Text(wonMessage, -1, -1, 20, Color.white);
		text.setMessage("");
	}
	
	public void keyPressed(int key, char c) {
	}
	
	
	public void enter(GameContainer gc, StateBasedGame sbg) {
		SoundManager.stopAll();
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		text.draw(g);
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		time += delta;
		
		if(time >= 200 && currentLetter + 1 <= wonMessage.length()) {
			text.setMessage(text.getMessage() + wonMessage.charAt(currentLetter++));
			time = 0;
		}
		
	}

	
	public int getID() {
		return Main.STATE_WON;
	}

}