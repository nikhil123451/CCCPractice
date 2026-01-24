import java.util.Scanner;

public class DogTreats {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int smalls = scn.nextInt();
		int mediums = scn.nextInt();
		int larges = scn.nextInt();
		
		int score = (smalls) + (2 * mediums) + (3 * larges); //formula from problem
		
		if (score >= 10) {
			System.out.println("happy");
		} else {
			System.out.println("sad");
		}
		scn.close();
	}
}
