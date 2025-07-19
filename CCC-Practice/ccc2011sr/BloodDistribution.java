import java.util.*;

public class BloodDistribution { //taken from GPT and modified

	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method

        int[] bloodUnits = new int[8]; //making an array for each sample of blood
        int[] patients = new int[8]; //making an array for each patient with a specific blood type (linked to bloodUnits)

        for (int i = 0; i < bloodUnits.length; i++) { //looping through bloodUnits
            bloodUnits[i] = scn.nextInt(); //setting each number in the array to the following number in the input
        }
        for (int i = 0; i < patients.length; i++) { //looping through patients
            patients[i] = scn.nextInt(); //setting each number in the array to the following number in the input after the blood units
        }

        int[][] compatibilities = { //creating a 2d array for the compatibilities of each blood type
            {0},                         //O- patients can only receive O-
            {1, 0},                      //O+ patients can only receive O+-
            {2, 0},                      //A- patients can only receive A- or O-
            {3, 2, 1, 0},                //A+ patients can only receive A+- or O+-
            {4, 0},                      //B- patients can only receive B- or O-
            {5, 4, 1, 0},                //B+ patients can only receive B+- or O+-
            {6, 2, 4, 0},                //AB- patients can only receive AB-, B-, A-, or O-
            {7, 6, 3, 2, 5, 4, 1, 0}     //AB+ patients can receive any blood type
        };

        int totalMatched = 0; //creating a counter to keep track of matches

        for (int i = 0; i < patients.length; i++) { //looping through all patient types
            for (int donor : compatibilities[i]) { //looping through the compatible donors for patient type i
                int unitsToGive = Math.min(patients[i], bloodUnits[donor]); //determines how many units of blood can be given based on how much is needed and how much is currently available
                patients[i] -= unitsToGive; //subtracts the amount to give from the patient count
                bloodUnits[donor] -= unitsToGive; //subtracts the amount to give from the blood units
                totalMatched += unitsToGive; //increase the match count
                if (patients[i] == 0) { //if there are no more patients with blood type i
                	break; //break the inner loop and go to the next set of patients
                }
            }
        }

        System.out.println(totalMatched); //print the total matches after every patient has been looped through
    }
}
