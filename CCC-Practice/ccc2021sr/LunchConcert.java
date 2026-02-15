import java.util.*;

public class LunchConcert {

    static Scanner scn = new Scanner(System.in);

    static int N;
    static long[] P, W, D;

    public static void main(String[] args) {

        N = scn.nextInt();

        P = new long[N];
        W = new long[N];
        D = new long[N];

        long left = Long.MAX_VALUE;
        long right = Long.MIN_VALUE;

        for (int i = 0; i < N; i++) {
            P[i] = scn.nextLong();
            W[i] = scn.nextLong();
            D[i] = scn.nextLong();

            left = Math.min(left, P[i] - D[i]);
            right = Math.max(right, P[i] + D[i]);
        }

        // Ternary search on convex function
        while (right - left > 3) {
            long m1 = left + (right - left) / 3;
            long m2 = right - (right - left) / 3;

            if (cost(m1) <= cost(m2)) {
                right = m2 - 1;
            } else {
                left = m1 + 1;
            }
        }

        long answer = Long.MAX_VALUE;

        for (long c = left; c <= right; c++) {
            answer = Math.min(answer, cost(c));
        }

        System.out.println(answer);
    }

    static long cost(long c) {
        long total = 0;

        for (int i = 0; i < N; i++) {
            long dist = Math.abs(P[i] - c) - D[i];
            if (dist > 0) {
                total += dist * W[i];
            }
        }

        return total;
    }
}