import java.util.Scanner;

public class Flipper2 {
	static Scanner scn = new Scanner(System.in);
	
	static int[][] grid = {{1,2},{3,4}};
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		String[] commands = inputString.split("");
		scn.close();
		
		for (int i = 0 ; i < commands.length ; i++) {
			if (commands[i].contains("H")) {
				flipHorizontally();
			} else if (commands[i].contains("V")) {
				flipVertically();
			} else {
				System.out.println("invalid input");
				break;
			}
		}
		
		System.out.println(Integer.toString(grid[0][0]) + " " + Integer.toString(grid[0][1]) + 
				"\n" + Integer.toString(grid[1][0]) + " " + Integer.toString(grid[1][1]));
	}
	
	public static void flipHorizontally() {
		int temporaryVariable;
		
		temporaryVariable = grid[0][0];
		grid[0][0] = grid[1][0];
		grid[1][0] = temporaryVariable;
		
		temporaryVariable = grid[0][1];
		grid[0][1] = grid[1][1];
		grid[1][1] = temporaryVariable;
	}
	
	public static void flipVertically() {
		int temporaryVariable;
		
		temporaryVariable = grid[0][0];
		grid[0][0] = grid[0][1];
		grid[0][1] = temporaryVariable;
		
		temporaryVariable = grid[1][0];
		grid[1][0] = grid[1][1];
		grid[1][1] = temporaryVariable;
	}
}