import java.util.Scanner;

public class BoringBusiness {
	static Scanner scn = new Scanner(System.in);
	static boolean[][] map = new boolean[401][401]; //200 on both sides +1 cuz java is 0-indexed
	static int[] currentCoordinate = {0, -1};
	
	public static void main(String args[]) {
		for (int i = 0 ; i < map.length ; i++) {
			for (int j = 0 ; j < map[i].length ; j++) {
				map[i][j] = false;
			}
		}
		
		//setting up the map
		mapCoordinate(0, -1);
		move("d", 2);
		move("r", 3);
		move("d", 2);
		move("r", 2);
		move("u", 2);
		move("r", 2);
		move("d", 4);
		move("l", 8);
		move("u", 2);
	}
	
	public static void mapCoordinate(int xCoordinate, int yCoordinate) {
		map[yCoordinate + 200][xCoordinate + 200] = true;
	}
	
	public static boolean checkCoordinate(int xCoordinate, int yCoordinate) {
		if (map[yCoordinate + 200][xCoordinate + 200]) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean move(String direction, int amount) {
		boolean hit = false;
		if (amount > 0) {
			for (int i = 0 ; i < amount ; i++) {
				if (direction.contains("u")) {
					currentCoordinate[1] = currentCoordinate[1] + 1;
				} else if (direction.contains("d")) {
					currentCoordinate[1] = currentCoordinate[1] - 1;
				} else if (direction.contains("l")) {
					currentCoordinate[0] = currentCoordinate[0] - 1;
				} else if (direction.contains("r")) {
					currentCoordinate[0] = currentCoordinate[0] + 1;
				}
				
				if (checkCoordinate(currentCoordinate[0], currentCoordinate[1])) {
					hit = true;
					break;
				} else {
					mapCoordinate(currentCoordinate[0], currentCoordinate[1]);
				}
			}
			return !hit;
		}
		return true; //should not reach here
	}
}
