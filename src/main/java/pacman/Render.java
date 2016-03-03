package pacman;

import java.util.ArrayDeque;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import pacman.FruitManager.FruitState;
import pacman.Level.Tile;

public class Render {

	public static void renderScore(ImageManager images, int score, Graphics g, int gridX) {
		
		String scoreText = String.valueOf(score);
		if (score == 0) {
			scoreText = "00";
		}
		
		for (int i = scoreText.length() - 1; i >= 0; i--) {
			
			int numOffset = (int)scoreText.charAt(i) - 48;
			
			g.drawImage(images.numbers, (gridX - (scoreText.length() - i - 1)) * Const.TILE_WIDTH, Const.TILE_WIDTH,
					(gridX - (scoreText.length() - i - 1)) * Const.TILE_WIDTH + Const.TILE_WIDTH,
					Const.TILE_WIDTH * 2,
					numOffset * Const.TILE_WIDTH, 0,
					numOffset * Const.TILE_WIDTH + Const.TILE_WIDTH, Const.TILE_WIDTH);
		}

	}
	
	public static void renderLevel(ImageManager images, Graphics g, Level level) {
		
		int yOffset = 3 * Const.TILE_HEIGHT;
		int xOffset = -2 * Const.TILE_WIDTH;
		
		int srcYOffset = 0;
		if (level.getFlash()) {
			srcYOffset = 8;
		}
		
		for (int y = 0; y < Const.LEVEL_HEIGHT; y++) {
			
			for (int x = 0; x < Const.LEVEL_WIDTH; x++) {
				
				if(level.getTile(x, y) == Tile.BIG_DOT && level.isBigDotFlash()) continue;
				
				g.drawImage(images.levelTiles, x * Const.TILE_WIDTH + xOffset, y * Const.TILE_HEIGHT + yOffset, 
						x * Const.TILE_WIDTH + Const.TILE_WIDTH + xOffset, 
						y * Const.TILE_HEIGHT + Const.TILE_HEIGHT + yOffset,
						level.getTile(x, y).ordinal() * Const.TILE_WIDTH, srcYOffset,
						level.getTile(x, y).ordinal() * Const.TILE_WIDTH + Const.TILE_WIDTH, srcYOffset + Const.TILE_HEIGHT);
			}
		}
	}
	
	public static void renderPlayer(ImageManager images, Graphics g , Player player, Score score, LevelState levelState) {
		
		if (!player.isShouldRender()) return;
		
		int xOffset = -5 - 2 * Const.TILE_WIDTH;
		int yOffset = -5 + 3 * Const.TILE_HEIGHT;
		int x = (int)player.getX() + xOffset;
		int y = (int)player.getY() + yOffset;
		int w = Const.PLAYER_IMAGE_WIDTH;
		int h = Const.PLAYER_IMAGE_HEIGHT;
		int xFrame = player.xFrame;
		int yFrame = player.yFrame;
		Image pacmanImage = images.pacmanSprites;
		
		if (levelState == LevelState.DEATH_ANIM) {
			
			pacmanImage = images.pacmanDeath;
			yFrame = 0;
			
		}
		
		if (score.isNextLevelTransition()) {
			
			xFrame = 0;
		}

		g.drawImage(pacmanImage, x, y, 
				x + w, 
				y + h,
				xFrame * Const.TILE_WIDTH * 2, yFrame * Const.TILE_WIDTH * 2,
				xFrame * Const.TILE_WIDTH * 2 + w, yFrame * Const.TILE_WIDTH * 2 + h);
	}
	
	public static void renderGhost(ImageManager images, Graphics g , Ghost ghost, Score score) {
		
		if (!ghost.isShouldRender()) return;
		
		int xOffset = -5 - 2 * Const.TILE_WIDTH;
		int yOffset = -5 + 3 * Const.TILE_HEIGHT;
		int x = (int)ghost.getX() + xOffset;
		int y = (int)ghost.getY() + yOffset;
		int w = Const.PLAYER_IMAGE_WIDTH;
		int h = Const.PLAYER_IMAGE_HEIGHT;
		int xFrame = ghost.xFrame;
		
		if (score.isNextLevelTransition()) {
			
			xFrame = 0;
		}

		g.drawImage(images.ghostSprites, x, y, 
				x + w, 
				y + h,
				ghost.getXFrameOffset() * Const.TILE_WIDTH * 2 + xFrame * Const.TILE_WIDTH * 2, ghost.yFrame * Const.TILE_WIDTH * 2,
				ghost.getXFrameOffset() * Const.TILE_WIDTH * 2 + xFrame * Const.TILE_WIDTH * 2 + w, ghost.yFrame * Const.TILE_WIDTH * 2 + h);
	}
	
