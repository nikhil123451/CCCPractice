import java.util.*;

public class Firehose { //taken from GPT
	
	static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        int[] housePositions = {0, 67000, 68000, 77000};
        int k = 1;

        int minHoseLength = findMinimumHoseLength(housePositions, k);
        System.out.println("Minimum maximum hose length: " + minHoseLength);
    }
    
    public static int findMinimumHoseLength(int[] houses, int k) {
        Arrays.sort(houses);

        int low = 0;
        int high = 1_000_000 / 2;

        while (low < high) {
            int mid = (low + high) / 2;
            if (canCoverAll(houses, k, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
    
    public static boolean canCoverAll(int[] houses, int k, int L) {
        int n = houses.length;
        int[] extended = new int[2 * n];
        
        for (int i = 0; i < n; i++) {
            extended[i] = houses[i];
            extended[i + n] = houses[i] + 1_000_000;
        }
        
        for (int start = 0; start < n; start++) {
            int hydrantsUsed = 0;
            int i = start;

            while (i < start + n) {
                int coverUntil = extended[i] + 2 * L;
                hydrantsUsed++;

                while (i < start + n && extended[i] <= coverUntil) {
                    i++;
                }
            }

            if (hydrantsUsed <= k) {
            	return true;
            }
        }

        return false;
    }
}
