import java.util.*;

public class Ragaman {
	static Scanner scn = new Scanner(System.in);
	static HashMap<String, Integer> characterCounts = new HashMap<>();
	static HashMap<String, Integer> testCharacterCounts = new HashMap<>();
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		String testString = scn.nextLine();
		
		String[] inputStringCharacters = inputString.split("");
		String[] testStringCharacters = testString.split("");
		
		boolean asteriskExists = false;
		
		for (String character : testStringCharacters) {
			if (character.contains("*")) {
				asteriskExists = true;
			}
		}
		
		if (!asteriskExists || (inputString.length() != testString.length())) {
			System.out.println("N");
			return;
		}
		
		ArrayList<String> uniqueCharacters = new ArrayList<String>();
		
		for (String character : inputStringCharacters) {
			if (characterCounts.containsKey(character)) {
				characterCounts.replace(character, characterCounts.get(character) + 1);
			} else {
				uniqueCharacters.add(character);
				characterCounts.put(character, 1);
			}
		}
		
		for (String character : testStringCharacters) {
			if (!character.contains("*")) {
				if (testCharacterCounts.containsKey(character)) {
					testCharacterCounts.replace(character, testCharacterCounts.get(character) + 1);
				} else {
					testCharacterCounts.put(character, 1);
				}
			}
		}
		
		for (String character : uniqueCharacters) {
			if (characterCounts.containsKey(character) && testCharacterCounts.containsKey(character)) {
				int initialAmount = characterCounts.get(character);
				int finalAmount = testCharacterCounts.get(character);
				
				if (finalAmount > initialAmount) {
					System.out.println("N");
					return;
				}
			} else {
				System.out.println("N");
				return;
			}
		}
		
		System.out.println("A"); //print A if all conditions have been satisfied
	}
}
