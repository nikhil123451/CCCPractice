import java.util.Scanner;

public class Epidemiology {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int infectionLimit = scn.nextInt();
		int initialInfected = scn.nextInt();
		int infectionsPerDay = scn.nextInt();
		
		int currentInfected = initialInfected;
		int day = 0;
		
		while (currentInfected <= infectionLimit) {
			day++;
			currentInfected += (int) initialInfected * Math.pow(infectionsPerDay, day);
		}
		
		System.out.println(day);
	}
}
