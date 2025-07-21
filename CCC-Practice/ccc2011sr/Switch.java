import java.util.*;

public class Switch { //taken from GPT and modified

   static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int lights = scn.nextInt(); //getting the number of lights
        StringBuilder initialStateBuilder = new StringBuilder(); //making a stringBuilder for the initial state of the lights
        for (int i = 0; i < lights; i++) { //looping through each light
            initialStateBuilder.append(scn.nextInt()); //append the state of each light to the string
        }
        String initialState = initialStateBuilder.toString(); //convert the builder to a string

        System.out.println(minActionsForAllOff(initialState)); //print out the calculated minimum actions to have all lights off 
        scn.close(); //close the scanner
    }

    public static String handleLights(String state) { //helper method to apply the rule stated in the problem
        StringBuilder builder = new StringBuilder(state); //creates a new stringBuilder based on the current state of the lights
        boolean changed; //declares a changed variable to account for the removal of a 4 light block in the sequence

        do { //do-while until there are no changes detected
            changed = false; //initially assume no changes have been made
            int i = 0; //initializing an iterator
            while (i < builder.length()) { //looping through all the valid lights in the stringBuilder
                if (builder.charAt(i) == '1') { //if the current light is on
                    int j = i; //making a new iterator at the same place as i
                    while (j < builder.length() && builder.charAt(j) == '1') { //while j is a valid light and the light at j is on
                        j++; //increment j
                    }
                    if (j - i >= 4) { //if there are atleast 4 lights between j and i
                        for (int k = i; k < j; k++) { //loop through lights between j and i
                            builder.setCharAt(k, '0'); //set every light to off
                        }
                        changed = true; //changes have indeed been made at this point
                        break; //the do-while will run again to check for other groups of 4+
                    }
                    i = j; //set i to the upper bound j and continue
                } else { //if the current light is off
                    i++; //go to the next light
                }
            }
        } while (changed); //while there have been changes made

        return builder.toString(); //return the state after all the changes have been made
    }
    
    public static int minActionsForAllOff(String initialState) { //method to calculate the minimum amount of actions to turn all lights off using breadth-first search (BFS)
        Queue<String> stateQueue = new LinkedList<>(); //queue for every state we still need to process
        Queue<Integer> actionsQueue = new LinkedList<>(); //queue that's linked to stateQueue which keeps track of the number of actions it takes to reach each state in stateQueue
        Set<String> processedStates = new HashSet<>(); //set that keeps track of every state we've already processed to avoid repeats

        stateQueue.add(initialState); //adding the initialState to the regular queue
        actionsQueue.add(0); //set the parallel actions to 0 since it's the initial state
        processedStates.add(initialState); //add the initial state to the processed states

        while (!stateQueue.isEmpty()) { //while there's still states in the queue
            String currentState = stateQueue.poll(); //gets the top most state in the queue
            int actions = actionsQueue.poll(); //gets the respective actions for the currentState

            if (currentState.chars().allMatch(character -> character == '0')) { //if every light is off
                return actions; //return the amount of actions taken to get to that state
            }

            for (int i = 0; i < currentState.length(); i++) { //looping through every light
                if (currentState.charAt(i) == '0') { //if the current light is off
                    StringBuilder mutableState = new StringBuilder(currentState); //creating a mutable state to allow us to change specific characters
                    mutableState.setCharAt(i, '1'); //turning the current light on
                    String newState = handleLights(mutableState.toString()); //making the newState whatever the result is of turning that one light on

                    if (!processedStates.contains(newState)) { //if this new state is actually new and hasn't been processed yet
                        processedStates.add(newState); //add the state to the processed set
                        stateQueue.add(newState); //add the state to the queued states to be processed
                        actionsQueue.add(actions + 1); //add the linked actions to be the current actions +1 because we made an action to turn on one of the lights
                    }
                }
            }
        }

        return -1; //code should never reach here
    }
}
