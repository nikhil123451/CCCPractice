import java.util.Scanner;

public class QuadrantSelection {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] agrs) {
		int xCoordinate = scn.nextInt();
		int yCoordinate = scn.nextInt();
		
		int quadrant;
		if (xCoordinate > 0) {
			if (yCoordinate > 0) {
				quadrant = 1;
			} else {
				quadrant = 4;
			}
		} else {
			if (yCoordinate > 0) {
				quadrant = 2;
			} else {
				quadrant = 3;
			}
		}
		
		System.out.println(quadrant);
	}
	
}
