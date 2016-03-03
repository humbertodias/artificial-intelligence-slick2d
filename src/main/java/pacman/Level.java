package pacman;


public class Level {

	public enum Tile { 

		LV_THIN_WALL,
		TL_THIN_WALL,
		TH_THIN_WALL,
		TR_THIN_WALL,
		RV_THIN_WALL,
		BR_THIN_WALL,
		BH_THIN_WALL,
		BL_THIN_WALL,
		
		TL_THIN_TO_THICK,
		TR_THIN_TO_THICK,
		RT_THIN_TO_THICK,
		RB_THIN_TO_THICK,
		LT_THIN_TO_THICK,
		LB_THIN_TO_THICK,
		
		LV_THICK_WALL,
		TL_THICK_WALL,
		TH_THICK_WALL,
		TR_THICK_WALL,
		RV_THICK_WALL,
		BR_THICK_WALL,
		BH_THICK_WALL,
		BL_THICK_WALL,
		TLI_THICK_WALL,
		TRI_THICK_WALL,
		BRI_THICK_WALL,
		BLI_THICK_WALL,
		
		LV_SQUARE_WALL,
		TL_SQUARE_WALL,
		TH_SQUARE_WALL,
		TR_SQUARE_WALL,
		RV_SQUARE_WALL,
		BR_SQUARE_WALL,
		BH_SQUARE_WALL,
		BL_SQUARE_WALL,
		LE_SQUARE_WALL,
		RE_SQUARE_WALL,
		
		SOLID_FLOOR,
		GATE,
		FLOOR,
		SMALL_DOT,
		BIG_DOT,
		PLAYER_SPAWN
	}
	
	private Tile tiles[];
	private boolean levelFlash;
	private boolean isFlashing;
	private int flashTicks;
	
	private int initDotCount;
	private int dotCount;
	
	private int bigDotFlashTicks;
	private boolean bigDotFlash;
	private boolean bigDotFlashPause;
	
	
	Level() {

		tiles = new Tile[Const.LEVEL_WIDTH * Const.LEVEL_HEIGHT];
		levelFlash = false;
		setFlashing(false);
		flashTicks = 0;
		
		bigDotFlash = false;
	}
	
	public int countsDots() {
		
		int count = 0;
		
		for (int i = 0; i < tiles.length; i++) {
			
			if (tiles[i] == Tile.SMALL_DOT || tiles[i] == Tile.BIG_DOT) {
				
				count++;
			}
		}
		return count;
	}
	
	private Tile charToTile(char c) {
		
		Tile retTile = Tile.FLOOR;
		
		switch(c) {
		
		case 'a':
			retTile = Tile.values()[0];
			break;
		case 'q':
			retTile = Tile.values()[1];
			break;
		case 'w':
			retTile = Tile.values()[2];
			break;
		case 'e':
			retTile = Tile.values()[3];
			break;
		case 'd':
			retTile = Tile.values()[4];
			break;
		case 'c':
			retTile = Tile.values()[5];
			break;
		case 'x':
			retTile = Tile.values()[6];
			break;
		case 'z':
			retTile = Tile.values()[7];
			break;

		case 'i':
			retTile = Tile.values()[8];
			break;
		case 'I':
			retTile = Tile.values()[9];
			break;
		case 'l':
			retTile = Tile.values()[10];
			break;
		case 'L':
			retTile = Tile.values()[11];
			break;
		case 'j':
			retTile = Tile.values()[12];
			break;
		case 'J':
			retTile = Tile.values()[13];
			break;
			
		case 'A':
			retTile = Tile.values()[14];
			break;
		case 'Q':
			retTile = Tile.values()[15];
			break;
		case 'W':
			retTile = Tile.values()[16];
			break;
		case 'E':
			retTile = Tile.values()[17];
			break;
		case 'D':
			retTile = Tile.values()[18];
			break;
		case 'C':
			retTile = Tile.values()[19];
			break;
		case 'X':
			retTile = Tile.values()[20];
			break;
		case 'Z':
			retTile = Tile.values()[21];
			break;
		case 'R':
			retTile = Tile.values()[22];
			break;
		case 'Y':
			retTile = Tile.values()[23];
			break;
		case 'N':
			retTile = Tile.values()[24];
			break;
		case 'V':
			retTile = Tile.values()[25];
			break;
		
			
		case 'f':
			retTile = Tile.values()[26];
			break;
		case 'r':
			retTile = Tile.values()[27];
			break;
		case 't':
			retTile = Tile.values()[28];
			break;
		case 'y':
			retTile = Tile.values()[29];
			break;
		case 'h':
			retTile = Tile.values()[30];
			break;
		case 'n':
			retTile = Tile.values()[31];
			break;
		case 'b':
			retTile = Tile.values()[32];
			break;
		case 'v':
			retTile = Tile.values()[33];
			break;
			
			
		case 'T':
			retTile = Tile.values()[34];
			break;
		case 'B':
			retTile = Tile.values()[35];
			break;
			
		case '$':
			retTile = Tile.values()[36];
			break;
		case 'g':
			retTile = Tile.values()[37];
			break;
		case '.':
			retTile = Tile.values()[38];
			break;
		case '`':
			retTile = Tile.values()[39];
			break;
		case '@':
			retTile = Tile.values()[40];
			break;
		case 'p':
			retTile = Tile.values()[41];
			break;
		}
		
		return retTile;
	}
	
	public void loadLevel(String data) {
	
		data = data.replaceAll("[\\n]", "");
		
		for (int i = 0; i < Const.LEVEL_WIDTH * Const.LEVEL_HEIGHT; i++) {
			tiles[i] = charToTile(data.charAt(i));
		}
	}
	
	public void setFlash(boolean flash) {
		
		levelFlash = flash;
	}
	
	public void setBigDotFlashPause(boolean flashPause) {
		
		bigDotFlashPause = flashPause;
	}

	public void resetLevel() {
		
		loadLevel(Const.levelData);
		setDotCount(countsDots());
		initDotCount = dotCount;
	}
	
	public void update() {
		
		if (isFlashing) {
			flashTicks++;

			if (flashTicks == 10) {
				flashTicks = 0;
				levelFlash = !levelFlash;
			}
		}
		
		bigDotFlashTicks++;

		if (bigDotFlashTicks == 12) {
			bigDotFlashTicks = 0;
			bigDotFlash = !bigDotFlash;
		}
	}
	
	public boolean isBigDotFlash() {
		
		if (bigDotFlashPause) {
			return false;
		}
		
		return bigDotFlash;
	}
	
	public boolean getFlash() {
		
		return levelFlash;
	}
	
	public Tile[] getTiles() {
		
		return tiles;
	}
	
	public Tile getTile(int i) {
		
		return tiles[i];
	}
	
	public Tile getTile(int x, int y) {
		
		return tiles[y * Const.LEVEL_WIDTH + x];
	}
	
	public Tile setTile(int x, int y, Tile tile) {
		
		return tiles[y * Const.LEVEL_WIDTH + x] = tile;
	}

	public int getInitDotCount() {
		
		return initDotCount;
	}
	
	public int getDotCount() {
		return dotCount;
	}

	public void setDotCount(int dotCount) {
		this.dotCount = dotCount;
	}

	public boolean isFlashing() {
		return isFlashing;
	}

	public void setFlashing(boolean isFlashing) {
		this.isFlashing = isFlashing;
		levelFlash = false;
	}
}
