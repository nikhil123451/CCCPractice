import java.util.*;

public class RuleOfThree { //taken from GPT and modified
	
	static final int RULES = 3;

    static String[] rulesLeftSide = new String[RULES]; //array containing the first half of the 3 rules
    static String[] rulesRightSide = new String[RULES]; //array containing the second half of the 3 rules

    static int steps; //declaring the steps variable
    static String initialSequence, finalSequence; //declaring the initial and final sequence variables

    static int[] usedRule; //array to store the rule that works
    static int[] usedIndex; //array to store the index that works
    static String[] usedSequence; //array to store the resulting sequence

    static boolean solved = false; //variable to keep track if we've gotten a match
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner

    public static void main(String[] args) { //main method
        for (int i = 0; i < RULES; i++) { //for every rule
            rulesLeftSide[i] = scn.next(); //get the left-hand side for the rule
            rulesRightSide[i] = scn.next(); //get the right-hand side for the rule
        }

        steps = scn.nextInt(); //get the steps needed
        initialSequence = scn.next(); //get the initial sequence
        finalSequence = scn.next(); //get the final expected sequence

        usedRule = new int[steps]; //initializing the usedRule array
        usedIndex = new int[steps]; //initializing the usedIndex array
        usedSequence = new String[steps]; //initializing the usedSequence array

        modifySequence(0, initialSequence); //modifying the initial sequence until it matches with the final sequence, starting at step 0

        for (int i = 0; i < steps; i++) { //looping through every step
            System.out.println(usedRule[i] + " " + usedIndex[i] + " " + usedSequence[i]); //prints out each element in the step
        }
        scn.close(); //closing the scanner
    }

    static void modifySequence(int step, String currentSequence) { //depth-first searching method that continually modifies the sequence to make the final sequence
        if (solved) { //if the current sequence is the same as the final sequence
        	return; //stop modifications because there's no more need for them
        
        }

        if (step == steps) { //if we're at the last step
            if (currentSequence.equals(finalSequence)) { //if the current sequence is the same as the final sequence
                solved = true; //the problem has been solved
            }
            return; //stop all modifications
        }

        if (currentSequence.length() > 50) { //if the current sequence exceeds the limit of 50 characters for the final sequence (as given in the problem)
        	return; //stop all modifications for this branch
        }

        for (int rule = 0; rule < RULES; rule++) { //looping through every rule
            String startingSequence = rulesLeftSide[rule]; //getting the sequence that we will replace
            String resultingSequence = rulesRightSide[rule]; //getting the substitution

            for (int i = 0; i + startingSequence.length() <= currentSequence.length(); i++) { //looping through the length of the current sequence, accounting for the size of the starting sequence
                if (currentSequence.substring(i, i + startingSequence.length()).equals(startingSequence)) { //if there exists a "copy" of the starting sequence in the current sequence

                    String modifiedSquence = currentSequence.substring(0, i) + resultingSequence + currentSequence.substring(i + startingSequence.length()); //replacing the starting sequence with the resulting sequence in the current sequence

                    usedRule[step] = rule + 1; //storing the rule used in the usedRule array (+1 cuz java is 0-indexed)
                    usedIndex[step] = i + 1; //storing the index where the replacement happened in the usedIndex array (+1 cuz java is 0-indexed)
                    usedSequence[step] = modifiedSquence; //storing the modified sequence in the usedSequence array (+1 cuz java is 0-indexed)

                    modifySequence(step + 1, modifiedSquence); //repeating the algorithm on the new sequence

                    if (solved) { //if the initial sequence and final sequence match at this step
                    	return; //stop all modifications
                    }
                }
            }
        }
    }
}