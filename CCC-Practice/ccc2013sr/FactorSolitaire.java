import java.util.*;

public class FactorSolitaire {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        long[] dp = new long[N + 1];
        Arrays.fill(dp, Long.MAX_VALUE / 2); // prevent overflow
        dp[1] = 0;

        // Precompute divisors
        List<Integer>[] divisors = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            divisors[i] = new ArrayList<>();
        }
        for (int i = 1; i <= N; i++) {
            for (int j = i; j <= N; j += i) {
                divisors[j].add(i);
            }
        }

        // DP transitions
        for (int c = 1; c < N; c++) {
            long currCost = dp[c];
            if (currCost >= Long.MAX_VALUE / 2) continue;

            for (int a : divisors[c]) {
                int b = c / a;
                int next = c + a;
                if (next <= N) {
                    dp[next] = Math.min(dp[next], currCost + b);
                }
            }
        }

        System.out.println(dp[N]);
    }
}