import java.util.*;

public class MinimumCostFlow { //taken from GPT and modified
    static int buildings, pipes; //declaring variables for the number of buildings and the number of pipes
    static long enhancerStrength; //declaring a variable for the strength of the enhancer

    static int[] edgeBuildingA, edgeBuildingB; //declaring int arrays to keep track of starting and ending points of edges
    static long[] edgeCost; //declaring an int array for edge costs
    static boolean[] isOriginal, inMinimumTree; //declaring boolean arrays to keep track if a specific edge is part of the original plan and if it is part of the minimum spanning tree (MST)

    static int[] parent, rank; //declaring int arrays for the parent of a given node and creating a shallow tree respectively

    static ArrayList<ArrayList<int[]>> adjacencies; //a list that contains connections that every node has
    static final int maximumJumpLength = 18; //the number that denotes how many jumps up the tree we can make
    static int[][] upperAncestor; //upperAncestor[i][j] gives the node of the 2^i-th ancestor of node j
    static long[][] maximumWeight; //array to store the maximum cost of the distance from node j to the 2^i-th ancestor of node j
    static boolean[][] maximumInitial, notMaximumInitial; //boolean arrays to keep track whether the path from node j to their ancestor is or is not part of the initial plan
    static int[] distanceToFirstBuilding; //array that stores the distance to building one for every node
    static long totalTreeCost; //declaring a variable to keep track of the total accumulated cost of all the edges in the tree
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner

