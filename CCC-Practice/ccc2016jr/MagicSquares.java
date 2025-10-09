import java.util.Scanner;

public class MagicSquares {
	static Scanner scn = new Scanner(System.in);
	
	static final int SQUARE_SIDE_LENGTH = 4;
	
	public static void main(String[] args) {
		int[][] square = new int[SQUARE_SIDE_LENGTH][SQUARE_SIDE_LENGTH];
		
		for (int i = 0 ; i < square.length ; i++) {
			for (int j = 0 ; j < square[i].length ; j++) {
				square[i][j] = scn.nextInt();
			}
		}
		scn.close();
		
		int initialSum = 0;
		
		//loop through rows
		for (int i = 0 ; i < square.length ; i++) {
			int sum = 0;
			for (int j = 0 ; j < square[i].length ; j++) {
				sum += square[i][j];
			}
			if (i == 0) {
				initialSum = sum;
			} else {
				if (sum == initialSum) {
					continue;
				} else {
					System.out.println("not magic");
					return;
				}
			}
		}
		
		//loop through columns
		for (int j = 0 ; j < square[0].length ; j++) {
			int sum = 0;
			for (int i = 0 ; i < square.length ; i++) {
				sum += square[i][j];
			}
			
			if (sum == initialSum) {
				continue;
			} else {
				System.out.println("not magic");
				return;
			}
		}
		
		System.out.println("magic"); //must be magic if all the sums are equal
	}
}
