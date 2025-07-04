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
				
				int nikkyDisplacement = 0;
				int byronDisplacement = 0;
				int nikkyStepCounter = totalSteps;
				int byronStepCounter = totalSteps;
				
				while (nikkyStepCounter > 0) {
					if (nikkyStepsForward <= nikkyStepCounter) {
						nikkyDisplacement += nikkyStepsForward;
					} else {
						nikkyStepsForward = nikkyStepCounter;
						nikkyDisplacement += nikkyStepsForward;
					}
					nikkyStepCounter -= nikkyStepsForward;
					
					if (nikkyStepCounter > 0) {
						if (nikkyStepsBackward <= nikkyStepCounter) {
							nikkyDisplacement -= nikkyStepsBackward;
						} else {
							nikkyStepsBackward = nikkyStepCounter;
							nikkyDisplacement -= nikkyStepsBackward;
						}
						nikkyStepCounter -= nikkyStepsBackward;
					}
				}
				while (byronStepCounter > 0) {
					if (byronStepsForward <= byronStepCounter) {
						byronDisplacement += byronStepsForward;
					} else {
						byronStepsForward = byronStepCounter;
						byronDisplacement += byronStepsForward;
					}
					byronStepCounter -= byronStepsForward;
					
					if (byronStepCounter > 0) {
						if (byronStepsBackward <= byronStepCounter) {
							byronDisplacement -= byronStepsBackward;
						} else {
							byronStepsBackward = byronStepCounter;
							byronDisplacement -= byronStepsBackward;
						}
						byronStepCounter -= byronStepsBackward;
					}
				}
				
				if (nikkyDisplacement > byronDisplacement) {
					System.out.println("Nikky");
				} else if (nikkyDisplacement == byronDisplacement) {
					System.out.println("Tied");
				} else {
					System.out.println("Byron");
				}
			}
		}
	}
}