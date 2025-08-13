import java.util.*;

public class ChancesOfWinning { //taken from GPT and modified
    static int T;
    static int[] pts = new int[5];         // 1..4
    static boolean[][] played = new boolean[5][5];
    static List<int[]> remaining = new ArrayList<>();
    static int answer = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        int G = sc.nextInt();

        // Process already played games and award points
        for (int i = 0; i < G; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            int SA = sc.nextInt();
            int SB = sc.nextInt();

            played[A][B] = played[B][A] = true;
            if (SA > SB) {
                pts[A] += 3;
            } else if (SA < SB) {
                pts[B] += 3;
            } else {
                pts[A] += 1;
                pts[B] += 1;
            }
        }

        // Build list of remaining games (all pairs i<j not yet played)
        for (int i = 1; i <= 4; i++) {
            for (int j = i + 1; j <= 4; j++) {
                if (!played[i][j]) remaining.add(new int[]{i, j});
            }
        }

        dfs(0);

        System.out.println(answer);
        sc.close();
    }

    // Explore outcomes for remaining games starting at index idx
    static void dfs(int idx) {
        if (idx == remaining.size()) {
            // Check if T is strictly ahead of all others
            for (int team = 1; team <= 4; team++) {
                if (team == T) continue;
                if (pts[T] <= pts[team]) return;
            }
            answer++;
            return;
        }

        int[] g = remaining.get(idx);
        int A = g[0], B = g[1];

        // Case 1: A wins
        pts[A] += 3;
        dfs(idx + 1);
        pts[A] -= 3;

        // Case 2: B wins
        pts[B] += 3;
        dfs(idx + 1);
        pts[B] -= 3;

        // Case 3: Tie
        pts[A] += 1; pts[B] += 1;
        dfs(idx + 1);
        pts[A] -= 1; pts[B] -= 1;
    }
}