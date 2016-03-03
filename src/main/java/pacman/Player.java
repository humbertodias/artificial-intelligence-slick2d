package pacman;

import pacman.Level.Tile;

public class Player extends Mob {
	
	FruitManager fruitManager;
	
	Player(Level level, Score score, FruitManager fruitManager) {
		
		super(level, score);
		
		this.fruitManager = fruitManager;
		score.setNextLevelTransition(false);
	}
	
	public void update() {
		
		mobUpdate();
		
		if (getCurTile() == Tile.PLAYER_SPAWN && fruitManager.isFruitCollidable()) {
			fruitManager.eatFruit();
			score.addScore(fruitManager.getFruitValue(score.getLevel()));
		}
		
		if (getCurTile() == Tile.SMALL_DOT) {
			
			score.addScore(10);
			setCurTile(Tile.FLOOR);
			level.setDotCount(level.getDotCount() - 1);
		}
	}
	
	public boolean collectedBigDot() {
		
		if (getCurTile() == Tile.BIG_DOT) {
				
				score.addScore(50);
				setCurTile(Tile.FLOOR);
				level.setDotCount(level.getDotCount() - 1);
				return true;
		}
		return false;
	}
	
	public void spawn() {
		
		x = Const.PLAYER_SPAWN_X;
		y = Const.PLAYER_SPAWN_Y;
		oldX = Const.PLAYER_SPAWN_X;
		oldY = Const.PLAYER_SPAWN_Y;
	}

	public void setNextLevelTransition(boolean nextLevelTransition) {
		
		score.setNextLevelTransition(nextLevelTransition);
		moveDir = Direction.LEFT;
		nextMoveDir = Direction.LEFT;
		xFrame = 0;
	}
	
	public void move(Direction dir) {

		if (dir != Direction.NONE) {
			nextMoveDir = dir;
		}
	}
}
