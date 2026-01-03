import java.util.Scanner;

public class SurmisingASprintersSpeed {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int observations = scn.nextInt();
		
		int[] times = new int[observations];
		int[] positions = new int[observations];
		
		for (int i = 0 ; i < observations ; i++) {
			times[i] = scn.nextInt();
			positions[i] = scn.nextInt();
		}
		
		double maximumSpeed = 0;
		for (int i = 0 ; i < observations - 1 ; i++) {
			double deltaTime = times[i + 1] - times[i];
			double deltaPosition = positions[i + 1] - positions[i];
			
			double speed = Math.abs(deltaPosition / deltaTime);
			if (speed > maximumSpeed) maximumSpeed = speed;
		}
		
		System.out.println(maximumSpeed);
	}
}
