import java.util.*;

public class NutrientTree { //taken from GPT
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

    static void skipSpaces() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
    }

    static int parseNode() {
        skipSpaces();

        if (index >= input.length()) {
            throw new IllegalArgumentException("Unexpected end of input at position " + index);
        }

        char c = input.charAt(index);

        if (c == '(') {
            index++; // skip '('
            skipSpaces();

            int left = parseNode();
            skipSpaces();

            int right = parseNode();
            skipSpaces();

            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Expected ')' at position " + index + ", but found '" +
                        (index < input.length() ? input.charAt(index) : "EOF") + "'");
            }
            index++; // skip ')'
            return storeInternalNode(left, right);

        } else if (Character.isDigit(c)) {
            int num = 0;
            while (index < input.length() && Character.isDigit(input.charAt(index))) {
                num = num * 10 + (input.charAt(index++) - '0');
            }
            return storeLeafNode(num);
        } else {
            throw new IllegalArgumentException("Unexpected character at position " + index + ": '" + c + "'");
        }
    }

    static int storeLeafNode(int val) {
        int id = nodeId++;
        isLeaf[id] = true;
        nutrients[id] = val;
        return id;
    }

    static int storeInternalNode(int left, int right) {
        int id = nodeId++;
        isLeaf[id] = false;
        children[id][0] = left;
        children[id][1] = right;
        return id;
    }

    static int dynamicProgram(int node, int x) {
        if (dynamicProgramCache[node][x] != -1) return dynamicProgramCache[node][x];

        int result;

        if (isLeaf[node]) {
            result = nutrients[node] + x;
        } else {
            int left = children[node][0];
            int right = children[node][1];

            int[] leftDP = new int[x + 1];
            int[] rightDP = new int[x + 1];

            for (int i = 0; i <= x; i++) {
                leftDP[i] = dynamicProgram(left, i);
                rightDP[i] = dynamicProgram(right, i);
            }

            result = 0;
            for (int total = 0; total <= x; total++) {
                for (int edgeGrowth = 0; edgeGrowth <= total; edgeGrowth++) {
                    int cap = (1 + edgeGrowth) * (1 + edgeGrowth);
                    int remaining = total - edgeGrowth;

                    for (int l = 0; l <= remaining; l++) {
                        int r = remaining - l;
                        int sum = leftDP[l] + rightDP[r];
                        result = Math.max(result, Math.min(sum, cap));
                    }
                }
            }
        }

        dynamicProgramCache[node][x] = result;
        return result;
    }

}
