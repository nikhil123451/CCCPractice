import java.util.Scanner;

public class WhoHasSeenTheWind {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		boolean reachedGround = false;
		System.out.print("");
		int humidityFactor = scn.nextInt();
		System.out.print("");
		int maximumTime = scn.nextInt();
		
		if (humidityFactor >= 0 && maximumTime >= 0) {
			for (int time = 1 ; time <= maximumTime ; time++) {
				double altitude = (-6 * Math.pow(time, 4)) + (humidityFactor * Math.pow(time, 3)) + (2 * (time) * (time)) + time; //formula from question
				if (altitude <= 0) {
					System.out.println("The balloon first touches ground at hour:");
					System.out.println(time);
					reachedGround = true;
					break;
				}
			}
			if (!reachedGround) {
				System.out.println("The balloon does not touch ground in the given time.");
			}
		}
	}
}
