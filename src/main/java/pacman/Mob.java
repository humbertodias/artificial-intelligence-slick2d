package pacman;

import pacman.Level.Tile;

public class Mob extends Animatable {

	Level level;
	Score score;
	
	float x, y;
	float oldX, oldY;
	protected float baseVel = 1.0f;
	protected float vel = 1.0f;
	
	protected Direction moveDir;
	protected Direction nextMoveDir;
	
	protected int tilesThatCollide = Tile.FLOOR.ordinal();
	protected boolean shouldRender;
	
	Mob(Level level, Score score) {
		
		setXMaxFrame(3);
		
		moveDir = Direction.LEFT;
		nextMoveDir = Direction.LEFT;
		
		yFrame = 0;
		xFrame = 0;
		
		this.level = level;
		this.score = score;
		this.shouldRender = false;
	}
	
	public int getGridCoord(float xy) {
		return (int)(xy + Const.TILE_WIDTH / 2) / Const.TILE_WIDTH;
	}
	
	protected Tile getCurTile() {
		
		int xx = getGridCoord(x);
		int yy = getGridCoord(y);
		return level.getTile(xx, yy);
	}
	
	protected Tile setCurTile(Tile tile) {
		
		int xx = (int)(x + Const.TILE_WIDTH / 2) / Const.TILE_WIDTH;
		int yy = (int)(y + Const.TILE_HEIGHT / 2) / Const.TILE_HEIGHT;
		return level.setTile(xx, yy, tile);
	}
	
