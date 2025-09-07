import java.util.*;

public class Jerseys {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int J = sc.nextInt();  // number of jerseys
        int A = sc.nextInt();  // number of athletes

        // Map sizes to ranks
        Map<Character, Integer> rank = new HashMap<>();
        rank.put('S', 0);
        rank.put('M', 1);
        rank.put('L', 2);

        int[] jerseys = new int[J + 1]; // jersey sizes (ranked)
        for (int j = 1; j <= J; j++) {
            char size = sc.next().charAt(0);
            jerseys[j] = rank.get(size);
        }

        boolean[] used = new boolean[J + 1]; // track assigned jerseys
        int satisfied = 0;

        for (int i = 0; i < A; i++) {
            char prefSize = sc.next().charAt(0);
            int jerseyNum = sc.nextInt();

            int pref = rank.get(prefSize);

            if (!used[jerseyNum] && jerseys[jerseyNum] >= pref) {
                used[jerseyNum] = true;
                satisfied++;
            }
        }

        System.out.println(satisfied);
    }
}