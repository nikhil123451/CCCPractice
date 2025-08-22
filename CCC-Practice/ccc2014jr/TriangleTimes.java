import java.util.Scanner;

public class TriangleTimes {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int angleA = scn.nextInt();
		int angleB = scn.nextInt();
		int angleC = scn.nextInt();
		
		if (angleA > 0 && angleA < 180 && angleB > 0 && angleB < 180 && angleC > 0 && angleC < 180) { //problem restrictions
			
			if (angleA == 60 && angleA == angleB && angleA == angleC) {
				System.out.println("Equilateral");
			
			} else if (angleA + angleB + angleC == 180) {
				
				if (angleA == angleB || angleA == angleC || angleB == angleC) {
					System.out.println("Isosceles");
				
				} else {
					System.out.println("Scalene");
				}
			
			} else {
				System.out.println("Error");
			}
		}
	}
}
