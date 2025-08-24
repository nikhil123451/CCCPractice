import java.util.Scanner;

public class DoubleDice {
	static Scanner scn = new Scanner(System.in);
	
	static final int STARTING_PLAYER_SCORE = 100;
	
	public static void main(String[] args) {
		int antoniaScore = STARTING_PLAYER_SCORE;
		int davidScore = STARTING_PLAYER_SCORE;
		
		int rounds = scn.nextInt();
		
		if (rounds >= 1 && rounds <= 15) {
			for (int i = 0 ; i < rounds ; i++) {
				int antoniaRoll = scn.nextInt();
				int davidRoll = scn.nextInt();
				
				if (antoniaRoll > davidRoll) {
					davidScore -= antoniaRoll;
				} else if (davidRoll > antoniaRoll) {
					antoniaScore -= davidRoll;
				} else {
					continue;
				}
			}
			
			System.out.println(antoniaScore + "\n" + davidScore);
		}
		
		scn.close();
	}
}
