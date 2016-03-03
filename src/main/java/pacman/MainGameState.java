package pacman;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import pacman.Ghost.AIState;
import pacman.Ghost.GhostName;

public class MainGameState implements GameState {
	
	Main game;
	
	Player player;
	Level level;
	Score playerScore;
	FruitManager fruitManager;
	
	Ghost blinkyGhost;
	Ghost pinkyGhost;
	Ghost inkyGhost;
	Ghost clydeGhost;
	Ghost[] ghosts;
	
	LevelState levelState;
	boolean nextLevelTransition;
	boolean gamePaused;
	
	Timer levelTransitionTimer;
	Timer aiStateTimer;
	Timer ghostScareTimer;
	
	Flash upTextFlash;
	
	MainGameState(Main game) {

		this.game = game;	
		
		level  = new Level();
		fruitManager = new FruitManager();
		playerScore = game.getScore();
		
		player = new Player(level, playerScore, fruitManager);
		player.setShouldRender(true);
		
		blinkyGhost = new Ghost(level, playerScore, player, GhostName.BLINKY);
		pinkyGhost = new Ghost(level, playerScore, player, GhostName.PINKY);
		inkyGhost = new Ghost(level, playerScore, player, GhostName.INKY);
		clydeGhost = new Ghost(level, playerScore, player, GhostName.CLYDE);
		ghosts = new Ghost[] {blinkyGhost, pinkyGhost, inkyGhost, clydeGhost};
		inkyGhost.setBlinkyRef(blinkyGhost);
		
		levelReset();
		
		nextLevelTransition = false;
		levelTransitionTimer = new Timer();
		aiStateTimer = new Timer();
		ghostScareTimer = new Timer();
		levelState = LevelState.GAME_BEGIN;
		levelTransitionTimer.start();
		game.sounds.levelStart.play();
		
		upTextFlash = new Flash(20);
	}
	
	private void levelReset() {
		
		level.resetLevel();
		player.spawn();
		for (int i = 0; i < ghosts.length; i++) {
			
			ghosts[i].spawn();
			ghosts[i].newLevelStart();
		}
	}
	
	private void nextLevelTransitionStart() {
		
		nextLevelTransition = true;
		player.setNextLevelTransition(true);
		level.setFlashing(true);
		levelTransitionTimer.start();
	}
	
	private void nextLevelTransitionEnd() {
		
		nextLevelTransition = false;
		player.setNextLevelTransition(false);
		level.setFlashing(false);
		playerScore.setLevel(playerScore.getLevel() + 1);
		fruitManager.nextLevel(playerScore);
		game.sounds.levelStart.play();
		levelState = LevelState.LEVEL_BEGIN;
		levelTransitionTimer.start();
		
		levelReset();
	}
	
	private void checkStateTransition() {

		switch (levelState) {
		
		case GAME_BEGIN:
			level.setBigDotFlashPause(true);
			
			if (levelTransitionTimer.getTicks() > 2000000000l) {
				levelTransitionTimer.start();
				levelState = LevelState.LEVEL_BEGIN;
				
				for (int i = 0; i< ghosts.length; i++) {
					ghosts[i].setShouldRender(true);
				}
			}
			break;
			
		case LEVEL_BEGIN:
			level.setBigDotFlashPause(true);
			
			if (levelTransitionTimer.getTicks() > 1200000000l) {
				levelState = LevelState.RUNNING;
				aiStateTimer.start();
				level.setBigDotFlashPause(false);
			}
			break;
		case DEATH_PAUSE:
			
			if (levelTransitionTimer.getTicks() > 1000000000l) {
				levelState = LevelState.DEATH_ANIM;
				for(int i = 0; i< ghosts.length; i++) {
					
					ghosts[i].setShouldRender(false);
				}
				player.setXMaxFrame(7);
				player.xFrame = 0;
				player.setXStepsPerFrame(4);
				player.setXFrameDir(1);
				levelTransitionTimer.start();
			}
			break;
		case DEATH_ANIM:
			
			if(player.xFrame != 7) {
				
				player.animationUpdate();
			}
			if (levelTransitionTimer.getTicks() > 1000000000l) {
				
				player.setXMaxFrame(3);
				player.xFrame = 0;
				player.setXFrameDir(1);
				player.setXStepsPerFrame(1);
				player.spawn();
				
				for (int i = 0; i< ghosts.length; i++) {
					
					ghosts[i].spawn();
				}
				levelTransitionTimer.start();
				levelState = LevelState.LEVEL_BEGIN;
				gamePaused = false;
				
				for (int i = 0; i< ghosts.length; i++) {
					ghosts[i].setShouldRender(true);
				}
			}
			
		default:
		}
	}
	
