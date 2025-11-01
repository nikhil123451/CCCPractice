import java.util.*;

public class MinimumCostFlow { //taken from GPT and modified
    static int N, M;
    static long D;

    // Edge data
    static int[] U, V;
    static long[] W;
    static boolean[] isInitial, inMST;

    // DSU
    static int[] parent, rank;

    // Graph and LCA
    static ArrayList<ArrayList<int[]>> adj;
    static final int LOG = 18;
    static int[][] up;
    static long[][] maxW;
    static boolean[][] hasInitMax, hasNonInitMax;
    static int[] depth;
    static long mstCost;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        D = sc.nextLong();

        U = new int[M];
        V = new int[M];
        W = new long[M];
        isInitial = new boolean[M];
        inMST = new boolean[M];

        for (int i = 0; i < M; i++) {
            U[i] = sc.nextInt();
            V[i] = sc.nextInt();
            W[i] = sc.nextLong();
            isInitial[i] = (i < N - 1);
        }

        // --- Build MST (Kruskal) ---
        Integer[] order = new Integer[M];
        for (int i = 0; i < M; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingLong(i -> W[i]));

        dsuInit(N);
        adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) adj.add(new ArrayList<>());

        mstCost = 0;
        int taken = 0;
        for (int id : order) {
            if (unite(U[id], V[id])) {
                inMST[id] = true;
                mstCost += W[id];
                adj.get(U[id]).add(new int[]{V[id], id});
                adj.get(V[id]).add(new int[]{U[id], id});
                taken++;
                if (taken == N - 1) break;
            }
        }

        // Count overlap between MST and initial edges
        int common = 0;
        for (int i = 0; i < M; i++)
            if (inMST[i] && isInitial[i])
                common++;

        // --- Prepare LCA tables ---
        up = new int[LOG][N + 1];
        maxW = new long[LOG][N + 1];
        hasInitMax = new boolean[LOG][N + 1];
        hasNonInitMax = new boolean[LOG][N + 1];
        depth = new int[N + 1];

        dfs(1, 0);

        for (int k = 1; k < LOG; k++) {
            for (int v = 1; v <= N; v++) {
                int mid = up[k - 1][v];
                if (mid == 0) continue;
                long lw = maxW[k - 1][v], rw = maxW[k - 1][mid];
                if (lw > rw) {
                    up[k][v] = up[k - 1][mid];
                    maxW[k][v] = lw;
                    hasInitMax[k][v] = hasInitMax[k - 1][v];
                    hasNonInitMax[k][v] = hasNonInitMax[k - 1][v];
                } else if (rw > lw) {
                    up[k][v] = up[k - 1][mid];
                    maxW[k][v] = rw;
                    hasInitMax[k][v] = hasInitMax[k - 1][mid];
                    hasNonInitMax[k][v] = hasNonInitMax[k - 1][mid];
                } else {
                    up[k][v] = up[k - 1][mid];
                    maxW[k][v] = lw;
                    hasInitMax[k][v] = hasInitMax[k - 1][v] || hasInitMax[k - 1][mid];
                    hasNonInitMax[k][v] = hasNonInitMax[k - 1][v] || hasNonInitMax[k - 1][mid];
                }
            }
        }

        // --- Compute best result ---
        long bestCost = Long.MAX_VALUE;
        int bestDays = Integer.MAX_VALUE;

        // Case 1: enhance MST edge
        for (int i = 0; i < M; i++) {
            if (!inMST[i]) continue;
            long newCost = mstCost - Math.min(D, W[i]);
            int days = (N - 1) - common;
            if (newCost < bestCost || (newCost == bestCost && days < bestDays)) {
                bestCost = newCost;
                bestDays = days;
            }
        }

        // Case 2: enhance non-MST edge
        for (int i = 0; i < M; i++) {
            if (inMST[i]) continue;
            long cPrime = Math.max(0, W[i] - D);
            long[] result = queryPath(U[i], V[i]);
            long maxEdge = result[0];
            boolean hasInit = result[1] == 1;
            boolean hasNonInit = result[2] == 1;

            if (cPrime <= maxEdge) {
                long newCost = mstCost + cPrime - maxEdge;
                if (newCost > bestCost) continue;
                int add = isInitial[i] ? 1 : 0;
                int removeIfAllInit = hasNonInit ? 0 : 1;
                int newCommon = common + add - removeIfAllInit;
                int days = (N - 1) - newCommon;
                if (newCost < bestCost || (newCost == bestCost && days < bestDays)) {
                    bestCost = newCost;
                    bestDays = days;
                }
            }
        }

        if (bestDays == Integer.MAX_VALUE) bestDays = (N - 1) - common;
        System.out.println(bestDays);
    }

    // ---------- DSU ----------
    static void dsuInit(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;
    }
    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    static boolean unite(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) parent[a] = b;
        else if (rank[b] < rank[a]) parent[b] = a;
        else { parent[b] = a; rank[a]++; }
        return true;
    }

    // ---------- DFS for LCA ----------
    static void dfs(int u, int p) {
        for (int[] pair : adj.get(u)) {
            int v = pair[0], id = pair[1];
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            up[0][v] = u;
            maxW[0][v] = W[id];
            hasInitMax[0][v] = isInitial[id];
            hasNonInitMax[0][v] = !isInitial[id];
            dfs(v, u);
        }
    }

    // ---------- Query path info (returns [maxWeight, hasInit, hasNonInit]) ----------
    static long[] queryPath(int a, int b) {
        long maxEdge = -1;
        boolean hasInit = false;
        boolean hasNonInit = false;

        if (depth[a] < depth[b]) {
            int tmp = a; a = b; b = tmp;
        }

        int diff = depth[a] - depth[b];
        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                if (maxW[k][a] > maxEdge) {
                    maxEdge = maxW[k][a];
                    hasInit = hasInitMax[k][a];
                    hasNonInit = hasNonInitMax[k][a];
                } else if (maxW[k][a] == maxEdge) {
                    hasInit |= hasInitMax[k][a];
                    hasNonInit |= hasNonInitMax[k][a];
                }
                a = up[k][a];
            }
        }

        if (a == b) return new long[]{maxEdge, hasInit ? 1 : 0, hasNonInit ? 1 : 0};

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][a] != 0 && up[k][a] != up[k][b]) {
                long[] res = combine(maxEdge, hasInit, hasNonInit, maxW[k][a], hasInitMax[k][a], hasNonInitMax[k][a]);
                maxEdge = res[0]; hasInit = res[1] == 1; hasNonInit = res[2] == 1;
                res = combine(maxEdge, hasInit, hasNonInit, maxW[k][b], hasInitMax[k][b], hasNonInitMax[k][b]);
                maxEdge = res[0]; hasInit = res[1] == 1; hasNonInit = res[2] == 1;
                a = up[k][a];
                b = up[k][b];
            }
        }

        long[] res1 = combine(maxEdge, hasInit, hasNonInit, maxW[0][a], hasInitMax[0][a], hasNonInitMax[0][a]);
        maxEdge = res1[0]; hasInit = res1[1] == 1; hasNonInit = res1[2] == 1;
        long[] res2 = combine(maxEdge, hasInit, hasNonInit, maxW[0][b], hasInitMax[0][b], hasNonInitMax[0][b]);
        maxEdge = res2[0]; hasInit = res2[1] == 1; hasNonInit = res2[2] == 1;

        return new long[]{maxEdge, hasInit ? 1 : 0, hasNonInit ? 1 : 0};
    }

    // ---------- Combine edge info helper ----------
    static long[] combine(long curMax, boolean curInit, boolean curNonInit,
                          long nextMax, boolean nextInit, boolean nextNonInit) {
        if (nextMax > curMax) return new long[]{nextMax, nextInit ? 1 : 0, nextNonInit ? 1 : 0};
        if (nextMax < curMax) return new long[]{curMax, curInit ? 1 : 0, curNonInit ? 1 : 0};
        return new long[]{curMax, (curInit || nextInit) ? 1 : 0, (curNonInit || nextNonInit) ? 1 : 0};
    }
}