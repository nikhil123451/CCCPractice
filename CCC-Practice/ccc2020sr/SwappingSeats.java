import java.util.*;

public class SwappingSeats { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        String s = scn.next();
        int n = s.length();

        int[] total = new int[3];
        for (char c : s.toCharArray()) {
            total[groupIdx(c)]++;
        }

        String doubled = s + s;

        int[][] perms = {
            {0, 1, 2},
            {0, 2, 1},
            {1, 0, 2},
            {1, 2, 0},
            {2, 0, 1},
            {2, 1, 0}
        };

        long answer = Long.MAX_VALUE;

        for (int[] perm : perms) {

            // group -> block mapping
            int[] groupToBlock = new int[3];
            for (int i = 0; i < 3; i++) {
                groupToBlock[perm[i]] = i;
            }

            int len0 = total[perm[0]];
            int len1 = total[perm[1]];
            int len2 = total[perm[2]];

            int[][] cnt = new int[3][3];

            // initial window
            for (int i = 0; i < len0; i++)
                cnt[0][groupToBlock[groupIdx(doubled.charAt(i))]]++;

            for (int i = len0; i < len0 + len1; i++)
                cnt[1][groupToBlock[groupIdx(doubled.charAt(i))]]++;

            for (int i = len0 + len1; i < n; i++)
                cnt[2][groupToBlock[groupIdx(doubled.charAt(i))]]++;

            for (int start = 0; start < n; start++) {

                if (start > 0) {
                    slide(cnt, doubled, start - 1, start + len0 - 1, 0, groupToBlock);
                    slide(cnt, doubled, start + len0 - 1, start + len0 + len1 - 1, 1, groupToBlock);
                    slide(cnt, doubled, start + len0 + len1 - 1, start + n - 1, 2, groupToBlock);
                }

                int[][] cur = new int[3][3];
                for (int i = 0; i < 3; i++)
                    cur[i] = cnt[i].clone();

                long swaps = 0;

                // direct swaps
                for (int i = 0; i < 3; i++) {
                    for (int j = i + 1; j < 3; j++) {
                        int x = Math.min(cur[i][j], cur[j][i]);
                        swaps += x;
                        cur[i][j] -= x;
                        cur[j][i] -= x;
                    }
                }

                // remaining 3-cycles
                int remaining = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (i != j) remaining += cur[i][j];
                    }
                }

                swaps += 2L * (remaining / 3);
                answer = Math.min(answer, swaps);
            }
        }

        System.out.println(answer);
    }

    static void slide(int[][] cnt, String s, int out, int in, int block, int[] map) {
        cnt[block][map[groupIdx(s.charAt(out))]]--;
        cnt[block][map[groupIdx(s.charAt(in))]]++;
    }

    static int groupIdx(char c) {
        return c == 'A' ? 0 : (c == 'B' ? 1 : 2);
    }
}