import java.util.*;

public class ConvexHull { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int K = sc.nextInt();
        int N = sc.nextInt();
        int M = sc.nextInt();

        // adjacency list: each edge = {to, time, wear}
        ArrayList<int[]>[] adj = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), t = sc.nextInt(), h = sc.nextInt();
            adj[a].add(new int[]{b, t, h});
            adj[b].add(new int[]{a, t, h});
        }

        int A = sc.nextInt(), B = sc.nextInt();
        int C = K - 1; // hull wear limit
        long INF = Long.MAX_VALUE / 4;

        long[][] dist = new long[N+1][C+1];
        for (int i = 1; i <= N; i++) Arrays.fill(dist[i], INF);

        // priority queue holds {time, node, usedWear}
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(x -> x[0]));
        dist[A][0] = 0;
        pq.add(new long[]{0, A, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long time = cur[0];
            int node = (int)cur[1];
            int used = (int)cur[2];
            if (time != dist[node][used]) continue;

            for (int[] e : adj[node]) {
                int to = e[0], t = e[1], h = e[2];
                int nu = used + h;
                if (nu > C) continue;
                long nt = time + t;
                if (nt < dist[to][nu]) {
                    dist[to][nu] = nt;
                    pq.add(new long[]{nt, to, nu});
                }
            }
        }

        long ans = INF;
        for (int w = 0; w <= C; w++) ans = Math.min(ans, dist[B][w]);
        System.out.println(ans == INF ? -1 : ans);
    }
}