package invaders.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceinvaders.Main;
import spaceinvaders.entities.Drawable;
import spaceinvaders.entities.Invader;
import spaceinvaders.entities.LargeInvader;
import spaceinvaders.entities.Laser;
import spaceinvaders.entities.MediumInvader;
import spaceinvaders.entities.PlayerLaser;
import spaceinvaders.entities.PlayerObject;
import spaceinvaders.entities.RemainingLives;
import spaceinvaders.entities.SmallInvader;
import spaceinvaders.entities.Text;
import spaceinvaders.entities.UFO;
import spaceinvaders.managers.SoundManager;
import spaceinvaders.managers.TextureManager;

public class GameState extends BasicGameState {
	
	public static boolean invaderWait = false;
	public static int invaderSpeed;
	public static int invaderDirection;
	public static int invaderDown = 0;
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	
	public static final float upBorder = 70;
	public static final float bottomBorder = 500;
	public static final float leftBorder = 20;
	public static final float rightBorder = 580;
	
	public static int animSpeed;
	public static float gameSpeed;
	private static final float speedTick = 0.08f;
	private static final int baseAnimSpeed = 500;
	
	public static final float laserSpeed = 0.4f;
	
	public static TextureManager texMan;
	public static SoundManager soundMan;
	
	private Text scoreText;
	private Text scoreValueText;
	private Text highScoreText;
	private Text hScoreValueText;
	private Text remainingLivesText;
	
	public static boolean playHandle = true;
	
	public static int score;
	public static int highScore;
	public static int remainingLives;
	
	public static int ufoTime;
	public static int time;
	public static boolean turn = false;
	
	public static boolean laserOnAir = false;
	
	private Line bottomLine;
	private RemainingLives remLives;
	
	public static PlayerObject player;
	public static ArrayList<Drawable> objects;
	public static ArrayList<Invader> invaders;
	
	public static ArrayList<Drawable> addQueue;
	public static ArrayList<Drawable> removeQueue;
	
	public static Audio[] bgSounds;
	public static int bgSoundIndex = 0;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		texMan = new TextureManager();
		soundMan = new SoundManager();
		
		bgSounds = new Audio[4];
		bgSounds[0] = SoundManager.getSound(SoundManager.INVADER1);
		bgSounds[1] = SoundManager.getSound(SoundManager.INVADER2);
		bgSounds[2] = SoundManager.getSound(SoundManager.INVADER3);
		bgSounds[3] = SoundManager.getSound(SoundManager.INVADER4);
		
		objects = new ArrayList<Drawable>();
		invaders = new ArrayList<Invader>();
		addQueue = new ArrayList<Drawable>();
		removeQueue = new ArrayList<Drawable>();
		
		invaderDirection = RIGHT;
		
		gameSpeed = 1.f;
		animSpeed = baseAnimSpeed;
		
		ufoTime = 0;
		time = 0;
		turn = false;
		
		score = 0;
		highScore = 9990;
		remainingLives = 3;
		
		scoreText = new Text("SCORE", 20, 20, 20, Color.white);
		highScoreText = new Text("HIGH SCORE", 430, 20, 20, Color.white);
		remainingLivesText = new Text("3", 10, gc.getHeight() - 30, 20, Color.white);
		
		scoreValueText = new Text(Integer.toString(score), 20, 40, 20, Color.white);
		hScoreValueText = new Text(Integer.toString(highScore), 480, 40, 20, Color.white);
		
		bottomLine = new Line(0,gc.getHeight() - 40, gc.getWidth(), gc.getHeight() - 40);
		remLives = new RemainingLives(40, gc.getHeight() - 30);
		
		player = new PlayerObject(20, 500);
		
		objects.add(scoreText);
		objects.add(highScoreText);
		objects.add(remainingLivesText);
		
		objects.add(scoreValueText);
		objects.add(hScoreValueText);
		
		objects.add(remLives);
		