	private Direction keyboardInToDirection(Input input) {
		
		if (input.isKeyDown(Const.KEY_UP)) {
			return Direction.UP;
		} else if (input.isKeyDown(Const.KEY_DOWN)) {
			return Direction.DOWN;
		} else if (input.isKeyDown(Const.KEY_LEFT)) {
			return Direction.LEFT;
		} else if (input.isKeyDown(Const.KEY_RIGHT)) {
			return Direction.RIGHT;
		}
		
		return Direction.NONE;
	}
	
	private int playerGhostCollided() {
		
		IntPair playerGridPos = new IntPair(player.getGridCoord(player.getX()), player.getGridCoord(player.getY()));
		
		for (int i = 0; i < ghosts.length; i++) {
			
			if(ghosts[i].isDead()) continue;
			
			IntPair ghostGridPos = new IntPair(ghosts[i].getGridCoord(ghosts[i].getX()), ghosts[i].getGridCoord(ghosts[i].getY()));
			
			if ((playerGridPos.x == ghostGridPos.x) && (playerGridPos.y == ghostGridPos.y)) {
				
				return i;
			}
		}
		
		return -1;
	}
	
	private AIState changeGhostAI() {
		
		
		double[] timeMarkers;
		if (game.score.getLevel() == 0) {
		
			 timeMarkers = new double[] {7, 20, 7, 20, 5, 20, 5};
		} else if (game.score.getLevel() <= 3) {
			
			timeMarkers = new double[] {7, 20, 7, 20, 5, 1033, 1 / 60};
		} else {
			
			timeMarkers = new double[] {5, 20, 5, 20, 5, 1037, 1 / 60};
		}

		
		double counter = 0;
		for (int i = 0; i< timeMarkers.length; i++) {
			
			if ( aiStateTimer.getTicks() < 1000000000l * (counter + timeMarkers[i])) {
				
				if (i % 2 == 0) {
					return AIState.SCATTER;
				} else {
					return AIState.CHASE;
				}
			}
			
			counter += timeMarkers[i];
		}
		return AIState.CHASE;
	}
	
