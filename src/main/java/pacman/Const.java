package pacman;

import org.newdawn.slick.Input;

public class Const {
	static final String RESOURCE_FOLDER = "pacman/";
	static final int WIDTH = 224;
	static final int HEIGHT = 288;
	static final float SCALE = 2f;
	
	static final int MAX_FPS = 60;
	
	static final int LEVEL_WIDTH = 32;
	static final int LEVEL_HEIGHT = 31;
	static final int TILE_WIDTH = 8;
	static final int TILE_HEIGHT = TILE_WIDTH;
	
	static final int PLAYER_SPAWN_X = 125;
	static final int PLAYER_SPAWN_Y = 184;
	static final int PLAYER_IMAGE_WIDTH = 16;
	static final int PLAYER_IMAGE_HEIGHT = 16;
	
	static final int MAX_SCORE_DIGITS = 6;
	
	static final long BASE_FRUIT_TIME = 10000000000l; //10 seconds
	static final long SCORE_FRUIT_TIME = 2000000000l;
	
	static final int CHERRY_SCORE = 100;
	static final int STRAWBERRY_SCORE = 300;
	static final int ORANGE_SCORE = 500;
	static final int BANANA_SCORE = 700;
	static final int APPLE_SCORE = 1000;
	static final int GRAPES_SCORE = 2000;
	static final int MANGO_SCORE = 3000;
	static final int TROPHY_SCORE = 5000;
	
	static final int SCORE_UNTIL_LIFE = 10000;
	static final String SCORE_FILE_PATH = RESOURCE_FOLDER + "score.dat";
	
	// Key-bindings
	static final int KEY_UP = Input.KEY_UP;
	static final int KEY_DOWN = Input.KEY_DOWN;
	static final int KEY_LEFT = Input.KEY_LEFT;
	static final int KEY_RIGHT = Input.KEY_RIGHT;
	
	static final int HOUSE_Y_CENTRE = 14 * Const.TILE_WIDTH;
	static final int HOUSE_X_CENTRE = (Const.LEVEL_WIDTH * Const.TILE_WIDTH) / 2 - 3;
	static final int HOUSE_TOP_BOUND = (int)(13.5 * Const.TILE_WIDTH);
	static final int HOUSE_BOT_BOUND = (int)(14.5 * Const.TILE_WIDTH);
	
	static final String levelData = 
"..qwwwwwwwwwwwwiIwwwwwwwwwwwwe..\n" +
"..a````````````AD````````````d..\n" +
"..a`QWWE`QWWWE`AD`QWWWE`QWWE`d..\n" +
"..a@A..D`A...D`AD`A...D`A..D@d..\n" +
"..a`ZXXC`ZXXXC`ZC`ZXXXC`ZXXC`d..\n" +
"..a``````````````````````````d..\n" +
"..a`QWWE`QE`QWWWWWWE`QE`QWWE`d..\n" +
"..a`ZXXC`AD`ZXXYRXXC`AD`ZXXC`d..\n" +
"..a``````AD````AD````AD``````d..\n" +
"..zxxxxE`AVWWE.AD.QWWND`Qxxxxc..\n" +
".......a`ARXXC.ZC.ZXXYD`d.......\n" +
".......a`AD..........AD`d.......\n" +
".......a`AD.rtTggBty.AD`d.......\n" +
"wwwwwwwC`ZC.f......h.ZC`Zwwwwwww\n" +
"..```````...f......h...```````..\n" +
"xxxxxxxE`QE.f......h.QE`Qxxxxxxx\n" +
".......a`AD.vbbbbbbn.AD`d.......\n" +
".......a`AD..........AD`d.......\n" +
".......a`AD.QWWWWWWE.AD`d.......\n" +
"..qwwwwC`ZC.ZXXYRXXC.ZC`Zwwwwe..\n" +
"..a````````````AD````````````d..\n" +
"..a`QWWE`QWWWE`AD`QWWWE`QWWE`d..\n" +
"..a`ZXYD`ZXXXC`ZC`ZXXXC`ARXC`d..\n" +
"..a@``AD```````pp```````AD``@d..\n" +
"..jWE`AD`QE`QWWWWWWE`QE`AD`QWl..\n" +
"..JXC`ZC`AD`ZXXYRXXC`AD`ZC`ZXL..\n" +
"..a``````AD````AD````AD``````d..\n" +
"..a`QWWWWNVWWE`AD`QWWNVWWWWE`d..\n" +
"..a`ZXXXXXXXXC`ZC`ZXXXXXXXXC`d..\n" +
"..a``````````````````````````d..\n" +
"..zxxxxxxxxxxxxxxxxxxxxxxxxxxc..";
}
