import java.util.Scanner;

public class Sunflowers2 {
	
	static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {
        
        int flowers = scn.nextInt();
        int[][] grid = new int[flowers][flowers];

        for (int i = 0; i < flowers; i++) {
            for (int j = 0; j < flowers; j++) {
                grid[i][j] = scn.nextInt();
            }
        }

        while (!isSorted(grid, flowers)) {
            grid = rotate(grid, flowers);
        }

        for (int i = 0; i < flowers; i++) {
            for (int j = 0; j < flowers; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean isSorted(int[][] grid, int flowers) {
        for (int i = 0; i < flowers; i++) {
            for (int j = 1; j < flowers; j++) {
                if (grid[i][j] <= grid[i][j - 1]) {
                	return false;
                }
            }
        }
        for (int i = 1; i < flowers; i++) {
            for (int j = 0; j < flowers; j++) {
                if (grid[i][j] <= grid[i - 1][j]) {
                	return false;
                }
            }
        }
        return true;
    }

    static int[][] rotate(int[][] grid, int flowers) {
        int[][] newGrid = new int[flowers][flowers];
        for (int i = 0; i < flowers; i++) {
            for (int j = 0; j < flowers; j++) {
                newGrid[i][j] = grid[flowers - j - 1][i];
            }
        }
        return newGrid;
    }
}