package spaceinvaders;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import invaders.states.FailState;
import invaders.states.GameState;
import invaders.states.LoadingState;
import invaders.states.SplashScreen;
import invaders.states.WonState;
import spaceinvaders.managers.SoundManager;

public class Main extends StateBasedGame {

	public static AppGameContainer appgc;
	
	public static final String gameName = "Space Invaders";
	public static final int width = 600;
	public static final int height = 600;
	
	// === STATES ===
	public static final int STATE_SPLASH = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_MENU = 2;
	public static final int STATE_GAME = 3;
	public static final int STATE_WON = 4;
	public static final int STATE_FAIL = 5;
	
	public static int nextState = STATE_GAME;
	
	public Main(String name) {
		super(name);
		
		this.addState(new SplashScreen());
		this.addState(new LoadingState());
		this.addState(new GameState());
		this.addState(new WonState());
		this.addState(new FailState());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		SoundManager.init(); 
 		SoundManager.startMusic();
		this.enterState(STATE_LOADING);
	}
	
	public static void main(String[] args) {
		
		try {
			appgc = new AppGameContainer(new Main(gameName));
			//appgc.setFullscreen(true);
			appgc.setDisplayMode(width, height, false);
			appgc.setTargetFrameRate(60);
			appgc.setShowFPS(false);
			appgc.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
}
