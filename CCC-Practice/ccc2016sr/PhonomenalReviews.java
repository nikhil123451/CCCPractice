import java.util.*;

public class PhonomenalReviews { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int restaurants = scn.nextInt(); //getting the number of total restaurants
        int phoRestaurants = scn.nextInt(); //getting the number of pho restaurants

        boolean[] isPho = new boolean[restaurants]; //creating a boolean array to keep track of every restaurant that's a pho restaurant
        for (int i = 0; i < phoRestaurants; i++) { //for every pho restaurant
            isPho[scn.nextInt()] = true; //set the restaurant's id to true
        }

        List<List<Integer>> connections = new ArrayList<>(); //making a list of lists that keeps track of every restaurants connections to other restaurants
        for (int i = 0; i < restaurants; i++) { //looping through every restaurant
        	connections.add(new ArrayList<>()); //add a list for every restaurant
        }

        for (int i = 0; i < restaurants - 1; i++) { //for the n - 1 connections between restaurants
            int startingRestaurant = scn.nextInt(), endingRestaurant = scn.nextInt(); //getting the starting and ending restaurant
            connections.get(startingRestaurant).add(endingRestaurant); //making the path between start and end
            connections.get(endingRestaurant).add(startingRestaurant); //making the path between end and start
        }

        Queue<Integer> pruningQueue = new ArrayDeque<>(); //creating a double-ended queue to help the pruning process
        int remainingConnections = restaurants - 1; //getting the amount of connections as n - 1 as described in the problem and as described previously
        int[] connectionDegrees = new int[restaurants]; //making a degree array to keep track of the amount of connections every restaurant has
        for (int i = 0; i < restaurants; i++) { //for every restaurant
        	connectionDegrees[i] = connections.get(i).size(); //get the amount of connections the restaurant has and add it to the degree array
        }

        for (int i = 0; i < restaurants; i++) { //looping through every restaurant
            if (connectionDegrees[i] == 1 && !isPho[i]) { //if the restaurant has only one connection and the restaurant isn't a pho restaurant
            	pruningQueue.add(i); //add it to the pruning queue
            }
        }

        boolean[] removed = new boolean[restaurants]; //making a boolean array to keep track of all the restaurants that have been removed
        while (!pruningQueue.isEmpty()) { //while there are still nodes in the queue to prune
            int node = pruningQueue.poll(); //getting the top node in the queue
            removed[node] = true; //setting the node to be removed
            for (int leaf : connections.get(node)) { //looping through the leaf for every node
                connectionDegrees[leaf]--; //subtracting the amount of connections the leaf has
                if (!removed[leaf]) { //if the leaf has not already been removed
                    remainingConnections--; //remove the connection
                    if (connectionDegrees[leaf] == 1 && !isPho[leaf]) { //if the leaf has one connection and is not a pho restaurant
                    	pruningQueue.add(leaf); //add the leaf to the pruning queue
                    }
                }
            }
        }

        int startingNode = -1; //setting the initial starting node to an invalid value
        for (int i = 0; i < restaurants; i++) { //looping through every restaurant
            if (!removed[i]) { //if the restaurant hasn't been removed
                startingNode = i; //set the starting node to be that restaurant
                break; //we found a valid restaurant, so break the loop
            }
        }

        int farthestNode = findFarthestNodeAndDistance(startingNode, connections, removed).getKey(); //getting the farthestNode from the starting node given the connections and the removed nodes
        int distance = findFarthestNodeAndDistance(farthestNode, connections, removed).getValue(); //getting the distance of the farthest node given the connections and removed nodes

        System.out.println(2 * remainingConnections - distance); //printing out a round trip minus the distance of the farthest node, because you can start and end anywhere on the tree
        
        scn.close(); //closing the scanner
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> findFarthestNodeAndDistance(int startingNode, List<List<Integer>> connections, boolean[] removed) { //method to find the farthest node given a starting node, a connections list, and a removed node array
        Queue<Integer> processingQueue = new ArrayDeque<>(); //creating a queue for the nodes that need to be processed
        int[] distances = new int[connections.size()]; //creating a distances array for every node
        Arrays.fill(distances, -1); //filling the array with the initial invalid value -1
        processingQueue.add(startingNode); //add the starting node to start off
        distances[startingNode] = 0; //the distance to the starting node is 0

        int farthestNode = startingNode; //initially start with the farthest node being the starting node
        while (!processingQueue.isEmpty()) { //while there are nodes to be processed
            int currentNode = processingQueue.poll(); //getting the current node to be processed
            for (int leaf : connections.get(currentNode)) { //looping through every leaf the node has
                if (!removed[leaf] && distances[leaf] == -1) { //if the leaf has not been removed and does not have a proper distance assigned to it yet
                    distances[leaf] = distances[currentNode] + 1; //set the distance to what the current node's distance is + 1
                    processingQueue.add(leaf); //add the leaf to the queue to be processed
                    if (distances[leaf] > distances[farthestNode]) { //if the distance of the leaf is greater than the distance of the farthest node
                    	farthestNode = leaf; //set the leaf as the new farthest node
                    }
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(farthestNode, distances[farthestNode]); //return the farthest node and the distance of that node after processing
    }
}