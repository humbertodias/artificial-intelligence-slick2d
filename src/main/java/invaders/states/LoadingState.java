package invaders.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceinvaders.Main;
import spaceinvaders.entities.Text;

public class LoadingState extends BasicGameState {
	
	Text text;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		text = new Text("LOADING ..", -1, -1, 20, Color.white);
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		text.draw(g);
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		sbg.enterState(Main.STATE_SPLASH);
	}

	
	public int getID() {
		return Main.STATE_LOADING;
	}

}