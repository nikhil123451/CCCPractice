import java.util.*;

public class RMT { //taken from GPT and modified
    static int stations, stationLines, actions; //declaring stations, station lines, and actions
    static int[] lineMap, passengers; //declaring int[] arrays for connections between stations and lines and for the number of passengers at a given station
    static ArrayList<Integer>[] connectedStations; //declaring an arrayList that contains the indices for every station mapped to a station line
    static int[] changeOffset; //declaring an int[] array for the amount a line has been changed
    static long[] tree; //declaring a tree array to keep track of operations performed during the program
    static final int THRESHOLD = 400; //defining the boundary between small lines and big lines
    static boolean[] isBig; //boolean array that tells whether or not a line is a small line or a big line
    static long[][] bigLineSums; //stores the sum of the passengers on a given big line
    static int[] bigIndices; //array that maps a big line to its position in the bigLineSums array
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
	@SuppressWarnings("unchecked") //suppressing unchecked warnings
	public static void main(String[] args) { //main method
        
        StringBuilder output = new StringBuilder(); //making a stringBuilder for the combined output

        stations = scn.nextInt(); //getting the number of stations
        stationLines = scn.nextInt(); //getting the number of station lines
        actions = scn.nextInt(); //getting the amount of actions to be done

        lineMap = new int[stations + 1]; //initializing the line map array (+1 cuz java is 0-indexed)
        passengers = new int[stations + 1]; //initializing the passengers array (+1 cuz java is 0-indexed)
        connectedStations = new ArrayList[stationLines + 1]; //initializing the lines array (+1 cuz java is 0-indexed)
        
        for (int i = 1; i <= stationLines; i++) { //looping through the station lines
        	connectedStations[i] = new ArrayList<>(); //creating an arrayList for every line
        }

        for (int i = 1; i <= stations; i++) { //looping through every station
            lineMap[i] = scn.nextInt(); //mapping to the corresponding line
            connectedStations[lineMap[i]].add(i); //adding the mapping to the lines array
        }

        for (int i = 1; i <= stations; i++) { //looping through every station
        	passengers[i] = scn.nextInt(); //getting the amount of passengers for the current station
        }

        tree = new long[stations + 1]; //initializing the fenwick tree
        for (int i = 1; i <= stations; i++) { //looping through every station
        	addToTree(i, passengers[i]); //adding the station key along with the passenger amount at that station
        }

        changeOffset = new int[stationLines + 1]; //initializing the changes array (+1 cuz java is 0-indexed)
        isBig = new boolean[stationLines + 1]; //initializing the isBig array (+1 cuz java is 0-indexed)
        bigIndices = new int[stationLines + 1]; //initializing the big line indices array (+1 cuz java is 0-indexed)

        ArrayList<Integer> bigLines = new ArrayList<>(); //creating an arrayList storing the bigLine values
        for (int i = 1; i <= stationLines; i++) { //looping through all the station lines
            if (connectedStations[i].size() > THRESHOLD) { //if the line is a big line
                isBig[i] = true; //set the boolean flag accordingly
                bigIndices[i] = bigLines.size(); //set the index of the line in the big line indices array
                bigLines.add(i); //adding the line to the big lines arrayList
            }
        }

        bigLineSums = new long[bigLines.size()][]; //initializing the big line sums array
        
        for (int bigIndex = 0; bigIndex < bigLines.size(); bigIndex++) { //looping through every big line
            int line = bigLines.get(bigIndex); //getting the line
            ArrayList<Integer> stationConnections = connectedStations[line]; //getting the station connections of the line
            int connections = stationConnections.size(); //getting the amount of connections
            long[] sums = new long[connections + 1]; //defining an array to keep track of accumulative sums between stations (+1 cuz java is 0-indexed)
            for (int i = 0; i < connections; i++) { //for every station
                sums[i + 1] = sums[i] + passengers[stationConnections.get(i)]; //add the passenger to the total and set that as the next value in sums
            }
            bigLineSums[bigIndex] = sums; //set the sums array as the value for the big line in the big line sums array
        }

        for (int action = 0; action < actions; action++) { //for every action
            int type = scn.nextInt(); //getting the type of action being performed
            if (type == 1) { //if the action is a survey
                int startingStation = scn.nextInt(); //getting the starting station for the survey
                int endingStation = scn.nextInt(); //getting the ending station for the survey
                long passengerSum = sum(endingStation) - sum(startingStation - 1); //getting the total passengers in this sub-array (-1 cuz java is 0-indexed)
                output.append(passengerSum).append('\n'); //adding the result to the output
            } else { //if the action is to operate
                int lineToBeOperated = scn.nextInt(); //getting the line to be operated
                operate(lineToBeOperated); //operate the line
            }
        }

        System.out.print(output.toString()); //print out the final combined output
        scn.close(); //close the scanner
    }

    static void operate(int line) { //method to operate on a line
        if (!isBig[line]) { //if the line is a small line
            ArrayList<Integer> stationConnections = connectedStations[line]; //getting the station connections for the line
            int lastStation = stationConnections.get(stationConnections.size() - 1); //getting the last station in the line
            int lastPasssengers = passengers[lastStation]; //getting the passengers for that station
            
            for (int i = stationConnections.size() - 1; i > 0; i--) { //looping through every station in reverse
                int startingStation = stationConnections.get(i); //getting the starting station
                int endingStation = stationConnections.get(i - 1); //getting the ending station
                updateTree(startingStation, passengers[endingStation] - passengers[startingStation]); //updating the starting station's passenger count to the difference between the 2 station's passenger counts
                passengers[startingStation] = passengers[endingStation]; //setting the starting station's passenger count to the ending station's passenger count
            }
            updateTree(stationConnections.get(0), lastPasssengers - passengers[stationConnections.get(0)]); //updating the first station's passenger count to the difference between the last station's passenger count and its count
            passengers[stationConnections.get(0)] = lastPasssengers; //setting the first station's passenger count to the last station's passenger count
        } else { //if the line is a big line
            changeOffset[line] = (changeOffset[line] + 1) % connectedStations[line].size(); //calculates and records the offset done by the rotation of a big line
        }
    }

    static void addToTree(int station, long value) { //method to add
        for (; station <= stations; station += station & -station) { //looping through every relevant node being updated in the tree
        	tree[station] += value; //update the node accordingly
        }
    }

    static long sum(int station) { //method to calculate the accumulative sum of passengers
        long passengerSum = 0; //initially setting the sum to 0
        for (; station > 0; station -= station & -station) { //looping through the relevant stations in the tree
        	passengerSum += tree[station]; //adding the passenger count to the total
        }
        return passengerSum; //returning the total passenger sum
    }

    static void updateTree(int station, long difference) { //method to update changes to the tree
        addToTree(station, difference); //adds the difference in passengers to the station node
    }
}