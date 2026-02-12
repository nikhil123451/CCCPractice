import java.util.Scanner;
import java.util.Arrays;

public class ModernArt {
	static Scanner scn = new Scanner(System.in);
	
	static boolean[][] hasGold;
	
	public static void main(String[] args) {
		int rows = scn.nextInt();
		int columns = scn.nextInt();
		int strokes = scn.nextInt();
		
		hasGold = new boolean[rows][columns];
		
		for (int i = 0 ; i < hasGold.length ; i++) {
			Arrays.fill(hasGold[i], false);
		}
		
		for (int i = 0 ; i < strokes ; i++) {
			scn.nextLine();
			String type = scn.next();
			int dimensionNumber = scn.nextInt();
			
			applyStroke(type, dimensionNumber);
		}
		
		int goldCount = 0;
		
		for (int i = 0 ; i < hasGold.length ; i++) {
			for (int j = 0; j < hasGold[i].length ; j++) {
				if (hasGold[i][j]) goldCount++;
			}
		}
		
		System.out.println(goldCount);
		scn.close();
	}
	
	public static void applyStroke(String type, int dimensionNumber) {
		if (type.equals("R")) {
			
			for (int i = 0 ; i < hasGold[dimensionNumber - 1].length ; i++) { //-1 cuz java is 0-indexed
				hasGold[dimensionNumber - 1][i] = !hasGold[dimensionNumber - 1][i];
			}
			
		} else if (type.equals("C")) {
			
			for (int i = 0 ; i < hasGold.length ; i++) {
				hasGold[i][dimensionNumber - 1] = !hasGold[i][dimensionNumber - 1]; //-1 cuz java is 0-indexed
			}
			
		} else {
			return; //should never reach here
		}
	}
}
