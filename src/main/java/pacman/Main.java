package pacman;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Main extends BasicGame {
	
	ImageManager images;
	SoundManager sounds;
	Score score;
	
	GameState currentGameState;
	
	public Main(String string) throws SlickException {
		super(string);
		
		score = new Score();
	}

	public Score getScore() {
		
		return score;
	}
	
	
	public void init(GameContainer gc) throws SlickException {
		
		images = new ImageManager();
		sounds = new SoundManager();
		currentGameState = new MainMenuState(this);
	}
	
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
		currentGameState.update(gc);
	}
	
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.scale(Const.SCALE, Const.SCALE);
		currentGameState.render(gc, g);
	}
	
	
    public boolean closeRequested()
    {
		score.saveHighScore();
		System.exit(0); // Use this if you want to quit the app.
		return false;
    }
	
	public static void main(String args[]) {
		
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Main("Not-Pacman"));
			app.setDisplayMode((int)(Const.WIDTH * Const.SCALE), (int)(Const.HEIGHT * Const.SCALE), false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.setTargetFrameRate(120);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {

			e.printStackTrace();
		}
	}
}


/* TO-DO List
 * 
 * 
 * Add dot/timer ghost release mechanism
 * Add paka paka sound
 * pacman speed depends on level
 * Add proper main menu
 * fruit despawn on player death
 * 
 * [Done] Add ghost in house code/ enter exit code
 * [Done] Add ghost Scatter/chase timer
 * [Done] add frightened state 
 * [Done] Get ghost to kill you, add death animation
 * [Done] Add high score properly
 * [Done] Get pathfinding to work through tunnel
 * [Done] Scrapped A* AI added arcade original Ai for all four ghosts
 * [Done] Added blinky basicAI, blinky follows generated path to player. 
 * [Done] Separate keyboard input from move commands
 * [Done] added basic AI path generation
 * [Done] Add mob base class and basics for ghosts
 * [Done] Add proper level start
 * [Done] Add text images (hi score, ready etc.)
 * [Done] Get Big Dots to flash
 * [Done] Add second frame to fruit (display points)
 * [Done] Add Lives images (and extra life at 10 000 points)
 * [Done] Add Level End (collected all pellets)
 * [Done] Add Fruit GUI
 * [Done] Add Fruit
 * 
 * 
 */