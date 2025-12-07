import java.util.Scanner;

public class AreWeThereYet {
	static Scanner scn = new Scanner(System.in);
	
	static final int CITIES = 5;
	static int[] rcd = new int[CITIES];
	static int[][] distances = new int[CITIES][CITIES];
	
	public static void main(String[] args) {
		for (int i = 0 ; i < CITIES ; i++) {
			if (i == 0) {
				rcd[i] = 0;
			} else {
				rcd[i] = scn.nextInt() + rcd[i - 1];
			}
		}
		
		for (int cC = 0 ; cC < CITIES ; cC++) {
			for (int j = 0 ; j < CITIES ; j++) {
				distances[cC][j] = Math.abs(rcd[cC] - rcd[j]);
			}
		}
		
		StringBuilder sb = new StringBuilder("");
		for (int i = 0 ; i < CITIES ; i++) {
			for (int j = 0 ; j < CITIES ; j++) {
				sb.append(distances[i][j]);
				sb.append(" ");
			}
			sb.delete(sb.length() - 1, sb.length() - 1);
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
