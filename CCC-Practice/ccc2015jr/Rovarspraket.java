import java.util.Scanner;

public class Rovarspraket {
	static Scanner scn = new Scanner(System.in);
	
	static final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", 
			"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	static final String[] VOWELS = {"a", "e", "i", "o", "u"};
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		scn.close();
		
		String[] characters = inputString.split("");
		
		String outputString = "";
		
		for (String character : characters) {
			if (getLetterType(character).contains("vowel")) {
				outputString += character;
				continue;
			}
			
			//keep going if the letter is a consonant
			outputString += character;
			outputString += getClosestVowel(character);
			outputString += getClosestConsonant(character);
		}
		
		System.out.println(outputString);
	}
	
	private static String getLetterType(String letter) {
		for (String character : VOWELS) {
			if (character.contains(letter)) {
				return "vowel";
			}
		}
		
		return "consonant"; //not a vowel
	}
	
	private static String getClosestVowel(String letter) {
		int letterIndex = 0;
		
		for (int i = 0; i < ALPHABET.length ; i++) {
			if (ALPHABET[i].contains(letter)) {
				letterIndex = i;
				break;
			}
		}
		
		int forwardDistance = 0;
		String forwardVowel = "";
		
		int backwardDistance = 0;
		String backwardVowel = "";
		
		//forward calculation
		for (int i = letterIndex ; i < ALPHABET.length - 1; i++) {
			String nextLetter = ALPHABET[i+1];
			forwardDistance++;
			if (getLetterType(nextLetter).contains("vowel")) {
				forwardVowel = nextLetter;
				break;
			}
		}
		
		//backward calculation
		for (int i = letterIndex ; i > 0; i--) {
			String previousLetter = ALPHABET[i-1];
			backwardDistance++;
			if (getLetterType(previousLetter).contains("vowel")) {
				backwardVowel = previousLetter;
				break;
			}
		}
		
		if (forwardDistance < backwardDistance) {
			if (!(forwardVowel.isEmpty())) {
				return forwardVowel;
			} else {
				return backwardVowel;
			}
		} else if (backwardDistance < forwardDistance) {
			if (!(backwardVowel.isEmpty())) {
				return backwardVowel;
			} else {
				return forwardVowel;
			}
		} else if (forwardDistance == backwardDistance) {
			return backwardVowel; //as per the instructions in the problem
		} else {
			return "error"; //should never reach here
		}
	}
	
	private static String getClosestConsonant(String letter) {
		if (letter.contains("z")) {
			return letter;
		} else {
			int letterIndex = 0;
			
			for (int i = 0; i < ALPHABET.length ; i++) {
				if (ALPHABET[i].contains(letter)) {
					letterIndex = i;
					break;
				}
			}
			
			for (int i = letterIndex ; i < ALPHABET.length - 1 ; i++) {
				String nextLetter = ALPHABET[i+1];
				if (getLetterType(nextLetter).contains("consonant")) {
					return nextLetter;
				}
			}
		}
		
		return ""; //should never reach here
	}
}