	private int getFrightTime() {
		
		switch(game.score.getLevel()) {
		
		case 0:
			return 6;
		case 1:
			return 5;
		case 2:
			return 4;
		case 3:
			return 3;
		case 4:
			return 2;
		case 5:
			return 5;
		case 6:
			return 2;
		case 7:
			return 2;
		case 8:
			return 1;
		case 9:
			return 5;
		case 10:
			return 2;
		case 11:
			return 1;
		case 12:
			return 1;
		case 13:
			return 3;
		case 14:
			return 1;
		case 15:
			return 1;
		case 16:
			return 0;
		case 17:
			return 1;
		case 18:
			return 0;
		case 19:
			return 0;
		default:
			return 0;
		}
	}
	
	
	public void update(GameContainer gc) {
		
		checkStateTransition();
		
		player.move(keyboardInToDirection(gc.getInput()));
		for (int i = 0; i < ghosts.length; i++) {
			
			ghosts[i].move();
		}
		
		if (gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			System.out.println(player.getGridCoord(player.getX()) + " " + player.getGridCoord(player.getY()));
		}
		
		if(levelState != LevelState.GAME_BEGIN && levelState != LevelState.LEVEL_BEGIN && !gamePaused) {
			player.update();
			
			if (player.collectedBigDot()) {
				
				for (int i = 0; i < ghosts.length; i++) {
					
					ghosts[i].scare();
					ghostScareTimer.start();
					aiStateTimer.pause();
				}
			}
			
			for (int i = 0; i < ghosts.length; i++) {
				
				ghosts[i].update();
			}
			
			{
				int result = playerGhostCollided();
				if(result != -1) {
					
					if (ghosts[result].isFrightened()) {
						
						ghosts[result].kill();
						
					} else {
						
						gamePaused = true;
						game.getScore().setLives(game.getScore().getLives() - 1);
						levelTransitionTimer.start();
						levelState = LevelState.DEATH_PAUSE;
					}
				}
			}
		}
		
		{
			AIState result = changeGhostAI();
			
			if (result == AIState.SCATTER) {
				for (int i = 0; i < ghosts.length; i++) {
					if (ghosts[i].aiState != AIState.SCATTER) {
						ghosts[i].scatter();
					}
				}
			} else {
				for (int i = 0; i < ghosts.length; i++) {
					if (ghosts[i].aiState != AIState.CHASE) {
						ghosts[i].chase();
					}
				}
			}
		}
		
		if (Ghost.areScared()) {
			
			if (ghostScareTimer.getTicks() > getFrightTime() * 1000000000l) {
				
				aiStateTimer.resume();
				for (int i = 0; i < ghosts.length; i++) {
					
					ghosts[i].unscare();
				}
			}
		}
		
		level.update();
		fruitManager.update();
		upTextFlash.update();
		
		if (level.getDotCount() <= 0) {
			
			if(!nextLevelTransition) {
				nextLevelTransitionStart();
			} 
			else if (levelTransitionTimer.getTicks() >= 2000000000) {
				nextLevelTransitionEnd();
			}
		}
		else if (level.getInitDotCount() - level.getDotCount() == 70) {
			
			fruitManager.spawn1stFruit();
		} 
		else if (level.getInitDotCount() - level.getDotCount() == 170) {
			
			fruitManager.spawn2ndFruit();
		}
	}

	
	public void render(GameContainer gc, Graphics g) {
	
		
		Render.renderScore(game.images, playerScore.getScore(), g, Const.MAX_SCORE_DIGITS);
		Render.renderScore(game.images, playerScore.getHighScore(), g, Const.MAX_SCORE_DIGITS * 2 + 2);
		
		Render.renderLevel(game.images, g, level);
		Render.renderFruit(game.images, g, level, fruitManager, playerScore);
		Render.renderPlayer(game.images, g ,player, playerScore, levelState);
		
		for (int i = 0; i < ghosts.length; i++) {
			
			Render.renderGhost(game.images, g, ghosts[i], playerScore);
		}
		
		Render.renderFruitGUI(game.images, g, fruitManager);
		Render.renderPlayerLives(game.images, g, playerScore);
		Render.renderText(game.images, g, playerScore, levelState, upTextFlash);
		
		//DEBUG
		
		g.setColor(Color.red);
		g.fillRect((blinkyGhost.goalPos.x - 2) * Const.TILE_WIDTH, (blinkyGhost.goalPos.y + 3) * Const.TILE_WIDTH, Const.TILE_WIDTH, Const.TILE_WIDTH);
		g.setColor(Color.pink);
		g.fillRect((pinkyGhost.goalPos.x - 2) * Const.TILE_WIDTH, (pinkyGhost.goalPos.y + 3) * Const.TILE_WIDTH, Const.TILE_WIDTH, Const.TILE_WIDTH);
		g.setColor(Color.blue);
		g.fillRect((inkyGhost.goalPos.x - 2) * Const.TILE_WIDTH, (inkyGhost.goalPos.y + 3) * Const.TILE_WIDTH, Const.TILE_WIDTH, Const.TILE_WIDTH);
		g.setColor(Color.orange);
		//g.fillRect((clydeGhost.goalPos.x - 2) * Const.TILE_WIDTH, (clydeGhost.goalPos.y + 3) * Const.TILE_WIDTH, Const.TILE_WIDTH, Const.TILE_WIDTH);	
		
	}
}