import java.util.*;

public class ArithmeticSquare { //taken from GPT and modified

	static final int SIZE = 3;
    static Scanner scn = new Scanner(System.in); //initializing a scanner

    public static void main(String[] args) { //main method
        Long[][] grid = new Long[SIZE][SIZE]; //creating a Long[][] grid array

        for (int i = 0; i < SIZE; i++) { //looping through every row
        	for (int j = 0; j < SIZE; j++) { //looping through every column
                String character = scn.next(); //getting the next character in the input
                if (!character.equals("X")) { //if the character is not an X
                	grid[i][j] = Long.parseLong(character); //record in the grid accordingly
                }
            }
        }
        scn.close(); //closing the scanner

        boolean modified; //boolean flag to check if we've properly, legally modified the grid
        do { //iteratively tries to fill the grid
            modified = false; //initially haven't modified anything
            for (int i = 0; i < SIZE; i++) { //looping through every row
            	modified |= fillRow(grid, i); //sets modified according to if anything was successfully modified
            }
            for (int j = 0; j < SIZE; j++) { //looping through every column
            	modified |= fillColumn(grid, j); //sets modified according to if anything was successfully modified
            }
        } while (modified); //repeat if something has been modified

        if (grid[1][1] == null) { //if the middle of the grid doesn't have an assigned value
            List<Long> candidates = new ArrayList<>(); //creating a list of candidates that satisfy the arithmetic rules of the grid
            if (grid[0][0] != null && grid[2][2] != null) { //if the top-left corner and the bottom-right corner have assigned values
            	candidates.add((grid[0][0]+grid[2][2])/2); //add the midpoint of those 2 (which will fit the arithmetic rules)
            }
            if (grid[0][2] != null && grid[2][0] != null) { //if the top-right corner and the bottom-left corner have assigned values
            	candidates.add((grid[0][2]+grid[2][0])/2); //add the midpoint of those 2 (which will fit the arithmetic rules)
            }
            for (int i = 0; i < SIZE; i++) { //looping through every column and row
                if (grid[i][0]!=null && grid[i][2]!=null) { //if the endpoints of row i have assigned values
                	candidates.add((grid[i][0]+grid[i][2])/2); //add the midpoint of those 2 (which will fit the arithmetic rules)
                }
                if (grid[0][i]!=null && grid[2][i]!=null) { //if the endpoints of column i have assigned values
                	candidates.add((grid[0][i]+grid[2][i])/2); //add the midpoint of those 2 (which will fit the arithmetic rules)
                }
            }
            grid[1][1] = candidates.isEmpty() ? 0L : candidates.get(0); //sets the middle of the grid to 0 if there are no candidates, but sets it to the first element in the candidates list otherwise
        }

        do { //iteratively tries to fill the grid again
            modified = false; //nothing has been modified initially
            for (int i = 0; i < SIZE; i++) { //looping through every row
            	modified |= fillRow(grid, i); //setting modified accordingly depending on if any successful changes were made
            }
            for (int j = 0; j < SIZE; j++) { //looping through every column
            	modified |= fillColumn(grid, j); //setting modified accordingly depending on if any successful changes were made
            }
        } while (modified); //repeat if there was a change made

        for (int i = 0; i < SIZE; i++) { //looping through every row
        	for (int j = 0; j < SIZE; j++) { //looping through every column
        		if (grid[i][j]==null) { //if the position is null
        			grid[i][j] = 0L; //set it to 0
        		}
        	}
        }
        
        if (!isArithmetic(grid)) { //if the entire grid is somehow not arithmetic
            long newCenter = (grid[0][1] + grid[2][1])/2; //try defining the new center as the midpoint between the top-middle position and the bottom middle position
            grid[1][1] = newCenter; //set the new center

            do { //filling the grid again after the modification
                modified = false; //nothing has been modified initially
                for (int i = 0; i < SIZE; i++) { //looping through every row
                	modified |= fillRow(grid, i); //set modified accordingly
                }
                for (int j = 0; j < SIZE; j++) { //looping through every column
                	modified |= fillColumn(grid, j); //set modified accordingly
                }
            } while (modified); //repeat if something has been modified
        }

        printGrid(grid); //print out the final grid (assuming that the whole grid is now arithmetic)
    }

    static boolean fillRow(Long[][] grid, int row) { //helper method to try and fill a given row
        Long elementA=grid[row][0], elementB=grid[row][1], elementC=grid[row][2]; //getting each element in the row
        if (elementA!=null && elementB!=null && elementC==null){ //if the first element and second element are assigned, but the other one is not
        	grid[row][2]=2*elementB - elementA; //sets element C accordingly (B + (B - A) = 2B - A)
        	return true; //something has been changed, so return true
        }
        if (elementA!=null && elementB==null && elementC!=null){ //if the first element and the last element are assigned, but the middle one is not
        	grid[row][1]=(elementA+elementC)/2; //set element B to their midpoint
        	return true; //something has been changed, so return true
        }
        if (elementA==null && elementB!=null && elementC!=null){ //if the second element and the last element are assigned, but the first one is not
        	grid[row][0]=2*elementB - elementC; //set element A accordingly (just like element C above)
        	return true; //something has been changed, so return true
        }
        return false; //return false if nothing could be changed for the row
    }

    static boolean fillColumn(Long[][] grid, int column) { //helper method to try and fill a given column
        Long elementA=grid[0][column], elementB=grid[1][column], elementC=grid[2][column]; //getting every element for the column
        if (elementA!=null && elementB!=null && elementC==null){ //if the first 2 elements are assigned, but not the last one
        	grid[2][column]=2*elementB - elementA; //set the last one accordingly
        	return true; //something has been changed
        }
        if (elementA!=null && elementB==null && elementC!=null){ //if the first and last elements are assigned, but not the middle one
        	grid[1][column]=(elementA+elementC)/2; //set the middle one accordingly
        	return true; //something has been changed
        }
        if (elementA==null && elementB!=null && elementC!=null){ //if the last 2 elements are assigned, but not the first one
        	grid[0][column]=2*elementB - elementC; //set the first one accordingly
        	return true; //something has been changed
        }
        return false; //return false if nothing could be changed for the column
    }

    static boolean isArithmetic(Long[][] grid) { //method to check if the whole grid follows the arithmetic rules
        for (int i=0;i<SIZE;i++) { //looping through every row
        	if (grid[i][0]==null || grid[i][1]==null || grid[i][2]==null || grid[i][1]-grid[i][0] != grid[i][2]-grid[i][1]) { //if there are any undefined spots or if the row is not arithmetic
        		return false; //the whole grid cannot be arithmetic
        	}
        }
        for (int j=0;j<SIZE;j++) { //looping through every column
        	if (grid[0][j]==null || grid[1][j]==null || grid[2][j]==null || grid[1][j]-grid[0][j] != grid[2][j]-grid[1][j]) { //if there are any undefined spots of if the column is not arithmetic
        		return false; //the whole grid cannot be arithmetic
        	
        	}
        }
        return true; //return true if no errors in the grid were found
    }

    static void printGrid(Long[][] grid) { //helper method to print out the entire grid in the specified format
        for (int i=0;i<SIZE;i++) //looping through each row
            System.out.println(grid[i][0]+" "+grid[i][1]+" "+grid[i][2]); //print out every element per row
    }
}