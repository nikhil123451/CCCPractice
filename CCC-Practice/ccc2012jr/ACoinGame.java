import java.util.*;

public class ACoinGame { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {
        List<List<Stack<Integer>>> testCases = new ArrayList<>();

        // Read all input
        while (true) {
            int n = Integer.parseInt(scn.nextLine().trim());
            if (n == 0) break;

            String[] tokens = scn.nextLine().trim().split("\\s+");
            List<Stack<Integer>> initial = new ArrayList<>();
            for (String token : tokens) {
                Stack<Integer> stack = new Stack<>();
                stack.push(Integer.parseInt(token));
                initial.add(stack);
            }
            testCases.add(initial);
        }

        // Process each test case and collect results
        List<String> results = new ArrayList<>();
        for (List<Stack<Integer>> testCase : testCases) {
            int result = bfs(testCase);
            results.add(result == -1 ? "IMPOSSIBLE" : String.valueOf(result));
        }

        // Output all results
        for (String res : results) {
            System.out.println(res);
        }
        scn.close();
    }

    static int bfs(List<Stack<Integer>> start) {
        Set<String> visited = new HashSet<>();
        Queue<Object[]> queue = new LinkedList<>();

        String startSerial = serialize(start);
        visited.add(startSerial);
        queue.add(new Object[]{cloneState(start), 0});

        while (!queue.isEmpty()) {
            Object[] item = queue.poll();
            List<Stack<Integer>> state = (List<Stack<Integer>>) item[0];
            int moves = (int) item[1];

            if (isGoal(state)) return moves;

            int n = state.size();
            for (int i = 0; i < n; i++) {
                if (state.get(i).isEmpty()) continue;
                int coin = state.get(i).peek();

                // Try left
                if (i > 0 && canStack(coin, state.get(i - 1))) {
                    List<Stack<Integer>> newState = cloneState(state);
                    newState.get(i).pop();
                    newState.get(i - 1).push(coin);
                    String serial = serialize(newState);
                    if (!visited.contains(serial)) {
                        visited.add(serial);
                        queue.add(new Object[]{newState, moves + 1});
                    }
                }

                // Try right
                if (i < n - 1 && canStack(coin, state.get(i + 1))) {
                    List<Stack<Integer>> newState = cloneState(state);
                    newState.get(i).pop();
                    newState.get(i + 1).push(coin);
                    String serial = serialize(newState);
                    if (!visited.contains(serial)) {
                        visited.add(serial);
                        queue.add(new Object[]{newState, moves + 1});
                    }
                }
            }
        }

        return -1;
    }

    static boolean canStack(int coin, Stack<Integer> stack) {
        return stack.isEmpty() || coin < stack.peek();
    }

    static boolean isGoal(List<Stack<Integer>> state) {
        int n = state.size();
        for (Stack<Integer> stack : state) {
            if (stack.size() != 1) return false;
        }
        for (int i = 1; i < n; i++) {
            if (state.get(i - 1).peek() >= state.get(i).peek()) return false;
        }
        return true;
    }

    static String serialize(List<Stack<Integer>> state) {
        StringBuilder sb = new StringBuilder();
        for (Stack<Integer> stack : state) {
            sb.append("[");
            for (int val : stack) {
                sb.append(val).append(",");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    static List<Stack<Integer>> cloneState(List<Stack<Integer>> state) {
        List<Stack<Integer>> copy = new ArrayList<>();
        for (Stack<Integer> stack : state) {
            Stack<Integer> newStack = new Stack<>();
            newStack.addAll(stack);
            copy.add(newStack);
        }
        return copy;
    }
}
