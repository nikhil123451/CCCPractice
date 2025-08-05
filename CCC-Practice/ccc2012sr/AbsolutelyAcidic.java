import java.util.*;

public class AbsolutelyAcidic { //taken from GPT and modified
	
	static final int MAX_SENSOR_READINGS = 1000;
    static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        
        int sensors = scn.nextInt(); //getting the number of sensors from input
        int[] readingFrequencies = new int[MAX_SENSOR_READINGS + 1]; //making an array for all the sensor reading frequencies (+1 cuz java is 0-indexed)
        
        if (sensors >= 2 && sensors <= 2000000) { //if the amount of sensors fit within the restrictions given by the problem
        	for (int i = 0; i < sensors; i++) { //loop through every sensor
                int reading = scn.nextInt(); //getting the next reading
                if (reading >= 1 && reading<= 1000) { //if the reading fits within the restrictions given by the problem
                    readingFrequencies[reading]++; //increment the reading spot by 1
                }
            }

            int largestFrequency = 0; //making a variable for the largest frequency
            int secondLargestFrequency = 0; //making a variable for the second-largest frequency
            for (int frequency : readingFrequencies) { //looping through the frequencies array
                if (frequency > largestFrequency) { //if the current frequency is greater than the current largest frequency
                    secondLargestFrequency = largestFrequency; //set the second-largest frequency as the current largest
                    largestFrequency = frequency; //set the largest as the current frequency
                } else if (frequency > secondLargestFrequency && frequency < largestFrequency) { //if the current frequency is greater than the second largest, but smaller than the largest
                    secondLargestFrequency = frequency; //set the second largest to be the current frequency
                }
            }

            List<Integer> largestFrequencyReadings = new ArrayList<>(); //making a list of the largest frequency readings
            List<Integer> secondLargestFrequencyReadings = new ArrayList<>(); //making a list of the second-largest frequency readings
            for (int reading = 1; reading <= 1000; reading++) { //looping through the frequencies array
                if (readingFrequencies[reading] == largestFrequency) { //if the current frequency is the same as the largest frequency
                	largestFrequencyReadings.add(reading); //add it to the largestFrequecyReadings list
                }
                else if (readingFrequencies[reading] == secondLargestFrequency) { //if the current frequency is equal to the second-largest frequency
                	secondLargestFrequencyReadings.add(reading); //add that to the second-largest frequency readings list
                }
            }

            int result = 0; //make a variable storing the result of upcoming calculations
            
            if (largestFrequencyReadings.size() >= 2) { //if there are multiple readings that have the largest frequency
                result = largestFrequencyReadings.get(largestFrequencyReadings.size() - 1)
                       - largestFrequencyReadings.get(0); //getting the largest absolute difference by taking the last element in the list as it would be the largest number based on how things are formatted and subtracting it by the first element
            } else { //if there is only 1 largest frequency
                int highestReading = largestFrequencyReadings.get(0); //getting the highest reading
                for (int reading : secondLargestFrequencyReadings) { //looping through every second largest frequency
                    result = Math.max(result, Math.abs(highestReading - reading)); //for every absolute difference, check if it is bigger than the current result and set it as that
                }
            }

            System.out.println(result); //print out the result
            scn.close(); //close the scanner
        }
    }
}