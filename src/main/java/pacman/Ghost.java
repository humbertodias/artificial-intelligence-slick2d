package pacman;

import java.util.ArrayList;
import java.util.Random;

import pacman.Level.Tile;

public class Ghost extends Mob {

	public enum GhostName {
		BLINKY,
		INKY,
		PINKY,
		CLYDE
	}
	
	public enum AIState {
		CHASE,
		SCATTER,
	}
	
	public enum HouseState {
		ENTERING,
		INSIDE,
		EXITING
	}
	
	protected int xFrameOffset;
	
	IntPair scatterPos;
	IntPair chasePos;
	protected static final IntPair gatePos = new IntPair(15, 11);
	public IntPair goalPos;
	
	IntPair lastTileCoords;
	Direction direction;
	
	protected int initX;
	protected int initY;
	
	protected int dotCount;
	protected int dotLimit;
	
	protected boolean isFrightened;
	protected boolean isDead;
	protected boolean inHouse;
	protected static boolean areScared;
	protected AIState aiState;
	protected GhostName ghostName;
	protected HouseState houseState;
	protected Ghost blinkyRef;
	
	Player player;

	
	Ghost(Level level, Score score, Player player, GhostName ghostName) {
		
		super(level, score);
		
		this.player = player;
		aiState = AIState.CHASE;
		houseState = HouseState.ENTERING;
		lastTileCoords = new IntPair();
		goalPos = new IntPair();
		direction = Direction.NONE;
		
		setXMaxFrame(1);
		setXStepsPerFrame(4);
		this.xFrameOffset = ghostName.ordinal() * 2;
		
		this.ghostName = ghostName;
		if (ghostName == GhostName.BLINKY) {
			
			this.initX = Const.PLAYER_SPAWN_X;
			this.initY = Const.PLAYER_SPAWN_Y - 12 * Const.TILE_HEIGHT;
		} else if (ghostName == GhostName.PINKY) {
			
			this.initX = Const.PLAYER_SPAWN_X;
			this.initY = Const.PLAYER_SPAWN_Y - 9 * Const.TILE_HEIGHT;
			this.inHouse = true;
			
		} else if (ghostName == GhostName.INKY) {
			
			this.initX = Const.PLAYER_SPAWN_X - 2 * Const.TILE_WIDTH;
			this.initY = Const.PLAYER_SPAWN_Y - 9 * Const.TILE_HEIGHT;
			this.inHouse = true;
		} else if (ghostName == GhostName.CLYDE) {
			
			this.initX = Const.PLAYER_SPAWN_X + 2 * Const.TILE_WIDTH;
			this.initY = Const.PLAYER_SPAWN_Y - 9 * Const.TILE_HEIGHT;
			this.inHouse = true;
		}
		
		switch(ghostName) {
		
		case BLINKY:
			scatterPos = new IntPair(Const.LEVEL_WIDTH - 5, -1);
			break;
		case INKY:
			scatterPos = new IntPair(Const.LEVEL_WIDTH - 3, Const.LEVEL_HEIGHT);
			break;
		case PINKY:
			scatterPos = new IntPair(4, -1);
			break;
		case CLYDE:
			scatterPos = new IntPair(2, Const.LEVEL_HEIGHT);
			break;
		}
	}
	
	public void setAIState(AIState aiState) {
		
		this.aiState = aiState;
	}
	
