import java.util.*;

public class Unfriend { //taken from GPT and modified
	
    static Scanner scn = new Scanner(System.in); //creating a scanner to read input from console
	
    public static void main(String[] args) {
        int N = scn.nextInt();

        List<Integer>[] tree = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) tree[i] = new ArrayList<>();

        for (int i = 1; i < N; i++) {
            int inviter = scn.nextInt();
            tree[inviter].add(i);
        }

        int count = 0;

        for (int mask = 0; mask < (1 << (N - 1)); mask++) {
            Set<Integer> removed = new HashSet<>();
            for (int i = 1; i < N; i++) {
                if ((mask & (1 << (i - 1))) != 0) removed.add(i);
            }

            boolean valid = true;
            for (int person : removed) {
                Stack<Integer> stack = new Stack<>();
                stack.push(person);
                while (!stack.isEmpty()) {
                    int current = stack.pop();
                    for (int child : tree[current]) {
                        if (!removed.contains(child)) {
                            valid = false;
                            break;
                        }
                        stack.push(child);
                    }
                    if (!valid) break;
                }
                if (!valid) break;
            }

            if (valid) count++;
        }

        System.out.println(count);
    }
}
