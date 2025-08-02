import java.util.*;

public class AromaticNumbers {
	static Scanner scn = new Scanner(System.in);
	
	static Map<String, Integer> romanNumerals = new HashMap<>();
	
	public static void main(String[] args) {
		romanNumerals.put("I", 1);
		romanNumerals.put("V", 5);
		romanNumerals.put("X", 10);
		romanNumerals.put("L", 50);
		romanNumerals.put("C", 100);
		romanNumerals.put("D", 500);
		romanNumerals.put("M", 1000);
		
		String inputString = scn.nextLine();
		
		if (inputString.length() >= 2 && inputString.length() <= 20) {
			String[] symbols = inputString.split("");
			
			int result = 0;
			
			for (int i = 0 ; i < inputString.length(); i = i + 2) {
				int arabicDigit = Integer.parseInt(symbols[i]);
				int romanNumeral = romanNumerals.get(symbols[i+1]);
				
				int previousRomanNumeral = 0;
				int previousArabicDigit = -1;
				
				if (!(i == 0)) {
					previousArabicDigit = Integer.parseInt(symbols[i-2]);
					previousRomanNumeral = romanNumerals.get(symbols[i-1]);
				}
				
				int product = arabicDigit * romanNumeral;
			}
		}
	}
}
