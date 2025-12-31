import java.util.*;

public class Tourism { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in);
    static int N, K;
    static long[] a;
    static int[] seg;

    public static void main(String[] args) {
        N = scn.nextInt();
        K = scn.nextInt();

        a = new long[N];
        for (int i = 0; i < N; i++) {
            a[i] = scn.nextLong();
        }

        seg = new int[4 * N];
        build(1, 0, N - 1);

        int days = (N + K - 1) / K;
        long answer = 0;

        int right = N - 1;

        for (int d = 0; d < days; d++) {
            int left = Math.max(0, right - K + 1);
            int idx = query(1, 0, N - 1, left, right);
            answer += a[idx];
            right = idx - 1;
        }

        System.out.println(answer);
    }

    static void build(int node, int l, int r) {
        if (l == r) {
            seg[node] = l;
            return;
        }
        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        int leftIdx = seg[node * 2];
        int rightIdx = seg[node * 2 + 1];
        seg[node] = (a[leftIdx] >= a[rightIdx]) ? leftIdx : rightIdx;
    }

    static int query(int node, int l, int r, int ql, int qr) {
        if (qr < l || r < ql) return -1;
        if (ql <= l && r <= qr) return seg[node];

        int mid = (l + r) / 2;
        int leftIdx = query(node * 2, l, mid, ql, qr);
        int rightIdx = query(node * 2 + 1, mid + 1, r, ql, qr);

        if (leftIdx == -1) return rightIdx;
        if (rightIdx == -1) return leftIdx;
        return (a[leftIdx] >= a[rightIdx]) ? leftIdx : rightIdx;
    }
}