import java.util.Scanner;
import java.util.ArrayList;

public class SilentAuction {
	static Scanner scn = new Scanner(System.in);
	static int people;
	
	public static void main(String[] args) {
		people = scn.nextInt();
		scn.nextLine(); //parsing through \n
		String[] names = new String[people];
		int[] bids = new int[people];
		
		for (int i = 0 ; i < people ; i++) {
			names[i] = scn.nextLine();
			bids[i] = scn.nextInt();
			scn.nextLine(); //parsing through \n
		}
		scn.close();
		
		ArrayList<Integer> highestBidIndices = new ArrayList<>();
		int highestBid = 0;
		
		for (int i = 0 ; i < bids.length ; i++) {
			int bid = bids[i];
			if (bid > highestBid) {
				highestBid = bid;
			}
		}
		
		for (int i = 0 ; i < bids.length ; i++) {
			int bid = bids[i];
			if (bid == highestBid) {
				highestBidIndices.add(i);
			}
		}
		
		System.out.println(names[highestBidIndices.getFirst()]);
	}
}
