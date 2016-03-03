package pacman;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class SoundManager {
	Sound levelStart;
	
	
	SoundManager() {
	
		try {
			levelStart = new Sound(Const.RESOURCE_FOLDER + "level_start.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
