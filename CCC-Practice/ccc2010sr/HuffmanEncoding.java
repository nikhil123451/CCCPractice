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
			
			String encodedString = "";
			String decodedString = "";
			System.out.print("");
			encodedString = scn.next();
			String[] encodedBinaryData = encodedString.split("");
			String bit = "";
			for (int i = 0 ; i < encodedBinaryData.length ; i++) {
				if (bit.length() <= 1) {
					bit = encodedBinaryData[i];
				}
				if (Arrays.stream(binaryData).anyMatch(bit::equals)) {
					int index = 0;
					for (int j = 0; j < binaryData.length; j++) {
					    if (bit.equals(binaryData[j])) {
					        index = j;
					        break;
					    }
					}
					decodedString = decodedString + characters[index];
					bit = "";
				} else {
					bit = bit + encodedBinaryData[i+1];
				}
			}
			System.out.println(decodedString);
		}
	}
}
