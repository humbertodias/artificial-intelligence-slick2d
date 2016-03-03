package spaceinvaders.managers;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TextureManager {
	private String resourceFolder = "spaceinvaders/";
	private Image sprites;
	private Image[][] spriteArr;
	
	public static final int LASER = 0;
	public static final int SHELTER = 1;
	public static final int LARGE_INVADER = 2;
	public static final int MEDIUM_INVADER = 3;
	public static final int SMALL_INVADER = 4;
	public static final int UFO = 5;
	public static final int PLAYER_EXPLOSION = 6;
	public static final int INVADER_EXPLOSION = 7;
	public static final int PLAYER_LASER = 8;
	public static final int INVADER_LASER = 9;
	public static final int INVADER_POWERFUL = 10;
	
	public TextureManager() {
		try {
			sprites = new Image(resourceFolder + "textures/sprites.png");
			
			spriteArr = new Image[11][];
			for(int i = 0; i < 11; i++)
				spriteArr[i] = new Image[2];
			
			spriteArr[LASER] = new Image[1];
			spriteArr[LASER][0] = sprites.getSubImage(76, 36, 26, 16);
			
			spriteArr[SHELTER] = new Image[1];
			spriteArr[SHELTER][0] = sprites.getSubImage(16, 30, 44, 32);
			
			spriteArr[LARGE_INVADER][0] = sprites.getSubImage(46, 6, 24, 16);
			spriteArr[LARGE_INVADER][1] = sprites.getSubImage(76, 6, 24, 16);
			
			spriteArr[MEDIUM_INVADER][0] = sprites.getSubImage(106, 6, 22, 16);
			spriteArr[MEDIUM_INVADER][1] = sprites.getSubImage(134, 6, 22, 16);
			
			spriteArr[SMALL_INVADER][0] = sprites.getSubImage(162, 6, 16, 16);
			spriteArr[SMALL_INVADER][1] = sprites.getSubImage(186, 6, 16, 16);
			
			spriteArr[UFO] = new Image[1];
			spriteArr[UFO][0] = sprites.getSubImage(8, 8, 32, 14);
			
			spriteArr[PLAYER_EXPLOSION][0] = sprites.getSubImage(110, 36, 30, 16);
			spriteArr[PLAYER_EXPLOSION][1] = sprites.getSubImage(142, 36, 32, 16);
			
			spriteArr[INVADER_EXPLOSION] = new Image[1];
			spriteArr[INVADER_EXPLOSION][0] = sprites.getSubImage(208, 6, 26, 16);
			
			spriteArr[PLAYER_LASER] = new Image[1];
			spriteArr[PLAYER_LASER][0] = sprites.getSubImage(66, 42, 2, 8);
			
			spriteArr[INVADER_LASER] = new Image[1];
			spriteArr[INVADER_LASER][0] = sprites.getSubImage(194, 40, 2, 12);
			
			spriteArr[INVADER_POWERFUL][0] = sprites.getSubImage(204, 38, 6, 14);
			spriteArr[INVADER_POWERFUL][1] = sprites.getSubImage(214, 38, 6, 14);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Animation getAnimatedTexture(int type, int duration) {
		return new Animation(spriteArr[type], duration);
	}
	
	public Image getTexture(int type) {
		return spriteArr[type][0];
	}
	
}
