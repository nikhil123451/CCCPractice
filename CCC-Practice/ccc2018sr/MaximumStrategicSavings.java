import java.util.*;

public class MaximumStrategicSavings { //taken from GPT and modified
    static int[] cityParents, cityParentDistances; //arrays for keeping track of parent/root nodes for cities and the distances to those nodes
    static int[] planetParents, planetParentDistances; //arrays for keeping track of parent/root nodes for planets and the distances to those nodes

    static int findCityParent(int node) { //helper method to find the city parent for a given node
        while (cityParents[node] != node) { //while we haven't found a node that leads to itself (the top-most parent)
            cityParents[node] = cityParents[cityParents[node]]; //setting the node's new parent to the parent of its current parent
            node = cityParents[node]; //setting the node to be its parent
        }
        return node; //returning the parent node after finding it
    }

    static int findPlanetParent(int node) { //helper method to find the planet parent for a given node
        while (planetParents[node] != node) { //while we haven't found a node that leads to itself (the top-most parent)
            planetParents[node] = planetParents[planetParents[node]]; //setting the node's new parent to the parent of its current parent
            node = planetParents[node]; //setting the node to be its parent
        }
        return node; //returning the parent node after finding it
    }

    static boolean cityMerge(int nodeA, int nodeB) { //helper method to merge city components if they aren't already in the same component
        nodeA = findCityParent(nodeA); //getting the top-most parent for nodeA
        nodeB = findCityParent(nodeB); //getting the top-most parent for nodeB
        if (nodeA == nodeB) { //if they have the same parent
        	return false; //return false and do nothing because they are already part of the same component
        }
        if (cityParentDistances[nodeA] < cityParentDistances[nodeB]) { //if nodeA is closer to its parent than B is
        	int placeholderVariable = nodeA; //storing the value of nodeA somewhere else for now
        	nodeA = nodeB; //setting nodeA to nodeB
        	nodeB = placeholderVariable; //setting node B to the previous value of nodeA
        }
        cityParents[nodeB] = nodeA; //setting nodeA as nodeB's parent
        cityParentDistances[nodeA] += cityParentDistances[nodeB]; //increment the distances accordingly
        return true; //return true for a successful city component merge
    }

