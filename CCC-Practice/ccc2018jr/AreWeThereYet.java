import java.util.Scanner;

public class AreWeThereYet {
	static Scanner scn = new Scanner(System.in);
	
	static final int CITIES = 5;
	static int[] relativeCityDistances = new int[CITIES];
	static int[][] distances = new int[CITIES][CITIES];
	
	public static void main(String[] args) {
		for (int i = 0 ; i < CITIES ; i++) {
			if (i == 0) {
				relativeCityDistances[i] = 0;
			} else {
				relativeCityDistances[i] = scn.nextInt() + relativeCityDistances[i - 1];
			}
		}
		
		for (int currentCity = 0 ; currentCity < CITIES ; currentCity++) {
			for (int j = 0 ; j < CITIES ; j++) {
				distances[currentCity][j] = Math.abs(relativeCityDistances[currentCity] - relativeCityDistances[j]);
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
