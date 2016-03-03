package pacman;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Score {

	private int score;
	private int highScore;
	private int lives;
	private int level;
	private int scoreUntilLife;
	
	private boolean nextLevelTransition;
	
	Score() {
		score = 0;
		loadHighScore();
		scoreUntilLife = Const.SCORE_UNTIL_LIFE;
		setLives(3);
		setLevel(0);
	}
	
	protected void loadHighScore() {
		
		try {
			highScore = Integer.parseInt(Files.readAllLines(Paths.get(Const.SCORE_FILE_PATH)).get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void saveHighScore() {
		
		Writer wr;
		try {
			wr = new FileWriter(Const.SCORE_FILE_PATH);
			wr.write(String.valueOf(highScore));
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addScore(int score) {
		
		this.score += score;
		this.scoreUntilLife -= score;
		
		if(scoreUntilLife <= 0) {
			scoreUntilLife += Const.SCORE_UNTIL_LIFE;
			if (lives < 5) {
				lives++;
			}
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getHighScore() {
		
		if (score > highScore) {
			
			highScore = score;
		}
		return highScore;
	}

	public boolean isNextLevelTransition() {
		return nextLevelTransition;
	}

	public void setNextLevelTransition(boolean nextLevelTransition) {
		this.nextLevelTransition = nextLevelTransition;
	}
}
