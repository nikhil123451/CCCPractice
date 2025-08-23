import java.util.Scanner;

public class VoteCount {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int votes = scn.nextInt();
		
		if (votes >= 1 && votes <= 15) {
			String voteSequence = scn.next();
			scn.close();
			
			String[] individualVotes = voteSequence.split("");
			
			if (votes != individualVotes.length) {
				return;
			}
			
			int countA = 0;
			int countB = 0;
			
			for (String vote : individualVotes) {
				if (vote.contains("A")) {
					countA++;
				} else if (vote.contains("B")) {
					countB++;
				} else {
					System.out.println("inout the votes properly");
					return;
				}
			}
			
			if (countA > countB) {
				System.out.println("A");
			} else if (countB > countA) {
				System.out.println("B");
			} else if (countA == countB) {
				System.out.println("Tie");
			} else {
				System.out.println("program broke"); //code should never reach here
			}
		}
	}
}
