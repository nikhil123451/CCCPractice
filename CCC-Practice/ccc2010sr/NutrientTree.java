import java.util.*;

public class NutrientTree { //taken from GPT and modified
    static final int MAX_NODES = 1000; //maxiumum amount of nodes for a tree

    static Scanner scn = new Scanner(System.in); //creating the scanner
    static String input; //declaring an input variable
    static int index = 0; //index to keep track of nodes

    static boolean[] isLeaf = new boolean[MAX_NODES]; //array that keeps track of which nodes are leaves
    static int[] nutrients = new int[MAX_NODES]; //array that keeps track of how many nutrients are at each node
    static int[][] children = new int[MAX_NODES][2]; //keeps track of a node's 2 children (0 being left, and 1 being right)
    static int[][] dynamicProgramCache; //cache to store results so that computation isn't repeated if it doesn't need to be
    static int nodeId = 0; //static variable that allows each node to have a unique id
    static int numberOfGrowthAgents; //static variable that keeps track of the amount of growth agents inputed

    public static void main(String[] args) { //main method
        input = scn.nextLine(); //takes the first line in the console and sets that to be the input
        numberOfGrowthAgents = Integer.parseInt(scn.nextLine()); //takes the second line and parses it as an int for the number of growth agents

        index = 0; //initial index of 0
        nodeId = 0; //first node will have a value of 0
        
        int root = parseNode(); //parses the input into a nodeID and sets that as the root

        dynamicProgramCache = new int[nodeId][numberOfGrowthAgents + 1]; //initializes the dynamic programming cache that is the amount of nodes by the amount of growing agents (including 0)
        for (int i = 0; i < nodeId; i++) { //loops through every node
            Arrays.fill(dynamicProgramCache[i], -1); //sets every value in the 2d array to -1 to indicated that they haven't been computed yet
        }
        int maximumAmountOfNutrients = dynamicProgram(root, numberOfGrowthAgents); //using dynamic programming to calculate the max amount of nutrients given the root and the amount of growth agents used

        System.out.println(maximumAmountOfNutrients); //printing out the calculated result
    }