    public static void main(String[] args) { //main method
        buildings = scn.nextInt(); //getting the number of buildings
        pipes = scn.nextInt(); //getting the number of pipes connecting the buildings
        enhancerStrength = scn.nextLong(); //getting the strength of the enhancer

        edgeBuildingA = new int[pipes]; //initializing the edgeBuildingA array
        edgeBuildingB = new int[pipes]; //initializing the edgeBuildingB array
        edgeCost = new long[pipes]; //initializing the cost array
        isOriginal = new boolean[pipes]; //initializing the original-checking array
        inMinimumTree = new boolean[pipes]; //initializing the MST-checking array

        for (int i = 0; i < pipes; i++) { //looping through all the pipes
            edgeBuildingA[i] = scn.nextInt(); //getting building A for the pipe
            edgeBuildingB[i] = scn.nextInt(); //getting building B for the pipe
            edgeCost[i] = scn.nextLong(); //getting the cost for the pipe
            if (i < buildings - 1) { //if the pipe can fit between 2 buildings
            	isOriginal[i] = true; //set it to be a part of the original layout
            } else { //if it's an extra pipe
            	isOriginal[i] = false; //it will be a part of the new layout
            }
        }

        Integer[] edgeIndexes = new Integer[pipes]; //creating an Integer array for the indices of every edge (or pipe in this case)
        for (int i = 0; i < pipes; i++) { //looping through every pipe
        	edgeIndexes[i] = i; //the edge's index will go from 0 to the number of pipes - 1
        }
        Arrays.sort(edgeIndexes, Comparator.comparingLong(i -> edgeCost[i])); //sorting the indexes in ascending order of the edge costs

        initializeSetUnion(buildings); //creating the set union for the buildings
        adjacencies = new ArrayList<>(); //initializing the adjacencies list
        for (int i = 0; i <= buildings; i++) { //looping through every building
        	adjacencies.add(new ArrayList<>()); //adding a new array list for every building
        }

        totalTreeCost = 0; //setting the initial tree cost to 0
        int edgesUsed = 0; //no edges have been added to the MST
        for (int index : edgeIndexes) { //looping through every edge index
            if (areNowUnited(edgeBuildingA[index], edgeBuildingB[index])) { //if the the edge recently connected 2 buildings
                inMinimumTree[index] = true; //add the edge to the MST
                totalTreeCost += edgeCost[index]; //increment the total cost accordingly
                adjacencies.get(edgeBuildingA[index]).add(new int[]{edgeBuildingB[index], index}); //adds the connection from a to b
                adjacencies.get(edgeBuildingB[index]).add(new int[]{edgeBuildingA[index], index}); //adds the connection from b to a
                edgesUsed++;  //increment the amount of edges used
                if (edgesUsed == buildings - 1) { //if we've used all the available edges
                	break; //break the loop
                }
            }
        }

        int commonEdges = 0; //initializing a counter variable to 0. the variable counts the amount of common edges there are between the original layout and the MST
        for (int i = 0; i < pipes; i++) //looping through every edge (pipe)
            if (inMinimumTree[i] && isOriginal[i]) //if the edge is in the MST and the original layout
                commonEdges++; //increment the counter accordingly

        upperAncestor = new int[maximumJumpLength][buildings + 1]; //initializing the upperAncenstor 2d array
        maximumWeight = new long[maximumJumpLength][buildings + 1]; //initializing the maximum cost weightings 2d array
        maximumInitial = new boolean[maximumJumpLength][buildings + 1]; //initializing a boolean 2d array to keep track of whether the maximum cost weighting is in the original layout
        notMaximumInitial = new boolean[maximumJumpLength][buildings + 1]; //initializing the counterpart boolean 2d array
        distanceToFirstBuilding = new int[buildings + 1]; //initializing the distance 2d array

        search(1, 0); //initiates a search from edge 0 to the first building which goes through the rest of the tree

        for (int k = 1; k < maximumJumpLength; k++) { //looping through the possible jump lengths
            for (int v = 1; v <= buildings; v++) { //looping through the building numbers
                int ancestor = upperAncestor[k - 1][v]; //getting the upper ancestor for node v
                if (ancestor == 0) { //if there is no ancestor
                	continue; //continue through the building loop
                }
                long lowerWeight = maximumWeight[k - 1][v]; //getting the weighted total cost from node v to its ancestor
                long upperWeight = maximumWeight[k - 1][ancestor]; //getting the weighted total cost from the ancestor to its ancestor
                if (lowerWeight > upperWeight) { //if the lower weight cost is the maximum between the 2
                    upperAncestor[k][v] = upperAncestor[k - 1][ancestor]; //setting the k-th ancestor to the next ancestor down
                    maximumWeight[k][v] = lowerWeight; //updating the new maximum weight
                    maximumInitial[k][v] = maximumInitial[k - 1][v]; //setting the original layout boolean flag accordingly
                    notMaximumInitial[k][v] = notMaximumInitial[k - 1][v]; //setting the counterpart accordingly
                } else if (upperWeight > lowerWeight) { //if the upper weight cost is the maximum between the 2
                    upperAncestor[k][v] = upperAncestor[k - 1][ancestor]; //setting the k-th ancestor to the next ancestor down
                    maximumWeight[k][v] = upperWeight; //updating the maximum weight
                    maximumInitial[k][v] = maximumInitial[k - 1][ancestor]; //setting the original layout boolean flag accordingly
                    notMaximumInitial[k][v] = notMaximumInitial[k - 1][ancestor]; //setting the counterpart accordingly
                } else { //if they are the same
                    upperAncestor[k][v] = upperAncestor[k - 1][ancestor]; //setting the k-th ancestor to the next ancestor down
                    maximumWeight[k][v] = lowerWeight; //updating the maximum weight
                    maximumInitial[k][v] = maximumInitial[k - 1][v] || maximumInitial[k - 1][ancestor]; //setting the original layout boolean flag to whether or not one of the others are on there
                    notMaximumInitial[k][v] = notMaximumInitial[k - 1][v] || notMaximumInitial[k - 1][ancestor]; //setting the counterpart in the same manner
                }
            }
        }

        long bestCost = Long.MAX_VALUE; //setting the best cost as the literal maximum value we can calculate
        int bestDays = Integer.MAX_VALUE; //assuming it will take an infinite amount of days to get the optimal layout

        for (int i = 0; i < pipes; i++) { //looping through every "edge"
            if (!inMinimumTree[i]) { //if the edge is not in the MST
            	continue; //go to the next edge
            }
            long newCost = totalTreeCost - Math.min(enhancerStrength, edgeCost[i]); //defining the new cost as defined in the problem
            int days = (buildings - 1) - commonEdges; //defining days as the amount of new edges to be placed
            if (newCost < bestCost || (newCost == bestCost && days < bestDays)) { //if the new cost is better than the current best cost or if cost is the same but the day count has gone down
                bestCost = newCost; //setting the new best cost accordingly
                bestDays = days; //setting the new minimum day count accordingly
            }
        }

        for (int i = 0; i < pipes; i++) { //looping through every "edge"
            if (inMinimumTree[i]) { //if the edge is in the MST
            	continue; //go to the next edge
            }
            long addedCost = Math.max(0, edgeCost[i] - enhancerStrength); //getting the added cost, accounting for the enhancer
            long[] details = getPathDetails(edgeBuildingA[i], edgeBuildingB[i]); //gets the details of the path between building a and b in the form {largestEdgeCost, a 1 or 0 denoting if the edge is part of the original layout, a 1 or 0 denoting if the edge is not part of the original layout}
            long largestEdgeCost = details[0]; //getting the largest edge cost
            boolean notInOriginal = details[2] == 1; //getting the boolean flag for whether or not the edge is in not in the original layout

            if (addedCost <= largestEdgeCost) { //if the added cost is less than or equal to the largest recorded edge cost
                long newCost = totalTreeCost + addedCost - largestEdgeCost; //computing the new total cost
                if (newCost > bestCost) { //if the new calculated cost is not better than the current best
                	continue; //keep going through the loop
                }
                int incrementValue = isOriginal[i] ? 1 : 0; //increment common edges by 1 if the edge is in the original layout. do not increment otherwise
                int removeValue = notInOriginal ? 0 : 1; //remove a common edge if we are, in fact, removing an edge from the original layout. do not remove anything if we are not
                int newCommon = commonEdges + incrementValue - removeValue; //calculating the new number of common edges
                int days = (buildings - 1) - newCommon; //calculating the new minimum amount of days
                if (newCost < bestCost || (newCost == bestCost && days < bestDays)) { //if the new cost is better than the best cost or if the new cost is the same but the day count is a new minimum
                    bestCost = newCost; //set the best cost accordingly
                    bestDays = days; //set the day count accordingly
                }
            }
        }

        if (bestDays == Integer.MAX_VALUE) { //if the day count was never changed
        	bestDays = (buildings - 1) - commonEdges; //set the minimum day count as 0 since the layout was already optimal
        }
        System.out.println(bestDays); //print out the result
        scn.close(); //close the scanner
    }

