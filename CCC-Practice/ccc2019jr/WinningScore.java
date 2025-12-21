import java.util.Scanner;

public class WinningScore {
	static Scanner scn = new Scanner(System.in);
	static final int TYPES_OF_SHOTS = 3;
	
	public static void main(String[] args) {
		int teamATotal = 0;
		int teamBTotal = 0;
		
		for (int i = TYPES_OF_SHOTS ; i >= 1 ; i--) {
			teamATotal += scn.nextInt() * i;
		}
		for (int i = TYPES_OF_SHOTS ; i >= 1 ; i--) {
			teamBTotal += scn.nextInt() * i;
		}
		
		if (teamATotal > teamBTotal) {
			System.out.println("A");
		} else if(teamATotal < teamBTotal) {
			System.out.println("B");
		} else {
			System.out.println("T");
		}
		scn.close();
	}
}
