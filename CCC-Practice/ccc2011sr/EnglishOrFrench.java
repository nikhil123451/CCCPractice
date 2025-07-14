import java.util.Scanner;

public class EnglishOrFrench {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		int lines = Integer.parseInt(scn.nextLine());
		if (lines > 0 && lines < 10000) {
			int numberOfS = 0;
			int numberOfT = 0;
			
			for (int i = 0 ; i < lines ; i++) {
				String inputLine = scn.nextLine();
				String[] characters = inputLine.split("");
				for (String letter : characters) {
					if (letter.toUpperCase().contains("T")) {
						numberOfT++;
					}
					if (letter.toUpperCase().contains("S")) {
						numberOfS++;
					}
				}
			}
			
			if (numberOfT > numberOfS) {
				System.out.println("English");
			} else if (numberOfS >= numberOfT) {
				System.out.println("French");
			} else {
				System.out.println("Something went wrong"); //shouldn't reach here
			}
		}
	}
}
