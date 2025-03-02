package utils;

public class Constants {
	
	public static class Directions {
		public static final int left = 0;
		public static final int up = 1;
		public static final int right = 2;
		public static final int down = 3;
	}
	public static class PlayerConstants{
		
		public static final int idle = 0;
		public static final int walk = 1;
		public static final int run = 2;
		public static final int combatIdle = 3;
		public static final int jump = 4;
		public static final int slash = 5;
		public static final int hurt = 6;
		
		public static int GetSpriteAmount(int playerAction) {
		    switch (playerAction) {
		        case walk:
		            return 9;
		        case run:
		            return 8;
		        case idle:
		        case combatIdle:
		            return 2;
		        case jump:
		            return 5;
		        case slash: 
		        case hurt:
		            return 6;
		        default:
		            return 1;
		    }
		}
	}
}
