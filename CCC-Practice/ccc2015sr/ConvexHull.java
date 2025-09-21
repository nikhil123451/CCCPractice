import java.util.*;

public class ConvexHull { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	static final long MAXIMUM = Long.MAX_VALUE / 4; //defining the maximum value for calculations
	
    public static void main(String[] args) { //main method
        int hullThickness = scn.nextInt(); //getting the thickness of the convex hull
        int islands = scn.nextInt(); //getting the number of islands
        int routes = scn.nextInt(); //getting the number of routes

        @SuppressWarnings("unchecked") //suppressing unchecked warnings cuz we're careful
		ArrayList<int[]>[] routeInformations = new ArrayList[islands+1]; //an arrayList containing int[] arrays that store each edge for each island node (+1 cuz java is 0-indexed)
        
		for (int i = 1; i <= islands; i++) { //looping through every island
			routeInformations[i] = new ArrayList<>(); //creating a new arrayList for every island
		}

        for (int i = 0; i < routes; i++) { //looping through every route
            int intitialIsland = scn.nextInt(); //getting the initial island of the route
            int finalIsland = scn.nextInt(); //getting the destination of the route
            int time = scn.nextInt(); //getting the time it takes to travel the route
            int wear = scn.nextInt(); //getting the amount that will be taken off the convex hull
            
            routeInformations[intitialIsland].add(new int[]{finalIsland, time, wear}); //adding the edge between the 2 nodes
            routeInformations[finalIsland].add(new int[]{intitialIsland, time, wear}); //adding the edge between the 2 nodes, but reversed
        }

        int islandA = scn.nextInt(); //getting the target starting island
        int islandB = scn.nextInt(); //getting the target ending island
        
        int hullWearLimit = hullThickness - 1; //defining the hull's wear limit as it's total thickness - 1
        
        long[][] minimumTimes = new long[islands+1][hullWearLimit+1]; //creating a 2d array for the times it would take to reach an island given the wear factor (+1 cuz java is 0-indexed)
        for (int i = 1; i <= islands; i++) { //looping through every island
        	Arrays.fill(minimumTimes[i], MAXIMUM); //make everything initially unreachable
        }

        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(x -> x[0])); //defining a queue to make calculations
        minimumTimes[islandA][0] = 0; //setting the time to get to islandA with no hull wear to 0
        priorityQueue.add(new long[]{0, islandA, 0}); //adding a long[] array in the queue to be processed in the form {time, node, usedWear}

        while (!priorityQueue.isEmpty()) { //while there are still long[] arrays to be processed in the queue
            long[] current = priorityQueue.poll(); //getting the top-most array in the queue
            long time = current[0]; //getting the time
            int node = (int) current[1]; //getting the node
            int usedWear = (int) current[2]; //getting the usedWear
            if (time != minimumTimes[node][usedWear]) { //if the current time is not the thought-to-be minimum
            	continue; //keep going through the while loop
            }

            for (int[] edge : routeInformations[node]) { //looping through every edge for the current node
                int destinationNode = edge[0]; //getting the node this route connects to
                int routeTime = edge[1]; //getting the time it will take
                int routeWear = edge[2]; //getting the amount of wear the route will cause
                
                int totalWear = usedWear + routeWear; //getting the total wear
                if (totalWear > hullWearLimit) { //if the wear exceeds the limit
                	continue; //keep going since this edge won't work
                }
                
                long totalTime = time + routeTime; //getting the total time
                if (totalTime < minimumTimes[destinationNode][totalWear]) { //if the total time is a new minimum
                    minimumTimes[destinationNode][totalWear] = totalTime; //set the total time as the new minimum
                    priorityQueue.add(new long[]{totalTime, destinationNode, totalWear}); //add this to the queue
                }
            }
        }

        long calculatedMinimumTime = MAXIMUM; //set the minimum time as "unreachable" to begin with
        for (int w = 0; w <= hullWearLimit; w++) { //for every wear of hull
        	calculatedMinimumTime = Math.min(calculatedMinimumTime, minimumTimes[islandB][w]); //getting the minimum travel time between the current minimum and the minimum recorded for the current wear
        }
        
        System.out.println(calculatedMinimumTime == MAXIMUM ? -1 : calculatedMinimumTime); //print out -1 if the calculated minimum stayed as "unreachable", print out the calculated minimum otherwise
        scn.close(); //close the scanner
    }
}