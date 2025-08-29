import java.util.*;

public class TheGenevaConfection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int[] cars = new int[N];
            for (int i = 0; i < N; i++) {
                cars[i] = sc.nextInt();
            }

            int[] stack = new int[N];
            int top = -1;
            int need = 1;

            // process cars in reverse order (bottom → top of input list)
            for (int i = N - 1; i >= 0; i--) {
                int car = cars[i];

                if (car == need) {
                    need++;
                } else {
                    stack[++top] = car;
                }

                // flush stack
                while (top >= 0 && stack[top] == need) {
                    top--;
                    need++;
                }
            }

            if (need == N + 1) out.append("Y\n");
            else out.append("N\n");
        }

        System.out.print(out.toString());
    }
}