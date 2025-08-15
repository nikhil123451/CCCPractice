import java.util.Scanner;

public class From1987To20132 {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int year = scn.nextInt();
		scn.close();
		
		if (year >= 0 && year <= 10000) {
			int result = 0;
			boolean distinct = false;
			int[] frequencyTable;
			String[] digits;
			int nextYear = year;
			
			mainLoop:
			while (!distinct) {
				nextYear++;
				String yearString = Integer.toString(nextYear);
				frequencyTable = new int[10]; //every digit from 0-9
				digits = yearString.split("");
				
				for (int i = 0; i < digits.length ; i++) {
					int digit = Integer.parseInt(digits[i]);
					frequencyTable[digit]++;
				}
				
				for (int frequency : frequencyTable) {
					if (frequency == 0) {
						continue;
					} else {
						if (frequency != 1) {
							continue mainLoop;
						}
					}
				}
				
				distinct = true;
				result = nextYear;
			}
			
			System.out.println(result);
		}
	}
}
