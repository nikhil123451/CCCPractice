import java.util.*;

public class PiDay { //taken from GPT and modified
    static int[][][] waysToDistributePies; //initializing a 3d array to keep track of many ways to distribute pies

    static int getNumberOfWaysToDistributePies(int pies, int people, int minimumPies) { //helper method to get the number of ways to distribute pies between a certain amount of people
        if (people == 1) { //if there is only one person
            return (pies >= minimumPies) ? 1 : 0; //return one way if the amount of pies is greater than equal to the minimum amount of pies, return 0 otherwise
        }
        if (waysToDistributePies[pies][people][minimumPies] != -1) { //if the amount of pies, people, and minimum pies have been calculated before
        	return waysToDistributePies[pies][people][minimumPies]; //return the calculated value
        }

        int ways = 0; //set ways to 0 initially
        for (int x = minimumPies; x <= pies / people; x++) { //looping from the minimum to the amount of pies divided by the amount of people
            ways += getNumberOfWaysToDistributePies(pies - x, people - 1, x); //recursively adds to the amount of ways
        }

        return waysToDistributePies[pies][people][minimumPies] = ways; //sets the specific to the amount of ways and returns it
    }

    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
    public static void main(String[] args) { //main method
        
        int pies = scn.nextInt(); //getting the number of pies
        int people = scn.nextInt(); //getting the number of people
        waysToDistributePies = new int[pies + 1][people + 1][pies + 1]; //storing a 3d array in the variable (+1 cuz java is 0-indexed)

        for (int[][] array2d : waysToDistributePies) { //for every 2d array in the 3d array
        	for (int[] array : array2d) { //for every array in the current 2d array
        		Arrays.fill(array, -1); //set the value to be -1 to show that it hasn't been calculated yet
        	}  
        }
        
        System.out.println(getNumberOfWaysToDistributePies(pies, people, 1)); //printing out the result of calculating the ways to distribute a specific amount of pies between a specific amount of people with a minimum of one pie per person
    }
}