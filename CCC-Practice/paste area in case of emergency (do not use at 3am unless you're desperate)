import java.util.*;

public class NailedIt { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] freq = new int[2001]; // wood lengths range from 1 to 2000

        for (int i = 0; i < N; i++) {
            int length = sc.nextInt();
            freq[length]++;
        }

        int maxFenceLength = 0;      // number of boards in the longest fence
        int numMaxHeights = 0;       // number of different heights achieving that maximum

        // possible board lengths go from 2 to 4000 (sum of two pieces)
        for (int s = 2; s <= 4000; s++) {
            int boards = 0;

            // combine pairs that sum to s
            for (int i = 1; i <= s / 2 && i <= 2000; i++) {
                int j = s - i;
                if (j < 1 || j > 2000) continue;

                if (i == j) {
                    boards += freq[i] / 2;
                } else {
                    boards += Math.min(freq[i], freq[j]);
                }
            }

            if (boards > maxFenceLength) {
                maxFenceLength = boards;
                numMaxHeights = 1;
            } else if (boards == maxFenceLength && boards > 0) {
                numMaxHeights++;
            }
        }

        System.out.println(maxFenceLength + " " + numMaxHeights);
    }
}