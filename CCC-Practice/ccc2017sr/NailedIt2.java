import java.util.Scanner;

public class NailedIt2 {
	
	static Scanner scn = new Scanner(System.in);
	static final int MAXIMUM_WOOD_LENGTH = 2000;
	static final int MINIMUM_WOOD_LENGTH = 1;
	
    public static void main(String[] args) {
        int woodPieces = scn.nextInt();
        int[] frequencies = new int[MAXIMUM_WOOD_LENGTH + 1];

        for (int i = 0; i < woodPieces; i++) {
            int length = scn.nextInt();
            frequencies[length]++;
        }
        scn.close();

        int maximumFenceLength = 0;
        int maximumFenceHeights = 0;
        
        for (int sumOfPieces = 2 * MINIMUM_WOOD_LENGTH; sumOfPieces <= 2 * MAXIMUM_WOOD_LENGTH; sumOfPieces++) {
            int boards = 0;

            for (int i = MINIMUM_WOOD_LENGTH; i <= sumOfPieces / 2 && i <= MAXIMUM_WOOD_LENGTH; i++) {
                int j = sumOfPieces - i;
                if (j < MINIMUM_WOOD_LENGTH || j > MAXIMUM_WOOD_LENGTH) {
                	continue;
                }

                if (i == j) {
                    boards += frequencies[i] / 2;
                } else {
                    boards += Math.min(frequencies[i], frequencies[j]);
                }
            }

            if (boards > maximumFenceLength) {
                maximumFenceLength = boards;
                maximumFenceHeights = 1;
            } else if (boards == maximumFenceLength && boards > 0) {
                maximumFenceHeights++;
            }
        }

        System.out.println(maximumFenceLength + " " + maximumFenceHeights);
    }
}