    static void initializeSetUnion(int nodes) { //helper method to create the set union tree
        parent = new int[nodes + 1]; //making an array to keep track of parent nodes
        rank = new int[nodes + 1]; //making an array to keep track of node ranks
        for (int i = 1; i <= nodes; i++) { //looping through every node
        	parent[i] = i; //setting the node's parent accordingly
        }
    }
    static int findParent(int node) { //helper method to find a node's parent
        if (parent[node] != node) { //if the node has a parent
        	parent[node] = findParent(parent[node]); //find the parent
        }
        return parent[node]; //return the parent node
    }
    static boolean areNowUnited(int nodeA, int nodeB) { //helper method to check if 2 nodes have been recently connected
        nodeA = findParent(nodeA); //getting the parent node for nodeA
        nodeB = findParent(nodeB); //getting the parent node for nodeB
        if (nodeA == nodeB) { //if they are the same as they were before
        	return false; //return false since they were always connected like this
        }
        if (rank[nodeA] < rank[nodeB]) { //if the rank of nodeA is less than the rank of nodeB
        	parent[nodeA] = nodeB; //set nodeB as nodeA's parent
        }
        else if (rank[nodeB] < rank[nodeA]) { //if the opposite is the case
        	parent[nodeB] = nodeA; //set nodeA as nodeB's parent
        }
        else { //if their ranks are equal
        	parent[nodeB] = nodeA; //set nodeA as nodeB's parent
        	rank[nodeA]++; //increment nodeA's rank accordingly
        }
        return true; //return true since we have just done the reconnecting
    }

    static void search(int node, int connectedEdgeIndex) { //helper method that runs of depth-first search on the MST
        for (int[] pair : adjacencies.get(node)) { //looping through the node's connections
            int connectedNode = pair[0]; //getting the node that's connected to the original node
            int index = pair[1]; //getting the new node's index
            if (connectedNode == connectedEdgeIndex) { //if the new node wraps back to the original node
            	continue; //continue through the loop
            }
            distanceToFirstBuilding[connectedNode] = distanceToFirstBuilding[node] + 1; //increment the distance accordingly
            upperAncestor[0][connectedNode] = node; //update the ancestor accordingly
            maximumWeight[0][connectedNode] = edgeCost[index]; //update the weighted cost accordingly
            maximumInitial[0][connectedNode] = isOriginal[index]; //update the boolean flag for the location in the original layout accordingly
            notMaximumInitial[0][connectedNode] = !isOriginal[index]; //update the counterpart accordingly
            search(connectedNode, node); //continue the search once more with the new node and the previous node (which will be the edge index)
        }
    }

