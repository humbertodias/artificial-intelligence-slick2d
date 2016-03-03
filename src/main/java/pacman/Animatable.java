package pacman;

public class Animatable {

	protected int xStepsPerFrame = 1;
	protected int xFrameDir;
	protected int xMaxFrame;
	protected int xStep;
	protected int xFrame;
	protected int yFrame;
	
	protected Animatable() {
		
		this.xMaxFrame = 1; 
		xFrameDir = 1;
	}
	
	protected void animationUpdate() {
		
		xStep++;
		if (xStep == xStepsPerFrame) {
			
			xStep = 0;
			xFrame += xFrameDir;
			
			if (xFrame == xMaxFrame || xFrame == 0) {
				xFrameDir *= -1;
			}
		}
	}
	
	public int getXFrame() {
		return xFrame;
	}
	
	public int getYFrame() {
		return yFrame;
	}
	
	public void setXMaxFrame(int xMaxFrame) {
		this.xMaxFrame= xMaxFrame; 
	}
	
	public void setXStepsPerFrame(int xStepsPerFrame) {
		
		this.xStepsPerFrame = xStepsPerFrame;
	}
	
	public void setXFrameDir(int xFrameDir) {
		
		this.xFrameDir = xFrameDir;
	}
}
