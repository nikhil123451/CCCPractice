import java.util.Scanner;

public class HiddenPalindrome {
	static Scanner scn = new Scanner(System.in);
	
	static String[] characters;
	static int distanceToCheck;
	static int palindromeLength;
	static int[] palindromeLengths;
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		scn.close();
		characters = inputString.split("");
		
		palindromeLengths = new int[characters.length];
		
		if (isPalindrome(inputString)) {
			System.out.println(inputString.length());
			return;
		}
		
		for (int i = 0 ; i < characters.length ; i++) {
			distanceToCheck = 1;
			palindromeLength = 1;
			palindromeCheck(i, distanceToCheck);
		}
		
		int maximumLength = 1;
		for (int length : palindromeLengths) {
			if (length > maximumLength) {
				maximumLength = length;
			}
		}
		
		System.out.println(maximumLength);
	}
	
	public static void palindromeCheck(int index, int distance) {
		if (index - distance >= 0 && index + distance <= characters.length - 1) {
			String previousCharacter = characters[index-distance];
			String nextCharacter = characters[index+distance];
			
			if (previousCharacter.contains(nextCharacter)) {
				palindromeLength += 2; //adding 2 letters to the length
				distanceToCheck++;
				palindromeLengths[index] = palindromeLength;
				palindromeCheck(index, distanceToCheck);
			}
		} else {
			if (distance == 1) {
				palindromeLengths[index] = 1;
			}
		}
	}
	
	public static boolean isPalindrome(String word) {
        int leftIndex = 0;
        int rightIndex = word.length() - 1;

        while (leftIndex < rightIndex) {
            if (word.charAt(leftIndex) != word.charAt(rightIndex)) {
                return false;
            }
            leftIndex++;
            rightIndex--;
        }

        return true;
    }
}
