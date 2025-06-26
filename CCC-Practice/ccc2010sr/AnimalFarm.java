import java.util.*;

public class AnimalFarm {//taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {
        int numberOfPens = scn.nextInt(); //total pen count
        int outsideNode = numberOfPens; //node that represents the outside of the pens

        Map<String, List<int[]>> edgeMap = new HashMap<>(); //initializing an edgeMap to keep track of all edges and connections
        List<int[]> edges = new ArrayList<>(); //making edges an ArrayList containing multiple integer arrays

        for (int i = 0; i < numberOfPens; i++) {//looping through each pen
            int penEdges = scn.nextInt(); //amount of edges for pen
            int[] corners = new int[penEdges]; //initializing corners int[] array which is equal to the amount of edges the pen has
            int[] costs = new int[penEdges]; //initializing costs int[] array which is equal to the amount of edges

            for (int j = 0; j < penEdges; j++) { //looping through every edge
            	corners[j] = scn.nextInt(); //making every element in corners[] the next int sequentially
            }
            
            for (int j = 0; j < penEdges; j++) { //looping through every edge
            	costs[j] = scn.nextInt(); //making every element in costs[] the next int sequentially after corners
            }

            for (int j = 0; j < penEdges; j++) { //looping through every edge
                int minCorner = corners[j]; //making the minCorner the first corner that hasnt been used
                int maxCorner = corners[(j + 1) % penEdges]; //making max corner the one right after, and looping back to the start once it's reached the last corner value
                int cost = costs[j]; //making the cost the cost marked at value j in the costs[] array

                int smallerCorner = Math.min(minCorner, maxCorner); //finding the smaller value between the 2 corners
                int biggerCorner = Math.max(minCorner, maxCorner); //finding the larger value between the 2 corners
                String key = smallerCorner + "," + biggerCorner; //making them into a key for the Map class

                edgeMap.putIfAbsent(key, new ArrayList<>()); //if there isn't already an edge with this key, then add it with the key and a new ArrayList
                edgeMap.get(key).add(new int[]{i, cost}); //add a new int[] array to the key which contains the pen# as well as the cost
            }
        }

        for (String key : edgeMap.keySet()) { //looping through every key in the edgeMap
            List<int[]> usage = edgeMap.get(key); //creates a list of every edge that has this key (edges that are touching) in the form [pen#, cost]
            if (usage.size() == 2) { //if there are 2 edges that are touching
                int[] firstEdge = usage.get(0), secondEdge = usage.get(1); //gets both edges
                int cost = Math.min(firstEdge[1], secondEdge[1]); //finding the smaller cost between the two edges
                edges.add(new int[]{firstEdge[0], secondEdge[0], cost}); //adding this edge to the edges list as [pen#, otherPen#, cost]
            } else if (usage.size() == 1) { //if there are no connections
                int[] edge = usage.get(0); //getting the edge
                edges.add(new int[]{edge[0], outsideNode, edge[1]}); //adding this edge to the edges list as [pen#, outside, cost] since it's touching the outside
            }
        }
        
        edges.sort(Comparator.comparingInt(e -> e[2])); //fancy way of sorting each list by ascending costs, for kruskal's min spanning tree algorithm

        int costWithOutside = kruskal(edges, numberOfPens + 1); //running the kruskal algorithm with the edges and the amount of nodes (including the outside)
        int costWithoutOutside = kruskal(
            edges.stream().filter(e -> e[0] != numberOfPens && e[1] != numberOfPens).toList(),
            numberOfPens
        ); //filtering out edges that include the outside and converting the stream back into a list. Putting the edges and node count (excluding outside) through the kruskal algorithm

        System.out.println(Math.min(costWithOutside, costWithoutOutside)); //taking both calculated costs, finding the smaller one, and then printing it out
    }

    static int find(int[] parent, int x) { //returns whatever root x belongs to (kruskal's algorithm)
        if (parent[x] != x) { //if index X is not equal to X
        	parent[x] = find(parent, parent[x]); //replace index X with another find with parent[x] being passed as a parameter recursively instead to ensure the value gets assigned properly
        }
        return parent[x]; //returns the value at index X
    }

    static int kruskal(List<int[]> edges, int nodeCount) { //implementation of kruskal's algorithm (min span tree (MST))
        int[] parent = new int[nodeCount]; //array of length nodeCount, tracks which nodes belong to which sets
        for (int i = 0; i < nodeCount; i++) { //looping through each node
        	parent[i] = i; //setting each element in the row to i (0,1,2,3,4....)
        }

        int cost = 0; //initializing cost to 0 for calculation purposes
        for (int[] edge : edges) {
            int startingNode = edge[0], endingNode = edge[1], costOfEdge = edge[2];
            int rootsOfStartingNode = find(parent, startingNode); //finds the roots of the startingNode
            int rootsOfEndingNode = find(parent, endingNode); //finds the roots of the endingNode
            if (rootsOfStartingNode != rootsOfEndingNode) { //checks if they aren't connected edges, makes sure there are no cycles
                parent[rootsOfEndingNode] = rootsOfStartingNode; //merge the 2 sets
                cost += costOfEdge; //add the cost of the edge to the total
            }
        }

        return cost; //returns the total cost calculated
    }
}
