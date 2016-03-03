package invaders.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceinvaders.Main;
import spaceinvaders.entities.Text;

public class SplashScreen extends BasicGameState {
	
	Input inp;
	Text text;
	
	private int time;
	private String splashMessage = "SPACE INVADERS";
	private int currentLetter = 0;
	
	private StateBasedGame sbg;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		inp = new Input(0);
		text = new Text(splashMessage, -1, -1, 20, Color.white);
		text.setMessage("");
		this.sbg = sbg;
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		text.draw(g);
	}

	public void keyPressed(int key, char c) {
		if(key == Input.KEY_RETURN)
			this.sbg.enterState(Main.STATE_GAME);
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		time += delta;
		
		if(time >= 200 && currentLetter + 1 <= splashMessage.length()) {
			text.setMessage(text.getMessage() + splashMessage.charAt(currentLetter++));
			time = 0;
		}
		
	}

	
	public int getID() {
		return Main.STATE_SPLASH;
	}

}
