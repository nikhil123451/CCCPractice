import java.util.*;

public class NutrientTree { //taken from GPT
    static final int MAX_NODES = 1000;

    static String input;
    static int index = 0;

    static boolean[] isLeaf = new boolean[MAX_NODES];
    static int[] nutrient = new int[MAX_NODES];
    static int[][] children = new int[MAX_NODES][2]; // IMPORTANT: fixed second dimension size 2
    static int[][] dpCache; // initialized after parsing
    static int nodeId = 0;
    static int X;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        X = Integer.parseInt(sc.nextLine());

        index = 0;
        nodeId = 0;
        
        int root = parseNode();

        dpCache = new int[nodeId][X + 1];
        for (int i = 0; i < nodeId; i++) {
            Arrays.fill(dpCache[i], -1);
        }
        int result = dp(root, X);

        System.out.println(result);
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
        nutrient[id] = val;
        return id;
    }

    static int storeInternalNode(int left, int right) {
        int id = nodeId++;
        isLeaf[id] = false;
        children[id][0] = left;
        children[id][1] = right;
        return id;
    }

    static int dp(int node, int x) {
        if (dpCache[node][x] != -1) return dpCache[node][x];

        int result;

        if (isLeaf[node]) {
            result = nutrient[node] + x;
        } else {
            int left = children[node][0];
            int right = children[node][1];

            int[] leftDP = new int[x + 1];
            int[] rightDP = new int[x + 1];

            for (int i = 0; i <= x; i++) {
                leftDP[i] = dp(left, i);
                rightDP[i] = dp(right, i);
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

        dpCache[node][x] = result;
        return result;
    }

}