	protected int dist(double x1, double y1, double x2, double y2) {
		
		return (int)Math.sqrt((Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2)));
	}
	
	protected Direction pathfind() {
		
		int minDist = Integer.MAX_VALUE;
		Direction minDir = Direction.NONE;
		Direction curDir = Direction.NONE;
		ArrayList<Direction> dirOptions = new ArrayList<Direction>();
		
		for (int i = 0; i < 4; i++) {
			
			//Set x y to that of left, right, up or down node
			int xDif = 0;
			int yDif = 0;
			switch(Direction.values()[i]) {
			case UP:
				if (direction == Direction.DOWN) continue;
				curDir = Direction.UP;
				yDif = -1;
				break;
			case LEFT:
				if (direction == Direction.RIGHT) continue;
				curDir = Direction.LEFT;
				xDif = -1;
				break;
			case DOWN:
				if (direction == Direction.UP) continue;
				curDir = Direction.DOWN;
				yDif = 1;
				break;
			case RIGHT:
				if (direction == Direction.LEFT) continue;
				curDir = Direction.RIGHT;
				xDif = 1;
				break;
			default:
				break;
			}
			
			IntPair curTile = new IntPair(getGridCoord(x) + xDif, getGridCoord(y) + yDif);
			
			//if tile solid
			if (level.getTile(curTile.x, curTile.y).ordinal() < tilesThatCollide) {
				
				continue;
			}
			
			if (isFrightened) {
				dirOptions.add(curDir);
			}
			
			if (dist(goalPos.x, goalPos.y, curTile.x, curTile.y) < minDist) {
				
				minDist = dist(goalPos.x, goalPos.y, curTile.x, curTile.y);
				minDir = Direction.values()[curDir.ordinal()];
			}
		}
		
		if (isFrightened && dirOptions.size() > 0) {
			
			Random rand = new Random();
			return dirOptions.get(rand.nextInt(dirOptions.size()));
		}
		
		return minDir;
	}
	
	protected void setPinkyGoal() {
		
		int difX = 0;
		int difY = 0;
		switch(Direction.values()[player.yFrame]) {
		
		case UP:
			difX = -4;
			difY = -4;
			break;
		case DOWN:
			difY = 4;
			break;
		case LEFT:
			difX = -4;
			break;
		case RIGHT:
			difX = 4;
			break;
		default:
			break;
		}
		
		goalPos.x = getGridCoord(player.getX()) + difX;
		goalPos.y = getGridCoord(player.getY()) + difY;
	}
	
	protected void setInkyGoal() {
		
		int difX = 0;
		int difY = 0;
		switch(Direction.values()[player.yFrame]) {
		
		case UP:
			difX = -2;
			difY = -2;
			break;
		case DOWN:
			difY = 2;
			break;
		case LEFT:
			difX = -2;
			break;
		case RIGHT:
			difX = 2;
			break;
		default:
			break;
		}
		
		goalPos.x = getGridCoord(blinkyRef.x) + (((getGridCoord(player.getX()) + difX) - getGridCoord(blinkyRef.x)) * 2);
		goalPos.y = getGridCoord(blinkyRef.y) + (((getGridCoord(player.getY()) + difY) - getGridCoord(blinkyRef.y)) * 2);
	}
	
	protected void setClydeGoal() {
		
		if (dist(x, y, player.getX(), player.getY()) > 8 * Const.TILE_WIDTH) {
			
			goalPos.x = getGridCoord(player.getX());
			goalPos.y = getGridCoord(player.getY());
		} else {
			
			goalPos.x = scatterPos.x;
			goalPos.y = scatterPos.y;
		}
	}
	
	protected void setChaseGoal() {
		
		switch(ghostName) {
		
		case BLINKY:
			
			goalPos.x = getGridCoord(player.getX());
			goalPos.y = getGridCoord(player.getY());
			break;
		case PINKY:
			
			setPinkyGoal();
			break;
		case INKY:
			
			setInkyGoal();
			break;
		case CLYDE:
			
			setClydeGoal();
			break;
		default:
			break;
		}
	}
	
	public static boolean areScared() {
		return areScared;
	}
	
	public void scare() {
		
		if (isFrightened || isDead) {
			return;
		}
		
		isFrightened = true;
		areScared = true;
		xFrameOffset = 8;
		vel = baseVel / 2; 
		
		direction = invertDirection(direction);
		direction = pathfind();
	}
	