    static void skipSpaces() { //skips space characters when parsing a node
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) { //loops through the input string, and checks if each character is a proper character eg. (, 5, 3
            index++; //increments the index for every proper character
        }
    }

    static int parseNode() { //properly taking in an input as a node
        skipSpaces(); //skip all the whitespace in the input

        if (index >= input.length()) { //if the index is greater than the input length for whatever reason
            throw new IllegalArgumentException("Unexpected end of input at position " + index); //throw an illegal argument exception
        }

        char character = input.charAt(index); //storing the character in a variable of type char

        if (character == '(') { //it it's a left bracket
            index++; //skip the bracket
            skipSpaces(); //skip whitespace

            int leftChildId = parseNode(); //parsing the left child as a node
            skipSpaces(); //skipping whitespace

            int rightChildId = parseNode(); //parsing the right child as a node
            skipSpaces(); //skip the whitespace

            if (index >= input.length() || input.charAt(index) != ')') { //if the index is greater than the length for whatever reason, or if the character is not a right bracket
                throw new IllegalArgumentException("Expected ')' at position " + index + ", but found '" +
                        (index < input.length() ? input.charAt(index) : "EOF") + "'"); //throw an illegal argument exception
            }
            index++; //skip the right bracket
            return storeParentNode(leftChildId, rightChildId); //stores the node that connects the children and returns its id

        } else if (Character.isDigit(character)) { //if the character is a number
            int nutrientAmount = 0; //initializing the number of nutrients to 0
            while (index < input.length() && Character.isDigit(input.charAt(index))) { //looping through the input string. and continually checking if the character at the index is a number
                nutrientAmount = nutrientAmount * 10 + (input.charAt(index++) - '0'); //parsing numbers that might be greater than 1 digit long
            }
            return storeLeafNode(nutrientAmount); //stores the leaf node with the specific nutrient value and returns its id
        } else { //if the character isn't recognized as a number or bracket
            throw new IllegalArgumentException("Unexpected character at position " + index + ": '" + character + "'"); //throw an illegal argument exception
        }
    }

    static int storeLeafNode(int nutrientAmount) { //storing a leaf node with a nutrient value
        int newNodeId = nodeId++; //setting the id to the next available one
        isLeaf[newNodeId] = true; //making sure the program knows the node is a leaf
        nutrients[newNodeId] = nutrientAmount; //adding nutrientAmount to the nutrient list
        return newNodeId; //returning the id of the leaf node
    }

    static int storeParentNode(int leftChildId, int rightChildId) { //storing a parent node
        int newNodeId = nodeId++; //setting the id to the next available one
        isLeaf[newNodeId] = false; //making sure the program knows the node is not a leaf
        children[newNodeId][0] = leftChildId; //setting the left child of the node
        children[newNodeId][1] = rightChildId; //setting the right child of the node
        return newNodeId; //returning the id of the parent node
    }

    static int dynamicProgram(int localNodeId, int amountOfGrowthAgents) { //using dynamic programming to calculate the maximum amount of nutrients that can flow into the root of the tree
        if (dynamicProgramCache[localNodeId][amountOfGrowthAgents] != -1) { //if the node with specific growth has already been calculated
        	return dynamicProgramCache[localNodeId][amountOfGrowthAgents]; //return that value (which is the maximum)
        }

        int maximumAmountOfNutrients; //declaring the maximum amount of nutrients as a variable

        if (isLeaf[localNodeId]) { //if the node is a leaf node
            maximumAmountOfNutrients = nutrients[localNodeId] + amountOfGrowthAgents; //setting the max nutrients to the amount of nutrients in the leaf + the amount of growth agents
        } else { //if the node is a parental node
            int leftChild = children[localNodeId][0]; //getting the left child of the parent node
            int rightChild = children[localNodeId][1]; //getting the right child of the parent node

            int[] leftCache = new int[amountOfGrowthAgents + 1]; //creating an int array to store all the calculations for the left child (+1 cuz java is 0 indexed)
            int[] rightCache = new int[amountOfGrowthAgents + 1]; //creating an int array to store all the calculations for the right child (+1 cuz java is 0 indexed)

            for (int i = 0; i <= amountOfGrowthAgents; i++) { //looping through the growth agents (+1 since java is 0 indexed)
                leftCache[i] = dynamicProgram(leftChild, i); //running the dynamic program down through the tree for the left child and storing the result
                rightCache[i] = dynamicProgram(rightChild, i); //running the dynamic program down through the tree for the right child and storing the result
            }

            maximumAmountOfNutrients = 0; //setting max nutrients to 0 to make sure the variable is initialized 
            
            for (int total = 0; total <= amountOfGrowthAgents; total++) { //looping through every growth agent (+1 since java is 0 indexed)
                for (int agents = 0; agents <= total; agents++) { //looping through the edges (+1 since java is 0 indexed)
                    int edgeCapacity = (1 + agents) * (1 + agents); //from the problem, after giving *agents* agents, you can transport (1+agents)^2 nutrients
                    int remainingAgents = total - agents; //finding the agents remaining after using a specific amount

                    for (int agentsForLeftChild = 0; agentsForLeftChild <= remainingAgents; agentsForLeftChild++) { //looping through the remaining agents (+1 since java is 0 indexed)
                        int agentsForRightChild = remainingAgents - agentsForLeftChild; //finding the agents for the right child by taking the difference of the total left (remainingAgents) and the ones used for the left child
                        int totalNutrients = leftCache[agentsForLeftChild] + rightCache[agentsForRightChild]; //calculating the total amount of nutrients from the right and left children
                        int minimumNutrientFlow = Math.min(totalNutrients, edgeCapacity); //finding which is the minimum amount of nutrients the edge can handle
                        maximumAmountOfNutrients = Math.max(maximumAmountOfNutrients, minimumNutrientFlow); //if the calculated minimumNutrientFlow is bigger than the max nutrients, set it to the maximum amount 
                    }
                }
            }
        }

        dynamicProgramCache[localNodeId][amountOfGrowthAgents] = maximumAmountOfNutrients; //store the result in the cache so there are no repeat calculations
        return maximumAmountOfNutrients; //return the calculated max amount of nutrients
    }

}
