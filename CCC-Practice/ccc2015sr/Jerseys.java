import java.util.*;

public class Jerseys { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in);;
	
    public static void main(String[] args) { //main method
        int jerseys = scn.nextInt(); //getting the number of available jerseys
        int athletes = scn.nextInt(); //getting the number of athletes

        Map<Character, Integer> sizeRanks = new HashMap<>(); //creating a map for small, medium, and large sizes
        sizeRanks.put('S', 0); //mapping small as 0
        sizeRanks.put('M', 1); //mapping medium as 1
        sizeRanks.put('L', 2); //mapping large as 2

        int[] jerseySizes = new int[jerseys + 1]; //an array for every jersey's size (+1 cuz java is 0-indexed)
        
        for (int j = 1; j <= jerseys; j++) { //looping through every jersey
            char size = scn.next().charAt(0); //getting the size of the jersey
            jerseySizes[j] = sizeRanks.get(size); //setting the size to the mapped rank
        }

        boolean[] used = new boolean[jerseys + 1]; //an array to keep track of jerseys that have been assigned (+1 cuz java is 0-indexed)
        
        int satisfied = 0; //a counter for the amount of assignments

        for (int i = 0; i < athletes; i++) { //looping through every athlete
            char preferredSize = scn.next().charAt(0); //getting the athlete's size
            int jerseyNumber = scn.nextInt(); //getting the requested jersey number

            int preferenceIndex = sizeRanks.get(preferredSize); //translating the letter size to the numerical value

            if (!used[jerseyNumber] && jerseySizes[jerseyNumber] >= preferenceIndex) { //if the jersey has not been assigned and if it's greater than or equal to the requested size
                used[jerseyNumber] = true; //set that jersey as used
                satisfied++; //increment the satisfied student
            }
        }

        System.out.println(satisfied); //print out the amount satisfied
        scn.close(); //close the scanner
    }
}