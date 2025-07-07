import java.util.*;

public class KnightHop { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing scanner
	
    public static void main(String[] args) { //main method
        System.out.print(""); //print out an empty string to get input
        int startingX = scn.nextInt(); //set the starting x position
        int startingY = scn.nextInt(); //set the starting y position
        System.out.print(""); //print out an empty string yet again to get input
        int endingX = scn.nextInt(); //set the ending x position
        int endingY = scn.nextInt(); //set the ending y position
        
        if (startingX >= 1 && startingX <= 8 && startingY >= 1 && startingY <= 8 
        		&& endingX >= 1 && endingX <= 8 && endingY >= 1 && endingY <= 8) { //if all the coordinates meet the input specifications
        	System.out.println(getMinimumMoves(startingX, startingY, endingX, endingY)); //calculating the minimum moves and then outputting it
        }

        scn.close(); //closing the scanner after use (i should do this more often)
    }

    public static int getMinimumMoves(int startingX, int startingY, int endingX, int endingY) { //method to calculate the minimum moves for the knight to take to reach the end point
        if (startingX == endingX && startingY == endingY) { //if the knight is already at the endpoint
            return 0; //0 moves need to be made
        }
        
        int[] changesInX = {2, 1, -1, -2, -2, -1, 1, 2}; //defining x movement for the knight, which is correlated to the same index for the y movement
        int[] changesInY= {1, 2, 2, 1, -1, -2, -2, -1}; //defining y movement for the knight, which is correlated to the same index for the x movement

        boolean[][] visited = new boolean[9][9]; //creating an 8x8 board that can be 1-indexed, storing places that have already been visited
        Queue<int[]> queuedVisitedPositions = new LinkedList<>(); //creating a queue that stores all the visited positions of the knight to assist with the Breadth-First Search algorithm
        queuedVisitedPositions.add(new int[]{startingX, startingY, 0}); //add the starting position to the visited positions queue
        visited[startingX][startingY] = true; //set the starting position as visited

        while (!queuedVisitedPositions.isEmpty()) { //while the queue still has positions in it
            int[] current = queuedVisitedPositions.poll(); //sets the current array to the position and the amount of moves it took to get there
            int xPosition = current[0], yPosition = current[1], moves = current[2]; //sets the x position, y position, and the amount of moves from the array

            for (int i = 0; i < visited.length - 1; i++) { //iterating through the amount of tiles on the board
                int newXPosition = xPosition + changesInX[i]; //setting the new x position to the x portion of the L shape
                int newYPosition = yPosition + changesInY[i]; //setting the new y position to the y portion of the L shape

                if (newXPosition == endingX && newYPosition == endingY) { //if the new position of the knight is the endpoint
                    return moves + 1; //return the original moves +1 because a move has been made
                }

                if (newXPosition >= 1 && newXPosition <= visited.length - 1 && newYPosition >= 1 && newYPosition <= visited.length - 1 && !visited[newXPosition][newYPosition]) { //if the new position is in bounds, and it hasn't been visited before
                    visited[newXPosition][newYPosition] = true; //make sure the new position has been recorded as visited
                    queuedVisitedPositions.add(new int[]{newXPosition, newYPosition, moves + 1}); //add the new position to the queue
                }
            }
        }
        return -1; //return -1 if the program somehow breaks with input
    }
}