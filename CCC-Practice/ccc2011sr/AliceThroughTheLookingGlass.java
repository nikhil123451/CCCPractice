import java.util.*;

public class AliceThroughTheLookingGlass { //taken from GPT and modified
    static final boolean[][] BASE_PATTERN = new boolean[5][5]; //making the original 5x5 base pattern

    static { //defines every crystal in the base pattern
        BASE_PATTERN[4 - 0][1] = true; //at the point (1,0)
        BASE_PATTERN[4 - 0][2] = true; //at the point (2,0)
        BASE_PATTERN[4 - 0][3] = true; //at the point (3,0)
        BASE_PATTERN[4 - 1][2] = true; //at the point (2,1)
    }
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner

    public static void main(String[] args) { //main method
        int testCases = scn.nextInt(); //getting the amount of textCases to test
        if (testCases > 0 && testCases <= 10) { //if testCases fits within the domain provided by the problem
	        List<String> results = new ArrayList<>(); //makes a list of each result (whether it's empty of has a crystal
	
	        for (int t = 0; t < testCases; t++) { //looping through every test case
	            int magnification = scn.nextInt(); //gets magnification
	            int xPosition = scn.nextInt(); //gets x position
	            int yPosition = scn.nextInt(); //gets y position
	            if ((magnification >= 1 && magnification <= 13) && 
	            	(xPosition >= 0 && xPosition < Math.pow(5, magnification)) && 
	            	(yPosition >= 0 && yPosition < Math.pow(5, magnification))) { //if magnification, xPosition, and yPosition fit within the restrictions given by the problem
		            results.add(isCrystal(xPosition, yPosition, magnification) ? "crystal" : "empty"); //adding crystal to the result list if the point is a crystal, otherwise mark it as empty
	            }
	        }
	
	        results.forEach(System.out::println); //for every item in the results list, print it out
	        scn.close(); //close the scanner
        }
    }

    static boolean isCrystal(int xPosition, int yPosition, int magnification) { //middle method to get to the check() method
        return check(xPosition, yPosition, magnification); //calls a check with a given x and y position at a certain magnification to see if the cell is a crystal
    }

    static boolean check(int xPosition, int yPosition, int magnification) { //checks to see if cell given at a certain magnification is a crystal
        if (magnification == 1) { //if the magnification is 1 (base pattern)
            if (xPosition >= 0 && xPosition < 5 && yPosition >= 0 && yPosition < 5) { //if the x and y positions are between 0 and 5 (right inclusive)
                return inBase(xPosition, yPosition); //sees if the given coords are in the original base pattern
            }
            return false; //return false otherwise
        }

        int size = pow5(magnification - 1); //setting the dimensions of the grid as a power of 5 given a magnification

        for (int currentXPosition = 0; currentXPosition < 5; currentXPosition++) { //looping through the first 5 x positions
            for (int currentYPosition = 0; currentYPosition < 5; currentYPosition++) { //for every x position, loop through the first 5 y positions
                int baseX = xPosition - currentXPosition * size; //gets the x position at the bottom left most corner
                int baseY = yPosition - currentYPosition * size; //gets the y position at the bottom left most corner

                if (inBase(currentXPosition, currentYPosition)) { //if the current cell is in the base pattern
                    int belowX = baseX; //the bottom would be the same as bottom left most x
                    int belowY = baseY - size; //would be the cell on the bottom supporting

                    if (check(belowX, belowY, magnification - 1)) { //if the supporting cell is a crystal
                        if (!check(belowX, belowY + size, magnification - 1)) { //if the top is exposed (hence the pattern can continue)
                            return true; //it is indeed a crystal
                        }
                    }
                }
            }
        }

        return false; //return false if the cell is not a crystal
    }

    static boolean inBase(int xPosition, int yPosition) { //checks if the given cell is in the base pattern
        if (xPosition < 0 || xPosition >= 5 || yPosition < 0 || yPosition >= 5) { //if any position is out of bounds
        	return false; //it is not inside the base pattern
        }
        return BASE_PATTERN[4 - yPosition][xPosition]; //returns whether or not there's a crystal at the coords (4-yPos cuz of how java's arrays are handled)
    }

    static int pow5(int exponent) { //helper method to exponentiate 5 as a base
        int result = 1; //setting initial value to 1 (5^0)
        for (int i = 0; i < exponent; i++) { //looping through the exponent value
        	result *= 5; //multiply by 5 (exponent) times
        }
        return result; //return the result
    }
}
