import java.util.Arrays;
import java.util.Scanner;

public class HuffmanEncoding {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		int numberOfCharactersToEncode;
		System.out.print("");
		numberOfCharactersToEncode = scn.nextInt();
		
		if (numberOfCharactersToEncode >= 1 && numberOfCharactersToEncode <= 20) {
			String[] characters = new String[numberOfCharactersToEncode];
			String[] binaryData = new String[numberOfCharactersToEncode];
			
			for (int i = 0 ; i < numberOfCharactersToEncode ; i++) {
				System.out.print("");
				String character = scn.next();
				String binaryValue = scn.next();
				
				characters[i] = character;
				binaryData[i] = binaryValue;
			}
			System.out.println(Arrays.toString(characters));
			System.out.println(Arrays.toString(binaryData));
		}
	}
}
