import java.util.Scanner;

public class SpeedingFinesAreNotFine {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.print("Enter the speed limit: ");
		int speedLimit = scn.nextInt();
		System.out.print("Enter the recorded speed of the car: ");
		int carSpeed = scn.nextInt();
		
		int speedOver = carSpeed - speedLimit;
		
		if (speedOver < 1) {
			System.out.println("Congratulations, you are within the speed limit!");
		} else {
			if (speedOver >= 1 && speedOver <= 20) {
				System.out.println("You are speeding and your fine is $100");
			} else if (speedOver >= 21 && speedOver <= 30) {
				System.out.println("You are speeding and your fine is $270");
			} else if (speedOver >= 31) {
				System.out.println("You are speeding and your fine is $500");
			} else {
				System.out.println("bro your speed broke the program");
			}
		}
		scn.close();
	}
}
