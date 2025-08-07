import java.util.*;

public class MouseJourney { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int R = sc.nextInt();
        int C = sc.nextInt();
        int K = sc.nextInt();

        boolean[][] hasCat = new boolean[R + 1][C + 1];

        for (int i = 0; i < K; i++) {
            int r = sc.nextInt();
            int c = sc.nextInt();
            hasCat[r][c] = true;
        }

        int[][] dp = new int[R + 1][C + 1];
        dp[1][1] = 1; // Start point

        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                if (hasCat[i][j]) {
                    dp[i][j] = 0;
                } else {
                    if (i > 1) dp[i][j] += dp[i - 1][j];
                    if (j > 1) dp[i][j] += dp[i][j - 1];
                }
            }
        }

        System.out.println(dp[R][C]);
    }
}
