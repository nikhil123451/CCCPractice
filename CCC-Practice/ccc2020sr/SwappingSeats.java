import java.util.*;

public class SwappingSeats { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
    static final int GROUPS = 3;

    public static void main(String[] args) { //main method
        String inputString = scn.next(); //getting the input
        scn.close(); //closing the scanner
        int people = inputString.length(); //getting the number of people

        int[] groupTotals = new int[GROUPS]; //making an array to store the amount of people per group
        for (char groupIdentifier : inputString.toCharArray()) { //looping through the input string
            groupTotals[groupIdx(groupIdentifier)]++; //increment the total for the given group
        }

        String inputDoubled = inputString + inputString; //adding the input string to itself to "simulate" a circular table

        int[][] permutations = { //2d array to keep track of the total amount of permutations of A, B and C you can make
            {0, 1, 2},
            {0, 2, 1},
            {1, 0, 2},
            {1, 2, 0},
            {2, 0, 1},
            {2, 1, 0}
        };

        long minimumSwaps = Long.MAX_VALUE; //starting at the largest number of swaps

        for (int[] permutation : permutations) {

            int[] properGroupPositions = new int[GROUPS]; //array to keep track of the proper group positions
            for (int i = 0; i < GROUPS; i++) { //for every group
                properGroupPositions[permutation[i]] = i; //assigning the values accordingly
            }

            int lengthA = groupTotals[permutation[0]]; //getting the amount of people in group A
            int lengthB = groupTotals[permutation[1]]; //getting the amount of people in group B
            int lengthC = groupTotals[permutation[2]]; //getting the amount of people in group C

            int[][] positions = new int[GROUPS][GROUPS]; //2d array to keep track of which block group X is in

            for (int i = 0; i < lengthA; i++) //looping through the expected positions for group A
                positions[0][properGroupPositions[groupIdx(inputDoubled.charAt(i))]]++; //setting the position accordingly in the 2d array

            for (int i = lengthA; i < lengthA + lengthB; i++) //looping through the expected positions for group B
                positions[1][properGroupPositions[groupIdx(inputDoubled.charAt(i))]]++; //setting the position accordingly in the 2d array

            for (int i = lengthA + lengthB; i < people; i++) //looping through the expected positions for group C
                positions[2][properGroupPositions[groupIdx(inputDoubled.charAt(i))]]++; //setting the position accordingly in the 2d array

            for (int tableStart = 0; tableStart < people; tableStart++) { //looping through every potential "starting point" for the circular table

                if (tableStart > 0) { //if the start isn't the first element (by default)
                    slide(positions, inputDoubled, tableStart - 1, tableStart + lengthA - 1, 0, properGroupPositions); //slides down by 1 for block 0
                    slide(positions, inputDoubled, tableStart + lengthA - 1, tableStart + lengthA + lengthB - 1, 1, properGroupPositions); //slides down by 1 for block 1
                    slide(positions, inputDoubled, tableStart + lengthA + lengthB - 1, tableStart + people - 1, 2, properGroupPositions); //slides down by 1 for block 2
                }

                int[][] currentPositions = new int[GROUPS][GROUPS]; //making an array for the current positions in their blocks (we make modifications to this array w/o worrying about changing anything important)
                for (int i = 0; i < GROUPS; i++) //looping through every group
                    currentPositions[i] = positions[i].clone(); //set the position

                long swaps = 0; //making a swap counter

                for (int i = 0; i < GROUPS; i++) { //looping through every unordered row
                    for (int j = i + 1; j < GROUPS; j++) { //looping through every unordered column
                        int optimalSwaps = Math.min(currentPositions[i][j], currentPositions[j][i]); //gets the optimal amount of swaps to fix any discrepancies
                        swaps += optimalSwaps; //add it to the swap counter
                        currentPositions[i][j] -= optimalSwaps; //remove the misplacement as it is resolved
                        currentPositions[j][i] -= optimalSwaps; //remove the misplacement as it is resolved
                    }
                }

                int pairsToBeSwapped = 0; //keeping track of the number of pairs to be swapped
                for (int i = 0; i < GROUPS; i++) { //for every block
                    for (int j = 0; j < GROUPS; j++) { //for every group
                        if (i != j) pairsToBeSwapped += currentPositions[i][j]; //increment pairsToBeSwapped if they are on one of the 2 diagonals
                    }
                }

                swaps += 2L * (pairsToBeSwapped / 3); //you will have to do 2 swaps for every multiple of 3 (pairs will always be a multiple of 3)
                minimumSwaps = Math.min(minimumSwaps, swaps); //update minimum swaps accordingly
            }
        }

        System.out.println(minimumSwaps); //print out the result
    }

    static void slide(int[][] positions, String arrangement, int start, int end, int block, int[] properGroupPositions) { //helper method to slide the arrangement given the start, end, and other parameters to help
        positions[block][properGroupPositions[groupIdx(arrangement.charAt(start))]]--; //removes the outgoing person on the starting end
        positions[block][properGroupPositions[groupIdx(arrangement.charAt(end))]]++; //adds the incoming person on the end
    }

    static int groupIdx(char identifier) { //helper method to get the index of a given group identifier
        return identifier == 'A' ? 0 : (identifier == 'B' ? 1 : 2); //returns 0 if A, 1 if B, and 2 otherwise (C)
    }
}