import java.util.*;

public class BloodDistribution { //taken from GPT and modified

	static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {

        int[] bloodUnits = new int[8];
        int[] patients = new int[8];

        for (int i = 0; i < 8; i++) {
            bloodUnits[i] = scn.nextInt();
        }
        for (int i = 0; i < 8; i++) {
            patients[i] = scn.nextInt();
        }

        int[][] compatible = {
            {0},                         // O−
            {1, 0},                      // O+
            {2, 0},                      // A−
            {3, 2, 1, 0},                // A+
            {4, 0},                      // B−
            {5, 4, 1, 0},                // B+
            {6, 2, 4, 0},                // AB−
            {7, 6, 3, 2, 5, 4, 1, 0}     // AB+
        };

        int totalMatched = 0;

        for (int i = 0; i < 8; i++) {
            for (int donor : compatible[i]) {
                int unitsToGive = Math.min(patients[i], bloodUnits[donor]);
                patients[i] -= unitsToGive;
                bloodUnits[donor] -= unitsToGive;
                totalMatched += unitsToGive;
                if (patients[i] == 0) break;
            }
        }

        System.out.println(totalMatched);
    }
}
