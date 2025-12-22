import java.util.Scanner;

public class ColdCompress {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int lines = scn.nextInt();
		StringBuilder sb = new StringBuilder();
		String[] characters;
		scn.nextLine(); //parsing through \n
		
		for (int line = 0 ; line < lines ; line++) {
			characters = scn.nextLine().split("");
			int characterCount = 0;
			for (int i = 0 ; i < characters.length ; i++) {
				if (i == 0) {
					characterCount++;
					continue;
				}
				String previousCharacter = characters[i - 1];
				String currentCharacter = characters[i];
				
				if (currentCharacter.contains(previousCharacter)) {
					characterCount++;
				} else {
					sb.append(Integer.toString(characterCount) + " " + previousCharacter + " ");
					characterCount = 1; //current character makes up 1
				}
			}
			sb.append(Integer.toString(characterCount) + " " + characters[characters.length - 1] + " ");
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
