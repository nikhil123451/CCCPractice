import java.util.Scanner;

public class SpecialDay {
	static Scanner scn = new Scanner(System.in);
	
	static final int FEBRUARY = 2;
	static final int FEBRUARY_DAY = 18;
	
	public static void main(String[] args) {
		int month = scn.nextInt();
		int day = scn.nextInt();
		scn.close();
		
		if (month < FEBRUARY) {
			System.out.println("Before");
		} else if (month > FEBRUARY) {
			System.out.println("After");
		} else {
			if (day < FEBRUARY_DAY) {
				System.out.println("Before");
			} else if (day > FEBRUARY_DAY) {
				System.out.println("After");
			} else {
				System.out.println("Special");
			}
		}
	}
}
