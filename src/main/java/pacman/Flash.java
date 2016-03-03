package pacman;

public class Flash {

	private int rate;
	private int ticks;
	private boolean isFlash;
	
	Flash(int rate) {
		
		this.isFlash = false;
		this.ticks = 0;
		this.rate = rate;
	}
	
	boolean isFlash() {
		
		return isFlash;
	}
	
	void update() {
		
		ticks++;
		if (ticks == rate) {
			
			ticks = 0;
			isFlash= !isFlash;
		}
	}
}
