import java.util.*;

public class GreedyForPies { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int firstPies = scn.nextInt(); //getting the amount of pies in line A
        int[] lineA = new int[firstPies]; //making an int[] array for the sugar values of line A
        
        for (int i = 0; i < firstPies; i++) { //looping through every line A pie
        	lineA[i] = scn.nextInt(); //adding the sugar value to the array
        }
        
        int secondPies = scn.nextInt(); //getting the amount of pies in line B
        int[] lineB = new int[secondPies]; //making an int[] array for the sugar values of line B
        
        for (int i = 0; i < secondPies; i++) { //looping through every line B pie
        	lineB[i] = scn.nextInt(); //adding the sugar value to the array
        }
        
        scn.close(); //closing the scanner

        Arrays.sort(lineB); //sorting line B in ascending order to be later put in descending order
        for (int i = 0; i < secondPies / 2; i++) { //looping through half of the array
            int temporaryValue = lineB[i]; //maintaining the value at position i because it's gonna be replaced
            lineB[i] = lineB[secondPies - 1 - i]; //set the element from the symmetric position at the end of the array into the position i
            lineB[secondPies - 1 - i] = temporaryValue; //and set that value at the end to the value that was originally at i
        }
        
        int[][][] maximumSugarValues = new int[firstPies + 1][secondPies + 1][2]; //creating a 3d array to keep track of the maximum sugar in take, the last array of size 2 is to show whether the previous pie was eaten or not (+1 cuz java is 0-indexed)

        for (int i = 0; i <= firstPies; i++) //looping through all the pies in line A
            for (int j = 0; j <= secondPies; j++) //looping through all the pies in line B
                Arrays.fill(maximumSugarValues[i][j], -1); //completely filling the array with -1 initially

        maximumSugarValues[0][0][0] = 0; //starting with nothing eaten

        for (int i = 0; i <= firstPies; i++) { //looping through all the pies in line A
            for (int j = 0; j <= secondPies; j++) { //looping through all the pies in line B
                for (int k = 0; k <= 1; k++) { //looping through the possible eaten values (0 being not eaten and 1 being eaten)
                    if (maximumSugarValues[i][j][k] == -1) { //if the current sugar value has not been processed yet
                    	continue; //then go to the next eaten value or go to the next pie in line B
                    }
                    int currentValue = maximumSugarValues[i][j][k]; //getting the current sugar value to be processed

                    if (i < firstPies) { //if there is a pie from line A to be eaten
                        maximumSugarValues[i + 1][j][0] = Math.max(maximumSugarValues[i + 1][j][0], currentValue); //setting the maximum sugar value to the greatest value between the current one and the next one in line A

                        if (k == 0) { //if the previous pie was not eaten
                            maximumSugarValues[i + 1][j][1] = Math.max(maximumSugarValues[i + 1][j][1], currentValue + lineA[i]); //eat the pie from line A and then compute the maximum
                        }
                    }

                    if (j < secondPies) { //if there is a pie from line B to be eaten
                        maximumSugarValues[i][j + 1][0] = Math.max(maximumSugarValues[i][j + 1][0], currentValue); //setting the maximum sugar value to the greatest value between the current one and the next one in line B

                        if (k == 0) { //if the previous pie was not eaten
                            maximumSugarValues[i][j + 1][1] = Math.max(maximumSugarValues[i][j + 1][1], currentValue + lineB[j]); //eat the pie from line B and then compute the maximum
                        }
                    }
                }
            }
        }

        int calculatedMaximum = 0; //initially setting the calculated maximum to 0
        for (int k = 0; k <= 1; k++) { //looping through the possible values of k
            calculatedMaximum = Math.max(calculatedMaximum, maximumSugarValues[firstPies][secondPies][k]); //setting the appropriate maximum between the 2 k values
        }

        System.out.println(calculatedMaximum); //printing out the result
    }
}