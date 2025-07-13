import java.util.*;

public class Unfriend { //taken from GPT and modified
	
    static Scanner scn = new Scanner(System.in); //creating a scanner to read input from console
	
    public static void main(String[] args) { //main method
        int peopleInNetwork = scn.nextInt(); //getting the amount of people in the network

        List<Integer>[] tree = new ArrayList[peopleInNetwork + 1]; //creating a list of arrays to represent a tree with peopleInNetwork + 1 cuz java is 0-indexed
        for (int i = 1; i <= peopleInNetwork; i++) { //looping through all the people in the network
        	tree[i] = new ArrayList<>(); //initializing every element in the list with an empty arrayList
        }

        for (int i = 1; i < peopleInNetwork; i++) { //looping through every person in the network minus mark
            int inviter = scn.nextInt(); //get the ID for the person invited through input
            tree[inviter].add(i); //add the inviter to the tree
        }

        int count = 0; //initializing a counter to keep track of all possible sets of people that can be removed

        for (int subset = 0; subset < (1 << (peopleInNetwork - 1)); subset++) { //looping through all the subsets (1 << (peopleInNetwork - 1)) = 2^(peopleInNetwork - 1)
            Set<Integer> removedPeople = new HashSet<>(); //creating a hashSet to keep track of all people removed
            for (int i = 1; i < peopleInNetwork; i++){ //looping through every person in the network minus mark
                if ((subset & (1 << (i - 1))) != 0) { //if their i value is a part of the subset
                	removedPeople.add(i); //add them to the removal list
                }
            }
            
            //depth-first search (DFS) algorithm
            boolean valid = true; //initializes a validity variable to see whether a person on the removal list has a valid subset
            for (int person : removedPeople) { //enhanced looping through every person on the removal list
                Stack<Integer> stack = new Stack<>(); //creating a stack to hold every person
                stack.push(person); //pushing the current person to the top of the stack
                while (!stack.isEmpty()) { //while there are still people in the stack
                    int current = stack.pop(); //sets current to the person at the top of the stack and removes them from the stack
                    for (int child : tree[current]) { //looping through every child of the person on the tree
                        if (!removedPeople.contains(child)) { //if the remove people list does not contain 1 child
                            valid = false; //set the person to not valid
                            break; //break the loop
                        }
                        stack.push(child); //put the child on the top of the stack
                    }
                    if (!valid) { //if the person is not valid
                    	break; //break the while and continue
                    }
                }
                if (!valid) { //if the person is not valid
                	break; //break the for loop to stop iterating through all people
                }
            }

            if (valid) { //if the person is valid for removal
            	count++; //increase the removal count
            }
        }

        System.out.println(count); //print out the counter result
    }
}
