import java.util.*;

public class AliceThroughTheLookingGlass { //taken from GPT and modified
    // 5x5 base pattern
    static final boolean[][] BASE = new boolean[5][5];

    static {
        BASE[4 - 0][1] = true; // (1,0)
        BASE[4 - 0][2] = true; // (2,0)
        BASE[4 - 0][3] = true; // (3,0)
        BASE[4 - 1][2] = true; // (2,1)
    }
    
    static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        int T = scn.nextInt();
        List<String> results = new ArrayList<>();

        for (int t = 0; t < T; t++) {
            int m = scn.nextInt();
            int x = scn.nextInt();
            int y = scn.nextInt();
            results.add(isCrystal(x, y, m) ? "crystal" : "empty");
        }

        results.forEach(System.out::println);
        scn.close();
    }

    static boolean isCrystal(int x, int y, int level) {
        return check(x, y, level);
    }

    static boolean check(int x, int y, int level) {
        if (level == 1) {
            // Check if in initial base pattern at (0, 0)
            if (x >= 0 && x < 5 && y >= 0 && y < 5) {
                return inBase(x, y);
            }
            return false;
        }

        int size = pow5(level - 1);

        for (int dx = 0; dx < 5; dx++) {
            for (int dy = 0; dy < 5; dy++) {
                int baseX = x - dx * size;
                int baseY = y - dy * size;

                if (inBase(dx, dy)) {
                    int belowX = baseX;
                    int belowY = baseY - size;

                    if (check(belowX, belowY, level - 1)) {
                        if (!check(belowX, belowY + size, level - 1)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    static boolean inBase(int x, int y) {
        if (x < 0 || x >= 5 || y < 0 || y >= 5) return false;
        return BASE[4 - y][x];
    }

    static int pow5(int exp) {
        int result = 1;
        for (int i = 0; i < exp; i++) result *= 5;
        return result;
    }
}
