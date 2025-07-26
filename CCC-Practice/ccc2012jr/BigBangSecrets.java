import java.util.Scanner;

public class BigBangSecrets {
	static Scanner scn = new Scanner(System.in);
	
	static String[] characters = {"A","B","C","D","E","F","G","H",
			"I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static void main(String[] args) {
		int shiftValue = scn.nextInt();
		String inputSequence = scn.next();
		String decodedSequence = "";
		
		if (shiftValue < 10 && inputSequence.length() <= 20) {
			String[] sequencePieces = inputSequence.split("");
			
			for (int k = 0 ; k < sequencePieces.length ; k++) {
				
				String character = sequencePieces[k];
				int characterIndex = 0;
				
				for (int i = 0; i < characters.length; i++) {
			        if (characters[i].contains(character)) {
			            characterIndex = i;
			            break;
			        }
			    }
				
				int totalShiftValue = shiftValue + 3*(k+1); //from the problem
				int newIndex = ((characterIndex - totalShiftValue) % characters.length 
								+ characters.length) % characters.length; //wraps around the array if it goes out of bounds
				
				String newCharacter = characters[newIndex];
				decodedSequence += newCharacter;
			}
			
			System.out.println(decodedSequence);
			scn.close();
		}
	}
}
