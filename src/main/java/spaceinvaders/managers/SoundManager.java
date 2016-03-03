package spaceinvaders.managers;

import org.newdawn.slick.Music;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundManager {
	private static final String resourceFolder = "spaceinvaders/";
	public static final int EXPLOSION = 0;
	public static final int INVADER1 = 1;
	public static final int INVADER2 = 2;
	public static final int INVADER3 = 3;
	public static final int INVADER4 = 4;
	public static final int INVADER_KILLED = 5;
	public static final int SHOOT = 6;
	public static final int UFO = 7;
	public static final int MUSIC = 8;
	
	private static Audio[] sounds;
	private static Music backgroundMusic;
	
	public SoundManager() {
		init();
	}
	
	public static void init() {
		sounds = new Audio[8];
		try {
			sounds[EXPLOSION] = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/explosion.wav"));
			sounds[INVADER1] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/fastinvader1.wav"));
			sounds[INVADER2] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/fastinvader2.wav"));
			sounds[INVADER3] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/fastinvader3.wav"));
			sounds[INVADER4] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/fastinvader4.wav"));
			sounds[INVADER_KILLED] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/invaderkilled.wav"));
			sounds[SHOOT] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/shoot.wav"));
			sounds[UFO] = AudioLoader.getAudio("WAV", 
					ResourceLoader.getResourceAsStream(resourceFolder + "sound/ufo.wav"));
			
			backgroundMusic = new Music(resourceFolder + "music/bg-music.ogg");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Audio getSound(int sound) {
		return sounds[sound];
	}
	
	public static void playSound(int sound) {
		sounds[sound].playAsSoundEffect(1.f, 1.f, false);
	}
	
	public static void playSound(int sound, float volume) {
		sounds[sound].playAsSoundEffect(1.f, volume, false);
	}
	
	public static void playSound(int sound, float volume, boolean loop) {
		sounds[sound].playAsSoundEffect(1.f, volume, loop);
	}
	
	public static void stopSound(int sound) {
		sounds[sound].stop();
	}
	
	public static void restartMusic() {
		stopMusic();
		startMusic();
	}
	
	public static void stopAll() {
		stopMusic();
		for(Audio a : sounds) {
			a.stop();
		}
	}
	
	public static void startMusic() {
		backgroundMusic.loop(1.f, 0.8f);
	}
	
	public static void stopMusic() {
		backgroundMusic.stop();
	}
	
}
