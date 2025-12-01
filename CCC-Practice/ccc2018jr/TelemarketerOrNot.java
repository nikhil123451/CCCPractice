import java.util.Scanner;

public class TelemarketerOrNot {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int[] digits = new int[4]; //storing the last 4 digits of a phone number
		for (int i = 0 ; i < 4 ; i++) {
			digits[i] = scn.nextInt();
		}
		
		if (isTelemarketer(digits)) {
			System.out.println("ignore");
		} else {
			System.out.println("answer");
		}
	}
	
	public static boolean isTelemarketer(int[] digits) {
		if ((digits[0] == 8 || digits[0] == 9) && (digits[3] == 8 || digits[3] == 9)) { //if the first and last digits are either 8s or 9s
			if (digits[1] == digits[2]) { //if the middle digits are identical
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
