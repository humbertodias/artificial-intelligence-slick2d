package pacman;

public class Timer {

	private long lastTime = 0l;
	private long pauseTime = 0l;
	boolean isPaused = false;
	
	public void start() {
		
		lastTime = System.nanoTime();
	}
	
	public long getTicks() {
		
		if (isPaused) {
			return pauseTime - lastTime;
		}
		
		return System.nanoTime() - lastTime;
	}
	
	public void pause() {
		
		if(!isPaused) {
			pauseTime = System.nanoTime();
			isPaused = true;
		}
	}
	
	public void resume() {
		
		if (isPaused) {
			lastTime += System.nanoTime() - pauseTime;
			isPaused = false;
		}
	}
}
