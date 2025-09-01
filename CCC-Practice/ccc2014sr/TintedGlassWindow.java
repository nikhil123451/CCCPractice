import java.util.*;

public class TintedGlassWindow { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method

        int glassPieces = scn.nextInt(); //getting the number of glass pieces
        long tintFactorThreshold = scn.nextLong(); //getting the tint factor threshold

        int[] topLeftX = new int[glassPieces]; //array for the top left X coords for every rectangle
        int[] topLeftY = new int[glassPieces]; //array for the top left y coords for every rectangle
        int[] bottomRightX = new int[glassPieces]; //array for the bottom right x coords for every rectangle
        int[] bottomRightY = new int[glassPieces]; //array for the bottom right y coords for every rectangle
        long[] tintFactors = new long[glassPieces]; //array for the tint factor of every rectangle

        ArrayList<Integer> xCoordinateList = new ArrayList<>(); //making an arrayList for every x coord (for compression)
        ArrayList<Integer> yCoordinateList = new ArrayList<>(); //making an arrayList for every y coord (for compression)

        for (int i = 0; i < glassPieces; i++) { //looping through every glass piece
            topLeftX[i] = scn.nextInt(); //setting the top left x coord for the piece
            topLeftY[i] = scn.nextInt(); //setting the top left y coord for the piece
            bottomRightX[i] = scn.nextInt(); //setting the bottom right x coord for the piece
            bottomRightY[i] = scn.nextInt(); //setting the bottom right y coord for the piece
            tintFactors[i] = scn.nextLong(); //setting the tint factor for the piece

            xCoordinateList.add(topLeftX[i]); //adding the top left x coord to the x coords list
            xCoordinateList.add(bottomRightX[i]); //adding the bottom right x coord to the x coords list
            yCoordinateList.add(topLeftY[i]); //adding the top left y coord to the y coords list
            yCoordinateList.add(bottomRightY[i]); //adding the bottom right y coord to the y coords list
        }
        scn.close(); //closing the scanner

        int[] sweepedXCoords = uniqueSorted(xCoordinateList); //sweeping all the x coords so that they're all unique
        int[] sweepedYCoords = uniqueSorted(yCoordinateList); //sweeping all the y coords so that they're all unique
        
        Map<Integer,Integer> xIndex = new HashMap<>(); //creating an x index for compressed coords
        
        for (int i = 0; i < sweepedXCoords.length; i++) { //looping through the sweeped x coords
        	xIndex.put(sweepedXCoords[i], i); //mapping the sweeped coord with a unique index
        }
        
        Map<Integer,Integer> yIndex = new HashMap<>(); //creating a y index for compressed coords
        
        for (int i = 0; i < sweepedYCoords.length; i++) { //looping through the sweeped y coords
        	yIndex.put(sweepedYCoords[i], i); //mapping the sweeped coord with a unique index
        }

		@SuppressWarnings("unchecked")  //suppressing unchecked warnings because all conversions are happening properly
		List<long[]>[] events = new ArrayList[sweepedXCoords.length]; //creating an events 2d array
        for (int i = 0; i < sweepedXCoords.length; i++) { //for every sweeped coord
        	events[i] = new ArrayList<>(); //add a new arrayList to the events array
        }

        for (int i = 0; i < glassPieces; i++) { //looping through every glass piece
            int x1 = xIndex.get(topLeftX[i]); //getting the mapped index for the sweeped coord
            int x2 = xIndex.get(bottomRightX[i]); //getting the mapped index for the sweeped coord
            int y1 = yIndex.get(topLeftY[i]); //getting the mapped index for the sweeped coord
            int y2 = yIndex.get(bottomRightY[i]); //getting the mapped index for the sweeped coord
            
            events[x1].add(new long[]{y1, y2, tintFactors[i]}); //making sure that x1 marks the start of the rectangle's tint
            events[x2].add(new long[]{y1, y2, -tintFactors[i]}); //making sure that x2 marks the end of the rectangle's tint
        }

        long[] yIntervalTints = new long[sweepedYCoords.length - 1]; //array for the tint factors for every y interval
        long area = 0; //variable to keep track of the total area with the tint factor threshold

        for (int i = 0; i < sweepedXCoords.length - 1; i++) { //looping through the x intervals
            for (long[] event : events[i]) { //looping through every event for the current interval
                int y1 = (int) event[0]; //getting y1
                int y2 = (int) event[1]; //getting y2
                
                long deltaTint = event[2]; //getting the change in tint factor
                for (int y = y1; y < y2; y++) { //for every y value within the interval
                    yIntervalTints[y] += deltaTint; //add the change in tint
                }
            }

            long length = 0; //variable to store the current length
            
            for (int y = 0; y < sweepedYCoords.length - 1; y++) { //looping through each y interval
                if (yIntervalTints[y] >= tintFactorThreshold) { //if the tint factor at that interval is greater than or equal to the threshold
                    length += sweepedYCoords[y+1] - sweepedYCoords[y]; //increment the length accordingly
                }
            }

            long width = sweepedXCoords[i+1] - sweepedXCoords[i]; //setting the width to the x interval
            area += length * width; //calculating the area
        }

        System.out.println(area); //printing out the area after fully processing
    }

    private static int[] uniqueSorted(ArrayList<Integer> list) { //helper method to uniquely sort an arrayList
        Collections.sort(list); //sorts the list to begin with
        ArrayList<Integer> uniqueList = new ArrayList<>(); //creating a brand new arrayList
        Integer previousValue = null; //variable to store the previous value
        
        for (int value : list) { //looping through every value in the list
            if (previousValue == null || value != previousValue) { //if it's the first value in the list or the current value is different from the previous value
                uniqueList.add(value); //add the value to the unique list
                previousValue = value; //set the previous value as this current value for the next iteration
            }
        }
        
        int[] finalArray = new int[uniqueList.size()]; //making an array that's the same size as the unique list
        for (int i = 0; i < uniqueList.size(); i++) { //looping through the unique list
        	finalArray[i] = uniqueList.get(i); //manually setting each value in the array to the same indexed value in the list
        }
        return finalArray; //returning the final array
    }
}