    static boolean planetMerge(int nodeA, int nodeB) { //helper method to merge planet components if they aren't already in the same component
        nodeA = findPlanetParent(nodeA); //getting the top-most parent for nodeA
        nodeB = findPlanetParent(nodeB); //getting the top-most parent for nodeB
        if (nodeA == nodeB) { //if they have the same parent
        	return false; //return false and do nothing because they're already a part of the same component
        }
        if (planetParentDistances[nodeA] < planetParentDistances[nodeB]) { //if nodeA is closer to its parent than B is
        	int placeholderVariable = nodeA; //storing the value of nodeA somewhere else temporarily
        	nodeA = nodeB; //setting node A to the value of node B
        	nodeB = placeholderVariable; //setting node B to the previous value of nodeA
        }
        planetParents[nodeB] = nodeA; //setting node A to node B's parent
        planetParentDistances[nodeA] += planetParentDistances[nodeB]; //increment the distances accordingly
        return true; //return true for a successful planet component merge
    }

    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
    public static void main(String[] args) { //main method
        int planets = scn.nextInt(); //getting the number of planets
        int citiesPerPlanet = scn.nextInt(); //getting the amount of cities per planet
        int flights = scn.nextInt(); //getting the amount of flights
        int portals = scn.nextInt(); //getting the amount of portals

        int[] flightStarters = new int[flights]; //making an array for all the starting points for flights
        int[] flightEnders = new int[flights]; //making an array for all the ending points for flights
        long[] flightCosts = new long[flights]; //making an array for the costs of all flights
        long totalflightCost = 0; //variable for the total flight cost
        for (int i = 0; i < flights; ++i) { //looping through every flight
            flightStarters[i] = scn.nextInt() - 1; //setting the flight's starting point (-1 cuz java is 0-indexed)
            flightEnders[i] = scn.nextInt() - 1; //setting the flight's ending point (-1 cuz java is 0-indexed)
            flightCosts[i] = scn.nextLong(); //setting the flight's cost
            totalflightCost += flightCosts[i]; //increment the total cost accordingly
        }

        int[] portalStarters = new int[portals]; //making an array for all the starting points for portals
        int[] portalEnders = new int[portals]; //making an array for all the ending points for portals
        long[] portalCosts = new long[portals]; //making an array for the costs of all portals
        long totalPortalCost = 0; //variable for the total portal cost
        for (int j = 0; j < portals; ++j) { //looping through every portal
            portalStarters[j] = scn.nextInt() - 1; //setting the flight's starting point (-1 cuz java is 0-indexed)
            portalEnders[j] = scn.nextInt() - 1; //setting the flight's ending point (-1 cuz java is 0-indexed)
            portalCosts[j] = scn.nextLong(); //setting the portal's cost
            totalPortalCost += portalCosts[j]; //incrementing the total cost accordingly
        }
        
        long totalCost = totalflightCost * (long)planets + totalPortalCost * (long)citiesPerPlanet; //calculating the total cost of every flight and portal put together

        Integer[] flightIndices = new Integer[flights]; //making an Integer[] array to store flight indices
        for (int i = 0; i < flights; ++i) { //for every flight
        	flightIndices[i] = i; //set the index accordingly
        }
        Arrays.sort(flightIndices, (indexA, indexB) -> Long.compare(flightCosts[indexA], flightCosts[indexB])); //sorting the indices based on increasing costs

        Integer[] portalIndices = new Integer[portals]; //making an Integer[] array to store portal indices
        for (int i = 0; i < portals; ++i) { //for every portal
        	portalIndices[i] = i; //set the index accordingly
        }
        Arrays.sort(portalIndices, (indexA, indexB) -> Long.compare(portalCosts[indexA], portalCosts[indexB])); //sorting the indices based on increasing costs

        cityParents = new int[citiesPerPlanet]; //initializing the cityParents array
        cityParentDistances = new int[citiesPerPlanet]; //initializing the respective distances array
        for (int i = 0; i < citiesPerPlanet; ++i) { //for every city
        	cityParents[i] = i; //set every node to be its own parent initially
        	cityParentDistances[i] = 1; //the distance to yourself would just be one (inclusive)
        }
        planetParents = new int[planets]; //initializing the planetParents array
        planetParentDistances = new int[planets]; //initializing the respective distances array
        for (int i = 0; i < planets; ++i) { //for every planet
        	planetParents[i] = i; //set every node to be its own parent initially
        	planetParentDistances[i] = 1; //the distance to yourself would just be one (inclusive)
        }

        int flightPointer = 0, portalPointer = 0; //setting both flight and pointer nodes to 0 initially
        long minimumCost = 0; //setting the minimum cost of the final layout as 0 initially
        int cityComponents = citiesPerPlanet; //every city initially starts as its own component
        int planetComponents = planets; //every planet initially starts as its own component

        while ((flightPointer < flights || portalPointer < portals) && (cityComponents > 1 || planetComponents > 1)) { //while there are still flights or portals to be used and there are flights or portals not connected to the main component 
            long nextUnusedFlight = (flightPointer < flights) ? flightCosts[flightIndices[flightPointer]] : Long.MAX_VALUE; //getting the next unused flight
            long nextUnusedPortal = (portalPointer < portals) ? portalCosts[portalIndices[portalPointer]] : Long.MAX_VALUE; //getting the next unused portal

            if (nextUnusedFlight <= nextUnusedPortal) { //if the flight is cheaper than the portal
                int nextCheapestFlight = flightIndices[flightPointer++]; //getting the next cheapest flight after the pointer
                int starter = flightStarters[nextCheapestFlight]; //getting the starting node for the flight
                int ender = flightEnders[nextCheapestFlight]; //getting the ending node for the flight
                if (findCityParent(starter) != findCityParent(ender)) { //if they both aren't in the same component
                    cityMerge(starter, ender); //merge the 2 components
                    minimumCost += nextUnusedFlight * (long)planetComponents; //the cost will increase for every planet not connected
                    cityComponents--; //decrement the amount of city components accordingly
                }
            } else { //if the portal is the cheaper option
                int nextCheapestPortal = portalIndices[portalPointer++]; //getting the next cheapest portal after the pointer
                int starter = portalStarters[nextCheapestPortal]; //getting the starting node for the portal
                int ender = portalEnders[nextCheapestPortal]; //getting the ending node for the portal
                if (findPlanetParent(starter) != findPlanetParent(ender)) { //if they both aren't in the same component
                    planetMerge(starter, ender); //merge the 2 components
                    minimumCost += nextUnusedPortal * (long)cityComponents; //the cost will increase for every city not connected
                    planetComponents--; //decrement the amount of planet components accordingly
                }
            }
        }

        long amountSaved = totalCost - minimumCost; //calculating the amount of money saved by removing flights and/or portals
        System.out.println(amountSaved); //print out the result
        scn.close(); //closing the scanner
    }
}