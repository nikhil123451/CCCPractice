import java.util.Scanner;

public class FavouriteTimes {
	static Scanner scn = new Scanner(System.in);
	static final int STARTING_HOUR = 12;
	static final int STARTING_MINUTE = 0;
	static final int MINUTES_IN_HOUR = 60;
	static final int MAXIMUM_HOUR = 12;
	
	public static void main(String[] args) {
		int timeObserving = scn.nextInt();
		
		int newHour = STARTING_HOUR;
		int newMinute = STARTING_MINUTE;
		int arithmeticSequences = 0;
		
		for (int i = 0 ; i < timeObserving ; i++) {
			newMinute += 1;
			
			if (newMinute >= MINUTES_IN_HOUR) {
				
				newHour += newMinute / MINUTES_IN_HOUR;
				
				if (newHour > MAXIMUM_HOUR) {
					newHour = newHour / MAXIMUM_HOUR; 
				}
				newMinute = newMinute % MINUTES_IN_HOUR;
			}
			
			String newMinuteStringed = "";
			
			if (newMinute < 10) {
				newMinuteStringed = "0" + Integer.toString(newMinute); //adding a leading 0 if it's a one digit number
			} else {
				newMinuteStringed = Integer.toString(newMinute);
			}
			
			if (formsArithmeticSequence(Integer.toString(newHour), newMinuteStringed)) {
				arithmeticSequences++;
			}
		}
		
		System.out.println(arithmeticSequences);
		scn.close();
	}
	
	public static boolean formsArithmeticSequence(String hour, String minute) {
		String totalString = hour + minute;
		String[] digitsStringed = totalString.split("");
		int[] digits = new int[totalString.length()];
		
		for (int i = 0 ; i < digitsStringed.length ; i++) {
			int digit = Integer.parseInt(digitsStringed[i]);
			digits[i] = digit;
		}
		
		int commonDifference = digits[1] - digits[0];
		
		for (int i = 2 ; i < digits.length ; i++) { //starting at the 3rd element because the first 2 define the common difference
			int difference = digits[i] - digits[i-1];
			
			if (difference != commonDifference) {
				return false;
			}
		}
		
		return true; //difference was always equal
	}
}
