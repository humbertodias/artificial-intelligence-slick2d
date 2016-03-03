package pacman;

import java.util.ArrayDeque;

public class FruitManager {
	
	FruitState fruit1State;
	FruitState fruit2State;
	
	Timer fruit1Timer;
	Timer fruit2Timer;
	
	ArrayDeque<FruitType> fruitGUI;
	
	public enum FruitType {
		CHERRY,
		STRAWBERRY,
		ORANGE,
		BANANA,
		APPLE,
		GRAPES,
		MANGO,
		TROPHY
	}
	
	public enum FruitState {
		
		UNSPAWNED,
		SPAWNED,
		SCORE_DISPLAY,
		DESPAWNED
	}
	
	public FruitManager() {
		
		reset();
		
		fruit1Timer = new Timer();
		fruit2Timer = new Timer();
		
		fruitGUI = new ArrayDeque<FruitType>();
		
		fruitGUI.push(FruitType.CHERRY);
	}
	
	public void reset() {
		
		fruit1State = FruitState.UNSPAWNED;
		fruit2State = FruitState.UNSPAWNED;
		
	}
	
	public void spawn1stFruit() {
		
		if (fruit1State == FruitState.SPAWNED) return;
		
		fruit1State = FruitState.SPAWNED;
		fruit1Timer.start();
	}

	public void spawn2ndFruit() {
		
		if (fruit2State == FruitState.SPAWNED) return;
		
		fruit2State = FruitState.SPAWNED;
		fruit2Timer.start();
	}
	
	public void eatFruit() {
		
		if (fruit1State == FruitState.SPAWNED) {
			
			fruit1State = FruitState.SCORE_DISPLAY;
			fruit1Timer.start();
		}
		else if (fruit2State == FruitState.SPAWNED) {
			
			fruit2State = FruitState.SCORE_DISPLAY;
			fruit2Timer.start();
		}
	}

	public void nextLevel(Score score) {
		
		reset();
		fruitGUI.push(getFruitType(score.getLevel()));
		if (fruitGUI.size() > 5) {
			fruitGUI.removeLast();
		}
	}
	
	public ArrayDeque<FruitType> getFruitStack() {
		
		return fruitGUI;
	}
	
	public int getFruitValue(int level) {
		
		 FruitType fruit = getFruitType(level);
		 
		 switch (fruit) {
		 
		 case CHERRY:
			 return Const.CHERRY_SCORE;
		 case STRAWBERRY:
			 return Const.STRAWBERRY_SCORE;
		 case ORANGE:
			 return Const.ORANGE_SCORE;
		 case BANANA:
			 return Const.BANANA_SCORE;
		 case APPLE:
			 return Const.APPLE_SCORE;
		 case GRAPES:
			 return Const.GRAPES_SCORE;
		 case MANGO:
			 return Const.MANGO_SCORE;
		 case TROPHY:
			 return Const.MANGO_SCORE;
		 default:
			 return 0;
		 }
	}
	
	public void despawn2ndFruit() {
		
		fruit1State = FruitState.DESPAWNED;
	}
	
	public boolean isFruitVisible() {
		
		return (fruit1State == FruitState.SPAWNED || fruit1State == FruitState.SCORE_DISPLAY) ||
			(fruit2State == FruitState.SPAWNED || fruit2State == FruitState.SCORE_DISPLAY);
	}
	
	public  FruitState getFruit1State() {
		return fruit1State;
	}
	
	public  FruitState getFruit2State() {
		return fruit2State;
	}
	
	public boolean isFruitCollidable() {

		return (fruit1State == FruitState.SPAWNED || fruit2State == FruitState.SPAWNED);
	}
	
	public void update() {
		
		//System.out.println(fruit1State + " " + fruit2State);
		
		if (fruit1Timer.getTicks() >= Const.BASE_FRUIT_TIME && fruit1State == FruitState.SPAWNED) {
			
			fruit1State = FruitState.DESPAWNED;
		}
		else if (fruit1Timer.getTicks() >= Const.SCORE_FRUIT_TIME && fruit1State == FruitState.SCORE_DISPLAY) {
			
			fruit1State = FruitState.DESPAWNED;
		}
		if (fruit2Timer.getTicks() >= Const.BASE_FRUIT_TIME && fruit2State == FruitState.SPAWNED) {
			
			fruit2State = FruitState.DESPAWNED;
		}
		else if (fruit2Timer.getTicks() >= Const.SCORE_FRUIT_TIME && fruit2State == FruitState.SCORE_DISPLAY) {
			
			fruit2State = FruitState.DESPAWNED;
		}
	}
			
	public FruitType getFruitType(int level) {
		
		switch (level) {
		
		case 0:
			return FruitType.CHERRY;
		case 1:
			return FruitType.STRAWBERRY;
		case 2:
			return FruitType.ORANGE;
		case 3:
			return FruitType.ORANGE;
		case 4:
			return FruitType.BANANA;
		case 5:
			return FruitType.BANANA;
		case 6:
			return FruitType.APPLE;
		case 7:
			return FruitType.APPLE;
		case 8:
			return FruitType.GRAPES;
		case 9:
			return FruitType.GRAPES;
		case 10:
			return FruitType.MANGO;
		case 11:
			return FruitType.MANGO;
		case 12:
			return FruitType.TROPHY;
		case 13:
			return FruitType.TROPHY;
		default:
			return FruitType.TROPHY;
		}
	}
}
