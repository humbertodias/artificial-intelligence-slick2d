package pacman;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageManager {
	Image mainMenuBack;
	Image levelTiles;
	Image pacmanSprites;
	Image pacmanDeath;
	Image ghostSprites;
	Image numbers;
	Image fruit;
	Image life;
	Image text;
	
	ImageManager() {
		
		try {
			mainMenuBack = new Image(Const.RESOURCE_FOLDER + "main_menu.png");
			mainMenuBack.setFilter(Image.FILTER_NEAREST);
			levelTiles = new Image(Const.RESOURCE_FOLDER + "tiles.png");
			levelTiles.setFilter(Image.FILTER_NEAREST);
			pacmanSprites = new Image(Const.RESOURCE_FOLDER + "pacman.png");
			pacmanSprites.setFilter(Image.FILTER_NEAREST);
			pacmanDeath = new Image(Const.RESOURCE_FOLDER + "pac_death.png");
			pacmanDeath.setFilter(Image.FILTER_NEAREST);
			ghostSprites = new Image(Const.RESOURCE_FOLDER + "ghosts.png");
			ghostSprites.setFilter(Image.FILTER_NEAREST);
			numbers = new Image(Const.RESOURCE_FOLDER + "nums.png");
			numbers.setFilter(Image.FILTER_NEAREST);
			fruit = new Image(Const.RESOURCE_FOLDER + "fruit.png");
			fruit.setFilter(Image.FILTER_NEAREST);
			life = new Image(Const.RESOURCE_FOLDER + "life.png");
			life.setFilter(Image.FILTER_NEAREST);
			text = new Image(Const.RESOURCE_FOLDER + "text.png");
			text.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