    static long[] getPathDetails(int nodeA, int nodeB) { //helper method to get details about the path from nodeA to nodeB
        long largestEdgeCost = -1; //initially set the largest edge cost to an impossible amount
        boolean inOriginal = false; //initially assume the edge with the largest cost is in the original layout
        boolean notInOriginal = false; //initially assume the edge is not in the original layout as well

        if (distanceToFirstBuilding[nodeA] < distanceToFirstBuilding[nodeB]) { //if nodeA is of a shorter distance down the tree compared to nodeB
            int nodeStorage = nodeA; //keeps nodeA in storage
            nodeA = nodeB; //sets nodeA to nodeB
            nodeB = nodeStorage; //sets nodeB to what was previously nodeA
        }

        int distanceBetweenNodes = distanceToFirstBuilding[nodeA] - distanceToFirstBuilding[nodeB]; //gets the distance between nodeA and nodeB
        for (int k = 0; k < maximumJumpLength; k++) { //looping through the maximum jump length
            if ((distanceBetweenNodes & (1 << k)) != 0) { //if a jump of 2^k aligns the nodes
                if (maximumWeight[k][nodeA] > largestEdgeCost) { //if the weighted cost of the jump from nodeA to it's next ancestor is greater than the largest edge cost
                    largestEdgeCost = maximumWeight[k][nodeA]; //set the new largest accordingly
                    inOriginal = maximumInitial[k][nodeA]; //set the boolean flag for the original layout presence accordingly
                    notInOriginal = notMaximumInitial[k][nodeA]; //set the counterpart accordingly
                } else if (maximumWeight[k][nodeA] == largestEdgeCost) { //if the 2 costs are the same
                    inOriginal |= maximumInitial[k][nodeA]; //update the boolean flag for the original layout presence accordingly based on it's initial state
                    notInOriginal |= notMaximumInitial[k][nodeA]; //update its counterpart in the same manner
                }
                nodeA = upperAncestor[k][nodeA]; //set nodeA as it's new ancestor
            }
        }

        if (nodeA == nodeB) { //if nodes a and b are the same node
        	return new long[]{largestEdgeCost, inOriginal ? 1 : 0, notInOriginal ? 1 : 0}; //return the edge details in the format {largestEdgeCost, inOriginal, notInOriginal}
        }

        for (int k = maximumJumpLength - 1; k >= 0; k--) { //looping through the maximum jump length in reverse order (from 18 to 0)
            if (upperAncestor[k][nodeA] != 0 && upperAncestor[k][nodeA] != upperAncestor[k][nodeB]) { //if the new ancestor of nodeA is not the first node and nodes a and b do not share this ancestor
                long[] details = combine(largestEdgeCost, inOriginal, notInOriginal, maximumWeight[k][nodeA], maximumInitial[k][nodeA], notMaximumInitial[k][nodeA]); //getting the details by combining the current data and the data for nodeA
                
                largestEdgeCost = details[0]; //getting the new largest edge cost
                inOriginal = details[1] == 1;  //getting the new value for the boolean flag
                notInOriginal = details[2] == 1; //getting the new value for the counterpart
                
                details = combine(largestEdgeCost, inOriginal, notInOriginal, maximumWeight[k][nodeB], maximumInitial[k][nodeB], notMaximumInitial[k][nodeB]); //combining the details of nodes A and B
                
                largestEdgeCost = details[0]; //getting the new largest edge cost
                inOriginal = details[1] == 1; //getting the new value for the boolean flag
                notInOriginal = details[2] == 1; //getting the new value for the counterpart
                
                nodeA = upperAncestor[k][nodeA]; //setting nodeA as its next ancestor
                nodeB = upperAncestor[k][nodeB]; //setting nodeB as its next ancestor
            }
        }

        long[] aDetails = combine(largestEdgeCost, inOriginal, notInOriginal, maximumWeight[0][nodeA], maximumInitial[0][nodeA], notMaximumInitial[0][nodeA]); //combining current data and data for nodeA
        
        largestEdgeCost = aDetails[0]; //getting the new largest edge cost
        inOriginal = aDetails[1] == 1; //getting the new value for the boolean flag
        notInOriginal = aDetails[2] == 1; //getting the new value for its counterpart
        
        long[] bDetails = combine(largestEdgeCost, inOriginal, notInOriginal, maximumWeight[0][nodeB], maximumInitial[0][nodeB], notMaximumInitial[0][nodeB]); //combining data for nodes a and b
        
        largestEdgeCost = bDetails[0]; //getting the new largest edge cost
        inOriginal = bDetails[1] == 1; //getting the new value for the boolean flag
        notInOriginal = bDetails[2] == 1; //getting the new value for its counterpart

        return new long[]{largestEdgeCost, inOriginal ? 1 : 0, notInOriginal ? 1 : 0}; //returning the details
    }

    static long[] combine(long largestEdgeCost, boolean inOriginal, boolean notInOriginal, long newLargestEdgeCost, boolean newInOriginal, boolean newNotInOriginal) { //helper method to combine path details
        if (newLargestEdgeCost > largestEdgeCost) { //if the new edge cost is greater than the current largest
        	return new long[]{newLargestEdgeCost, newInOriginal ? 1 : 0, newNotInOriginal ? 1 : 0}; //return the new values
        }
        if (newLargestEdgeCost < largestEdgeCost) { //if the new edge cost is less than the current largest
        	return new long[]{largestEdgeCost, inOriginal ? 1 : 0, notInOriginal ? 1 : 0}; //return the current values
        }
        return new long[]{largestEdgeCost, (inOriginal || newInOriginal) ? 1 : 0, (notInOriginal || newNotInOriginal) ? 1 : 0}; //return the current largest edge cost along with the states of the current or new layout states
    }
}