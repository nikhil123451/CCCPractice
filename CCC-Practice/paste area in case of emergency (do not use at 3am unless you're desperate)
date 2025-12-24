import java.util.*;

public class RuleOfThree { //taken from GPT and modified

    static String[] lhs = new String[3];
    static String[] rhs = new String[3];

    static int S;
    static String I, F;

    static int[] usedRule;
    static int[] usedPos;
    static String[] usedWord;

    static boolean solved = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read substitution rules
        for (int i = 0; i < 3; i++) {
            lhs[i] = sc.next();
            rhs[i] = sc.next();
        }

        // Read S, I, F
        S = sc.nextInt();
        I = sc.next();
        F = sc.next();

        usedRule = new int[S];
        usedPos = new int[S];
        usedWord = new String[S];

        dfs(0, I);

        // Output result
        for (int i = 0; i < S; i++) {
            System.out.println(
                usedRule[i] + " " +
                usedPos[i] + " " +
                usedWord[i]
            );
        }
    }

    static void dfs(int step, String current) {
        if (solved) return;

        if (step == S) {
            if (current.equals(F)) {
                solved = true;
            }
            return;
        }

        if (current.length() > 50) return;

        // Try each rule
        for (int r = 0; r < 3; r++) {
            String from = lhs[r];
            String to = rhs[r];

            // Try each possible position
            for (int i = 0; i + from.length() <= current.length(); i++) {
                if (current.substring(i, i + from.length()).equals(from)) {

                    String next =
                        current.substring(0, i) +
                        to +
                        current.substring(i + from.length());

                    usedRule[step] = r + 1;       // 1-indexed
                    usedPos[step] = i + 1;        // 1-indexed
                    usedWord[step] = next;

                    dfs(step + 1, next);

                    if (solved) return;
                }
            }
        }
    }
}