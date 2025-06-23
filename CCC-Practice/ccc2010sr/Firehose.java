import java.util.*;

public class Firehose { //taken from GPT
	
	static Scanner scn = new Scanner(System.in);
	static final int CIRCLE_CIRCUMFERENCE = 1_000_000;

    public static void main(String[] args) {
    	System.out.print("");
    	int numberOfHouses = scn.nextInt();
    	if (numberOfHouses >= 1 && numberOfHouses <= 1000) {
    		int[] housePositions = new int[numberOfHouses];
    		for (int i = 0 ; i < numberOfHouses ; i++) {
    			System.out.print("");
    			housePositions[i] = scn.nextInt();;
    		}
    		
    		System.out.print("");
            int fireHydrants = scn.nextInt();
            if (fireHydrants >= 1 && fireHydrants <= 1000) {
        	}
            int minHoseLength = findMinimumHoseLength(housePositions, fireHydrants);
            System.out.println(minHoseLength);
    	}
    }
    
    public static int findMinimumHoseLength(int[] housePositions, int fireHydrants) {
        Arrays.sort(housePositions);

        int low = 0;
        int high = CIRCLE_CIRCUMFERENCE / 2;

        while (low < high) { //binary search
            int mid = (low + high) / 2;
            if (canCoverAll(housePositions, fireHydrants, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
    
    public static boolean canCoverAll(int[] housePositions, int fireHydrants, int mid) {
        int[] extendedArray = new int[2 * housePositions.length];
        
        for (int i = 0; i < housePositions.length; i++) { //fill array with house positions + house positions with full rotation
            extendedArray[i] = housePositions[i];
            extendedArray[i + housePositions.length] = housePositions[i] + CIRCLE_CIRCUMFERENCE;
        }
        
        for (int start = 0; start < housePositions.length; start++) {
            int hydrantsUsed = 0;
            int i = start;

            while (i < start + housePositions.length) { //looping through all regular house positions
                int coverUntil = extendedArray[i] + 2 * mid; //max length hydrant can cover
                hydrantsUsed++;

                while (i < start + housePositions.length && extendedArray[i] <= coverUntil) { //looping again + checking if the house position is <= max cover dist 
                    i++;
                }
            }

            if (hydrantsUsed <= fireHydrants) { //if the hydrants used can actually cover everything
            	return true;
            }
        }

        return false;
    }
}
