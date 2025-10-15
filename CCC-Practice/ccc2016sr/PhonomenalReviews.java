import java.util.*;

public class PhonomenalReviews { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        boolean[] isPho = new boolean[N];
        for (int i = 0; i < M; i++) {
            isPho[sc.nextInt()] = true;
        }

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < N - 1; i++) {
            int a = sc.nextInt(), b = sc.nextInt();
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        // Step 1: Prune non-Pho leaves
        Queue<Integer> q = new ArrayDeque<>();
        int remainingEdges = N - 1;
        int[] degree = new int[N];
        for (int i = 0; i < N; i++) degree[i] = adj.get(i).size();

        for (int i = 0; i < N; i++) {
            if (degree[i] == 1 && !isPho[i]) q.add(i);
        }

        boolean[] removed = new boolean[N];
        while (!q.isEmpty()) {
            int u = q.poll();
            removed[u] = true;
            for (int v : adj.get(u)) {
                degree[v]--;
                if (!removed[v]) {
                    remainingEdges--;
                    if (degree[v] == 1 && !isPho[v]) q.add(v);
                }
            }
        }

        // Step 2: Find diameter of remaining tree
        int start = -1;
        for (int i = 0; i < N; i++) {
            if (!removed[i]) {
                start = i;
                break;
            }
        }

        int farthestNode = bfs(start, adj, removed).getKey();
        int diameter = bfs(farthestNode, adj, removed).getValue();

        System.out.println(2 * remainingEdges - diameter);
        
        sc.close();
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> bfs(int start, List<List<Integer>> adj, boolean[] removed) {
        Queue<Integer> q = new ArrayDeque<>();
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, -1);
        q.add(start);
        dist[start] = 0;

        int farthestNode = start;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (!removed[v] && dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.add(v);
                    if (dist[v] > dist[farthestNode]) farthestNode = v;
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(farthestNode, dist[farthestNode]);
    }
}