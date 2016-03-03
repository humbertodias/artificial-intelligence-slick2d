package invaders;

import java.awt.event.KeyEvent;

import spaceinvaders.actors.Player;

/**
 * creates a thread to process player input
 * @author ghast
 *
 */
public class InputHandler extends Thread {
	
	private Main invaders = null;
	private Player player  = null;
	public Action action;
	public KeyEvent event;
	
	public InputHandler(Main invaders, Player player) {
		this.invaders = invaders;
		this.player = player;
	}
	
	public void run() {
		if (action == Action.PRESS) {
			if (KeyEvent.VK_ENTER == event.getKeyCode()) {
				if (invaders.gameOver || invaders.gameWon) {
					invaders.initWorld();
					invaders.game();
				}
			}
				
			else
				player.keyPressed(event);
		}
		else if (action == Action.RELSEASE)
			player.keyReleased(event);		
	}
	
	public enum Action {
		PRESS,
		RELSEASE
	}
}