	protected boolean difference180Degrees(Direction dir1, Direction dir2) {
		
		if (dir1 == Direction.NONE || dir2 == Direction.NONE) return false;
		
		if (dir1.ordinal() / 2 == dir2.ordinal() / 2) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean isTileClear(int x, int y) {
		
		return level.getTile(x, y).ordinal() >= tilesThatCollide;
	}
	
	protected boolean isRelTileClear(Direction dir) {
		
		if (dir == Direction.NONE) return false;
		
		int gridX = getGridCoord(x);
		int gridY = getGridCoord(y);
		
		switch(dir) {
		case UP:
			gridY -= 1;
			break;
		case DOWN:
			gridY += 1;
			break;
		case LEFT:
			gridX -= 1;
			break;
		case RIGHT:
			gridX += 1;
			break;
		default:
		}
		return isTileClear(gridX, gridY);
	}
	
	protected boolean isCurTileDifferent() {
		return getGridCoord(x + Const.TILE_WIDTH / 2) != getGridCoord(oldX + Const.TILE_WIDTH / 2) ||
				getGridCoord(y+ Const.TILE_WIDTH / 2) != getGridCoord(oldY+ Const.TILE_WIDTH / 2);
	}
	
	protected boolean crossedTileCentre() {
		
		float tileCenX = getGridCoord(x) * Const.TILE_WIDTH + Const.TILE_WIDTH / 2;
		float tileCenY = getGridCoord(y) * Const.TILE_WIDTH + Const.TILE_WIDTH / 2;
		
		float cenX = x + Const.TILE_WIDTH / 2;
		float cenY = y + Const.TILE_WIDTH / 2;
		float cenOX = oldX + Const.TILE_WIDTH / 2;
		float cenOY = oldY + Const.TILE_WIDTH / 2;
		
		boolean xGrt =  (cenX > tileCenX);
		boolean yGrt =  (cenY > tileCenY);
		boolean oxGrt =  (cenOX > tileCenX);
		boolean oyGrt =  (cenOY > tileCenY);
		
		return xGrt != oxGrt || yGrt != oyGrt;
	}
	
	protected void tryMove() {
		
		switch(moveDir) {
		case UP:
			y -= vel;
			break;
		case DOWN:
			y += vel;
			break;
		case LEFT:
			x -= vel;
			break;
		case RIGHT:
			x += vel;
			break;
		default:
		}
		
	}
	
	protected void turnCorner() {
		
		float xDiff = x - oldX;
		float yDiff = y - oldY;
		x = getGridCoord(x) * Const.TILE_WIDTH;
		y = getGridCoord(y) * Const.TILE_WIDTH;
		
		switch(nextMoveDir) {
		case UP:
			y -= Math.abs(xDiff);
			break;
		case DOWN:
			y += Math.abs(xDiff);
			break;
		case LEFT:
			y -= Math.abs(yDiff);
			break;
		case RIGHT:
			y += Math.abs(yDiff);
			break;
		default:
		}
	}
	
	protected Direction invertDirection(Direction dir) {
		
		switch(dir) {
		
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.LEFT;
		default:
			return Direction.NONE;
		}
	}
	
	protected boolean collidedWithWall() {
		
		boolean hasCollided = false;
		
		switch(moveDir) {
		case UP:
			hasCollided = (level.getTile((int)(x / Const.TILE_WIDTH), (int)(y / Const.TILE_WIDTH)).ordinal() < tilesThatCollide);
			break;
		case DOWN:
			hasCollided = (level.getTile((int)(x / Const.TILE_WIDTH), (int)((y + Const.TILE_WIDTH) / Const.TILE_WIDTH)).ordinal() < tilesThatCollide);
			break;
		case LEFT:
			hasCollided = (level.getTile((int)(x / Const.TILE_WIDTH), (int)(y / Const.TILE_WIDTH)).ordinal() < tilesThatCollide);
			break;
		case RIGHT:
			hasCollided = (level.getTile((int)((x + Const.TILE_WIDTH) / Const.TILE_WIDTH), (int)(y / Const.TILE_WIDTH)).ordinal() < tilesThatCollide);
			break;
		default:
		}
		
		return hasCollided;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Direction getDirection() {
		return moveDir;
	}
	
	public void mobUpdate() {
		
		if(score.isNextLevelTransition()) {
			
			return;
		}
		
		if (moveDir != nextMoveDir) {
			
			if(difference180Degrees(moveDir, nextMoveDir)) moveDir = nextMoveDir;
			else if(moveDir == Direction.NONE) {
				if(isRelTileClear(nextMoveDir)) {
					moveDir = nextMoveDir;
				}
			}
		}
		tryMove();
		
		if (x >= (Const.LEVEL_WIDTH - 1) * Const.TILE_WIDTH) {
			x = 0;
		}
		else if (x <= 0) {
			x = (Const.LEVEL_WIDTH - 1) * Const.TILE_WIDTH;
		}
		
		if(collidedWithWall()) {
			
			x = getGridCoord(oldX) * Const.TILE_WIDTH;
			y = getGridCoord(oldY) * Const.TILE_WIDTH;
			moveDir = Direction.NONE;
		}
		
		if (moveDir != nextMoveDir && nextMoveDir != Direction.NONE) {
			
			if((crossedTileCentre()) && isRelTileClear(nextMoveDir) && !difference180Degrees(moveDir, nextMoveDir)) {
				
				turnCorner();
				moveDir = nextMoveDir;
			}
		}

		if(moveDir != Direction.NONE) {
			
			animationUpdate();
			yFrame = moveDir.ordinal();
		}
		
		if (isCurTileDifferent()) {
			nextMoveDir = Direction.NONE;
		}
		
		oldX = x;
		oldY = y;
		//System.out.println("nexMov" + nextMoveDir + "movDir" + moveDir + "x" + x + "y" + y + "gridX" + (x - x % Const.TILE_WIDTH) + "gridY" + (y - y % Const.TILE_HEIGHT));
	}
	
	boolean canMoveInDir(int xx, int yy, Direction newMoveDir) {
		return level.getTile((int)(x / Const.TILE_WIDTH + xx), (int)(y / Const.TILE_HEIGHT + yy)).ordinal() >= tilesThatCollide && newMoveDir != Direction.NONE;
	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}
	
}
