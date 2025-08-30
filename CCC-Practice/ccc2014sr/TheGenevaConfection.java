import java.util.*;

public class TheGenevaConfection { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        StringBuilder output = new StringBuilder(); //creating a stringBuilder for the output

        int tests = scn.nextInt(); //getting the amount of tests to be run
        
        while (tests-- > 0) { //while there are still tests to do, decrement tests after every test
            int cars = scn.nextInt(); //getting the number of cars
            int[] carPositions = new int[cars]; //creating an int array for the car positions
            for (int i = 0; i < cars; i++) { //looping through every car
                carPositions[i] = scn.nextInt(); //adding the car to the position array
            }

            int[] stack = new int[cars]; //creating a stack array for the cars in the branch
            int topIndex = -1; //index to show cars available in the stack array (starting at -1, cuz java is 0-indexed)
            int carNeeded = 1; //variable that shows us what car we need (starts at car 1 initially)
            
            for (int i = cars - 1; i >= 0; i--) { //looping through cars in reverse order
                int car = carPositions[i]; //getting the current car

                if (car == carNeeded) { //if the car is the next one in line
                    carNeeded++; //set neededCar to the next car needed
                } else { //if it's not the right car
                    stack[++topIndex] = car; //set the next index up to the car (adding it to the branch)
                }

                while (topIndex >= 0 && stack[topIndex] == carNeeded) { //while there are cars in the branch and the top car is the needed car
                    topIndex--; //set the top index to the next one down
                    carNeeded++; //set the needed car to the next one
                }
            }

            if (carNeeded == cars + 1) { //if needed cars has exceeded the amount of cars
            	output.append("Y\n"); //set that test to Y because all the cars have been processed properly
            } else { //if the needed car is still waiting for a car at the top or in the branch
            	output.append("N\n"); //set that test to N because not all cars have been processed properly
            }
        }

        System.out.print(output.toString()); //print out the output accumulated
    }
}