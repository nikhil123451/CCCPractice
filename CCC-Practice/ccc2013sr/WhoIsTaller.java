import java.util.*;

public class WhoIsTaller { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method

        int people = scn.nextInt(); //getting the number of people
        int comparisonsDone = scn.nextInt(); //getting the number of comparisons already done
        
        if (people >= 1 && people <= 1000000 && comparisonsDone >= 1 && comparisonsDone <= 10000000) { //if people and comparisons are within the domain provided by the problem
        	
        	List<List<Integer>> heightGraph = new ArrayList<>(); //making a 2d height graph containing nodes representing taller and shorter people
            for (int i = 0; i <= people; i++) { //looping through every person (+1 cuz java is 0-indexed)
                heightGraph.add(new ArrayList<>()); //for every person, add a node to the graph
            }

            for (int i = 0; i < comparisonsDone; i++) { //looping through every comparison done
                int taller = scn.nextInt(); //get the taller person from the comparison
                int shorter = scn.nextInt(); //get the shorter person from the comparison
                if (taller >= 1 && shorter <= people) { //if the taller and shorter person fit within the domain given by the problem
                    heightGraph.get(taller).add(shorter); //adds the shorter person to the taller node
                }
            }

            int personP = scn.nextInt(); //getting the unknown person P
            int personQ = scn.nextInt(); //getting the unknown person Q
            
            if (personP >= 1 && personQ <= people) { //if people P and Q fit within the restrictions given by the problem
            	if (findChain(heightGraph, personP, personQ, people)) { //if there is a chain of heights that leads to person P being taller than person Q
                    System.out.println("yes"); //print out yes
                } else if (findChain(heightGraph, personQ, personP, people)) { //if there is a chain of heights that leads to person Q being taller than person P
                    System.out.println("no"); //print out no
                } else { //if there are no chains of either scenario
                    System.out.println("unknown"); //print unknown since we didn't get enough info
                }
            }
        }
        
        scn.close(); //close the scanner
    }

    private static boolean findChain(List<List<Integer>> heightGraph, int nodeA, int nodeB, int nodes) { //method to find if a chain between 2 nodes exists
        boolean[] visited = new boolean[nodes + 1]; //making a boolean array to see if we've visited each node (+1 cuz java is 0-indexed)
        Queue<Integer> nodeQueue = new LinkedList<>(); //making a queue to keep track of every node we've processed
        nodeQueue.add(nodeA); //adding the starting node
        visited[nodeA] = true; //setting visited to true

        while (!nodeQueue.isEmpty()) { //while there are still nodes in the queue to process
            int currentNode = nodeQueue.poll(); //grabbing and removing the top node in the queue
            if (currentNode == nodeB) { //if the current node is the ending node we want to reach
            	return true; //return true since we've completed the chain
            }

            for (int neighbor : heightGraph.get(currentNode)) { //for every shorter person than the current person
                if (!visited[neighbor]) { //if we haven't processed this person yet
                    visited[neighbor] = true; //set visited to true
                    nodeQueue.add(neighbor); //add them to the node queue
                }
            }
        }

        return false; //return false after going through all the nodes in the queue, finding no result
    }
}
