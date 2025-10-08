import java.util.Scanner;

public class TournamentSelection {
	static Scanner scn = new Scanner(System.in);
	static final int GAMES = 6;
	
	public static void main(String[] args) {
		String gameStatus;
		int wins = 0;
		int group = -1; //initially assuming no wins
		
		for (int i = 0 ; i < GAMES ; i++) {
			gameStatus = scn.next();
			scn.nextLine();
			
			if (gameStatus.contains("W")) {
				wins++;
			}
		}
		
		if (wins == 6 || wins == 5) {
			group = 1;
		} else if (wins == 4 || wins == 3) {
			group = 2;
		} else if (wins == 2 || wins == 1) {
			group = 3;
		}
		
		System.out.println(group);
	}
}
