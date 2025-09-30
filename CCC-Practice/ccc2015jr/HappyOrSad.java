import java.util.Scanner;

public class HappyOrSad {
	static Scanner scn = new Scanner(System.in);
	
	static final String HAPPY_EMOTICON = ":-)";
	static final String SAD_EMOTICON = ":-(";
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		String[] words = inputString.split(" ");
		
		int happyCount = 0, sadCount = 0;
		
		for (String word : words) {
			if (word.contains(HAPPY_EMOTICON)) {
				happyCount++;
			}
			if (word.contains(SAD_EMOTICON)) {
				sadCount++;
			}
		}
		
		if (happyCount == 0 && sadCount == 0) {
			System.out.println("none");
		} else if (happyCount > sadCount) {
			System.out.println("happy");
		} else if (sadCount > happyCount) {
			System.out.println("sad");
		} else if (happyCount == sadCount) {
			System.out.println("unsure");
		} else { //program should never reach here
			return;
		}
	}
}
