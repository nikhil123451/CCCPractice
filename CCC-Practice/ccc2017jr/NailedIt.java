import java.util.*;

public class NailedIt { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	static final int MAXIMUM_WOOD_LENGTH = 2000;
	static final int MINIMUM_WOOD_LENGTH = 1;
	
    public static void main(String[] args) { //main method
        int woodPieces = scn.nextInt(); //getting the amount of wood pieces
        int[] frequencies = new int[MAXIMUM_WOOD_LENGTH + 1]; //creating a frequency array for every wood length (+1 cuz java is 0-indexed)

        for (int i = 0; i < woodPieces; i++) { //looping through every wood piece
            int length = scn.nextInt(); //get its length
            frequencies[length]++; //increment the length in the frequency array
        }
        scn.close(); //closing the scanner

        int maximumFenceLength = 0; //variable to keep track of the longest fence length
        int maximumFenceHeights = 0; //variable to keep track of the possible heights given a fence length
        
        for (int sumOfPieces = 2 * MINIMUM_WOOD_LENGTH; sumOfPieces <= 2 * MAXIMUM_WOOD_LENGTH; sumOfPieces++) { //looping through each possible sum of wood lengths
            int boards = 0; //variable to keep track of the boards that can be made

            for (int i = MINIMUM_WOOD_LENGTH; i <= sumOfPieces / 2 && i <= MAXIMUM_WOOD_LENGTH; i++) { //looping through individual wood piece lengths
                int j = sumOfPieces - i; //getting the other pair that goes with piece i
                if (j < MINIMUM_WOOD_LENGTH || j > MAXIMUM_WOOD_LENGTH) { //if j does not fit the bounds of a wood piece
                	continue; //keep going through the loop
                }

                if (i == j) { //if the 2 pieces are of equal length
                    boards += frequencies[i] / 2; //getting the number of boards made with 2 pieces
                } else { //if the pieces are not of equal length
                    boards += Math.min(frequencies[i], frequencies[j]); //getting the number of boards made by getting the frequency of the material not in access
                }
            }

            if (boards > maximumFenceLength) { //if the board count is greater than the recorded maximum
                maximumFenceLength = boards; //set the new maximum as the amount of boards
                maximumFenceHeights = 1; //only 1 board height achieved this record
            } else if (boards == maximumFenceLength && boards > 0) { //if the board amount is equal to the current maximum and it's a positive integer
                maximumFenceHeights++; //increase the amount of board heights that made it to the maximum
            }
        }

        System.out.println(maximumFenceLength + " " + maximumFenceHeights); //print out the result
    }
}