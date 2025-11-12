import java.util.*;

public class Sunflowers { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        
        int flowers = scn.nextInt(); //getting the number of sunflowers
        int[][] grid = new int[flowers][flowers]; //creating the grid of flower data

        for (int i = 0; i < flowers; i++) { //looping through every row in the grid
            for (int j = 0; j < flowers; j++) { //looping through every column
                grid[i][j] = scn.nextInt(); //setting the coordinate appropriately
            }
        }

        while (!isSorted(grid, flowers)) { //while the grid is not sorted properly, as per the problem specifications
            grid = rotate(grid, flowers); //rotate the grid
        }

        for (int i = 0; i < flowers; i++) { //looping through every row
            for (int j = 0; j < flowers; j++) { //looping through every column
                System.out.print(grid[i][j] + " "); //printing out the coordinate plus a space
            }
            System.out.println(); //going down the the next row
        }
    }

    static boolean isSorted(int[][] grid, int flowers) { //helper method to check if the given grid is sorted with a flower count
        for (int i = 0; i < flowers; i++) { //looping through every row
            for (int j = 1; j < flowers; j++) { //looping through every column but the first one
                if (grid[i][j] <= grid[i][j - 1]) { //if the value in the column in front is less than the value in the column before it
                	return false; //return false because they aren't increasing values
                }
            }
        }
        for (int i = 1; i < flowers; i++) { //looping through every row but the first one
            for (int j = 0; j < flowers; j++) { //looping through every column
                if (grid[i][j] <= grid[i - 1][j]) { //if the value in the current row is less than the value of the previous row
                	return false; //return false because they aren't increasing values
                }
            }
        }
        return true; //return true if all values are increasing
    }

    static int[][] rotate(int[][] grid, int flowers) { //method to "rotate" the given grid
        int[][] newGrid = new int[flowers][flowers]; //creating a new grid to place all the new values
        for (int i = 0; i < flowers; i++) { //looping through every row
            for (int j = 0; j < flowers; j++) { //looping through every column
                newGrid[i][j] = grid[flowers - j - 1][i]; //rotation algorithm to rotate clockwise 90 degrees
            }
        }
        return newGrid; //return the final grid after rotation
    }
}