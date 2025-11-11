import java.util.*;

public class Sunflowers { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] grid = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Rotate until sorted properly
        while (!isSorted(grid, N)) {
            grid = rotate(grid, N);
        }

        // Print the correct orientation
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean isSorted(int[][] grid, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (grid[i][j] <= grid[i][j - 1]) return false; // not increasing in row
            }
        }
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] <= grid[i - 1][j]) return false; // not increasing in column
            }
        }
        return true;
    }

    static int[][] rotate(int[][] grid, int N) {
        int[][] newGrid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newGrid[i][j] = grid[N - j - 1][i];
            }
        }
        return newGrid;
    }
}