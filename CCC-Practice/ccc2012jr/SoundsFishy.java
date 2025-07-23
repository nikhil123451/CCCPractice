import java.util.*;

public class SoundsFishy {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		ArrayList<String> directions = new ArrayList<String>();
		int[] depths = new int[4];
		String direction = "neutral";
		
		for (int i = 0 ; i < depths.length ; i++) {
			int depth = scn.nextInt();
			depths[i] = depth;
		}
		
		for (int i = 0 ; i < depths.length ; i++) {
			int currentDepth = depths[i];
			if (currentDepth != depths[0]) {
				int previousDepth = depths[i-1];
				int difference = currentDepth - previousDepth;
				if (difference < 0) {
					direction = "down";
				} else if (difference > 0) {
					direction = "up";
				} else if (difference == 0) {
					direction = "neutral";
				} else {
					System.out.println("program broke"); //shouldn't reach here
				}
				directions.add(direction);
			}
		}
		
		String firstDirection = directions.get(0);
		boolean consistentDirection = true;
		for (int i = 0 ; i < directions.size(); i++) {
			if (!(directions.get(i).contains(firstDirection))) {
				System.out.println("No Fish");
				consistentDirection = false;
				break;
			}
		}
		
		if (consistentDirection) {
			if (firstDirection.contains("down")) {
				System.out.println("Fish Diving");
			} else if (firstDirection.contains("up")) {
				System.out.println("Fish Rising");
			} else if (firstDirection.contains("neutral")) {
				System.out.println("Constant Depth");
			} else {
				System.out.println("broke :("); //shouldn't reach here
			}
		}
		scn.close();
	}
}
