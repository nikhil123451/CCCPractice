import java.util.*;

public class MouseJourney { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        
        int rows = scn.nextInt(); //getting the number of rows
        int columns = scn.nextInt(); //getting the number of columns
        int catCages = scn.nextInt(); //getting the amount of cages w/ cats in them
        
        if (rows >= 1 && columns <= 25) { //if the rows and columns meets the input specifications
        	boolean[][] hasCat = new boolean[rows + 1][columns + 1]; //make a 2d array of the map, indicating which cages have cats (+1 cuz java is 0-indexed)

            for (int i = 0; i < catCages; i++) { //looping through every cat cage
                int rowCoordinate = scn.nextInt(); //getting the row coord of the cage
                int columnCoordinate = scn.nextInt(); //getting the column coord of the cage
                hasCat[rowCoordinate][columnCoordinate] = true; //setting the cage to at the specified coords to contain a cat
            }

            int[][] floodArray = new int[rows + 1][columns + 1]; //making an int array to keep track of the paths to get to the cage
            floodArray[1][1] = 1; //the starting upper corner

            for (int i = 1; i <= rows; i++) { //looping through each row
                for (int j = 1; j <= columns; j++) { //looping each column per row
                    if (hasCat[i][j]) { //if there is a cat cage at the given coords
                        floodArray[i][j] = 0; //set that value as 0, as that's the target
                    } else { //if it's a regular cage
                        if (i > 1) { //if the row isn't the first one
                        	floodArray[i][j] += floodArray[i - 1][j]; //flood to the right by incrementing the current tile by the previous amount (usually being a 1, but can be more if that path has been crossed before)
                        }
                        if (j > 1) { //if the column isn't the first one
                        	floodArray[i][j] += floodArray[i][j - 1]; //flood downward by incrementing the current tile by the previous amount (usually being 1, but can be more if that path has been crossed before)
                        }
                    }
                }
            }

            System.out.println(floodArray[rows][columns]); //print out the value at the brother's cage, which will be the amount of paths that reached there
            scn.close(); //close the scanner
        }
    }
}