public void unscare() {
		
		if (isDead) return;
	
		isFrightened = false;
		areScared = false;
		xFrameOffset = ghostName.ordinal() * 2;
		vel = baseVel; 
	}
	
	public void scatter() {
		
		if (isDead) return;
		
		direction = invertDirection(direction);
		direction = pathfind();
		aiState = AIState.SCATTER;
	}
	
	public void chase() {
		
		if (isDead) return;
		
		direction = invertDirection(direction);
		direction = pathfind();
		aiState = AIState.CHASE;
	}
	
	public void setBlinkyRef(Ghost blinky) {
		
		this.blinkyRef = blinky;
	}
	
	protected void houseMoveEntering() {
		
		if (y < Const.HOUSE_Y_CENTRE) {
			
			if (x < Const.HOUSE_X_CENTRE) {
				yFrame = Direction.RIGHT.ordinal();
				x += getVel();
				if (x > Const.HOUSE_X_CENTRE) {
					x = Const.HOUSE_X_CENTRE;
				}
				
			} else if (x > Const.HOUSE_X_CENTRE) {
				yFrame = Direction.LEFT.ordinal();
				x -= getVel();
				if (x < Const.HOUSE_X_CENTRE) {
					x = Const.HOUSE_X_CENTRE;
				}
			}
			
			if (x == Const.HOUSE_X_CENTRE) {
				yFrame = Direction.DOWN.ordinal();
				y += getVel();
			}
		} else {
			
			switch (ghostName) {
			
			case BLINKY:
				unkill();
				houseState = HouseState.EXITING;
				break;
			case PINKY:
				houseState = HouseState.INSIDE;
				direction = Direction.UP;
				break;
			case INKY:
				if (x > initX) {
					yFrame = Direction.LEFT.ordinal();
					x -= getVel();
					if  (x < initX) {
						x = initX;
					}
				} else {
					houseState = HouseState.INSIDE;
					direction = Direction.UP;
				}
				break;
			case CLYDE:
				if (x < initX) {
					yFrame = Direction.RIGHT.ordinal();
					x += getVel();
					if  (x > initX) {
						x = initX;
					}
				} else {
					houseState = HouseState.INSIDE;
					direction = Direction.UP;
				}
				break;
			}
		}
	}
	
	protected void houseMoveExiting() {
		
		if (x != Const.HOUSE_X_CENTRE) {
			
			if (x < Const.HOUSE_X_CENTRE) {
				yFrame = Direction.RIGHT.ordinal();
				x += getVel();
				if  (x > Const.HOUSE_X_CENTRE) {
					x = Const.HOUSE_X_CENTRE;
				}
			} else {
				yFrame = Direction.LEFT.ordinal();
				x -= getVel();
				if  (x < Const.HOUSE_X_CENTRE) {
					x = Const.HOUSE_X_CENTRE;
				}
			}
			
		} else {
			yFrame = Direction.UP.ordinal();
			y -= getVel();
			if (y <= Const.HOUSE_Y_CENTRE - 3 * Const.TILE_WIDTH) {
				y = Const.HOUSE_Y_CENTRE - 3 * Const.TILE_WIDTH;
				inHouse = false;
				vel = baseVel;
			}
		}
	}
	
	protected void houseMoveInside() {
		
		if (direction == Direction.UP) {
			y -= getVel();
			if (y < Const.HOUSE_TOP_BOUND) {
				y = Const.HOUSE_TOP_BOUND;
				direction = Direction.DOWN;
			}
		} else {
			y += getVel();
			if (y > Const.HOUSE_BOT_BOUND) {
				y = Const.HOUSE_BOT_BOUND;
				direction = Direction.UP;
			}
		}
		yFrame = direction.ordinal();
	}
	
	protected void houseMove() {
		
		switch(houseState) {
		
		case ENTERING:
			
			houseMoveEntering();
		case INSIDE:
			
			houseMoveInside();
			break;
		case EXITING:
			
			houseMoveExiting();
			break;
		}
	}
	
	public void move() {
		
		if (inHouse) return;
		
		switch(aiState) {
		
		case CHASE:
			
			setChaseGoal();	
			break;
		case SCATTER:
			goalPos.x = scatterPos.x;
			goalPos.y = scatterPos.y;
			break;
		}
		
		if (isDead) {
			goalPos.x = gatePos.x;
			goalPos.y = gatePos.y;
		}
		
		// if current tile different from last
		if (!(getGridCoord(x) == lastTileCoords.x && getGridCoord(y) == lastTileCoords.y)) {
			
			direction = pathfind();
			lastTileCoords.x = getGridCoord(x);
			lastTileCoords.y = getGridCoord(y);
		}
		
		nextMoveDir = direction;
	}
	
	public void spawn() {
		
		x = initX;
		y = initY;
		oldX = x;
		oldY = y;
		
		lastTileCoords.x = getGridCoord(x);
		lastTileCoords.x = getGridCoord(y);
	}
	
	public void newLevelStart() {
		
		dotCount = 0;
		
		switch (ghostName) {
		
		case BLINKY:
			dotLimit = 0;
			inHouse = false;
			break;
		case PINKY:
			dotLimit = 0;
			inHouse = true;
			break;
		case INKY:
			if (score.getLevel() == 0) {
				dotLimit = 30;
			} else {
				dotLimit = 0;
			}
			inHouse = true;
			break;
		case CLYDE:
			if (score.getLevel() == 0) {
				dotLimit = 60;
			} else if (score.getLevel() == 1) {
					dotLimit = 50;
			} else {
				dotLimit = 0;
			}
			inHouse = true;
			break;
		}
	}
	
	public void kill() {
		
		setDead(true);
		xFrameOffset = 6 * 2;
		vel = baseVel * 2;
	}
	
	public void unkill() {
		
		setDead(false);
		xFrameOffset = ghostName.ordinal() * 2;
		vel = baseVel;
	}
	
	public void update() {
		
		if (inHouse == true) {
			
			if (dotCount >= dotLimit) {
				houseState = HouseState.EXITING;
			}
			
			houseMove();
			animationUpdate();
			return;
		}
		
		mobUpdate();
		
		// if dead ghosts is above gate
		if (isDead && level.getTile(getGridCoord(x), getGridCoord(y) + 1) == Tile.GATE) {
			
			inHouse = true;
			houseState = HouseState.ENTERING;
		}
	}
	
	public boolean isFrightened() {
		return isFrightened;
	}
	
	public int getXFrameOffset() {
		
		return xFrameOffset;
	}
	
	public boolean isDead() {
		
		return isDead;
	}
	
	public float getVel() {
		
		if (inHouse) {
			vel = baseVel / 3;
		} else if (isDead) {
			vel = baseVel * 2;
		}
		
		return vel;
	}
	
	public void setDead(boolean isDead) {
		this.isFrightened = false;
		this.isDead = isDead;
	}
}
