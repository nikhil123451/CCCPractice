import java.util.*;

public class RoboThieves { //taken from GPT and modified

    static int gridLength, gridWidth; //declaring variables for the grid's length and width
    static char[][] grid; //making a 2d char array for the grid
    static boolean[][] visible; //a boolean array to keep track of where cameras can see on the grid
    static int[][] distances; //declaring a 2d array to keep track of the distances from the starting point to every other cell in the grid (-1 if the cell isn't reachable)
    static int[] rowMovement = {-1, 1, 0, 0}; //array for up and down movement
    static int[] columnMovement = {0, 0, -1, 1}; //array for left and right movement

    static final int IMPOSSIBLE_TRAVEL_VALUE = -1; //-1 represents the value where the robot cannot reach the cell
    static final int DIRECTIONS = 4;
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
    public static void main(String[] args) { //main method
        gridLength = scn.nextInt(); //getting the grid's length
        gridWidth = scn.nextInt(); //getting the grid's width
        grid = new char[gridLength][gridWidth]; //making the grid with the given dimensions

        int startingRowCoordinate = 0; 
        int startingColumnCoordinate = 0;

        for (int i = 0; i < gridLength; i++) { //looping through the rows
            String line = scn.next(); //getting the data for the row
            for (int j = 0; j < gridWidth; j++) { //looping through each column position for the row
                grid[i][j] = line.charAt(j); //setting the grid value accordingly
                if (grid[i][j] == 'S') { //if the cell i the starting space
                    startingRowCoordinate = i; //set the row coordinate accordingly
                    startingColumnCoordinate = j; //set the column coordinate accordingly
                }
            }
        }

        visible = new boolean[gridLength][gridWidth]; //initializing the visible array
        markVisibleCells(); //activating the boolean flag for every "visible" cell

        distances = new int[gridLength][gridWidth]; //initializing the distances array
        for (int[] row : distances) { //looping through every row in the 2d array
        	Arrays.fill(row, IMPOSSIBLE_TRAVEL_VALUE); //filling everything in the array with -1 initially
        }

        searchPaths(startingRowCoordinate, startingColumnCoordinate); //searching the paths to every empty cell and recording the distances

        for (int i = 0; i < gridLength; i++) { //looping through every row
            for (int j = 0; j < gridWidth; j++) { //looping through every column position for the row
                if (grid[i][j] == '.') { //if the cell is an empty cell
                    System.out.println(distances[i][j]); //print out the distance recorded for the cell
                }
            }
        }
    }

    static void markVisibleCells() { //helper method to mark cells that are "visible"
        for (int r = 0; r < gridLength; r++) { //looping through each row
            for (int c = 0; c < gridWidth; c++) { //looping through each column position for the row
                if (grid[r][c] == 'C') { //if the cell is a camera
                	
                    for (int newRow = r - 1; newRow >= 0; newRow--) { //looping through every row from the current one up
                        if (grid[newRow][c] == 'W') { //if there is a wall in the way
                        	break; //go to the next direction
                        }
                        if (grid[newRow][c] == '.') { //if the cell is an empty cell
                        	visible[newRow][c] = true; //set the cell's visibility to true
                        }
                    }
                    
                    for (int newRow = r + 1; newRow < gridLength; newRow++) { //looping through every row from the current one down
                        if (grid[newRow][c] == 'W') { //if there is a wall in the way
                        	break; //go to the next direction
                        }
                        if (grid[newRow][c] == '.') { //if the cell is an empty cell
                        	visible[newRow][c] = true; //set the cell's visibility to true
                        }
                    }
                    
                    for (int newColumn = c - 1; newColumn >= 0; newColumn--) { //looping through every column from the current one left
                        if (grid[r][newColumn] == 'W') { //if there is a wall in the way
                        	break; //go to the next direction
                        }
                        if (grid[r][newColumn] == '.') { //if the cell is an empty cell
                        	visible[r][newColumn] = true; //set the cell's visibility to true
                        }
                    }
                    
                    for (int newColumn = c + 1; newColumn < gridWidth; newColumn++) { //looping through every column from the current one right
                        if (grid[r][newColumn] == 'W') { //if there's a wall in the way
                        	break; //go to the next camera
                        }
                        if (grid[r][newColumn] == '.') { //if the cell is an empty cell
                        	visible[r][newColumn] = true; //set the cell's visibility to true
                        }
                    }
                }
            }
        }
    }

