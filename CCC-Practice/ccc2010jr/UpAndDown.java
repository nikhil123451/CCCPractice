import java.util.Scanner;

public class UpAndDown {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		System.out.print("");
		int nikkyStepsForward = scn.nextInt();
		System.out.print("");
		int nikkyStepsBackward = scn.nextInt();
		System.out.print("");
		int byronStepsForward = scn.nextInt();
		System.out.print("");
		int byronStepsBackward = scn.nextInt();
		System.out.print("");
		int totalSteps = scn.nextInt();
		if (totalSteps >= 1 && totalSteps <= 10000) {
			if ((nikkyStepsForward >= 1 && nikkyStepsForward <= 100) && 
				(nikkyStepsBackward >= 1 && nikkyStepsBackward <= 100) &&
				(byronStepsForward >= 1 && byronStepsForward <= 100) &&
				(byronStepsBackward >= 1 && byronStepsBackward <= 100)) {
				System.out.println(nikkyStepsForward);
				System.out.println(nikkyStepsBackward);
				System.out.println(byronStepsForward);
				System.out.println(byronStepsBackward);
				System.out.println(totalSteps);
			}
		}
	}
}