		initInvaders();
		
	}

	public static void restart() {
		//score = 0;
		//initInvaders();
		for(Drawable d : objects) {
			if(d instanceof PlayerLaser)
				removeQueue.add(d);
			if(d instanceof Laser)
				removeQueue.add(d);
		}
		laserOnAir = false;
		//gameSpeed = 1.f;
		time = 0;
	}
	
	public static void addObject(Drawable object) {
		addQueue.add(object);
	}
	
	public static void removeObject(Drawable object) {
		removeQueue.add(object);
	}
	
	public static void initInvaders() {
		
		int startX = 25;
		int startY = 75;
		invaders.clear();
		
		//for(int x = 0; x < 1; x++)
		//	invaders.add(new SmallInvader(startX + 4 + x * 30, startY + 30));
		
		// SMALL INVADERS
		for(int x = 0; x < 15; x++)
			invaders.add(new SmallInvader(startX + 4 + x * 30, startY + 30));
		
		// MEDIUM INVADERS
		for(int y = 0; y < 2; y++) {
			for(int x = 0; x < 15; x++)
				invaders.add(new MediumInvader(startX + 1 + x * 30, startY + 60 + y * 30));
		}
		
		// LARGE INVADERS
		for(int y = 0; y < 2; y++) {
			for(int x = 0; x < 15; x++)
				invaders.add(new LargeInvader(startX + x * 30, startY + 120 + y * 30));
		}
		
		
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(Drawable r : objects)
			r.draw(g);
		
		for(Invader i : invaders)
			i.draw(g);
		
		player.draw(g);
		
		g.draw(bottomLine);
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(remainingLives == 0 && time >= 500) {
			sbg.enterState(Main.STATE_FAIL);
		}
		
		scoreValueText.setMessage(Integer.toString(score));
		remainingLivesText.setMessage(Integer.toString(remainingLives));
		
		animSpeed = (int) (baseAnimSpeed * (1/gameSpeed));
		invaderSpeed = invaderDirection * delta;
		time += delta;
		ufoTime += delta;
		
		if(playHandle) {
			
			for(Drawable d : objects)
				d.update(delta);
			
			for(Invader i : invaders) {
				i.update(delta);
				if(i.getBottomBorder() > GameState.bottomBorder)
					sbg.enterState(Main.STATE_FAIL);
			}
			
			for(Invader i : invaders) {
				if(GameState.time >= 1000 * (1 / GameState.gameSpeed)) {
					
					if(i.getLeftBorder() < GameState.leftBorder) {
						GameState.invaderDirection = 1;
						turn = true;
					}
					
					if(i.getRightBorder() > GameState.rightBorder) {
						GameState.invaderDirection = -1;
						turn = true;
					}
					
				}
			}
			
			if(time >= 1000 * (1/gameSpeed)) {
				
				if(turn) {
					for(Invader i : invaders) {
						i.moveDown(delta);
					}
					
					turn = false;
				}
				
				/*
				bgSounds[bgSoundIndex++].playAsSoundEffect(1.f, 1.0f, false);
				if(bgSoundIndex > 3) {
					bgSoundIndex = 0;
				}
				*/
				
				time = 0;
			}
			
			if(ufoTime >= 20000) {
				addObject(new UFO(0, 0));
				ufoTime = 0;
			}
			
		}
		
		if(addQueue.size() > 0) {
			
			for(Drawable d : addQueue)
				objects.add(d);
			
			addQueue = new ArrayList<Drawable>();
			
		}
		
		if( removeQueue.size() > 0 ) {
			for(Drawable d : removeQueue) {
				if(d instanceof Invader) {
					invaders.remove(d);
				} else {
					objects.remove(d);
				}
			}
				
			
			removeQueue = new ArrayList<Drawable>();
		}
		
		if(invaders.size() == 0)
			sbg.enterState(Main.STATE_WON);
		
		player.update(delta);
		
	}

	public static void increaseSpeed() {
		if(gameSpeed < 5)
			gameSpeed += speedTick;
	}
	
	
	public int getID() {
		return Main.STATE_GAME;
	}

}