    static int[] followConveyor(int row, int column) { //method to process conveyor logic and output the new coordinates of the robot's location
        boolean[][] visited = new boolean[gridLength][gridWidth]; //making a boolean array to check if a conveyor's been visited or not
        int currentRow = row, currentColumn = column; //setting the current row and column accordingly

        while (true) { //keep looping until we need to break
            if (grid[currentRow][currentColumn] != 'L' && grid[currentRow][currentColumn] != 'R' && grid[currentRow][currentColumn] != 'U' && grid[currentRow][currentColumn] != 'D') { //if the current cell isn't a conveyor
                return new int[]{currentRow, currentColumn}; //return the current position
            }
            if (visited[currentRow][currentColumn]) { //if we've already visited the conveyor
            	return null; //return null since we're in a loop
            }
            visited[currentRow][currentColumn] = true; //set this conveyor as visited

            int newRow = currentRow, newColumn = currentColumn; //setting newRow and newColumn to the current ones for now
            switch (grid[currentRow][currentColumn]) { //switching the conveyor type
                case 'L': //if it's a left-facing conveyor
                	newColumn--; //move the robot's position left
                	break; //break the loop
                case 'R':  //if it's a right-facing conveyor
                	newColumn++; //move the robot's position right
                	break; //break the loop
                case 'U': //if it's a upward-facing conveyor
                	newRow--; //move the robot's position up
                	break; //break the loop
                case 'D': //if it's a downward-facing conveyor
                	newRow++;  //move the robot's position down
                	break; //break the loop
            }

            if (grid[newRow][newColumn] == 'W' || grid[newRow][newColumn] == 'C') { //if the robot's new coordinates are in a wall or camera
            	return null; //return null since robots can't do that
            }
            currentRow = newRow; //set the new current row accordingly
            currentColumn = newColumn; //set the new current column accordingly
        }
    }

    static void searchPaths(int startingRow, int startingColumn) { //breadth-first search method to find distances to empty cells
        Queue<int[]> pathQueue = new LinkedList<>(); //creating a pathQueue to keep track of paths to check
        pathQueue.add(new int[]{startingRow, startingColumn}); //adding the robot's starting position to the queue
        distances[startingRow][startingColumn] = 0; //the distance from the starting point to the starting point is 0

        while (!pathQueue.isEmpty()) { //while there are paths to check
            int[] currentPath = pathQueue.poll(); //getting the top-most path to check
            int row = currentPath[0], column = currentPath[1]; //getting the row and column coordinates for the path
            int currentDistance = distances[row][column]; //getting the distance from the starting position

            for (int direction = 0; direction < DIRECTIONS; direction++) { //looping through every direction the robot can travel in
                int newRow = row + rowMovement[direction]; //accordingly updating the row coordinate based on the direction
                int newColumn = column + columnMovement[direction]; //accordingly updating the column coordinate based on the direction

                if (!isValid(newRow, newColumn)) { //if the position is not within the grid
                	continue; //go to the next direction
                }
                if (grid[newRow][newColumn] == 'W' || grid[newRow][newColumn] == 'C') { //if the new position is a wall or a camera
                	continue; //go to the next direction
                }

                if (grid[newRow][newColumn] == '.' && visible[newRow][newColumn]) { //if the new position is a visible empty cell
                	continue; //go to the next direction
                }

                int nextDistance = currentDistance + 1; //setting the next distance accordingly

                if (isConveyor(newRow, newColumn)) { //if the new position is a conveyor
                    int[] endingCoordinates = followConveyor(newRow, newColumn); //setting the ending coordinates after following the conveyor system
                    if (endingCoordinates == null) { //if the conveyor leads to a wall, camera, or a loop
                    	continue; //go to the next direction
                    }

                    int endingRow = endingCoordinates[0], endingColumn = endingCoordinates[1]; //getting the ending row and column coordinates
                    if (visible[endingRow][endingColumn] && grid[endingRow][endingColumn] == '.') { //if the ending position is a visible empty cell
                    	continue; //go to the next direction
                    }

                    if (distances[endingRow][endingColumn] == IMPOSSIBLE_TRAVEL_VALUE || distances[endingRow][endingColumn] > nextDistance) { //if the distance of the ending position has not been assigned a proper value or if it's greater than the next distance
                        distances[endingRow][endingColumn] = nextDistance; //set it equal to the next distance
                        pathQueue.add(new int[]{endingRow, endingColumn}); //add the path to the queue to be checked
                    }
                } else { //if the new position is not a conveyor
                    if (distances[newRow][newColumn] == IMPOSSIBLE_TRAVEL_VALUE || distances[newRow][newColumn] > nextDistance) { //if the distance of the new position has not been assigned a value yet or if it's greater than the next distance
                        distances[newRow][newColumn] = nextDistance; //set it equal to the next distance
                        pathQueue.add(new int[]{newRow, newColumn}); //add the path to the queue to be checked
                    }
                }
            }
        }
    }

    static boolean isValid(int row, int column) { //method to check if a given position is within the grid
        return row >= 0 && row < gridLength && column >= 0 && column < gridWidth; //returns true if both the row and column coordinates fit within their respective bounds. returns false otherwise
    }

    static boolean isConveyor(int row, int column) { //helper method to check if a given cell is a conveyor
        char cellType = grid[row][column]; //getting the cell type
        return cellType == 'L' || cellType == 'R' || cellType == 'U' || cellType == 'D'; //returns true if the cellType is a conveyor (as given by the problem). returns false otherwise
    }
}