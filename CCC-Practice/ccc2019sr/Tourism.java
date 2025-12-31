import java.util.*;

public class Tourism { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in); //initializing a scanner
    static int attractions, attractionsPerDay; //declaring attractions and attractions per day variables
    static long[] attractionScores;
    static int[] treeIndices; //array to store multiple indices for the scores array

    public static void main(String[] args) { //main method
        attractions = scn.nextInt(); //getting the number of attractions
        attractionsPerDay = scn.nextInt(); //getting the maximum attractions per day

        attractionScores = new long[attractions]; //initializing the attractionScores array
        for (int i = 0; i < attractions; i++) { //looping through the array
            attractionScores[i] = scn.nextLong(); //adding the scores to the array
        }

        treeIndices = new int[4 * attractions]; //initializing the indices array as 4 times the number of attractions to overestimate the size
        buildSegmentedTree(1, 0, attractions - 1); //building the segmented tree from node 1 (index 0) to the last node (index n - 1)

        int days = (attractions + attractionsPerDay - 1) / attractionsPerDay; //calculating the number of days
        long maximumTotalScore = 0; //creating a variable to keep track of the total score

        int rightIndex = attractions - 1; //getting the rightmost index of the tree

        for (int d = 0; d < days; d++) { //for every day
            int leftIndex = Math.max(0, rightIndex - attractionsPerDay + 1); //gets the leftmost index for the block we want to search, ensuring we don't leave the bounds of the array
            int maximumIndex = findMaximumScoreIndex(1, 0, attractions - 1, leftIndex, rightIndex); //finding the index of the attraction with the maximum score in the block
            maximumTotalScore += attractionScores[maximumIndex]; //adding the score to the total
            rightIndex = maximumIndex - 1; //decrementing the new rightmost index for the new block
        }

        System.out.println(maximumTotalScore); //printing out the final total
        scn.close(); //closing the scanner
    }

    static void buildSegmentedTree(int node, int leftIndex, int rightIndex) { //method to build the segmented tree for searching
        if (leftIndex == rightIndex) { //if left index is the same as the right index
            treeIndices[node] = leftIndex; //store the index of the node
            return; //finished building the tree at this branch
        }
        int midpoint = (leftIndex + rightIndex) / 2; //getting the middle index between the left and right indices
        buildSegmentedTree(node * 2, leftIndex, midpoint); //building the left-half of the tree (containing even nodes)
        buildSegmentedTree(node * 2 + 1, midpoint + 1, rightIndex); //building the right-half of the tree (containing odd nodes)

        int maximumLeftIndex = treeIndices[node * 2]; //getting the maximum index in the left half
        int maximumRightIndex = treeIndices[node * 2 + 1]; //getting the maximum index in the right half
        treeIndices[node] = (attractionScores[maximumLeftIndex] >= attractionScores[maximumRightIndex]) ? maximumLeftIndex : maximumRightIndex; //setting the maximum value of the index of the node between the 2 indices
    }

    static int findMaximumScoreIndex(int node, int leftIndex, int rightIndex, int leftBound, int rightBound) { //method to find the maximum score index within a range
        if (rightBound < leftIndex || rightIndex < leftBound) { //if the bounds are not within the left and right most indices
        	return -1; //not possible so return -1
        }
        if (leftBound <= leftIndex && rightIndex <= rightBound) { //if the bounds are completely within the left and right most indices
        	return treeIndices[node]; //return the maximum value index of the node
        }

        int midpoint = (leftIndex + rightIndex) / 2; //getting the middle index between the leftmost and rightmost indices
        int maximumLeftIndex = findMaximumScoreIndex(node * 2, leftIndex, midpoint, leftBound, rightBound); //getting the maximum valued index for the left-hand-side
        int maximumRightIndex = findMaximumScoreIndex(node * 2 + 1, midpoint + 1, rightIndex, leftBound, rightBound); //getting the maximum valued index for the right-hand-side

        if (maximumLeftIndex == -1) { //if the left maximum index is not in bounds
        	return maximumRightIndex; //return the right one
        }
        if (maximumRightIndex == -1) { //if the right maximum index is not in bounds
        	return maximumLeftIndex; //return the left one
        }
        return (attractionScores[maximumLeftIndex] >= attractionScores[maximumRightIndex]) ? maximumLeftIndex : maximumRightIndex; //return the greater valued one between the 2
    }
}