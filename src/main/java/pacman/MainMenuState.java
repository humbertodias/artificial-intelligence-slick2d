package pacman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class MainMenuState implements GameState {

	Main game;

	
	MainMenuState(Main game) {

		this.game = game;		
	}

	
	public void update(GameContainer gc) {
		
		if(gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			game.currentGameState = new MainGameState(game);
		}
	}

	
	public void render(GameContainer gc, Graphics g) {
		
		g.drawImage(game.images.mainMenuBack, 0, 0);
	}
}
