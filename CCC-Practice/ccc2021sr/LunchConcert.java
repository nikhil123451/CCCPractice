import java.util.*;

public class LunchConcert { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in); //initializing a scanner

    static int friends; //variable for the amount of friends you have
    static long[] positions, speeds, hearingRanges; //arrays for the friends' positions, speeds, and hearing ranges

    public static void main(String[] args) { //main method

        friends = scn.nextInt(); //getting the amount of friends

        positions = new long[friends]; //initializing the positions array
        speeds = new long[friends]; //initializing the speeds array
        hearingRanges = new long[friends]; //initializing the hearing ranges array

        long left = Long.MAX_VALUE; //setting left as an impossibly large value
        long right = Long.MIN_VALUE; //setting right as an impossibly small value

        for (int i = 0; i < friends; i++) { //for every friend
            positions[i] = scn.nextLong(); //set their position
            speeds[i] = scn.nextLong(); //set their speed
            hearingRanges[i] = scn.nextLong(); //set their hearing range

            left = Math.min(left, positions[i] - hearingRanges[i]); //getting left-most bound
            right = Math.max(right, positions[i] + hearingRanges[i]); //getting the right-most bound
        }

        while (right - left > 3) { //while the bounds are large enough to keep shrinking
            long midpoint1 = left + (right - left) / 3; //get the first midpoint between the bounds
            long midpoint2 = right - (right - left) / 3; //get the second midpoint between the bounds

            if (cost(midpoint1) <= cost(midpoint2)) { //if the value of midpoint1 is less than or equal to the value of midpoint2
                right = midpoint2 - 1; //set the new right bound accordingly
            } else { //if the value of midpoint1 is actually greater than the value of midpoint2
                left = midpoint1 + 1; //set the new left bound accordingly
            }
        }

        long result = Long.MAX_VALUE; //setting the result to an arbitrarily large value

        for (long value = left; value <= right; value++) { //looping through the length of the bounds
            result = Math.min(result, cost(value)); //set the minimum result accordingly
        }

        System.out.println(result); //print out the result
        scn.close(); //closing the scanner
    }

    static long cost(long cValue) { //helper method that finds the speed of all friends traversing to point c
        long total = 0; //total counter

        for (int i = 0; i < friends; i++) { //looping through every friend
            long dist = Math.abs(positions[i] - cValue) - hearingRanges[i]; //get the friend's distance from c (accounting for their hear
            if (dist > 0) { //if the distance is non-negative
                total += dist * speeds[i]; //increment the total time accordingly
            }
        }

        return total; //return the total time calculated
    }
}