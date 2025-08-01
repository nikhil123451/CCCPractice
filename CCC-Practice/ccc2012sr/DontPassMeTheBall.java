import java.util.Scanner;

public class DontPassMeTheBall { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int jerseyNumber = scn.nextInt(); //getting the jersey number
        scn.close(); //closing the scanner
        
        if (jerseyNumber >= 1 && jerseyNumber <= 99) { //if the jersey number fits within the restrictions given in the problem
        	if (jerseyNumber < 4) { //if the jersey number is less than 4
                System.out.println(0); //print 0 and end the program since there are no possible combinations with a jersey number lower than 4
                return; //returns void
            }

            long players = jerseyNumber - 1; //number of players before the person who scored
            long combinations = (players * (players - 1) * (players - 2)) / 6; //combination algorithm for the amount of combinations
            System.out.println(combinations); //prints out the result
        }
    }
}