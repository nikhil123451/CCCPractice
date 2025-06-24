import java.util.*;

public class AnimalFarm {//taken from GPT
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt(); // number of pens
        int OUTSIDE = M;      // virtual node for outside

        Map<String, List<int[]>> edgeMap = new HashMap<>();
        List<int[]> edges = new ArrayList<>(); // Each edge is [from, to, cost]

        for (int pen = 0; pen < M; pen++) {
            int ep = sc.nextInt(); // number of edges in this pen
            int[] corners = new int[ep];
            int[] costs = new int[ep];

            for (int i = 0; i < ep; i++) corners[i] = sc.nextInt();
            for (int i = 0; i < ep; i++) costs[i] = sc.nextInt();

            for (int i = 0; i < ep; i++) {
                int a = corners[i];
                int b = corners[(i + 1) % ep];
                int cost = costs[i];

                int u = Math.min(a, b);
                int v = Math.max(a, b);
                String key = u + "," + v;

                edgeMap.putIfAbsent(key, new ArrayList<>());
                edgeMap.get(key).add(new int[]{pen, cost});
            }
        }

        // Build edges
        for (String key : edgeMap.keySet()) {
            List<int[]> usage = edgeMap.get(key);
            if (usage.size() == 2) {
                int[] a = usage.get(0), b = usage.get(1);
                int cost = Math.min(a[1], b[1]); // Use the minimum cost between the two pens
                edges.add(new int[]{a[0], b[0], cost});
            } else if (usage.size() == 1) {
                int[] a = usage.get(0);
                edges.add(new int[]{a[0], OUTSIDE, a[1]}); // Edge to outside
            }
        }

        // Sort edges by cost
        edges.sort(Comparator.comparingInt(e -> e[2]));

        // Try two MSTs: one with outside, one without
        int costWithOutside = kruskal(edges, M + 1);
        int costWithoutOutside = kruskal(
            edges.stream().filter(e -> e[0] != M && e[1] != M).toList(),
            M
        );

        System.out.println(Math.min(costWithOutside, costWithoutOutside));
    }

    static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    static int kruskal(List<int[]> edges, int nodeCount) {
        int[] parent = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) parent[i] = i;

        int cost = 0, used = 0;
        for (int[] e : edges) {
            int a = e[0], b = e[1], w = e[2];
            int ra = find(parent, a), rb = find(parent, b);
            if (ra != rb) {
                parent[rb] = ra;
                cost += w;
                used++;
            }
        }

        return cost;
    }
}
