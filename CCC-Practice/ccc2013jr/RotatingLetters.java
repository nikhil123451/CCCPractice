import java.util.Scanner;

public class RotatingLetters {
	static Scanner scn = new Scanner(System.in);
	
	static final String[] NON_ROTATIONAL_LETTERS = {"I", "O", "S", "H", "Z", "X", "N"};
	
	public static void main(String[] args) {
		String inputSequence = scn.nextLine();
		scn.close();
		
		if (inputSequence.length() >= 1 && inputSequence.length() <= 30) {
			String[] inputCharacters = inputSequence.split("");
			for (String character : inputCharacters) {
				if (!isInLetters(character)) {
					System.out.println("NO");
					return;
				}
			}
			System.out.println("YES");
		}
	}
	
	public static boolean isInLetters(String character) {
		for (String letter : NON_ROTATIONAL_LETTERS) {
			if (letter.contains(character)) {
				return true;
			}
		}
		return false;
	}
}
