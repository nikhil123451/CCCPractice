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
		scn.close();
		
		if (inputString.length() >= 2 && inputString.length() <= 20) {
			String[] symbols = inputString.split("");
			
			int result = 0;
			
			for (int i = 1; i < inputString.length(); i = i + 2) {
				int arabicNumber = Integer.parseInt(symbols[i - 1]);
				int romanNumeral = romanNumerals.get(symbols[i]);
				
				if (i == 1) {
					if (inputString.length() == 2) {
						result = arabicNumber * romanNumeral;
						System.out.println(result);
						return;
					}
					continue;
				}

				int previousArabicNumber = Integer.parseInt(symbols[i - 3]);
				int previousRomanNumeral = romanNumerals.get(symbols[i - 2]);
				
				if (romanNumeral > previousRomanNumeral) {
					result -= previousArabicNumber * previousRomanNumeral;
				} else if (romanNumeral <= previousRomanNumeral) {
					result += previousArabicNumber * previousRomanNumeral;
				} else {
					return;
				}
				
				if (i == inputString.length() - 1) {
					result += arabicNumber * romanNumeral;
				}
			}
			
			System.out.println(result);
		}
	}
}
