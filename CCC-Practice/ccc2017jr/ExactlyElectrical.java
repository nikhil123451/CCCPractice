import java.util.Scanner;

public class ExactlyElectrical {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int startingXCoordinate = scn.nextInt();
		int startingYCoordinate= scn.nextInt();
		
		int destinationXCoordinate = scn.nextInt();
		int destinationYCoordinate = scn.nextInt();
		
		int charge = scn.nextInt();
		
		int distance = Math.abs(destinationXCoordinate - startingXCoordinate) + Math.abs(destinationYCoordinate - startingYCoordinate);
		
		if (charge >= distance && (charge - distance) % 2 == 0) { //if there's enough charge to reach destination and if the extra charge is even
			System.out.println("Y");
		} else {
			System.out.println("N");
		}
	}
}
