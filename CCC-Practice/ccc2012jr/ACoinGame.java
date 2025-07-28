import java.util.*;

public class ACoinGame { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        List<List<Stack<Integer>>> testCases = new ArrayList<>(); //creates a list of test cases (each test case being its own list)

        while (true) { //loops until broken by a 0 input
            int coins = Integer.parseInt(scn.nextLine().trim()); //getting number of coins, all the stuff is security to ensure the input is parsed as an int properly
            if (coins == 0) { //if the input is 0
            	break; //end the program
            }

            String[] tokens = scn.nextLine().trim().split("\\s+"); //converting the line of numbers to a string of tokens (ie. if the input was 2 1, then the string[] will contain {"2", "1"})
            List<Stack<Integer>> initialState = new ArrayList<>(); //making an empty initial state to start
            for (String token : tokens) { //looping through every token
                Stack<Integer> stack = new Stack<>(); //creating a stack to keep track of each position
                stack.push(Integer.parseInt(token)); //adds the token to the stack
                initialState.add(stack); //adds the stack with the coin to the initial state
            }
            testCases.add(initialState); //adds the initial state to the test cases
        }

        List<String> results = new ArrayList<>(); //creating a list of results to be printed out
        for (List<Stack<Integer>> testCase : testCases) { //looping through every test case
            int result = minimumMoves(testCase); //sets the result to whatever the BFS algorithm returns
            results.add(result == -1 ? "IMPOSSIBLE" : String.valueOf(result)); //adds IMPOSSIBLE if BFS returned -1, otherwise add the number it returned
        }

        // Output all results
        for (String result : results) { //looping through every result in the results list
            System.out.println(result); //print the result out
        }
        scn.close(); //close the scanner
    }

    static int minimumMoves(List<Stack<Integer>> startingState) { //using BFS to process the game state to find the minimum amount or moves and to see if the goal is possible
        Set<String> processedStates = new HashSet<>(); //creating a set of every visited state to ensure we don't re-process states
        Queue<Object[]> states = new LinkedList<>(); //creates a queue for the states to go over

        String startSerial = serialize(startingState); //creating a string key out of the starting state for easier use
        processedStates.add(startSerial); //adding the state to the processed states set
        states.add(new Object[]{cloneState(startingState), 0}); //adds the initial state to the queue along with an initial move number of 0

        while (!states.isEmpty()) { //while there are states to process
            Object[] currentStateInformation = states.poll(); //grabs the top state in the queue
            @SuppressWarnings("unchecked") //suppressing unchecked warnings cuz we are only dealing with lists of stacks
			List<Stack<Integer>> currentState = (List<Stack<Integer>>) currentStateInformation[0]; //setting the currentState from the currentStateInfo
            int moves = (int) currentStateInformation[1]; //gets the number of moves to get to the given state

            if (isGoal(currentState)) { //if the currentState meets the goal requirements given in the problem
            	return moves; //return the amount of moves taken to reach the goal
            }

            int spots = currentState.size(); //gets the amount of spots from the state size
            for (int i = 0; i < spots; i++) { //loops through every spot
                if (currentState.get(i).isEmpty()) { //if the spot is empty
                	continue; //stop this iteration and move to the next spot
                }
                int coin = currentState.get(i).peek(); //gets the coin at the top of the stack
                
                if (i > 0 && canStack(coin, currentState.get(i - 1))) { //if the spot is in bounds to try to go left and if you can stack the coin on the spot to the left
                    List<Stack<Integer>> newState = cloneState(currentState); //making a new state with the modification
                    newState.get(i).pop(); //removes the top coin from the stack
                    newState.get(i - 1).push(coin); //adds the coin to the top of the stack on the left
                    String serializedNewState = serialize(newState); //creates a key out of the new state
                    if (!processedStates.contains(serializedNewState)) { //if this is an actual new state
                        processedStates.add(serializedNewState); //add it to the processed set
                        states.add(new Object[]{newState, moves + 1}); //add it to the queue to be processed and increase the moves by 1
                    }
                }

                // Try right
                if (i < spots - 1 && canStack(coin, currentState.get(i + 1))) { //if the spot is in bounds to try to go right and if you can stack the coin on the spot to the right
                    List<Stack<Integer>> newState = cloneState(currentState); //making a new state with the modification
                    newState.get(i).pop(); //removes the top coin from the stack
                    newState.get(i + 1).push(coin); //adds the coin to the top of the stack on the left
                    String serial = serialize(newState); //creates a key out of the new state
                    if (!processedStates.contains(serial)) { //if this is an actual new state
                        processedStates.add(serial); //add it to the processed set
                        states.add(new Object[]{newState, moves + 1}); //add it to the queue to be processed and increase the moves by 1
                    }
                }
            }
        }

        return -1; //if we've gone through every state and ensured none have met the goal, then return -1 to indicate that the goal is impossible
    }

    static boolean canStack(int coin, Stack<Integer> stack) { //helper method to check if a coin can be stacked in a spot
        return stack.isEmpty() || coin < stack.peek(); //if the stack is empty or if it has a coin larger than the coin in question, then return true. return false otherwise
    }

    static boolean isGoal(List<Stack<Integer>> state) { //method to check if the goal is met with a given state
        int spots = state.size();
        for (Stack<Integer> stack : state) { //looping through every stack in the state
            if (stack.size() != 1) { //if the current stack has more than one coin on it or has no coins
            	return false; //return false as the goal requires every coin to be unstacked
            }
        }
        for (int i = 1; i < spots; i++) { //looping through every spot
            if (state.get(i - 1).peek() >= state.get(i).peek()) { //if the spot to the left is greater than the current spot
            	return false; //return false because the coins should be in increasing order
            }
        }
        return true; //return true otherwise
    }

    static String serialize(List<Stack<Integer>> state) { //helper method to convert a state to a string key
        StringBuilder builder = new StringBuilder(); //creating a stringBuilder to help with the key creating process
        for (Stack<Integer> stack : state) { //looping through every stack in the state
            builder.append("["); //start with a left square bracket
            for (int coinValue : stack) { //looping through every coin in the stack
                builder.append(coinValue).append(","); //adding the coin to the string with a comma at the end
            }
            builder.append("]"); //adding a right square bracket at the end
        }
        return builder.toString(); //returns the final key
    }

    static List<Stack<Integer>> cloneState(List<Stack<Integer>> state) {//helper method to clone the given state
        List<Stack<Integer>> copiedState = new ArrayList<>(); //creating a variable for the copied state
        for (Stack<Integer> stack : state) { //looping through every stack in the state
            Stack<Integer> newStack = new Stack<>(); //creating a new stack
            newStack.addAll(stack); //adding all the elements in the original stack to the new stack
            copiedState.add(newStack); //adding the new stack to the copied state
        }
        return copiedState; //returning the copied state
    }
}