	public static void renderFruit(ImageManager images, Graphics g, Level level, FruitManager fruitManager, Score score) {
		
		if (!fruitManager.isFruitVisible()) return;
		
		int srcXOffset = fruitManager.getFruitType(score.getLevel()).ordinal();
		int srcYOffset = 0;
		if (fruitManager.getFruit1State() == FruitState.SCORE_DISPLAY || fruitManager.getFruit2State() == FruitState.SCORE_DISPLAY) {
			
			srcYOffset = 1;
		}
		
		int xOffset = -5 - 2 * Const.TILE_WIDTH;
		int yOffset = -5 + 3 * Const.TILE_HEIGHT;
		int x = Const.PLAYER_SPAWN_X + xOffset;
		int y = Const.PLAYER_SPAWN_Y + yOffset;
		
		g.drawImage(images.fruit, x, y, 
				x + Const.TILE_WIDTH * 2, 
				y + Const.TILE_HEIGHT * 2,
				srcXOffset * Const.TILE_WIDTH * 2, srcYOffset * Const.TILE_HEIGHT * 2,
				srcXOffset * Const.TILE_WIDTH * 2 + Const.TILE_HEIGHT * 2,
				srcYOffset * Const.TILE_HEIGHT * 2 + Const.TILE_HEIGHT * 2);
	}
	
	public static void renderFruitGUI(ImageManager images, Graphics g, FruitManager fruitManager) {
		
		ArrayDeque<FruitManager.FruitType> fruitStack = fruitManager.getFruitStack();
		
		int x = Const.WIDTH - Const.TILE_WIDTH * 4;
		int y = Const.HEIGHT - Const.TILE_HEIGHT * 2;
		int w = Const.TILE_WIDTH * 2;
		int h = Const.TILE_WIDTH * 2;

		Iterator<FruitManager.FruitType> it = fruitStack.descendingIterator();
		int srcXOffset = 0;
		
		for (int i = 0; i < fruitStack.size(); i++) {
			
			FruitManager.FruitType fruit = it.next();
			srcXOffset = fruit.ordinal();
			
			g.drawImage(images.fruit, x, y,
					x + w,
					y + w,
					srcXOffset * w, 0,
					srcXOffset * w + w,
					h);
			
			x -= Const.TILE_WIDTH * 2;
		}
	}
	
	public static void renderPlayerLives(ImageManager images, Graphics g, Score score) {
		
		int x = Const.TILE_WIDTH * 2;
		int y = Const.HEIGHT - Const.TILE_HEIGHT * 2;
		int w = Const.TILE_WIDTH * 2;
		int h = Const.TILE_WIDTH * 2;
		
		for (int i = 0; i < score.getLives(); i++) {
			
			g.drawImage(images.life, x, y, 
					x + w, 
					y + w,
					0, 0,
					w, 
					h);
			x += Const.TILE_WIDTH * 2;
		}
	}
	
	public static void renderText(ImageManager images, Graphics g, Score score, LevelState levelState, Flash upTextFlash) {
		
		// Render HI-SCORE
		int c = 8;
		int x = Const.TILE_WIDTH * 9;
		int y = 0;
		int w = Const.TILE_WIDTH;
		int h = Const.TILE_WIDTH;
		int srcY = Const.TILE_WIDTH;
		
		g.drawImage(images.text, x, y, 
				x + c * w, 
				y + h,
				0, srcY,
				c * w, 
				srcY + h);
		
		//Render 1UP
		if (upTextFlash.isFlash()) {
			
			x = Const.TILE_WIDTH * 3;
			c = 3;
			srcY = Const.TILE_WIDTH * 2;
			
			g.drawImage(images.text, x, y, 
					x + c * w, 
					y + h,
					0, srcY,
					c * w, 
					srcY + h);
		}
		
		//Render "READY!"
		if(levelState == LevelState.GAME_BEGIN || levelState == LevelState.LEVEL_BEGIN) {
			
			x = Const.TILE_WIDTH * 11;
			y = Const.TILE_WIDTH * 17 + Const.TILE_WIDTH * 3;
			c = 6;
			srcY = 0;
			
			g.drawImage(images.text, x, y, 
					x + c * w, 
					y + h,
					0, srcY,
					c * w, 
					srcY + h);
		}
		
		//PLAYER ONE
		if(levelState == LevelState.GAME_BEGIN) {
			
			x = Const.TILE_WIDTH * 10;
			y = Const.TILE_WIDTH * 11 + Const.TILE_WIDTH * 3;
			c = 8;
			srcY = 4 * Const.TILE_WIDTH;
			
			g.drawImage(images.text, x, y, 
					x + c * w, 
					y + h,
					0, srcY,
					c * w, 
					srcY + h);
		}
	}
}
