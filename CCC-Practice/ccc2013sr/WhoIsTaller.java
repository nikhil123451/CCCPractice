import java.util.*;

public class WhoIsTaller { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt(); // Number of people
        int M = scanner.nextInt(); // Number of comparisons

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int taller = scanner.nextInt();
            int shorter = scanner.nextInt();
            graph.get(taller).add(shorter); // taller -> shorter
        }

        int p = scanner.nextInt();
        int q = scanner.nextInt();

        if (canReach(graph, p, q, N)) {
            System.out.println("yes");
        } else if (canReach(graph, q, p, N)) {
            System.out.println("no");
        } else {
            System.out.println("unknown");
        }
    }

    private static boolean canReach(List<List<Integer>> graph, int start, int target, int N) {
        boolean[] visited = new boolean[N + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) return true;

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }
}
