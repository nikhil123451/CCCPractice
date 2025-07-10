import java.util.Scanner;

public class SumacSequences {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		System.out.print("");
		int termOne = scn.nextInt();
		System.out.print("");
		int termTwo = scn.nextInt();
		
		if (termOne > termTwo && termTwo > 0 && termOne > 0 && termTwo < 10000 && termOne < 10000) {
			int difference = termOne - termTwo;
			int sequenceCounter = 2; //starts with 2 terms
			int previousTerm = termTwo;
			while (difference > 0) {
				int newTerm = difference;
				sequenceCounter++;
				difference = previousTerm - newTerm;
				previousTerm = newTerm;
			}
			System.out.println(sequenceCounter);
		}
	}
}
