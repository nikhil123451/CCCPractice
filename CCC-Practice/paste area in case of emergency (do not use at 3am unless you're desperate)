import java.util.*;

public class RMT {
    static int N, M, Q;
    static int[] lineOf, passengers;
    static ArrayList<Integer>[] lines;
    static int[] shift;
    static long[] bit;
    static final int THRESHOLD = 400; // sqrt(N)
    static boolean[] isBig;
    static long[][] bigLinePrefix;
    static int[] bigIndex;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder output = new StringBuilder();

        N = sc.nextInt();
        M = sc.nextInt();
        Q = sc.nextInt();

        lineOf = new int[N + 1];
        passengers = new int[N + 1];
        lines = new ArrayList[M + 1];
        for (int i = 1; i <= M; i++) lines[i] = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            lineOf[i] = sc.nextInt();
            lines[lineOf[i]].add(i);
        }

        for (int i = 1; i <= N; i++) passengers[i] = sc.nextInt();

        // Build Fenwick Tree
        bit = new long[N + 1];
        for (int i = 1; i <= N; i++) add(i, passengers[i]);

        shift = new int[M + 1];
        isBig = new boolean[M + 1];
        bigIndex = new int[M + 1];

        ArrayList<Integer> bigLines = new ArrayList<>();
        for (int i = 1; i <= M; i++) {
            if (lines[i].size() > THRESHOLD) {
                isBig[i] = true;
                bigIndex[i] = bigLines.size();
                bigLines.add(i);
            }
        }

        // Precompute prefix sums for big lines
        bigLinePrefix = new long[bigLines.size()][];
        for (int idx = 0; idx < bigLines.size(); idx++) {
            int line = bigLines.get(idx);
            ArrayList<Integer> st = lines[line];
            int sz = st.size();
            long[] pref = new long[sz + 1];
            for (int i = 0; i < sz; i++) {
                pref[i + 1] = pref[i] + passengers[st.get(i)];
            }
            bigLinePrefix[idx] = pref;
        }

        for (int q = 0; q < Q; q++) {
            int type = sc.nextInt();
            if (type == 1) {
                int l = sc.nextInt();
                int r = sc.nextInt();
                long ans = sum(r) - sum(l - 1);
                output.append(ans).append('\n');
            } else {
                int x = sc.nextInt();
                operate(x);
            }
        }

        // Output everything at once
        System.out.print(output.toString());
    }

    static void operate(int x) {
        if (!isBig[x]) {
            // Small line: perform rotation directly
            ArrayList<Integer> st = lines[x];
            int lastIdx = st.get(st.size() - 1);
            int lastVal = passengers[lastIdx];
            for (int i = st.size() - 1; i > 0; i--) {
                int to = st.get(i);
                int from = st.get(i - 1);
                update(to, passengers[from] - passengers[to]);
                passengers[to] = passengers[from];
            }
            update(st.get(0), lastVal - passengers[st.get(0)]);
            passengers[st.get(0)] = lastVal;
        } else {
            // Big line: just record shift offset
            shift[x] = (shift[x] + 1) % lines[x].size();
        }
    }

    // Fenwick Tree operations
    static void add(int i, long v) {
        for (; i <= N; i += i & -i) bit[i] += v;
    }

    static long sum(int i) {
        long s = 0;
        for (; i > 0; i -= i & -i) s += bit[i];
        return s;
    }

    static void update(int i, long diff) {
        add(i, diff);
    }
}