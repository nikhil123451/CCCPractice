import java.util.*;

public class ACoinGame2 {

    static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {
        List<List<Stack<Integer>>> testCases = new ArrayList<>();
        
        while (true) {
            int coins = Integer.parseInt(scn.nextLine().trim());
            if (coins == 0) {
            	break;
            }

            String[] tokens = scn.nextLine().trim().split("\\s+");
            List<Stack<Integer>> initialState = new ArrayList<>();
            for (String token : tokens) {
                Stack<Integer> stack = new Stack<>();
                stack.push(Integer.parseInt(token));
                initialState.add(stack);
            }
            testCases.add(initialState);
        }

        List<String> results = new ArrayList<>();
        for (List<Stack<Integer>> testCase : testCases) {
            int result = minimumMoves(testCase);
            results.add(result == -1 ? "IMPOSSIBLE" : String.valueOf(result));
        }
        
        for (String result : results) {
            System.out.println(result);
        }
        scn.close();
    }

    static int minimumMoves(List<Stack<Integer>> startingState) {
        Set<String> processedStates = new HashSet<>();
        Queue<Object[]> states = new LinkedList<>();

        String startSerial = serialize(startingState);
        processedStates.add(startSerial);
        states.add(new Object[]{cloneState(startingState), 0});

        while (!states.isEmpty()) {
            Object[] currentStateInformation = states.poll();
            @SuppressWarnings("unchecked")
			List<Stack<Integer>> currentState = (List<Stack<Integer>>) currentStateInformation[0];
            int moves = (int) currentStateInformation[1];

            if (isGoal(currentState)) {
            	return moves;
            }

            int spots = currentState.size();
            for (int i = 0; i < spots; i++) {
                if (currentState.get(i).isEmpty()) {
                	continue;
                }
                int coin = currentState.get(i).peek();
                
                if (i > 0 && canStack(coin, currentState.get(i - 1))) {
                    List<Stack<Integer>> newState = cloneState(currentState);
                    newState.get(i).pop();
                    newState.get(i - 1).push(coin);
                    String serializedNewState = serialize(newState);
                    if (!processedStates.contains(serializedNewState)) {
                        processedStates.add(serializedNewState);
                        states.add(new Object[]{newState, moves + 1});
                    }
                }

                if (i < spots - 1 && canStack(coin, currentState.get(i + 1))) {
                    List<Stack<Integer>> newState = cloneState(currentState);
                    newState.get(i).pop();
                    newState.get(i + 1).push(coin);
                    String serial = serialize(newState);
                    if (!processedStates.contains(serial)) {
                        processedStates.add(serial);
                        states.add(new Object[]{newState, moves + 1});
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
        int spots = state.size();
        for (Stack<Integer> stack : state) {
            if (stack.size() != 1) {
            	return false;
            }
        }
        for (int i = 1; i < spots; i++) {
            if (state.get(i - 1).peek() >= state.get(i).peek()) {
            	return false;
            }
        }
        return true;
    }

    static String serialize(List<Stack<Integer>> state) {
        StringBuilder builder = new StringBuilder();
        for (Stack<Integer> stack : state) {
            builder.append("[");
            for (int coinValue : stack) {
                builder.append(coinValue).append(",");
            }
            builder.append("]");
        }
        return builder.toString();
    }

    static List<Stack<Integer>> cloneState(List<Stack<Integer>> state) {
        List<Stack<Integer>> copiedState = new ArrayList<>();
        for (Stack<Integer> stack : state) {
            Stack<Integer> newStack = new Stack<>();
            newStack.addAll(stack);
            copiedState.add(newStack);
        }
        return copiedState;
    }
}
