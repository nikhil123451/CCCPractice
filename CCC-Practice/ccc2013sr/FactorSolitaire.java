import java.util.*;

public class FactorSolitaire { //taken from GPT and modified

	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int targetNumber = scn.nextInt(); //getting the target number to be reached
        scn.close(); //closing the scanner
        
        if (targetNumber >= 1 && targetNumber <= 5000000) { //if the target number fits within the restrictions given by the problem
        	long[] costs = new long[targetNumber + 1]; //creating an array for every cost of every number (+1 cuz java is 0-indexed)
            Arrays.fill(costs, Long.MAX_VALUE / 2); //setting each value in the array to a value that's really big before setting the values to reasonable numbers
            costs[1] = 0; //setting the cost of 1 to be 0

            @SuppressWarnings("unchecked") //suppressing unchecked warnings
    		List<Integer>[] divisors = new ArrayList[targetNumber + 1]; //creating a list of divisors for every number (+1 cuz java is 0-indexed)
            for (int i = 1; i <= targetNumber; i++) { //for every number before and including the target number
                divisors[i] = new ArrayList<>(); //make a new arrayList for every number, which will contain it's divisors
            }
            for (int i = 1; i <= targetNumber; i++) { //for every number before and including the target number
                for (int multiple = i; multiple <= targetNumber; multiple += i) { //visiting every multiple of i
                    divisors[multiple].add(i); //adding i to j's divisor list
                }
            }

            for (int currentNumber = 1; currentNumber < targetNumber; currentNumber++) { //looping through each number before the target number
                long cost = costs[currentNumber]; //getting the current cost of the number
                if (cost >= Long.MAX_VALUE / 2) { //if the current cost is greater than or equal to the close-to-infinity number we filled into the long[] array
                	continue; //keep going through the for loop as we haven't found a cost for that number yet
                }

                for (int divisorA : divisors[currentNumber]) { //looping through every divisor for the current number
                    int divisorB = currentNumber / divisorA; //getting the other divisor by dividing the current number by divisor A
                    int nextNumber = currentNumber + divisorA; //setting the next number as current number plus divisor A
                    if (nextNumber <= targetNumber) { //if the next number is in range
                        costs[nextNumber] = Math.min(costs[nextNumber], cost + divisorB); //setting the cheapest cost if we've already found the path
                    }
                }
            }

            System.out.println(costs[targetNumber]); //printing out the cost to reach the target number
        }
    }
}