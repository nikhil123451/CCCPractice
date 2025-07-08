import java.util.Scanner;

public class WhichAlien {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		System.out.println("How many antennas?");
		System.out.print("");
		int antenna = scn.nextInt();
		System.out.println("How many eyes?");
		System.out.print("");
		int eyes = scn.nextInt();
		
		if (antenna >= 3 && eyes <= 4) {
			System.out.println("TroyMartian");
		}
		if (antenna <= 6 && eyes >= 2) {
			System.out.println("VladSaturnian");
		}
		if (antenna <= 2 && eyes <= 3) {
			System.out.println("GraemeMercurian");
		}
	}
}
